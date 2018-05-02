package game.mind;

import game.general.GameState;

public class WaitingThread extends Thread {
	private Strategy strategy;
	private GameState currentState;
	
	public WaitingThread(StrategyFactory strategyFactory, GameState currentState) {
		this.currentState=currentState;
		this.strategy=strategyFactory.selectStrategy(currentState);
	}

	public ThinkingStatus getThinkingStatus() {
		return strategy.getThinkingStatus();
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("Thread " + t.getName() + ":WaitingThread - attendo...");
		strategy.chooseAction(currentState);
		System.out.println("DEBUG: lanciata la chooseAction");
	}

}
