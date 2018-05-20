package test.mulino;

import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.Board;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.Position;
import mulino.ia.FunzioneEuristica;
import mulino.strategy.MulinoIterativeAlphaBeta;

public class TestIterative {

	public static void main(String[] args) {
				
		Board b = new Board();
		
		b.put(new Position(3,3),Checker.WHITE);
		b.put(new Position(3,-3),Checker.WHITE);
		b.put(new Position(2,0),Checker.WHITE); // 1, 0 -> 2 mosse
		b.put(new Position(0,-2),Checker.WHITE);
		b.put(new Position(-2,0),Checker.WHITE);
		
		b.put(new Position(-3,-3),Checker.BLACK);
//		b.put(new Position(-2,2),Checker.BLACK);
		b.put(new Position(0,1),Checker.BLACK);
		b.put(new Position(0,-3), Checker.BLACK);

		MulinoState state = new MulinoState(b, 0, 0);
		MulinoGame game = new MulinoGame(100, -100); // 2, -1
		FunzioneEuristica heuristic = new FunzioneEuristica();
		MulinoIterativeAlphaBeta search = new MulinoIterativeAlphaBeta(game, heuristic);

		System.out.println(state);
		
		GameAction action = search.makeDecision(state);
		System.out.println(action);
		System.out.println(action.perform(state));
	}

}
