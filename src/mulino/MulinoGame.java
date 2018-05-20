package mulino;

import java.util.List;

import aima.core.search.adversarial.Game;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;

public class MulinoGame implements Game<MulinoState, GameAction, Checker> {

	private double victoryValue;
	private double lostValue;
	
	private static final Checker[] players = new Checker[] { Checker.WHITE , Checker.BLACK };

	public MulinoGame(double victoryValue, double lostValue) {
		this.victoryValue = victoryValue;
		this.lostValue = lostValue;
	}
		
	public double getVictoryValue() {
		return victoryValue;
	}

	public double getLostValue() {
		return lostValue;
	}

	@Override
	public MulinoState getInitialState() { // never used
		return null;
	}

	@Override
	public Checker[] getPlayers() {
		return players;
	}

	@Override
	public Checker getPlayer(MulinoState state) {
		return state.getDutyPlayer();
	}

	@Override
	public List<GameAction> getActions(MulinoState state) {
		return state.legitActions();
	}

	@Override
	public MulinoState getResult(MulinoState state, GameAction action) {
		return (MulinoState) action.perform(state);
	}

	@Override
	public boolean isTerminal(MulinoState state) {
		return state.isOver();
	}

	@Override
	public double getUtility(MulinoState state, Checker player) {
		if(!state.hasLegitActions())
			return state.getDutyPlayer()==player ? lostValue : victoryValue;
		else
			return state.getBoard().checkers(player)<3 ? lostValue : victoryValue;
	}

}
