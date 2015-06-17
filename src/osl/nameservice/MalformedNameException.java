package osl.nameservice;

/**
 * This class defines an exception which is thrown when a name argument to a
 * nameservice method has an illegal representation. This may occur, for
 * example, if names are combined from incompatible nameservice implementations,
 * or if a client attempts to spoof a name by creating an illegal instance.
 * <p>
 * 
 * @see NameService
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:32 $)
 */

public class MalformedNameException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3321698083181939849L;

	/**
	 * The default constructor with a message.
	 */
	public MalformedNameException(String s) {
		super(s);
	}
}
