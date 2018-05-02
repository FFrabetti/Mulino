package mulino;

import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State;

public class Phase23MulinoAction extends MulinoAction {

	private int[] from;
	
	// just for conversions to Phase2Action and PhaseFinalAction
	private State.Phase phase;
	
	public Phase23MulinoAction(int[] from, int[] to, int[] removeOpponent) {
		super(to, removeOpponent);
		
		this.from = from;
	}

	public Phase23MulinoAction(int[] from, int[] to) {
		this(from, to, null);
	}
	
	public Phase23MulinoAction() {
		// may be useful, if you want to manually set all fields
	}
	
	public int[] getFrom() {
		return from;
	}

	public void setFrom(int[] from) {
		this.from = from;
	}

	public State.Phase getPhase() {
		return phase;
	}

	public void setPhase(State.Phase phase) {
		this.phase = phase;
	}

	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		State.Checker player = oldState.getDutyPlayer();
		
		// sposto la mia pedina
		newState.moveChecker(from, getTo());

		// parte comune a tutte le fasi
		return finishToPerform(newState, player);
	}

}
