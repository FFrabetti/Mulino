package mulino;

import game.general.GameState;

public class Phase1MulinoAction extends MulinoAction {

	private int[] putPosition;

	public Phase1MulinoAction(int[] putPosition) {
		super();
		this.putPosition[0] = putPosition[0];
		this.putPosition[1] = putPosition[1];
	}

	public Phase1MulinoAction(int[] putPosition, int[] enemyPosition) {
		super(enemyPosition);
		this.putPosition[0] = putPosition[0];
		this.putPosition[1] = putPosition[1];
	}

	public int[] getPutPosition() {
		return this.putPosition;
	}

	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		// muovo la pedina
		newState.getBoard().replace(putPosition, oldState.getDutyPlayer());

		// aggiorno il numero di pedine ancora da posizionare ed il numero di quelle in
		// campo
		if (oldState.getDutyPlayer().equalsChecker('W')) {
			newState.setWhiteCheckers(oldState.getWhiteCheckers() - 1);
			newState.setWhiteCheckersOnBoard(oldState.getWhiteCheckersOnBoard() + 1);
		} else if (oldState.getDutyPlayer().equalsChecker('B')) {
			newState.setBlackCheckers(oldState.getBlackCheckers() - 1);
			newState.setBlackCheckersOnBoard(oldState.getBlackCheckersOnBoard() + 1);
		}

		// se ho fatto un mulino rimuovo una pedina nemica ed aggiorno il numero di
		// quelle sul tabellone
		if (removeOpponentChecker != null) {
			newState.getBoard().replace(removeOpponentChecker, State.Checker.EMPTY);
			if (oldState.getDutyPlayer().equalsChecker('W')) {
				newState.setBlackCheckersOnBoard(oldState.getBlackCheckersOnBoard() - 1);
			} else if (oldState.getDutyPlayer().equalsChecker('B')) {
				newState.setWhiteCheckersOnBoard(oldState.getWhiteCheckersOnBoard() - 1);
			}
		}

		// adesso toccherà muovere all'avversario
		if (oldState.getDutyPlayer().equalsChecker('W'))
			newState.setDutyPlayer(State.Checker.BLACK);
		else if (oldState.getDutyPlayer().equalsChecker('B'))
			newState.setDutyPlayer(State.Checker.WHITE);

		// controllo se devo cambiare fase di gioco
		newState.updatePhase();

		return newState;
	}
}
