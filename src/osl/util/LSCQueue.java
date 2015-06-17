package osl.util;

public class LSCQueue<T> extends Queue<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8820664930257387904L;

	private Object[] theDisQ;
	private int frontDis, backDis;
	public static final int DISABLED_Q_INITIAL_SIZE = 5;

	public LSCQueue() {
		super();
		frontDis = backDis = 0;
	}

	public LSCQueue(int initialSize) {
		super(initialSize);
		frontDis = backDis = 0;
	}

	public void initDisabledQ() {
		theDisQ = new Object[DISABLED_Q_INITIAL_SIZE];
	}

	synchronized private void growDis() {
		Object[] newDisQ = new Object[2 * theDisQ.length];

		if (backDis >= frontDis) {
			System.arraycopy(theDisQ, frontDis, newDisQ, frontDis, backDis
					- frontDis + 1);
		} else {
			System.arraycopy(theDisQ, frontDis, newDisQ, frontDis,
					theDisQ.length - frontDis);
			System.arraycopy(theDisQ, 0, newDisQ, theDisQ.length, backDis);
			backDis = frontDis + theDisQ.length - 1;
		}

		theDisQ = newDisQ;
	}

	synchronized public boolean hasDisabledMessage() {
		return !(frontDis == backDis);
	}

	synchronized public void enqueueDisable(T q) {
		if (((backDis + 1) % theDisQ.length) == frontDis) {
			growDis();
		}
		theDisQ[backDis] = q;
		backDis = (backDis + 1) % theDisQ.length;
	}

	@SuppressWarnings("unchecked")
	synchronized public void flushDisables() {
		T toReturn;
		while (hasDisabledMessage()) {
			backDis = ((backDis - 1 + theDisQ.length) % theDisQ.length);
			toReturn = (T) theDisQ[backDis];
			insertAtFront(toReturn);
		}
	}

	private void insertAtFront(T q) {
		if (((front - 1 + theQ.length) % theQ.length) == back) {
			grow();
		}
		front = ((front - 1 + theQ.length) % theQ.length);
		theQ[front] = q;
	}

	synchronized public int numDisElements() {
		if (frontDis <= backDis)
			return (backDis - frontDis);
		else
			return (backDis + (theDisQ.length - frontDis));
	}
}
