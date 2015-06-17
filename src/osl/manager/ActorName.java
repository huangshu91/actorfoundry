package osl.manager;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Vector;

import osl.nameservice.Name;

/**
 * Instances of this class are used to represent actor names. An actor name is
 * required for just about every interaction with a local actor. We have
 * standardized the implementation of this class so that different manager
 * implementations can interact with one another. Implementations which require
 * additional functionality can always extend this class appropriately.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/01/19 18:43:33 $)
 * @see ActorManager
 * @see Actor
 */

public class ActorName implements Externalizable {
	/**
	 * Cached serialized representation of this name.
	 */
	// transient byte[] serializedForm = null;
	/**
	 * The nameservice name for this actor. Must be initialized by each
	 * constructor.
	 */
	private Name actorName;
	private boolean isRegistered;

	private transient ActorImpl theActor;
	private transient Vector<Long> access = new Vector<Long>();

	/**
	 * Need this constructor for serialization support.
	 */
	public ActorName() {
		actorName = null;
	}

	/**
	 * Special constructor used to quickly create an actor name from a
	 * serialization stream.
	 */
	ActorName(ObjectInput in) throws IOException, ClassNotFoundException {
		actorName = (Name) in.readObject();
	}

	/**
	 * The usual constructor used for this class.
	 */
	public ActorName(Name N) {
		actorName = N;
	}

	public ActorName(Name N, ActorImpl actor) {
		actorName = N;
		theActor = actor;
	}

	/**
	 * Provide read-only access to the internal name.
	 */
	public final Name getName() {
		return actorName;
	}

	public ActorImpl getActor() {
		return theActor;
	}

	public void setActor(ActorImpl actor) {
		this.theActor = actor;
	}

	public Vector<Long> getAccessingThreads() {
		return access;
	}
	
	public void setAccessingThreads(Vector<Long> vec) {
		access = vec;
	}


	/**
	 * Add the current thread to the access table.
	 */
	public void in(Thread I) {
		// Have to do this check because Hashtable hurls on a null
		// argument here.
		if ((I != null) && (theActor != null))
			access.add(I.getId());
	}

	/**
	 * Remove the current thread from the access table.
	 */
	public void out(Thread O) {
		// Have to do this check because Hashtable hurls on a null
		// argument here.
		if (O != null)
			access.remove(O.getId());
	}

	/**
	 * Returns <b>true</b> if two names should be considered equal and
	 * <b>false</b> otherwise. Equality is based on the equality of the
	 * <b>actorName</b> field.
	 */
	public boolean equals(Object other) {
		return ((other instanceof ActorName) && (((ActorName) other).actorName
				.equals(actorName)));
	}

	/**
	 * Return the hash code for this name. The hash value used is simply that of
	 * the encapsulated <em>Name</em> structure.
	 */
	public int hashCode() {
		return actorName.hashCode();
	}

	/**
	 * A useful method for debugging.
	 */
	public String toString() {
		return "<ActorName " + actorName + ">";
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
		if (!isRegistered && theActor != null) {
			ActorManager mgr = ActorManager.getInstance();
			if (mgr != null)
			{
				mgr.actorEscape(this);
				isRegistered = true;
			}
		}
		out.writeObject(actorName);
	}

	/**
	 * Deserialize into a new instance of <em>ActorName</em> by reading from the
	 * given input stream.
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
		actorName = (Name) in.readObject();
	}

}
