package osl.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * This class provides a convenient mechanism for specifying a list of Objects
 * which represent parameters. Parameter lists are used to configure several
 * classes in the foundry in which general interfaces are used to encapsulate a
 * wide class of implementations (for example, see the <em>TransportLayer</em>
 * interface). We provide twenty different constructors for this class so that
 * the <em>addParameter</em> function need not be called very often.
 * <b>NOTE:</b> None of the methods in this class are synchronized. Keep this in
 * mind if you expect multiple threads to be accessing a parameter list.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:29 $)
 */

public class ParameterList implements Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2367451608235403140L;

	/**
	 * This constant sets the initial size of the array which is used to hold
	 * parameters.
	 */
	static final int INITIAL_SIZE = 10;

	/**
	 * This array holds the actual parameters. The array will grow dynamically
	 * to accomodate new elements. Note that elements can not be removed from
	 * parameter lists. Array size is doubled when necessary which should make
	 * for a rather efficient implementation.
	 */
	Object[] theParams;

	/**
	 * The index of the next open spot in the parameter list array.
	 */
	int open;

	/**
	 * Default constructor for the <em>Queue</em> class.
	 */
	public ParameterList() {
		theParams = new Object[INITIAL_SIZE];
		open = 0;
	}

	// Varargs versions of constructors
	public ParameterList(Object arg1) {
		Object[] theArgs = { arg1 };
		theParams = theArgs;
		open = 1;
	}

	public ParameterList(Object arg1, Object arg2) {
		Object[] theArgs = { arg1, arg2 };
		theParams = theArgs;
		open = 2;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3) {
		Object[] theArgs = { arg1, arg2, arg3 };
		theParams = theArgs;
		open = 3;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4) {
		Object[] theArgs = { arg1, arg2, arg3, arg4 };
		theParams = theArgs;
		open = 4;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5 };
		theParams = theArgs;
		open = 5;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6 };
		theParams = theArgs;
		open = 6;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7 };
		theParams = theArgs;
		open = 7;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8 };
		theParams = theArgs;
		open = 8;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9 };
		theParams = theArgs;
		open = 9;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10 };
		theParams = theArgs;
		open = 10;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11 };
		theParams = theArgs;
		open = 11;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12 };
		theParams = theArgs;
		open = 12;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13 };
		theParams = theArgs;
		open = 13;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14 };
		theParams = theArgs;
		open = 14;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15 };
		theParams = theArgs;
		open = 15;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16 };
		theParams = theArgs;
		open = 16;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17 };
		theParams = theArgs;
		open = 17;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17, Object arg18) {
		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17,
				arg18 };
		theParams = theArgs;
		open = 18;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19) {

		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17,
				arg18, arg19 };
		theParams = theArgs;
		open = 19;
	}

	public ParameterList(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19, Object arg20) {

		Object[] theArgs = { arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
				arg9, arg10, arg11, arg12, arg13, arg14, arg15, arg16, arg17,
				arg18, arg19, arg20 };
		theParams = theArgs;
		open = 20;
	}

	/**
	 * This private method is used to double the size of the parameter list when
	 * more space is required (as a result of calling <em>addParameter</em>).
	 */
	private void grow() {
		Object[] newArray = new Object[2 * theParams.length];
		System.arraycopy(theParams, 0, newArray, 0, open);
		theParams = newArray;
	}

	/**
	 * Add a new element to the list.
	 * 
	 * @param <b>param</b> An <em>Object</em> which should be added as the next
	 *        paramater.
	 */
	public void addParameter(Object param) {
		if (open == theParams.length)
			grow();
		theParams[open++] = param;
	}

	/**
	 * The canonical toString method.
	 */
	public String toString() {
		String returnVal = "ParameterList: ";

		if (open == 0)
			return returnVal + "no elements";

		for (int i = 0; i < open; i++)
			returnVal = returnVal + theParams[i].toString() + " ";

		return returnVal;
	}

	/**
	 * Returns an enumeration of the parameter list.
	 */
	public Enumeration<?> enumerate() {
		return new ParmListEnumerate(this);
	}

	// Inner class which implements the Enumeration interface
	public class ParmListEnumerate implements Enumeration<Object> {
		/**
		 * A reference to the parameter list which created us.
		 */
		ParameterList parent = null;

		/**
		 * An index into our parent's parameter array that shows the current
		 * position of this enumeration.
		 */
		int where = 0;

		/**
		 * The default constructor for this class.
		 */
		public ParmListEnumerate(ParameterList P) {
			parent = P;
		}

		/**
		 * Return true if there are any more elements. False otherwise.
		 */
		public boolean hasMoreElements() {
			return (where < parent.open);
		}

		public Object nextElement() throws NoSuchElementException {
			if (where < parent.open)
				return parent.theParams[where++];
			else
				throw new NoSuchElementException(
						"No more elements in ParameterList");
		}
	}
}
