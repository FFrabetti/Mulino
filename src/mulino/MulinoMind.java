package mulino;

import game.mind.Mind;
import game.mind.StrategyFactory;
import it.unibo.ai.didattica.mulino.domain.State;

public class MulinoMind extends Mind {
	
	private State.Checker checker;
	
	// interface MulinoServer per maggiore flessibilità?
	public MulinoMind(MulinoTCPClient client, StrategyFactory strategyFactory) {
		super(client, strategyFactory);
		
		checker = client.getChecker();
	}

	@Override
	public int getGamePosition() {
		return checker==State.Checker.WHITE ? 1 : 2;
	}
	
}
