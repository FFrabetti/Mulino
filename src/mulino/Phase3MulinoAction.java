package mulino;

import game.general.GameState;

public class Phase3MulinoAction extends MulinoAction {

	private int[] from;
	private int[] to;

	public Phase3MulinoAction(int[] from, int[] to) {
		super();
		this.from[0] = from[0];
		this.from[1] = from[1];
		this.to[0] = to[0];
		this.to[0] = to[0];
	}

	public Phase3MulinoAction(int[] from, int[] to, int[] enemyPosition) {
		super(enemyPosition);
		this.from[0] = from[0];
		this.from[1] = from[1];
		this.to[0] = to[0];
		this.to[0] = to[0];
	}

	public int[] getFrom() {
		return this.from;
	}

	public int[] getTo() {
		return this.to;
	}

	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldState = (MulinoState) currentState;
		MulinoState newState = oldState.clone(); // newState sarà molto simile a oldState

		// sposto la mia pedina
		newState.getBoard().replace(from, State.Checker.EMPTY);
		newState.getBoard().replace(to, oldState.getDutyPlayer());

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
