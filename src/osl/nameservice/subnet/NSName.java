//  Copyright Notice.
// 
//  NSName - the implementation of a name, used by the name service
//  Copyright (C) 1998  Thomas Heide Clausen <voop@cs.auc.dk>
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Library General Public
//  License as published by the Free Software Foundation;
//  version 2 of the License.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Library General Public License for more details.
//
//  You should have received a copy of the GNU Library General Public
//  License along with this library; if not, write to the
//  Free Software Foundation, Inc., 59 Temple Place - Suite 330,
//  Boston, MA  02111-1307, USA./  
// *************************************************************************

package osl.nameservice.subnet;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import osl.nameservice.Name;
import osl.transport.PhysicalAddress;

/**
 * This class defines the name service name, which is used by the name service
 * for identification of the entities. The name service name is composed by
 * three parts:
 * 
 * <li>The physical address of the creating node, <li>A timstamp, uniquely
 * identifying the time of creation with milisecond precition. <li>An
 * identifyer, assigned by the name service. This is required as it obviously is
 * possoble to create two instances of NSName within the same milisecond (Thanks
 * SUN for making fast machines, and thanks IBM for making my laptop so slow,
 * that I did not discover this before).
 * 
 * @author Thomas Heide Clausen (voop@cs.auc.dk)
 * @version 0.2 (980331)
 */

public class NSName implements Name {
	/**
	 * The next three variables define uniquely the identity of the name.
	 */
	long create_time;
	PhysicalAddress creator;
	long counter;

	/**
	 * Default constructor.
	 * 
	 * @param PhysicalAddress
	 *            creator
	 * @param PhysicalAddress
	 *            counter
	 */
	public NSName(PhysicalAddress creator, long counter) {
		this.create_time = java.lang.System.currentTimeMillis();
		this.creator = creator;
		this.counter = counter;
	}

	/**
	 * Compares two instances and returns true if they should be considered
	 * "equal".
	 * 
	 * @param NSName
	 *            name
	 */
	public boolean equals(Object o) {
		if (o instanceof NSName) {
			NSName name = (NSName) o;
			if ((this.create_time == name.create_time)
					&& (this.counter == name.counter)
					&& (this.creator.equals(name.creator))) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * The canonical toString method.
	 */
	public String toString() {
		return "NSName: created = " + create_time + " at = "
				+ creator.toString() + " identifier = " + counter;
	}

	/**
	 * The canonical hashCode()
	 */
	public int hashCode() {
		return (int) ((this.create_time) + this.counter);
	}

	/**
	 * Serialize the contents of this class to the output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to which we should write this
	 *        instance.
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeLong(create_time);
		out.writeObject(creator);
		out.writeLong(counter);
	}

	/**
	 * Deserialize into a new instnace of <em>NSName</em> by reading from the
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
		create_time = in.readLong();
		creator = (PhysicalAddress) in.readObject();
		counter = in.readLong();
	}

}
