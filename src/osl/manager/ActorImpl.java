package osl.manager;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import kilim.Task;
import kilim.pausable;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.MethodStructure;
import osl.util.MethodStructureVector;

/**
 * This class defines the basic functionality required of all classes which
 * provide actor implementations. An actor implementation provides the interface
 * used by the actor manager to do such things as deliver messages and
 * exceptions, or instruct the actor that it should rebuild its state after a
 * migration. Similarly, an actor implementation provides the interface by which
 * the <em>Actor</em> class translates user service requests into actual service
 * invocations on the <em>ActorManager</em>.
 * <p>
 * 
 * This class is an abstract extension of thread (rather than just an interface)
 * in order to solve a technical problem involving the instantiation of
 * user-written extensions of <em>Actor</em>. In particular, in order to allow
 * user-written actors to invoke actor operations within their constructors, it
 * is first necessary to properly initialize the <em>Actor</em> fields "self"
 * and "implementor". Moreover, these fields should have at least package
 * protection so that they are inaccessible to the user-written portion of an
 * actor. The easy solution would be to initialize these fields by passing
 * arguments to the actor constructor. However, since this must be done through
 * a user-written constructor then we immediately give the user access to
 * sensitive fields. The solution used is to take advantage of package level
 * protection and have the <em>Actor</em> constructor (which is always called
 * before any user-written constructor) consult package protected fields in the
 * <em>ActorImpl</em> class. In particular, the "self" field in this class must
 * be initialized before the actor implementation instantiates the user-written
 * actor. Moreover, the protected status of the fields given below SHOULD NOT be
 * weakened any further (e.g. made public) otherwise smart users will be able to
 * mangle these fields from their constructor.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.11 $ ($Date: 1998/10/05 15:47:38 $)
 * @see ActorManager
 * @see ActorName
 * @see Actor
 */

