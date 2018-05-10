package mulino.strategy;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.adversarial.AdversarialSearch;
import game.general.GameAction;
import game.general.GameState;
import game.mind.Strategy;
import game.mind.ThinkingStatus;
import mulino.MulinoAction;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.ia.MulinoIterativeDeepeningAlphaBetaSearch;

public class MulinoPhase3Strategy extends Strategy {

	@Override
	public void chooseAction(GameState state) {
		MulinoGame game = new MulinoGame();
		//AdversarialSearch<MulinoState, MulinoAction> search = AlphaBetaSearch.createFor(game);
		AdversarialSearch<MulinoState,MulinoAction> search = MulinoIterativeDeepeningAlphaBetaSearch.createFor(game,-1,1,40);
		((IterativeDeepeningAlphaBetaSearch<?, ?, ?>) search).setLogEnabled(true);
		GameAction action = search.makeDecision((MulinoState)state);
		setThinkingStatus(new ThinkingStatus(action, state, null));
	}

}
