package osl.manager;

/**
 * This exception is thrown from within the <em>clone</em> method of
 * <em>ActorRequest</em> (or any of its subclasses) if there is an error during
 * clone creation. In certain cases, the fields of an <em>ActorRequest</em> are
 * "deep copied" (i.e. <em>DeepCopy.deepCopy</em>) in order to create a safe
 * copy (that is, a copy that can safely be distributed to other actors in the
 * system). Thus, a <em>clone</em> operation will usually fail because of a
 * serialization error encountered while serializing parts of a request. The
 * exception itself is actually a wrapper for the exception encountered while
 * performing the clone.
 * <p>
 * 
 * Note that this class extends <em>CloneNotSupportedException</em> so that we
 * can safely throw it from <em>clone</em> methods.
 * <p>
 * 
 * @see ActorManager
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/10/05 15:57:11 $)
 */

public class ActorRequestCloneException extends CloneNotSupportedException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2064873928580033463L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public ActorRequestCloneException() {
		super();
		detail = null;
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public ActorRequestCloneException(String s, Throwable t) {
		super(s);
		detail = t;
	}

}
