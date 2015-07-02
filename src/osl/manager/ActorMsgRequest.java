package osl.manager;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import osl.util.DeepCopy;

/**
 * This class packages a request to send a message. Instances of this class are
 * passed from actors to their managers in order to request a message send.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/01/19 18:43:32 $)
 * @see ActorManager
 * @see ActorRequest
 */

public class ActorMsgRequest extends ActorRequest {
	/**
	 * The sending actor's name.
	 */
	public ActorName sender;

	/**
	 * The receiving actor's name.
	 */
	public ActorName receiver;

	//TODO marker
	public static enum GC_TYPE {
		NA, GC, GCINV, NEW, STOP, BACKPROP, FINISH, INVACQ
	}
	
	//TODO marker
	public static enum MSG_COLOR {
		NA, NEW, OLD
	}
	
	//TODO marker
	public static long GENERATION = 0;
	
	//TODO marker
	public ActorMsgRequest.GC_TYPE msgtype = ActorMsgRequest.GC_TYPE.NA;
	public ActorMsgRequest.MSG_COLOR msgcolor = ActorMsgRequest.MSG_COLOR.NA;
	
	/**
	 * The method to be invoked on the receiver.
	 */
	public String method;

	/**
	 * The arguments to pass to the invoked method in the receiver. If no
	 * arguments are required then THIS SHOULD BE AN ARRAY OF LENGTH 0, rather
	 * than null.
	 */
	public Object[] methodArgs;

	/**
	 * Set to <b>true</b> if this message has blocked the sender waiting for a
	 * reply, <b>false</b> otherwise.
	 */
	public boolean RPCRequest;

	public boolean byCopy = false;

	/**
	 * The default constructor. Note that this <b>DOES NOT</b> construct a legal
	 * message.
	 */
	public ActorMsgRequest() {
		this(null, null, null, null, false);
	}

	/**
	 * The usual constructor that is used to build messages. This construct
	 * accepts values for each of the message fields, but sets the
	 * <em>RPCRequest</em> field to <b>false</b>.
	 */
	public ActorMsgRequest(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray) {
		this(theSender, theReceiver, newStr, newArray, false);
	}

	public ActorMsgRequest(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray, boolean rpc, boolean byCopy) {
		sender = theSender;
		receiver = theReceiver;
		method = newStr;
		methodArgs = newArray;
		RPCRequest = rpc;
		tags = null;
		this.byCopy = byCopy;
	}

	public void initialize() {
		initialize(null, null, null, null, false);
	}

	/**
	 * The usual constructor that is used to build messages. This construct
	 * accepts values for each of the message fields, but sets the
	 * <em>RPCRequest</em> field to <b>false</b>.
	 */
	public void initialize(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray) {
		initialize(theSender, theReceiver, newStr, newArray, false);
	}

	public void initialize(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray, boolean rpc, boolean byCopy) {
		sender = theSender;
		receiver = theReceiver;
		method = newStr;
		methodArgs = newArray;
		RPCRequest = rpc;
		tags = null;
		this.byCopy = byCopy;
	}

	public void initialize(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray, boolean rpc) {
		initialize(theSender, theReceiver, newStr, newArray, rpc, false);
	}

	/**
	 * The most general constructor. Allows every field except for the
	 * <em>tags hashtable to be set to an arbitrary value.
	 */
	public ActorMsgRequest(ActorName theSender, ActorName theReceiver,
			String newStr, Object[] newArray, boolean rpc) {
		this(theSender, theReceiver, newStr, newArray, rpc, false);
	}

	/**
	 * A useful method for debugging.
	 */
	public String toString() {
		return "<ActorMsgRequest: " + super.toString() + " sender=" + sender
				+ " receiver=" + receiver + " method=" + method + " numArgs="
				+ methodArgs.length + " rpc?=" + RPCRequest + ">";
	}

