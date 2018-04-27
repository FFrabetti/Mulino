package game.mind;

import game.general.GameServer;
import game.general.GameState;

public abstract class Mind {

	private GameServer server;
	private StrategyFactory strategyFactory;
	private State state;		// internal mind state
	private GameState currentState;
	
	public Mind(GameServer server, StrategyFactory strategyFactory) {
		this.server = server;
		this.strategyFactory = strategyFactory;
		
		state = State.stateInit;
	}
	
	// starts the FSA
	public void start() {
		state.handle(this); // do set-up operations
		stateChanged(); // INIT -> PLAY|WAIT
	}
	
	public GameServer getGameServer() {
		return server;
	}
	
	public GameState getCurrentState() {
		return currentState;
	}
	
	public void setCurrentState(GameState state) {
		currentState = state;
	}
	
	public StrategyFactory getStrategyFactory() {
		return strategyFactory;
	}

	// 1: first to play, >1: wait for your turn
	public abstract int getGamePosition();
	
	private void stateChanged() {	
		while(state != State.stateEnd) {
			state = state.transition(this); // polymorphism!
			state.handle(this);
		}
	}
	
}
