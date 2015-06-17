package osl.nameservice.simple;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import osl.transport.PhysicalAddress;

/**
 * This class defines a protocol message that is used for interactions between
 * instances of the DefaultNameService. These messages are used for things like
 * registering bindings with the owners (i.e. creators) of names. The messages
 * themselves are sent through whatever transport layer instance happens to be
 * associated with the <em>DefaultNameService</em>.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/06/26 17:39:57 $)
 * @see DefaultNameService
 * @see DefaultName
 */

public class DefaultNameMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5684389380042905035L;
	/**
	 * These values indicate the purpose of this message.
	 */
	static final int DNMSG_UNKNOWN = -1;
	static final int DNMSG_BIND_REQUEST = 0;
	static final int DNMSG_RESOLVE = 1;
	static final int DNMSG_BIND_ACK = 2;
	static final int DNMSG_RESOLVE_REPLY = 3;
	static final int DNMSG_REMOVE_REQUEST = 4;
	static final int DNMSG_REMOVE_ACK = 5;

	/**
	 * The type of this message.
	 */
	int type;

	/**
	 * The name associated with a bind request, acknowledgement, resolve
	 * request, or remove request message.
	 */
	DefaultName target;

	/**
	 * In the case of a bind request or acknowledgement, this field uniquely
	 * identifies the request or ack relative to all other requests sent by the
	 * same client. This field serves a similar purpose for resolve/remove
	 * requests and replies.
	 */
	int bindID;

	/**
	 * In the case of a bind request, the address that should be bound with the
	 * target name. In the case of a resolve reply, this field returns the
	 * address associated with the target name. In the case of a remove request,
	 * this address should be equal to the address the receiver maintains
	 * authoritative information for.
	 */
	PhysicalAddress bind;

	/**
	 * This field holds an exception that may be associated with a particular
	 * request or reply. Note that this field IS NOT set by any of the
	 * constructors so that it always defaults to null.
	 */
	Exception error;

	/**
	 * The default constructor. This constructs a legal (but not very
	 * interesting) <em>DefaultNameMsg</em>.
	 */
	public DefaultNameMsg() {
		this(DNMSG_UNKNOWN, null, -1, null);
	}

	/**
	 * The full constructor.
	 */
	public DefaultNameMsg(int P, DefaultName T, int ID, PhysicalAddress B) {
		type = P;
		target = T;
		bindID = ID;
		bind = B;
		error = null;
	}

	/**
	 * The canonical <em>toString</em> method.
	 */
	public String toString() {
		return "DefaultNameMsg - type: " + type + " target: " + target
				+ " bind:" + bind;
	}

	/**
	 * This is just a convenient method for building a serialized form of a
	 * <em>DefaultNameMsg</em>. We use (messy) stack-allocated variables so that
	 * multiple serializations may take place concurrently (on different
	 * messages of course).
	 */
	static final byte[] serializeMsg(DefaultNameMsg M) throws IOException {
		ByteArrayOutputStream serializeArgStream = new ByteArrayOutputStream();
		ObjectOutputStream serializeOut = new ObjectOutputStream(
				serializeArgStream);
		serializeOut.writeObject(M);
		serializeOut.flush();
		return serializeArgStream.toByteArray();
	}

	/**
	 * This is just a convenient method for deserializing a
	 * <em>DefaultNameMsg</em> message from a byte stream. We use (messy)
	 * stack-allocated variables so that multiple serializations may take place
	 * concurrently (on different messages of course).
	 */
	static final DefaultNameMsg deserializeMsg(byte[] in) throws IOException,
			ClassNotFoundException {
		ByteArrayInputStream deserializeArgStream = new ByteArrayInputStream(in);
		ObjectInputStream serializeIn = new ObjectInputStream(
				deserializeArgStream);
		DefaultNameMsg newM = (DefaultNameMsg) serializeIn.readObject();
		return newM;
	}
}
