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

	@Override
	public boolean isOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWinning() {
		// TODO Auto-generated method stub
		return false;
	}

}
