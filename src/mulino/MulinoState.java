package mulino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.general.GameAction;
import game.general.GameState;
import mulino.shared.State.Checker;
import mulino.shared.State.Phase;

public class MulinoState extends GameState {

	private MulinoPhase currentPhase;
	private Checker dutyPlayer;
	private int[] checkers = new int[] {9, 9};
	private int[] checkersOnBoard = new int[] {0, 0};
	private HashMap<int[], Checker> board = new HashMap<>();

	@Override
	public List<GameAction> legitActions() {
		return currentPhase.legitActions(this);
	}

	@Override
	public boolean isOver() {
		// TODO
		return false;
	}

	public void updatePhase() {
		currentPhase = MulinoPhase.getPhase(checkers[0], checkers[1], checkersOnBoard[0], checkersOnBoard[1]);
	}

	// potrebbe tornarci utile per "memorizzare" gli stati già visitati mentre
	// ragioniamo sulla prossima mossa
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + checkers[1];
		result = prime * result + checkersOnBoard[1];
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((currentPhase == null) ? 0 : currentPhase.getPhaseName().hashCode());
		result = prime * result + checkers[0];
		result = prime * result + checkersOnBoard[0];
		return result;
	}

	public MulinoState clone() {
		// generate the new State
		MulinoState result = new MulinoState();

		// replicate the current board
		result.getBoard().putAll(this.getBoard());

		// update the checkers available to the players
		result.setCheckers(this.getCheckers());
		result.setCheckersOnBoard(this.getCheckersOnBoard());

		// update the phase
		result.setCurrentPhase(this.getCurrentPhase());

		// set the same dutyPlayer
		result.setDutyPlayer(this.getDutyPlayer());

		return result;
	}

	// mi dice se la pedina in "xy" è parte di un mulino del colore "checker"
	public boolean thereIsMulino(int[] xy, Checker checker) {
		int x = xy[0];
		int y = xy[1];
		int pedine = 0;
		int i;
		if (x != 0 && y != 0) {
			// controllo la riga
			if (board.get(new int[] { -x, y }) == checker && board.get(new int[] { 0, y }) == checker) {
				return true;
			}
			// controllo la colonna
			if (board.get(new int[] { x, -y }) == checker && board.get(new int[] { x, 0 }) == checker) {
				return true;
			}
		} else if (x == 0) {
			// controllo la riga
			if (board.get(new int[] { y, y }) == checker && board.get(new int[] { -y, y }) == checker) {
				return true;
			}
			// controllo la mezza colonna
			if (y < 0) {
				for (i = -1; i <= -3; i--) {
					if (i != y && board.get(new int[] { 0, i }) == checker)
						pedine++;
				}
			} else {
				for (i = 1; i <= 3; i++) {
					if (i != y && board.get(new int[] { 0, i }) == checker)
						pedine++;
				}
			}
			// se ci sono esattamente 2 pedine oltre a quella in esame -> mulino!
			if (pedine == 2) {
				return true;
			}
		} else if (y == 0) {
			// controllo la colonna
			if (board.get(new int[] { x, x }) == checker && board.get(new int[] { x, -x }) == checker) {
				return true;
			}
			// controllo la mezza riga
			if (x < 0) {
				for (i = -1; i <= -3; i--) {
					if (i != x && board.get(new int[] { i, 0 }) == checker)
						pedine++;
				}
			} else {
				for (i = 1; i <= 3; i++) {
					if (i != x && board.get(new int[] { i, 0 }) == checker)
						pedine++;
				}
			}
			// se ci sono esattamente 2 pedine oltre a quella in esame -> mulino!
			if (pedine == 2) {
				return true;
			}
		}
		return false;
	}

	// per fase 1
	public void newCheckerPlayed(int[] to, Checker player) {
		board.replace(to, player); // aggiungo la pedina

		// aggiorno le pedine ancora da posizionare e quelle in campo
		setCheckers(player, getCheckers(player) - 1);
		setCheckersOnBoard(player, getCheckersOnBoard(player) + 1);
	}
	
	// per fase 2 e 3
	public void moveChecker(int[] from, int[] to) {
		Checker checker = board.get(from);
		board.replace(from, Checker.EMPTY);
		board.replace(to, checker);
	}

	public void removeChecker(int[] pos, Checker player) {
		board.replace(pos, Checker.EMPTY);

		setCheckersOnBoard(player, getCheckersOnBoard(player) - 1);
	}

	// localizza le posizioni di tutte le pedine dell'enemyPlayer sul tabellone
	public List<int[]> findEnemies() {
		List<int[]> mulinoEnemies = new ArrayList<int[]>();
		List<int[]> isolatedEnemies = new ArrayList<int[]>();
		Checker checker;

		for (int[] xy : board.keySet()) {
			checker = board.get(xy);
			if (checker == enemyPlayer()) {
				if (thereIsMulino(xy, checker)) {
					mulinoEnemies.add(xy);
				} else {
					isolatedEnemies.add(xy);
				}
			}
		}

		return isolatedEnemies.isEmpty() ? mulinoEnemies : isolatedEnemies;
	}

	// utility
	private Checker enemyPlayer() {
		return dutyPlayer == Checker.WHITE ? Checker.BLACK : Checker.WHITE;
	}

	// getters and setters
	public HashMap<int[], Checker> getBoard() {
		return board;
	}

	public void setBoard(HashMap<int[], Checker> hashMap) {
		this.board = hashMap;
	}

	public Checker getDutyPlayer() {
		return dutyPlayer;
	}

	public void setDutyPlayer(Checker player) {
		this.dutyPlayer = player;
	}

	public Phase getCurrentPhase() {
		return currentPhase.getPhaseName();
	}

	public void setCurrentPhase(Phase phase) {
		currentPhase = MulinoPhase.getPhase(phase);
	}

	public int getWhiteCheckers() {
		return checkers[0];
	}

	public void setWhiteCheckers(int whiteCheckers) {
		this.checkers[0] = whiteCheckers;
	}

	public int getBlackCheckers() {
		return checkers[1];
	}

	public void setBlackCheckers(int blackCheckers) {
		this.checkers[1] = blackCheckers;
	}

	public int getWhiteCheckersOnBoard() {
		return checkersOnBoard[0];
	}

	public void setWhiteCheckersOnBoard(int whiteCheckersOnBoard) {
		this.checkersOnBoard[0] = whiteCheckersOnBoard;
	}

	public int getBlackCheckersOnBoard() {
		return checkersOnBoard[1];
	}

	public void setBlackCheckersOnBoard(int blackCheckersOnBoard) {
		this.checkersOnBoard[1] = blackCheckersOnBoard;
	}

