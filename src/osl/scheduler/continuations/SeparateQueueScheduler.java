package osl.scheduler.continuations;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import kilim.Task;
import osl.scheduler.Scheduler;
import osl.util.Queue;

public class SeparateQueueScheduler implements Scheduler, Runnable {
	public static final int INIT_WORKER_POOL_SIZE = 5;

	/**
	 * This queue holds the set of threads scheduled by the scheduler. It is a
	 * <em>NoSynchQueue</em>, which has no synchronized methods, to avoid the
	 * possibility of ever blocking the main scheduling thread.
	 */

	private int currentWorkerPoolSize = INIT_WORKER_POOL_SIZE;
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

	private AtomicInteger tasksNum = new AtomicInteger(0);

	private int nextWorkerThread = 0;

	public void scheduleThread(final Task toSchedActor)
			throws IllegalThreadStateException {
		if (toSchedActor != null) {
			tasksNum.incrementAndGet();
			if (workerPool == null) {
				workerPool = new Vector<WorkerThread>(2 * INIT_WORKER_POOL_SIZE);

				/*
				 * WorkerThread workerThread = new WorkerThread();
				 * workerThread.privateSchedule.enqueue(toSchedActor);
				 * workerThread.start(); nextWorkerThread = (nextWorkerThread +
				 * 1) % currentWorkerPoolSize; workerPool.add(workerThread);
				 */
				for (int i = 0; i < INIT_WORKER_POOL_SIZE; i++) {
					WorkerThread workerThread = new WorkerThread();
					workerThread.start();
					workerPool.add(workerThread);
				}
			}

			WorkerThread w = workerPool.get(nextWorkerThread);
			w.privateSchedule.enqueue(toSchedActor);
			synchronized (w.privateSchedule) {
				w.privateSchedule.notify();
			}
			nextWorkerThread = (nextWorkerThread + 1) % currentWorkerPoolSize;
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

	private int counter = 0;

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
					synchronized (tasksNum) {
						if (barrierSemaphore.get() == currentWorkerPoolSize
								&& tasksNum.get() == 0) {
							System.out.println("Exiting...");
							System.exit(0);
						}
					}
				}
			}
			try {
				counter++;

				if (counter == 20) {

					// test if any of the worker threads have made progress
					boolean progress = false;
					Iterator<WorkerThread> it = workerPool.iterator();
					while (it.hasNext()) {
						if (it.next().madeProgress()) {
							progress = true;
						}
					}

					// prevent starvation due to multiple non-cooperative actors
					// by expanding pool
					if (!progress && tasksNum.get() > 0) {
						WorkerThread workerThread = new WorkerThread();
						workerPool.add(workerThread);
						workerThread.start();
						currentWorkerPoolSize++;
					}
					counter = 0;
				}

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
		private AtomicInteger progress = new AtomicInteger(0);
		Queue<Task> privateSchedule = new Queue<Task>(10);

		public boolean madeProgress() {
			if (progress.get() > 0) {
				progress.set(0);
				return true;
			} else
				return false;
		}

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
					tasksNum.decrementAndGet();
					barrierSemaphore.incrementAndGet();
					progress.incrementAndGet();
				} else {
					barrierSemaphore.incrementAndGet();
					// If our scheduling queue is empty, pause for a few
					// milliseconds to allow other (perhaps blocked) threads to
					// execute.
					/*
					 * try { Thread.sleep(150); } catch (InterruptedException e)
					 * { System.err
					 * .println("ERROR: BasicScheduler should never be interrupted!"
					 * ); System.exit(1); }
					 */

					try {
						synchronized (privateSchedule) {
							privateSchedule.wait(100);
						}
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
