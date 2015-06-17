package osl.transport;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * This class defines messages that are used by transport layer implementations.
 * Instances of this class are really only manipulated by transport layer
 * clients. Thus, transport layer implementations need not be restricted by this
 * format. However, this class implements serializable so that transport layer
 * implementations, if they so desire, may directly send these structures to
 * remote nodes.
 * 
 * @see TransportLayer
 * @see TransportInstance
 * @see TransportClient
 * @see PhysicalAddress
 * @see TransportException
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/12/29 03:17:12 $)
 */

public class TransportMessage implements Externalizable {

	/**
	 * The physical address of the original sender of this message.
	 */
	public PhysicalAddress sender;

	/**
	 * The physical address of the original receiver of this message.
	 */
	public PhysicalAddress receiver;

	/**
	 * The contents of the message.
	 */
	public byte[] contents;

	/**
	 * The default constructor.
	 */
	public TransportMessage() {
		this(null, null, null);
	}

	/**
	 * The complete constructor.
	 */
	public TransportMessage(PhysicalAddress S, PhysicalAddress R, byte[] C) {
		sender = S;
		receiver = R;
		contents = C;
	}

	/**
	 * A convenient constructor for clients.
	 */
	public TransportMessage(byte[] C) {
		this(null, null, C);
	}

	// /////////////////////////////////////////////////
	// //// Externalizable Interface Functions
	// /////////////////////////////////////////////////

	/**
	 * Serialize the contents of this class to the output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to which we should write this
	 *        instance.
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(sender);
		out.writeObject(receiver);
		out.writeObject(contents);
	}

	/**
	 * Deserialize into a new instance of <em>TransportMessage</em> by reading
	 * from the given input stream.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> from which we should
	 *        deserialize this instance.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error is encountered while reading the
	 *                input stream.
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if a class being deserialized from the input stream
	 *                cannot be found by the class loader.
	 * @exception java.lang.ClassCastException
	 *                Thrown if a class deserialized from the input stream had
	 *                an unexpected type.
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		sender = (PhysicalAddress) in.readObject();
		receiver = (PhysicalAddress) in.readObject();
		contents = (byte[]) in.readObject();
	}

}
