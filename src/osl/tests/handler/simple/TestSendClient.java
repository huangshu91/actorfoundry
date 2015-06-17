package osl.tests.handler.simple;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import osl.handler.RequestClient;
import osl.handler.RequestException;
import osl.handler.RequestHandler;
import osl.handler.RequestID;
import osl.handler.RequestSession;
import osl.nameservice.Name;
import osl.nameservice.simple.DefaultNameService;
import osl.scheduler.Scheduler;
import osl.scheduler.basic.BasicScheduler;
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
 * @version $Revision: 1.6 $ ($Date: 1999/12/29 03:16:45 $)
 */

public class TestSendClient implements Runnable, RequestClient, TransportClient {
	// Parameters for send test
	Scheduler scheduler = null;
	TransportLayer transport = null;
	DefaultNameService nameservice = null;
	RequestHandler handler = null;
	UDPAddress tAddr = null;
	RequestSession session = null;
	TransportInstance instance = null;
	Name remoteName = null;
	String host = null;

	// Call this to start the send portion of the example
	public static void main(String[] argv) {
		try {
			// Parse arguments
			if (argv.length != 3) {
				System.out
						.println("Usage: java TestSendClient <hostName><RcvrHost> <RcvrPort>");
				System.exit(1);
			}

			// Grab the host and port for communicating with the receiver
			InetAddress rHost = InetAddress.getByName(argv[1]);
			int rPort = (new Integer(argv[2])).intValue();

			// Create a scheduler and start it
			Scheduler theScheduler = new BasicScheduler();
			theScheduler.schedulerInitialize();

			// Finally, create an instance to run the test battery
			theScheduler.scheduleThread(new Thread(new TestSendClient(
					theScheduler, new UDPAddress(rHost, rPort), argv[0])));

		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	// Constructor
	public TestSendClient(Scheduler S, UDPAddress rAddr, String H) {
		scheduler = S;
		tAddr = rAddr;
		host = H;
	}

	// Call this to start the modules and send a request
	public void run() {
		try {

			int i;

			// Create the modules required for this test (except the
			// scheduler, that should already be created for us).
			transport = new UDPTransportLayer();
			nameservice = new DefaultNameService();
			handler = new RequestHandler();

			((UDPTransportLayer) transport)
					.transportInitialize(scheduler, host);
			nameservice.nsInitialize(scheduler, transport);
			handler.handlerInitialize(scheduler, transport, nameservice);

			// Create a handler session. We use this session for sending
			// outgoing requests. Note that the sending client never
			// receives any requests in this test battery.
			try {
				session = handler.handlerOpenSession(this);
			} catch (RequestException e) {
				System.out.println("Error opening connection: " + e);
				System.exit(-1);
			}

			// Now create a separate transport session which we use to
			// communicate directly with the receiving client. We use this
			// connection to obtain the DefaultName of the receiving client,
			// which is necessary before we can send any messages.
			try {
				instance = transport.transportOpen(this);
			} catch (TransportException e) {
				System.out.println("Error opening transport instance: " + e);
				System.exit(-1);
			}

			// Create a request message for the receivers DefaultName
			String req = "getName";
			ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			ObjectOutputStream serialOut = new ObjectOutputStream(streamOut);
			serialOut.writeUTF(req);
			serialOut.flush();

			// Now create and send the request message. We block until the
			// remoteName field is set (this is done automatically by the
			// thread which handles the reply to the request.
			synchronized (this) {
				while (remoteName == null) {
					System.out.print("Requesting receiver name...");
					TransportMessage toSend = new TransportMessage(streamOut
							.toByteArray());
					instance.transportSend(tAddr, toSend);
					this.wait();
				}
				System.out.println(remoteName.toString());
			}

			// Name field should be set, run the test battery:

			// -*- Test 1 -*-
			// Basic asynchronous method invocation with no method
			// arguments.
			System.out
					.print("Running test 1 (errors appear asynchronously)...");
			session.handlerRequest(remoteName, "test1");
			System.out.println("complete");

			// -*- Test 2 -*-
			// An RPC request with no method arguments
			System.out.print("Running test 2...");
			String res = (String) session
					.handlerRPCRequest(remoteName, "test2");
			if (res.equals("test2"))
				System.out.println("complete");
			else
				System.out.println("failed, expected \"test2\", received \""
						+ res + "\"");

			// -*- Test 3 -*-
			// Basic asynchronous method invocation with two arguments.
			System.out
					.print("Running test 3 (errors appear asynchronously)...");
			session.handlerRequest(remoteName, "test3", "test", session
					.handlerGetAddress());
			System.out.println("complete");

			// -*- Test 4 -*-
			// An RPC request with two arguments
			System.out.print("Running test 4...");
			Name othername = (Name) session.handlerRPCRequest(remoteName,
					"test4", "test", session.handlerGetAddress());
			if (othername.equals(remoteName))
				System.out.println("complete");
			else
				System.out.println("failed, expected " + remoteName
						+ ", received " + othername);

			// -*- Test 5 -*-
			// An asynchronous no such method error
			System.out
					.print("Running test 5 (this should cause an asynchronous NoSuchMethod error)...");
			session.handlerRequest(remoteName, "nomethod");
			System.out.println("complete");

			// -*- Test 6 -*-
			// An RPC no such method error
			System.out
					.print("Running test 6 - NoSuchMethod error should appear here ->");
			try {
				session.handlerRPCRequest(remoteName, "nomethod");
			} catch (Exception e) {
				// Catch the exception here so that the tests can continue
				System.out.println("Received handler exception: " + e);
			}
			System.out.println("complete");

			// -*- Test 7 -*-
			// An asynchronous receiver exception
			System.out
					.print("Running test 7 (this should cause an asynchronous RequestException error)...");
			session.handlerRequest(remoteName, "test7");
			System.out.println("complete");

			// -*- Test 8 -*-
			// An RPC receiver exception
			System.out
					.print("Running test 8 - RequestException error should appear here ->");
			try {
				session.handlerRPCRequest(remoteName, "test7");
			} catch (Exception e) {
				// Catch the exception here so that the tests can continue
				System.out.println("Received handler exception: " + e);
			}
			System.out.println("complete");

			// Spin indefinitely
			System.out.println("Tests completed");

			// -*- Test 9 -*-
			// RPC serialization test:
			// Send 1000 RPC's to verify that everything works under a
			// moderate load.
			System.out
					.print("Running test 9 - (1000 msg load test)...msg #    ");
			for (i = 0; i < 1000; i++) {
				char fourBack[] = { 8, 8, 8, 8 };
				String printBack = new String(fourBack);
				String statVal = "" + (i + 1);
				while (statVal.length() < 4)
					statVal = " " + statVal;
				System.out.print(printBack + statVal);

				res = (String) session.handlerRPCRequest(remoteName, "test2");
				if (!res.equals("test2")) {
					System.out
							.println(" failed, expected \"test2\", received \""
									+ res + "\"");
					break;
				}
			}
			System.out.println(" complete");

			while (true) {
				Thread.yield();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	// This is required for TransportClient interface. The only message
	// we should ever receive here is the name of the receiving client
	// for this test.
	public void transportReceive(TransportInstance to, TransportMessage msg) {
		try {
			// The contents of the message should be an instance of Name
			// holding the name of the remote client for this test.
			ByteArrayInputStream deserializeArgStream = new ByteArrayInputStream(
					msg.contents);
			ObjectInputStream serializeIn = new ObjectInputStream(
					deserializeArgStream);
			remoteName = (Name) serializeIn.readObject();

			// Once we have safely deserialized the name, wake the thread
			// waiting for it.
			synchronized (this) {
				this.notifyAll();
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

	// This required to implement RequestClient
	public void handlerException(RequestSession session, Exception except,
			RequestID id) {
		System.out.println("Received handler exception: " + except);
	}

}
