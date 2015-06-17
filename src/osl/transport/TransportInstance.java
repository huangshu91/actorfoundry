package osl.transport;

import osl.util.ParameterList;

/**
 * Instances of this interface are returned by the <em>TransportLayer</em>
 * implementation to represent connections. In particular, clients interact with
 * this interface to send messages over a connection as well as configure
 * aspects of the connection.
 * <p>
 * 
 * @see TransportLayer
 * @see TransportClient
 * @see PhysicalAddress
 * @see TransportMessage
 * @see TransportException
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:16 $)
 */

public interface TransportInstance {

	/**
	 * Obtain the physical address of this instance. This value may be passed in
	 * messages to allow other transport clients to communicate with this
	 * connection.
	 * 
	 * @return The <em>PhysicalAddress</em> by which this transport instance
	 *         receives messages.
	 */
	public PhysicalAddress transportGetAddress();

	/**
	 * Attempt to send a message over the connection. The sending address is the
	 * address associated with this transport instance. The <em>sender</em> and
	 * <em>receiver</em> fields are filled in automatically by this method.
	 * 
	 * @param <b>dest</b> The <em>PhysicalAddress</em> to which the message
	 *        should be sent.
	 * @param <b>msg</b> The <em>TransportMessage</em> instance to be sent.
	 * @exception osl.transport.TransportException
	 *                Thrown if an error is encountered while attempting the
	 *                send. The actual exception thrown is implementation
	 *                dependent and is encapsulated by this exception.
	 */
	public void transportSend(PhysicalAddress dest, TransportMessage msg)
			throws TransportException;

	/**
	 * Configure the transport layer relative to this connection according to
	 * the options specified in <b>params</b>. The options available and
	 * behavior of this method are implementation dependent.
	 * 
	 * @param <b>params</b> A <em>ParameterList</em> instance which lists
	 *        parameters that should be used to customize this connection. The
	 *        list of acceptable parameters is implementation dependent.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem configuring the connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public void transportConfigure(ParameterList params)
			throws TransportException;

}
