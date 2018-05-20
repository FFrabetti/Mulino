package game.mind;

import game.general.GameAction;
import game.general.GameState;

public class ThinkingThread extends AlertingThread {

	private GameState currentState;
	private Strategy strategy;
	
	public ThinkingThread(WaitingQueue queue, StrategyFactory strategyFactory, GameState currentState) {
		super(queue);
		
		this.currentState=currentState;
		this.strategy=strategyFactory.selectStrategy(currentState);
	}
	
	public GameAction getSelectedAction() {
		return strategy.getSelectedAction();
	}
	
	public void run() {
//		Thread t = Thread.currentThread();
//		System.out.println("Thread " + t.getName() + ":ThinkingThread - incomincio a pensare");

		strategy.chooseAction(currentState);
		
		alert(); // wakes up the mind
	}

}
