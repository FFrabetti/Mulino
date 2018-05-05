package mulino;

import game.general.GameState;

public class Phase1MulinoAction extends MulinoAction {
	
	public Phase1MulinoAction(Position to, Position removeOpponent) {
		super(to, removeOpponent);
	}

	public Phase1MulinoAction(Position to) {
		this(to, null);
	}
	
	public Phase1MulinoAction() {
		// may be useful, if you want to manually set all fields
	}	
	
	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		newState.playChecker(getTo());

		// parte comune a tutte le fasi
		return finishToPerform(newState);
	}

}
