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
		valori[0][0] = 1;
		valori[1][0] = 7;
		valori[0][1] = 3;
		valori[2][0] = 29;
		valori[0][2] = 15;
		valori[1][1] = 0;
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
				maxActionVal = phase1ActionValue(s, (Phase1MulinoAction)a);
				if(maxActionVal == 30)
					break;
			}
			value = maxActionVal; // in fase 1 un buono stato coincide con una buona mossa
		} else {
			for (GameAction a : s.legitActions()) {
				maxActionVal = phase23ActionValue(s, (Phase23MulinoAction)a);
				if(maxActionVal == 30)
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

		// controllo la riga
		for(Position p : state.getRow(action.getTo())) {
			if(!p.equals(action.getTo())) {
			Checker c = state.getBoard().getChecker(p);
			if(c == state.getDutyPlayer())
				dp++;
			else if(c == state.getDutyPlayer())
				ep++;
			}
		}
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		for(Position p : state.getColumn(action.getTo())) {
			if(!p.equals(action.getTo())) {
			Checker c = state.getBoard().getChecker(p);
			if(c == state.getDutyPlayer())
				dp++;
			else if(c == state.getDutyPlayer())
				ep++;
			}
		}
		val = val + valori[dp][ep];
		
		return val;
	}

	private int phase23ActionValue(MulinoState state, Phase23MulinoAction action) {
		int dp = 0;
		int ep = 0;
		int val = 0;

		// controllo la riga
		for(Position p : state.getRow(action.getTo())) {
			if(!p.equals(action.getTo()) && !p.equals(action.getFrom())) {
			Checker c = state.getBoard().getChecker(p);
			if(c == state.getDutyPlayer())
				dp++;
			else if(c == state.getDutyPlayer())
				ep++;
			}
		}
		val = val + valori[dp][ep];
		// controllo la colonna
		dp = 0;
		ep = 0;
		for(Position p : state.getColumn(action.getTo())) {
			if(!p.equals(action.getTo()) && !p.equals(action.getFrom())) {
			Checker c = state.getBoard().getChecker(p);
			if(c == state.getDutyPlayer())
				dp++;
			else if(c == state.getDutyPlayer())
				ep++;
			}
		}
		val = val + valori[dp][ep];
		
		return val;
	}

}
