package game.mind;

import game.general.GameState;

class StateWait extends State {

	// start thinking thread (based on predictions)
	// read state from server (waiting for opponent move...)
	// when state read:
	//		stop thinking thread (save "thinking status")
	//		current state = state read
	//	-> mind stateChanged
	@Override
	public void handle(Mind mind) {
		ThinkingThread thinkingThr = new ThinkingThread(mind.getStrategyFactory(), mind.getCurrentState());
		thinkingThr.start();
		
		GameState gameState = mind.getGameServer().getCurrentState(); 	// waiting...
		thinkingThr.interrupt();
		// what to do with all the work done so far by thinkingThr?
		mind.setCurrentState(gameState);
	}

	@Override
	public State transition(Mind mind) {
		return mind.getCurrentState().isOver() ? State.stateEnd : State.statePlay;
	}

}