////	 temporary utilities
////	 TODO: metodi evitabili se NON usassimo "white" e "black" nei NOMI di
////	 variabili e metodi...
//
//	private int getCheckers(Checker player) {
//		return player == Checker.WHITE ? whiteCheckers : blackCheckers;
//	}
//
//	private void setCheckers(Checker player, int n) {
//		if (player == Checker.WHITE)
//			whiteCheckers = n;
//		else
//			blackCheckers = n;
//	}
//
//	private int getCheckersOnBoard(Checker player) {
//		return player == Checker.WHITE ? whiteCheckersOnBoard : blackCheckersOnBoard;
//	}
//
//	private void setCheckersOnBoard(Checker player, int n) {
//		if (player == Checker.WHITE)
//			whiteCheckersOnBoard = n;
//		else
//			blackCheckersOnBoard = n;
//	}
	
	// long-term utilities
	// TODONE: metodi fantastici e super efficienti che risolvono pure la fame nel mondo...
	private int getCheckers(Checker player) {
		return checkers[player.ordinal() - 1];
	}
	
	private void setCheckers(Checker player, int n) {
		checkers[player.ordinal() - 1] = n;
	}
	
	private int getCheckersOnBoard(Checker player) {
		return checkersOnBoard[player.ordinal() - 1];
	}
	
	private void setCheckersOnBoard(Checker player, int n) {
		checkers[player.ordinal() - 1] = n;
	}
	
	private int[] getCheckers() {
		return this.checkers;
	}
	
	private void setCheckers(int[] checkers) {
		this.checkers = checkers;
	}
	
	private int[] getCheckersOnBoard() {
		return checkersOnBoard;
	}
	
	private void setCheckersOnBoard(int[] checkersOnBoard) {
		this.checkersOnBoard = checkersOnBoard;
	}
}