package osl.scheduler.continuations;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import kilim.Task;
import osl.scheduler.Scheduler;
import osl.util.Queue;

public class ContinuationsBasedScheduler implements Scheduler, Runnable {

	public static final int INIT_WORKER_POOL_SIZE = 5;

	/**
	 * This queue holds the set of threads scheduled by the scheduler. It is a
	 * <em>NoSynchQueue</em>, which has no synchronized methods, to avoid the
	 * possibility of ever blocking the main scheduling thread.
	 */
	private Queue<Task> privateSchedule = new Queue<Task>(100);

	private Vector<WorkerThread> workerPool = null;

	private AtomicInteger barrierSemaphore = null;
	private boolean open;

	/**
	 * The initialization function for the scheduler. This is called immediately
	 * after the scheduler is created but before it is started.
	 */
	public void schedulerInitialize(Boolean open) {
		this.open = open;
		// Start a thread for ourselves. In order for this scheduler to
		// work, this has to be the highest priority thread in the virtual
		// machine.
		barrierSemaphore = new AtomicInteger(0);
		(new Thread(this, "MetaSchedulerThread")).start();
	}

	public void scheduleThread(final Task toSchedActor)
			throws IllegalThreadStateException {
		if (toSchedActor != null) {
			privateSchedule.enqueue(toSchedActor);
			if (workerPool == null) {
				workerPool = new Vector<WorkerThread>(2 * INIT_WORKER_POOL_SIZE);
				for (int i = 0; i < INIT_WORKER_POOL_SIZE; i++) {
					WorkerThread workerThread = new WorkerThread();
					workerPool.add(workerThread);
					workerThread.start();
				}
			}
		}
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
		// toSched.setPriority(mPri - 1);
		toSched.start();
	}

	/**
	 * This is the main scheduling loop. After startup tasks are completed, this
	 * routine is entered and remains active until the system is terminated.
	 * Within this procedure, each alive thread is given TIME_SLICE milliseconds
	 * to execute before it is pre-empted.
	 */
	public void run() {
		// The scheduling loop runs forever...
		while (true) {
			if (!open) {
				synchronized (barrierSemaphore) {
					synchronized (privateSchedule) {
						if (barrierSemaphore.get() == INIT_WORKER_POOL_SIZE
								&& privateSchedule.empty()) {
							System.out.println("Exiting...");
							System.exit(0);
						}
					}
				}
			}
			try {
				// enjoy the customary break
				Thread.sleep(250);
			} catch (InterruptedException e) {
				System.err
						.println("ERROR: MetaScheduler should never be interrupted!");
				System.exit(1);
			}
		}
	}

	class WorkerThread extends Thread {
		public void run() {
			// The scheduling loop runs forever...
			barrierSemaphore.incrementAndGet();

			while (true) {

				// Look for a new task to schedule off our private queue
				// If we find a task that has died, then remove it from the
				// schedule.

				barrierSemaphore.decrementAndGet();
				Object nextThread = privateSchedule.dequeue();
				if (nextThread != null) {
					Task toSchedActor = (Task) nextThread;
					toSchedActor._runExecute();
					barrierSemaphore.incrementAndGet();
				} else {
					barrierSemaphore.incrementAndGet();
					// If our scheduling queue is empty, pause for a few
					// milliseconds to allow other (perhaps blocked) threads to
					// execute.
					try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						System.err
								.println("ERROR: BasicScheduler should never be interrupted!");
						System.exit(1);
					}
				}
			}
		}
	}

	@Override
	public void schedulerInitialize() {
		this.schedulerInitialize(false);
	}
}
