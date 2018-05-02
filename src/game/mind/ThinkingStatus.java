package game.mind;

import game.general.GameAction;
import game.general.GameState;

public class ThinkingStatus {

	private GameAction selectedAction; //it's the best action calculated so far
	private GameState gameState;
	private Object ramifications; 	//TODO sono le ramificazioni calcolate da ChooseAction di Strategy
									// per ora le ho messe come un generico Object

	public ThinkingStatus(GameAction selectedAction, GameState gameState, Object ramifications) {
		this.selectedAction = selectedAction;
		this.gameState = gameState;
		this.ramifications = ramifications;
	}
	
	//getters and setters
	
	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Object getRamifications() {
		return ramifications;
	}

	public void setRamifications(Object ramifications) {
		this.ramifications = ramifications;
	}

	public GameAction getSelectedAction() {
		return selectedAction;
	}
	
	public void setSelectedAction(GameAction selectedAction) {
		this.selectedAction = selectedAction;
	}
	
}
