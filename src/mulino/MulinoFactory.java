package mulino;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameState;
import game.mind.Strategy;
import mulino.strategy.MulinoPhase1Strategy;

public class MulinoFactory extends GameFactory {

	@Override
	public GameState fromState(Object state) {
		State s = (State)state;
		
		// TODO Auto-generated method stub
		return null; // MulinoState
	}

	@Override
	public Object toAction(GameAction action) {
		// MulinoAction ma = (MulinoAction)action;
		Action a = new Phase1Action();
		
		// TODO Auto-generated method stub
		return a;
	}

	@Override
	public Strategy getStrategy(GameState state) {
		// MulinoState ms = (MulinoState)state;
		Strategy strategy = new MulinoPhase1Strategy(); // flyweight?
		
		// TODO Auto-generated method stub
		return strategy;
	}

}
