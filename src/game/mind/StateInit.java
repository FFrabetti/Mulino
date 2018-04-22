package game.mind;

class StateInit extends State {

	@Override
	public void handle(Mind mind) {
		// set the initial state of the game (received from the server)
		mind.setCurrentState(mind.getGameServer().getCurrentState()); // wait...
	}

	@Override
	public State transition(Mind mind) {
		return mind.getGamePosition()==1 ? State.statePlay : State.stateWait;
	}

}
