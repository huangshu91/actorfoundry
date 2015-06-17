package osl.manager;

/**
 * This exception indicates that the <em>ActorName</em> specified as an argument
 * to a particular method is either ill-formed or does not correspond to a legal
 * actor name.
 * <p>
 * 
 * @see ActorManager
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/07/18 18:59:45 $)
 */

public class IllegalTargetException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 665048139617774404L;

	/**
	 * The default constructor with a message.
	 */
	public IllegalTargetException(String s) {
		super(s);
	}
}
