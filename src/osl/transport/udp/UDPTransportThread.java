package osl.transport.udp;

/**
 * This class is a helper for running threads in the UDPInstance class. The
 * construct allows the specification of an instance in which the new thread
 * will run, as well as whether this thread will be the "send" thread or the
 * "rcv" thread.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:25 $)
 * @see osl.transport.udp.UDPInstance
 */

public class UDPTransportThread implements Runnable {
	/**
	 * This is the transport instance in which this thread will be executed.
	 */
	UDPInstance instance;

	/**
	 * Constants which indicate where this thread will run within a UDPInstance
	 * object.
	 */
	public static final int RUN_SEND_THREAD = 0;
	public static final int RUN_RCV_THREAD = 1;
	public static final int RUN_GC_THREAD = 2;

	/**
	 * Determines which routine this thread will run in when started on a
	 * UDPInstance object.
	 */
	int runWhere;

	/**
	 * The usual constructor used to build instances.
	 */
	public UDPTransportThread(UDPInstance o, int where) {
		instance = o;
		runWhere = where;
	}

	/**
	 * Default constructor.
	 */
	public UDPTransportThread() {
		this(null, RUN_SEND_THREAD);
	}

	/**
	 * The run method which is called when this thread is started. Pretty
	 * simple.
	 */
	public void run() {
		switch (runWhere) {
		case RUN_SEND_THREAD:
			instance.startSendThread();
			break;

		case RUN_RCV_THREAD:
			instance.startRcvThread();
			break;

		case RUN_GC_THREAD:
			instance.startGCThread();
			break;
		}
	}
}
