package game.general;

import java.io.IOException;

public abstract class GameServer {
	
	private GameFactory gameFactory;

	public GameFactory getGameFactory() {
		return gameFactory;
	}
	
	public void setGameFactory(GameFactory gameFactory) {
		this.gameFactory = gameFactory;
	}
	
	public abstract void playAction(GameAction action) throws IOException;
	
	public abstract GameState getCurrentState() throws ClassNotFoundException, IOException;


}
