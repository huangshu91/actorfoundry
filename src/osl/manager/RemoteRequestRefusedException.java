package osl.manager;

/**
 * This exception is normally thrown by a manager if it decides to refuse a
 * request passed through one of the "managerXXX" functions. The exact reason
 * for refusing the request is implementation depedent (manager implementers may
 * subclass this exception to indicate specific reasons for refusing service).
 * <p>
 * 
 * @see ActorManager
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:17 $)
 */

public class RemoteRequestRefusedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4469177957637550851L;

	/**
	 * The default constructor with a message.
	 */
	public RemoteRequestRefusedException(String s) {
		super(s);
	}
}
