package osl.handler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import osl.nameservice.Name;
import osl.transport.PhysicalAddress;

/**
 * This class encodes a message sent between request handler sessions. The
 * fields in this class indicate whether or not a message represents a request,
 * a reply to a request (e.g. for RPC requests), or an exception caused by a
 * request which should be delivered back to the calling client. Custom
 * serialization routines are implemented for efficiency purposes. Note that the
 * only information we include about the parties involved in the request is the
 * <em>Name</em> of the sender. The reason is that request messages are
 * transmitted via <em>TransportMessage</em>s, which already include sender and
 * receiver <em>PhysicalAddress</em>es. Including that information here would
 * result in transmitting (and worse, serializing) duplicate information.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1998/10/05 15:47:36 $)
 * @see RequestHandler
 */

public class RequestMsg implements Externalizable {
	/**
	 * Indicates an unknown message type. These are simply dumped by the request
	 * handler.
	 */
	static final int MTYPE_UNKNOWN = 0;

	/**
	 * Indicates that a message is a reply to an earlier RPC request.
	 */
	static final int MTYPE_RPC_REPLY = 1;

	/**
	 * Indicates that a message is an asynchronous request.
	 */
	static final int MTYPE_REQUEST = 2;

	/**
	 * Indicates that a message is a synchronous (i.e. RPC) request. The caller
	 * is blocked awaiting a reply.
	 */
	static final int MTYPE_RPC_REQUEST = 3;

	/**
	 * Indicates that a message is an exception being returned from an
	 * asynchronous request.
	 */
	static final int MTYPE_EXCEPTION = 4;

	/**
	 * Indicates that a message is an exception being returned from an RPC
	 * request.
	 */
	static final int MTYPE_RPC_EXCEPTION = 5;

	/**
	 * Used to indicate that a range of <em>RequestID</em>s are no longer valid
	 * (i.e. the original requests have successfully completed.
	 */
	static final int MTYPE_GC_REQUESTIDS = 6;

	/**
	 * Used to request the class data for a local class. A message of this type
	 * is received when a previous message we have sent contained a class
	 * unknown to the receiver.
	 */
	static final int MTYPE_REQUEST_CDATA = 7;

	/**
	 * Used to reply to a previous request for class data.
	 */
	static final int MTYPE_REPLY_CDATA = 8;

	/**
	 * The <em>PhysicalAddress</em> of the original sender of this message. We
	 * need this data so that replies are correctly routed in cases where a
	 * request arrives at the wrong target and needs to be forwarded to another
	 * request session.
	 */
	PhysicalAddress msgOrigSender = null;

	/**
	 * The name of the target of this message. We need this data for maintaining
	 * consistency when a name is removed from the nameservice.
	 */
	Name msgDestName = null;

	/**
	 * The type of this message.
	 */
	int type;

	/**
	 * The ID for this message. This value should be unique relative to the
	 * sender.
	 */
	long id;

	/**
	 * If this is an RPC reply, then this field holds the ID of the message to
	 * which this message serves as a reply. If this is an EXCEPTION, then this
	 * field holds the ID of the original request message. If this is a
	 * REPLY_CDATA, then this field holds the ID of the original REQUEST_CDATA
	 * message.
	 */
	long srcID;

	/**
	 * If this is an RPC reply, then this field is a reference to the object
	 * returned from the call. If this is a message of type EXCEPTION then this
	 * field is a reference to the exception object.
	 */
	Object returnVal;

	/**
	 * If this is a REQUEST or RPC_REQUEST, then this field holds the name of
	 * the method to invoke on the target actor manager.
	 */
	String targetMethod;

	/**
	 * If this is a REQUEST or RPC_REQUEST, then this field holds an array of
	 * arguments to pass to the invoked method on the target actor manager.
	 */
	Object[] args;

	/**
	 * If this is a GC_REQUESTIDS message, then this is an array of all the
	 * request IDs that may be GC'd.
	 */
	long[] gcReqs;

