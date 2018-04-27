package game.general;

import java.util.List;

public abstract class GameState {
	
	public abstract List<GameAction> legitActions();

	public abstract boolean isOver();

	public abstract boolean isWinning();
	
}
