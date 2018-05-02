package mulino;

import java.util.Optional;

import game.general.GameAction;
import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public abstract class MulinoAction extends GameAction {

	private int[] to;
	private Optional<int[]> removeOpponent;
	
	public MulinoAction(int[] to, int[] removeOpponent) {
		this.to = to;
		setRemoveOpponent(removeOpponent);
	}

	public MulinoAction(int[] to) {
		this(to, null);
	}
	
	public MulinoAction() {
		// may be useful, if you want to manually set all fields
	}
	
	public int[] getTo() {
		return to;
	}

	public void setTo(int[] to) {
		this.to = to;
	}

	public Optional<int[]> getRemoveOpponent() {
		return removeOpponent;
	}

	public void setRemoveOpponent(int[] removeOpponent) {
		this.removeOpponent = Optional.ofNullable(removeOpponent);
	}

	private Checker opponent(Checker player) {
		return player==Checker.WHITE ? Checker.BLACK : Checker.WHITE;
	}
	
	// parte comune a tutte le fasi:
	protected GameState finishToPerform(MulinoState newState, Checker player) {
		Checker opponent = opponent(player);
		
		// se posso (ho fatto un mulino) rimuovo una pedina nemica
		if (removeOpponent.isPresent())
			newState.removeChecker(removeOpponent.get(), opponent);

		// adesso toccherà muovere all'avversario
		newState.setDutyPlayer(opponent);

		// controllo se devo cambiare fase di gioco
		newState.updatePhase();
		
		return newState;
	}
	
	@Override
	public abstract GameState perform(GameState currentState);
	
}