	/**
	 * If this is a REQUEST_CDATA message, then this field contains the name of
	 * the class being requested.
	 */
	String className;

	/**
	 * If this is a REPLY_CDATA message, then this field contains the class data
	 * for a previously requested class.
	 */
	byte[] classData;

	/**
	 * This is the full version of the <em>RequestMsg</em> constructor.
	 * 
	 * @param <b>oSend</b> The <em>PhysicalAddress</em> of the original sender
	 *        of this message. We need this data so that replies are correctly
	 *        routed in the case of forwarded requests.
	 * @param <b>dName</b> The <em>Name</em> of the receiver.
	 * @param <b>theType</b> The <em>int</em> type of this message.
	 * @param <b>theId</b> The <em>int</em> ID for this message. Should be
	 *        unique relative to the sender.
	 * @param <b>theSrcID</b> In the case of an RPC reply, the <em>int</em> id
	 *        of the original request message.
	 * @param <b>theRVal</b> In the case of an RPC reply, the <em>Object</em>
	 *        which holds the returned value. In the case of an exception, the
	 *        <em>Exception</em> which was returned by the receiver. This will
	 *        either be a <em>RemoteException</em> or a
	 *        <em>NoSuchMethodException</em>.
	 * @param <b>target</b> The <em>String</em> naming the method to invoke on
	 *        the receiver.
	 * @param <b>theArgs</b> An object array (i.e. <em>Object[]</em>) giving the
	 *        arguments to pass to the target method.
	 */
	public RequestMsg(PhysicalAddress oSend, Name dName, int theType,
			long theId, long theSrcID, Object theRVal, String target,
			Object[] theArgs, long[] rGC, String cName, byte[] cData) {
		msgOrigSender = oSend;
		msgDestName = dName;
		type = theType;
		id = theId;
		srcID = theSrcID;
		returnVal = theRVal;
		targetMethod = target;
		args = theArgs;
		gcReqs = rGC;
		className = cName;
		classData = cData;
	}

	/**
	 * This constructor useful for building REQUESTS or RPC_REQUESTS.
	 * 
	 * @param <b>oSender</b> The <em>PhysicalAddress</em> of the original sender
	 *        of the message.
	 * @param <b>dest</b> The <em>Name</em> of the receiver.
	 * @param <b>type</b> The type of the message. Will usually be either
	 *        <tt>MTYPE_REQUEST</tt> or <tt>MTYPE_RPC_REQUEST</tt>.
	 * @param <b>targMeth</b> A <em>String</em> naming the method to invoke on
	 *        the target.
	 * @param <b>theArgs</b> The object array holding the arguments to pass to
	 *        the target method.
	 * @param <b>theId</b> The integer ID of this message. Should be unique
	 *        relative to the sender.
	 */
	public RequestMsg(PhysicalAddress oSender, Name dest, int type,
			String targMeth, Object[] theArgs, long theID) {
		this(oSender, dest, type, theID, 0, null, targMeth, theArgs, null,
				null, null);
	}

	/**
	 * This constructor useful for building a reply to an RPC request or for
	 * returning an exception given the original request message, the id of the
	 * reply, and the value to send as the reply.
	 * 
	 * @param <b>rVal</b> The object to send as the return value for the
	 *        original request.
	 * @param <b>msgID</b> The id of the reply message. Should be unique
	 *        relative to the sender.
	 * @param <b>mType</b> The type of reply: either MTYPE_RPC_REPLY or
	 *        MTYPE_RPC_EXCEPTION.
	 * @param <b>oReq</b> The original request message.
	 */
	public RequestMsg(Object rVal, long msgID, int mType, RequestMsg oReq) {
		this(null, null, mType, msgID, oReq.id, rVal, null, null, null, null,
				null);
	}

