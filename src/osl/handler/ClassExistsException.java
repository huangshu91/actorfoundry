package osl.handler;

/**
 * This exception indicates that an attempt was made to add a new local class,
 * but a class with the given name has already been loaded with the
 * <em>NetLoader</em>.
 * <p>
 * 
 * @see NetLoader
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/10/05 15:57:10 $)
 */

public class ClassExistsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1146547189017464712L;

	/**
	 * The default constructor with a message.
	 */
	public ClassExistsException(String s) {
		super(s);
	}
}
