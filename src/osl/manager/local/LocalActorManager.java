package osl.manager.local;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

import osl.foundry.FoundryStart;
import osl.handler.RequestException;
import osl.handler.RequestHandler;
import osl.handler.RequestID;
import osl.handler.RequestSession;
import osl.manager.Actor;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMigrationStructure;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.IllegalNodeException;
import osl.manager.IllegalTargetException;
import osl.manager.RemoteCodeException;
import osl.manager.RemoteRequestRefusedException;
import osl.manager.Signal;
import osl.manager.basic.BasicActorImpl;
import osl.manager.basic.StreamErrorActorImpl;
import osl.manager.basic.StreamInputActorImpl;
import osl.manager.basic.StreamOutputActorImpl;
import osl.nameservice.Name;
import osl.nameservice.simple.DefaultName;
import osl.scheduler.Scheduler;
import osl.service.Service;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.service.shell.ShellActorImpl;
import osl.util.Queue;

/**
 * This class defines the basic implementation of the <em>ActorManager</em>
 * class. The purpose of this class is to serve as a reference implementation
 * for operating the foundry. This class was designed with modularity in mind so
 * that new implementations could be created simply by extending this class.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.16 $ ($Date: 1999/03/06 21:18:37 $)
 * @see BasicActorImpl
 * @see osl.manager.ActorManager
 * @see osl.manager.ActorImpl
 */

public class LocalActorManager extends ActorManager {

	/**
	 * Version information for this actor manager implementation.
	 */
	public String AM_VERSION = "LocalActorManager v1.0, ActorFoundry v"
			+ FoundryStart.foundryVersion;

	/**
	 * The initial number of "service" threads to create in the manager. A
	 * service thread is used to hand-off a local actor request which is to be
	 * serviced on-node. This is done because local calls are usually filtered
	 * directly into the code of the target actor (which could therefore block
	 * indefinitely with all the usual problems).
	 */
	public static final int numServiceThreads = 10;

	/**
	 * The length of time that a service thread should wait before killing
	 * itself off (currently 5 minutes). If a service thread is not used again
	 * within this timeout period, then after its next use it will not add
	 * itself back to the service queue. This is done as a simple way to cull
	 * the service queue when not much is happening in a node. The queue will
	 * automatically build itself back up during periods of increased activity.
	 */
	public static final long SERVICE_TIMEOUT = 300000;

	/**
	 * The scheduler for this actor manager. The scheduler is in charge of
	 * scheduling all the threads associated with an actor manager.
	 */
	// protected Scheduler actorScheduler = null;
	/**
	 * The request handler implementation to use for creating request sessions
	 * for this manager. The basic implementation only creates one session for
	 * interacting with remote entities.
	 */
	// protected RequestHandler ourHandler = null;
	/**
	 * The session used by this actor manager to interact with remote managers.
	 */
	// protected RequestSession session = null;
	/**
	 * The default actor name which is reserved for special use by this manager.
	 * Typically, a manager uses this name in order to invoke functions which
	 * require an originator (e.g. actorCreate). Classes which extend
	 * ActorManager should be aware of this use of the name and code around it
	 * appropriately. This name is assigned during the initialization of the
	 * actor manager.
	 */
	protected ActorName defaultActorName = null;

	/**
	 * This field hashes <em>ActorName</em>s to the <em>ActorImpl</em> instances
	 * they are associated with. In actuality, managed actors are stored using
	 * the <em>ActorEntry</em> inner class. Note that we can't necessarily
	 * assume that local actors are instances of <em>BasicActorImpl</em>.
	 */
	// protected Hashtable<ActorName, ActorEntry> managed_actors = null;
	/**
	 * This hashtable maintains the current set of registered services available
	 * to local actors.
	 */
	protected Hashtable<ServiceName, Service> localServices = null;

	/**
	 * The name of this actor manager. This name is created during
	 * initialization after the <em>RequestSession</em> for this actor has been
	 * instantiated.
	 */
	protected ActorManagerName managerName = null;

	/**
	 * This table hashes RequestID's to the requests associated with the ID.
	 * During requestHandler exception handling, we use this information to
	 * figure out which request caused the exception and therefore which actor
	 * should receive the exception message. An independent thread periodically
	 * sweeps this table clean of any expired request IDs.
	 */
	// protected Hashtable<RequestID, ActorMsgRequest> requestMap = null;
	/**
	 * The queue of waiting "service" threads. When a service needs to be
	 * handled asynchronously, the first thread on this queue is grabbed and
	 * assigned the task. If the queue is empty then the caller spawns a new
	 * "service" thread and assigns it the task. The new service thread is
	 * automatically added to the queue once it has completed its task.
	 */
	protected Queue<ServiceThread> serviceQueue = null;

	/**
	 * The default constructor. Initialize all fields that can be initialized
	 * before the managerInitialize function is called.
	 */
	public LocalActorManager() {
		// managed_actors = new Hashtable<ActorName, ActorEntry>();
		localServices = new Hashtable<ServiceName, Service>();
		// requestMap = new Hashtable<RequestID, ActorMsgRequest>();
		// serviceQueue = new Queue<ServiceThread>();
	}

	public void managerInitialize(Scheduler S, RequestHandler R)
			throws RequestException {
	}

