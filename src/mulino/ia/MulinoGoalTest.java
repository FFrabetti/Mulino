package mulino.ia;

import aima.core.search.framework.problem.GoalTest;
import mulino.MulinoState;

public class MulinoGoalTest implements GoalTest{

	@Override
	public boolean isGoalState(Object state) {
		return ((MulinoState)state).isOver();
	}

}
