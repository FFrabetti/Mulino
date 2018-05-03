package mulino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;

public class MulinoPhaseFactory {

	private MulinoPhase[] phases = new MulinoPhase[] { new MulinoPhase1(), new MulinoPhase2(), new MulinoPhase3() };
	private static MulinoPhaseFactory instance;

	private MulinoPhaseFactory() {
	}

	public static MulinoPhaseFactory getInstance() {
		if (instance == null)
			instance = new MulinoPhaseFactory();
		return instance;
	}

	public MulinoPhase of(Phase phase) {
		if (phase == Phase.FIRST)
			return phases[0];
		else if (phase == Phase.SECOND)
			return phases[1];
		else
			return phases[2];
	}

	public MulinoPhase of(int whiteCheckers, int blackCheckers, int whiteCheckersOnBoard, int blackCheckersonBoard) {
		if (whiteCheckersOnBoard == 3 || blackCheckersonBoard == 3)
			return phases[2]; // FINAL
		else if (whiteCheckers == 0 && blackCheckers == 0)
			return phases[1]; // SECOND
		else
			return phases[0]; // FIRST
	}
	
	// CLASSI ANNIDATE

	private class MulinoPhase1 extends MulinoPhase {

		public MulinoPhase1() {
			this.phase = Phase.FIRST;
		}

//		@Override
//		public List<GameAction> legitActions(MulinoState ms) {
//			List<GameAction> actions = new ArrayList<GameAction>();
//			boolean spottedEnemies = false;
//			List<int[]> enemyPositions = new ArrayList<int[]>();
//			HashMap<int[], Checker> board = ms.getBoard();
//
//			for (int[] xy : board.keySet()) {
//				// guardo tutte le posizioni vuote
//				if (board.get(xy) == Checker.EMPTY) {
//					// controllo se faccio un mulino
//					if (ms.thereIsMulino(xy, ms.getDutyPlayer()) == true) {
//						System.out.println("DEBUG:C'è un mulino!!!");
//						// se non ho ancora controllato le posizioni nemiche lo faccio
//						if (!spottedEnemies) {
//							enemyPositions = ms.findEnemies(); // TODO
//							// tutte le pedine "cattive" sono state rilevate
//							spottedEnemies = true;
//						}
//						// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
//						for (int[] position : enemyPositions) {
//							actions.add(new Phase1MulinoAction(xy, position));
//						}
//					} else {
//						actions.add(new Phase1MulinoAction(xy));
//					}
//				}
//			}
//			return actions;
//		}
		
		@Override
		public List<GameAction> legitActions(MulinoState ms) {
			List<GameAction> actions = new ArrayList<GameAction>();
			boolean spottedEnemies = false;
			List<int[]> enemyPositions = new ArrayList<int[]>();
			HashMap<int[], Checker> board = ms.getBoard();

			for (int[] xy : board.keySet()) {
				// guardo tutte le posizioni vuote
				if (get(xy[0],xy[1],ms) == Checker.EMPTY) {
					// controllo se faccio un mulino
					if (ms.thereIsMulino(xy, ms.getDutyPlayer()) == true) {
						// se non ho ancora controllato le posizioni nemiche lo faccio
						if (!spottedEnemies) {
							enemyPositions = ms.findEnemies(); // TODO
							// tutte le pedine "cattive" sono state rilevate
							spottedEnemies = true;
						}
						// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
						for (int[] position : enemyPositions) {
							actions.add(new Phase1MulinoAction(xy, position));
						}
					} else {
						actions.add(new Phase1MulinoAction(xy));
					}
				}
			}
			return actions;
		}
	}

	private class MulinoPhase2 extends MulinoPhase {

		public MulinoPhase2() {
			// super();
			this.phase = Phase.SECOND;
		}

//		@Override
//		public List<GameAction> legitActions(MulinoState ms) {
//			List<GameAction> actions = new ArrayList<GameAction>();
//			boolean spottedEnemies = false;
//			List<int[]> enemyPositions = new ArrayList<int[]>();
//			HashMap<int[], Checker> board = ms.getBoard();
//
//			for (int[] from : board.keySet()) {
//				// controllo casella partenza ed arrivo
//				if (board.get(from) == ms.getDutyPlayer()) {
//					for (int[] to : board.keySet()) {
//						// controllo che la casella sia libera ed adiacente a quella di partenza
//						if (board.get(to) == Checker.EMPTY && ((to[0] == from[0] && Math.abs(to[1] - from[1]) == 1)
//								|| (to[1] == from[1] && Math.abs(to[0] - from[0]) == 1))) {
//							// controllo se faccio un mulino
//							if (ms.thereIsMulino(to, ms.getDutyPlayer()) == true) {
//								if (!spottedEnemies) {
//									enemyPositions = ms.findEnemies(); // TODO
//									// tutte le pedine "cattive" sono state rilevate
//									spottedEnemies = true;
//								}
//								// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
//								for (int[] position : enemyPositions) {
//									Phase23MulinoAction a = new Phase23MulinoAction(from, to, position);
//									a.setPhase(Phase.SECOND);
//									actions.add(a);
//								}
//							} else {
//								Phase23MulinoAction a = new Phase23MulinoAction(from, to);
//								a.setPhase(Phase.SECOND);
//								actions.add(a);
//							}
//						}
//					}
//				}
//			}
//			return actions;
//		}
		
