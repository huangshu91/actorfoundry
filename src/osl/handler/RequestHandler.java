package osl.handler;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import osl.nameservice.NameService;
import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportException;
import osl.transport.TransportLayer;
import osl.util.Debug;
import osl.util.MethodStructure;
import osl.util.MethodStructureVector;

/**
 * The request handler abstracts over the low-level transport layer interactions
 * by providing an asynchronous/synchronous remote procedure call mechanism.
 * Using the request handler, clients may invoke functions on other remote/local
 * request handler clients. All that is required to make an invocation is the
 * <em>Name</em> of the remote client, which may be obtained using the registry
 * lookup functions.
 * <p>
 * 
 * Clients expose methods for remote requests by extending the
 * <em>RequestClient</em> interface. Upon opening a new session, the request
 * handler computes the set of remotely visible methods as follows:
 * <p>
 * 
 * <ol>
 * <li>Set <tt>visibleMethods</tt> to <tt>null</tt>.
 * <li>Let <tt>rtClass</tt> be the run-time class of the client.
 * <li>For each interface <tt>i</tt> implemented by <em>rtClass</em>:
 * <ol>
 * <li>If <tt>i</tt> extends <em>RequestClient</em>:
 * <ol>
 * <li>For each public method <tt>j</tt> of <tt>i</tt>:
 * <ol>
 * <li>Add <tt>j</tt> to <tt>visibleMethods</tt>
 * </ol>
 * </ol>
 * </ol>
 * </ol>
 * <p>
 * 
 * This mechanism is provided so that clients may provide local services by
 * declaring them public without accidentally exposing these services to remote
 * clients.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/10/05 15:47:36 $)
 * @see osl.transport.TransportLayer
 * @see RequestSession
 * @see RequestException
 * @see RequestClient
 */

public class RequestHandler {

	/**
	 * Set to true if you want debugging output.
	 */
	public static final boolean DEBUG = false;

	/**
	 * The <em>TransportLayer</em> used to communicate with other request
	 * handlers.
	 */
	TransportLayer ourLayer = null;

	/**
	 * The <em>Scheduler</em> we should use to schedule our threads.
	 */
	Scheduler ourScheduler = null;

	/**
	 * The <em>NameService</em> which we consult for all naming operations.
	 */
	NameService ourNameservice = null;

	/**
	 * If true then resolve unknown classes arriving over the network.
	 */
	static boolean netLoader = false;

	/**
	 * This is a convenient constant which we'll need to build method tables for
	 * clients.
	 */
	static Class<?> rcClass = null;

	/**
	 * The default class constructor. Not much to do here.
	 */
	public RequestHandler() {
		try {
			rcClass = Class.forName("osl.handler.RequestClient");
		} catch (Exception e) {
			Debug.exit(e.toString());
		}
	}

	/**
	 * This method is called to initialize a <em>RequestHandler</em> instance
	 * once it has been instantiatied.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule handler threads.
	 * @param <b>T</b> A reference to the transport layer which should be used
	 *        to transmit and receive requests.
	 * @param <b>N</b> A reference to the name service which should be used to
	 *        lookup the <em>PhysicalAddress</em> associated with a particular
	 *        name.
	 */
	public void handlerInitialize(Scheduler S, TransportLayer T, NameService N) {
		ourScheduler = S;
		ourLayer = T;
		ourNameservice = N;

		if (System.getProperty("osl.foundry.netloader", "no").equals("yes")) {
			netLoader = true;
			NetLoader.initLoader();
		} else
			netLoader = false;
	}

	/**
	 * This method is called to open a new request handler session. This version
	 * assigns the first available <em>PhysicalAddress</em>.
	 * 
	 * @param <b>client</b> A reference to the object which will receive remote
	 *        requests for this session.
	 * @exception osl.handler.RequestException
	 *                Thrown if an error occurs while opening the session. An
	 *                error thrown here is usually caused by an exception in the
	 *                transport layer.
	 */
	public RequestSession handlerOpenSession(RequestClient client)
			throws RequestException {
		return handlerOpenSession(client, null);
	}

