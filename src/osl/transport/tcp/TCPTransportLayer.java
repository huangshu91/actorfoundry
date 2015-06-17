package osl.transport.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportLayer;
import osl.util.ParameterList;

/**
 * This class provides a TCP implementation of the TransportLayer interface.
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/07/13 02:01:51 $)
 * @see osl.transport.TransportLayer
 */

public class TCPTransportLayer implements TransportLayer {
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
	public TCPTransportLayer() {
		scheduler = null;
	}

	// /////////////////////////////////////////////////////////////
	// Methods which implement TransportLayer
	// /////////////////////////////////////////////////////////////

	/**
	 * Required transport initializer. This method is required by the transport
	 * layer interface. However, calling this initializer will generate a
	 * run-time exception.
	 */
	public void transportInitialize(Scheduler sysScheduler) {
		throw new RuntimeException(
				"Error: use osl.transport.tcp.TCPTransportLayer(Scheduler, String) for transport intialization");
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
		// Attempt to open a new server socket for the connection. If
		// request is non-null then attempt to use the user specified port
		// as well.
		ServerSocket sock = null;

		if (request == null)
			try {
				sock = new ServerSocket(0, 50, socketAddress);
			} catch (IOException e) {
				throw new TransportException("Unable to create socket", e);
			}
		else {
			if (request instanceof TCPAddress)
				try {
					sock = new ServerSocket(((TCPAddress) request).serverPort,
							0, socketAddress);
				} catch (IOException e) {
					throw new TransportException(
							"Unable to create socket with requested port "
									+ ((TCPAddress) request).serverPort, e);
				}
			else
				throw new TransportException(
						"Requested PhysicalAddress must be a TCPAddress");
		}

		// One more sanity check before we create the connection
		if (client == null)
			throw new TransportException(
					"Receiving client field must be non-null");

		// If we get here then we successfully created a socket so create
		// a transport instance to handle the new connection, register it
		// in our local hash table, and return the instance to the caller.
		TCPAddress receiver = null;
		receiver = new TCPAddress(socketAddress, sock.getLocalPort());
		TCPInstance session = new TCPInstance(scheduler, client, receiver, sock);
		session.init();

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
