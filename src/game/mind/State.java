package game.mind;

abstract class State {
	private static StateInit stateInit = new StateInit();
	private static StatePlay statePlay = new StatePlay();
	private static StateWait stateWait = new StateWait();
	private static StateEnd stateEnd = new StateEnd();

	
	public static State selectState(Mind mind) {
//		if(...) return statoX
//		else if(...) return statoY
//		etc...
		return stateInit;
	}
	
	public abstract void Handle();
	
}