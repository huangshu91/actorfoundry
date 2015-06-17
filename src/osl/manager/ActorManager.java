package osl.manager;

import osl.handler.RequestException;
import osl.handler.RequestHandler;
import osl.manager.basic.StreamErrorActorImpl;
import osl.manager.basic.StreamInputActorImpl;
import osl.manager.basic.StreamOutputActorImpl;
import osl.scheduler.Scheduler;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.service.shell.ShellActorImpl;

/**
 * This abstract class defines the basic methods necessary for implementing an
 * actor manager. An actor manager is responsible for providing the basic actor
 * services (e.g. sending messages, or creating other actors) to a collection of
 * local actors. Moreover, a manager shields its local actors from interactions
 * with external entities. That is, all interactions with local actors must go
 * through the actor manager.
 * <p>
 * 
 * The <em>ActorManager</em> class is also expected to implement the
 * <em>RemoteActorManager</em> interface which defines the mechanism by which
 * external entities (except for actors) interact with a local actor manager.
 * Typically, the <em>RemoteActorManager</em> interface is passed as the client
 * interface for any <em>RequestHandler</em> sessions opened by an actor
 * manager. Each actor manager also has an <em>ActorManagerName</em> which
 * uniquely defines the manager relative to all other managers in the system (or
 * at least relative to all other actor managers using the same implementation).
 * An actor manager's name is used by other actor managers or local actors to
 * request location-specific services such as remote creation or migration.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/01/19 18:43:32 $)
 * @see Actor
 * @see ActorManagerName
 * @see RemoteActorManager
 * @see osl.scheduler.Scheduler
 * @see osl.handler.RequestHandler
 */

public abstract class ActorManager implements RemoteActorManager {

	/**
	 * The scheduler for this actor manager. The scheduler is in charge of
	 * scheduling all the threads associated with an actor manager.
	 */
	protected Scheduler actorScheduler = null;
	protected static ActorManager instance = null;
	
	protected boolean garbageCollector = false;
	
	public void setCollector() {
		this.garbageCollector = true;
	}

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
	public abstract void managerInitialize(Scheduler S, RequestHandler R)
			throws RequestException;

	// ///////////////////////////////////////////////////////////////////
	// Actor Access Methods:
	// These methods allow actor manager implementations to access
	// the methods defined in the ActorImpl class. We require
	// special access methods because all of the ActorImpl methods
	// are protected to prevent user-written extensions from
	// accessing them directly.
	// ///////////////////////////////////////////////////////////////////

	/**
	 * Call the initialization method of an actor implementation.
	 * 
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
	 */
	protected void implInitialize(ActorImpl actor, ActorName you,
			ActorCreateRequest req) throws Exception {
		actor.actorInitialize(this, you, req);
	}

	/**
	 * Deliver a message to an actor implementation.
	 * 
	 * @param <b>actor</b> A reference to the <em>ActorImpl</em> which receive
	 *        the message.
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> structure to be delivered.
	 *        This structure must be maintained by the actor as it is required
	 *        if an exception is returned to the manager.
	 */
	protected void implDeliver(ActorImpl actor, ActorMsgRequest msg) {
		actor.actorDeliver(msg);

		if (!(actor instanceof ShellActorImpl)
				&& !(actor instanceof StreamInputActorImpl)
				&& !(actor instanceof StreamOutputActorImpl)
				&& !(actor instanceof StreamErrorActorImpl)) {
			actor.resume();
		}
		// actorScheduler.scheduleThread(actor);
	}

	/**
	 * Return the name associated with an actor implementation. This is a
	 * protected field defined in <em>ActorImpl</em>, thus the need for the
	 * function definition here.
	 * 
	 * @param <b>actor</b> A reference to the <em>ActorImpl</em> who's name
	 *        should be returned.
	 */
	protected ActorName implGetName(ActorImpl actor) {
		return actor.self;
	}

	/**
	 * Instruct an actor implementation to rebuild itself after a migration.
	 * 
	 * @param <b>actor</b> The actor to rebuild.
	 * @param <b>ourMgr</b> A reference to the new manager of the implementation
	 *        after migration has occurred.
	 */
	protected void implPostMigrateRebuild(ActorImpl actor, ActorManager ourMgr) {
		actor.actorPostMigrateRebuild(ourMgr);
	}

	/**
	 * Instruct an actor implementation to stamp a message.
	 * 
	 * @param <b>actor</b> The actor to do the stamping.
	 * @param <b>msg</b> The message to be stamped.
	 */
	protected void implStamp(ActorImpl actor, ActorMsgRequest msg) {
		actor.stampRequest(msg);
	}

