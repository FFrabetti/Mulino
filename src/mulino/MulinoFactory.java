package mulino;

import java.util.HashMap;
import java.util.Map;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameState;
import it.unibo.ai.didattica.mulino.actions.*;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.*;
import mulino.Board.Position;

public class MulinoFactory extends GameFactory<State,Action> {

	@Override
	public GameState fromState(State state) {
		// converto il tabellone
		Map<String, Checker> b = state.getBoard();
		Map<Position, Checker> map = new HashMap<>();
		Board board = new Board(map, state.getWhiteCheckersOnBoard(), state.getBlackCheckersOnBoard());
		
		putIfNotEmpty(map, b, -3, -3, "a1", board);
		putIfNotEmpty(map, b, -3, 0, "a4", board);
		putIfNotEmpty(map, b, -3, 3, "a7", board);
		putIfNotEmpty(map, b, -2, -2, "b2", board);
		putIfNotEmpty(map, b, -2, 0, "b4", board);
		putIfNotEmpty(map, b, -2, 2, "b6", board);
		putIfNotEmpty(map, b, -1, -1, "c3", board);
		putIfNotEmpty(map, b, -1, 0, "c4", board);
		putIfNotEmpty(map, b, -1, 1, "c5", board);
		putIfNotEmpty(map, b, 0, -3, "d1", board);
		putIfNotEmpty(map, b, 0, -2, "d2", board);
		putIfNotEmpty(map, b, 0, -1, "d3", board);
		putIfNotEmpty(map, b, 0, 1, "d5", board);
		putIfNotEmpty(map, b, 0, 2, "d6", board);
		putIfNotEmpty(map, b, 0, 3, "d7", board);
		putIfNotEmpty(map, b, 1, -1, "e3", board);
		putIfNotEmpty(map, b, 1, 0, "e4", board);
		putIfNotEmpty(map, b, 1, 1, "e5", board);
		putIfNotEmpty(map, b, 2, -2, "f2", board);
		putIfNotEmpty(map, b, 2, 0, "f4", board);
		putIfNotEmpty(map, b, 2, 2, "f6", board);
		putIfNotEmpty(map, b, 3, -3, "g1", board);
		putIfNotEmpty(map, b, 3, 0, "g4", board);
		putIfNotEmpty(map, b, 3, 3, "g7", board);
		

		MulinoState ms = new MulinoState(board, state.getWhiteCheckers(), state.getBlackCheckers());

		return ms;
	}

	private void putIfNotEmpty(Map<Position, Checker> map, Map<String, Checker> b, int i, int j, String k, Board board) {
		Checker c = b.get(k);
		if(c!=Checker.EMPTY)
			map.put(board.getPos(i, j), c);
	}

	@Override
	public Action toAction(GameAction action) {
		Action result = null;
		
		if (action instanceof Phase1MulinoAction)
			result = toPhase1Action((Phase1MulinoAction)action);
		else if (action instanceof Phase23MulinoAction) {
			Phase23MulinoAction a = (Phase23MulinoAction)action;
			result = a.getPhase()==Phase.SECOND ? toPhase2Action(a) : toPhaseFinalAction(a);
		}
		
		return result;
	}

	private Phase1Action toPhase1Action(Phase1MulinoAction a) {
		Phase1Action result = new Phase1Action();
		
		result.setPutPosition(parsePosition(a.getTo()));
		if(a.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parsePosition(a.getRemoveOpponent().get()));
		
		return result;
	}

	private Phase2Action toPhase2Action(Phase23MulinoAction a) {
		Phase2Action result = new Phase2Action();
		
		result.setFrom(parsePosition(a.getFrom()));
		result.setTo(parsePosition(a.getTo()));
		if(a.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parsePosition(a.getRemoveOpponent().get()));
		
		return result;
	}

	// metodo uguale al precedente
	// se le classi di destinazione fossero state in gerarchia ne sarebbe bastato uno...
	private PhaseFinalAction toPhaseFinalAction(Phase23MulinoAction a) {
		PhaseFinalAction result = new PhaseFinalAction();
		
		result.setFrom(parsePosition(a.getFrom()));
		result.setTo(parsePosition(a.getTo()));
		if(a.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parsePosition(a.getRemoveOpponent().get()));
		
		return result;
	}
	
	private String parsePosition(Position p) {
		char c = (char) (p.getX()+3+'a');
		int n = p.getY()+4;
		
		return c+""+n;
	}

}
