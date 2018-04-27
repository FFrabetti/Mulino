package game.mind;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitingQueue {

	private Lock lock;
	private Condition cond;
	
	public WaitingQueue() {
		lock = new ReentrantLock();
		cond = lock.newCondition();
	}
	
	public void await() throws InterruptedException {
		lock.lock();
		try {
			cond.await();
		} finally {
			lock.unlock();
		}
	}
	
	public void signal() {
		lock.lock();
		try {
			cond.signal();
		} finally {
			lock.unlock();
		}
	}
}
