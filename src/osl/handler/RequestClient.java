package osl.handler;

/**
 * This interface defines the methods expected from request handler clients.
 * Currently, we only require a method to handle exceptions thrown by
 * asynchronous requests. This method is invoked when an asynchronous
 * <em>handlerRequest</em> results in an exception (synchronous requests throw
 * an exception directly to their caller).
 * <p>
 * 
 * @see RequestHandler
 * @see RequestSession
 * @see RequestException
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:01 $)
 */

public interface RequestClient {

	/**
	 * Called when an exception has been received for an asynchronous request
	 * sent on a particular session.
	 * 
	 * @param <b>session</b> The <em>RequestSession</em> that the original
	 *        asynchronous call originated from. This field is provided so that
	 *        clients with multiple handler sessions may disambiguate the origin
	 *        of exceptions.
	 * @param <b>except</b> The <em>Exception</em> which was returned from the
	 *        remote handler. This will either be a
	 *        <em>NoSuchMethodException</em> which indicates that no matching
	 *        method could be found on the target (and thus the method was never
	 *        invoked); or a <em>RemoteException</em> which encapsulates an
	 *        exception thrown by the remote method itself (however, the remote
	 *        method WAS invoked).
	 * @param <b>id</b> The <em>RequestID</em> of the original request which
	 *        caused the exception. This information is used to disambiguate
	 *        multiple asynchronous exceptions.
	 */
	public void handlerException(RequestSession session, Exception except,
			RequestID id);

}
