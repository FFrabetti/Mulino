package game.mind;

public class AlertingThread extends Thread {

	private WaitingQueue queue;

	public AlertingThread(WaitingQueue queue) {
		this.queue = queue;
	}

	public void alert() {
		queue.signal();
	}

}