	// ///////////////////////////////////////////////////////////////////
	// Implementation of abstract ActorManager functions
	// ///////////////////////////////////////////////////////////////////
	/**
	 * Initialize a new actor manager. This method should be called before the
	 * actor manager is used to manage a collection of actors.
	 * <p>
	 * 
	 * @param <b>S</b> The <em>Scheduler</em> instance which should be used to
	 *        schedule all threads required by the manager. Most of these
	 *        threads will correspond to actors. Thus, it is advantageous to use
	 *        a scheduler customized to efficiently handle actors.
	 * @param <b>R</b> The <em>RequestHandler</em> instance which should be used
	 *        for interactions between this manager and other managers in the
	 *        system.
	 * @exception osl.handler.RequestException
	 *                Thrown if an error is encountered while opening a new
	 *                <em>RequestHandler</em> session.
	 */
	public void managerInitialize(Scheduler S) throws RequestException {
		try {
			Name temp;

			// Save fields
			// PRAGMA [assert] Assert.afAssert(S != null, "scheduler is null");
			// PRAGMA [assert] Assert.afAssert(R != null,
			// "request handler is null");
			actorScheduler = S;
			// ourHandler = R;

			// Print startup message
			System.out.println(AM_VERSION);
			nsID = new AtomicInteger();

			// Now create a request handler session for use by this manager
			// session = ourHandler.handlerOpenSession(this);

			// Good now create our set of default names
			temp = this.generateName();
			// session.handlerRegister(temp);
			managerName = new ActorManagerName(ActorManagerName.MGR_BASIC, temp);
			temp = this.generateName();
			// session.handlerRegister(temp);
			defaultActorName = new ActorName(temp);

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("New NS name for manager is: " + temp);

			// Register the ServiceThread class with the security manager
			/*
			 * SecurityManager mgr = System.getSecurityManager(); if ((mgr !=
			 * null) && (mgr instanceof FoundrySecurityManager)) {
			 * ((FoundrySecurityManager) mgr) .addPrivilegedClass(Class
			 * .forName("osl.manager.basic.BasicActorManager$ServiceThread"));
			 * //
			 * Class.forName("osl.manager.basic.BasicActorManager$ServiceThread"
			 * )); }
			 */

			// Last, start our cleanup thread running
			// startCleanup();
		} catch (Exception e) {
			// Any exception here gets packaged and thrown as a request
			// exception unless it is already a request exception.
			if (e instanceof RequestException)
				throw (RequestException) e;
			else
				throw new RequestException(
						"Error initializing BasicActorManager", e);
		}
	}

	/**
	 * This method is called by a local actor to request the creation of a new
	 * actor. The type of the new actor, the node it should be created on, as
	 * well as the arguments that should be passed to its constructor are
	 * encapsulated in the <em>request</em> structure. The name of the new actor
	 * is returned as the result of this call. Semantically speaking, once this
	 * method completes, the returned name is henceforth a valid target for
	 * receiving messages. As a side-effect, the ID field of the
	 * <em>request</em> argument is assigned a value unique relative to all
	 * other local requests. This value may be used to disambiguate any
	 * asynchronous exceptions caused by this request.
	 * <p>
	 * 
	 * If an exception is thrown by this method (asynchronously or otherwise)
	 * then the returned actor name is no longer valid and any messages sent to
	 * it will be discarded. The following asynchronous exceptions may be
	 * thrown:
	 * <p>
	 * 
	 * <ul>
	 * <li><b>BehaviorNotFoundException</b> - Thrown if the classes necessary to
	 * instantiate the new actor cannot be found (locally or otherwise).
	 * <li><b>NoSuchMethodException</b> - Thrown if no constructor can be found
	 * which matches the argument list provided in this request.
	 * <li><b>IllegalNodeException</b> - Thrown if the target manager for the
	 * creation request cannot be found.
	 * <li><b>RemoteRequestRefusedException</b> - Thrown if the target manager
	 * for the creation refuses the request.
	 * <li><b>RemoteCodeException</b> - Thrown if the constructor invoked to
	 * construct the new actor threw an exception before completing.
	 * </ul>
	 * <p>
	 * 
	 * @param <b>request</b> The <em>ActorCreateRequest</em> structure which
	 *        describes the new actor to be created.
	 * @return The <em>ActorName</em> of the newly created actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the class of the new requested actor is not a
	 *                subclass of <em>Actor</em>, or if the class of the new
	 *                requested actor implementation is not a subclass of
	 *                <em>ActorImpl</em>.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown for any other error encountered while attempting to
	 *                perform the create. Note that this error may also be
	 *                thrown asynchronously (e.g. when performing a remote
	 *                create).
	 */
	protected ActorName actorCreate(ActorImpl caller, ActorCreateRequest request)
			throws SecurityException, RemoteCodeException {

		try {
			// Two sanity checks first
			if (!(Actor.getClassRef().isAssignableFrom(request.behToCreate)))
				throw new SecurityException(
						"Error: only subclasses of Actor may be created!");
			if (!(ActorImpl.getClassRef()
					.isAssignableFrom(request.implToCreate)))
				throw new SecurityException(
						"Error: only subclasses of ActorImpl may be used to implement new actors!");

			// ActorManagerName target = request.site;

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Entered actorCreate...");

			/*
			 * if ((target != null) && (!target.equals(managerName))) { // This
			 * is a remote create. We handle this as follows: // -- For the
			 * moment we just block the caller until the remote // call
			 * completes. Eventually we will change this so that it // does the
			 * remote creation with latency hiding (ahhhhh).
			 * 
			 * // PRAGMA //
			 * [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager
			 * ] // Log.println("Sending remote request to create actor");
			 * 
			 * ActorName created = (ActorName) session.handlerRPCRequest(
			 * target.managerName, "managerCreate", request, null);
			 * 
			 * // Return the name of the new actor return created; }
			 */

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Creating new name, actorname, and implementation");
			// Create a new name for the actor and instantiate a new
			// implementation for it. The implementation we instantiate is
			// specified by the request.
			Name newName = this.generateName();
			ActorImpl newImp = (ActorImpl) request.implToCreate.newInstance();
			ActorName newAct = new ActorName(newName, newImp);
			// ActorEntry newEntry = new ActorEntry(newImp);

			// Register the new actor for logging before we initialize it.
			// Note that we do this BEFORE the new actor is initialized.
			// This is done in case there are any log.println(this, ...)
			// calls in the initialization function.
			// Log.logThread("Actor", newImp);
			// session.handlerRegister(newName);

			// synchronized (managed_actors) {
			// managed_actors.put(newAct, newEntry);
			// newEntry.in(Thread.currentThread());
			newAct.in(Thread.currentThread());
			// }

			// Note that we pass a clone of the original request to avoid
			// sharing problems between the tag tables of the caller and the
			// new actor.
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Cloning original request");
			ActorCreateRequest copyReq = (ActorCreateRequest) request.clone();
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Initializing implementation");
			implInitialize(newImp, newAct, copyReq);
			// newEntry.out(Thread.currentThread());
			newAct.out(Thread.currentThread());

			// Schedule the thread for execution.
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Scheduling actor");
			// actorScheduler.scheduleThread(newImp);
			newImp.setScheduler(actorScheduler);
			// newImp.start();
			if ((newImp instanceof ShellActorImpl)
					|| (newImp instanceof StreamInputActorImpl)
					|| (newImp instanceof StreamOutputActorImpl)
					|| (newImp instanceof StreamErrorActorImpl)) {
				// actorScheduler.scheduleThread(newImp);
				final ActorImpl finalNewImpl = newImp;
				new Thread(new Runnable() {
					public void run() {
						finalNewImpl._runExecute();
					}
				}, "Long-running actor").start();
			}
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Exiting actorCreate...");

			// Return the name of the new actor to the caller
			return newAct;
		} catch (RemoteCodeException e) {
			throw e;
		} catch (SecurityException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw new RemoteCodeException("Error performing create", e
					.getTargetException());
		} catch (Exception e) {
			// Log.println("<BasicActorManager.actorCreate> Logging create error");
			// Log.logExceptionTrace(e);
			throw new RemoteCodeException("Error performing create", e);
		}
	}

