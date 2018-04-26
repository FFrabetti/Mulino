package mulino;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import game.general.GameAction;
import game.general.GameState;
import mulino.State.Checker;
import mulino.State.Phase;

public class MulinoState extends GameState {

	private Phase currentPhase;
	private Checker dutyPlayer;
	private int whiteCheckers = 9;
	private int blackCheckers = 9;
	private int whiteCheckersOnBoard = 0;
	private int blackCheckersOnBoard = 0;
	private HashMap<int[], Checker> board = new HashMap<>();

	// /* idee:
	// * 1) usare un array di posizioni per inizializzare lo stato (come Chesani,
	// però così ogni MulinoState ha un array composto da 24 array di 2 interi
	// ciascuno (48 int), inefficiente);
	// * 2) inizializzarlo in maniera bovina (brutto da vedere ma più efficiente ->
	// DOBBIAMO VINCERE!);
	// */
	// // per l'idea 1)
	// public final int[][] positions = {
	// {-3,-3},{-3,0},{-3,3},
	// {-2,-2},{-2,0},{-2,2},
	// {-1,-1},{-1,0},{-1,1},
	// {0,-3},{0,-2},{0,-1},{0,1},{0,2},{0,3},
	// {1,-1},{1,0},{1,1},
	// {2,-2},{2,0},{2,2},
	// {3,-3},{3,0},{3,3}};
	//
	//
	// public MulinoState() {
	// // idea 1) in azione
	// for(int[] xy : positions) {
	// board.put(xy, Checker.EMPTY);
	// }
	// // idea 2) in azione
	// for(int x = -3; x < 0; x++) {
	// board.put(new int[]{x,x}, Checker.EMPTY);
	// board.put(new int[]{x,0}, Checker.EMPTY);
	// board.put(new int[]{x,-x}, Checker.EMPTY);
	// }
	// for(int x = 1; x <= 3; x++) {
	// board.put(new int[]{x,x}, Checker.EMPTY);
	// board.put(new int[]{x,0}, Checker.EMPTY);
	// board.put(new int[]{x,-x}, Checker.EMPTY);
	// }
	// for(int y = -3; y <= 3; y++) {
	// if(y != 0) {
	// board.put(new int[]{0,y}, Checker.EMPTY);
	// }
	// }
	// }

