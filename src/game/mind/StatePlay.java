package game.mind;

import game.general.GameAction;
import game.general.Timer;
import mulino.MulinoSettings;

class StatePlay extends State {

	// start timer thread
	// start thinking thread (based on current state)
	// when timeout:
	//		stop thinking thread
	//		get selected action
	// 		play action (game server)
	// -> mind stateChanged
	@Override
	public void handle(Mind mind) {
		WaitingQueue queue = new WaitingQueue();
		
		Timer timer = new Timer(queue, MulinoSettings.TIMER);
		ThinkingThread thinkingThr = new ThinkingThread(queue, mind.getStrategyFactory(), mind.getCurrentState());
		
		timer.start();
		thinkingThr.start();
		
		try {
			queue.await();
			
			timer.interrupt();
			thinkingThr.interrupt(); 	// javadoc: "Interrupting a thread that is not alive need not have any effect."
			
			GameAction action = thinkingThr.getSelectedAction();
			mind.getGameServer().playAction(action);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public State transition(Mind mind) {
		return mind.getCurrentState().isOver() ? State.stateEnd : State.stateWait;
	}

}
