package game.general;

import aima.core.agent.Action;

public interface GameAction extends Action {
 
	public GameState perform (GameState currentState);
	
}
