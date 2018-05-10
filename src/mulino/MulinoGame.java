package mulino;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.Game;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;


public class MulinoGame implements Game<MulinoState, MulinoAction, Checker> {

	private MulinoState initialState = new MulinoState();

	@Override
	public MulinoState getInitialState() {
		return initialState;
	}

	@Override
	public Checker[] getPlayers() {
		return new Checker[] { Checker.WHITE , Checker.BLACK };
	}

	@Override
	public Checker getPlayer(MulinoState state) {
		return state.getDutyPlayer();
	}

	//il metodo vuole delle MulinoAction, ma il nostro torna delle GameAction
	// faccio conversione per non fare casino per ora
	@Override
	public List<MulinoAction> getActions(MulinoState state) {
		List<GameAction> actions = state.legitActions();
		List<MulinoAction> mActions = new ArrayList<MulinoAction>();
		for(GameAction action : actions) {
			mActions.add((MulinoAction)action);
		}
		return mActions;
	}

	@Override
	public MulinoState getResult(MulinoState state, MulinoAction action) {
		MulinoState result = (MulinoState) action.perform(state);
		//result.mark(action);
		return result;
	}

	@Override
	public boolean isTerminal(MulinoState state) {
//		if(state.getUtility()!=-2) {
//			System.out.println("---------------------------------------\nDEBUG:STATO TERMINALE:");
//			System.out.println(state.toString());
//			return true;
//		}
//		else return false;
		return state.getUtility() != -2;
	}

	@Override
	public double getUtility(MulinoState state, Checker player) {
		double result = state.getUtility();
		if (result != -2) {
			if (player==Checker.BLACK)
				result = - result;
		} else {
			throw new IllegalArgumentException("State is not terminal.");
		}
		return result;
	}
}
