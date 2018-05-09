package mulino.ia;

import java.util.List;

import aima.search.framework.HeuristicFunction;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.MulinoAction;
import mulino.MulinoState;
import mulino.Phase1MulinoAction;
import mulino.Phase23MulinoAction;
import mulino.Position;

public class FunzioneEuristica implements HeuristicFunction {

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

		for (GameAction a : s.legitActions()) {
			MulinoAction mossa = (MulinoAction) a;

		}
		return 0;
	}

	private double mulinoStateValue(MulinoState state) {
		return (state.getCheckers(state.getDutyPlayer()) - state.getCheckers(state.enemyPlayer()));
	}

	private double phase1ActionValue(Phase1MulinoAction action) {
		// no perform
		return 0;
	}

	private double phase23ActionValue(Phase23MulinoAction action) {
		// sì perform
		return 0;
	}

	private double trisValue(MulinoState state, Position xy) {
		double value = 0;
		int dp = 0;
		int ep = 0;
		for(Position p : state.getRow(xy)) {
			if(!p.equals(xy)) {
			Checker c = state.getBoard().getChecker(p);
			if(c == state.getDutyPlayer())
				dp++;
			else if(c == state.getDutyPlayer())
				ep++;
			}
		}
		value = 
		return 0;
	}

}
