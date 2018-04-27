package game.mind;

abstract class State {
	
	public static StateInit stateInit = new StateInit();
	public static StatePlay statePlay = new StatePlay();
	public static StateWait stateWait = new StateWait();
	public static StateEnd stateEnd = new StateEnd();
	
	public abstract void handle(Mind mind);
	
	public abstract State transition(Mind mind);
	
}
