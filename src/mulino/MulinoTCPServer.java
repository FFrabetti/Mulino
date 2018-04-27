package mulino;

import java.io.IOException;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameServer;
import game.general.GameState;
import game.interaction.Diplomat;
import mulino.exception.MulinoServerException;
import mulino.shared.Action;
import mulino.shared.State;
import mulino.shared.State.Checker;

public class MulinoTCPServer implements GameServer {

	private State.Checker checker;
	private Diplomat diplomat;
	private GameFactory<State, Action> factory;
	
	public MulinoTCPServer(State.Checker checker, Diplomat diplomat) {
		this.checker = checker;
		this.diplomat = diplomat;
		
		factory = GameFactory.of("Mulino");
	}

	public Checker getChecker() {
		return checker;
	}
	
	@Override
	public void playAction(GameAction action) {
		try {
			diplomat.write(factory.toAction(action));
		} catch (IOException e) {
			throw new MulinoServerException(e);
		}
	}

	@Override
	public GameState getCurrentState() { // waiting...
		try {
			diplomat.read(); // game state after our own move
			State state = (State) diplomat.read(); // after opponent move
			
			// conversion
			MulinoState ms = (MulinoState) factory.fromState(state);
			ms.setDutyPlayer(checker); // it's our turn to play
			return ms;
			
		} catch (Exception e) {
			throw new MulinoServerException(e);
		}
	}

}
