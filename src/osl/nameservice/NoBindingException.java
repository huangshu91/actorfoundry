package osl.nameservice;

/**
 * This class defines an exception which is thrown when a name currently has no
 * associated <em>PhysicalAddress</em> binding. This is usually an indication of
 * a race condition between a thread which creates a name and another thread
 * which binds the name to a physical address.
 * <p>
 * 
 * @see NameService
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:34 $)
 */

public class NoBindingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 992899319177614415L;

	/**
	 * The default constructor with a message.
	 */
	public NoBindingException(String s) {
		super(s);
	}
}
