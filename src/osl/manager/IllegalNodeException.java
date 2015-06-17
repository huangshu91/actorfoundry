package osl.manager;

/**
 * This exception indicates that the <em>ActorManagerName</em> argument used in
 * a method call does not specify a legal manager name. This is usually because
 * the name is malformed in some way. This may indicate an attempt by a client
 * to spoof a name.
 * <p>
 * 
 * @see ActorManager
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:14 $)
 */

public class IllegalNodeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4754658496420228523L;

	/**
	 * The default constructor with a message.
	 */
	public IllegalNodeException(String s) {
		super(s);
	}
}
