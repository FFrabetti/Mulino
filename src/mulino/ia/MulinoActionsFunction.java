package mulino.ia;

import java.util.HashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import mulino.MulinoState;

public class MulinoActionsFunction implements ActionsFunction{

	@Override
	public Set<Action> actions(Object state) {
		Set<Action> actions = new HashSet<>();
		actions.addAll(((MulinoState)state).legitActions());
		return actions;
	}

}
