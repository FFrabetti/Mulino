package mulino;

import java.io.IOException;

import game.general.GameAction;
import game.general.GameFactory;
import game.general.GameServer;
import game.general.GameState;
import game.interaction.Diplomat;

public class MulinoTCPServer extends GameServer{

	private Diplomat diplomat;
	
	public MulinoTCPServer(Diplomat diplomat) {
		this.diplomat=diplomat;
		this.setGameFactory(GameFactory.of("Mulino"));
	}

	@Override
	public void playAction(GameAction action) throws IOException {
		diplomat.write(this.getGameFactory().toAction(action));
	}

	@Override
	public GameState getCurrentState() throws ClassNotFoundException, IOException {
		diplomat.read(); //basta cosi?
		return this.getGameFactory().fromState(diplomat.read());
	}

}
