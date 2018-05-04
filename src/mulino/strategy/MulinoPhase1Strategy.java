package mulino.strategy;

import java.util.Random;

import game.general.GameState;
import game.mind.Strategy;
import game.mind.ThinkingStatus;
import mulino.MulinoState;
import mulino.Phase1MulinoAction;

public class MulinoPhase1Strategy extends Strategy {

	@Override
	public void chooseAction(GameState state) {
		// TODO Auto-generated method stub
		//begin to think
	
		MulinoState ms=(MulinoState)state;
		int rnd = new Random().nextInt(ms.legitActions().size());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Phase1MulinoAction action=(Phase1MulinoAction)ms.legitActions().get(rnd);
		if(action.getRemoveOpponent().isPresent()) {
			System.out.println("DEBUG:Mossa rimuove: "+action.getRemoveOpponent().get()[0]+","+action.getRemoveOpponent().get()[1]);
		}
		
		this.setThinkingStatus(new ThinkingStatus(action,state,null));
//		System.out.println("Immetti le coordinate dove vuoi inserire una pedina");
//		Scanner sc = new Scanner(System.in);
//		StringTokenizer st= new StringTokenizer(sc.nextLine());
//		int[] xy= {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
		
//		GameAction action = new Phase1MulinoAction(xy);
//		this.setThinkingStatus(new ThinkingStatus(action, state, null));
	}

}
