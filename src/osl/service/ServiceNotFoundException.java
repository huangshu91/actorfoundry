package osl.service;

/**
 * This class defines an exception which is thrown when the service
 * corresponding to a particular <em>ServiceName</em> can not be found. This
 * exception is normally only thrown by <em>ActorManager</em>s.
 * <p>
 * 
 * @see Service
 * @see osl.manager.ActorManager
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:56 $)
 */

public class ServiceNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5197522651368773822L;

	/**
	 * The default constructor with a message.
	 */
	public ServiceNotFoundException(String s) {
		super(s);
	}
}
