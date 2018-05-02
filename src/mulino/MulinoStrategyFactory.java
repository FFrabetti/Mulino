package mulino;

import game.general.GameState;
import game.mind.Strategy;
import game.mind.StrategyFactory;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import mulino.strategy.*;

public class MulinoStrategyFactory implements StrategyFactory{

	@Override
	public Strategy selectStrategy(GameState state) {
		MulinoState mState = (MulinoState) state;
		Phase phase =mState.getCurrentPhase();
		if(phase==Phase.FIRST)
			return new MulinoPhase1Strategy();
		else if(phase==Phase.SECOND)
			return new MulinoPhase2Strategy();
		else 
			return new MulinoPhase3Strategy();
	}

}
