import game.interaction.Diplomat;
import game.mind.Mind;
import game.mind.StrategyFactory;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.AlphaBetaStrategyFactory;
import mulino.EndlessAlphaBetaStrategyFactory;
import mulino.MulinoMind;
import mulino.MulinoSettings;
import mulino.MulinoTCPClient;

public class EndlessMulinoMain {
	
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.out.println("Usage: expected 1 arg White|Black");
			System.exit(-1);
		}
		
		try {
			Checker player = args[0].equals("White") ? Checker.WHITE : Checker.BLACK;
			
			Diplomat d = new Diplomat(player==Checker.WHITE ? MulinoSettings.WHITE_PORT : MulinoSettings.BLACK_PORT);
			MulinoTCPClient server = new MulinoTCPClient(player, d);
			
			StrategyFactory strategyFactory = new EndlessAlphaBetaStrategyFactory();
			
			Mind mind = new MulinoMind(server, strategyFactory);			
			mind.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
