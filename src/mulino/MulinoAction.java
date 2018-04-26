package mulino;

import game.general.GameAction;
import game.general.GameState;

public abstract class MulinoAction extends GameAction {

	protected int[] removeOpponentChecker;
	
	protected MulinoAction() {
		this.removeOpponentChecker = null;
	}
	
	protected MulinoAction(int[] enemyPosition) {
		this.removeOpponentChecker[0] = enemyPosition[0];
		this.removeOpponentChecker[1] = enemyPosition[1];
	}
	
	public int[] getRemoveOpponentChecker() {
		return this.removeOpponentChecker;
	}
	 
	@Override
	public abstract GameState perform(GameState currentState);
}
