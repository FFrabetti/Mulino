package game.mind;

public class Mind {

	private State state;
	
	private void stateChanged() {
		state = State.selectState(this);
	}
	
}