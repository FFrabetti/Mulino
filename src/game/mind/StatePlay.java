package game.mind;

import java.io.IOException;

import game.general.GameAction;
import game.general.Timer;

class StatePlay extends State {

	// start timer thread
	// start thinking thread (based on current state)
	// when timeout:
	//		stop thinking thread
	//		get selected action
	// 		play action (game server)
	// -> mind stateChanged
	@Override
	public void handle(Mind mind) throws IOException {
		//intero solo per far compilare, visto che timer vuole un numero di secondi
		int DAVERIFICARE = 10;
		Timer timer = new Timer(DAVERIFICARE); 	// the nr of seconds should be defined somewhere...
		ThinkingThread thinkingThr = new ThinkingThread(mind.getStrategyFactory(), mind.getCurrentState());
		timer.start();
		thinkingThr.start();
		
		try {
			timer.join(); 	// are we always taking the entire available time?
			thinkingThr.interrupt(); 	// javadoc: "Interrupting a thread that is not alive need not have any effect."
			GameAction action = thinkingThr.getSelectedAction();
			mind.getGameServer().playAction(action);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public State transition(Mind mind) {
		return mind.getCurrentState().isOver() ? State.stateEnd : State.stateWait;
	}

}