	/**
	 * This method is called by a local actor to send a message to another
	 * actor. The sender and receiver of the message, as well as the method to
	 * invoke and any arguments to pass are encapsulated in the <em>message</em>
	 * structure. Note that all messages are sent asynchronously so that method
	 * completion does not indicate message delivery. As a side-effect, the ID
	 * field of the <em>message</em> argument is assigned a value unique
	 * relative to all other local requests. This value may be used to
	 * disambiguate any asynchronous exceptions caused by this request.
	 * <p>
	 * 
	 * If an exception is returned by this method then the message structure is
	 * never sent. For asynchronous exceptions, only the
	 * <b>RemoteCodeException</b> corresponds to a message structure which
	 * should be viewed as "delivered". All other asynchronous exceptions should
	 * be interpreted as indicating that the original message structure was
	 * never sent. The following asynchronous exceptions may be thrown:
	 * <p>
	 * 
	 * <ul>
	 * <li><b>NoSuchMethodException</b> - Thrown if no method matching the
	 * target method and arguments can be found in the target actor.
	 * <li><b>IllegalTargetException</b> - Thrown if the target actor name is
	 * not legal. This may be because a sender attempted to spoof the name.
	 * <li><b>RemoteRequestRefusedException<b> - Thrown if the manager which
	 * manages the target actor refuses the request to deliver the message.
	 * <li><b>RemoteCodeException</b> - Thrown if the the target actor throws an
	 * exception while processing the message. In this case the message was
	 * delivered but the state of the actor depends on the exception thrown. For
	 * example, a fatal exception might indicate that the target actor is
	 * corrupted and has stopped processing messages.
	 * </ul>
	 * <p>
	 * 
	 * @param <b>message</b> The <em>ActorMsg</em> structure which indicates the
	 *        sender and receiver of the message, the method to invoke on the
	 *        receiver, and any arguments to pass to the target method.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if the target of the send is null or does not
	 *                correspond to a legal actor name.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown for any other error encountered while attempting to
	 *                perform the send. This usually indicates a local error in
	 *                the manager implementation rather than an error
	 *                encountered at the target of the message.
	 */
	protected void actorSend(ActorImpl caller, ActorMsgRequest message)
			throws RemoteCodeException {

		// PRAGMA [assert] Assert.afAssert(message.valid(),
		// "message to send is invalid");

		// ActorEntry entry = null;
		// ServiceThread newTask = null;

		// PRAGMA
		// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
		// Log.println("<BasicActorManager.actorSend> Starting send");

		// One quick check. If the target is "defaultActorName" then just
		// log the message. The "defaultActorName" is a dummy name which
		// can't receive any messages.
		if (message.receiver.equals(defaultActorName)) {
			// Log.println(FoundryStart.sysLog,
			// "<BasicActorManager.actorSend> Default name received message: " +
			// message);
			return;
		}

		// This is simple since most of the work is actually handled by a
		// service thread. If the send is local then:
		//
		// 1. Get a new service thread to perform the work.
		// 2. Clone the local message to be delivered so that we don't
		// have sharing problems between local actors.
		// 3. Assign the toDeliver field in the service thread.
		// 4. Notify the service thread.
		// 5. Return to the caller.
		//
		// otherwise make the remote call to the home of the actor as
		// usual.
		try {
			// synchronized (managed_actors) {
			// entry = managed_actors.get(message.receiver);

			// if (entry != null) {
			/*
			 * // PRAGMA [debug,osl.manager.ActorManager,osl.manager.basic
			 * .BasicActorManager]Log.println(
			 * "<BasicActorManager.actorSend> Actor is local, grabbing task thread"
			 * ); newTask = getTask(); // PRAGMA [debug,osl.manager.ActorManager
			 * ,osl.manager.basic.BasicActorManager]
			 * Log.println("<BasicActorManager.actorSend> HERE 1");
			 * newTask.toDeliver = (ActorMsgRequest) message.clone(); //
			 * PRAGMA[debug,osl.manager.ActorManager,osl.manager.basic.
			 * BasicActorManager]
			 * Log.println("<BasicActorManager.actorSend> HERE 2");
			 * newTask.toDeliverTarget = entry; // PRAGMA [debug,osl.manager
			 * .ActorManager,osl.manager.basic.BasicActorManager]
			 * Log.println("<BasicActorManager.actorSend> HERE 3");
			 * entry.in(newTask.us); // PRAGMA [debug,osl.manager.ActorManager
			 * ,osl.manager.basic.BasicActorManager]Log.println(
			 * "<BasicActorManager.actorSend> Task prepared to handle delivery"
			 * );
			 */
			// entry.in(Thread.currentThread());
			// }
			// }
			// We test again so that we can exit the synchronized block
			// (above) as quickly as possible.
			// if (entry != null) {
			if (message.receiver.getActor() != null) {
				message.receiver.in(Thread.currentThread());

				// rajesh: replaced message cloning (message.clone()) with
				// passing reference
				if (message.byCopy) {
					implDeliver(message.receiver.getActor(),
							(ActorMsgRequest) message.clone());
				} else {
					implDeliver(message.receiver.getActor(), message);
				}
				message.receiver.out(Thread.currentThread());

				/*
				 * synchronized (newTask) { newTask.notify(); // PRAGMA
				 * [debug,osl
				 * .manager.ActorManager,osl.manager.basic.BasicActorManager]
				 * Log.println(
				 * "<BasicActorManager.actorSend> Task notified, exiting...");
				 * 
				 * }
				 */

				return;
			}
		} catch (Exception e) {

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("Error checking for local actor" + e);

			// Have to be careful about how we cleanup here. If entry.in
			// was successfully executed then we need to remove the entry,
			// otherwise this actor can't be migrated (and other bad things
			// might happen as well).
			System.err.println(e.getMessage());
			if ((message.receiver.getActor() != null))
			// && (newTask != null)
			{
				// entry.out(newTask.us);
				message.receiver.out(Thread.currentThread());
			}

			return;
		}

		/*
		 * try { // Destination is not local. Forward the request to the current
		 * // manager of this actor. We synchronize on requestMap to avoid //
		 * the race condition where an exception is triggered and // delivered
		 * to handlerException before we have a chance to put // the request in
		 * the map. synchronized (requestMap) { // PRAGMA //
		 * [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
		 * // Log.println("Sending message to remote manager"); RequestID
		 * trackID = session.handlerRequest(message.receiver .getName(),
		 * "managerDeliver", message); requestMap.put(trackID, message); } }
		 * catch (Exception e) {
		 * 
		 * // If we end up here then the remote request failed so it is // safe
		 * to assume that the message was never sent. In this case // we just
		 * deliver the exception to our caller rather than // building an
		 * asynchException message. if (e instanceof RequestException) {
		 * RequestException sub = (RequestException) e; if ((sub.detail
		 * instanceof MalformedNameException) || (sub.detail instanceof
		 * NameNotFoundException) || (sub.detail instanceof NoBindingException))
		 * { // In this case, the target name was screwed up so throw an //
		 * illegal target exception throw new IllegalTargetException(
		 * "message dest not a legal actor name"); } else if (sub.detail
		 * instanceof TransportException) { // In this case, we have a
		 * transport-level error. Rethrow // the error as a RemoteCodeException.
		 * These errors should // really be fatal, or should be handled here
		 * rather than // throw to the user. throw new RemoteCodeException(
		 * "transport layer error while handling send", sub.detail); } else //
		 * Anything else just gets repackaged as a remote code // exception
		 * throw new RemoteCodeException( "unknown error while handling send",
		 * sub.detail); } else { //Log.println(
		 * "<BasicActorManager.actorSend> Returning send exception, local trace follows:"
		 * ); // Log.logExceptionTrace(e); throw new
		 * RemoteCodeException("Error while performing send:", e); } }
		 */
	}

