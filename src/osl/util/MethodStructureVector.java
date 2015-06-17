package osl.util;

import java.util.Vector;

/**
 * This class is used to sort <em>MethodStructures</em> first based on the
 * number of arguments they require, and second on the specificity of arguments.
 * This class is used, for example, to sort all the methods with the same name
 * in a particular object. Upon sorting, a linear search from the beginning of
 * the vector should always yield the most specific method. This class extends
 * the <em>Vector<em>
  class.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:28 $)
 * @see osl.util.MethodStructure
 * @see java.util.Vector
 */

public class MethodStructureVector extends Vector<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5825239434727504942L;

	/**
	 * The default constructors just call those inherited from <em>Vector</em>.
	 */
	public MethodStructureVector() {
		super();
	}

	public MethodStructureVector(int a) {
		super(a);
	}

	public MethodStructureVector(int a, int b) {
		super(a, b);
	}

	/**
	 * This method is equivalent to <em>addElement</em> except that it
	 * implements the sorting operations to insert the new
	 * <em>MethodStructure</em> in the appropriate location. We couldn't just
	 * override <em>addElement</em> in <em>Vector</em> because it is declared
	 * final.
	 * 
	 * @param <b>obj</b> An <em>MethodStructure</em> that should be inserted in
	 *        the vector.
	 */
	synchronized public void insertElement(MethodStructure obj) {
		int size = size();
		int numArgs = obj.argTypes.length;
		int i, first, last;
		boolean found = false;

		// If no elements then just insert
		if (size == 0) {
			addElement(obj);
			return;
		}

		// Otherwise look for first element with the same number of
		// arguments as this one.
		for (i = 0; i < size; i++)
			if (((MethodStructure) elementAt(i)).argTypes.length == numArgs) {
				found = true;
				break;
			}

		// If we can't find an element with the same number of args then
		// the new element belongs either at the front or the back of the
		// vector...
		if (!found) {
			if (((MethodStructure) firstElement()).argTypes.length > numArgs)
				// Goes in front
				insertElementAt(obj, 0);
			else
				// Goes in back
				addElement(obj);
			return;
		}

		// Found the front of a block, now find the back
		// When done first = first element of block
		// last = last element of block
		first = i;
		last = first;
		while (((last + 1) < size)
				&& (((MethodStructure) elementAt(last + 1)).argTypes.length == numArgs))
			last++;

		// Ok, now scan the block and figure out where to put the new
		// element based on specificity
		for (i = first; i <= last; i++)
			if (moreSpecific(obj, (MethodStructure) elementAt(i))) {
				insertElementAt(obj, i);
				break;
			}

		// If no place was found for the new method then it must go at
		// the end of the list.
		if (i > last)
			insertElementAt(obj, last + 1);
	}

	/**
	 * This function determines if one method is more specific than another. We
	 * use this to figure out where to put new methods in the vector. According
	 * to the Java lang spec, one method is more specific than another if each
	 * argument is a sub-class of the argument of the other.
	 */
	boolean moreSpecific(MethodStructure obj1, MethodStructure obj2) {
		// INVARIANT: both obj1 and obj2 have the same number of
		// arguments. I.e. obj1.argTypes.length == obj2.argTypes.length
		for (int i = 0; i < obj1.argTypes.length; i++)
			if (!obj2.argTypes[i].isAssignableFrom(obj1.argTypes[i]))
				return false;

		return true;

	}
}
