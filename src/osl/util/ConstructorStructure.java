package osl.util;

import java.lang.reflect.Constructor;

/**
 * This class is identical to the <em>MethodStructure</em> class except that the
 * type of the <b>meth</b> field is changed to <em>Constructor</em>. By making
 * this class an extension of the <em>MethodStructure</em> class we can now sort
 * constructors using the <em>MethodStructureVector</em> class. This gives us an
 * easy way of finding the most specific constructor given a constructor name
 * and an array of argument types.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:25 $)
 * @see osl.util.MethodStructureVector
 */

public class ConstructorStructure extends MethodStructure {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6868755109738905813L;
	/**
	 * The constructor stored in this structure. This overrides the <b>meth</b>
	 * field inherited from <em>MethodStructure</em>.
	 */
	public transient Constructor<?> meth;

	/**
	 * The default constructor.
	 */
	public ConstructorStructure() {
		this(null, null);
	}

	/**
	 * A more useful constructor.
	 */
	public ConstructorStructure(Constructor<?> theMeth, Class<?>[] args) {
		super(null, args);
		meth = theMeth;
	}

	/**
	 * Returns a string representation of this class for debugging purposes.
	 */
	public String toString() {
		String result = "osl.util.ConstructorStructure: [meth = "
				+ meth.toString() + ", ";
		if ((argTypes == null) || (argTypes.length == 0))
			return result + "no arguments]";

		result = result + "args = ";
		for (int i = 0; i < argTypes.length; i++) {
			result = result + "[" + argTypes[i].toString() + "] ";
		}

		return result + "]";
	}
}