	/**
	 * This method is called by an actor implementation to report that it has
	 * encountered a fatal error. The caller should pass an a reference to
	 * itself in order to be safely terminated by the manager. For the basic
	 * actor manager, the behavior is to report the error on the managers log
	 * file and kill the actor (thus removing it from the system).
	 * 
	 * @param <b>thrower</b> The <em>ActorImpl</em> signalling the fatal error.
	 *        Usually, this actor will be removed from the system.
	 * @param <b>e</b> The <em>Exception</em> which describes the fatal error
	 *        encountered in the actor.
	 */
	protected void actorFatalError(ActorImpl caller, Exception e) {
		try {
			// An actor has failed spectacularly. For this implementation we
			// remove the actor from our local managed table, print an error
			// message, and then kill the thread associated with this actor.
			ActorName next = implGetName(caller);
			// ActorEntry entry = null;
			// ActorImpl imp = null;
			// ActorImpl thrower = caller;
			// Queue<ActorName> names = new Queue<ActorName>();

			// Note that we have to find all the aliases to the actor so
			// that we don't accidentally leave a reference in the
			// managed_actors table.

			// TODO: worry about finding aliases later. They are not being used
			// anywhere except ShellActorImpl
			/*
			 * for (Enumeration<?> N = managed_actors.keys();
			 * N.hasMoreElements();) { next = (ActorName) N.nextElement(); entry
			 * = managed_actors.get(next); imp = entry.impl;
			 * 
			 * if ((imp != null) && (imp.equals(thrower))) names.enqueue(next);
			 * }
			 */

			// if (!names.empty()) {
			/*
			 * while (!names.empty()) { next = (ActorName) names.dequeue();
			 * managed_actors.remove(next);
			 * session.handlerRemove(next.getName()); }
			 */
			// Log.println("<BasicActorManager.actorFatalError>: Fatal Error "
			// + e + " in actor " + next +
			// ", killing this actor, stack trace follows:");
			// Log.logExceptionTrace(e);
			// session.handlerRemove(next.getName());
			System.err
					.println("<BasicActorManager.actorFatalError>: Fatal Error "
							+ e
							+ " in actor "
							+ next
							+ ", killing this actor, stack trace follows:");
			e.printStackTrace();
			// } else {
			// Log.println("<BasicActorManager.actorFatalError>: Actor not in local name table.");
			// }
			// thrower.stop();
		} catch (Exception q) {
			// Ignore all exceptions so the manager doesn't crash
			// Log.println("<BasicActorManager.actorFatalError>: Exception while processing fatal error: "
			// + Q + " " + Q.getMessage());
			// Log.logExceptionTrace(Q);
			System.err
					.println("<BasicActorManager.actorFatalError>: Exception while processing fatal error: "
							+ q + " " + q.getMessage());
			e.printStackTrace();
		}
	}

	public void actorMigrate(ActorImpl caller, ActorManagerName where)
			throws IllegalNodeException, RemoteRequestRefusedException,
			RemoteCodeException {
	}

