package mulino;

import java.util.HashMap;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameState;
import it.unibo.ai.didattica.mulino.actions.Action;
import it.unibo.ai.didattica.mulino.actions.Phase1Action;
import it.unibo.ai.didattica.mulino.actions.Phase2Action;
import it.unibo.ai.didattica.mulino.actions.PhaseFinalAction;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.*;

public class MulinoFactory extends GameFactory<State,Action> {

	@Override
	public GameState fromState(State state) {
		MulinoState ms = new MulinoState();

		// ricopio le variabili
		ms.setCurrentPhase(state.getCurrentPhase());
		ms.setWhiteCheckers(state.getWhiteCheckers());
		ms.setWhiteCheckersOnBoard(state.getWhiteCheckersOnBoard());
		ms.setBlackCheckers(state.getBlackCheckers());
		ms.setBlackCheckersOnBoard(state.getBlackCheckersOnBoard());

		// converto il tabellone
		HashMap<String, Checker> b = state.getBoard();
		HashMap<int[], Checker> board = new HashMap<>();
		board.put(new int[] { -3, -3 }, b.get("a1"));
		board.put(new int[] { -3, 0 }, b.get("a4"));
		board.put(new int[] { -3, 3 }, b.get("a7"));
		board.put(new int[] { -2, -2 }, b.get("b2"));
		board.put(new int[] { -2, 0 }, b.get("b4"));
		board.put(new int[] { -2, 2 }, b.get("b6"));
		board.put(new int[] { -1, -1 }, b.get("c3"));
		board.put(new int[] { -1, 0 }, b.get("c4"));
		board.put(new int[] { -1, 1 }, b.get("c5"));
		board.put(new int[] { 0, -3 }, b.get("d1"));
		board.put(new int[] { 0, -2 }, b.get("d2"));
		board.put(new int[] { 0, -1 }, b.get("d3"));
		board.put(new int[] { 0, 1 }, b.get("d5"));
		board.put(new int[] { 0, 2 }, b.get("d6"));
		board.put(new int[] { 0, 3 }, b.get("d7"));
		board.put(new int[] { 1, -1 }, b.get("e3"));
		board.put(new int[] { 1, 0 }, b.get("e4"));
		board.put(new int[] { 1, 1 }, b.get("e5"));
		board.put(new int[] { 2, -2 }, b.get("f2"));
		board.put(new int[] { 2, 0 }, b.get("f4"));
		board.put(new int[] { 2, 2 }, b.get("f6"));
		board.put(new int[] { 3, -3 }, b.get("g1"));
		board.put(new int[] { 3, 0 }, b.get("g4"));
		board.put(new int[] { 3, 3 }, b.get("g7"));
		ms.setBoard(board);

		return ms;
	}

	@Override
	public Action toAction(GameAction action) {
		Action act = null;
		
		if (action instanceof Phase1MulinoAction)
			act = toPhase1Action((Phase1MulinoAction)action);
		else if (action instanceof Phase23MulinoAction) {
			Phase23MulinoAction a = (Phase23MulinoAction)action;
			act = a.getPhase()==Phase.SECOND ? toPhase2Action(a) : toPhaseFinalAction(a);
		}
		
		return act;
	}

	private Phase1Action toPhase1Action(Phase1MulinoAction action) {
		Phase1Action result = new Phase1Action();
		
		// converto la posizione "to"
		result.setPutPosition(parseCoordinates(action.getTo()));
		
		// converto la pedina da togliere (se presente)
		if(action.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parseCoordinates(action.getRemoveOpponent().get()));
		
		return result;
	}

	private Phase2Action toPhase2Action(Phase23MulinoAction action) {
		Phase2Action result = new Phase2Action();
		
		// converto la posizione "from"
		result.setFrom(parseCoordinates(action.getFrom()));
		
		// converto la posizione "to"
		result.setTo(parseCoordinates(action.getTo()));
		
		// converto la pedina da togliere (se presente)
		if(action.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parseCoordinates(action.getRemoveOpponent().get()));
		
		return result;
	}

	// metodo uguale al precedente
	// se le classi di destinazione fossero state in gerarchia ne sarebbe bastato uno...
	private PhaseFinalAction toPhaseFinalAction(Phase23MulinoAction action) {
		PhaseFinalAction result = new PhaseFinalAction();
		
		// converto la posizione "from"
		result.setFrom(parseCoordinates(action.getFrom()));
		
		// converto la posizione "to"
		result.setTo(parseCoordinates(action.getTo()));
		
		// converto la pedina da togliere (se presente)
		if(action.getRemoveOpponent().isPresent())
			result.setRemoveOpponentChecker(parseCoordinates(action.getRemoveOpponent().get()));
		
		return result;
	}
	
	private String parseCoordinates(int[] xy) {
		char c = (char) (xy[0]+3+'a');
		int n = xy[1]+4;
		
		return c+""+n;
	}

}
