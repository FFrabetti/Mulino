package mulino.ia;

import aima.core.search.framework.problem.GoalTest;
import mulino.MulinoState;

public class MulinoGoalTest implements GoalTest{

	@Override
	public boolean isGoalState(Object state) {
		MulinoState s = (MulinoState)state;
//		System.out.println("---------------------------------------------------------");
//		System.out.println("DEBUG:fase="+s.getCurrentPhase());
//		System.out.println("DEBUG: W=" + s.getBoard().checkers(Checker.WHITE) + " B=" + s.getBoard().checkers(Checker.BLACK));
//		System.out.println("DEBUG: isOver " + ((MulinoState)state).isOver());
		return ((MulinoState)state).isOver();
	}

}
