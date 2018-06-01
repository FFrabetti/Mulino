package mulino.ia;

public interface HeuristicFunction<S,A> extends aima.core.search.framework.evalfunc.HeuristicFunction {

	double evalAction(S state, A a);

}
