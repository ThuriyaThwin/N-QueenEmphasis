package util;

/**
 * Implements a thread with an additional flag indicating cancellation.
 *
 */
public class CancellableThread extends Thread {


	public CancellableThread(Runnable runnable) {
		super(runnable);
	}
	/**
	 * Returns <code>true</code> if the current thread is cancelled
	 * 
	 * @return <code>true</code> if the current thread is cancelled
	 */
	public static boolean currIsCancelled() {
		if (Thread.currentThread() instanceof CancellableThread)
			return ((CancellableThread) Thread.currentThread()).isCancelled;
		return false;
	}


	private volatile boolean isCancelled;

	/**
	 * Getter method.Returns true if this thread is cancelled
	 * 
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Setter method.Cancels this thread.
	 */

	public void cancel() {
		isCancelled = true;
	}
}
