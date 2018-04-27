package mulino;

import java.io.IOException;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameServer;
import game.general.GameState;
import game.interaction.Diplomat;
import mulino.State.Checker;
import mulino.exception.MulinoServerException;

public class MulinoTCPServer extends GameServer{

	private Diplomat diplomat;
	private State.Checker checker;
	
	public MulinoTCPServer(Diplomat diplomat, State.Checker checker) {
		this.diplomat=diplomat;
		this.checker = checker;
		this.setGameFactory(GameFactory.of("Mulino"));
	}

	@Override
	public void playAction(GameAction action) {
		try {
			diplomat.write(this.getGameFactory().toAction(action));
		} catch (IOException e) {
			throw new MulinoServerException(e);
		}
	}

	@Override
	public GameState getCurrentState() {
		try {
			diplomat.read();
			
			MulinoState s = (MulinoState) getGameFactory().fromState(diplomat.read());
			s.setDutyPlayer(checker);
			return s;
		} catch (Exception e) {
			throw new MulinoServerException(e);
		}
	}

	public Checker getDutyPlayer() {
		return checker;
	}

}
