package osl.tests.handler.simple;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import osl.handler.RequestException;
import osl.handler.RequestHandler;
import osl.handler.RequestID;
import osl.handler.RequestSession;
import osl.nameservice.Name;
import osl.nameservice.simple.DefaultNameService;
import osl.scheduler.Scheduler;
import osl.scheduler.basic.BasicScheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportLayer;
import osl.transport.TransportMessage;
import osl.transport.udp.UDPAddress;
import osl.transport.udp.UDPTransportLayer;

/**
 * A simple test of the request handler using the following services: 1. The
 * basic scheduler 2. The UDP implementation of the transport layer 3. The
 * simple implementation of the nameservice.
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1999/12/29 03:16:45 $)
 */

public class TestRcvClient implements Runnable, CustomRequestClient,
		TransportClient {
	// Parameters for send test
	Scheduler scheduler = null;
	TransportLayer transport = null;
	DefaultNameService nameservice = null;
	RequestHandler handler = null;
	PhysicalAddress me = null;
	RequestSession session = null;
	TransportInstance instance = null;
	Name myName = null;
	String host = null;

	// Call this to start the receive portion of the example
	public static void main(String[] argv) {
		try {
			// Parse arguments
			if (argv.length != 1) {
				System.out.println("Usage: java TestRcvClient hostname");
				System.exit(1);
			}

			// Create a scheduler and start it
			Scheduler theScheduler = new BasicScheduler();
			theScheduler.schedulerInitialize();

			// Now create an instance to run the tests
			theScheduler.scheduleThread(new Thread(new TestRcvClient(
					theScheduler, argv[0])));
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	// Constructor
	public TestRcvClient(Scheduler S, String H) {
		scheduler = S;
		host = H;
	}

	// Call this to start the transport and wait for messages
	public void run() {
		try {

			// Create the modules required for this test (except the
			// scheduler, that should already be created for us).
			transport = new UDPTransportLayer();
			nameservice = new DefaultNameService();
			handler = new RequestHandler();

			((UDPTransportLayer) transport)
					.transportInitialize(scheduler, host);
			nameservice.nsInitialize(scheduler, transport);
			handler.handlerInitialize(scheduler, transport, nameservice);

			// Create a handler session. We use this session to receive
			// incoming requests. Note that the receiving client never
			// sends any requests in this test battery.
			try {
				session = handler.handlerOpenSession(this);
			} catch (RequestException e) {
				System.out.println("Error opening connection: " + e);
				System.exit(-1);
			}

			// Now create a separate transport session which we use to
			// communicate directly with the sending client. In particular,
			// we need to accept request messages for the name which should
			// be used to communicate with us.
			try {
				instance = transport.transportOpen(this);
			} catch (TransportException e) {
				System.out.println("Error opening transport instance: " + e);
				System.exit(-1);
			}

			// Create a name which may be used to send us messages.
			// Register it with ourselves so we can receive these messages.
			myName = session.handlerGenerateName();
			session.handlerRegister(myName);

			// Now print out the address of our transport session so that we
			// can receive requests for our name.
			me = instance.transportGetAddress();
			System.out.println("Rcv address: " + ((UDPAddress) me).hostAddr);
			System.out.println("Rcv port   : " + ((UDPAddress) me).hostPort);

			// And just exit when we are finished. The transport layer and
			// request handler will provide all the threads we need for this
			// example.
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	// Receive a message from a remote client
	public void transportReceive(TransportInstance to, TransportMessage msg) {
		try {
			// Decode the message. If it is a name request, send back the
			// name which we may receive handler interactions on.
			ByteArrayInputStream deserializeArgStream = new ByteArrayInputStream(
					msg.contents);
			ObjectInputStream serializeIn = new ObjectInputStream(
					deserializeArgStream);
			String req = serializeIn.readUTF();

			if (req.equals("getName")) {
				// Create and send the reply message
				ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
				ObjectOutputStream serialOut = new ObjectOutputStream(streamOut);
				serialOut.writeObject(myName);
				serialOut.flush();
				TransportMessage toSend = new TransportMessage(streamOut
						.toByteArray());
				instance.transportSend(msg.sender, toSend);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	/**
	 * Called if the transport layer encounters an exception while attempting to
	 * send a previously queued message. If a client holds several connections
	 * it is their responsibility to demultiplex incoming messages as
	 * appropriate. The <b>target</b> field may be used to determine which
	 * connection this message is associated with.
	 * 
	 * @param <b>target</b> The <em>TransportInstance</em> reference that the
	 *        original message was sent from. By assumption, the client is the
	 *        owner of this instance.
	 * @param <b>msg</b> The original <em>TransportMessage</em> that was queued
	 *        to be sent to the remote host.
	 * @param <b>error</b> The <em>TransportException</em> which encapsulates
	 *        the error encountered during transmission.
	 */
	public void transportException(TransportInstance target,
			TransportMessage msg, TransportException error) {
		// just a stub for now
	}

	// Test methods
	public void handlerException(RequestSession session, Exception except,
			RequestID id) {
		// This should never happen since we don't send requests, but...
		System.out.println("Received request exception: " + except);
	}

	public void test1() {
		// Do nothing
		System.out.println("Test 1 activated");
	}

	public String test2() {
		// Return a simple string
		System.out.println("Test 2 activated");
		return "test2";
	}

	public void test3(String arg1, PhysicalAddress arg2) {
		// Do nothing
		System.out.println("Test 3 activated");
	}

	public Name test4(String arg1, PhysicalAddress arg2) {
		// Return our name
		System.out.println("Test 4 activated");
		return myName;
	}

	public Object test7() {
		// Throw an exception
		System.out.println("Test 7 activiated");
		String foo = null;
		foo.length();

		return null;
	}

}
