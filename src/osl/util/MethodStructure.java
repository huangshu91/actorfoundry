package osl.util;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * This class is a simple structure used for holding a <em>Method</em> and the
 * array of its argument types. These structures may be sorted in
 * <em>MethodStructureVectors</em> for building hash tables for efficient method
 * lookup.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:28 $)
 * @see osl.util.MethodStructureVector
 */

public class MethodStructure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5037222790350157591L;

	/**
	 * The method stored in this structure.
	 */
	public transient Method meth;

	/**
	 * The array of arguments expected by this method.
	 */
	public Class<?>[] argTypes;

	/**
	 * The default constructor.
	 */
	public MethodStructure() {
		this(null, null);
	}

	/**
	 * A more useful constructor.
	 */
	public MethodStructure(Method theMeth, Class<?>[] args) {
		meth = theMeth;
		argTypes = args;
	}

	/**
	 * Returns a string representation of this class for debugging purposes.
	 */
	public String toString() {
		String result = "osl.util.MethodStructure: [meth = " + meth.toString()
				+ ", ";
		if ((argTypes == null) || (argTypes.length == 0))
			return result + "no arguments]";

		result = result + "args = ";
		for (int i = 0; i < argTypes.length; i++) {
			result = result + "[" + argTypes[i].toString() + "] ";
		}

		return result + "]";
	}
}
