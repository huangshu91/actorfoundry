package osl.util;

/**
 * This class is a simple extension of the <em>Queue</em> class which uses the
 * <em>wait-notify</em> syntax to allow threads to wait on queue manipulation
 * and automatically be awoken when the queue has changed in size.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:31 $)
 */

public class WaitQueue<T> extends Queue<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5182693777868656606L;

	synchronized public void enqueue(T q) {
		super.enqueue(q);
		notifyAll();
	}

	synchronized public T dequeue() {
		T res = super.dequeue();
		notifyAll();
		return res;
	}

	synchronized public boolean remove(T rem) {
		boolean res = super.remove(rem);
		if (res)
			notifyAll();
		return res;
	}

	synchronized public int removeAll(T rem) {
		int numRem = super.removeAll(rem);
		if (numRem != 0)
			notifyAll();
		return numRem;
	}
}
