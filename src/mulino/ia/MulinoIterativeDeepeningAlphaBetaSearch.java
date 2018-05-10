package mulino.ia;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.MulinoAction;
import mulino.MulinoState;

//classe appena abbozzata: bisogna fare in modo di tenere in memoria le azioni già calcolate
//ma soprattutto di settarle come thinking status

public class MulinoIterativeDeepeningAlphaBetaSearch extends IterativeDeepeningAlphaBetaSearch{

	public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
    public final static String METRICS_MAX_DEPTH = "maxDepth";

    protected Game<MulinoState, MulinoAction, Checker> game;
    protected double utilMax;
    protected double utilMin;
    protected int currDepthLimit;
    private boolean heuristicEvaluationUsed; // indicates that non-terminal
    // nodes
    // have been evaluated.
    private Timer timer;
    private boolean logEnabled;
    private ActionStore<MulinoAction> fromWaitToPlayActions;
    
	public MulinoIterativeDeepeningAlphaBetaSearch(Game game, double utilMin, double utilMax, int time) {
		super(game, utilMin, utilMax, time);
	}
		
	public int getCurrDepthLimit() {
		return currDepthLimit;
	}

	public ActionStore<MulinoAction> getFromWaitToPlayActions() {
		return fromWaitToPlayActions;
	}


	public MulinoAction makeDecision(MulinoState state) {
		//metrics = new Metrics();
		StringBuffer logText = null;
		Checker player = (Checker) game.getPlayer(state);
		List<MulinoAction> results = orderActions(state, game.getActions(state), player, 0);
		this.timer.start();
		currDepthLimit = 0;
		do {
			incrementDepthLimit();
			if (logEnabled)
				logText = new StringBuffer("depth " + currDepthLimit + ": ");
			heuristicEvaluationUsed = false;

			fromWaitToPlayActions = new ActionStore<MulinoAction>();
			for (MulinoAction action : results) {
				double value = minValue(game.getResult(state, action), player, Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY, 1);
				if (timer.timeOutOccurred())
					break; // exit from action loop
				fromWaitToPlayActions.add(action, value);
				
				if (logEnabled)
					logText.append(action + "->" + value + " ");
			}
			if (logEnabled)
				System.out.println(logText);
			if (fromWaitToPlayActions.size() > 0) {
				results = fromWaitToPlayActions.actions;
				if (!timer.timeOutOccurred()) {
					if (hasSafeWinner(fromWaitToPlayActions.utilValues.get(0)))
						break; // exit from iterative deepening loop
					else if (fromWaitToPlayActions.size() > 1
							&& isSignificantlyBetter(fromWaitToPlayActions.utilValues.get(0), fromWaitToPlayActions.utilValues.get(1)))
						break; // exit from iterative deepening loop
				}
			}
		} while (!timer.timeOutOccurred() && heuristicEvaluationUsed);
		return results.get(0);
	}
	
	private static class Timer {
        private long duration;
        private long startTime;

        Timer(int maxSeconds) {
            this.duration = 1000 * maxSeconds;
        }

        void start() {
            startTime = System.currentTimeMillis();
        }

        boolean timeOutOccurred() {
            return System.currentTimeMillis() > startTime + duration;
        }
    }

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
	
}
