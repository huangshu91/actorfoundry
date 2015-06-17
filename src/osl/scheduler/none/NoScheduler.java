package osl.scheduler.none;

import kilim.Task;
import osl.scheduler.Scheduler;

/**
 * This implementation of the <em>Scheduler</em> interface just relies on Java's
 * default thread scheduling mechanisms. In particular, the <em>schedule</em>
 * method does nothing and the <em>scheduleThread</em> method simply starts the
 * thread.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:53 $)
 */

public class NoScheduler implements Scheduler {

	/**
	 * The initialization function for the scheduler. This is called immediately
	 * after the scheduler is created but before it is started.
	 */
	public void schedulerInitialize(Boolean open) {
		// Nothing to do here
	}

	public void schedulerInitialize() {
		this.schedulerInitialize(false);
	}

	/**
	 * Just start the thread running here.
	 */
	public void scheduleThread(Thread toSched)
			throws IllegalThreadStateException {
		if (toSched.isAlive())
			throw new IllegalThreadStateException("thread already started!!!");
		else
			toSched.start();
	}

	public void scheduleThread(Task toSched) throws IllegalThreadStateException {
	}
}
