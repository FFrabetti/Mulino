package mulino;

import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class Phase1MulinoAction extends MulinoAction {
	
	public Phase1MulinoAction(int[] to, int[] removeOpponent) {
		super(to, removeOpponent);
	}

	public Phase1MulinoAction(int[] to) {
		this(to, null);
	}
	
	public Phase1MulinoAction() {
		// may be useful, if you want to manually set all fields
	}	
	
	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		Checker player = oldState.getDutyPlayer();
		
		// aggiungo la pedina
		newState.newCheckerPlayed(getTo(), player);

		// parte comune a tutte le fasi
		return finishToPerform(newState, player);
	}

}
