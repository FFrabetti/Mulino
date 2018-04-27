package mulino;

import java.util.HashMap;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameState;
import mulino.State.Checker;

public class MulinoFactory extends GameFactory {

	@Override
	public GameState fromState(Object state) {
		State s = (State) state;
		MulinoState ms = new MulinoState();

		// ricopio le variabili
		ms.setCurrentPhase(s.getCurrentPhase());
		ms.setWhiteCheckers(s.getWhiteCheckers());
		ms.setWhiteCheckersOnBoard(s.getWhiteCheckersOnBoard());
		ms.setBlackCheckers(s.getBlackCheckers());
		ms.setBlackCheckersOnBoard(s.getBlackCheckersOnBoard());

		// converto il tabellone
		HashMap<String, Checker> b = s.getBoard();
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
	public Object toAction(GameAction action) {
		Action act = null;
		if (action instanceof Phase1MulinoAction) {
			Phase1MulinoAction ma = (Phase1MulinoAction) action;
			Phase1Action a = new Phase1Action();
			// converto la posizione "xy" in una "lettera_numero"
			a.setPutPosition(parseCoordinatesToString(ma.getPutPosition()));
			// converto la pedina da togliere (se presente)
			if (ma.getRemoveOpponentChecker() == null)
				a.setRemoveOpponentChecker(null);
			else {
				a.setRemoveOpponentChecker(parseCoordinatesToString(ma.getRemoveOpponentChecker()));
			}
			act = a;
		} else if (action instanceof Phase2MulinoAction) {
			Phase2MulinoAction ma = (Phase2MulinoAction) action;
			Phase2Action a = new Phase2Action();
			// converto la posizione "from" in una "lettera_numero"
			a.setFrom(parseCoordinatesToString(ma.getFrom()));
			// converto la posizione "to" in una "lettera_numero"
			a.setTo(parseCoordinatesToString(ma.getTo()));
			// converto la pedina da togliere (se presente)
			if (ma.getRemoveOpponentChecker() == null)
				a.setRemoveOpponentChecker(null);
			else {
				a.setRemoveOpponentChecker(parseCoordinatesToString(ma.getRemoveOpponentChecker()));
			}
			act = a;
		} else if (action instanceof Phase3MulinoAction) {
			Phase3MulinoAction ma = (Phase3MulinoAction) action;
			PhaseFinalAction a = new PhaseFinalAction();
			// converto la posizione "from" in una "lettera_numero"
			a.setFrom(parseCoordinatesToString(ma.getFrom()));
			// converto la posizione "to" in una "lettera_numero"
			a.setTo(parseCoordinatesToString(ma.getTo()));
			// converto la pedina da togliere (se presente)
			if (ma.getRemoveOpponentChecker() == null)
				a.setRemoveOpponentChecker(null);
			else {
				a.setRemoveOpponentChecker(parseCoordinatesToString(ma.getRemoveOpponentChecker()));
			}
			act = a;
		}
		return act;
	}

	private String parseCoordinatesToString(int[] xy) {
		char c = (char) (xy[0]+3+'a');
		int n = xy[1]+4;
		
		return c+""+n;
	}

}
