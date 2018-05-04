package game.mind;

import java.util.HashMap;

import game.general.GameAction;
import game.general.GameServer;
import game.general.GameState;
import game.general.Timer;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.MulinoAction;
import mulino.MulinoSettings;
import mulino.MulinoState;

public abstract class Mind {

	private GameServer server;
	private StrategyFactory strategyFactory;
	
	// state pattern: internal mind state
	private StateFactory mindStates;
	private MindState state;
	private ThinkingStatus thinkingStatus=null;
	
	private GameState gameState;
	
	public Mind(GameServer server, StrategyFactory strategyFactory) {
		this.server = server;
		this.strategyFactory = strategyFactory;
		
		mindStates = new StateFactory();
		state = mindStates.of(State.INIT);
	}
	
	// starts the FSA
	public void start() {
		state.handle(); // do set-up operations
		stateChanged(); // INIT -> PLAY|WAIT
	}
	
	// 1: first to play, >1: wait for your turn
	public abstract int getGamePosition();
	
	private void setGameState(GameState state) {
		gameState = state;
	}
	
	private void stateChanged() {
		while(state != mindStates.of(State.END)) {
			state = state.transition(); // polymorphism!
			state.handle();
		}
	}
	
	
	// -------------------- INNER CLASSES --------------------
	
	private enum State { INIT, PLAY, WAIT, END };
	
	// flyweight factory
	private class StateFactory {
		
		private MindState[] states;
		
		public StateFactory() {
			states = new MindState[] {
				new StateInit(), new StatePlay(), new StateWait(), new StateEnd()
			};
		}
		
		public MindState of(State key) {
			return states[key.ordinal()];
		}
	}
	
	private abstract class MindState {
		
		public abstract void handle();
		
		public MindState transition() {
			return mindStates.of(transitionTo());
		}
		
		protected abstract State transitionTo();
	}
	
	private class StateInit extends MindState {

		@Override
		public void handle() {
			// set the initial state of the game (received from the server)
			setGameState(server.getInitState()); // wait...
		}

		@Override
		protected State transitionTo() {
			return getGamePosition()==1 ? State.PLAY : State.WAIT;
		}
	}

	private class StatePlay extends MindState {

		// start timer and thinking thread (based on current game state)
		// when timeout or thinking's over:
		//		stop thinking and timer threads
		//		get selected action
		// 		play action (game server)
		//		update current game state
		// return -> mind stateChanged
		@Override
		public void handle() {
			System.out.println("--------------------------------------------\nStato di play");
			WaitingQueue queue = new WaitingQueue();
			
			Timer timer = new Timer(queue, MulinoSettings.TIMER);
			ThinkingThread thinkingThr = new ThinkingThread(queue, strategyFactory, gameState, thinkingStatus);

			timer.start();
			thinkingThr.start();
			
			try {
				queue.await();

				thinkingThr.interrupt(); // javadoc: "Interrupting a thread that is not alive need not have any effect."
				timer.interrupt();
				GameAction action = thinkingThr.getSelectedAction();
				printInfo(action);
				server.playAction(action);
				setGameState(action.perform(gameState));
				
				server.getCurrentState(); // after my own move
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		protected State transitionTo() {
			return gameState.isOver() ? State.END : State.WAIT;
		}
	}

	private class StateWait extends MindState {

		// start waiting thread (based on current state and predictions)
		// read state from server (waiting for opponent move...)
		// when state read:
		//		stop waiting thread (save "thinking status")
		//		update current game state
		// return -> mind stateChanged
		@Override
		public void handle() {
			System.out.println("--------------------------------------------\nStato di wait");
			WaitingThread waitingThr = new WaitingThread(strategyFactory, gameState);
			waitingThr.start();
			
			GameState gameState = server.getCurrentState(); // waiting...
			
			printInfo(gameState);
			waitingThr.interrupt();
			thinkingStatus = waitingThr.getThinkingStatus();
			
			setGameState(gameState);
		}

		@Override
		protected State transitionTo() {
			return gameState.isOver() ? State.END : State.PLAY;
		}
	}

	private class StateEnd extends MindState {

		@Override
		public void handle() {
			System.out.println("GAME OVER");
			System.out.println(gameState);
		}

		@Override
		protected State transitionTo() { // it is never called
			return null;
		}
	}
	
	//Per DEBUG
	private void printInfo(GameState gameState) {
		MulinoState state = (MulinoState) gameState;
		HashMap<int[], Checker> board = state.getBoard();
		System.out.println("Stato ricevuto:");
		for(int[] i : board.keySet()) {
			System.out.println(i[0]+","+i[1]+": "+board.get(i));
		}
	}
	
	private void printInfo(GameAction gameAction) {
		MulinoAction action=(MulinoAction) gameAction;
		System.out.println("Azione: "+action.getTo()[0]+","+action.getTo()[1]);
				if (action.getRemoveOpponent().isPresent())
					System.out.print(" rimovendo "+action.getRemoveOpponent().get()[0]+","+action.getRemoveOpponent().get()[1]);
	}
	

}
