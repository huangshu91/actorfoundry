package osl.manager;

/**
 * This class defines an exception which encapsulates an exception thrown while
 * invoking an ActorImpl extension (i.e. a call to ActorImpl.implExtension). The
 * actual exception thrown depends on the extension operation invoked. This
 * class merely serves as a wrapper for whatever exception happened to be
 * thrown.
 * <p>
 * 
 * @see ActorImpl#implExtension
 * @see Actor#extension
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/07/18 18:59:45 $)
 */

public class ExtensionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7600194249170086895L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public ExtensionException() {
		super();
	}

	/**
	 * The default constructor with a message.
	 */
	public ExtensionException(String s) {
		super(s);
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public ExtensionException(String s, Throwable t) {
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
