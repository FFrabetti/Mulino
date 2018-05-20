package mulino.ia;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import mulino.MulinoState;
import mulino.Phase1MulinoAction;
import mulino.Phase23MulinoAction;
import mulino.Position;

public class FunzioneEuristica implements HeuristicFunction {

	private static final int MUL_FACTOR = 100;

	private int[][] valori = new int[3][3];

	public FunzioneEuristica() {
		valori[0][0] = 1; // pedina giocata in riga vuota
		valori[1][0] = 7; // ho già un'altra pedina e l'ultimo spazio è vuoto -> minaccio un mulino!
		valori[0][1] = 3; // stessa riga di un nemico -> non può più farci mulino
		valori[2][0] = 56; // due pedine mie + quella che gioco -> MULINO!
		valori[0][2] = 36; // stava per fare mulino ma occupo l'ultimo spazio della riga -> ehhh volevi!
		valori[1][1] = 0; // pedina mia e pedina sua... perché giocarne un'altra? :(
	}

	public double evalAction(MulinoState state, GameAction action) {
		return action instanceof Phase1MulinoAction ? phase1ActionValue(state, (Phase1MulinoAction) action) :
			phase23ActionValue(state, (Phase23MulinoAction) action);
	}
	
	/*
	 * Il valore di uno stato dipende da due parametri: 1) la migliore mossa che mi
	 * si presenta; 2) l'andamento della partita;
	 * 
	 * Per quanto riguarda la 1), attribuisco ad ogni mossa un punteggio e controllo
	 * le legitActions() mantenendo traccia del punteggio migliore (la mossa che
	 * sceglierei se mi trovassi in quello stato). Invece il parametro 2) lo deduco
	 * dalla differenza di pedine bianche e nere sul tavolo: se ho più pedine
	 * rispetto all'avversario vuol dire che non sto giocando male, viceversa man
	 * mano che lui fa mulini vado verso la sconfitta (applicabile dalla fase 2 in
	 * poi)
	 */
	@Override
	public double h(Object state) {
		MulinoState s = (MulinoState) state;

		double value = 0;
		double maxActionVal = 0;
		double tempVal = 0;
		if (s.getCurrentPhase() == Phase.FIRST) {
			for (GameAction a : s.legitActions()) {
				tempVal = phase1ActionValue(s, (Phase1MulinoAction) a);
				if (tempVal > maxActionVal)
					maxActionVal = tempVal;
				if (maxActionVal == valori[0][2]*2)
					break;
			}
			
			value = maxActionVal + stateValue(
					s.getBoard().checkers(s.getDutyPlayer()) + (s.getDutyPlayer()==Checker.BLACK ? 1 : 0),
					s.getBoard().checkers(s.enemyPlayer())); // in fase 1 un buono stato coincide con una buona mossa a disposizione
		} else {
			for (GameAction a : s.legitActions()) {
				tempVal = phase23ActionValue(s, (Phase23MulinoAction) a);
				if (tempVal > maxActionVal)
					maxActionVal = tempVal;
				if (maxActionVal == valori[0][2]*2)
					break;
			}
			value = maxActionVal + stateValue(
					s.getBoard().checkers(s.getDutyPlayer()) + (s.getDutyPlayer()==Checker.BLACK ? 1 : 0),
					s.getBoard().checkers(s.enemyPlayer())); // in fase 23 sullo stato pesano anche il numero di pedine rimaste
		}
//		System.out.println(state + " Hval = " + value);

		return value;
//		return 1 - 1 / value;
	}

	private int stateValue(int ch1, int ch2) {
		return (ch1 - ch2)*MUL_FACTOR;
	}

	private double evalRemoveOpponent(MulinoState state, Position position) {
		return evalPutChecker(state, position, state.enemyPlayer())/100.0;
	}
	
	private double phase1ActionValue(MulinoState state, Phase1MulinoAction action) {
		double result = evalPutChecker(state, action.getTo(), state.getDutyPlayer());
		
		if(action.getRemoveOpponent().isPresent())
			result = result + evalRemoveOpponent(state, action.getRemoveOpponent().get());
		
		return result;
	}

	private double evalPutChecker(MulinoState state, Position to, Checker dutyPlayer) {
		int dp = 0;
		int ep = 0;
		int val = 0;
		Checker c;
		Checker enemyPlayer = dutyPlayer==Checker.WHITE ? Checker.BLACK : Checker.WHITE;

		// recupero le posizioni "mulinabili"
		Position[] pos = state.getBoard().mulinoPositions(to);
		// controllo la riga
		if (!pos[0].equals(to)) {
			c = state.getBoard().getChecker(pos[0]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[1].equals(to)) {
			c = state.getBoard().getChecker(pos[1]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if(!pos[2].equals(to)){
			c = state.getBoard().getChecker(pos[2]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		if (!pos[3].equals(to)) {
			c = state.getBoard().getChecker(pos[3]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[4].equals(to)) {
			c = state.getBoard().getChecker(pos[4]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if(!pos[5].equals(to)){
			c = state.getBoard().getChecker(pos[5]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];

		return val;
	}

	// qui devo ricordarmi di non considerare la posizione di partenza della pedina
	// -> !pos[P].equals(action.getFrom());
	private double phase23ActionValue(MulinoState state, Phase23MulinoAction action) {
		int dp = 0;
		int ep = 0;
		double val = 0;
		Checker c;
		Checker enemyPlayer = state.enemyPlayer();
		Checker dutyPlayer = state.getDutyPlayer();

		// recupero le posizioni "mulinabili"
		Position[] pos = state.getBoard().mulinoPositions(action.getTo());
		// controllo la riga
		if (!pos[0].equals(action.getFrom()) && !pos[0].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[0]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[1].equals(action.getFrom()) && !pos[1].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[1]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[2].equals(action.getFrom()) && !pos[2].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[2]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		if (!pos[3].equals(action.getFrom()) && !pos[3].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[3]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[4].equals(action.getFrom()) && !pos[4].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[4]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[5].equals(action.getFrom()) && !pos[5].equals(action.getTo())) {
			c = state.getBoard().getChecker(pos[5]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];

		if(action.getRemoveOpponent().isPresent())
			val = val + evalRemoveOpponent(state, action.getRemoveOpponent().get());
		
		return val;
	}

}
