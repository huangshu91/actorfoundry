package osl.transport.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportLayer;
import osl.util.ParameterList;

/**
 * This class implements a UDP implementation of the TransportLayer interface.
 * In the current version of this class, datagram packets are always PACKET_SIZE
 * bytes. Outoging data is automatically segmented into sequenced packets of
 * this size and reconstructed on the receiving end.
 * 
 * @author Mark Astley
 * @version $Revision: 1.8 $ ($Date: 1999/12/29 03:17:17 $)
 * @see osl.transport.TransportLayer
 */

public class UDPTransportLayer implements TransportLayer {
	/**
	 * Set these to true if you want debugging output.
	 */
	public static final boolean DEBUG = false;
	public static final boolean RAW_BYTE_DEBUG = false;

	/**
	 * transportOpen option which specifies that a new connection should be
	 * reliable. This is the default.
	 */
	public static final Integer MODE_RELIABLE = new Integer(1);

	/**
	 * transportOpen option which specifies that a new connection need not be
	 * reliable. Strictly speaking, "reliable" mode is not really reliable but
	 * rather best-effort reliable. That is, an attempt is made to deliver each
	 * message reliably but a timeout is used to determine when to give up and
	 * declare failure. In "unreliable" mode, each message is sent once and
	 * forgotten. It is up to the transport client to ensure that the message is
	 * delivered reliably.
	 */
	public static final Integer MODE_UNRELIABLE = new Integer(2);

	/**
	 * transportOpen/transportConfigure option which specifies the timeout for
	 * giving up on unacknowledged messages in "reliable" mode. The option
	 * should be followed by an Integer which specifies the timeout in
	 * milliseconds, or -1 for no timeout. -1 is the default.
	 */
	public static final Integer FAIL_TIMEOUT = new Integer(3);

	/**
	 * Message types for messages sent by the transport layer. These int values
	 * are always the first thing sent in a DatagramPacket to another transport
	 * object.
	 */
	static final int MESSAGE_TYPE_DATA = 0;
	static final int MESSAGE_TYPE_GC = 1;
	static final int MESSAGE_TYPE_ALIVE = 2;
	static final int MESSAGE_TYPE_NACK = 3;

	/**
	 * The sizes of each packet, the header portion of a data packet, and the
	 * raw data stored in a data packet. Note that PACKET_SIZE must always be
	 * greater than DATA_PACKET_HEADER_SIZE. The value for
	 * DATA_PACKET_HEADER_SIZE is based on the size of 5 serialized integers.
	 * Thus, this value is subject to change (i.e. if the Java language standard
	 * changes in the future).
	 */
	static final int DATA_PACKET_HEADER_SIZE = 26;
	static final int PACKET_SIZE = 1024;
	static final int RAW_DATA_SIZE = PACKET_SIZE - DATA_PACKET_HEADER_SIZE;

	/**
	 * This constant determines the delay between the sending of ALIVE messages.
	 * These messages are only sent when there are un-GCed local messages. In
	 * reponse to receiving an ALIVE message, the <em>UDPTransportLayer</em>
	 * either sends a NACK (if it missed a message) or sends a GC indicating
	 * that it got all the appropriate messages.
	 */
	public static final int SEND_ALIVE_TIMEOUT = 1000;

	/**
	 * This hashtable maintains the current set of connections being supported
	 * by the UDP transport layer. We hash <em>PhysicalAddresses</em> to the
	 * instances of <em>TransportClient</em> which are holding those addresses.
	 * We use this table to route incoming messages on ports we listen on.
	 */
	Hashtable<UDPAddress, UDPInstance> connections;

	/**
	 * The scheduler to be used for all transport layer threads.
	 */
	Scheduler scheduler;

	/**
	 * The InetAddress to use when opening new sockets. This address is created
	 * from the host name provided by the initialization method used to start
	 * the layer.
	 */
	InetAddress socketAddress;

	/**
	 * The default constructor for the <em>UDPTransportLayer</em> class. All we
	 * do is initialize the queues and create a socket for ourselves. If
	 * <em>portNum</em> is less than 0, then we allocate an arbitrary socket.
	 */
	public UDPTransportLayer() {
		connections = new Hashtable<UDPAddress, UDPInstance>();
		scheduler = null;
	}

	// /////////////////////////////////////////////////////////////
	// Methods which implement TransportLayer
	// /////////////////////////////////////////////////////////////

	/**
	 * Initializer required by TransportLayer. This initializer should never be
	 * used and will generate an error if called.
	 */
	public void transportInitialize(Scheduler S) {
		throw new RuntimeException(
				"Error: use transportInitialize(Scheduer, String) only!");
	}

