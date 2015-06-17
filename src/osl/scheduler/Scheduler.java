package osl.scheduler;

import kilim.Task;

/**
 * This interface defines the basic methods expected of a scheduler. A scheduler
 * is instantiated when a foundry node is first started and is used to schedule
 * all the threads associated with the node. Note that all schedulers must be
 * runnable, thus the extension below.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:52 $)
 */

public interface Scheduler {

	/**
	 * The initialization function for the scheduler. This is called immediately
	 * after the scheduler is created but before it is started.
	 */
	public void schedulerInitialize();

	public void schedulerInitialize(Boolean open);

	/**
	 * This method is used to submit a thread to be scheduled by the thread
	 * scheduler. The new thread MUST NOT be running yet. The thread scheduler
	 * calls start when it adds the thread to its schedule. An exception is
	 * raised if the thread has already been started.
	 */
	public void scheduleThread(Thread toSched)
			throws IllegalThreadStateException;

	public void scheduleThread(Task toSched) throws IllegalThreadStateException;

}
