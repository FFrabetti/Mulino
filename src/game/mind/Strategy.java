package game.mind;

import game.general.GameAction;
import game.general.GameState;

public abstract class Strategy {

	private GameAction selectedAction;
	
	public GameAction getSelectedAction() {
		return selectedAction;
	}

	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
	}

	public abstract void chooseAction(GameState state);
	
}
