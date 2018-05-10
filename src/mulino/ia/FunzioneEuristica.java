package mulino.ia;

import java.util.List;

import aima.search.framework.HeuristicFunction;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import it.unibo.ai.didattica.mulino.domain.State.Phase;
import mulino.MulinoAction;
import mulino.MulinoState;
import mulino.Phase1MulinoAction;
import mulino.Phase23MulinoAction;
import mulino.Position;

public class FunzioneEuristica implements HeuristicFunction {

	private int[][] valori = new int[3][3];

	public FunzioneEuristica() {
		valori[0][0] = 1; // pedina giocata in riga vuota
		valori[1][0] = 7; // ho già un'altra pedina e l'ultimo spazio è vuoto -> minaccio un mulino!
		valori[0][1] = 3; // stessa riga di un nemico -> non può più farci mulino
		valori[2][0] = 29; // due pedine mie + quella che gioco -> MULINO!
		valori[0][2] = 15; // stava per fare mulino ma occupo l'ultimo spazio della riga -> ehhh volevi!
		valori[1][1] = 0; // pedina mia e pedina sua... perché giocarne un'altra? :(
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
	public double getHeuristicValue(Object state) {
		MulinoState s = (MulinoState) state;

		double value = 0;
		int maxActionVal = 0;
		if (s.getCurrentPhase() == Phase.FIRST) {
			for (GameAction a : s.legitActions()) {
				maxActionVal = phase1ActionValue(s, (Phase1MulinoAction) a);
				if (maxActionVal == 30)
					break;
			}
			value = maxActionVal; // in fase 1 un buono stato coincide con una buona mossa a disposizione
		} else {
			for (GameAction a : s.legitActions()) {
				maxActionVal = phase23ActionValue(s, (Phase23MulinoAction) a);
				if (maxActionVal == 30)
					break;
			}
			value = maxActionVal + stateValue(s); // in fase 23 sullo stato pesano anche il numero di pedine rimaste
		}
		return value;
	}

	private int stateValue(MulinoState state) {
		return (state.getCheckers(state.getDutyPlayer()) - state.getCheckers(state.enemyPlayer()));
	}

	private int phase1ActionValue(MulinoState state, Phase1MulinoAction action) {
		int dp = 0;
		int ep = 0;
		int val = 0;
		Checker c;
		Checker enemyPlayer = state.enemyPlayer();
		Checker dutyPlayer = state.getDutyPlayer();

		// recupero le posizioni "mulinabili" (non pos[2]=pos[5]=action.getTo())
		Position[] pos = state.getBoard().mulinoPositions(action.getTo());
		// controllo la riga
		c = state.getBoard().getChecker(pos[0]);
		if (c == dutyPlayer)
			dp++;
		else if (c == enemyPlayer)
			ep++;
		c = state.getBoard().getChecker(pos[1]);
		if (c == dutyPlayer)
			dp++;
		else if (c == enemyPlayer)
			ep++;
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		c = state.getBoard().getChecker(pos[3]);
		if (c == dutyPlayer)
			dp++;
		else if (c == enemyPlayer)
			ep++;
		c = state.getBoard().getChecker(pos[4]);
		if (c == dutyPlayer)
			dp++;
		else if (c == enemyPlayer)
			ep++;
		val = val + valori[dp][ep];

		return val;
	}

	// qui devo ricordarmi di non considerare la posizione di partenza della pedina
	// -> !pos[P].equals(action.getFrom());
	private int phase23ActionValue(MulinoState state, Phase23MulinoAction action) {
		int dp = 0;
		int ep = 0;
		int val = 0;
		Checker c;
		Checker enemyPlayer = state.enemyPlayer();
		Checker dutyPlayer = state.getDutyPlayer();

		// recupero le posizioni "mulinabili" (non pos[2]=pos[5]=action.getTo())
		Position[] pos = state.getBoard().mulinoPositions(action.getTo());
		// controllo la riga
		if (!pos[0].equals(action.getFrom())) {
			c = state.getBoard().getChecker(pos[0]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[1].equals(action.getFrom())) {
			c = state.getBoard().getChecker(pos[1]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		if (!pos[3].equals(action.getFrom())) {
			c = state.getBoard().getChecker(pos[3]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		if (!pos[4].equals(action.getFrom())) {
			c = state.getBoard().getChecker(pos[4]);
			if (c == dutyPlayer)
				dp++;
			else if (c == enemyPlayer)
				ep++;
		}
		val = val + valori[dp][ep];

		return val;
	}

}