	@Override
	public Collection<GameAction> legitActions() {
		Collection<GameAction> actions = new ArrayList<GameAction>();
		boolean spottedEnemies = false;
		Collection<int[]> mulinoEnemies = new ArrayList<int[]>();
		Collection<int[]> isolatedEnemies = new ArrayList<int[]>();

		if (currentPhase.equalsName("First")) {
			for (int[] xy : board.keySet()) {
				// guardo tutte le posizioni vuote
				if (board.get(xy).equalsChecker('O')) {
					// controllo se faccio un mulino
					if (thereIsMulino(xy, dutyPlayer) == true) {
						// se non ho ancora controllato le posizioni nemiche lo faccio
						if (!spottedEnemies) {
							for (int[] tmp : board.keySet()) {
								Checker enemy = board.get(tmp);
								if (enemy.name().equals(enemyPlayer().name())) {
									if (thereIsMulino(tmp, enemy)) {
										mulinoEnemies.add(tmp);
									} else {
										isolatedEnemies.add(tmp);
									}

								}
							}
							// tutte le pedine "cattive" sono state rilevate
							spottedEnemies = true;
						}
						// genero una nuova MulinoAction per ogni possibile pedina nemica isolata
						for (int[] position : isolatedEnemies) {
							actions.add(new Phase1MulinoAction(xy, position));
						}
						// se non ci sono pedine nemiche isolate posso rimuovere quelle nei mulini
						if (isolatedEnemies.isEmpty()) {
							for (int[] position : mulinoEnemies) {
								actions.add(new Phase1MulinoAction(xy, position));
							}
						}
					}
				} else {
					actions.add(new Phase1MulinoAction(xy));
				}
			}
		} else if (currentPhase.equalsName("Second")) {
			for (int[] from : board.keySet()) {
				// controllo casella partenza ed arrivo
				if (board.get(from).name() == dutyPlayer.name()) {
					for (int[] to : board.keySet()) {
						// controllo che la casella sia libera ed adiacente a quella di partenza
						if (board.get(to).equalsChecker('O') && ((to[0] == from[0] && Math.abs(to[1] - from[1]) == 1)
								|| (to[1] == from[1] && Math.abs(to[0] - from[0]) == 1))) {
							// controllo se faccio un mulino
							if (thereIsMulino(to, dutyPlayer) == true) {
								if (!spottedEnemies) {
									for (int[] tmp : board.keySet()) {
										Checker enemy = board.get(tmp);
										if (enemy.name().equals(enemyPlayer().name())) {
											if (thereIsMulino(tmp, enemy)) {
												mulinoEnemies.add(tmp);
											} else {
												isolatedEnemies.add(tmp);
											}

										}
									}
									// tutte le pedine "cattive" sono state rilevate
									spottedEnemies = true;
								}
								// genero una nuova MulinoAction per ogni possibile pedina nemica isolata
								for (int[] position : isolatedEnemies) {
									actions.add(new Phase2MulinoAction(from, to, position));
								}
								// se non ci sono pedine nemiche isolate posso rimuovere quelle nei mulini
								if (isolatedEnemies.isEmpty()) {
									for (int[] position : mulinoEnemies) {
										actions.add(new Phase2MulinoAction(from, to, position));
									}
								}
							} else {
								actions.add(new Phase2MulinoAction(from, to));
							}
						}
					}
				}
			}
		} else if (currentPhase.equalsName("Final")) {
			for (int[] from : board.keySet()) {
				// controllo casella partenza ed arrivo
				if (board.get(from).name() == dutyPlayer.name()) {
					for (int[] to : board.keySet()) {
						if (board.get(to).equalsChecker('O')) {
							// controllo se faccio un mulino
							if (thereIsMulino(to, dutyPlayer) == true) {
								if (!spottedEnemies) {
									for (int[] tmp : board.keySet()) {
										Checker enemy = board.get(tmp);
										if (enemy.name().equals(enemyPlayer().name())) {
											if (thereIsMulino(tmp, enemy)) {
												mulinoEnemies.add(tmp);
											} else {
												isolatedEnemies.add(tmp);
											}

										}
									}
									// tutte le pedine "cattive" sono state rilevate
									spottedEnemies = true;
								}
								// genero una nuova MulinoAction per ogni possibile pedina nemica isolata
								for (int[] position : isolatedEnemies) {
									actions.add(new Phase3MulinoAction(from, to, position));
								}
								// se non ci sono pedine nemiche isolate posso rimuovere quelle nei mulini
								if (isolatedEnemies.isEmpty()) {
									for (int[] position : mulinoEnemies) {
										actions.add(new Phase3MulinoAction(from, to, position));
									}
								}
							} else {
								actions.add(new Phase3MulinoAction(from, to));
							}
						}
					}
				}
			}
		}
		return actions;
	}

	@Override
	public boolean isOver() {
		// TODO ?
		return false;
	}

	@Override
	public boolean isWinning() {
		// manca la parte in cui vinco perché l'avversario non riesce più a muovere
		// (fase 2)
		if (currentPhase.equalsName("Final")) {
			if (dutyPlayer.equalsChecker('W') && blackCheckersOnBoard < 3)
				return true;
		} else if (currentPhase.equalsName("Final")) {
			if (dutyPlayer.equalsChecker('B') && whiteCheckersOnBoard < 3)
				return true;
		}
		return false;
	}

	public void updatePhase() {
		if (currentPhase.equalsName("First")) {
			if (whiteCheckers == 0 && blackCheckers == 0)
				currentPhase = State.Phase.SECOND;
		} else if (currentPhase.equalsName("Second")) {
			if (whiteCheckers < 3 || blackCheckers < 3)
				currentPhase = State.Phase.FINAL;
		}
	}

