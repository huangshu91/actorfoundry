package osl.manager;

import java.io.Serializable;

import kilim.pausable;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;

/**
 * This class defines the basic functionality of a foundry actor. All
 * user-written actors must inherit from this class. The purpose of this class
 * is to separate the basic services provided to actors from the actual
 * implementation of those services. This allows user-written actors to be used
 * with a wide variety of implementations.
 * <p>
 * 
 * All user-written actors have access to the following methods defined in this
 * class:
 * <p>
 * 
 * <table border=0>
 * <tr>
 * <td><b>self</b></td>
 * <td>
 * Returns the <em>ActorName</em> of this actor.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>send</b></td>
 * <td>
 * Interact asynchronously with another actor.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>call</b></td>
 * <td>
 * Interact with another actor using RPC semantics.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>create</b></td>
 * <td>
 * Create a new actor.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>migrate</b></td>
 * <td>
 * Request migration to another foundry node.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>cancelMigrate</b></td>
 * <td>
 * Cancel a previous migration request.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>invokeService</b></td>
 * <td>
 * Invoke a service on this node.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>destroy</b></td>
 * <td>
 * Remove this actor from the system immediately (i.e. without waiting for the
 * normal garbage collection process to remove the actor).</td>
 * </tr>
 * </table>
 * <p>
 * 
 * While the services described above are standard for all implementations of
 * the foundry, in certain instances the underlying system may wish to provide a
 * more extended set of services. The <em>extension</em> method calls the
 * <em>implExtension</em> method in the managing <em>ActorImpl</em>, which may
 * be overridden to provide a specific service. Thus, user-written actors may
 * use <em>extension</em> to access extensions provided by specific
 * implementations. For example, suppose an actor implementation implements a
 * "broadcast" extension with the prototype:
 * <p>
 * 
 * <blockquote>
 * 
 * <pre>
 * public void broadcastMsg(ActorName[] targets, String method, Object arg);
 * </pre>
 * 
 * </blockquote>
 * 
 * A user-written actor would be able to invoke this added functionality within
 * a method as follows:
 * <p>
 * 
 * <blockquote>
 * 
 * <pre>
 * extension(&quot;broadcastMsg&quot;, targetArray, method, arg);
 * </pre>
 * 
 * </blockquote>
 * <p>
 * 
 * where <tt>targetArray</tt> is of type <em>ActorName[]</em>, <tt>method</tt>
 * is of type <em>String</em>, and <tt>arg</tt> is of type <em>Object</em>.
 * 
 * @author Mark Astley
 * @version $Revision: 1.11 $ ($Date: 2000/01/13 02:40:09 $)
 * @see ActorManager
 * @see ActorName
 */

