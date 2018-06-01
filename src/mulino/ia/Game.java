package mulino.ia;

public interface Game<S, A, P> extends aima.core.search.adversarial.Game<S, A, P> {

	double getLostValue();

	double getVictoryValue();
	
}
