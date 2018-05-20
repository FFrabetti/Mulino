package mulino.strategy;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;
import game.general.GameAction;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.MulinoGame;
import mulino.MulinoState;
import mulino.ia.FunzioneEuristica;

public class MulinoIterativeAlphaBeta implements AdversarialSearch<MulinoState, GameAction> {

	private static final int NR_ITERATIONS = 1;
	
	private MulinoGame game;
	private FunzioneEuristica heuristic;
	private double utilMax;
	private double utilMin;
	private int currDepthLimit;
	private boolean heuristicEvaluationUsed; // indicates that non-terminal nodes have been evaluated.

	private GameAction currBestAction;
	
	public MulinoIterativeAlphaBeta(MulinoGame game, FunzioneEuristica heuristic) {
		this.game = game;
		this.heuristic = heuristic;
		this.utilMin = game.getLostValue();
		this.utilMax = game.getVictoryValue();
	}

	@Override
	public GameAction makeDecision(MulinoState state) {
		Checker player = game.getPlayer(state);
		ActionStore<GameAction> newResults = new ActionStore<>();
		for(GameAction a : game.getActions(state)) {
			newResults.add(a, heuristic.evalAction(state, a));
		}
		List<GameAction> actions = newResults.actions;
		
		System.out.println(actions);
		
		currDepthLimit = 0;
		do {
			incrementDepthLimit();
			heuristicEvaluationUsed = false;
			double alpha = Double.NEGATIVE_INFINITY;
			double beta = Double.POSITIVE_INFINITY;
			
			newResults = new ActionStore<>();
			for (GameAction action : actions) {
				double value = minValue(game.getResult(state, action), player, alpha, beta, 1);
				newResults.add(action, value);
				
				if(value > alpha) {
					alpha = value;
					System.out.println(action + " new alpha (topLV) = " + alpha);
					if(alpha>=utilMax) {
						System.out.println("trovata mossa vincente: " + action);
						break;
					}
				}
			}

			actions = newResults.actions; // stesse azioni, ma ordinate per valore
			currBestAction = actions.get(0);
			System.out.println("Current best action: " + currBestAction);
			
			// se la mossa migliore fa terminare la partita
			if (hasSafeWinner(newResults.utilValues.get(0)))
				break; // exit from iterative deepening loop
			else if (newResults.size() > 1
					&& isSignificantlyBetter(newResults.utilValues.get(0), newResults.utilValues.get(1)))
				break; // exit from iterative deepening loop

		} while (heuristicEvaluationUsed && currDepthLimit<=NR_ITERATIONS);
		
		return actions.get(0);
	}

	// returns an utility value
	public double maxValue(MulinoState state, Checker player, double alpha, double beta, int depth) {
		double result = 0;
		
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			result = eval(state, player);
		} else {
			double value = Double.NEGATIVE_INFINITY;
			for (GameAction action : game.getActions(state)) {
				value = Math.max(value, minValue(game.getResult(state, action), player, alpha, beta, depth + 1));
				if (value >= beta) {
					result = value;
					System.out.println("TAGLIO: val = " + result + " >= beta (" + beta + ")");
					break;
				}
				
				if(value > alpha) {
					alpha = value;
					System.out.println("new alpha = " + alpha);
				}
			}
			
			result = value;
		}
		
		System.out.println(state + " val = " + result);
		return result;
	}

	// returns an utility value
	public double minValue(MulinoState state, Checker player, double alpha, double beta, int depth) {
		double result = 0;
		
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			result = eval(state, player);
		} else {
			double value = Double.POSITIVE_INFINITY;
			for (GameAction action : game.getActions(state)) {
				value = Math.min(value, maxValue(game.getResult(state, action), player, alpha, beta, depth + 1));
				if (value <= alpha) {
					result = value;
					System.out.println("TAGLIO: val = " + result + " <= alpha (" + alpha + ")");
					break;
				}
				
				if(value < beta) {
					beta = value;
					System.out.println("new beta = " + beta);
				}
			}
			
			result = value;
		}
		
		System.out.println(state + " val = " + result);
		return result;
	}

	protected void incrementDepthLimit() {
		currDepthLimit++;
	}

	protected boolean isSignificantlyBetter(double newUtility, double utility) {
		return newUtility>=70 && utility<50;
	}

	protected boolean hasSafeWinner(double resultUtility) {
		return resultUtility <= utilMin || resultUtility >= utilMax;
	}

	protected double eval(MulinoState state, Checker player) {
		if (game.isTerminal(state)) {
			return game.getUtility(state, player);
		} else {
			heuristicEvaluationUsed = true;
			return heuristic.h(state)*(player==state.getDutyPlayer() ? 1 : -1); // (utilMin + utilMax) / 2; // TODO
		}
	}

	/**
	 * Orders actions by utility.
	 */
	private static class ActionStore<A> {
		private List<A> actions = new ArrayList<>();
		private List<Double> utilValues = new ArrayList<>();

		void add(A action, double utilValue) {
			int idx = 0;
			while (idx < actions.size() && utilValue <= utilValues.get(idx))
				idx++;
			actions.add(idx, action);
			utilValues.add(idx, utilValue);
		}

		int size() {
			return actions.size();
		}
	}

	@Override
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}
}