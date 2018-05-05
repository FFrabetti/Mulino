package mulino.strategy;

import java.util.List;
import java.util.Random;

import game.general.GameAction;
import game.general.GameState;
import game.mind.Strategy;
import game.mind.ThinkingStatus;
import mulino.MulinoState;

public class RandomStrategy extends Strategy {

	private Random rnd;
	
	public RandomStrategy() {
		rnd = new Random();
	}
	
	@Override
	public void chooseAction(GameState state) {
		MulinoState ms = (MulinoState)state;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<GameAction> actions = ms.legitActions();
		GameAction action = actions.get(rnd.nextInt(actions.size()));
				
		setThinkingStatus(new ThinkingStatus(action, state, null));
		
//		System.out.println("Immetti le coordinate dove vuoi inserire una pedina");
//		Scanner sc = new Scanner(System.in);
//		StringTokenizer st= new StringTokenizer(sc.nextLine());
//		int[] xy= {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
		
//		GameAction action = new Phase1MulinoAction(xy);
//		this.setThinkingStatus(new ThinkingStatus(action, state, null));
	}

}
