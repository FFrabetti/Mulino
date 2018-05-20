package mulino;

import game.general.GameState;
import game.mind.Strategy;
import game.mind.StrategyFactory;
import mulino.ia.FunzioneEuristica;
import mulino.strategy.MulinoIterativeAlphaBeta;

public class AlphaBetaStrategyFactory implements StrategyFactory {

	@Override
	public Strategy selectStrategy(GameState state) {
		MulinoGame game = new MulinoGame(100, -100); // 2, -1
		FunzioneEuristica heuristic = new FunzioneEuristica();
		MulinoIterativeAlphaBeta search = new MulinoIterativeAlphaBeta(game, heuristic, 4);

		return search;
	}

}