	/**
	 * The default constructor sets the message type as UNKNOWN with no id and
	 * no return value. Note that this is not a legal message.
	 */
	public RequestMsg() {
		this(null, null, -1, -1, -1, null, null, null, null, null, null);
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
	public void writeExternal(ObjectOutput out) throws IOException,
			RequestException {
		// First things first, write out the sender and receiver info, the
		// type of the message, and its ID
		out.writeObject(msgOrigSender);
		out.writeObject(msgDestName);
		out.writeByte(type);
		out.writeLong(id);

		// What we do now depends on the type of message we are. We try
		// to minimize the amount of data we write out here for efficiency
		// purposes.
		switch (type) {
		case MTYPE_UNKNOWN:
			// Don't need to serialize anything else here as all the fields
			// are meaningless for a message of unknown type. These
			// messages should never be sent.
			break;

		case MTYPE_RPC_REPLY:
		case MTYPE_RPC_EXCEPTION:
			// RPC replies and exceptions can be handled the same way: just
			// serialize srcID and returnVal
			out.writeLong(srcID);
			out.writeObject(returnVal);
			break;

		case MTYPE_EXCEPTION:
			// Regular exceptions write practically the entire message
			// structure.
			out.writeLong(srcID);
			out.writeObject(returnVal);
			out.writeUTF(targetMethod);
			out.writeObject(args);
			break;

		case MTYPE_REQUEST:
		case MTYPE_RPC_REQUEST:
			// Both types of requests are handled equivalently. Write out
			// the target method and the args array.
			out.writeUTF(targetMethod);
			out.writeObject(args);
			break;

		case MTYPE_GC_REQUESTIDS:
			// Ok, just write out the id array
			out.writeObject(gcReqs);
			break;

		case MTYPE_REQUEST_CDATA:
			// Only need to write out the requested class name
			out.writeObject(className);
			break;

		case MTYPE_REPLY_CDATA:
			// Need to write our srcID and classData
			out.writeLong(srcID);
			out.writeObject(classData);
			break;

		default:
			throw new RequestException("Unknown message type " + type
					+ ": message is corrupted");
		}

	}

	/**
	 * Deserialize into a new instnace of <em>RequestMesg</em> by reading from
	 * the given input stream.
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
	 * @exception osl.handler.RequestException
	 *                Thrown if the message deserialized from the input stream
	 *                has an unknown type.
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException, ClassCastException, RequestException {
		// Header is always the same, read in sender and receiver info,
		// the type of the message, and its ID
		msgOrigSender = (PhysicalAddress) in.readObject();
		msgDestName = (Name) in.readObject();
		type = in.readUnsignedByte();
		id = in.readLong();

		// What we do now depends on the type of message we are.
		switch (type) {
		case MTYPE_UNKNOWN:
			// Don't need to deserialize anything else here as all the
			// fields are meaningless for a message of unknown type. These
			// messages are dumped when they are received anyway.
			break;

		case MTYPE_RPC_REPLY:
		case MTYPE_RPC_EXCEPTION:
			// RPC replies and exceptions can be handled equivalently.
			// I.e. deserialize srcID and returnVal
			srcID = in.readLong();
			returnVal = in.readObject();
			break;

		case MTYPE_EXCEPTION:
			// Regular exceptions need to read practically the entire
			// message structure.
			srcID = in.readLong();
			returnVal = in.readObject();
			targetMethod = in.readUTF();
			args = (Object[]) in.readObject();
			break;

		case MTYPE_REQUEST:
		case MTYPE_RPC_REQUEST:
			// Both types of requests are handled equivalently. Read in the
			// target method and the args array.
			targetMethod = in.readUTF();
			args = (Object[]) in.readObject();
			break;

		case MTYPE_GC_REQUESTIDS:
			// Ok, just read in the id array
			gcReqs = (long[]) in.readObject();
			break;

		case MTYPE_REQUEST_CDATA:
			// Only need to read in the requested class name
			className = (String) in.readObject();
			break;

		case MTYPE_REPLY_CDATA:
			// Need to read in srcID and classData
			srcID = in.readLong();
			classData = (byte[]) in.readObject();
			break;

		default:
			throw new RequestException("Unknown message type " + type
					+ ": message is corrupted");
		}

	}

}
