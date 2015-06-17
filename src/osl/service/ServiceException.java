package osl.service;

/**
 * This class defines an exception which encapsulates an exception thrown while
 * invoking a service. The actual exception thrown is service dependent. This
 * class merely serves as a wrapper for whatever exception happened to be
 * thrown.
 * <p>
 * 
 * @see Service
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:55 $)
 */

public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5817478773122138925L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public ServiceException() {
		super();
	}

	/**
	 * The default constructor with a message.
	 */
	public ServiceException(String s) {
		super(s);
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public ServiceException(String s, Throwable t) {
		super(s);
		detail = t;
	}

	/**
	 * Produce the message, include the message from the nested exception if
	 * there is one.
	 */
	public String getMessage() {
		return super.getMessage() + "\n  nested exception: "
				+ detail.toString() + detail.getMessage();
	}
}
