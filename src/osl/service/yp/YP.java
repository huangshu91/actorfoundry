package osl.service.yp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import osl.handler.RequestClient;
import osl.handler.RequestException;
import osl.handler.RequestHandler;
import osl.handler.RequestID;
import osl.handler.RequestSession;
import osl.manager.ActorManagerName;
import osl.manager.ActorName;
import osl.manager.RemoteActorManager;
import osl.scheduler.Scheduler;
import osl.service.Service;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportLayer;
import osl.transport.TransportMessage;
import osl.transport.tcp.TCPAddress;
import osl.transport.tcp.TCPTransportLayer;
import osl.transport.udp.UDPAddress;
import osl.transport.udp.UDPTransportLayer;
import osl.util.QueueSearch;
import osl.util.WaitQueue;

/**
 * This is a simple YP service which allows local actors to look up the
 * addresses of remote actor managers by specifying an IP or hostname. The
 * manager name returned is always that returned by the YP service running on
 * the remote node. If there is no YP service then the service call will hang.
 * 
 * @author Mark Astley
 * @version $Revision: 1.10 $ ($Date: 1999/12/29 03:16:31 $)
 * @see osl.service.Service
 */

public class YP extends Service implements TransportClient, RequestClient {
	/**
	 * The name of this service. This field is static and final so that it may
	 * be referenced by all user-written actors which need access to this
	 * service. The name is represented as an anonymous class (cool dirty trick,
	 * yes?)
	 */
	public static final ServiceName name = new ServiceName() {
		/**
		 * This redefinition of toString lets us perform the equality check. Not
		 * real efficient but works.
		 */
		public String toString() {
			return "YP_SERVICE_NAME";
		}

		/**
		 * The equals function. Always returns true if compared with another
		 * name of
		 */
		public boolean equals(Object other) {
			return (other.toString().equals("YP_SERVICE_NAME"));
		}

		/**
		 * The hashcode function. Always returns the same (somewhat arbitrary)
		 * value.
		 */
		public int hashCode() {
			return 12345;
		}
	};

	/**
	 * The port number used by this service. This is specified by the
	 * initialization method for this service.
	 */
	public int port;

	/**
	 * This constant indicates a query YP message.
	 */
	public static final int YP_QUERY = 0;

	/**
	 * This constant indicates a response YP message.
	 */
	public static final int YP_RESPONSE = 1;

	/**
	 * The transport instance associated with this service.
	 */
	TransportInstance session = null;

	/**
	 * The request session instance associated with this service.
	 */
	RequestSession rSession = null;

	/**
	 * The name of our local actor manager. This is the value we return in
	 * response to queries.
	 */
	ActorManagerName localName = null;

	/**
	 * The ID of the next message sent by this service. This field is used to
	 * disambiguate multiple request messages.
	 */
	int nextID = 0;

	/**
	 * This queue holds replies to previously sent queries.
	 */
	WaitQueue<Object> requestReplies = null;

	/**
	 * The default constructor.
	 */
	public YP() {
		requestReplies = new WaitQueue<Object>();
		serviceRegisterMethod("ypLookupRemoteManager");
		serviceRegisterMethod("ypMapActorToManager");
	}

	/**
	 * Initialize this service instance.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 * @param <b>T</b> A reference to the transport layer which should be used
	 *        for interactions between nameservice instances.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error initializing this service.
	 */
	public void serviceInitialize(Scheduler S, RemoteActorManager M)
			throws ServiceException {
		// This version of the initialize function is required by the
		// Service interface but should never be called to start this
		// service. If it is then barf an exception.
		throw new ServiceException(
				"ERROR: This version of initialize should never be called!");
	}

	/**
	 * The REAL initializer for this service. This method is the required
	 * initializer in order for the YP service to work correctly.
	 * 
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error initializing this service.
	 */
	/*
	 * public void serviceInitialize(Scheduler S, RemoteActorManager M,
	 * UDPTransportLayer T, RequestHandler R, String P)
	 */
	public void serviceInitialize(Scheduler S, RemoteActorManager M,
			TransportLayer T, RequestHandler R, String P)
			throws ServiceException {
		try {
			// Try to parse the port argument first. If this doesn't work
			// then this service isn't going to be started
			port = (new Integer(P)).intValue();

			// Add ourselves as a service to the specified manager
			M.managerRegisterService(name, this);

			// We don't create any threads of our own so we can skip the
			// scheduler.
			// Save the name associated with our actor manager.
			localName = M.managerGetName();

			// Create a transport session instance. We need to submit our own
			// PhysicalAddress here so that we can listen on the correct port.
			PhysicalAddress ourAddr = null;
			if (T instanceof UDPTransportLayer)
				ourAddr = new UDPAddress(InetAddress.getLocalHost(), port);
			else if (T instanceof TCPTransportLayer)
				ourAddr = new TCPAddress(InetAddress.getLocalHost(), port);
			else
				throw new ServiceException(
						"Error: unsupported transport layer type: "
								+ T.getClass().getName());

			session = T.transportOpen(this, ourAddr);

			// Create a request session instance. We use this connection to
			// perform ActorName to ActorManagerName lookups.
			rSession = R.handlerOpenSession(this);

			// Done
		} catch (UnknownHostException e) {
			throw new ServiceException(
					"Error attempting to create physical address for YP service",
					e);
		} catch (RequestException e) {
			throw new ServiceException(
					"Error attempting to open request handler session for YP service",
					e);
		} catch (TransportException e) {
			throw new ServiceException(
					"Error attempting to open transport session for YP service",
					e);
		} catch (NumberFormatException e) {
			throw new ServiceException("Error reading port for YP service", e);
		}
	}

