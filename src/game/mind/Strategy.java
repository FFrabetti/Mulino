package game.mind;

import game.general.GameAction;
import game.general.GameState;

public interface Strategy {

	public GameAction chooseAction(GameState state);
	
}
