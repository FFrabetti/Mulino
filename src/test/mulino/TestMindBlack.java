package test.mulino;

import java.io.IOException;

import game.interaction.Diplomat;
import game.mind.Mind;
import game.mind.StrategyFactory;
import it.unibo.ai.didattica.mulino.domain.State;
import mulino.MulinoMind;
import mulino.MulinoStrategyFactory;
import mulino.MulinoTCPServer;

public class TestMindBlack {
	
	public static void main(String[] args) {
		Diplomat d = null;

		try {
			d = new Diplomat(5801);

		} catch (IOException e) {
			e.printStackTrace();
		}
		StrategyFactory strategyFactory = new MulinoStrategyFactory();
		MulinoTCPServer server = new MulinoTCPServer(State.Checker.BLACK,d);
		Mind mind= new MulinoMind(server, strategyFactory);
		
		mind.start();
	}
}
