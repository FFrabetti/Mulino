package game.mind;

import game.general.GameAction;
import game.general.GameState;

public class ThinkingThread extends Thread {

	private GameState currentState;
	private StrategyFactory strategyFactory;
	private GameAction selectedAction;
	
	public ThinkingThread(StrategyFactory strategyFactory, GameState currentState) {
		this.currentState=currentState;
		this.strategyFactory=strategyFactory;
	}

	public GameAction getSelectedAction() {
		return selectedAction;
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("Thread " + t.getName() + ":ThinkingThread - incomincio a pensare");
		Strategy strategy =strategyFactory.selectStrategy(currentState);
		this.selectedAction = strategy.chooseAction(currentState);
		//to change...probably xD
		//se viene interrotto, quale selectedAction setta??
	}

}
