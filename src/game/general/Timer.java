package game.general;

public class Timer extends Thread {
	private int seconds;

	public Timer(int seconds) {
		this.seconds = seconds;
	}
	
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("Thread " + t.getName() + ":Timer - inizio countdown");
		try {
			int cont=0;
			while (cont++ < seconds) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread " + t.getName() + ":Timer - tempo finito!");
	}
}
