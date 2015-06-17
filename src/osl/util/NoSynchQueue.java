package osl.util;

import java.io.Serializable;

/**
 * This class is equivalen to the Queue class except that nothing is
 * synchronized. Use this version with care as multiple threads accessing a
 * single instance of this class may conflict.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/09/01 02:39:23 $)
 */

public class NoSynchQueue implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2954918937185895921L;

	/**
	 * This constant sets the initial size of a queue when the default
	 * constructor is invoked to create a queue. Users requiring a different
	 * initial size may invoke the appropriate constructor below.
	 */
	public static final int INITIAL_SIZE = 10;

	/**
	 * This array holds the queue. We use a dynamically growing circular array
	 * to represent the queue. Queue size is doubled when necessary which should
	 * make for a rather efficient implementation.
	 */
	Object[] theQ;

	/**
	 * These two fields hold indices to the first and last element of the queue
	 * respectively. Technically, <em>back</em> points to the first open spot in
	 * the queue rather than the last element.
	 */
	int front;
	int back;

	/**
	 * Default constructor for the <em>Queue</em> class.
	 */
	public NoSynchQueue() {
		theQ = new Object[INITIAL_SIZE];
		front = back = 0;
	}

	/**
	 * Alternative constructor for the <em>Queue</em> class which allows the
	 * specification of the initial size of the queue.
	 * 
	 * @param <b>initialSize</b> The initial size of the new <em>Queue</em>.
	 */
	public NoSynchQueue(int initialSize) {
		theQ = new Object[initialSize];
		front = back = 0;
	}

	/**
	 * This private method is used to double the size of the queue when more
	 * space is required (as a result of calling enqueue). Note that because the
	 * implementation is in terms of a circular array, we have to be careful
	 * when copying elements from the old array to the new array.
	 */
	private void grow() {
		Object[] newQ = new Object[2 * theQ.length];

		if (back >= front)
			System.arraycopy(theQ, front, newQ, front, back - front + 1);
		else {
			System.arraycopy(theQ, front, newQ, front, theQ.length - front);
			System.arraycopy(theQ, 0, newQ, theQ.length, back);
			back = front + theQ.length - 1;
		}

		theQ = newQ;
	}

	/**
	 * Determine if the queue is empty.
	 * 
	 * @return <b>true</b> if the queue contains no elements, <b>false</b>
	 *         otherwise.
	 */
	public boolean empty() {
		return (front == back);
	}

	/**
	 * Add an object to the end of the queue.
	 * 
	 * @param <b>q</b> A reference to the object to add.
	 * @return <b>void</b>
	 */
	public void enqueue(Object q) {
		// First check to see if we need to grow
		if (((back + 1) % theQ.length) == front)
			grow();

		theQ[back] = q;
		back = (back + 1) % theQ.length;
	}

	/**
	 * Remove an object from the front of the queue. The returned object is
	 * removed from the queue.
	 * 
	 * @return A reference to the <b>Object</b> at the front of the queue.
	 */
	public Object dequeue() {
		Object toReturn = null;

		if (!empty()) {
			toReturn = theQ[front];
			front = (front + 1) % theQ.length;
		}

		return toReturn;
	}

	/**
	 * Get the object at the front of the queue without removing it.
	 * 
	 * @return A reference to the <b>Object</b> at the front of the queue.
	 */
	public Object peekFront() {
		if (!empty())
			return theQ[front];
		else
			return null;
	}

	/**
	 * Determine how many objects are currently stored in the queue.
	 * 
	 * @return An <b>int</b> indicating the number of objects in the queue.
	 */
	public int numElements() {
		if (front <= back) {
			return (back - front);
		} else {
			return (back + (theQ.length - front));
		}
	}
}