	/**
	 * This method is called to open a new request handler session. This version
	 * allows the caller to request that a particular <em>PhysicalAddress</em>
	 * be associated with the new session.
	 * 
	 * @param <b>client</b> A reference to the object which will receive remote
	 *        requests for this session.
	 * @param <b>request</b> The physical address which the handler should
	 *        attempt to associate with this session.
	 * @exception osl.handler.RequestException
	 *                Thrown if an error occurs while opening the session. An
	 *                error thrown here is usually caused by an exception in the
	 *                transport layer.
	 */
	public RequestSession handlerOpenSession(RequestClient client,
			PhysicalAddress request) throws RequestException {
		// A new request handler session is constructed as follows:
		// 1. Build the method table for the calling client.
		// 2. Create a new request session instance and initialize it.
		// 3. Attempt to create a new transport session using the
		// requested address (or the first available if request =
		// null). If successful, return the new client session back
		// to the calling client.

		Hashtable<String, Object> sessionMethods = null;
		RequestSession result = null;

		// These need to be in place first because a request session can
		// start receiving messages once the tranport layer instance has
		// been opened.
		sessionMethods = buildMethodTable(client);
		result = new RequestSession(client, sessionMethods, ourNameservice);

		try {
			if (request != null)
				result.transInstance = ourLayer.transportOpen(result, request);
			else
				result.transInstance = ourLayer.transportOpen(result);

			result.ourAddress = result.transInstance.transportGetAddress();

		} catch (TransportException e) {
			throw new RequestException("Error while creating new session", e);
		}

		// And now add the physical address to the name service...
		ourNameservice.nsAddAddress(result.ourAddress);

		// If we get here then we're in good shape so start the gc thread
		// for the new session and return it to the client.
		ourScheduler.scheduleThread(new Thread(result, "result"));
		return result;
	}

	/**
	 * Build a hashtable of all the externally visible methods for a client
	 * instance. The construction of this table is described by the algorithm
	 * above (i.e. the class documentation comment).
	 * 
	 * @param <b>client</b> A reference to the <em>RequestClient</em> for which
	 *        we are building this table.
	 */
	Hashtable<String, Object> buildMethodTable(RequestClient client)
			throws RequestException {
		// Discover the run-time class of the client and all the
		// interfaces implemented by the client. We have to get all the
		// interfaces because the client may implement multiple
		// interfaces, each of which extend RequestClient.
		Class<?> clientClass = client.getClass();
		Class<?>[] clientInts = findInterfaces(clientClass);
		Hashtable<String, Object> result = new Hashtable<String, Object>();
		Method[] theMeths = null;
		String methName = null;
		MethodStructureVector methObjs = null;
		MethodStructure[] refArray = null;
		Enumeration<String> e = null;
		int i, j;

		if (DEBUG)
			Debug.out
					.println("Building method table for new request client...");

		if (DEBUG)
			Debug.out.println("Client has class: " + clientClass + " with "
					+ clientInts.length + " interfaces");

		// Now scan each interface looking for something that extends
		// RequestClient. Once we find such an interface, include all of
		// its public methods in our method table.
		for (i = 0; i < clientInts.length; i++) {

			if (DEBUG)
				Debug.out.println("Checking interface " + clientInts[i]);

			if (rcClass.isAssignableFrom(clientInts[i])) {
				// If here then this interface extends RemoteClient so
				// incorporate all of its methods...
				try {
					theMeths = clientInts[i].getMethods();

					for (j = 0; j < theMeths.length; j++) {
						methName = theMeths[j].getName();

						if (result.containsKey(methName)) {
							if (DEBUG)
								Debug.out.println("Method table for "
										+ methName
										+ " already exists, appending...");

							((MethodStructureVector) result.get(methName))
									.insertElement(new MethodStructure(
											theMeths[j], theMeths[j]
													.getParameterTypes()));
						} else {
							if (DEBUG)
								Debug.out
										.println("Creating new method table for methods named "
												+ methName);

							methObjs = new MethodStructureVector();
							result.put(methName, methObjs);
							methObjs.insertElement(new MethodStructure(
									theMeths[j], theMeths[j]
											.getParameterTypes()));
						}
					}

					// Once we have all the public methods sorted into vectors,
					// recopy everything into arrays so that run-time searching
					// is faster.
					for (e = result.keys(); e.hasMoreElements();) {
						methName = e.nextElement();
						methObjs = (MethodStructureVector) result.get(methName);
						refArray = new MethodStructure[methObjs.size()];
						methObjs.copyInto(refArray);
						result.put(methName, refArray);
					}

				} catch (SecurityException E) {
					throw new RequestException(
							"Error building method table for new client: "
									+ client, E);
				}
			}
		}

		if (DEBUG)
			Debug.out.println("Done building method table");

		// Return the result
		return result;
	}

	/**
	 * A convenience method for discovering all the interfaces implemented by a
	 * particular class (including interfaces implemented by superclasses).
	 * 
	 * @param <b>C</b> The class to inspect.
	 * @return An array of type <em>Class</em> holding all the interfaces
	 *         implemented by this class.
	 */
	Class<?>[] findInterfaces(Class<?> C) {
		Vector<Object> result = new Vector<Object>();
		Class<?> next = null;
		Class<?>[] ints = null;
		int i = 0;

		while (C != null) {
			next = C.getSuperclass();
			ints = C.getInterfaces();

			for (i = 0; i < ints.length; i++)
				result.addElement(ints[i]);

			C = next;
		}

		Class<?>[] R = new Class[result.size()];
		Object[] foo = new Object[result.size()];
		result.copyInto(foo);
		for (i = 0; i < foo.length; i++)
			R[i] = (Class<?>) foo[i];

		return R;
	}

}
