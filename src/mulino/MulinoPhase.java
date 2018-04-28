package mulino;

import java.util.List;

import game.general.GameAction;
import mulino.shared.State.Phase;

public abstract class MulinoPhase {
	protected Phase phase;
	
	public Phase getPhaseName() {
		return this.phase;
	}

	public static MulinoPhase getPhase(int whiteCheckers, int blackCheckers, int whiteCheckersOnBoard,
			int blackCheckersonBoard) {
		return MulinoPhaseFactory.getInstance().of(whiteCheckers, blackCheckers, whiteCheckersOnBoard, blackCheckersonBoard);
	}
	
	public static MulinoPhase getPhase(Phase phase) {
		return MulinoPhaseFactory.getInstance().of(phase);
	}

	public abstract List<GameAction> legitActions(MulinoState ms);

}