	/**
	 * This method is called by a local actor in order to access a locally
	 * provided node service. The name of the service must be specified together
	 * with any arguments required to correctly invoke the service. An arbitrary
	 * object is returned as a result of the service call. Note that
	 * <em>Object</em>s are used to specify both the service arguments as well
	 * as the return type so that very general services may be specified. It is
	 * up to the caller to correctly cast return values to their appropriate
	 * type.
	 * 
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
	public Object actorInvokeService(ActorImpl caller, ServiceName serviceName,
			String meth, Object[] serviceArgs) throws ServiceNotFoundException,
			ServiceException {

		// Ok now attempt to locate the service
		Service serv = localServices.get(serviceName);
		if (serv == null)
			throw new ServiceNotFoundException(
					"Error: couldn't locate local service with name "
							+ serviceName);

		try {
			return serv.serviceInvoke(meth, serviceArgs);
		} catch (Exception e) {
			if (e instanceof ServiceException)
				throw (ServiceException) e;
			else
				throw new ServiceException("Error invoking service "
						+ serviceName, e);
		}
	}

	// ///////////////////////////////////////////////////////////////////
	// Methods required by RemoteActorManager interface
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method is called to request that a new local actor be created and
	 * managed by the target actor. Both the ID properties and the site field of
	 * the request are ignored and the actor is always created locally. Unlike
	 * <em>actorCreate</em>, the caller may optionally specify the name of the
	 * new actor. This is done to allow more efficient implementations of remote
	 * creation where a name is immediately returned to a local actor although
	 * the creation itself is taking place asynchronously at a remote manager.
	 * Normally this method is called using the RPC features of the request
	 * handler. This is done so that the name of the new actor may be returned
	 * and so that the caller may determine when it is legal to send the new
	 * actor messages. However, this method MAY be called asynchronously (e.g.
	 * if the caller already knows the name of the new actor because the
	 * <em>newName</em> field has been specified) in which case other means will
	 * need to be used to determine when it is legal to send the actor messages.
	 * 
	 * @param <b>request</b> An <em>ActorCreateRequest</em> structure describing
	 *        the new actor to be created. The ID and site fields are ignored
	 *        and the new actor is always created locally.
	 * @param <b>newName</b> An optional argument indicating the desired
	 *        <em>ActorName</em> of the new actor. If null then a new actor name
	 *        is generated, otherwise an attempt is made to use the provided
	 *        name.
	 * @return The <em>ActorName</em> of the newly created actor. This actor is
	 *         a legal target for messages once the caller has received its
	 *         name.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if this manager decides to refuse the creation
	 *                request.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any general exception which occurs
	 *                while attempting to create the new actor.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if the specified value for <em>newName</em> is
	 *                non-NULL but does not correspond to a legal actor name.
	 * @exception java.lang.IllegalAccessException
	 *                Thrown if the requested class of the new actor does not
	 *                inherit from <em>Actor</em>.
	 */
	public ActorName managerCreate(ActorCreateRequest request, ActorName reqName)
			throws RemoteRequestRefusedException, RemoteCodeException,
			IllegalTargetException, IllegalAccessException {

		try {
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println(FoundryStart.sysLog,
			// "Received remote create request: " + request + " request name: "
			// + reqName);

			// First verify that the requested behavior really is an actor
			if (!(Actor.getClassRef().isAssignableFrom(request.behToCreate)))
				throw new IllegalAccessException(
						"Error: only subclasses of Actor may be created!");
			if (!(ActorImpl.getClassRef()
					.isAssignableFrom(request.implToCreate)))
				throw new IllegalAccessException(
						"Error: only subclasses of ActorImpl may be used to implement the new actor!");

			// Good, now perform the local creation and return the new name.
			// Note that we assume that the create will be local if this
			// method is invoked so we don't bother checking the site field of
			// the request.

			// Create a new name for the actor and instantiate a new
			// implementation for it.
			Name newName = null;
			ActorName newAct = null;
			ActorImpl newImp = null;
			// ActorEntry newEntry = null;

			newImp = (ActorImpl) request.implToCreate.newInstance();
			// If necessary, generate a name for the new actor
			if (reqName == null) {
				newName = this.generateName();
				newAct = new ActorName(newName, newImp);
			} else {
				newAct = reqName;
				reqName.setActor(newImp);
				newName = reqName.getName();
			}

			// Now instantiate an implementation for the actor
			// newEntry = new ActorEntry(newImp);

			// Register the new actor for logging before we initialize it.
			// Note that we do this BEFORE the new actor is initialized.
			// This is done in case there are any log.println(this, ...)
			// calls in the initialization function.
			// Register the new actor for logging...
			// Log.logThread("Actor", newImp);
			// session.handlerRegister(newName);

			/*
			 * synchronized (managed_actors) { managed_actors.put(newAct,
			 * newEntry); newEntry.in(Thread.currentThread()); }
			 */
			newAct.in(Thread.currentThread());

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println(FoundryStart.sysLog, "Initializing implementation");
			implInitialize(newImp, newAct, request);
			newAct.out(Thread.currentThread());

			// Schedule the thread for execution.
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println(FoundryStart.sysLog, "Scheduling actor");
			newImp.setScheduler(actorScheduler);
			if ((newImp instanceof ShellActorImpl)
					|| (newImp instanceof StreamInputActorImpl)
					|| (newImp instanceof StreamOutputActorImpl)
					|| (newImp instanceof StreamErrorActorImpl)) {
				// actorScheduler.scheduleThread(newImp);
				final ActorImpl finalNewImpl = newImp;
				new Thread(new Runnable() {
					public void run() {
						finalNewImpl._runExecute();
					}
				}, "Long-running actor").start();
			}
			// newImp.start();
			// actorScheduler.scheduleThread(newImp);

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println(FoundryStart.sysLog,
			// "New actor scheduled, returning");

			// Return the name of the new actor to the caller
			return newAct;
		} catch (IllegalTargetException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw e;
		} catch (RemoteCodeException e) {
			throw e;
		} catch (SecurityException e) {
			throw e;
		} catch (InvocationTargetException e) {
			throw new RemoteCodeException("Error performing create", e
					.getTargetException());
		} catch (Exception e) {
			throw new RemoteCodeException("Error performing create", e);
		}
	}

	/**
	 * This method is called to indicate that a new message should be delivered
	 * to a local actor. This method is normally called asynchronously using the
	 * exception handling mechanism of the request layer. Because messages are
	 * processed by actors asynchronously, a separate method is used to return
	 * exceptions resulting from actor message processing.
	 * <p>
	 * 
	 * @param <b>del</b> The <em>ActorMsg</em> structure to deliver to a local
	 *        actor.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if either the specified target actor name is
	 *                malformed, or the target is not managed locally.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if the local manager decides to refuse the remote
	 *                request.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any other exception encountered
	 *                while attempting to deliver the message.
	 */
	public void managerDeliver(ActorMsgRequest del)
			throws IllegalTargetException, RemoteRequestRefusedException,
			RemoteCodeException {

		// ActorEntry entry = null;

		// PRAGMA
		// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
		// Log.println(FoundryStart.sysLog,
		// "<BasicActorManager.managerDeliver> Handling deliver request");

		// If the target is "defaultActorName" then just log the message.
		// The "defaultActorName" is a dummy name which can't receive any
		// messages.
		if (del.receiver.equals(defaultActorName)) {
			// Log.println(FoundryStart.sysLog,
			// "<BasicActorManager.actorSend> Default name received message: " +
			// del);
			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println(FoundryStart.sysLog,
			// "<BasicActorManager.managerDeliver> Done handling deliver request");
			return;
		}

		// If the sender and receiver are the same, and the method is
		// _manager_resume, then this is a resume message for a migration.
		// Start the thread for the actor and dump the message. Note that
		// we don't need to add our thread to the access list because this
		// actor hasn't been started yet (and therefore can't migrate
		// while we are in the process of restarting it).
		if (del.method.equals("_manager_Resume")
				&& del.sender.equals(del.receiver)) {

			// Since this is a special message never seen by the user, we
			// handle exceptions transparently. In particular, any
			// exception we get here is logged to the system log and the
			// local actor is removed.
			try {
				// PRAGMA
				// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
				// Log.println(FoundryStart.sysLog,
				// "<BasicActorManager.managerDeliver> Processing migration resume message");

				// entry = managed_actors.get(del.receiver);

				// PRAGMA [assert] Assert.afAssert(entry,
				// "migrated actor should have managed_actors entry but doesn't");
				// PRAGMA
				// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
				// Log.println(FoundryStart.sysLog,
				// "<BasicActorManager.managerDeliver> Scheduling actor...");

				// Schedule the actor for execution so it can start processing
				// messages.
				// PRAGMA [assert] Assert.afAssert(!entry.impl.isAlive(),
				// "migrated thread has already been restarted");
				// Log.logThread("Actor", entry.impl);
				// actorScheduler.scheduleThread(entry.impl);
				actorScheduler.scheduleThread(del.receiver.getActor());
			} catch (Exception e) {
				// Something bad happened while resuming the actor. Remove
				// the actor from our managed table and log the error to the
				// system log.
				try {
					// managed_actors.remove(del.receiver);
					// session.handlerRemove(del.receiver.getName());
				} catch (Exception f) {
					// These are ignored, but logged
					// Log.println(FoundryStart.sysLog,
					// "Exception cleaning up for migration resume error: " +
					// f);
				}

				// Now log the error
				// Log.println(FoundryStart.sysLog,
				// "Error resuming migrated actor, actor has been removed: " +
				// e);
			}

			return;
		}

		// Synchronize on the managed actors table and add our thread to
		// the access list (this prevents the actor from being removed
		// while we are delivering the message).
		try {
			/*
			 * synchronized (managed_actors) { entry =
			 * managed_actors.get(del.receiver); Assert .afAssert(entry,
			 * "Message was delivered to manager which doesn't manage actor!");
			 * entry.in(Thread.currentThread()); }
			 */

			del.receiver.in(Thread.currentThread());
			implDeliver(del.receiver.getActor(), del);
			del.receiver.out(Thread.currentThread());
		} catch (Exception e) {
			// Before we process the exception it's important to make sure
			// that we remove our entry if necessary. Otherwise this actor
			// might be stuck indefinitely.
			// if (entry != null)
			// entry.out(Thread.currentThread());
			del.receiver.out(Thread.currentThread());
			// NOTES: Any exception here is automatically delivered to the
			// caller by the request handler layer (or directly if we are
			// being called locally through our RemoteActorManager
			// interface).
			if (e instanceof IllegalTargetException)
				throw (IllegalTargetException) e;
			else if (e instanceof RemoteCodeException)
				throw (RemoteCodeException) e;
			else
				throw new RemoteCodeException("Error delivering message:", e);
		}

		// PRAGMA
		// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
		// Log.println(FoundryStart.sysLog,
		// "<BasicActorManager.managerDeliver> Done handling deliver request");
	}

	public void managerMigrate(ActorMigrationStructure mig)
			throws RemoteRequestRefusedException, RemoteCodeException {
	}

	/**
	 * Called externally to register a new node service. The service provider is
	 * responsible for providing a unique service name. Any existing service
	 * with the same name is removed.
	 * 
	 * @param <b>sName</b> The <em>ServiceName</em> of the new service.
	 * @param <b>S</b> A reference to the new <em>Service</em>.
	 */
	public void managerRegisterService(ServiceName sName, Service S) {
		// Simple: just store the named service locally
		localServices.put(sName, S);
	}

	/**
	 * Called externally to remove a node service. An exception is thrown if the
	 * named service does not exist.
	 * 
	 * @param <b>sName</b> The name of the service to remove.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no service with the given name is associated
	 *                with this manager.
	 */
	public void managerRemoveService(ServiceName sName)
			throws ServiceNotFoundException {
		Service which = localServices.get(sName);

		if (which == null)
			throw new ServiceNotFoundException("No local service " + sName);

		localServices.remove(sName);
	}

	/**
	 * Called by other foundry modules (or remote managers) to invoke a local
	 * service. This equivalent to the <em>actorInvokeService</em> method except
	 * that no check is made to ensure that the caller is an actor.
	 * 
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
	public Object managerInvokeService(ServiceName serviceName, String meth,
			Object[] serviceArgs) throws ServiceNotFoundException,
			ServiceException {

		// Attempt to locate and invoke the service.
		Service serv = localServices.get(serviceName);
		if (serv == null)
			throw new ServiceNotFoundException(
					"Error: couldn't locate local service with name "
							+ serviceName);

		try {
			return serv.serviceInvoke(meth, serviceArgs);
		} catch (Exception e) {
			if (e instanceof ServiceNotFoundException)
				throw (ServiceNotFoundException) e;
			else if (e instanceof ServiceException)
				throw (ServiceException) e;
			else
				throw new ServiceException("Error invoking service "
						+ serviceName, e);
		}
	}

	/**
	 * Returns the name of this actor manager.
	 */
	public ActorManagerName managerGetName() {
		return managerName;
	}

	// ///////////////////////////////////////////////////////////////////
	// Required by the RequestClient interface
	// ///////////////////////////////////////////////////////////////////

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
			RequestID id) {
		/*
		 * try { // Check if this request is in our local request map. If it is
		 * // then build and send a request message to the appropriate //
		 * originator. Any exception triggered here is ignored as we // really
		 * don't have any one to deliver the exception to. ActorRequest orig =
		 * requestMap.get(id);
		 * 
		 * if (orig != null) { requestMap.remove(id); Object[] errArgs = new
		 * Object[2]; ActorMsgRequest errMsg = new
		 * ActorMsgRequest(defaultActorName, orig.originator, "asynchException",
		 * errArgs); errArgs[0] = orig; errArgs[1] = except;
		 * 
		 * actorSend(null, errMsg); } } catch (Exception e) { //
		 * Log.println(FoundryStart.sysLog, //
		 * "Error processing handler exception: " + e); }
		 */
	}

	// ///////////////////////////////////////////////////////////////////
	// Extensions to the ActorManager interface
	//
	// The methods may be called by ActorImpls to gain access to
	// extended functionality. Note that we have to make these
	// methods public so that they may be accessed (i.e. we can't use
	// the same tricks we used for the other "actor" methods.
	//
	// ///////////////////////////////////////////////////////////////////
	/**
	 * Create an alias for an actor. A new <em>ActorName</em> is created and
	 * linked to the caller actor implementation. Henceforth, this name may be
	 * used as a target for messages to the calling implementation.
	 * <p>
	 * 
	 * N.B.: This will only work for non-migrating actors. That's not to say
	 * that an aliased actor can't be migrated, but strange things will happen
	 * if that DOES occur. Have to formalize this at some later date!!!!
	 * <p>
	 * 
	 * @return An <em>ActorName</em> which may be used as an alias for the
	 *         calling actor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the calling thread is not an instance of
	 *                <em>ActorImpl</em>, or if the calling thread is not
	 *                currently managed by this actor manager.
	 */
	public ActorName actorCreateAlias(ActorImpl caller) {
		// ActorName next = null;
		// ActorEntry entry = null;
		Name newName = null;
		ActorName newActName = null;
		// boolean done = false;

		// Determine if we manage this thread or not.
		/*
		 * for (Enumeration<?> N = managed_actors.keys(); N.hasMoreElements() &&
		 * (!done);) { next = (ActorName) N.nextElement(); entry =
		 * managed_actors.get(next);
		 * 
		 * if ((entry.impl != null) && (entry.impl.equals(caller))) done = true;
		 * }
		 */

		// TODO Don't think this can happen
		/*
		 * if (!done) throw new SecurityException(
		 * "ERROR: calling ActorImpl not managed by this manager");
		 */

		// Generate a new name for this actor and link it to the current
		// implementation.
		newName = this.generateName();
		newActName = new ActorName(newName, caller);

		/*
		 * try { session.handlerRegister(newName); } catch
		 * (MalformedNameException e) { throw new
		 * RuntimeException("Error registering name: " + e); }
		 */
		// managed_actors.put(newActName, entry);
		// And return the alias to the caller
		return newActName;

	}

	// ///////////////////////////////////////////////////////////////////
	// Useful utility methods
	// ///////////////////////////////////////////////////////////////////
	/**
	 * This method is used to start (or restart) the requestMap cleanup thread.
	 */
	/*
	 * void startCleanup() { actorScheduler.scheduleThread(new Thread(new
	 * CleanupReqMap(), "requestMapCleanup")); }
	 */

	/**
	 * This method grabs the next free <em>ServiceThread</em> from the
	 * <em>serviceQueue</em>, initializes its fields, and returns it to the
	 * caller. If the queue is empty then <em>numServiceThreads</em> new threads
	 * are added to the queue. It is the callers responsibility to awaken the
	 * thread by notifying it.
	 */
	synchronized ServiceThread getTask() {
		ServiceThread next = null;
		Thread T = null;

		if (serviceQueue.empty())
			for (int i = 0; i < numServiceThreads; i++) {
				next = new ServiceThread(this);
				T = new Thread(next, "Service" + i);
				next.us = T;

				actorScheduler.scheduleThread(T);
				serviceQueue.enqueue(next);
			}

		next = serviceQueue.dequeue();

		return next;
	}

	// ///////////////////////////////////////////////////////////////////
	// Inner Classes
	// ///////////////////////////////////////////////////////////////////
	/**
	 * This class implements a cleanup thread that is used to cleanup the
	 * requestMap table when old requests have become invalid (i.e. they have
	 * either completed without errors or have generated exceptions which have
	 * been handled locally). Removal of old requests is actually done in two
	 * phases to avoid a race condition where we happen to remove an entry
	 * before the handlerException method has a chance to process an exception
	 * associated with the entry.
	 */
	// class CleanupReqMap implements Runnable {
	//
	// /**
	// * This is the timeout value (in milliseconds) between cleanup phases.
	// * We set this pretty high so that we don't waste all our time scanning
	// * for garbage.
	// */
	// final long GC_TIMEOUT = 30000;
	//
	// /**
	// * This hashtable holds all the old request IDs to be removed during the
	// * next phase.
	// */
	// Hashtable<RequestID, RequestID> nextRemoval = new Hashtable<RequestID,
	// RequestID>();
	//
	// /**
	// * Wake up every GC_TIMEOUT seconds and clean out all the entries in
	// * nextRemoval which are also in requestMap.
	// */
	// public void run() {
	// try {
	// Enumeration<RequestID> e = null;
	// RequestID task = null;
	//
	// while (true) {
	// // Sleep until the next phase
	// Thread.sleep(GC_TIMEOUT);
	//
	// // Remove everything scheduled for the current phase
	// for (e = nextRemoval.keys(); e.hasMoreElements();) {
	// task = e.nextElement();
	// requestMap.remove(task);
	// }
	//
	// // Reset and copy all expired IDs for removal in the next
	// // phase
	// nextRemoval.clear();
	// for (e = requestMap.keys(); e.hasMoreElements();) {
	// task = e.nextElement();
	// nextRemoval.put(task, task);
	// }
	//
	// }
	// } catch (Throwable e) {
	// // Any error conditions will probably kill this thread so as a
	// // default we always tell our manager to restart us if we die
	// // for some strange reason. We also print out the reason for
	// // dying.
	// // Log.println("Cleanup thread died because of: " + e);
	// startCleanup();
	// }
	// }
	// }
	/**
	 * This class defines a "service" thread, which is used to handle
	 * asynchronous tasks for the <em>BasicActorManager</em>. This is done in
	 * order to free up <em>ActorImpl</em> threads which call one of the "actor"
	 * functions in the manager. We want to avoid cases where the calling thread
	 * is blocked indefinitely because it makes a down call into another
	 * <em>ActorImpl</em> which is forced to synchronize. This also avoids the
	 * case where a thread deadlocks itself by making an upcall into the manager
	 * followed by a later downcall into the same actor.
	 * <p>
	 * 
	 * Instances of this class are maintained in a queue in the
	 * <em>BasicActorManager</em>. When a new task needs to be handled, the
	 * first <em>ServiceThread</em> on the queue is assigned the task. After the
	 * task is completed, the thread automatically adds itself back to the
	 * queue.
	 * <p>
	 * 
	 * The actual tasks which are performed by an instance of this class are
	 * predetermined:
	 * <p>
	 * 
	 * <ul>
	 * <li>Deliver a message to a local actor.
	 * <li>Initialize a new local actor implementation.
	 * </ul>
	 */
	protected class ServiceThread implements Runnable {
		/**
		 * The thread that runs this object. This must be set by our creator. We
		 * need this field ahead of time so that our scheduler can set our
		 * access status correctly inside an <em>ActorEntry</em>.
		 */
		Thread us;

		/**
		 * The last time we were used. If the current time minus this time is
		 * greater than the timeout value SERVICE_TIMEOUT, then this thread will
		 * not re-add itself to the service queue. This is a simple way to cull
		 * the service queue of threads if they aren't being utilized. The queue
		 * will build itself back up again in times of high usage.
		 */
		long lastUsed;

		/**
		 * The actor manager we are performing tasks for.
		 */
		ActorManager parent;

		/**
		 * If this thread is to deliver a new local message, then this field
		 * holds the message to deliver. Otherwise, this field should be null.
		 */
		ActorMsgRequest toDeliver = null;

		/**
		 * If this thread is to deliver a new local message, then this field
		 * holds the <em>ActorEntry</em> for the local target actor. Otherwise,
		 * this field should be null.
		 */
		ActorName toDeliverTarget = null;

		/**
		 * If this thread is to initialize a new local actor implementation,
		 * then this field contains the creation request to give to the new
		 * actor. Otherwise, this field should be null.
		 */
		ActorCreateRequest toCreate = null;

		/**
		 * If this thread is to initialize a new local actor implementation,
		 * then this field contains the actor name of the new actor. Otherwise,
		 * this field should be null.
		 */
		ActorName toCreateName = null;

		/**
		 * Constructor requires a reference to the creating manager since "this"
		 * will actually refer to the local inner class instance.
		 */
		ServiceThread(ActorManager P) {
			parent = P;
			lastUsed = System.currentTimeMillis();
		}

		/**
		 * Run method for this class. Note that we have to immediately check our
		 * local fields because we may not be scheduled until AFTER we have
		 * actually received a task. That is, we might need to start working
		 * right away, rather than going to sleep on the local queue. We also
		 * add ourselves to be logged to the system log. This allows someone to
		 * make sense of things during debugging.
		 */
		public void run() {
			// Add ourselves to the system log
			/*
			 * try { Log.logThread("System", Thread.currentThread()); } catch
			 * (IOException e) { Debug.exit("Error writing to system log" + e);
			 * }
			 */
			// Spin until the usage of this thread goes below
			// SERVICE_TIMEOUT
			while (true) {
				synchronized (this) {
					if ((toDeliver == null) && (toCreate == null))
						try {
							this.wait();
						} catch (Exception e) {
							// Error, we were probably interrupted incorrectly
							// Log.println("<BasicActorManager.ServiceThread.run> Error: interrupted while waiting for assignment, removing this service thread");
						}
				}

				if (toDeliver != null)
					deliverMessage(toDeliver);
				else if (toCreate != null) {
				} else {
					// Error, we were awoken without given a task to do
					// Log.println("<BasicActorManager.ServiceThread.run> Error: awoken with no task, removing this service thread");
					return;
				}

				if ((System.currentTimeMillis() - lastUsed) > SERVICE_TIMEOUT) {
					// Log.println("<BasicActorManager.ServiceThread.run> Thread use decreasing, removing this thread");
					return;
				} else {
					lastUsed = System.currentTimeMillis();
					toDeliver = null;
					toDeliverTarget = null;
					toCreate = null;
					toCreateName = null;
					serviceQueue.enqueue(this);
				}
			}
		}

		/**
		 * This method delivers a message to a local actor. Any exception
		 * encountered here is sent to the original caller using an
		 * "asynchException" message.
		 */
		void deliverMessage(ActorMsgRequest reqCopy) {

			// PRAGMA
			// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
			// Log.println("<BasicActorManager.ServiceThread.deliverMessage> Assigned message delivery for request ID ("
			// + reqCopy.originator + ", " + reqCopy.ID + ")");

			try {

				// Note that we don't have to synchronize on managed_actors
				// here because the task handoff in actorSend already does
				// this for us. We DO have to remember to remove our access
				// when we're done though. Because of the way the handoff is
				// done, we can also assume that the actor is local while we
				// are making the delivery.

				// PRAGMA
				// [debug,osl.manager.ActorManager,osl.manager.basic.BasicActorManager]
				// Log.println("<BasicActorManager.ServiceThread.deliverMessage> Delivering message...");

				// Deliver the message to the target
				implDeliver(toDeliverTarget.getActor(), reqCopy);

				// Remove our access and return
				toDeliverTarget.out(us);
				return;
			} catch (Exception e) {

				// Make sure we remove ourselves from the access table in case
				// it didn't get done above.
				toDeliverTarget.out(us);

				// Any exception received here is delivered as an
				// "asynchException" message to the sending actor. Note that
				// because we use the defaultActorName as the sender of the
				// message, any exception which results from THIS message will
				// simply be logged.

				// Build the exception message
				Object[] errArgs = new Object[2];
				ActorMsgRequest errMsg = new ActorMsgRequest(defaultActorName,
						reqCopy.sender, "asynchException", errArgs);
				errArgs[0] = reqCopy;
				errArgs[1] = e;

				// Log.println("<BasicActorManager.ServiceThread.deliverMessage> Delivering asynchException: "
				// + e);
				// Log.println("<BasicActorManager.ServiceThread.deliverMessage> Original message is: "
				// + reqCopy);

				try {
					actorSend(null, errMsg);
				} catch (Exception j) {
					// Any error here is just logged
					// Log.println("<BasicActorManager.ServiceThread.deliverMessage> Error attempting to deliver asynchException, stack trace follows: ");
					// Log.logExceptionTrace(j);
				}
			}
		}
	}

	@Override
	protected void actorSignal(ActorImpl signaler, ActorName actor, Signal sig) {
		if (actor.getActor() != null) {
			actor.in(Thread.currentThread());
			implSignal(actor.getActor(), sig);
			actor.out(Thread.currentThread());
			return;
		}
	}

	AtomicInteger nsID;

	// / Rajesh: a spoof of NameService generateName in order to disable
	// distribution
	public Name generateName() {
		try {
			// We generate a fresh name by creating a DefaultName
			// structure and storing it in our hash table with a new,
			// empty vector of bound addresses.
			DefaultName newName = new DefaultName(null, nsID.getAndIncrement());
			return newName;
		} catch (Exception e) {
			throw new RuntimeException("ERROR: can't create name: " + e);
		}
	}

	public void actorEscape(ActorName actor) {
	}

}
