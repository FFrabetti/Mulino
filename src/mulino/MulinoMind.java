package mulino;

import game.general.GameServer;
import game.mind.Mind;
import game.mind.StrategyFactory;

public class MulinoMind extends Mind {
	
	private MulinoTCPServer mServer;
	
	public MulinoMind(GameServer server, StrategyFactory strategyFactory) {
		super(server, strategyFactory);
		
		mServer = (MulinoTCPServer)server;
	}

	@Override
	public int getGamePosition() {
		if(mServer.getDutyPlayer()==State.Checker.WHITE)
			return 1;
		else if(mServer.getDutyPlayer()==State.Checker.BLACK)
			return 2;
		else return -1;
	}
	
}
