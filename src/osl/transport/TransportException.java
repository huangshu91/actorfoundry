package osl.transport;

/**
 * This class defines an exception which is thrown when an error is encountered
 * by the transport layer or one of its connections. The actual exception thrown
 * is implementation dependent. This class merely serves as a wrapper for
 * whatever exception happened to be thrown.
 * <p>
 * 
 * @see TransportLayer
 * @see TransportInstance
 * @see TransportClient
 * @see PhysicalAddress
 * @see TransportMessage
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:16 $)
 */

public class TransportException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8490721375365004720L;
	/**
	 * The nested exception.
	 */
	public Throwable detail;

	/**
	 * The default constructor with no message.
	 */
	public TransportException() {
		super();
	}

	/**
	 * The default constructor with a message.
	 */
	public TransportException(String s) {
		super(s);
	}

	/**
	 * Constructor used to build an encapsulated exception.
	 */
	public TransportException(String s, Throwable t) {
		super(s);
		detail = t;
	}

	/**
	 * Produce the message, include the message from the nested exception if
	 * there is one.
	 */
	public String getMessage() {
		return super.getMessage() + "\n  nested exception: "
				+ detail.getMessage();
	}
}