	/**
	 * Return the name of this service.
	 * 
	 * @return A <em>ServiceName</em> structure representing the name of this
	 *         service.
	 */
	public ServiceName serviceName() {
		return name;
	}

	/**
	 * Lookup the <em>ActorManagerName</em> of the foundry node running on a
	 * particular machine. At the moment, this method does the dumb thing and
	 * sends a request to the remote host everytime (rather than being a little
	 * smarter and caching previous lookups). I'll implement the cache later.
	 * 
	 * @param <b>toLookup</b> A <em>String</em> giving the host name or IP of
	 *        the node to lookup.
	 * @return The <b>ActorManagerName</b> of the manager running on node
	 *         <b>toLookup</b>. Note that because the UDP transport layer does
	 *         not currently detect node/link failure, a call to this function
	 *         may hang if no manager is running on the given node, or if there
	 *         is a communication failure while sending the message.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error invoking this service.
	 */
	public ActorManagerName ypLookupRemoteManager(String toLookup)
			throws ServiceException {
		PhysicalAddress target = null;
		YPMsg req = null;
		YPWaitSearch waiter = null;
		Object[] results = null;

		try {
			// PRAGMA [debug,osl.service.yp.YP] Log.println(FoundryStart.sysLog,
			// "<YP.ypLookupRemoteManager>: Building address to look up host: "
			// + toLookup);

			// Build an address to send the remote request to.
			target = new UDPAddress(InetAddress.getByName(toLookup), port);

			// Now build and send a request message. Block this thread
			// waiting for the response.

			// PRAGMA [debug,osl.service.yp.YP] Log.println(FoundryStart.sysLog,
			// "<YP.ypLookupRemoteManager>: sending lookup message");
			req = new YPMsg(YP_QUERY, nextID++, null);
			waiter = new YPWaitSearch(req.ID);
			synchronized (requestReplies) {
				session.transportSend(target, new TransportMessage(
						serializeMsg(req)));
				results = requestReplies.search(waiter);
				while (results.length == 0) {
					requestReplies.wait();
					results = requestReplies.search(waiter);
				}
			}

			// PRAGMA [debug,osl.service.yp.YP] Log.println(FoundryStart.sysLog,
			// "<YP.ypLookupRemoteManager>: result received, returning name");

			if (results.length != 1)
				throw new SecurityException(
						"Fatal Error: Received more than one reply to query!");

			// Ok, got the reply so remove it from the wait queue and return
			// the result to the original query thread.
			requestReplies.remove(results[0]);
			return ((YPMsg) results[0]).response;

		} catch (UnknownHostException e) {
			throw new ServiceException("Error: Unknown host " + toLookup, e);
		} catch (InterruptedException e) {
			throw new ServiceException(
					"Fatal Error: Waiting query thread should never be interrupted!",
					e);
		} catch (TransportException e) {
			throw new ServiceException(
					"Error: Transport exception while sending request", e);
		} catch (Exception e) {
			// Log.println("Error encountered in YP service method \"ypLookupRemoteManager\": "
			// + e + "\n");
			throw new ServiceException(
					"Error: Unexpected exception while sending request", e);
		}
	}

	/**
	 * Map an <em>ActorName</em> to the <em>ActorManagerName</em> of the actor
	 * currently managing the actor (according to the nameservice). Note that
	 * this information returned by this method may quickly become stale if the
	 * target actor migrates often.
	 * 
	 * @param <b>key</b> The <em>ActorName</em> of the actor to look up.
	 * @return The <b>ActorManagerName</b> of the manager currently managing the
	 *         actor with name <b>key</b>.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error invoking this method.
	 */
	public ActorManagerName ypMapActorToManager(ActorName key)
			throws ServiceException {
		try {
			return (ActorManagerName) rSession.handlerRPCRequest(key.getName(),
					"managerGetName");
		} catch (Exception e) {
			// Log.println("Error encountered in YP service method \"ypMapActorToManager\": "
			// + e + "\n");
			throw new ServiceException(
					"Error: Unexpected exception while sending request", e);
		}
	}

