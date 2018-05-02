package mulino;

import java.io.IOException;
import java.net.Socket;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameServer;
import game.general.GameState;
import game.interaction.Diplomat;
import it.unibo.ai.didattica.mulino.actions.Action;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.exception.MulinoServerException;

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
			System.out.println("DEBUG:ho inviato l'azione");

		} catch (IOException e) {
			throw new MulinoServerException(e);
		}
	}

	@Override
	public GameState getCurrentState() { // waiting...
		try {
			diplomat.read(); // game state after our own move
			State state = (State) diplomat.read(); // after opponent move
			System.out.println("DEBUG:fatte 2 letture");

			// conversion
			MulinoState ms = (MulinoState) factory.fromState(state);
			ms.setDutyPlayer(checker); // it's our turn to play
			return ms;
			
		} catch (Exception e) {
			throw new MulinoServerException(e);
		}
	}
	
	//TODO da fare meglio: è molto simile alla getCurrentState ma con una sola lettura
	@Override
	public GameState getInitState() { 
		try {
			State state = (State) diplomat.read();
			// conversion
			MulinoState ms = (MulinoState) factory.fromState(state);
			ms.setDutyPlayer(checker); // it's our turn to play
			return ms;
			
		} catch (Exception e) {
			throw new MulinoServerException(e);
		}
	}

}
