package game.general;

import java.util.HashMap;
import java.util.Map;

import game.mind.Strategy;
import mulino.MulinoFactory;

public abstract class GameFactory {

	private static final Map<String, GameFactory> games = new HashMap<>();
	
	static {
		games.put("Mulino", new MulinoFactory());
	}
	
	public static GameFactory of(String gameName) {
//		if(!games.containsKey(gameName)) {
//			// reflection?
//			if("Mulino".equals(gameName))
//				games.put(gameName, new MulinoFactory());
//		}
		
		return games.get(gameName); // or null
	}
	
	// abstract methods:
	public abstract GameState fromState(Object state);
	
	public abstract Object toAction(GameAction action);

}
