package game.general;

public interface GameClient {
	
	public void playAction(GameAction action);
	
	public GameState getCurrentState();
	
	public GameState getInitState();//da fare meglio

}
