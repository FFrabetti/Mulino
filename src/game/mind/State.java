package game.mind;

import java.io.IOException;

abstract class State {
	
	public static StateInit stateInit = new StateInit();
	public static StatePlay statePlay = new StatePlay();
	public static StateWait stateWait = new StateWait();
	public static StateEnd stateEnd = new StateEnd();

//	public static State selectState(Mind mind) {
//		if(...) return statoX
//		else if(...) return statoY
//		etc...
//		return stateInit;
//	}
	
	public abstract void handle(Mind mind) throws ClassNotFoundException, IOException;
	
	public abstract State transition(Mind mind);
	
}
