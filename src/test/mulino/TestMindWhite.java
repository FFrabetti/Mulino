package test.mulino;

import java.io.IOException;

import game.interaction.Diplomat;
import game.mind.Mind;
import game.mind.StrategyFactory;
import it.unibo.ai.didattica.mulino.domain.State;
import mulino.MulinoMind;
import mulino.MulinoSettings;
import mulino.MulinoStrategyFactory;
import mulino.MulinoTCPServer;

public class TestMindWhite {
	
	public static void main(String[] args) {
		try {
			Diplomat d = new Diplomat(MulinoSettings.WHITE_PORT);
			StrategyFactory strategyFactory = new MulinoStrategyFactory();
			MulinoTCPServer server = new MulinoTCPServer(State.Checker.WHITE, d);
			Mind mind = new MulinoMind(server, strategyFactory);
			
			mind.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}