		@Override
		public List<GameAction> legitActions(MulinoState ms) {
			List<GameAction> actions = new ArrayList<GameAction>();
			boolean spottedEnemies = false;
			List<int[]> enemyPositions = new ArrayList<int[]>();
			HashMap<int[], Checker> board = ms.getBoard();

			for (int[] from : board.keySet()) {
				// controllo casella partenza ed arrivo
				if (get(from[0],from[1],ms) == ms.getDutyPlayer()) {
					for (int[] to : board.keySet()) {
						// controllo che la casella sia libera ed adiacente a quella di partenza
						if (get(to[0],to[1],ms) == Checker.EMPTY && ((to[0] == from[0] && Math.abs(to[1] - from[1]) == 1)
								|| (to[1] == from[1] && Math.abs(to[0] - from[0]) == 1))) {
							// controllo se faccio un mulino
							if (ms.thereIsMulino(to, ms.getDutyPlayer()) == true) {
								if (!spottedEnemies) {
									enemyPositions = ms.findEnemies(); // TODO
									// tutte le pedine "cattive" sono state rilevate
									spottedEnemies = true;
								}
								// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
								for (int[] position : enemyPositions) {
									Phase23MulinoAction a = new Phase23MulinoAction(from, to, position);
									a.setPhase(Phase.SECOND);
									actions.add(a);
								}
							} else {
								Phase23MulinoAction a = new Phase23MulinoAction(from, to);
								a.setPhase(Phase.SECOND);
								actions.add(a);
							}
						}
					}
				}
			}
			return actions;
		}

	}

	private class MulinoPhase3 extends MulinoPhase {

		public MulinoPhase3() {
			// super();
			this.phase = Phase.FINAL;
		}

//		@Override
//		public List<GameAction> legitActions(MulinoState ms) {
//			List<GameAction> actions = new ArrayList<GameAction>();
//			boolean spottedEnemies = false;
//			List<int[]> enemyPositions = new ArrayList<int[]>();
//			HashMap<int[], Checker> board = ms.getBoard();
//
//			for (int[] from : board.keySet()) {
//				// controllo casella partenza ed arrivo
//				if (board.get(from) == ms.getDutyPlayer()) {
//					for (int[] to : board.keySet()) {
//						if (board.get(to) == Checker.EMPTY) {
//							// controllo se faccio un mulino
//							if (ms.thereIsMulino(to, ms.getDutyPlayer()) == true) {
//								if (!spottedEnemies) {
//									enemyPositions = ms.findEnemies();
//									// tutte le pedine "cattive" sono state rilevate
//									spottedEnemies = true;
//								}
//								// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
//								for (int[] position : enemyPositions) {
//									Phase23MulinoAction a = new Phase23MulinoAction(from, to, position);
//									a.setPhase(Phase.FINAL);
//									actions.add(a);
//								}
//							} else {
//								Phase23MulinoAction a = new Phase23MulinoAction(from, to);
//								a.setPhase(Phase.FINAL);
//								actions.add(a);
//							}
//						}
//					}
//				}
//			}
//			return actions;
//		}
		
		@Override
		public List<GameAction> legitActions(MulinoState ms) {
			List<GameAction> actions = new ArrayList<GameAction>();
			boolean spottedEnemies = false;
			List<int[]> enemyPositions = new ArrayList<int[]>();
			HashMap<int[], Checker> board = ms.getBoard();

			for (int[] from : board.keySet()) {
				// controllo casella partenza ed arrivo
				if (get(from[0],from[1],ms) == ms.getDutyPlayer()) {
					for (int[] to : board.keySet()) {
						if (get(to[0],to[1],ms) == Checker.EMPTY) {
							// controllo se faccio un mulino
							if (ms.thereIsMulino(to, ms.getDutyPlayer()) == true) {
								if (!spottedEnemies) {
									enemyPositions = ms.findEnemies();
									// tutte le pedine "cattive" sono state rilevate
									spottedEnemies = true;
								}
								// genero una nuova MulinoAction per ogni possibile pedina nemica rimovibile
								for (int[] position : enemyPositions) {
									Phase23MulinoAction a = new Phase23MulinoAction(from, to, position);
									a.setPhase(Phase.FINAL);
									actions.add(a);
								}
							} else {
								Phase23MulinoAction a = new Phase23MulinoAction(from, to);
								a.setPhase(Phase.FINAL);
								actions.add(a);
							}
						}
					}
				}
			}
			return actions;
		}
	}
	
	private Checker get(int x,int y,MulinoState ms) {
		
		for(int[] i : ms.getBoard().keySet()) {
			if(i[0]==x && i[1]==y) {
				return ms.getBoard().get(i);
			}
		}
		return null;
	}
}
