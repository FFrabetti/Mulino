package mulino;

import game.general.GameServer;
import game.mind.Mind;
import game.mind.StrategyFactory;

public class MulinoMind extends Mind {
	
	private mulino.State.Checker checker;
	
	public MulinoMind(GameServer server, StrategyFactory strategyFactory) {
		super(server, strategyFactory);
	}

	@Override
	public int getGamePosition() {
		if(checker.equalsChecker('W'))
			return 1;
		else if(checker.equalsChecker('B'))
			return 2;
		else return -1;
	}
	
	
}