	// /////////////////////////////////////////////////////////////////
	// Serialization Methods
	// /////////////////////////////////////////////////////////////////

	/**
	 * A convenient function for serializing a message.
	 */
	public static byte[] serializeMsg(YPMsg src) throws IOException {
		ByteArrayOutputStream serializeArgStream = new ByteArrayOutputStream();
		ObjectOutputStream serializeOut = new ObjectOutputStream(
				serializeArgStream);
		serializeOut.writeObject(src);
		serializeOut.flush();

		return serializeArgStream.toByteArray();
	}

	/**
	 * A convenient function for deserializing a message.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error is encountered while reading the
	 *                input stream created for the deserialized message.
	 * @exception java.io.StreamCorruptedException
	 *                Thrown if the stream constructed for the deserialized
	 *                message is corrupted.
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if a class extracted from the message could not be
	 *                found.
	 * @exception java.io.OptionalDataException
	 *                Thrown if unexpected data appeared on the input stream
	 *                created for the deserialized message.
	 */
	public static YPMsg deserializeMsg(byte[] src) throws IOException,
			StreamCorruptedException, ClassNotFoundException,
			OptionalDataException {
		ByteArrayInputStream deserializeArgStream = new ByteArrayInputStream(
				src);
		ObjectInputStream serializeIn = new ObjectInputStream(
				deserializeArgStream);

		return (YPMsg) serializeIn.readObject();
	}

	// /////////////////////////////////////////////////////////////////
	// Required by TransportClient
	// /////////////////////////////////////////////////////////////////

	/**
	 * Called when the transport layer has received a new message on a
	 * connection associated with this client. If a client holds several
	 * connections it is their responsibility to demultiplex incoming messages
	 * as appropriate. The <b>target</b> field may be used to determine which
	 * connection this message is associated with.
	 * 
	 * @param <b>target</b> The <em>TransportInstance</em> reference that this
	 *        message is targeted for. By assumption, the client is the owner of
	 *        this instance.
	 * @param <b>msg</b> The <em>TransportMessage</em> that was received for
	 *        this connection.
	 */
	public void transportReceive(TransportInstance target, TransportMessage msg) {
		try {
			// First deserialize the incoming message. If it isn't a YPMsg
			// then throw an exception and ignore the request.
			YPMsg incoming = deserializeMsg(msg.contents);

			// What we do next depends on what type of message this is
			switch (incoming.type) {
			case YP_QUERY:
				// Build a response message with the name of our local manager
				YPMsg reply = new YPMsg(YP_RESPONSE, incoming.ID, localName);
				try {
					session.transportSend(msg.sender, new TransportMessage(
							serializeMsg(reply)));
				} catch (TransportException e) {
					throw new RuntimeException(
							"Error sending response message: " + e);
				}
				break;
			case YP_RESPONSE:
				// Just dump in our local wait queue. One of our response
				// threads should be waiting for this message.
				requestReplies.enqueue(incoming);
				break;
			default:
				throw new RuntimeException("Error: Unknown YP message type: "
						+ incoming.type);
			}
		} catch (Exception e) {
			// Log.println("<YP.transportReceive> Unexpected exception while receiving transport message: "
			// + e);
			// Log.logExceptionTrace(FoundryStart.sysLog, e);
			throw new RuntimeException(
					"Error: Unexpected exception while receiving transport message: "
							+ e);
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
		// stub for now
	}

	// /////////////////////////////////////////////////////////////////
	// Required by RequestClient
	// /////////////////////////////////////////////////////////////////
	/**
	 * This method is required by <em>RequestClient</em>, but should never
	 * actually be called since we only issue RPC requests through our request
	 * session. We log an error message if this method IS called.
	 */
	public void handlerException(RequestSession session, Exception except,
			RequestID id) {
		// Log.println(FoundryStart.sysLog,
		// "ERROR: Unexpected call to handlerException in YP service");
	}

	// /////////////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////////////

	/**
	 * This inner class is used to wakeup query threads when a new response is
	 * received from the transport layer.
	 */
	class YPWaitSearch implements QueueSearch {
		/**
		 * The ID of the original request message. The reply message for the
		 * original query will have an ID field that corresponds to the value.
		 */
		int ID;

		/**
		 * The constructor for this class is used to assign the ID of the
		 * message of which we are awaiting a reply.
		 */
		public YPWaitSearch(int query) {
			ID = query;
		}

		/**
		 * We match a message if it is an instance of <em>YPMsg</em> and its ID
		 * field is equal to ours.
		 */
		public boolean queueEvalPred(Object arg) {
			return ((arg instanceof YPMsg) && (ID == ((YPMsg) arg).ID));
		}
	}

}
