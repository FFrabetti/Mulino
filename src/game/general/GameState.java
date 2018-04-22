package game.general;

import java.util.Collection;

public abstract class GameState {
	
	public abstract Collection<GameAction> legitActions();

	public abstract boolean isOver();

	public abstract boolean isWinning();
	
}
