package osl.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import osl.manager.RemoteActorManager;
import osl.scheduler.Scheduler;
import osl.util.MethodStructure;
import osl.util.MethodStructureVector;

/**
 * This interface defines the methods required in order to implement a node
 * service. This amounts to an initialization function which is called after the
 * service is constructed, and an invocation method which is used to invoke the
 * service. Each service should also define a name for itself. It is up to
 * service providers to ensure that their <em>ServiceName</em>s are unique.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1998/08/31 18:59:02 $)
 * @see ServiceName
 */

public abstract class Service {
	/**
	 * A hashtable which holds the <em>Method</em> structures pointing to
	 * service-provided methods.
	 */
	protected Hashtable<String, MethodStructure[]> serviceMethods = null;

	// ///////////////////////////////////////////////////////////////////////
	// Service Interface Methods:
	//
	// These methods are expected to be implemented by the actual
	// service which extends this class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Initialize this service instance.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 * @param <b>M</b> A reference to the local actor manager which should be
	 *        used to register this service as well as interact with its actors
	 *        (for example, by delivering messages).
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error initializing the service.
	 */
	public abstract void serviceInitialize(Scheduler S, RemoteActorManager M)
			throws ServiceException;

	/**
	 * Return the name of this service.
	 * 
	 * @return A <em>ServiceName</em> structure representing the name of this
	 *         service.
	 */
	public abstract ServiceName serviceName();

	// ///////////////////////////////////////////////////////////////////////
	// Auxilliary Methods:
	//
	// These methods are visible only to the service extension
	// (except for serviceInvoke). The main purpose of these methods
	// is to allow the service to register its methods and to receive
	// invocations as they arrive.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Register a service method. Note that this must be done before the method
	 * may be called by remote objects. Note that ALL variants of the given
	 * method name are registered. That is, only one call is necessary to
	 * register an overloaded method.
	 */
	protected final void serviceRegisterMethod(String methodName) {
		// See if we need to build an extension table
		if (serviceMethods == null)
			serviceMethods = new Hashtable<String, MethodStructure[]>();

		// For each instance of the named method, add it to the extension
		// table in sorted order.
		Method[] theMeths = null;
		MethodStructureVector newMeths = new MethodStructureVector();
		MethodStructure[] copyArray = null;
		int i;

		// Look up and store the public methods of the actor we are about
		// to create
		theMeths = this.getClass().getMethods();

		for (i = 0; i < theMeths.length; i++)
			if (theMeths[i].getName().equals(methodName))
				newMeths.insertElement(new MethodStructure(theMeths[i],
						theMeths[i].getParameterTypes()));

		copyArray = new MethodStructure[newMeths.size()];
		newMeths.copyInto(copyArray);
		serviceMethods.put(methodName, copyArray);
	}

	/**
	 * Invoke a method in this service. The method to invoke must have
	 * previously been registered by the extension which implements this
	 * service. A service method consists of an otherwise protected method which
	 * an implementation wishes to export to its internal actor. Exceptions are
	 * returned by wrapping them in an instance of <em>ServiceException</em>.
	 * <p>
	 * 
	 * @param <b>name</b> The name of the service method to invoke.
	 * @param <b>args</b> An <em>Object[]</em> of arguments to pass to the
	 *        invoked method. If no arguments are required, this should be an
	 *        array of length 0 (i.e. NOT null). The number and format of the
	 *        arguments depends on the service method being invoked.
	 * @return An <em>Object</em> giving the result of the method invocation.
	 *         The type of this object depends on the method being invoked.
	 * @exception osl.service.ServiceException
	 *                Thrown as a wrapper for any error which occurs while
	 *                invoking the extension. Note that if the named method can
	 *                not be found, this exception wraps a
	 *                <em>NoSuchMethodException</em>.
	 */
	public final Object serviceInvoke(String name, Object[] args)
			throws ServiceException {
		MethodStructure[] potMeths = null;
		Method toInvoke = null;
		boolean found = false;

		// See if we need to rebuild our table
		if (serviceMethods == null)
			serviceMethods = new Hashtable<String, MethodStructure[]>();

		try {
			// Grab the extension we are supposed to invoke, barf if we
			// can't find any such extension
			potMeths = (MethodStructure[]) serviceMethods.get(name);

			if (potMeths == null)
				throw new NoSuchMethodException("No method matching \"" + name
						+ "\" in this service");

			for (int i = 0; (i < potMeths.length) && (!found); i++) {
				if (args == null) {
					if (potMeths[i].argTypes.length == 0) {
						found = true;
						toInvoke = potMeths[i].meth;
						break;
					} else
						continue;
				} else if (potMeths[i].argTypes == null)
					continue;
				else if (args.length != potMeths[i].argTypes.length)
					continue;

				found = true;
				toInvoke = potMeths[i].meth;
				for (int j = 0; j < potMeths[i].argTypes.length; j++)
					if ((args[j] != null)
							&& (!potMeths[i].argTypes[j].isInstance(args[j]))) {
						found = false;
						break;
					}
			}

			if (!found)
				throw new NoSuchMethodException("No method matching \"" + name
						+ "\" in this service");

			// Now invoke the extension and send back the return value
			return toInvoke.invoke(this, args);
		} catch (Exception e) {
			if (e instanceof ServiceException)
				throw (ServiceException) e;
			else if (e instanceof InvocationTargetException)
				throw new ServiceException(
						"exception returned from invoked method",
						((InvocationTargetException) e).getTargetException());
			else
				throw new ServiceException("Error invoking service method", e);
		}
	}

}
