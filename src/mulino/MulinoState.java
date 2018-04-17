package mulino;

import java.util.ArrayList;
import java.util.Collection;

import game.general.GameAction;
import game.general.GameState;

public class MulinoState extends GameState {

	@Override
	public Collection<GameAction> legitActions() {
		// TODO Auto-generated method stub
		
		Collection<GameAction> actions = new ArrayList<GameAction>();
		//actions.add(new MulinoAction());
		
		return actions;
	}

}
