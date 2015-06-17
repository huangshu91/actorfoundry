package osl.nameservice;

/**
 * This class defines an exception which is thrown when a name argument to a
 * nameservice method can not be found. This may be because the name doesn't
 * exist, or because it is not part of local authoritative information.
 * <p>
 * 
 * @see NameService
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:33 $)
 */

public class NameNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607980779948085270L;

	/**
	 * The default constructor with a message.
	 */
	public NameNotFoundException(String s) {
		super(s);
	}
}