public class Actor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8423330864807158963L;

	/**
	 * A convenient constant which holds the class reference for this class.
	 */
	private static Class<?> classRef = null;

	static {
		try {
			setClassRef(Class.forName("osl.manager.Actor"));
		} catch (Exception e) {
			System.out.println("Error setting up classRef static field: " + e);
			System.exit(1);
		}
	}

	/**
	 * A reference to the class which implements our actor functionality. This
	 * reference is private to prevent user-written actors from (intentionally
	 * or otherwise) mangling themselves. This field is set by the constructor
	 * for this class.
	 */
	private ActorImpl implementor = null;

	/**
	 * The name of this actor. This field is private to avoid any mangling by
	 * user-written code.
	 */
	private ActorName self = null;

	/**
	 * A reference to the last request we sent. This can be used by user-written
	 * actors if they want to track asynchronous exceptions and their causes.
	 */
	protected ActorRequest lastRequest = null;

	/**
	 * The name of the "stdout" actor for this actor. This actor is always an
	 * implementation of the <em>StreamOutputActor</em> interface.
	 * 
	 * @see osl.manager.StreamOutputActor
	 */
	protected ActorName stdout = null;

	/**
	 * The name of the "stdin" actor for this actor. This actor is always an
	 * implementation of the <em>StreamInputActor</em> interface.
	 * 
	 * @see osl.manager.StreamInputActor
	 */
	protected ActorName stdin = null;

	/**
	 * This is a convenience method for converting a Character[] to a String.
	 * The methods associated with stdin normally return a Character[] which can
	 * be inconvenient to display. So this method is provided for quickly
	 * performing conversions.
	 * 
	 * @param <b>V</b> The <em>Character[]</em> to convert.
	 * @return A <em>String</em> representation of <b>V</b>.
	 */
	protected String caToString(Character[] V) {
		if ((V == null) || (V.length == 0))
			return "";
		else {
			char[] tmp = new char[V.length];

			for (int i = 0; i < V.length; i++)
				tmp[i] = V[i].charValue();

			return new String(tmp);
		}
	}

	/**
	 * The name of the "stderr" actor for this actor. This actor is always an
	 * implementation of the <em>StreamOutputActor</em> interface.
	 * 
	 * @see osl.manager.StreamOutputActor
	 */
	protected ActorName stderr = null;

	/**
	 * This table is used for private initialization between this actor and the
	 * creating actor implementation.
	 */
	// static Hashtable<Object, Object> initTable = new Hashtable<Object,
	// Object>();
	/**
	 * The only constructor for this class. By convention, the calling thread of
	 * this constructor must always be an extension of the <em>ActorImpl</em>
	 * class. We use this fact to initialize the private fields above so that
	 * user-written constructors may safely invoke actor services such as create
	 * and send.
	 */
	public Actor() {
		/*
		 * Thread caller = Thread.currentThread();
		 * 
		 * try { implementor = (ActorImpl) initTable.get(caller); self =
		 * implementor.self; stdin = implementor.context.stdin; stdout =
		 * implementor.context.stdout; stderr = implementor.context.stderr;
		 * 
		 * } catch(ClassCastException e) { // If we get here then the calling
		 * thread was not an instance of // ActorImpl. If this is the case then
		 * we can't initialize // ourselves properly so throw an exception.
		 * throw newRuntimeException(
		 * "Error: initTable does not contain creating actor implementation"); }
		 */
	}

	public void _init(ActorImpl control) {
		implementor = control;
		self = implementor.self;
		stdin = implementor.context.stdin;
		stdout = implementor.context.stdout;
		stderr = implementor.context.stderr;
	}

	/**
	 * The canonical implementation of create. This version allows a string
	 * argument for class name (relaxing the requirement that users provide a
	 * class reference for the actor they wish to create). The function of the
	 * method is to build an <em>ActorCreateRequest</em> structure and call the
	 * implementation to perform the creation.
	 * 
	 * @param <b>node</b> The node on which the new actor should be created. If
	 *        null then the local node is used.
	 * @param <b>className</b> A <em>String</em> giving the complete class name
	 *        of the new actor to create.
	 * @param <b>args</b> An array of arguments to pass to the constructor of
	 *        the new actor. Note: to invoke the default constructor, this
	 *        should be an array of length 0, NOT null.
	 * @return The <em>ActorName</em> of the new actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the behavior of the new actor is not a subclass
	 *                of <em>Actor</em>.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any other exception encountered
	 *                while attempting the create. Note that this exception may
	 *                also be thrown asynchronously.
	 */
	private ActorName createImpl(ActorManagerName node, String className,
			Object[] args) throws SecurityException, RemoteCodeException {
		try {
			return createImpl(node, Class.forName(className), args);
		} catch (ClassNotFoundException e) {
			throw new RemoteCodeException("Error creating new actor", e);
		}
	}

	/**
	 * The canonical implementation of create. The function of this method is to
	 * build an <em>ActorCreateRequest</em> structure and call the
	 * implementation to perform the creation. Note that the choice of
	 * implementation of the new actor is left to null. It is up to the managing
	 * actor implementation to decide what the implementation of the new actor
	 * should be. If necessary an implementation can add to an extension
	 * interface in order to provide methods for indicating what implementation
	 * should be specified for the new actor.
	 * 
	 * @param <b>node</b> The node on which the new actor should be created. If
	 *        null then the local node is used.
	 * @param <b>classType</b> A <em>Class</em> giving the type of the new actor
	 *        to instantiate. This should be a subclass of <em>Actor</em>,
	 *        otherwise the run-time system will throw an exception.
	 * @param <b>args</b> An array of arguments to pass to the constructor of
	 *        the new actor. Note: to invoke the default constructor, this
	 *        should be an array of length 0, NOT null.
	 * @return The <em>ActorName</em> of the new actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the behavior of the new actor is not a subclass
	 *                of <em>Actor</em>.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any other exception encountered
	 *                while attempting the create. Note that this exception may
	 *                also be thrown asynchronously.
	 */
	private ActorName createImpl(ActorManagerName node, Class<?> classType,
			Object[] args) throws SecurityException, RemoteCodeException {

		ActorCreateRequest req = new ActorCreateRequest(self, classType, null,
				args, node);

		return implementor.implCreate(req);
	}

	protected long getLastRequestID() {
		return implementor.getLastRequestID();
	}

	/**
	 * The canonical implementation of extension. The function of this method is
	 * to call an implementation-specific extension provided by our managing
	 * implementation. The implementation returns the result of invoking the
	 * extension, or an <em>ExtensionException</em> if either the extension is
	 * not supported, or an error occured during invcation.
	 * 
	 * @param <b>meth</b> The <em>String</em> name of the extension to invoke.
	 * @param <b>args</b> An array of arguments to pass to the invoked
	 *        extension. If the target method takes no arguments then this
	 *        should be an array of length 0.
	 * @return The <em>Object</em> returned by the invoked extension. The type
	 *         of this object depends on the extension invoked.
	 * @exception osl.manager.ExtensionException
	 *                Thrown as a wrapper for any error that may have occured
	 *                while attempting to invoke the extension.
	 */
	private Object extensionImpl(String meth, Object[] args)
			throws ExtensionException {
		return implementor.implExtension(meth, args);
	}

	/**
	 * The canonical implementation of invokeService. Node services are another
	 * mechanism for providing added functionality to a system. However, node
	 * services do not have access to actor internals. Thus services are
	 * somewhat restricted in the functionality that they may provide.
	 * 
	 * @param <b>name</b> The <em>ServiceName</em> of the service to invoke.
	 * @param <b>meth</b> The <em>String</em> name of the service method to
	 *        invoke.
	 * @param <b>args</b> An <em>Object[]</em> holding the arguments to be
	 *        passed to the service method.
	 * @return The <em>Object</em> reference returned by the service invocation.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no instance of the named service can be found on
	 *                this node.
	 * @exception osl.service.ServiceException
	 *                Thrown if the service throws an exception while processing
	 *                the request. This will wrap a
	 *                <em>NoSuchMethodException</em> if <em>meth</em> is not a
	 *                registered method of this service.
	 */
	private Object invokeServiceImpl(ServiceName name, String meth,
			Object[] args) throws ServiceNotFoundException, ServiceException {
		return implementor.implInvokeService(name, meth, args);
	}

	// /////////////////////////////////////////////////////////////
	// Methods accessible to user-level actors
	// /////////////////////////////////////////////////////////////
	/**
	 * Return the name of this actor. We provide an explicit accessor since the
	 * self variable is protected from access by outside packages.
	 */
	protected ActorName self() {
		return self;
	}

	// Variable arguments versions for the create method.
	/**
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any error which occurs while
	 *                performing the create.
	 * @deprecated This method is superfluous (and inefficient). Use the
	 *             "classType" version instead. E.g. replace
	 *             "osl.examples.helloworld.HelloActor" with
	 *             osl.examples.helloworld.HelloActor.class.
	 */
	protected ActorName create(String className, Serializable... args)
			throws SecurityException, RemoteCodeException {
		if (args == null || args.length == 0) {
			return createImpl((ActorManagerName) null, className, null);
		} else {
			return createImpl((ActorManagerName) null, className, args);
		}
	}

	protected ActorName create(Class<?> classType, Serializable... args)
			throws SecurityException, RemoteCodeException {
		if (args == null || args.length == 0) {
			return createImpl((ActorManagerName) null, classType, null);
		} else {
			return createImpl((ActorManagerName) null, classType, args);
		}
	}

	/**
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any error which occurs while
	 *                performing the create.
	 * @deprecated This method is superfluous (and inefficient). Use the
	 *             "classType" version instead. E.g. replace
	 *             "osl.examples.helloworld.HelloActor" with
	 *             osl.examples.helloworld.HelloActor.class.
	 */
	protected ActorName create(ActorManagerName node, String className,
			Serializable... args) throws SecurityException, RemoteCodeException {
		if (args == null || args.length == 0) {
			return createImpl(node, className, null);
		} else {
			return createImpl(node, className, args);
		}
	}

	protected ActorName create(ActorManagerName node, Class<?> classType,
			Serializable... args) throws SecurityException, RemoteCodeException {
		if (args == null || args.length == 0) {
			return createImpl(node, classType, null);
		} else {
			return createImpl(node, classType, args);
		}
	}

	// An inconsistency between the create and send request.
	// The difference also shows up in ActorMsgRequest and ActorCreateRequest
	// in that the ActorCreateRequest has to check for null arguments
	protected void sendByRef(ActorName dest, String meth, Serializable... args) {
		try {
			if (args == null) {
				implementor.implSend(dest, meth, zeroArray, false);
			} else {
				implementor.implSend(dest, meth, args, false);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error: unexpected exception: " + e);
		}
	}

	protected void send(ActorName dest, String meth, Serializable... args) {
		try {
			if (args == null) {
				implementor.implSend(dest, meth, zeroArray, true);
			} else {
				implementor.implSend(dest, meth, args, true);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error: unexpected exception: " + e);
		}
	}

	protected ActorMsgRequest msg(ActorName dest, String meth,
			Serializable... args) {
		ActorMsgRequest req = new ActorMsgRequest(self, dest, meth, args, true,
				true);
		return req;
	}

	protected ActorMsgRequest msgByRef(ActorName dest, String meth,
			Serializable... args) {
		ActorMsgRequest req = new ActorMsgRequest(self, dest, meth, args, true,
				false);
		return req;
	}

	@pausable
	protected Object join(Class<?> customer, ActorMsgRequest... msgs)
			throws RemoteCodeException {
		try {
			if (customer == null)
				throw new IllegalTargetException("join customer is null");

			return implementor.implJoin(msgs, customer);
		} catch (RemoteCodeException e) {
			throw e;
		} catch (IllegalTargetException e) {
			throw e;
		} catch (Exception e) {
			throw new RemoteCodeException("Error performing call:", e);
		}
	}

	// Var-args versions of call

	private static Object[] zeroArray = new Object[0];

	@pausable
	protected Object callByRef(ActorName dest, String meth,
			Serializable... args) throws RemoteCodeException {
		try {
			if (args == null) {
				return implementor.implCall(dest, meth, zeroArray, false);
			} else {
				return implementor.implCall(dest, meth, args, false);
			}
		} catch (RemoteCodeException e) {
			throw e;
		} catch (IllegalTargetException e) {
			throw e;
		} catch (Exception e) {
			throw new RemoteCodeException("Error performing call:", e);
		}
	}

	@pausable
	protected Object call(ActorName dest, String meth, Serializable... args)
			throws RemoteCodeException {
		try {
			if (args == null) {
				return implementor.implCall(dest, meth, zeroArray, true);
			} else {
				return implementor.implCall(dest, meth, args, true);
			}
		} catch (RemoteCodeException e) {
			throw e;
		} catch (IllegalTargetException e) {
			throw e;
		} catch (Exception e) {
			throw new RemoteCodeException("Error performing call:", e);
		}
	}

	/**
	 * This method allows the actor to signal that it wishes to be migrated to
	 * another machine. The actor isn't actually migrated until the current
	 * method completes. Multiple invocations of the migrate command may appear
	 * in an actor method, but the last actual invocation determines where the
	 * actor will be moved after the current method completes. An exception is
	 * thrown if an illegal <em>ActorManagerName</em> is given as argument.
	 * 
	 * @param <b>loc</b> The <em>ActorManagerName</em> where this actor should
	 *        be moved.
	 */
	protected void migrate(ActorManagerName loc) {
		if (loc == null)
			throw new NullPointerException("location argument must not be null");

		implementor.implMigrate(loc);
	}

	/**
	 * This method allows an actor to cancel an earlier migration request. This
	 * is done by issuing a migration request with the argument <em>null</em>.
	 * The same behavior could be achieved using <em>migrate</em> explicitly.
	 * Thus, this method is provided purely for convenience.
	 */
	protected void cancelMigrate() {
		implementor.implMigrate(null);
	}

	/**
	 * This method is used to receive asynchronous exceptions generated by
	 * earlier requests from this actor. For example, a message send may later
	 * generate an exception when the message is finally delivered to the
	 * target. Normally, user actors will override this method if they want to
	 * customize exception handling. By default, the exception is simply printed
	 * out, and no other action is taken.
	 * 
	 * @param <b>cause</b> A reference to the request that caused the exception.
	 * @param <b>e</b> The exception that was thrown.
	 */
	@pausable
	public void asynchException(ActorRequest cause, Exception e) {
		// Default behavior is to print the exception to the log.
		// Log.println("<Actor.asynchException>: received exception: " + e +
		// " with cause: " + cause);
		// Log.logExceptionTrace(e);
	}

	/**
	 * This method is used by actors which wish to be removed from the system.
	 * Normally, a fully-functional foundry node will include garbage collection
	 * so that Actors need not explicitly remove themselves. However, certain
	 * Actors may wish to short circuit garbage collection in order to
	 * immediately exit from the system. Upon calling this method, the actor is
	 * immediately removed from the system (i.e. this method normally will not
	 * return). Note that because normal garbage collection is not invoked, an
	 * actor may be removed even though it is accessible by other actors. Thus,
	 * if an actor attempts to send a message to a removed actor, it will
	 * receive a <em>RemoteCodeException</em> containing a delivery error.
	 * 
	 * @param <b>reason</b> A <em>String</em> giving a "reason" for the removal
	 *        of this actor. This string is normally appended to the log for the
	 *        actor before it is removed.
	 * @see osl.manager.ActorImpl#implDestroy
	 */
	protected void destroy(String reason) {
		implementor.implDestroy(reason);
	}

	// Variable arguments versions for the extension method.
	protected Object extension(String meth, Object... args)
			throws ExtensionException {
		if (args == null) {
			return extensionImpl(meth, zeroArray);
		} else {
			return extensionImpl(meth, args);
		}
	}

	protected Object invokeService(ServiceName name, String meth,
			Object... args) throws ServiceNotFoundException, ServiceException {
		if (args == null) {
			return invokeServiceImpl(name, meth, zeroArray);
		} else {
			return invokeServiceImpl(name, meth, args);
		}
	}

	public static void setClassRef(Class<?> classRef) {
		Actor.classRef = classRef;
	}

	public static Class<?> getClassRef() {
		return classRef;
	}

	protected boolean terminated() {
		return implementor.getSignalValue() == Signal.TERMINATE;
	}

	protected boolean paused() {
		return implementor.getSignalValue() == Signal.PAUSE;
	}

	protected boolean resumed() {
		return implementor.getSignalValue() == Signal.RESUME;
	}

	protected void signal(ActorName actor, Signal sig) {
		implementor.implSignal(actor, sig);
	}

}
