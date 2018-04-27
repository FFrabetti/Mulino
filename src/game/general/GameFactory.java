package game.general;

import java.util.HashMap;
import java.util.Map;

import mulino.MulinoFactory;

public abstract class GameFactory<S,A> {

	private static final Map<String, GameFactory<?,?>> games = new HashMap<>();
	
	static {
		games.put("Mulino", new MulinoFactory());
	}
	
	public static <U,V> GameFactory<U,V> of(String gameName) {
//		if(!games.containsKey(gameName)) {
//			// reflection?
//			if("Mulino".equals(gameName))
//				games.put(gameName, new MulinoFactory());
//		}
		
		return (GameFactory<U,V>) games.get(gameName); // or null
	}
	
	// abstract methods:
	public abstract GameState fromState(S state);
	
	public abstract A toAction(GameAction action);

}
