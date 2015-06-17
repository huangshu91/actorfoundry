package osl.manager;

/**
 * This class defines an exception which encapsulates an exception thrown while
 * invoking a remote method. The actual exception thrown depends on the manager
 * operation invoked. Typically, this exception is thrown due to an exception
 * raised somewhere in user-defined code (e.g. in a user-defined method in an
 * actor). This class merely serves as a wrapper for whatever exception happened
 * to be thrown.
 * <p>
 * 
 * @see ActorManager
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/06/12 21:32:16 $)
 */

public class RemoteCodeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5772390115221768131L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public RemoteCodeException() {
		super();
	}

	/**
	 * The default constructor with a message.
	 */
	public RemoteCodeException(String s) {
		super(s);
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public RemoteCodeException(String s, Throwable t) {
		super(s);
		detail = t;
	}

	/**
	 * Produce the message, include the message from the nested exception if
	 * there is one.
	 */
	public String getMessage() {
		return super.getMessage() + "\n  nested exception: "
				+ detail.toString() + " " + detail.getMessage();
	}
}
