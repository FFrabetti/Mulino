package mulino;

import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

public class Phase23MulinoAction extends MulinoAction {

	private Position from;
	private Phase phase; // for conversions to Phase2Action and PhaseFinalAction
	
	public Phase23MulinoAction(Position from, Position to, Position removeOpponent, Phase phase) {
		super(to, removeOpponent);
		this.from = from;
		this.phase = phase;
	}

	public Phase23MulinoAction(Position from, Position to, Phase phase) {
		this(from, to, null, phase);
	}
	
	public Phase23MulinoAction() {
		// may be useful, if you want to manually set all fields
	}
	
	public Position getFrom() {
		return from;
	}

	public void setFrom(Position from) {
		this.from = from;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		// sposto la mia pedina
		newState.moveChecker(from, getTo());

		// parte comune a tutte le fasi
		return finishToPerform(newState);
	}

	@Override
	public String toString() {
		return from + " -> " + super.toString();
	}
	
}
