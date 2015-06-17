package osl.transport;

import osl.scheduler.Scheduler;
import osl.util.ParameterList;

/**
 * This interface defines the main functions expected of the transport layer
 * component of the foundry. The transport layer is intended to be used solely
 * for off-node interactions. The actual protocol used to implement the layer
 * depends on the implementation provided for this interface. Currently, this
 * interface is designed as a "factory" which allows clients to initialize the
 * transport and open connections. The connections themselves are realized by
 * implementations of the <em>TransportInstance</em> interface. Instances of
 * this interface are returned by the <em>transportOpen</em> method below.
 * <p>
 * 
 * Naturally, it's virtually impossible to provide a clean interface which
 * encapsulates the thousands of protocols that may be used to handle off-node
 * connections. In an attempt to skirt these issues, the <em>transportOpen</em>
 * method, for example, may optionally be called with a parameter list that
 * specifies implementation-dependent configuration options. Knowledgeable
 * clients may use these options to customize specific implementations of the
 * interface. For example, in the <em>UDPTransport</em> implementation of the
 * interface, an unreliable connection may be opened by invoking
 * <em>transportOpen</em> as follows:
 * 
 * <center> <tt>params = new ParameterList(UDPTransport.MODE_UNRELIABLE);</tt><br>
 * <tt>connection = transport.transportOpen(params);</tt><br>
 * </center>
 * 
 * The appropriate method documentation should be consulted below for more
 * specific details.
 * <p>
 * 
 * @see TransportClient
 * @see TransportInstance
 * @see PhysicalAddress
 * @see TransportMessage
 * @see TransportException
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/07/13 02:01:50 $)
 */

public interface TransportLayer {

	/**
	 * Initialize the transport layer implementation using the specified
	 * scheduler to schedule transport threads. This method shold be called
	 * immediately after the transport layer has been instantiated, and before
	 * any other transport method. Module implementations are free to add
	 * additional initialization functions.
	 * 
	 * @param <b>sysScheduler</b> A reference to the <em>Scheduler</em> that is
	 *        being used to schedule all threads in the system.
	 */
	public void transportInitialize(Scheduler sysScheduler);

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
			throws TransportException;

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
			PhysicalAddress request) throws TransportException;

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
			ParameterList params) throws TransportException;

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
			throws TransportException;

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
			throws TransportException;

}
