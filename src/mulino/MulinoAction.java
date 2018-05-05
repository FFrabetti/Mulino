package mulino;

import java.util.Optional;

import game.general.GameAction;
import game.general.GameState;

public abstract class MulinoAction implements GameAction {

	private Position to;
	private Optional<Position> removeOpponent;
	
	public MulinoAction(Position to, Position removeOpponent) {
		this.to = to;
		setRemoveOpponent(removeOpponent);
	}

	public MulinoAction(Position to) {
		this(to, null);
	}
	
	public MulinoAction() {
		// may be useful, if you want to manually set all fields
	}
	
	public Position getTo() {
		return to;
	}

	public void setTo(Position to) {
		this.to = to;
	}

	public Optional<Position> getRemoveOpponent() {
		return removeOpponent;
	}

	public void setRemoveOpponent(Position removeOpponent) {
		this.removeOpponent = Optional.ofNullable(removeOpponent);
	}
	
	// parte comune a tutte le fasi:
	protected GameState finishToPerform(MulinoState newState) {
		// se posso (ho fatto un mulino) rimuovo una pedina nemica
		if (removeOpponent.isPresent())
			newState.removeChecker(removeOpponent.get());

		// adesso toccherà muovere all'avversario
		newState.switchDutyPlayer();

		return newState;
	}
	
	@Override
	public abstract GameState perform(GameState currentState);

	@Override
	public String toString() {
		String result = to.toString();
		
		if(removeOpponent.isPresent())
			result += " remove " + removeOpponent.get();
		
		return result;
	}
	
}
