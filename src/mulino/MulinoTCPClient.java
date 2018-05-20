package mulino;

import java.io.IOException;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameClient;
import game.general.GameState;
import game.interaction.Diplomat;
import it.unibo.ai.didattica.mulino.actions.Action;
import it.unibo.ai.didattica.mulino.domain.State;
import it.unibo.ai.didattica.mulino.domain.State.Checker;
import mulino.exception.MulinoClientException;

public class MulinoTCPClient implements GameClient {

	private Checker checker;
	private Diplomat diplomat;
	private GameFactory<State, Action> factory;
	
	public MulinoTCPClient(Checker checker, Diplomat diplomat) {
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
//			System.out.println("DEBUG:ho inviato l'azione");

		} catch (IOException e) {
			throw new MulinoClientException(e);
		}
	}

	@Override
	public GameState getCurrentState() {
		try {
			State state = (State)diplomat.read(); // waiting for opponent move

			MulinoState ms = (MulinoState) factory.fromState(state);
			ms.setDutyPlayer(checker); // it's our turn to play

			return ms;
		} catch (Exception e) {
			throw new MulinoClientException(e);
		}
	}
	
	@Override
	public GameState getInitState() { 
//		GameState gameState = getCurrentState();
//		((MulinoState)gameState).setDutyPlayer(Checker.WHITE);
//		
//		return gameState;
		
		try {
			diplomat.read();			// posso evitare la conversione della factory
			return new MulinoState();	// stato iniziale: tocca al bianco e la board è vuota
		} catch (Exception e) {
			throw new MulinoClientException(e);
		}
	}

}
