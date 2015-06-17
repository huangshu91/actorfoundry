package osl.handler;

import java.io.IOException;

import osl.util.Log;

/**
 * This class defines an exception which is thrown when an error is encountered
 * by the request handler or one of its sessions. The actual exception thrown
 * may depend on the implementation or the type of exception thrown by a remote
 * client. This class merely serves as a wrapper for whatever exception happened
 * to be thrown. Note that this class extends <em>IOException</em> so that it
 * can be thrown from custom written serialization methods.
 * <p>
 * 
 * @see RequestHandler
 * @see RequestSession
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/03/06 21:18:36 $)
 */

public class RequestException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8541626586859034553L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public RequestException() {
		super();
	}

	/**
	 * The default constructor with a message.
	 */
	public RequestException(String s) {
		super(s);
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public RequestException(String s, Throwable t) {
		super(s);
		detail = t;
	}

	/**
	 * Produce the message, include the message from the nested exception if
	 * there is one.
	 */
	public String getMessage() {
		return super.getMessage() + "\n  nested exception: "
				+ detail.toString() + ": " + detail.getMessage()
				+ "\n  nested trace: " + Log.getThrowableTrace(detail);
	}
}
