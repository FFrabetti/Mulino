package mulino.ia;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;
import mulino.MulinoAction;
import mulino.MulinoState;

public class MulinoResultFunction implements ResultFunction {

	@Override
	public Object result(Object state, Action action) {
		System.out.println((MulinoState)state);
		return ((MulinoAction)action).perform((MulinoState)state);
	}

}
