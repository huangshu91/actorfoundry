package osl.manager;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import osl.util.DeepCopy;

/**
 * This class packages a request to create a new actor.
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/03/06 21:18:36 $)
 * @see Actor
 * @see ActorManager
 */

public class ActorCreateRequest extends ActorRequest {
	/**
	 * The actor performing the creation.
	 */
	public ActorName requester;

	/**
	 * The class of the new actor requested. This should be a subclass of
	 * <em>Actor</em>.
	 */
	transient public Class<?> behToCreate;
	String behToCreateName = null;

	/**
	 * The class of the implementation of the new actor. This should be a
	 * subclass of <em>ActorImpl</em>.
	 */
	transient public Class<?> implToCreate;
	String implToCreateName = null;

	/**
	 * The array of arguments to pass to the constructor of the newly created
	 * actor behavior.
	 */
	public Object[] constructorArgs;

	/**
	 * The name of the <em>ActorManager</em> on which to construct the new
	 * actor.
	 */
	public ActorManagerName site;

	/**
	 * The context that should be associated with the new actor.
	 */
	public ActorContext context;

	/**
	 * The default constructor. Note that this <b>DOES NOT</b> construct a legal
	 * creation request.
	 */
	public ActorCreateRequest() {
		this(null, null, null, null, null);
	}

	/**
	 * The usual constructor that is used to build creation requests.
	 */
	public ActorCreateRequest(ActorName R, Class<?> toCreate, Class<?> toImpl,
			Object[] args, ActorManagerName loc) {
		requester = R;
		behToCreate = toCreate;
		implToCreate = toImpl;
		constructorArgs = args;
		site = loc;
	}

	/**
	 * A useful method for debugging. Returns a string representation of this
	 * create request.
	 */
	public String toString() {
		String conArgs = "(";
		if (constructorArgs.length > 0) {
			for (int i = 0; i < constructorArgs.length; i++) {
				conArgs = conArgs + constructorArgs[i].toString();
				if (i < (constructorArgs.length - 1))
					conArgs = conArgs + ", ";
			}
		}
		conArgs = conArgs + ")";

		return "<ActorCreateRequest: requester=" + requester + " behToCreate="
				+ behToCreate + " implToCreate=" + implToCreate
				+ " constructorArgs=" + conArgs + " site=" + site + ">";
	}

	/**
	 * Provide a "safe" clone of a create request. A safe create request clone
	 * is a regular clone of the original request with the exception that a deep
	 * copy is performed on the <em>constructorArgs</em> and <em>context</em>
	 * fields. This allows cloning to be used when an a copy of a
	 * <em>ActorCreateRequest</em> must be created for starting a new local
	 * actor.
	 * 
	 * @exception osl.manager.ActorRequestCloneException
	 *                Thrown as a wrapper for any error which occurs during
	 *                clone creation. Typically, this exception is thrown due to
	 *                a serialization error in the <tt>constructorArgs</tt>
	 *                field. This field is serialized in order to create a deep
	 *                copy. We use a deep copy to prevent the creation of hidden
	 *                channels between actors sharing a reference to a common
	 *                object.
	 */
	public Object clone() throws ActorRequestCloneException {
		// First create a regular cloned copy.
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.clone> Calling super.clone...");
		ActorCreateRequest newCopy = (ActorCreateRequest) super.clone();

		// Now deep copy the constructor args and the context.
		try {
			// PRAGMA [debug,osl.manager.ActorCreateRequest]
			// Log.println("<ActorCreateRequest.clone> Cloning constructor args...");
			// newCopy.constructorArgs = (Object[])
			// DeepCopy.deepCopy(newCopy.constructorArgs);
			if (constructorArgs != null) {
				newCopy.constructorArgs = new Object[constructorArgs.length];
				for (int i = 0; i < constructorArgs.length; i++)
					if ((constructorArgs[i] != null)
							&& (DeepCopy.isMutableClass(constructorArgs[i]
									.getClass())))
						newCopy.constructorArgs[i] = DeepCopy
								.deepCopy((Serializable) constructorArgs[i]);
					else
						newCopy.constructorArgs[i] = constructorArgs[i];
			}

		} catch (IOException e) {
			// Serialization error for constructorArgs
			throw new ActorRequestCloneException(
					"error serializing constructorArgs in ActorCreateRequest",
					e);
		} catch (Exception e) {
			// Anything else is probably fatal but return it anyway since it
			// is probably an error in one of the arguments in the
			// constructorArgs array.
			throw new ActorRequestCloneException(
					"error cloning ActorCreateRequest", e);
		}

		try {
			// PRAGMA [debug,osl.manager.ActorCreateRequest]
			// Log.println("<ActorCreateRequest.clone> Cloning context...");
			// newCopy.context = (ActorContext)
			// DeepCopy.deepCopy(newCopy.context);
			if (context != null && DeepCopy.isMutableClass(context.getClass()))
				newCopy.context = (ActorContext) DeepCopy.deepCopy(context);
			else
				newCopy.context = context;
		} catch (IOException e) {
			// Serialization error for context
			throw new ActorRequestCloneException(
					"error serializing context in ActorCreateRequest", e);
		} catch (Exception e) {
			// Anything else is probably fatal but return it anyway since it
			// is probably an error in one of the structures within the
			// context.
			throw new ActorRequestCloneException(
					"error cloning ActorCreateRequest", e);
		}

		// And return the clone.
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.clone> Done cloning...");
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
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.writeExternal> Writing out create request...");
		// Serialize parent first
		super.writeExternal(out);

		// Now serialize our fields
		requester.writeExternal(out);
		// out.writeObject(behToCreate);
		// out.writeObject(implToCreate);
		if (behToCreateName == null)
			behToCreateName = behToCreate.getName();
		if (implToCreateName == null)
			implToCreateName = implToCreate.getName();
		out.writeUTF(behToCreateName);
		out.writeUTF(implToCreateName);

		out.writeObject(constructorArgs);
		out.writeObject(site);
		out.writeObject(context);
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.writeExternal> Done...");
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
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> Reading in create request...");
		// Deserialize parent first
		super.readExternal(in);

		// Now deserialize our fields
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 1");
		requester = new ActorName(in);

		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 2");
		// behToCreate = (Class) in.readObject();
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 3");
		// implToCreate = (Class) in.readObject();

		behToCreateName = in.readUTF();
		implToCreateName = in.readUTF();
		behToCreate = Class.forName(behToCreateName);
		implToCreate = Class.forName(implToCreateName);

		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 4");
		constructorArgs = (Object[]) in.readObject();
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 5");
		site = (ActorManagerName) in.readObject();
		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> HERE 6");
		context = (ActorContext) in.readObject();

		// PRAGMA [debug,osl.manager.ActorCreateRequest]
		// Log.println("<ActorCreateRequest.readExternal> Done...");
	}

}
