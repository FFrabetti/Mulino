package game.general;

import game.mind.AlertingThread;
import game.mind.WaitingQueue;

public class Timer extends AlertingThread {
	private int seconds;

	public Timer(WaitingQueue queue, int seconds) {
		super(queue);
		
		this.seconds = seconds;
	}
	
	public void run() {
//		Thread t = Thread.currentThread();
//		System.out.println("Thread " + t.getName() + ":Timer - inizio countdown");
		
		try {
//			int cont=0;
//			while (cont++ < seconds) {
//				Thread.sleep(1000);
//			}
			
			sleep(seconds*1000);
			alert(); // waking up the mind
			
//			System.out.println("Thread " + t.getName() + ":Timer - tempo finito!");	
			
		} catch (InterruptedException e) {
			//e.printStackTrace();
//			System.out.println("Mossa eseguita: timer fermato");
		}
	}
}
