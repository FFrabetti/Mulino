package game.general;

public interface GameServer {
	
	public void playAction(GameAction action);
	
	public GameState getCurrentState();

}
