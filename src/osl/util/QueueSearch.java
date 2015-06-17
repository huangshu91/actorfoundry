package osl.util;

/**
 * This interface is used to identify classes which may search a Queue. By
 * implementing this interface, each such class defines a predicate function
 * which may be used in conjunction with the <em>search</em> function defined in
 * <em>Queue</em>.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:30 $)
 */

public interface QueueSearch {
	/**
	 * This function defines a predicate which takes an element of a
	 * <em>Queue</em> as an argument and returns <b>true</b> or <b>false</b>
	 * depending on whether or not the object satisfies the predcicate.
	 */
	public boolean queueEvalPred(Object arg);

}
