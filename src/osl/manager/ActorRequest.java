package osl.manager;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Hashtable;

import osl.util.Assert;
import osl.util.DeepCopy;

/**
 * This class is the common root of all actor requests. Each request has a
 * unique ID which is represented by a pair: a <em>long</em> timestamp, and the
 * <em>ActorName</em> of the originator of the request. These fields are used to
 * correctly compare and hash actor requests.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/01/19 18:43:33 $)
 * @see Actor
 * @see ActorManager
 */

public class ActorRequest implements Externalizable, Cloneable {
	/**
	 * The name of the actor which initiated this request. This field helps to
	 * "uniquify" this request, as well as providing an address for deliverying
	 * exception messages.
	 */
	public ActorName originator;

	/**
	 * The id number of this request.
	 */
	public long ID;

	/**
	 * Normally requests have a fixed structure which makes extensions difficult
	 * without introducing new classes to the hiearchy. This hashtable is
	 * provided as a convenient way to add implementation-specific extensions to
	 * a request. This field is not initialized by the constructor so that the
	 * amount of information serialized for off-node interactions is minimized.
	 * The convenience function below, however, initializes this field the first
	 * time it is used.
	 */
	public Hashtable<Object, Object> tags;

	/**
	 * The default constructor. This constructor DOES NOT create a valid ID!
	 */
	public ActorRequest() {
		originator = null;
		ID = -1;
		tags = null;
	}

	/**
	 * Test whether or not two requests are equivalent.
	 */
	public boolean equals(Object other) {
		if (other instanceof ActorRequest) {
			ActorRequest o = (ActorRequest) other;

			// PRAGMA [assert] Assert.afAssert(o.originator != null,
			// "malformed request passed to equals: " + other);
			return (o.originator.equals(originator)) && (o.ID == ID);
		} else
			return false;
	}

	/**
	 * Return the hashcode for this request.
	 */
	public int hashCode() {
		return (int) (0xFFFF & ID) + originator.hashCode();
	}

	/**
	 * Useful for debugging.
	 */
	public String toString() {
		return "<ActorRequest: ID: " + ID + " originator: " + originator + ">";
	}

	/**
	 * This function allows extra information to be added to a request in the
	 * form of a "tag". This allows foundry extensions to add content to
	 * messages for use by other managers.
	 */
	public void addTag(Object key, Object value) {
		try {
			tags.put(key, value);
		} catch (NullPointerException e) {
			// If here then we need to initialize the hashtable.
			tags = new Hashtable<Object, Object>(5);
			tags.put(key, value);
		}
	}

	/**
	 * This method allows easy access to a tag stored in the tag table. Returns
	 * the value of the tag if it exists, null otherwise.
	 */
	public Object getTag(Object key) {
		// PRAGMA [assert] Assert.afAssert(key != null, "tag must be non-null");
		if (tags == null)
			return null;
		else
			return tags.get(key);
	}

	/**
	 * A quicky function for determining if this request is valid for sending. A
	 * request is valid if "originator" is non-null and "ID" is not -1.
	 */
	public boolean valid() {
		return (originator != null) && (ID != -1);
	}

	/**
	 * Allows a clone to be made of this request. Note that clones of the tag
	 * table are "safe" in that they are deep copies.
	 * 
	 * @exception osl.manager.ActorRequestCloneException
	 *                Thrown as a wrapper for any error which occurs during
	 *                clone creation. Typically, this exception is thrown due to
	 *                a serialization error in the <tt>tags</tt> field. This
	 *                field is serialized in order to create a deep copy. We use
	 *                a deep copy to prevent the creation of hidden channels
	 *                between actors sharing a reference to a common object.
	 */
	public Object clone() throws ActorRequestCloneException {
		ActorRequest newCopy = null;

		try {
			newCopy = (ActorRequest) super.clone();
			newCopy.tags = (Hashtable<Object, Object>) DeepCopy
					.deepCopy(newCopy.tags);
		} catch (CloneNotSupportedException e) {
			// This should never happen. If it does then fail an assertion
			// as this is a critical error.
			Assert
					.afAssert(false,
							"we implement cloneable but received CloneNotSupportedAnyway");
		} catch (IOException e) {
			// Serialization error for tag table
			throw new ActorRequestCloneException(
					"error serializing tag table in ActorRequest", e);
		} catch (Exception e) {
			// Anything else is probably fatal but return it anyway since it
			// is probably an error in one of the structures within the tags
			// table.
			throw new ActorRequestCloneException("error cloning ActorRequest",
					e);
		}

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
		// PRAGMA [assert] Assert.afAssert(out != null,
		// "\"out\" must be a valid ObjectOutput stream here");
		// PRAGMA [assert] Assert.afAssert(originator != null,
		// "\"originator\" must be non-null, maybe request wasn't stamped?");
		originator.writeExternal(out);
		out.writeLong(ID);
		out.writeObject(tags);
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
		originator = new ActorName(in);
		ID = in.readLong();
		tags = (Hashtable<Object, Object>) in.readObject();
	}

}
