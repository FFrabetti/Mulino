package mulino;

import java.util.LinkedList;
import java.util.List;

import game.general.GameAction;
import game.general.GameState;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

/*
 * Refactoring:
 * - nessuno deve sapere come gestisco lo stato internamente, così posso cambiarlo quando mi pare
 * - cerco di minimizzare le informazioni, alcune posso calcolarle (se è veloce farlo)
 */
public class MulinoState extends GameState {

	private static final int W = 0;
	private static final int B = 1;

	private Checker dutyPlayer;
	private int[] availableCheckers; // per fase 1
	private Board board;

	public MulinoState(Board board, int avWCheckers, int avBCheckers) {
		dutyPlayer = Checker.WHITE;
		availableCheckers = new int[] { avWCheckers, avBCheckers };
		this.board = board;
	}
	
	public MulinoState() {
		this(new Board(), MulinoSettings.INITIAL_CHECKERS, MulinoSettings.INITIAL_CHECKERS);
	}

	// debug
	// private void printInfo(GameAction gameAction) {
	// MulinoAction action=(MulinoAction) gameAction;
	// System.out.println("Azione: "+action.getTo()[0]+","+action.getTo()[1]);
	// if (action.getRemoveOpponent().isPresent())
	// System.out.print(" rimovendo
	// "+action.getRemoveOpponent().get()[0]+","+action.getRemoveOpponent().get()[1]);
	// }

	// può essere calcolata facilmente -> non devo preoccuparmi di aggiornarla!
	public Phase getCurrentPhase() {
		if (availableCheckers[W]>0 || availableCheckers[B]>0)
			return Phase.FIRST;
		else if (board.checkers(Checker.WHITE) == 3 || board.checkers(Checker.BLACK) == 3)
			return Phase.FINAL;
		else
			return Phase.SECOND;
	}

	public Checker getDutyPlayer() {
		return dutyPlayer;
	}

	public void setDutyPlayer(Checker player) {
		this.dutyPlayer = player;
	}

	public void switchDutyPlayer() {
		dutyPlayer = enemyPlayer();
	}
	
	// metodi fantastici e super efficienti che risolvono pure la fame nel mondo...
	public int getCheckers(Checker player) {
		return availableCheckers[player.ordinal() - 1];
	}

	public void setCheckers(Checker player, int n) {
		availableCheckers[player.ordinal() - 1] = n;
	}

	private int index(Checker player) {
		return player.ordinal() - 1;
	}

	@Override
	public boolean isOver() {
		return getCurrentPhase()==Phase.FINAL &&
				(board.checkers(Checker.WHITE) < 3 || board.checkers(Checker.BLACK) < 3);
	}

	@Override
	public int hashCode() {
		return availableCheckers[W] ^ availableCheckers[B] ^ dutyPlayer.hashCode() ^ board.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof MulinoState))
			return false;
		
		MulinoState that = (MulinoState)o;
		return that.dutyPlayer==this.dutyPlayer &&
				that.availableCheckers[W]==this.availableCheckers[W] &&
				that.availableCheckers[B]==this.availableCheckers[B] &&
				that.board.equals(this.board);
	}

	@Override
	public MulinoState clone() {
		MulinoState result = new MulinoState();

		result.board = this.board.clone();
		result.availableCheckers[W] = this.availableCheckers[W];
		result.availableCheckers[B] = this.availableCheckers[B];
		result.dutyPlayer = this.dutyPlayer;

		return result;
	}

	// per fase 1
	public void playChecker(Position to) {
		board.put(to, dutyPlayer); // aggiungo la pedina

		// aggiorno le pedine ancora da posizionare
		availableCheckers[index(dutyPlayer)]--;
	}

	// per fase 2 e 3
	public void moveChecker(Position from, Position to) {
		board.move(from, to);
	}

	public void removeChecker(Position pos) {
		board.remove(pos);
	}

	// utility
	private Checker enemyPlayer() {
		return dutyPlayer == Checker.WHITE ? Checker.BLACK : Checker.WHITE;
	}

	// localizza le posizioni di tutte le pedine dell'enemyPlayer sul tabellone
	private List<Position> findEnemies() {
		List<Position> mulinoEnemies = new LinkedList<>();
		List<Position> isolatedEnemies = new LinkedList<>();

		for (Position p : board.getPositions(enemyPlayer())) {
			// evito di controllare 2 volte le pedine che so già essere in un mulino
			if (!mulinoEnemies.contains(p)) {
				if (board.isInMulino(p, mulinoEnemies)) // inserisce anche le altre pedine nella lista
					mulinoEnemies.add(p);
				else
					isolatedEnemies.add(p);
			}
		}

		return isolatedEnemies.isEmpty() ? mulinoEnemies : isolatedEnemies;
	}

	@Override
	public List<GameAction> legitActions() {
		switch (getCurrentPhase()) {
		case FIRST:
			return phase1Actions();
		case SECOND: // same actions
		case FINAL:
			return phase23Actions();
		}
		return null;
	}

	private List<GameAction> phase1Actions() {
		List<GameAction> list = new LinkedList<>();
		List<Position> removable = new LinkedList<>();

		board.freePositions().forEach(to -> {
			if (board.withMove(to, dutyPlayer).isInMulino(to)) {
				if (removable.isEmpty())
					removable.addAll(findEnemies());

				removable.forEach(r -> list.add(new Phase1MulinoAction(to, r)));
			} else
				list.add(new Phase1MulinoAction(to));
		});

		return list;
	}

	private List<GameAction> phase23Actions() {
		List<GameAction> list = new LinkedList<>();
		List<Position> removable = new LinkedList<>();

		board.getPositions(dutyPlayer).forEach(from -> {
			// posso spostarla in un posto adiacente, se libero
			// oppure in uno libero qualsiasi (se == 3)
			for (Position to : (board.checkers(dutyPlayer)==3 ? board.freePositions() : board.freeAdiacent(from))) {
				if (board.withMove(from, to).isInMulino(to)) {
					if (removable.isEmpty())
						removable.addAll(findEnemies());

					removable.forEach(r -> list.add(new Phase23MulinoAction(from, to, r, getCurrentPhase())));
				} else
					list.add(new Phase23MulinoAction(from, to, getCurrentPhase()));
			}
		});

		return list;
	}

	@Override
	public String toString() {
		return dutyPlayer + " [" + getCurrentPhase() + "] " + board;
	}
	
}
