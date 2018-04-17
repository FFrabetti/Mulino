package game.general;

public abstract class GameAction {
 
	public abstract GameState perform (GameState currentState);
}
