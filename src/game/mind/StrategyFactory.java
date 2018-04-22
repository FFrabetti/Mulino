package game.mind;

import game.general.GameState;

public interface StrategyFactory {

	public Strategy selectStrategy(GameState state);
	
}
