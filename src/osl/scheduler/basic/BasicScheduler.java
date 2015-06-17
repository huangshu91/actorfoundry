package osl.scheduler.basic;

import kilim.Task;
import osl.scheduler.Scheduler;
import osl.util.Queue;

/**
 * This class implements a simple scheduler for use with foundry nodes. The
 * default behavior defined in this class is to schedule all foundry threads in
 * a round-robin fashion. This is to ensure fairness which, unfortunately, is
 * not required by the default Java thread scheduling mechanism.
 * <p>
 * 
 * A new scheduler is started by calling the <em>schedule</em> method defined
 * below. In order for scheduling to be effective, it is important that the main
 * scheduling thread is never blocked indefinitely. This can be guaranteed by
 * making the main scheduling thread the highest priority thread in the Java
 * virtual machine. If this priority is <b>mPri</b>, then each thread managed by
 * the scheduler has an initial priority of <b>mPri - 2</b>. When scheduling a
 * thread, the scheduler promotes a threads priority to <b>mPri -1</b> and
 * sleeps for <em>TIME_SLICE</em> milliseconds. Upon re-awakening the pre-empted
 * thread's priority is demoted, and a new thread is selected for execution.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1998/10/05 15:47:41 $)
 * @see osl.scheduler.Scheduler
 */

public class BasicScheduler implements Scheduler, Runnable {

	/**
	 * The time slice for each thread managed by the scheduler. This is
	 * specified in milliseconds.
	 */
	private static long TIME_SLICE;

	/**
	 * This queue holds the set of threads scheduled by the scheduler. It is a
	 * <em>NoSynchQueue</em>, which has no synchronized methods, to avoid the
	 * possibility of ever blocking the main scheduling thread.
	 */
	private Queue<Thread> privateSchedule = new Queue<Thread>();

	/**
	 * This is the priority at which the main scheduling loop runs. We set this
	 * field in <em>schedulerInitialize</em>. In particular, the main scheduling
	 * loop is run at the same priority of the thread which calls
	 * <em>schedulerInitialize</em>.
	 */
	private int mPri;

	/**
	 * The initialization function for the scheduler. This is called immediately
	 * after the scheduler is created but before it is started.
	 */
	public void schedulerInitialize(Boolean open) {
		// Should implement facility for terminating the Foundry if !open

		TIME_SLICE = 3;
		// Start a thread for ourselves. In order for this scheduler to
		// work, this has to be the highest priority thread in the virtual
		// machine.
		mPri = Thread.currentThread().getPriority();
		(new Thread(this)).start();
	}

	/**
	 * The initialization function for the scheduler. This is called immediately
	 * after the scheduler is created but before it is started.
	 * 
	 * @param <b>T</b> Time slice for each thread (default = 3).
	 */
	public void schedulerInitialize(String T) {
		TIME_SLICE = (new Long(T)).longValue();

		// Start a thread for ourselves. In order for this scheduler to
		// work, this has to be the highest priority thread in the virtual
		// machine.
		mPri = Thread.currentThread().getPriority();
		(new Thread(this)).start();
	}

	/**
	 * This method is used to submit a thread to be scheduled by the thread
	 * scheduler. The new thread MUST NOT be running yet. The thread scheduler
	 * calls start when it adds the thread to its schedule. An exception is
	 * raised if the thread has already been started.
	 */
	public void scheduleThread(Thread toSched)
			throws IllegalThreadStateException {
		if (toSched.isAlive())
			throw new IllegalThreadStateException("thread already started!!!");

		// Start the thread immediately so it doesn't have to wait for the
		// scheduler to get to it (forcing a new thread to wait here is
		// one of the main reasons for a slower basic scheduler).
		toSched.setPriority(mPri - 2);
		toSched.start();

		// Have to promote the current thread to make sure it successfully
		// gets through this loop
		Thread.currentThread().setPriority(mPri);
		privateSchedule.enqueue(toSched);
		Thread.currentThread().setPriority(mPri - 2);
	}

	public void scheduleThread(Task toSched) throws IllegalThreadStateException {
	}

	/**
	 * This is the main scheduling loop. After startup tasks are completed, this
	 * routine is entered and remains active until the system is terminated.
	 * Within this procedure, each alive thread is given TIME_SLICE milliseconds
	 * to execute before it is pre-empted.
	 */
	void schedule() {
		// The scheduling loop runs forever...
		while (true) {

			// Look for a new thread to schedule off our private queue
			// If we find a thread that has died, then remove it from the
			// schedule.
			if (!privateSchedule.empty()) {
				Thread nextThread = (Thread) privateSchedule.dequeue();
				int queueSize = privateSchedule.numElements();

				if ((queueSize >= 0) && (nextThread.isAlive())) {
					// Promote the new thread to execute
					nextThread.setPriority(mPri - 1);

					// To pre-empt ourselves. We wait on our private schedule
					// object. Note that this will never block us indefinitely
					// since we are the only one with a reference to this object
					try {
						synchronized (privateSchedule) {
							privateSchedule.wait(TIME_SLICE);
						}
					} catch (InterruptedException e) {
						System.err
								.println("ERROR: BasicScheduler should never be interrupted!");
						System.exit(1);
					}

					// De-mote the thread that just executed and put it on the
					// back of the queue. Note that if the thread is no longer
					// alive then it is never put back on the queue and should
					// eventually be GC'd.
					if (nextThread.isAlive()) {
						nextThread.setPriority(mPri - 2);
						privateSchedule.enqueue(nextThread);
					}
				}
			} else
				// If our scheduling queue is empty, pause for a few
				// milliseconds to allow other (perhaps blocked) threads to
				// execute.
				try {
					Thread.sleep(TIME_SLICE);
				} catch (InterruptedException e) {
					System.err
							.println("ERROR: BasicScheduler should never be interrupted!");
					System.exit(1);
				}
		}
	}

	/**
	 * This is just provided for convenience.
	 */
	public void run() {
		schedule();
	}

	@Override
	public void schedulerInitialize() {
		this.schedulerInitialize(false);
	}
}