public abstract class ActorImpl extends Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6588303306253035951L;

	/**
	 * This field holds the name of the actor managed by this implementation.
	 */
	protected ActorName self = null;

	/**
	 * This field holds the run-time class of the actor managed by this
	 * implementation.
	 */
	protected Class<?> actorClass = null;

	/**
	 * The context passed in the initialize method of this actor.
	 */
	protected ActorContext context = null;

	/**
	 * The ID to associate with the next request. This is basically a timestamp.
	 */
	protected long nextID = 0;

	/**
	 * A hashtable which holds the <em>Method</em> structures pointing to
	 * extension methods. Note that since instances of <em>Method</em> are not
	 * serializable, this table must be rebuilt after migration. That is, the
	 * extending implementation will need to re-register each of its extension
	 * methods after migration.
	 */
	protected transient Hashtable<String, MethodStructure[]> extensionMethods = null;

	// ///////////////////////////////////////////////////////////////////////
	// Useful Methods
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Stamp a request structure with a timestamp and our ID.
	 */
	protected void stampRequest(ActorRequest req) {
		// PRAGMA [assert] Assert.assert(self != null,
		// "self is null, this will create a malformed request: " + req);
		req.originator = self;
		req.ID = nextID++;
	}

	/**
	 * Check if a message is formatted correctly for an "asynchException"
	 * message.
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> to check.
	 * @return <tt>true</tt> if the message is a valid "asynchException"
	 *         message, and <tt>false</tt> otherwise.
	 */
	public static final boolean formatAsynchException(ActorMsgRequest msg) {
		return ((msg != null) && (msg.method.equals("asynchException"))
				&& (msg.methodArgs != null) && (msg.methodArgs.length == 2)
				&& (msg.methodArgs[0] instanceof ActorRequest) && (msg.methodArgs[1] instanceof Exception));
	}

	protected long getLastRequestID() {
		return nextID - 1;
	}

	/**
	 * Build an asynchException message. It is still up to the caller to
	 * correctly stamp the message after it has been built.
	 * 
	 * @param <b>sender</b> The <em>ActorName</em> of the sender of the message.
	 * @param <b>receiver</b> The <em>ActorName</em> of the receiver of the
	 *        message.
	 * @param <b>cause</b> The <em>ActorRequest</em> giving the original request
	 *        which caused the exception.
	 * @param <b>E</b> The <em>Exception</em> which was generated as a result of
	 *        the request.
	 * @return An <em>ActorMsgRequest</em> formatted as an "asynchException"
	 *         message with the given <b>sender</b>, <b>receiver</b>,
	 *         <b>cause</b> and exception <b>E</b>.
	 */
	public static final ActorMsgRequest buildAsynchException(ActorName sender,
			ActorName receiver, ActorRequest cause, Exception E) {
		// PRAGMA [debug,osl.manager.ActorImpl]
		// Log.println("<ActorImpl.buildAsynchException> Building exception message with cause: "
		// + cause + " and error: " + E);
		Object[] msgArgs = new Object[2];
		msgArgs[0] = cause;
		msgArgs[1] = E;
		ActorMsgRequest req = new ActorMsgRequest(sender, receiver,
				"asynchException", msgArgs, false);
		return req;
	}

	/**
	 * Register an extension method. Note that this must be done before the
	 * method may be called by internal actors. Moreover, extension methods must
	 * be re-registered after every migration since instances of <em>Method</em>
	 * are not serializable. Note that ALL variants of the given method name are
	 * registered. That is, only one call is necessary to register an overloaded
	 * method.
	 */
	protected void registerExtension(String extensionName) {
		// See if we need to build an extension table
		if (extensionMethods == null)
			extensionMethods = new Hashtable<String, MethodStructure[]>();

		// PRAGMA [debug,osl.manager.ActorImpl] Log.println(this,
		// "<ActorImpl.registerExtension> Registering extension " +
		// extensionName);

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
			if (theMeths[i].getName().equals(extensionName)) {
				// PRAGMA [debug,osl.manager.ActorImpl] Log.println(this,
				// "<ActorImpl.registerExtension> Found method matching " +
				// extensionName + ", adding...");
				newMeths.insertElement(new MethodStructure(theMeths[i],
						theMeths[i].getParameterTypes()));
			}

		if (newMeths.size() == 0)
			throw new RuntimeException("ERROR: no methods found with name \""
					+ extensionName + "\"");

		copyArray = new MethodStructure[newMeths.size()];
		newMeths.copyInto(copyArray);
		extensionMethods.put(extensionName, copyArray);
	}

	/**
	 * Remove a previously registered extension method. Note that this method
	 * removes ALL overloaded copies of the method. This method has no effect if
	 * the named method was not registered.
	 */
	protected void removeExtension(String extensionName) {
		// If no extensions have been registered then return
		if (extensionMethods == null)
			return;

		// PRAGMA [debug,osl.manager.ActorImpl] Log.println(this,
		// "<ActorImpl.registerExtension> Removing extension " + extensionName);
		// Remove the method structure vector for this extension from the
		// hashtable.
		extensionMethods.remove(extensionName);
		return;
	}

	/**
	 * This method is used by an actor implementation to synchronize with the
	 * underlying Actor being created. This is necessary to relay private
	 * information between an actor and it's creator.
	 */
	protected void setCreatingThread(Thread caller, ActorImpl creator) {
		// Actor.initTable.put(caller, creator);
	}

	// ///////////////////////////////////////////////////////////////////////
	// Manager Access Methods:
	// These methods allow an actor implementation to access certain
	// methods defined in the ActorManager class. We require special
	// access methods because certain manager methods are protected
	// to prevent access to code written outside the manager package.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Call <em>actorCreate</em> in a manager.
	 * 
	 * @param <b>mgr</b> The manager on which <em>actorCreate</em> should be
	 *        invoked.
	 * @param <b>request</b> The <em>ActorCreateRequest</em> structure which
	 *        describes the new actor to be created.
	 * @return The <em>ActorName</em> of the newly created actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the class of new requested actor is not a
	 *                subclass of <em>Actor</em>, or if the class of the new
	 *                requested actor implementation is not a subclass of
	 *                <em>ActorImpl</em>.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown for any other error encountered while attempting to
	 *                perform the create. Note that this error may also be
	 *                thrown asynchronously (e.g. when performing a remote
	 *                create).
	 */
	protected ActorName mgrActorCreate(ActorManager mgr,
			ActorCreateRequest request) throws SecurityException,
			RemoteCodeException {
		return mgr.actorCreate(this, request);
	}

	/**
	 * Call <em>actorSend</em> in a manager.
	 * 
	 * @param <b>mgr</b> The manager on which <em>actorSend</em> should be
	 *        invoked.
	 * @param <b>message</b> The <em>ActorMsg</em> structure which indicates the
	 *        sender and receiver of the message, the method to invoke on the
	 *        receiver, and any arguments to pass to the target method.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown for any other error encountered while attempting to
	 *                perform the send. This usually indicates a local error in
	 *                the manager implementation rather than an error
	 *                encountered at the target of the message.
	 */
	protected void mgrActorSend(ActorManager mgr, ActorMsgRequest message)
			throws RemoteCodeException {
		mgr.actorSend(this, message);
	}

	/**
	 * Call <em>actorFatalError</em> in a manager.
	 * 
	 * @param <b>mgr</b> The manager on which <em>actorFatalError</em> should be
	 *        called.
	 * @param <b>thrower</b> The <em>ActorImpl</em> which is signalling the
	 *        fatal error.
	 * @param <b>e</b> The <em>Exception</em> which describes the fatal error
	 *        encountered in the actor.
	 */
	protected void mgrActorFatalError(ActorManager mgr, Exception e) {
		mgr.actorFatalError(this, e);
	}

	/**
	 * Call <em>actorMigrate</em> in a manager.
	 * 
	 * @param <b>mgr</b> The manager on which <em>actorMigrate</em> should be
	 *        called.
	 * @param <b>thrower</b> A reference to the <em>ActorImpl</em> which wishes
	 *        to be migrated.
	 * @param <b>where</b> The <em>ActorManagerName</em> of the manager where
	 *        this actor should be moved.
	 * @exception osl.manager.IllegalNodeException
	 *                Thrown if the <em>where</em> argument does not correspond
	 *                to a legal <em>ActorManagerName</em>.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if the target manager of the migration refuses to
	 *                accept the migrated actor.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown if the restart code after the migration throws an
	 *                exception. The remote copy is destroyed in this case and
	 *                the actor is treated as if it never migrated.
	 */
	protected void mgrActorMigrate(ActorManager mgr, ActorManagerName where)
			throws IllegalNodeException, RemoteRequestRefusedException,
			RemoteCodeException {
		mgr.actorMigrate(this, where);
	}

	/**
	 * Call <em>actorInvokeService</em> in a manager.
	 * 
	 * @param <b>mgr</b> The manager on which <em>actorInvokeService</em> should
	 *        be called.
	 * @param <b>serviceName</b> The <em>ServiceName</em> of the service to
	 *        invoke.
	 * @param <b>serviceArgs</b> An <em>Object</em> which represents the sole
	 *        argument to pass to the invocation function of the service.
	 * @return An <em>Object</em> representing the value returned by the service
	 *         invocation.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no instance of the named service can be found on
	 *                this node.
	 * @exception osl.service.ServiceException
	 *                Thrown if the service throws an exception while processing
	 *                the request.
	 */
	protected Object mgrActorInvokeService(ActorManager mgr,
			ServiceName serviceName, String meth, Object[] serviceArgs)
			throws ServiceNotFoundException, ServiceException {
		return mgr.actorInvokeService(this, serviceName, meth, serviceArgs);
	}

	// ///////////////////////////////////////////////////////////////////////
	// Manager Interface Functions:
	//
	// These methods are intended to be invoked by extensions of
	// the ActorManager abstract class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * This method is called by a manager to initialize an actor implementation
	 * after it is instantiated. The manager provides a reference to itself, a
	 * reference giving the name of the new actor, and a create request
	 * describing the new <em>Actor</em> that should be created and managed.
	 * After calling this method, a manager will call the run method in this
	 * class to start the actor executing. Note that the protection of this
	 * method prevents it from being called external to <tt>osl.manager</tt>.
	 * This was done to prevent arbitrary classes from initializing new actors.
	 * However, managers in different packages may invoke this method using
	 * <em>implInitialize</em>.
	 * <p>
	 * 
	 * @param <b>ourMgr</b> The <em>ActorManager</em> which should be used by
	 *        this actor implementation to invoke actor services.
	 * @param <b>you</b> The <em>ActorName</em> that should be used as the name
	 *        of the new actor.
	 * @param <b>rtClass</b> The run-time <em>Class</em> of the user-written
	 *        actor that should be instantiated by this implementation. This
	 *        class will always be an extension of the <em>Actor</em> class.
	 * @param <b>initArgs</b> The array of arguments to pass to the constructor
	 *        of the user-defined actor when it is instantiated.
	 * @exception java.lang.Exception
	 *                The actual type of exception thrown depends on the
	 *                implementation being used.
	 * @see osl.manager.ActorManager#implInitialize
	 */
	protected abstract void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) throws Exception;

	/**
	 * This method is called by a manager to deliver a new message to the local
	 * actor. Any exceptions resulting from the processing of this message
	 * should be sent back to the original caller using an "asynchException"
	 * message. This method is protected so that it has package level protection
	 * and therefore may not be invoked directly by user-written actor code.
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> structure to be delivered.
	 *        This structure must be maintained by the actor as it is required
	 *        if an exception is returned to the manager.
	 */
	protected abstract void actorDeliver(ActorMsgRequest msg);

	/**
	 * This method is called by the new manager of an actor implementation just
	 * after migration has occurred. The implementation is expected to perform
	 * any re-initialization necessary after a migration (e.g. rebuilding
	 * transient fields). This method will be called before the implementations
	 * thread is restarted.
	 * 
	 * @param <b>ourMgr</b> A reference to the new manager of the implementation
	 *        after migration has occurred.
	 */
	protected abstract void actorPostMigrateRebuild(ActorManager ourMgr);

	// ///////////////////////////////////////////////////////////////////////
	// Actor Interface Functions:
	//
	// These methods are intended to be invoked by the Actor class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Request a message to be sent. The message argument is forwarded to the
	 * manager. If the RPC field of the message is set to true then the caller
	 * is blocked while the RPC takes place. This method is protected so that it
	 * has package level protection and therefore may not be invoked directly by
	 * user-written actor code.
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> describing the message to
	 *        send.
	 * @return If this is an RPC request, then the return value from the message
	 *         call is returned. Otherwise, null is returned.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if the destination address of <b>msg</b> does not
	 *                correspond to a legal actor name. Note that it is not
	 *                necessary for implementations to verify that
	 *                <tt>msg.dest != null</tt> since this check is already
	 *                performed in <em>Actor.sendImpl</em>
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown only if this is an RPC exception which throws an
	 *                exception during invocation. An exception thrown in any
	 *                other case is an error.
	 * @see osl.manager.Actor#send(ActorName, String)
	 * @see osl.manager.Actor#call(ActorName, String)
	 */
	protected abstract Object implSend(ActorName dest, String meth,
			Object[] args, boolean byCopy) throws RemoteCodeException;

	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) throws RemoteCodeException {
		return null;
	}

	@pausable
	protected Object implJoin(ActorMsgRequest[] msgs, Class<?> customer)
			throws RemoteCodeException {
		return null;
	}

	/**
	 * Request a new actor to be created. The request argument is forwarded to
	 * the manager and the returned name is passed on to the <em>Actor</em>
	 * caller. This method is protected so that it has package level protection
	 * and therefore may not be invoked directly by user-written actor code.
	 * 
	 * @param <b>req</b> The <em>ActorCreateRequest</em> describing the new
	 *        actor to create.
	 * @return The <em>ActorName</em> of the new actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the behavior of the new actor is not a subclass
	 *                of <em>Actor</em>.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any other error that is
	 *                encountered while attempting the create. Note that such
	 *                errors may also be thrown asynchronously.
	 * @see osl.manager.Actor#create(String)
	 * @see osl.manager.Actor#create(Class)
	 * @see osl.manager.Actor#create(ActorManagerName, String)
	 * @see osl.manager.Actor#create(ActorManagerName, Class)
	 */
	protected abstract ActorName implCreate(ActorCreateRequest req)
			throws SecurityException, RemoteCodeException;

	/**
	 * Request that this actor wishes to be migrated to a new location. By
	 * convention, this call is meant to indicate a request rather than serving
	 * as a notice to immediately migrate the actor. Normally, the
	 * implementation will record the request but wait to migrate the actor
	 * until the current message being processed has completed. Once this occurs
	 * the actor is migrated and restarted at its new node. This method is
	 * protected so that it has package level protection and therefore may not
	 * be invoked directly by user-written actor code.
	 * 
	 * @param <b>loc</b> The <em>ActorManagerName</em> of the node to migrate
	 *        to.
	 * @see osl.manager.Actor#migrate
	 * @see osl.manager.Actor#cancelMigrate
	 */
	protected abstract void implMigrate(ActorManagerName loc);

	/**
	 * Request a service invocation on the named node service. This request is
	 * forwarded to the actor manager and the appropriate return value is
	 * provided. This method is protected so that it has package level
	 * protection and therefore may not be invoked directly by user-written
	 * actor code.
	 * 
	 * @param <b>name</b> The <em>ServiceName</em> describing the service to
	 *        invoke.
	 * @param <b>args</b> The <em>Object</em> argument to pass to the service
	 *        invocation function.
	 * @return The <em>Object</em> returned as a result of the service
	 *         invocation.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no instance of the named service can be found on
	 *                this node.
	 * @exception osl.service.ServiceException
	 *                Thrown if the service throws an exception while processing
	 *                the request.
	 * @see osl.manager.Actor#invokeService
	 */
	protected abstract Object implInvokeService(ServiceName name, String meth,
			Object[] args) throws ServiceNotFoundException, ServiceException;

	/**
	 * Request to remove this actor from the system. Normally, this method will
	 * not return as the actor is immediately removed from the system. Note that
	 * any actor garbage collection process is ignored in this call so that this
	 * actor may be removed even though it is accessible by other actors.
	 * 
	 * @param <b>reason</b> A <em>String</em> giving a "reason" for the removal.
	 *        This string should normally be appended to the log for the actor
	 *        before removing it from the system.
	 * @see osl.manager.Actor#destroy
	 */
	protected abstract void implDestroy(String reason);

	/**
	 * Invoke an extension provided by this implementation. Implementations
	 * register extensions using the <em>registerExtension</em> method. An
	 * extension consists of an otherwise protected method which an
	 * implementation wishes to export to its internal actor. Once registered,
	 * an extension may be invoked by calling <em>implExtension</em> which
	 * attempts to look up the method and make a direct call, returning any
	 * exception wrapped in an <em>ExtensionException</em>.
	 * <p>
	 * 
	 * @param <b>name</b> The name of the extension to invoke.
	 * @param <b>args</b> An <em>Object[]</em> of arguments to pass to the
	 *        invoked extension. If no arguments are required, this should be an
	 *        array of length 0 (i.e. NOT null). The number and format of the
	 *        arguments depends on the extension being invoked.
	 * @return An <em>Object</em> giving the result of the extension invocation.
	 *         The type of this object depends on the extension being invoked.
	 * @exception osl.manager.ExtensionException
	 *                Thrown as a wrapper for any error which occurs while
	 *                invoking the extension.
	 */
	protected Object implExtension(String name, Object[] args)
			throws ExtensionException {
		MethodStructure[] potMeths = null;
		Method toInvoke = null;
		boolean found = false;

		// See if we need to rebuild our table
		if (extensionMethods == null)
			extensionMethods = new Hashtable<String, MethodStructure[]>();

		try {
			// Grab the extension we are supposed to invoke, barf if we
			// can't find any such extension
			potMeths = extensionMethods.get(name);

			if (potMeths == null)
				throw new NoSuchMethodException("No extension matching \""
						+ name + "\" in this implementation");

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
				throw new NoSuchMethodException("No extension matching \""
						+ name + "\" in this implementation");

			// Now invoke the extension and send back the return value
			return toInvoke.invoke(this, args);
		} catch (Exception e) {
			if (e instanceof ExtensionException)
				throw (ExtensionException) e;
			else if (e instanceof InvocationTargetException)
				throw new ExtensionException(
						"exception returned from invoked method",
						((InvocationTargetException) e).getTargetException());
			else
				throw new ExtensionException("Error invoking extension", e);
		}

	}

	// ///////////////////////////////////////////////////////////////////////
	//
	// Miscellany
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Output to the system log that we are being GC'd. We mainly use this for
	 * testing at the moment.
	 * 
	 * @exception java.lang.Throwable
	 *                This exception is thrown so that any exceptions caught in
	 *                <em>Object.finalize()</em> are forwarded to the runtime
	 *                system.
	 */
	protected void finalize() throws Throwable {
		//Log.println(osl.foundry.FoundryStart.sysLog,
		//		"<ActorImpl.finalize> GC'ing Actor with name: " + self);
		super.finalize();
	}

	public static void setClassRef(Class<?> classRef) {
		ActorImpl.classRef = classRef;
	}

	public static Class<?> getClassRef() {
		return classRef;
	}

	/**
	 * A convenient constant which holds the class reference for this class.
	 */
	private static Class<?> classRef = null;

	static {
		try {
			setClassRef(Class.forName("osl.manager.ActorImpl"));
		} catch (Exception e) {
			System.out.println("Error setting up classRef static field: " + e);
		}
	}

    protected Signal signalValue = Signal.NONE;

    public Signal getSignalValue() {
            return signalValue;
    }

    protected void actorSignal(Signal sig) {
            this.signalValue = sig;
    }

    protected void mgrActorSignal(ActorManager mgr, ActorName actor, Signal sig) {
            mgr.actorSignal(this, actor, sig);
    }

    protected void implSignal(ActorName actor, Signal sig) {
    }

}
