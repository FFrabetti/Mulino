package mulino;

import game.general.GameAction;
import game.general.GameState;

public class MulinoAction extends GameAction {

	@Override
	public GameState perform(GameState currentState) {
		MulinoState oldMs= (MulinoState) currentState;
		// TODO Auto-generated method stub
		
		GameState newMs = new MulinoState();
		return newMs;
	}

}
