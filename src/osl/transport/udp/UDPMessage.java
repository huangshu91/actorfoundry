package osl.transport.udp;

/**
 * This class is used to implement incoming or outgoing messages through an
 * instance of the UDPTransportLayer class. A message is simply an origin, a
 * destination, and an array of bytes which represents the raw (usually
 * serialized) message. These messages are stored in transport layer queues when
 * they need to be sent or when they are received from another transport object.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:23 $)
 */

public class UDPMessage {
	/**
	 * The address of the sender of this message.
	 */
	public UDPAddress sendAddress = null;

	/**
	 * The address of the receiver of this message.
	 */
	public UDPAddress destAddress = null;

	/**
	 * The raw byte stream associated with this message.
	 */
	public byte[] rawData = null;

	/**
	 * The default constructor. Note that this DOES NOT correspond to a legal
	 * message since everything is NULL. Trying to send this will cause the
	 * transport layer to blow chunks.
	 */
	public UDPMessage() {
		this(null, null, null);
	}

	/**
	 * The usual constructor for this class.
	 */
	public UDPMessage(UDPAddress sendA, UDPAddress destA, byte[] data) {
		sendAddress = sendA;
		destAddress = destA;
		rawData = data;
	}
}
