package game.general;

public abstract class GameServer {

	public abstract void playAction(GameAction action);
	
	public abstract GameState getCurrentState();
	
}
