package mulino.strategy;

import java.util.Random;

import game.general.GameState;
import game.mind.Strategy;
import game.mind.ThinkingStatus;
import mulino.MulinoState;
import mulino.Phase23MulinoAction;

public class MulinoPhase2Strategy extends Strategy {

	@Override
	public void chooseAction(GameState state) {
		// TODO Auto-generated method stub
		
		MulinoState ms=(MulinoState)state;
		int rnd = new Random().nextInt(ms.legitActions().size());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Phase23MulinoAction action=(Phase23MulinoAction)ms.legitActions().get(rnd);
		if(action.getRemoveOpponent().isPresent()) {
			System.out.println("DEBUG:Mossa rimuove: "+action.getRemoveOpponent().get()[0]+","+action.getRemoveOpponent().get()[1]);
		}
		
		this.setThinkingStatus(new ThinkingStatus(action,state,null));
	}

}