	/**
	 * Provide a "safe" clone of a message. A safe message clone is a regular
	 * clone of the original message with the exception that a deep copy is
	 * performed on the <em>methodArgs</em> field. This allows cloning to be
	 * used for handing a message off during local message passing. That is, the
	 * invariant is that the <em>methodArgs</em> structure must be fresh to
	 * avoid hidden channels between user actors.
	 * 
	 * @exception osl.manager.ActorRequestCloneException
	 *                Thrown as a wrapper for any error which occurs during
	 *                clone creation. Typically, this exception is thrown due to
	 *                a serialization error in the <tt>methodArgs</tt> field.
	 *                This field is serialized in order to create a deep copy.
	 *                We use a deep copy to prevent the creation of hidden
	 *                channels between actors sharing a reference to a common
	 *                object.
	 */
	public Object clone() throws ActorRequestCloneException {
		// PRAGMA [debug,osl.manager.ActorMsgRequest]
		// Log.println("<ActorMsgRequest.clone> Here 0...");

		// First create a regular cloned copy.
		ActorMsgRequest newCopy = (ActorMsgRequest) super.clone();

		// Now deep copy the methodArgs field.
		try {
			// PRAGMA [debug,osl.manager.ActorMsgRequest]
			// Log.println("<ActorMsgRequest.clone> Here 1...");
			// This dirty trick knocks several ms off the standard cloning time
			if (osl.foundry.FoundryStart.useNative) {
				// PRAGMA [debug,osl.manager.ActorMsgRequest]
				// Log.println("<ActorMsgRequest.clone> Here 2...");
				newCopy.methodArgs = new Object[methodArgs.length];
				for (int i = 0; i < methodArgs.length; i++)
					if ((methodArgs[i] != null)
							&& (DeepCopy.isMutable(methodArgs[i].getClass()
									.toString())))
						newCopy.methodArgs[i] = DeepCopy
								.deepCopy((Serializable) methodArgs[i]);
					else
						newCopy.methodArgs[i] = methodArgs[i];
			} else
				// PRAGMA [debug,osl.manager.ActorMsgRequest] {
				// PRAGMA [debug,osl.manager.ActorMsgRequest]
				// Log.println("<ActorMsgRequest.clone> About to clone methodArgs...");
				// PRAGMA [assert] Assert.assert(methodArgs != null,
				// "Method args should not be null here");

				newCopy.methodArgs = new Object[methodArgs.length];
			for (int i = 0; i < methodArgs.length; i++)
				if ((methodArgs[i] != null)
						&& (DeepCopy.isMutableClass(methodArgs[i].getClass())))
					newCopy.methodArgs[i] = DeepCopy
							.deepCopy((Serializable) methodArgs[i]);
				else
					newCopy.methodArgs[i] = methodArgs[i];

			// newCopy.methodArgs = (Object[]) DeepCopy.deepCopy(methodArgs);
			// PRAGMA [debug,osl.manager.ActorMsgRequest]
			// Log.println("<ActorMsgRequest.clone> Done cloning methodArgs...");
			// PRAGMA [debug,osl.manager.ActorMsgRequest] }
		} catch (IOException e) {
			// Serialization error for methodArgs
			throw new ActorRequestCloneException(
					"error serializing methodArgs in ActorMsgRequest", e);
		} catch (Exception e) {
			// Anything else is probably fatal but return it anyway since it
			// is probably an error in one of the arguments in the
			// methodArgs array.
			// Log.logExceptionTrace(e);
			throw new ActorRequestCloneException(
					"error cloning ActorMsgRequest", e);
		}

		// And return the clone.
		return newCopy;
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
		// Serialize parent first
		super.writeExternal(out);

		// Now serialize our fields
		sender.writeExternal(out);
		receiver.writeExternal(out);
		out.writeUTF(method);
		out.writeObject(methodArgs);
		out.writeBoolean(RPCRequest);
	}

	/**
	 * Deserialize into a new instnace of <em>UDPAddress</em> by reading from
	 * the given input stream.
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
		// Deserialize parent first
		super.readExternal(in);

		// Now serialize our fields
		sender = new ActorName(in);
		receiver = new ActorName(in);
		method = in.readUTF();
		methodArgs = (Object[]) in.readObject();
		RPCRequest = in.readBoolean();

	}

}
