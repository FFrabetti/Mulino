package mulino;

import game.mind.Mind;
import game.mind.StrategyFactory;
import mulino.shared.State;

public class MulinoMind extends Mind {
	
	private State.Checker checker;
	
	// interface MulinoServer per maggiore flessibilità?
	public MulinoMind(MulinoTCPServer server, StrategyFactory strategyFactory) {
		super(server, strategyFactory);
		
		checker = server.getChecker();
	}

	@Override
	public int getGamePosition() {
		return checker==State.Checker.WHITE ? 1 : 2;
	}
	
}