	// ///////////////////////////////////////////////////////////////////
	// Local Actor Interface:
	// These methods comprise the local actor interface and should
	// only be invoked by local actors or by the manager itself.
	// The actor associated with each call is identified based on the
	// thread making the call. In particular, the manager is
	// responsible for creating all local actors and assigning their
	// threads, thus the manager may signal an error if an actorXXX
	// method is called from an illegal thread.
	//
	// Many actor errors are thrown asynchronously. That is, for
	// efficiency reasons, certain requests are not handled
	// immediately (e.g. remote creation). Thus, these requests may
	// later result in exceptions which should be delivered to the
	// calling actor. Such exceptions are delivered through the
	// actorAsynchException method defined in the Actor interface.
	// It is up to each actor implementation to handle the exception
	// from there.
	// ///////////////////////////////////////////////////////////////////

	/**
	 * This method is called by a local actor implementation to request the
	 * creation of a new actor. The type of the new actor, the type of
	 * implementation that should encapsulate the new actor, the node it should
	 * be created on, as well as the arguments that should be passed to its
	 * constructor are encapsulated in the <em>request</em> structure. The name
	 * of the new actor is returned as the result of this call. Semantically
	 * speaking, once this method completes, the returned name is henceforth a
	 * valid target for receiving messages. As a side-effect, the ID field of
	 * the <em>request</em> argument is assigned a value unique relative to all
	 * other local requests. This value may be used to disambiguate any
	 * asynchronous exceptions caused by this request.
	 * <p>
	 * 
	 * If an exception is thrown by this method (asynchronously or otherwise)
	 * then the returned actor name is no longer valid and any messages sent to
	 * it will be discarded. Furthermore, if the asynchronous exception resulted
	 * from the constructor invoked for the new actor, then any messages sent
	 * will be dropped. The following asynchronous exceptions may be thrown:
	 * <p>
	 * 
	 * <ul>
	 * <li><b>BehaviorNotFoundException</b> - Thrown if the classes necessary to
	 * instantiate the new actor cannot be found (locally or otherwise).
	 * <li><b>NoSuchMethodException</b> - Thrown if no constructor can be found
	 * which matches the argument list provided in this request.
	 * <li><b>IllegalNodeException</b> - Thrown if the target manager for the
	 * creation request cannot be found.
	 * <li><b>RemoteRequestRefusedException<b> - Thrown if the target manager
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
	protected abstract ActorName actorCreate(ActorImpl caller,
			ActorCreateRequest request) throws SecurityException,
			RemoteCodeException;

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
	protected abstract void actorSend(ActorImpl caller, ActorMsgRequest message)
			throws RemoteCodeException;

	/**
	 * This method is called by an actor implementation to report that it has
	 * encountered a fatal error. Usually such an error means that the actor is
	 * no longer executable and should probably be removed from the system. The
	 * caller should pass an a reference to itself in order to be safely
	 * terminated by the manager.
	 * <p>
	 * 
	 * @param <b>thrower</b> The <em>ActorImpl</em> signalling the fatal error.
	 *        Usually, this actor will be removed from the system.
	 * @param <b>e</b> The <em>Exception</em> which describes the fatal error
	 *        encountered in the actor.
	 */
	protected abstract void actorFatalError(ActorImpl caller, Exception e);

	/**
	 * This method is called by a local actor that wishes to be migrated to
	 * another manager. The calling actor is migrated immediately, thus it is
	 * the responsibility of the underlying <em>Actor</em> implementation to
	 * ensure that the actor is in a consistent state before this method is
	 * called. Note that immediate migration will destroy the thread currently
	 * running in the actor. Once the migration is complete, the actor is
	 * restarted with a fresh thread. An exception returned by this method
	 * indicates that the actor was not migrated.
	 * <p>
	 * 
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
	protected abstract void actorMigrate(ActorImpl caller,
			ActorManagerName where) throws IllegalNodeException,
			RemoteRequestRefusedException, RemoteCodeException;

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
	protected abstract Object actorInvokeService(ActorImpl caller,
			ServiceName serviceName, String meth, Object[] serviceArgs)
			throws ServiceNotFoundException, ServiceException;

	public static ActorManager getInstance() {
		if (instance != null)
			return instance;
		return null;
	}

	public abstract void actorEscape(ActorName actor);

	protected abstract void actorSignal(ActorImpl signaler, ActorName actor,
			Signal sig);

	protected void implSignal(ActorImpl actor, Signal sig) {
		actor.actorSignal(sig);
		if (!(actor instanceof ShellActorImpl)
				&& !(actor instanceof StreamInputActorImpl)
				&& !(actor instanceof StreamOutputActorImpl)
				&& !(actor instanceof StreamErrorActorImpl)) {
			actor.resume();
		}
	}

}
