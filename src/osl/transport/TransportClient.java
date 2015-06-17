package osl.transport;

/**
 * This interface defines the methods expected to be implemented by clients of a
 * <em>TransportLayer</em>. These methods are called by the transport layer
 * implementation when a new message is received for a particular connection.
 * <p>
 * 
 * @see TransportLayer
 * @see TransportInstance
 * @see PhysicalAddress
 * @see TransportMessage
 * @see TransportException
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/12/29 03:17:11 $)
 */

public interface TransportClient {

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
	public void transportReceive(TransportInstance target, TransportMessage msg);

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
			TransportMessage msg, TransportException error);
}
