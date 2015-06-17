package osl.transport.tcp;

import osl.transport.TransportClient;
import osl.transport.TransportMessage;

/**
 * This class defines a thread which is used to call <em>TransportClient</em>
 * instances when a new message has been received by the transport layer.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:19 $)
 * @see osl.transport.tcp.TCPInstance
 * @see osl.transport.TransportClient
 */

public class TCPDeliverThread implements Runnable {
	/**
	 * The transport instance which received the message. This field is passed
	 * to the transport client so it can figure out where the call came from.
	 */
	TCPInstance instance;

	/**
	 * The client which should be called to receive this message.
	 */
	TransportClient client;

	/**
	 * The message which should be delivered to the client.
	 */
	TransportMessage msg;

	/**
	 * The usual constructor used to build instances.
	 */
	TCPDeliverThread(TCPInstance I, TransportClient C, TransportMessage M) {
		instance = I;
		client = C;
		msg = M;
	}

	/**
	 * Default constructor.
	 */
	TCPDeliverThread() {
		this(null, null, null);
	}

	/**
	 * The run method which is called when this thread is started. We simply
	 * call the <em>transportReceive</em> method on the client and then exit.
	 */
	public void run() {
		client.transportReceive(instance, msg);
	}
}