	// potrebbe tornarci utile per "memorizzare" gli stati già visitati mentre
	// ragioniamo sulla prossima mossa
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blackCheckers;
		result = prime * result + blackCheckersOnBoard;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((currentPhase == null) ? 0 : currentPhase.hashCode());
		result = prime * result + whiteCheckers;
		result = prime * result + whiteCheckersOnBoard;
		return result;
	}

	public MulinoState clone() {
		// generate the new State
		MulinoState result = new MulinoState();

		// replicate the current board
		result.getBoard().putAll(this.getBoard());

		// update the checkers available to the players
		result.setWhiteCheckers(this.getWhiteCheckers());
		result.setBlackCheckers(this.getBlackCheckers());
		result.setWhiteCheckersOnBoard(this.getWhiteCheckersOnBoard());
		result.setBlackCheckersOnBoard(this.getBlackCheckersOnBoard());

		// update the phase
		result.setCurrentPhase(this.getCurrentPhase());

		// set the same dutyPlayer
		result.setDutyPlayer(this.getDutyPlayer());
		return result;
	}

	// mi dice se la pedina in "xy" è parte di un mulino del colore "checker"
	private boolean thereIsMulino(int[] xy, Checker checker) {
		int x = xy[0];
		int y = xy[1];
		int pedine = 0;
		int i;
		if (x != 0 && y != 0) {
			// controllo la riga
			if (board.get(new int[] { -x, y }).name().equals(checker.name())
					&& board.get(new int[] { 0, y }).name().equals(checker.name())) {
				return true;
			}
			// controllo la colonna
			if (board.get(new int[] { x, -y }).name().equals(checker.name())
					&& board.get(new int[] { x, 0 }).name().equals(checker.name())) {
				return true;
			}
		} else if (x == 0) {
			// controllo la riga
			if (board.get(new int[] { y, y }).name().equals(checker.name())
					&& board.get(new int[] { -y, y }).name().equals(checker.name())) {
				return true;
			}
			// controllo la mezza colonna
			if (y < 0) {
				for (i = -1; i <= -3; i--) {
					if (board.get(new int[] { 0, i }).name().equals(checker.name()))
						pedine++;
				}
			} else {
				for (i = 1; i <= 3; i++) {
					if (board.get(new int[] { 0, i }).name().equals(checker.name()))
						pedine++;
				}
			}
			// se ci sono esattamente 2 pedine, la mia completa il mulino
			if (pedine == 2) {
				return true;
			}
		} else if (y == 0) {
			// controllo la colonna
			if (board.get(new int[] { x, x }).name().equals(checker.name())
					&& board.get(new int[] { x, -x }).name().equals(checker.name())) {
				return true;
			}
			// controllo la mezza riga
			if (x < 0) {
				for (i = -1; i <= -3; i--) {
					if (board.get(new int[] { i, 0 }).name().equals(checker.name()))
						pedine++;
				}
			} else {
				for (i = 1; i <= 3; i++) {
					if (board.get(new int[] { i, 0 }).name().equals(checker.name()))
						pedine++;
				}
			}
			// se ci sono esattamente 2 pedine, la mia completa il mulino
			if (pedine == 2) {
				return true;
			}
		}
		return false;
	}

	private Checker enemyPlayer() {
		return dutyPlayer.equalsChecker('W') ? Checker.BLACK : Checker.WHITE;
	}

	// getters and setters
	public HashMap<int[], Checker> getBoard() {
		return board;
	}

	public void setBoard(HashMap<int[], Checker> hashMap) {
		this.board = hashMap;
	}

	public Checker getDutyPlayer() {
		return this.dutyPlayer;
	}

	public void setDutyPlayer(Checker checker) {
		this.dutyPlayer = checker;
	}

	// public int[][] getPositions() {
	// return positions;
	// }

	public Phase getCurrentPhase() {
		return currentPhase;
	}

	public void setCurrentPhase(Phase currentPhase) {
		this.currentPhase = currentPhase;
	}

	public int getWhiteCheckers() {
		return whiteCheckers;
	}

	public void setWhiteCheckers(int whiteCheckers) {
		this.whiteCheckers = whiteCheckers;
	}

	public int getBlackCheckers() {
		return blackCheckers;
	}

	public void setBlackCheckers(int blackCheckers) {
		this.blackCheckers = blackCheckers;
	}

	public int getWhiteCheckersOnBoard() {
		return whiteCheckersOnBoard;
	}

	public void setWhiteCheckersOnBoard(int whiteCheckersOnBoard) {
		this.whiteCheckersOnBoard = whiteCheckersOnBoard;
	}

	public int getBlackCheckersOnBoard() {
		return blackCheckersOnBoard;
	}

	public void setBlackCheckersOnBoard(int blackCheckersOnBoard) {
		this.blackCheckersOnBoard = blackCheckersOnBoard;
	}

}
