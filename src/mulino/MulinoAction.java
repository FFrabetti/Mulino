package mulino;

import java.util.Optional;

import aima.core.agent.Action;
import game.general.GameAction;
import game.general.GameState;
import mulino.Board.Position;

public abstract class MulinoAction implements GameAction{

	private Position to;
	private Optional<Position> removeOpponent;
	
	public MulinoAction(Position to, Position removeOpponent) {
		this.to = to;
		setRemoveOpponent(removeOpponent);
	}

	public MulinoAction(Position to) {
		this(to, null);
	}
	
	public Position getTo() {
		return to;
	}

	public Optional<Position> getRemoveOpponent() {
		return removeOpponent;
	}

	private void setRemoveOpponent(Position removeOpponent) {
		this.removeOpponent = Optional.ofNullable(removeOpponent);
	}
	
	// parte comune a tutte le fasi:
	protected GameState finishToPerform(MulinoState newState) {
		// se ho fatto un mulino posso rimuovere una pedina nemica
		if (removeOpponent.isPresent())
			newState.removeChecker(removeOpponent.get());
		
		newState.switchDutyPlayer(); // poi toccher� all'avversario

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
	
	@Override
	public boolean isNoOp() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
