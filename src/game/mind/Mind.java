package game.mind;

import game.general.GameAction;
import game.general.GameServer;
import game.general.GameState;
import game.general.Timer;
import mulino.MulinoSettings;

public abstract class Mind {

	private GameServer server;
	private StrategyFactory strategyFactory;
	
	// state pattern: internal mind state
	private StateFactory mindStates;
	private MindState state;
	private ThinkingStatus thinkingStatus;
	
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
			setGameState(server.getCurrentState()); // wait...
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
				server.playAction(action);
				
				setGameState(action.perform(gameState));
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
			WaitingThread waitingThr = new WaitingThread(strategyFactory, gameState);
			waitingThr.start();
			
			GameState gameState = server.getCurrentState(); 	// waiting...
			
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

}
