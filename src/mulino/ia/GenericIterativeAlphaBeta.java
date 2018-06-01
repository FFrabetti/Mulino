package mulino.ia;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;
import game.general.GameAction;
import game.general.GameState;
import game.mind.Strategy;

public class GenericIterativeAlphaBeta<S extends GameState,A extends GameAction,P> extends Strategy implements AdversarialSearch<S, A> {

	private int nrIterations;
	
	private Game<S,A,P> game;
	private HeuristicFunction<S,A> heuristic;
	private double utilMax;
	private double utilMin;
	private int currDepthLimit;
	private boolean heuristicEvaluationUsed; // indicates that non-terminal nodes have been evaluated.

	public GenericIterativeAlphaBeta(Game<S,A,P> game, HeuristicFunction<S,A> heuristic, int nrIterations) {
		this.game = game;
		this.heuristic = heuristic;
		this.utilMin = game.getLostValue();
		this.utilMax = game.getVictoryValue();
		this.nrIterations = nrIterations;
	}

	@Override
	public A makeDecision(S state) {
		P player = game.getPlayer(state);
		ActionStore<A> newResults = new ActionStore<>();
		for(A a : game.getActions(state)) {
			newResults.add(a, heuristic.evalAction(state, a));
		}
		List<A> actions = newResults.actions;
		
		currDepthLimit = 0;
		do {
			currDepthLimit++;
			heuristicEvaluationUsed = false;
			double alpha = Double.NEGATIVE_INFINITY;
			double beta = Double.POSITIVE_INFINITY;
			
			newResults = new ActionStore<>();
			for (A action : actions) {
				double value = minValue(game.getResult(state, action), player, alpha, beta, 1);
				newResults.add(action, value);
				
				if(value > alpha) {
					alpha = value;
					if(alpha>=utilMax) {
						break;
					}
				}
			}

			actions = newResults.actions; // stesse azioni, ma ordinate per valore
			setSelectedAction(actions.get(0));
			
			// se la mossa migliore fa terminare la partita
			if (hasSafeWinner(newResults.utilValues.get(0)))
				break; // exit from iterative deepening loop
			else if (newResults.size() > 1
					&& isSignificantlyBetter(newResults.utilValues.get(0), newResults.utilValues.get(1)))
				break; // exit from iterative deepening loop

		} while (heuristicEvaluationUsed && currDepthLimit<=nrIterations);
		
		return actions.get(0);
	}

	// returns an utility value
	public double maxValue(S state, P player, double alpha, double beta, int depth) {
		double result = 0;
		
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			result = eval(state, player);
		} else {
			double value = Double.NEGATIVE_INFINITY;
			for (A action : game.getActions(state)) {
				value = Math.max(value, minValue(game.getResult(state, action), player, alpha, beta, depth + 1));
				if (value >= beta) {
					result = value;
					break;
				}
				
				if(value > alpha) {
					alpha = value;
				}
			}
			
			result = value;
		}
		
		return result;
	}

	// returns an utility value
	public double minValue(S state, P player, double alpha, double beta, int depth) {
		double result = 0;
		
		if (game.isTerminal(state) || depth >= currDepthLimit) {
			result = eval(state, player);
		} else {
			double value = Double.POSITIVE_INFINITY;
			for (A action : game.getActions(state)) {
				value = Math.min(value, maxValue(game.getResult(state, action), player, alpha, beta, depth + 1));
				if (value <= alpha) {
					result = value;
					break;
				}
				
				if(value < beta) {
					beta = value;
				}
			}
			
			result = value;
		}
		
		return result;
	}

	protected boolean isSignificantlyBetter(double newUtility, double utility) {
		return newUtility>=70 && utility<50;
	}

	protected boolean hasSafeWinner(double resultUtility) {
		return resultUtility <= utilMin || resultUtility >= utilMax;
	}

	protected double eval(S state, P player) {
		if (game.isTerminal(state)) {
			return game.getUtility(state, player);
		} else {
			heuristicEvaluationUsed = true;
			return heuristic.h(state)*(player==game.getPlayer(state) ? 1 : -1); // (utilMin + utilMax) / 2; // TODO
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

	@Override
	public void chooseAction(GameState state) {
		GameAction bestAction = makeDecision((S)state);
		
		setSelectedAction(bestAction);
	}
}