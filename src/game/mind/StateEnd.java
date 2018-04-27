package game.mind;

class StateEnd extends State {

	@Override
	public void handle(Mind mind) {
//		System.out.println(mind.getCurrentState().isWinning() ? "YOU WON!" : "YOU LOST :(");
	}

	@Override
	public State transition(Mind mind) { // never called
		return null;
	}

}
