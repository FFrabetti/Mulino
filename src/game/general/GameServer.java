package game.general;

public abstract class GameServer {
	
	private GameFactory gameFactory;

	public GameFactory getGameFactory() {
		return gameFactory;
	}
	
	public void setGameFactory(GameFactory gameFactory) {
		this.gameFactory = gameFactory;
	}
	
	public abstract void playAction(GameAction action);
	
	public abstract GameState getCurrentState();

}