	/**
	 * Initialize the transport layer implementation using the specified host
	 * name as the local address for connections. This method shold be called
	 * immediately after the transport layer has been instantiated.
	 * 
	 * @param <b>sysScheduler</b> A reference to the <em>Scheduler</em> that is
	 *        being used to schedule all threads in the system.
	 * @param <b>hostName</b> The name of the host to use for opening
	 *        connnections. This argument is used to build an
	 *        <em>InetAddress</em> for local connections.
	 */
	public void transportInitialize(Scheduler sysScheduler, String hostName) {
		scheduler = sysScheduler;
		try {
			socketAddress = InetAddress.getByName(hostName);
		} catch (UnknownHostException e) {
			throw new RuntimeException("Error initializing transport: " + e);
		}
	}

	/**
	 * Open a new connection in the transport layer. The specified <b>client</b>
	 * is used as the target for <em>TransportMessage</em>s received on the new
	 * connection.
	 * 
	 * @param <b>client</b> A reference to the <em>TransportClient</em> which
	 *        will receive messages on this connection.
	 * @return A <em>TransportInstance</em> which may be used to send messages
	 *         over the new connection.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem creating the new connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public TransportInstance transportOpen(TransportClient client)
			throws TransportException {
		return transportOpen(client, null, null);
	}

	/**
	 * Attempt to open a new connection with <b>request</b> as the
	 * <em>PhysicalAddress</em> of the requesting client.
	 * 
	 * @param <b>client</b> A reference to the <em>TransportClient</em> which
	 *        will receive messages on this connection.
	 * @param <b>request</b> The <em>PhysicalAddress</em> which the client
	 *        should be associated with.
	 * @return A <em>TransportInstance</em> which may be used to send messages
	 *         over the new connection.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem creating the new connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public TransportInstance transportOpen(TransportClient client,
			PhysicalAddress request) throws TransportException {
		return transportOpen(client, request, null);
	}

	/**
	 * Open a new connection in the transport layer using <b>param</b> as the
	 * set of configuration options for the connection.
	 * 
	 * @param <b>client</b> A reference to the <em>TransportClient</em> which
	 *        will receive messages on this connection.
	 * @param <b>params</b> A <em>ParameterList</em> instance which lists
	 *        parameters that should be used to customize the transport layer
	 *        for the new connection. The list of acceptable parameters is
	 *        implementation dependent.
	 * @return A <em>TransportInstance</em> which may be used to send messages
	 *         over the new connection.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem creating the new connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public TransportInstance transportOpen(TransportClient client,
			ParameterList params) throws TransportException {
		return transportOpen(client, null, params);
	}

	/**
	 * Attempt to open a new connection with <b>request</b> as the
	 * <em>PhysicalAddress</em> of the client. Use <b>params</b> as the set of
	 * configuration options for the new connection.
	 * 
	 * @param <b>client</b> A reference to the <em>TransportClient</em> which
	 *        will receive messages on this connection.
	 * @param <b>request</b> The <em>PhysicalAddress</em> which the client
	 *        should be associated with.
	 * @param <b>params</b> A <em>ParameterList</em> instance which lists
	 *        parameters that should be used to customize the transport layer
	 *        for the new connection. The list of acceptable parameters is
	 *        implementation dependent.
	 * @return A <em>TransportInstance</em> which may be used to send messages
	 *         over the new connection.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem creating the new connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public TransportInstance transportOpen(TransportClient client,
			PhysicalAddress request, ParameterList params)
			throws TransportException {
		// Attempt to open a new socket for the connection. If request is
		// non-null then attempt to use the user specified port as well.
		DatagramSocket sock = null;

		if (request == null)
			try {
				sock = new DatagramSocket(0, socketAddress);
			} catch (SocketException e) {
				throw new TransportException("Unable to create socket", e);
			}
		else {
			if (request instanceof UDPAddress)
				try {
					sock = new DatagramSocket(((UDPAddress) request).hostPort,
							socketAddress);
				} catch (SocketException e) {
					throw new TransportException(
							"Unable to create socket with requested port "
									+ ((UDPAddress) request).hostPort, e);
				}
			else
				throw new TransportException(
						"Requested PhysicalAddress must be a UDPAddress");
		}

		// One more sanity check before we create the connection
		if (client == null)
			throw new TransportException(
					"Receiving client field must be non-null");

		// If we get here then we successfully created a socket so create
		// a transport instance to handle the new connection, register it
		// in our local hash table, and return the instance to the caller.
		UDPAddress receiver = new UDPAddress(socketAddress, sock.getLocalPort());
		UDPInstance session = new UDPInstance(scheduler, client, receiver, sock);
		session.init();
		connections.put(receiver, session);

		return session;
	}

	/**
	 * Configure the transport layer according to the options specified in
	 * <b>params</b>. The options available and behavior of this method are
	 * implementation dependent. This method is intended to configure the
	 * transport layer as a whole. Specific connections may be configured either
	 * at creation or by calling <em>transportConfigure</em> in
	 * <em>TransportInstance</em>.
	 * 
	 * @param <b>params</b> A <em>ParameterList</em> instance which lists
	 *        parameters that should be used to customize the transport layer.
	 *        The list of acceptable parameters is implementation dependent.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem configuring the transport.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public void transportConfigure(ParameterList params)
			throws TransportException {
		// Current no options are supported here
	}

}
