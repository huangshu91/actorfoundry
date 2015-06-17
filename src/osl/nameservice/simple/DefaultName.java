package osl.nameservice.simple;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import osl.nameservice.Name;
import osl.transport.PhysicalAddress;

/**
 * This class defines an implementation of the <em>Name</em> interface for use
 * in the "default" nameservice implementation. In the default implementation, a
 * name is a <em>PhysicalAddress</em>/<em>integer</em> pair which identifies the
 * creator of the name. This information may be used to track down the current
 * location of the entity associated with the name when local name tables are
 * invalidated.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/07/13 02:01:50 $)
 * @see osl.nameservice.Name
 * @see osl.nameservice.NameService
 * @see DefaultNameService
 */

public class DefaultName implements Name {
	/**
	 * The <em>PhysicalAddress</em> of the nameservice instance which created
	 * this name.
	 */
	PhysicalAddress creatorAddr;

	/**
	 * The <em>int</em> identifier which uniquely identifies this name relative
	 * to all other names created by the same nameservice instance.
	 */
	int creatorID;

	/**
	 * Cached String representation of this Name. Names are supposed to be
	 * immutable so we should be able to generate this entry just once.
	 */
	// transient String nameRep;
	/**
	 * The default constructor. Note that this constructor DOES NOT build a
	 * legal name.
	 */
	public DefaultName() {
	}

	/**
	 * The default constructor for building legal names. This constructor is
	 * protected so that it can't be spoofed by clients. Note that this
	 * constructor may be used to create a legal name as long as <b>cAddr</b>
	 * refers to the physical address of an existing <em>DefaultNameService</em>
	 * instance, and <b>cID</b> is unique relative to all other name IDs created
	 * by the nameservice <b>cAddr</b>.
	 * 
	 * @param <b>cAddr</b> The <em>PhysicalAddress</em> of the creator of this
	 *        name.
	 * @param <b>cID</b> The <em>int</em> ID which should be unique relative to
	 *        all names created by <b>cAddr</b>.
	 */
	public DefaultName(PhysicalAddress cAddr, int cID) {
		creatorAddr = cAddr;
		creatorID = cID;
		// nameRep = "<DefaultName:" + creatorAddr + " | " + creatorID + ">";
	}

	/**
	 * The canonical <em>toString</em> method.
	 */
	public String toString() {
		// return "DefaultName - creatorAddr: " + creatorAddr + " creatorID: " +
		// creatorID;
		// return nameRep;
		return "<DefaultName:" + creatorAddr + " | " + creatorID + ">";
	}

	/**
	 * Determines whether two <em>DefaultName</em>s should be considered equal.
	 * Two names are equal if they have the same creator with the same integral
	 * id.
	 * 
	 * @param <b>other</b> A reference to the other <em>Object</em> to which
	 *        equality is being tested
	 * @return <b>true</b> if the names are equal, <b>false</b> otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof DefaultName) {
			DefaultName theOther = (DefaultName) other;
			if (creatorAddr!=null)
				return (creatorAddr.equals(theOther.creatorAddr) && (creatorID == theOther.creatorID));
			else
				return (creatorID == theOther.creatorID);

		} else
			return false;
	}

	/**
	 * Returns the hash code for this <em>DefaultName</em>. The hash code for a
	 * default name is a combination of the hash codes of the address of its
	 * creator and its integral ID.
	 * 
	 * @return An <b>int</b> representing the hash code for this object.
	 */
	public int hashCode() {
		return creatorAddr.hashCode() + creatorID;
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
		// Serialize fields
		out.writeObject(creatorAddr);
		out.writeInt(creatorID);
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
		creatorAddr = (PhysicalAddress) in.readObject();
		creatorID = in.readInt();
		// nameRep = "<DefaultName:" + creatorAddr + " | " + creatorID + ">";
	}

}
