package osl.manager.local;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import kilim.Task;
import kilim.pausable;
import osl.manager.Actor;
import osl.manager.ActorContext;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorExecutor;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.manager.IllegalTargetException;
import osl.manager.RemoteCodeException;
import osl.manager.Signal;
import osl.manager.basic.BasicActorManager;
import osl.manager.Empty_MbReason;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.ConstructorStructure;
import osl.util.LSCQueue;
import osl.util.MethodStructureVector;
import osl.util.Queue;
import osl.util.QueueSearch;

/**
 * This class defines the basic implementation of the <em>ActorImpl</em> class.
 * The implementation provided here is very similar to the original
 * <tt>ActorFoundry-0.2[alpha]</tt> implementation of the <em>Actor</em> class.
 * Specifically, upon construction this implementation first discovers all of
 * the available public methods (minus constructors) of the actor it
 * encapsulates. Each such public method is a valid target for an external
 * message.
 * <p>
 * 
 * After initializing itself, the implementation calls the encapsulated actor's
 * constructor, after which messages are processed as usual. Any synchronous
 * exceptions (that is, those that result directly from calls by the
 * encapsulated actor) are immediately returned to the actor. Asynchronous
 * exceptions are not yet passed back to the encapsulated actor.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.15 $ ($Date: 1999/07/13 02:01:50 $)
 * @see BasicActorManager
 * @see BasicActorName
 * @see osl.util.MethodStructure
 * @see osl.util.MethodStructureVector
 */

public class LocalActorImpl extends ActorImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4178568903516186217L;

	public static final int MAX_MESSAGES = 100;

	/**
	 * The actor instance that we manage.
	 */
	// protected Actor ourActor = null;
	protected ActorExecutor actorExecutor = null;

	/**
	 * The array of arguments to pass to the constructor of the encapsulated
	 * actor when it is instantiated. We set this field to null after the new
	 * actor has been constructed so that this data may be GC'd.
	 */
	transient protected Object[] conArgs = null;

	/**
	 * The mail queue for this actor. This should only be modified by the
	 * manager which created this actor and the thread which schedules it.
	 */
	protected Queue<Object> mailQueue = null;

	/**
	 * The <em>ActorManager</em> which manages this actor. This reference is
	 * used to access system level operations such as invoking <tt>create</tt>
	 * or <tt>send</tt>. Note that this need not be a <em>BasicActorManager</em>
	 * . The basic actor implementation should work fine if migrated to other
	 * manager implementations.
	 */
	transient protected ActorManager ourManager = null;

	/**
	 * The <em>ActorManagerName</em> of our actor manager. Why is this public?
	 */
	// transient public ActorManagerName managerName = null;
	/**
	 * This hashtable hashes the names of our public methods to an array of
	 * MethodStructure instances for quick lookup during method invocation. We
	 * initialize this table when the actor is instantiated by an
	 * <em>ActorManager</em>. Note that this table is transient because the
	 * things it contains (Method instances) are final and therefore can't be
	 * serialized for migration.
	 */
	// transient protected Hashtable<String, Object> ourMethods = null;
	/**
	 * This field holds a class instance which is used to search mail queues for
	 * RPC reply messages. We detect these messages when the user sends an RPC
	 * request.
	 */
	protected WaitRPCReply scanQueue = new WaitRPCReply();

	/**
	 * If this field is non-null, then the actor wishes to be migrated after the
	 * current method has finished.
	 */
	// transient protected ActorManagerName migrateTo = null;
	/**
	 * A reference to the create request which was used to create this actor.
	 * This field may be set to null once the new internal actor is successfully
	 * created.
	 */
	protected ActorCreateRequest createReq = null;
	protected ActorMsgRequest reusableMsgObj = null;

	// //////////////////////////////////////////////////////////////////
	// The default constructor is used for this class. Most of the
	// "initialization" code is in the actorInitialize method.
	// //////////////////////////////////////////////////////////////////

	// //////////////////////////////////////////////////////////////////
	// Core Methods:
	// These methods define the core behavior of the basic
	// implementation. Things like general message processing and
	// dispatch are defined here.
	// //////////////////////////////////////////////////////////////////

	/**
	 * This method is called from <em>run</em> to invoke a method on this actor.
	 * Any exception is passed up to the caller of this method. All exceptions
	 * are passed up to the caller.
	 * 
	 * @param <b>nextMethod</b> The <em>String</em> naming the next method to
	 *        invoke on the encapsulated actor.
	 * @param <b>methodArgs</b> An array of <em>Object</em>s to pass to the
	 *        target method.
	 * @return The <em>Object</em> returned by the target method.
	 * @exception java.lang.NoSuchMethodException
	 *                Thrown if no method can be found with a signature that
	 *                matches <em>nextMethod</em>(<em>methodArgs</em>).
	 * @exception java.lang.reflect.InvocationTargetException
	 *                Thrown if an error is encountered while invoking
	 *                <em>nextMethod</em>.
	 * @exception java.lang.IllegalAccessException
	 *                Thrown if the target method is inaccessible.
	 */

	/*
	 * protected Object invokeMethod(String nextMethod, Object[] methodArgs)
	 * throws NoSuchMethodException, InvocationTargetException,
	 * IllegalAccessException { MethodStructure[] potMeths = null; Method
	 * toInvoke = null; boolean found = false;
	 * 
	 * // Grab the method we are supposed to invoke, barf if we can't // find
	 * any such method potMeths = (MethodStructure[])
	 * ourMethods.get(nextMethod);
	 * 
	 * if (potMeths == null) throw new
	 * NoSuchMethodException("No method matching \"" + nextMethod +
	 * "\" in this object");
	 * 
	 * for(int i=0; (i < potMeths.length) && (!found); i++) { if (methodArgs ==
	 * null) { if (potMeths[i].argTypes.length == 0) { found = true; toInvoke =
	 * potMeths[i].meth; break; } else continue; } else if (potMeths[i].argTypes
	 * == null) continue; else if (methodArgs.length !=
	 * potMeths[i].argTypes.length) continue;
	 * 
	 * found = true; toInvoke = potMeths[i].meth; for(int j=0;
	 * j<potMeths[i].argTypes.length; j++) if ((methodArgs[j] != null) &&
	 * (!potMeths[i].argTypes[j].isInstance(methodArgs[j]))) { found = false;
	 * break; } }
	 * 
	 * if (!found) throw new NoSuchMethodException("No method matching \"" +
	 * nextMethod + "\" in this object");
	 * 
	 * // Now invoke the method and send back the return value try { return
	 * toInvoke.invoke(ourActor, methodArgs); } catch (InvocationTargetException
	 * e) { if (e.getTargetException() instanceof ThreadDeath) throw
	 * (ThreadDeath) e.getTargetException(); else throw e; } }
	 */
	/**
	 * This method handles the processing of the next message on the actor's
	 * mail queue. We also inspect our local state after the message has been
	 * processed in order to determine whether or not to migrate. If migration
	 * is necessary then it is performed and we return to the caller (which
	 * should just kill us off).
	 * 
	 * @param <b>nextMsg</b> The <em>ActorMsgRequest</em> to process.
	 * @exception java.lang.Exception
	 *                Just about anything can be thrown from this method since
	 *                it calls user-written code. So we just throw an instance
	 *                of Exception and rely on the caller to catch it and
	 *                process it appropriately.
	 */
	@pausable
	protected void processMessage(ActorMsgRequest nextMsg) throws Exception {
		Object rVal = null;
		try {
			// Attempt to pass this message as an invocation to our
			// managed actor
			// PRAGMA [assert] Assert.assert(nextMsg.methodArgs != null,
			// "message not properly formatted");
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("About to invoke method: " + nextMsg.method);
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("with arguments: (");
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// for (int DI=0; DI < nextMsg.methodArgs.length; DI++)
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("\t\t" + DI + ": " + nextMsg.methodArgs[DI]);
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println(")");
			// rVal = invokeMethod(nextMsg.method, nextMsg.methodArgs);
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("Done invoking method: " + nextMsg.method);

			rVal = actorExecutor.execute(nextMsg);

			// If this message is an RPC request, then send out the reply
			// message
			if (nextMsg.RPCRequest && rVal != ActorExecutor.DISABLED_FLAG) {
				Object[] returnIt = new Object[1];
				returnIt[0] = rVal;
				// ActorMsgRequest theReply = new ActorMsgRequest(self,
				// nextMsg.sender, "__RPCReply", returnIt, false);
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.processMessage> Request is RPC, returning value: "
				// + rVal);
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// if (rVal != null)
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.processMessage> with type: " +
				// rVal.getClass());
				// stampRequest(theReply);
				// implSend(theReply);
				// Trying to send the reply back in the same "box"
				reusableMsgObj = nextMsg;

				implSend(nextMsg.sender, "__RPCReply", returnIt, false);
			}

			else if (rVal != ActorExecutor.DISABLED_FLAG) {
				// An exception will require keeping the old message; hence late
				// reusability
				reusableMsgObj = nextMsg;
			}
		} catch (Exception e) {

			// Kill any pending exception
			// migrateTo = null;

			if ((!nextMsg.receiver.equals(self))
					|| (!nextMsg.method.equals("asynchException"))) {
				// Note: we NEVER send an exception if the message which
				// caused the exception was an "asynchException" message.
				// If we get such an exception then we just rethrow it so
				// that it is caught by the outside block.

				/*
				 * if (nextMsg.receiver.equals(self))Log.println(
				 * "<BasicActorImpl.processMessage> Attempt to invoke method \""
				 * + nextMsg.method + "\" caused exception, local trace is...");
				 * elseLog.println(
				 * "<BasicActorImpl.processMessage> Attempt to process message from requestAsynchSend failed, local trace is..."
				 * );
				 */
				/*
				 * if (e instanceof InvocationTargetException) { // Unwrap
				 * invocation target exceptions so we get a more reasonable
				 * error message.Log.println(
				 * "<BasicActorImpl.processMessage> Invocation target exception with contents:"
				 * ); Log.logExceptionTrace(((InvocationTargetException)
				 * e).getTargetException()); } else Log.logExceptionTrace(e);
				 */
				// Any exception resulting from a method call is sent
				// directly to the original sender of the message. For
				// this implementation we don't treat a message
				// exception as fatal. That is, we continue to dequeue
				// messages as usual although the encapsulated actor may
				// now be fried. HOWEVER: if the following send causes
				// an exception, then we'll be caught in the outer try
				// block and exit with a fatal error.
				if (!(e instanceof RemoteCodeException))
					e = new RemoteCodeException("Error invoking method "
							+ nextMsg.method, e);
				ActorMsgRequest errMsg = buildAsynchException(self,
						nextMsg.sender, nextMsg, e);
				stampRequest(errMsg);
				implSend(errMsg);
			} else
				throw e;
		}

		// If migrateTo is non-null then this actor wishes to be migrated.
		// If necessary, make the call to our manager to perform the
		// migration. Note that if the migration is successful then the
		// current thread will return with a "ThreadDeath" throwable which
		// is expected to be processed by the caller of this function. If
		// the migration is not successful then this call will return with
		// an exception. We handle the exception by putting an
		// asynchException message at the front of our mail queue.

	}

	private static Object[] zeroArray = new Object[0];

	/**
	 * This method initializes the standard streams for this implementation and
	 * attempts to create the <em>Actor</em> requested in the create message. An
	 * exception is returned if the initialization failed.
	 * 
	 * @exception java.lang.Exception
	 *                A wide variety of exceptions may be thrown by this method.
	 */
	protected void initializeActor() throws Exception {
		ActorCreateRequest nextReq;
		Object[] args;

		// See if we need to setup local streams
		if (context == null) {
			// Log.println("<BasicActorImpl.run> No context found, creating new");
			context = new ActorContext();
		}

		if (context.stdin == null) {
			// Log.println("<BasicActorImpl.run> Creating stdin stream");
			nextReq = new ActorCreateRequest(self, Class
					.forName("osl.manager.Actor"),
					osl.manager.basic.StreamInputActorImpl.class, zeroArray,
					null);
			nextReq.originator = self;
			context.stdin = mgrActorCreate(ourManager, nextReq);
		}

		if (context.stdout == null) {
			// Log.println("<BasicActorImpl.run> Creating stdout stream");
			args = new Object[1];
			args[0] = "out";
			nextReq = new ActorCreateRequest(self, Class
					.forName("osl.manager.Actor"),
					osl.manager.basic.StreamOutputActorImpl.class, args, null);
			nextReq.originator = self;
			context.stdout = mgrActorCreate(ourManager, nextReq);

		}

		if (context.stderr == null) {
			// Log.println("<BasicActorImpl.run> Creating stderr stream");
			args = new Object[1];
			args[0] = "err";
			nextReq = new ActorCreateRequest(self, Class
					.forName("osl.manager.Actor"),
					osl.manager.basic.StreamErrorActorImpl.class, args, null);
			nextReq.originator = self;
			context.stderr = mgrActorCreate(ourManager, nextReq);
		}

		// If it hasn't already been done, instantiate the new actor.
		// Note that user-written constructors may make use of the basic
		// actor services.
		// PRAGMA [assert] Assert.assert(ourActor == null,
		// "internal actor already instantiated");
		Constructor<?> toCreate = null;

		// This method should only be called from actorInitialize.
		// Therefore we simply pass on any exception, which should result
		// in the creation for this actor failing.
		toCreate = findConstructor(actorClass, conArgs);
		// setCreatingThread(Thread.currentThread(), this);
		Actor ourActor = (Actor) toCreate.newInstance(conArgs);
		ourActor._init(this);
		Class<?> execClass = Class.forName(actorClass.getName() + "Executor");
		Object[] execConArgs = new Object[] { ourActor, mailQueue };
		Constructor<?> toCreateExec = findConstructor(execClass, execConArgs);
		actorExecutor = (ActorExecutor) toCreateExec.newInstance(execConArgs);

		// Clean up so things may be garbage collected
		conArgs = null;
		createReq = null;

	}

	// //////////////////////////////////////////////////////////////////
	// Main Run Loop:
	// This is where everything interesting happens.
	// //////////////////////////////////////////////////////////////////

	/**
	 * The run method for the basic actor implementation. This method is called
	 * after this class has been instantiated and after the
	 * <em>actorInitialize</em> has been called. If the actor we are managing
	 * has not yet been created, then the first thing we do is create the local
	 * actor and call its constructor. Note that we can't do this in
	 * <em>actorInitialize</em> because the user's constructor might invoke
	 * "send" or "create", which require an up call to the manager which can't
	 * be performed because the calling thread is the manager's, not an instance
	 * of ActorImpl. After creating the actor, the main purpose of this method
	 * is to dequeue the next ready message and call the appropriate method on
	 * the encapsulated actor. This method also suspends the actor's thread when
	 * it has no more messages to process. Because the mail queue is a
	 * <em>Queue</em>, the sleeping thread is automatically awoken when a new
	 * message arrives.
	 */
	@pausable
	public void execute() {
		ActorMsgRequest nextMsg = null;
		int messageCount = 0;

		// ActorCreateRequest nextReq;
		// Object[] args;
		try {

			// Log.println("<BasicActorImpl.run> New actor started with name: "
			// + self);

			while (true) {
				if (!mailQueue.empty() && messageCount < MAX_MESSAGES) {
					// Get the next message to process and call our actor
					nextMsg = (ActorMsgRequest) mailQueue.dequeue();

					processMessage(nextMsg);
					// reusableMsgObj = nextMsg;
					messageCount++;

					// If we just migrated then exit this actor (it's garbage
					// now)

					// PRAGMA
					// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
					// } else
					// PRAGMA
					// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
					// Log.println("<BasicActorImpl.run> method complete, moving to next message");

				} else {
					// See if the mail queue is still empty. If it is then
					// provide a hint to our scheduler and put this thread to
					// sleep. Otherwise we automatically go back and pick off
					// the message.
					// synchronized (mailQueue) {
					if (mailQueue.empty()) {
						// mailQueue.wait();
						// Task.yield();
						Task.pause(new Empty_MbReason(mailQueue));
					} else { // count of processed messages has reached
						// MAX_MESSAGES
						// Task.pause(new YieldReason());
						Task.yield();
					}
					messageCount = 0;
					// }
				}
			}
		} catch (Throwable e) {
			// We catch a throwable here rather than an exception so we get
			// EVERYTHING that might cause this actor to fail (unless we
			// catch a ThreadDeath message, in which case we just throw it
			// again). We then package things into a RemoteCodeException
			// for delivery to our manager. The manager should either kill
			// us or restart us by recalling start().
			if (e instanceof ThreadDeath)
				throw (ThreadDeath) e;
			else
				mgrActorFatalError(ourManager, new RemoteCodeException(
						"Error in run loop of ActorImpl:", e));
		}
	}

	/**
	 * This function returns the appropriate constructor for building an actor
	 * class based on its type and the signatures of the arguments supplied.
	 * 
	 * @param <b>classType</b> A <em>Class</em> describing the complete class
	 *        type of the actor.
	 * @param <b>args</b> The set of arguments to be supplied to the
	 *        constructor. If the default constructor is to be invoked, then
	 *        this must be a 0 length array NOT null.
	 * @return The <em>Constructor</em> which should be used to instantiate the
	 *         new actor.
	 * @exception java.lang.NoSuchMethodException
	 *                Thrown if no matching constructor could be found using the
	 *                given arguments.
	 */
	protected Constructor<?> findConstructor(Class<?> classType, Object[] args)
			throws NoSuchMethodException {

		int i;
		boolean found = false;
		Constructor<?> toInvoke = null;
		Constructor<?>[] cons = null;
		// Class<?>[] params = null;

		// Grab set of constructors, signal error if no public constructors
		cons = classType.getConstructors();
		if (cons.length == 0)
			throw new NoSuchMethodException("Class " + classType
					+ " has no public constructors!");

		// First build a method structure vector for all the
		// constructors. This automatically sorts the constructors so
		// that the first match is the most specific constructor.
		MethodStructureVector items = new MethodStructureVector();
		for (i = 0; i < cons.length; i++)
			items.insertElement(new ConstructorStructure(cons[i], cons[i]
					.getParameterTypes()));

		// Now scan the constructor list for the most specific constructor
		ConstructorStructure next = null;
		Object[] sorted = new Object[items.size()];
		items.copyInto(sorted);

		if (args == null) {
			args = zeroArray;
		}
		for (i = 0; ((i < sorted.length) && (!found)); i++) {
			next = (ConstructorStructure) sorted[i];

			// Compare argument list lengths
			if (args.length != next.argTypes.length)
				continue;

			// Found a possible candidate, compare argument types
			found = true;
			toInvoke = next.meth;
			for (int j = 0; j < args.length; j++)
				if (!next.argTypes[j].isInstance(args[j])) {
					found = false;
					break;
				}
		}

		if (!found) {
			String argList = "(";

			if ((args == null) || (args.length == 0))
				argList = argList + ")";
			else {
				for (i = 0; i < args.length - 1; i++)
					argList = argList + args[i].getClass() + ", ";
				argList = argList + args[i].getClass() + ")";
			}

			throw new NoSuchMethodException("No constructor for " + classType
					+ " with arg types " + argList);
		}

		// Found the constructor, so return it
		return toInvoke;
	}

	// ///////////////////////////////////////////////////////////////////////
	// ////// Methods required by ActorImpl //////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	// ///////////////////////////////////////////////////////////////////////
	// Manager Interface Functions:
	//
	// These methods are intended to be invoked by extensions of
	// the ActorManager abstract class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * This method is called to initialize the actor implementation after it is
	 * instantiated. The caller provides the name of the new actor, the run-time
	 * class of the user-defined actor that should be instantiated, and the
	 * array of arguments that should be passed to the constructor of the new
	 * actor when it is created. This method is protected so that it has package
	 * level protection and therefore may not be invoked directly by
	 * user-written actor code.
	 * <p>
	 * 
	 * The semantics of <em>ActorImpl</em> require that any exceptions which
	 * result from the creation of the actor should be thrown from this method.
	 * Therefore, the new user actor is created within this method and its
	 * constructor is called. If the user actor attempts to make a "call" to the
	 * creating actor, then a run-time exception is returned and the actor is
	 * never created. This is done because any such call never has a hope of
	 * completing as the creator is blocked waiting for the address of the new
	 * actor (and therefore can't respond to the call).
	 * <p>
	 * 
	 * @param <b>ourMgr</b> The <em>ActorManager</em> which should be used by
	 *        this actor implementation to invoke actor services.
	 * @param <b>you</b> The <em>ActorName</em> that should be used as the name
	 *        of the new actor.
	 * @param <b>req</b> The <em>ActorCreateReq</em> that requested this new
	 *        actor to be created. Error messages during the creation should be
	 *        sent to the "requester" stored in this request.
	 * @exception java.lang.Exception
	 *                The actual exception thrown depends on the exceptions
	 *                which may be thrown by the constructor of a new actor.
	 *                This is a run-time rather than compile-time property.
	 */
	protected void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) throws Exception {
		// First establish our local fields. The basic actor doesn't
		// provide any extensions so we implement the ActorExtension
		// interface ourself.
		self = you;
		actorClass = req.behToCreate;
		ourManager = ourMgr;
		// managerName = ourMgr.managerGetName();
		// migrateTo = null;
		conArgs = req.constructorArgs;
		createReq = req;
		context = req.context;

		// // Now that that's done, build the method tables for the actor we
		// // manage.
		// Method[] theMeths = null;
		// ourMethods = new Hashtable<String, Object>();
		//
		// // Look up and store the public methods of the actor we are about
		// // to create
		// try {
		// theMeths = actorClass.getMethods();
		//
		// for(int i=0; i<theMeths.length; i++)
		// if (ourMethods.containsKey(theMeths[i].getName()))
		// ((MethodStructureVector) ourMethods.get(theMeths[i].getName())).
		// insertElement(new MethodStructure(theMeths[i],
		// theMeths[i].getParameterTypes()));
		// else {
		// MethodStructureVector newVec = new MethodStructureVector();
		// ourMethods.put(theMeths[i].getName(), newVec);
		// newVec.insertElement(new MethodStructure(theMeths[i],
		// theMeths[i].getParameterTypes()));
		// }
		//	  
		// for(Enumeration<String> e=ourMethods.keys(); e.hasMoreElements(); ) {
		// String methName = e.nextElement();
		// MethodStructureVector methObjs = (MethodStructureVector)
		// ourMethods.get(methName);
		// MethodStructure[] refArray = new MethodStructure[methObjs.size()];
		//
		// methObjs.copyInto(refArray);
		// ourMethods.put(methName, refArray);
		// }
		//
		// } catch(SecurityException e) {
		// throw new SecurityException("Error instantiating " +
		// actorClass.toString() + ": " + e.toString());
		// }

		// Initialize our mail queue
		mailQueue = new LSCQueue<Object>();

		// And finally call the actor creation code
		initializeActor();
	}

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
	protected void actorPostMigrateRebuild(ActorManager ourMgr) {
		// Restore our transient fields
		// managerName = ourMgr.managerGetName();
		// migrateTo = null;
		/*
		 * ourManager = ourMgr; conArgs = null;
		 */

		// // Now restore the method tables for the actor we manage. We
		// // can't migrate these tables because things like
		// // java.lang.reflect.Method aren't Serializable (they are mostly
		// // native implementations anyway).
		// Method[] theMeths = null;
		// ourMethods = new Hashtable<String, Object>();
		//
		// try {
		// theMeths = actorClass.getMethods();
		//
		// for(int i=0; i<theMeths.length; i++)
		// if (ourMethods.containsKey(theMeths[i].getName()))
		// ((MethodStructureVector) ourMethods.get(theMeths[i].getName())).
		// insertElement(new MethodStructure(theMeths[i],
		// theMeths[i].getParameterTypes()));
		// else {
		// MethodStructureVector newVec = new MethodStructureVector();
		// ourMethods.put(theMeths[i].getName(), newVec);
		// newVec.insertElement(new MethodStructure(theMeths[i],
		// theMeths[i].getParameterTypes()));
		// }
		//	  
		// for(Enumeration<String> e=ourMethods.keys(); e.hasMoreElements(); ) {
		// String methName = e.nextElement();
		// MethodStructureVector methObjs = (MethodStructureVector)
		// ourMethods.get(methName);
		// MethodStructure[] refArray = new MethodStructure[methObjs.size()];
		//
		// methObjs.copyInto(refArray);
		// ourMethods.put(methName, refArray);
		// }
		//
		// } catch(SecurityException e) {
		// throw new SecurityException("Error building method tables for " +
		// actorClass.toString() + ": " + e.toString());
		// }
	}

	/**
	 * This method is called by a manager to deliver a new message to the local
	 * actor. Any exceptions resulting from the processing of this message
	 * should be passed back to the manager using the
	 * <em>managerMsgException</em> method. This method is protected so that it
	 * has package level protection and therefore may not be invoked directly by
	 * user-written actor code.
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> structure to be delivered.
	 *        This structure must be maintained by the actor as it is required
	 *        if an exception is returned to the manager.
	 */
	protected void actorDeliver(ActorMsgRequest msg) {
		// Simple, just place the message in the local mail queue. This
		// will automatically wake our thread up if it is sleeping on the
		// queue.
		// PRAGMA [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
		// Log.println("<BasicActorImpl.actorDeliver>: Queueing message: " +
		// msg);
		mailQueue.enqueue(msg);
		// PRAGMA [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
		// Log.println("<BasicActorImpl.actorDeliver>: Queueing complete");

	}

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
	 *                Thrown as a wrapper for any error that is encountered
	 *                while attempting the send. Errors which occur on the
	 *                receiving end are delivered asynchronously.
	 */
	protected Object implSend(ActorName dest, String meth, Object[] args,
			boolean byCopy) throws RemoteCodeException {

		if (dest == null)
			throw new IllegalTargetException("send target is null");
		ActorMsgRequest msg = null;
		if (reusableMsgObj == null)
			msg = new ActorMsgRequest(self, dest, meth, args, false, byCopy);
		else {
			msg = reusableMsgObj;
			msg.initialize(self, dest, meth, args, false, byCopy);
			reusableMsgObj = null;
		}
		return this.implSend(msg);
	}

	private Object implSend(ActorMsgRequest msg) throws RemoteCodeException {

		// Throw an immediate run-time exception if we are being called
		// within a user-actor constructor, this is an RPC call, and the
		// call is being made to our creator. This will always deadlock
		// and therefore the create should fail.
		if ((createReq != null) && (msg.RPCRequest)
				&& (msg.receiver.equals(createReq.requester)))
			throw new IllegalTargetException("Constructor for actor "
					+ createReq.behToCreate
					+ " makes synchronous call to creator.");

		// if (!msg.valid())
		stampRequest(msg);

		try {
			// If the RPC field is false then just make the call and return,
			// otherwise we block the caller and perform the RPC algorithm.
			if (!msg.RPCRequest) {
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.implSend>: Sending message:" +
				// msg);
				mgrActorSend(ourManager, msg);
				return null;
			}
		} catch (IllegalTargetException e) {
			// Just rethrow to the caller. We don't want to deliver these
			// asynchronously.
			throw e;
		} catch (Exception e) {
			// For anything else, what we do with the error depends on
			// whether or not this is an RPC request. If it's an RPC
			// request then we throw a remote code exception containing the
			// error. Otherwise we deliver an exception message to the
			// sending actor.
			// Log.println("<BasicActorImpl.implSend>: Processing general exception with stack trace...");
			// Log.logExceptionTrace(e);

			if (msg.RPCRequest)
				if (e instanceof RemoteCodeException)
					throw (RemoteCodeException) e;
				else
					throw new RemoteCodeException(
							"Error encountered while performing send:", e);
			else
				try {
					Object[] errArgs = new Object[2];
					ActorMsgRequest errMsg = new ActorMsgRequest(self, self,
							"asynchException", errArgs);
					errArgs[0] = msg;
					errArgs[1] = e;
					stampRequest(errMsg);
					actorDeliver(msg);
				} catch (Exception f) {
					// If we catch an exception while delivering the exception
					// message then we dump a fatal error.
					mgrActorFatalError(ourManager, f);
				}
		}

		return null;
	}

	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) throws RemoteCodeException {
		if (dest == null)
			throw new IllegalTargetException("send target is null");
		ActorMsgRequest msg = null;
		if (reusableMsgObj == null)
			msg = new ActorMsgRequest(self, dest, meth, args, true, byCopy);
		else {
			msg = reusableMsgObj;
			msg.initialize(self, dest, meth, args, true, byCopy);
			reusableMsgObj = null;
		}

		// Throw an immediate run-time exception if we are being called
		// within a user-actor constructor, this is an RPC call, and the
		// call is being made to our creator. This will always deadlock
		// and therefore the create should fail.
		if ((createReq != null) && (msg.RPCRequest)
				&& (msg.receiver.equals(createReq.requester)))
			throw new IllegalTargetException("Constructor for actor "
					+ createReq.behToCreate
					+ " makes synchronous call to creator.");

		// if (!msg.valid())
		stampRequest(msg);

		try {
			// If the RPC field is false then just make the call and return,
			// otherwise we block the caller and perform the RPC algorithm.
			// Send the message, then scan our mail queue for one of following
			// events (note that we can only LEGALLY receive one or the
			// other, it is a fatal error if we receive both):
			// 1. We receive a message with method "__RPCReply"
			// 2. We receive a message with method "asynchException"
			// which is a properly formed asynchException message
			// where the "cause" is equal to the request we
			// originally sent.

			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("<BasicActorImpl.implSend>: Sending RPC message");

			mgrActorSend(ourManager, msg);
			Object[] sat1 = null;
			Object[] sat2 = null;
			WaitAsynchExcept ourTest = new WaitAsynchExcept(msg);

			// synchronized (mailQueue) {
			sat1 = mailQueue.search(scanQueue);
			sat2 = mailQueue.search(ourTest);

			while ((sat1.length == 0) && (sat2.length == 0)) {

				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.implSend>: RPC/Exception reply not found yet, waiting");

				// mailQueue.wait();
				// Task.yield();
				Task.pause(new Empty_MbReason(mailQueue));
				sat1 = mailQueue.search(scanQueue);
				sat2 = mailQueue.search(ourTest);
			}
			// }
			// PRAGMA
			// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
			// Log.println("<BasicActorImpl.implSend>: RPC/Exception found, continuing");
			// PRAGMA [assert] Assert.assert( ((sat1.length == 1) &&
			// (sat2.length == 0)) || ((sat1.length == 0) && (sat2.length ==
			// 1)),
			// PRAGMA [assert]
			// "either an RPC or asynchException message should be received, but not both in implSend!!!");

			// Figure out if we're handling a reply or an exception and handle
			// it
			if (sat1.length != 0) {
				// This is a reply so return it to the caller
				mailQueue.remove(sat1[0]);

				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.implSend>: Returning RPC result");
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// if (((ActorMsgRequest) sat1[0]).methodArgs[0] != null)
				// PRAGMA
				// [debug,osl.manager.ActorImpl,osl.manager.basic.BasicActorImpl]
				// Log.println("<BasicActorImpl.implSend>: Class is: "+
				// ((ActorMsgRequest) sat1[0]).methodArgs[0].getClass());

				return ((ActorMsgRequest) sat1[0]).methodArgs[0];
			} else {
				// This is an exception, so throw it to the caller. It will
				// actually be delivered as a packaged run-time exception.
				mailQueue.remove(sat2[0]);

				throw (Exception) ((ActorMsgRequest) sat2[0]).methodArgs[1];
			}
		} catch (InterruptedException e) {
			// This should never happen or we have real big problems. If it
			// does happen then dump to the manager with a fatal error.
			mgrActorFatalError(ourManager, new RuntimeException(
					"Fatal Error: received unexepected InterruptedException in implSend: "
							+ e));
		} catch (IllegalTargetException e) {
			// Just rethrow to the caller. We don't want to deliver these
			// asynchronously.
			throw e;
		} catch (Exception e) {
			// For anything else, what we do with the error depends on
			// whether or not this is an RPC request. If it's an RPC
			// request then we throw a remote code exception containing the
			// error. Otherwise we deliver an exception message to the
			// sending actor.
			// Log.println("<BasicActorImpl.implSend>: Processing general exception with stack trace...");
			// Log.logExceptionTrace(e);

			if (msg.RPCRequest)
				if (e instanceof RemoteCodeException)
					throw (RemoteCodeException) e;
				else
					throw new RemoteCodeException(
							"Error encountered while performing send:", e);
			else
				try {
					Object[] errArgs = new Object[2];
					ActorMsgRequest errMsg = new ActorMsgRequest(self, self,
							"asynchException", errArgs);
					errArgs[0] = msg;
					errArgs[1] = e;
					stampRequest(errMsg);
					actorDeliver(msg);
				} catch (Exception f) {
					// If we catch an exception while delivering the exception
					// message then we dump a fatal error.
					mgrActorFatalError(ourManager, f);
				}
		}

		return null;
	}

	@pausable
	protected Object implJoin(ActorMsgRequest[] msgs, Class<?> customerClass)
			throws RemoteCodeException {
		int totalSent = 0;
		LSCQueue<Object> dummyQueue = new LSCQueue<Object>(1);
		ActorMsgRequest msg = null;
		ActorExecutor custExecutor = null;

		// This method should only be called from actorInitialize.
		// Therefore we simply pass on any exception, which should result
		// in the creation for this actor failing.
		try {
			Constructor<?> toCreate = findConstructor(customerClass, zeroArray);
			Actor ourActor = (Actor) toCreate.newInstance(zeroArray);
			ourActor._init(this);
			Class<?> execClass = Class.forName(customerClass.getName()
					+ "Executor");
			Object[] execConArgs = new Object[] { ourActor, dummyQueue };
			Constructor<?> toCreateExec = findConstructor(execClass,
					execConArgs);
			custExecutor = (ActorExecutor) toCreateExec
					.newInstance(execConArgs);
			// custExecutor = new osl.examples.join.CountCustomerExecutor(new
			// osl.examples.join.CountCustomer(), dummyQueue);

		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < msgs.length; i++) {
			msg = msgs[i];
			msg.RPCRequest = true; // Make sure it is acknowledged

			if (msg.receiver == null)
				throw new IllegalTargetException("send target is null");

			// Throw an immediate run-time exception if we are being called
			// within a user-actor constructor, this is an RPC call, and the
			// call is being made to our creator. This will always deadlock
			// and therefore the create should fail.
			if ((createReq != null) && (msg.RPCRequest)
					&& (msg.receiver.equals(createReq.requester)))
				throw new IllegalTargetException("Constructor for actor "
						+ createReq.behToCreate
						+ " makes synchronous call to creator.");

			stampRequest(msg);
			mgrActorSend(ourManager, msg);
			totalSent++;
		}

		try {

			Object[] sat = null;
			WaitJoinReply joinTest = new WaitJoinReply();
			Object answer = null;

			while (totalSent > 0) {
				sat = mailQueue.search(joinTest);

				while (sat.length == 0) {
					Task.pause(new Empty_MbReason(mailQueue));
					sat = mailQueue.search(joinTest);
				}

				// Figure out if we're handling a reply or an exception and
				// handle it
				if (((ActorMsgRequest) sat[0]).method.equals("__RPCReply")) {
					// This is a reply so send it to the customer as "process"
					// message
					mailQueue.remove(sat[0]);
					((ActorMsgRequest) sat[0])
							.initialize(
									self,
									self,
									"process",
									new Object[] { ((ActorMsgRequest) sat[0]).methodArgs[0] },
									true, false);
					answer = custExecutor.execute((ActorMsgRequest) sat[0]);
					totalSent--;
					sat = null;
				} else {
					// This is an exception, so throw it to the caller. It will
					// actually be delivered as a packaged run-time exception.
					mailQueue.remove(sat[0]);
					throw (Exception) ((ActorMsgRequest) sat[0]).methodArgs[1];
				}
			}

			return answer;

		} catch (InterruptedException e) {
			// This should never happen or we have real big problems. If it
			// does happen then dump to the manager with a fatal error.
			mgrActorFatalError(ourManager, new RuntimeException(
					"Fatal Error: received unexepected InterruptedException in implSend: "
							+ e));
		} catch (IllegalTargetException e) {
			// Just rethrow to the caller. We don't want to deliver these
			// asynchronously.
			throw e;
		} catch (Exception e) {
			// For anything else, what we do with the error depends on
			// whether or not this is an RPC request. If it's an RPC
			// request then we throw a remote code exception containing the
			// error. Otherwise we deliver an exception message to the
			// sending actor.
			// Log.println("<BasicActorImpl.implSend>: Processing general exception with stack trace...");
			// Log.logExceptionTrace(e);

			if (msg.RPCRequest)
				if (e instanceof RemoteCodeException)
					throw (RemoteCodeException) e;
				else
					throw new RemoteCodeException(
							"Error encountered while performing send:", e);
			else
				try {
					Object[] errArgs = new Object[2];
					ActorMsgRequest errMsg = new ActorMsgRequest(self, self,
							"asynchException", errArgs);
					errArgs[0] = msg;
					errArgs[1] = e;
					stampRequest(errMsg);
					actorDeliver(msg);
				} catch (Exception f) {
					// If we catch an exception while delivering the exception
					// message then we dump a fatal error.
					mgrActorFatalError(ourManager, f);
				}
		}

		return null;
	}

	/**
	 * 
	 */
	/*
	 * public NoDistActorImpl() { super();
	 * System.out.println("created NoDistActorImpl"); }
	 */

	/**
	 * Request a new actor to be created. The request argument is forwarded to
	 * the manager and the returned name is passed on to the <em>Actor</em>
	 * caller. This method is protected so that it has package level protection
	 * and therefore may not be invoked directly by user-written actor code. For
	 * the basic implementation, the requested implementation type of the new
	 * actor is set to "BasicActorImpl".
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
	 */
	protected ActorName implCreate(ActorCreateRequest req)
			throws SecurityException, RemoteCodeException {
		// PRAGMA [assert] Assert.assert(req != null,
		// "null create request received");
		// PRAGMA [assert] Assert.assert(req.requester != null,
		// "null \"requester\" specified");
		// // PRAGMA [assert] Assert.assert(req.requester.equals(self),
		// "\"requester\" field different from caller");
		// PRAGMA [assert] Assert.assert(req.behToCreate != null,
		// "\"behToCreate\" field is null (requires subclass of Actor)");
		// PRAGMA [assert] Assert.assert(req.constructorArgs != null,
		// "\"constructorArgs\" is null (use new Object[0] for empty args)");

		req.implToCreate = this.getClass();
		req.context = context;
		if (!req.valid())
			stampRequest(req);
		return mgrActorCreate(ourManager, req);
	}

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
	 *        to. Passing a value of <em>null</em> cancels any previous
	 *        migration request.
	 */
	protected void implMigrate(ActorManagerName loc) {
		// migrateTo = loc;
	}

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
	 */
	protected Object implInvokeService(ServiceName name, String meth,
			Object[] args) throws ServiceNotFoundException, ServiceException {
		// PRAGMA [assert] Assert.assert(name != null,
		// "null service name specified");
		// PRAGMA [assert] Assert.assert(meth != null,
		// "null service method specified");
		// PRAGMA [assert] Assert.assert(args != null,
		// "null argument list specified (use new Object[0] for no args)");
		return mgrActorInvokeService(ourManager, name, meth, args);
	}

	/**
	 * Request to remove this actor from the system. Normally, this method will
	 * not return as the actor is immediately removed from the system. Note that
	 * any actor garbage collection process is ignored in this call so that this
	 * actor may be removed even though it is accessible by other actors. For
	 * the "basic" implementation, we just log the reason and call
	 * <em>mgrActorFatalError</em>.
	 * 
	 * @param <b>reason</b> A <em>String</em> giving a "reason" for the removal.
	 *        This string should normally be appended to the log for the actor
	 *        before removing it from the system.
	 * @see osl.manager.Actor#destroy
	 */
	protected void implDestroy(String reason) {
		// Log.println(this, "<BasicActorImpl.implDestroy> user msg -> " +
		// reason);
		// Log.println("<BasicActorImpl.implDestroy> user msg -> " + reason);
		mgrActorFatalError(ourManager, new RuntimeException(
				"User-actor requested removal"));
	}

	protected void implSignal(ActorName actor, Signal sig) {
		mgrActorSignal(ourManager, actor, sig);
	}

	// ///////////////////////////////////////////////////////////////
	// Inner Classes
	// ///////////////////////////////////////////////////////////////

	/**
	 * This inner class is used to search the mail queue when this actor is
	 * blocked waiting for the return value of an RPC call.
	 */
	class WaitRPCReply implements QueueSearch, Serializable {
		/**
	 * 
	 */
		private static final long serialVersionUID = 8127375527485945864L;

		/**
		 * We run this method on elements of our <em>mailQueue</em> and returns
		 * true if one of the messages matches an RPC reply. The special method
		 * name "__RPCReply" signals a message that is an RPC reply.
		 */
		public boolean queueEvalPred(Object arg) {
			if (arg instanceof ActorMsgRequest) {
				ActorMsgRequest nextMsg = (ActorMsgRequest) arg;
				if (nextMsg.method.equals("__RPCReply"))
					return true;
			}

			return false;
		}
	}

	/**
	 * This inner class is used to search the mail queue when this actor is
	 * blocked waiting for one of the reply value or an exception for messages
	 * in a join expression.
	 */
	class WaitJoinReply implements QueueSearch, Serializable {

		/**
	 * 
	 */
		private static final long serialVersionUID = 8195941856013441863L;

		/**
		 * We run this method on elements of our <em>mailQueue</em> and returns
		 * true if one of the messages matches an RPC reply. The special method
		 * name "__RPCReply" signals a message that is an RPC reply.
		 */
		public boolean queueEvalPred(Object arg) {
			if (arg instanceof ActorMsgRequest) {
				ActorMsgRequest nextMsg = (ActorMsgRequest) arg;
				if (nextMsg.method.equals("__RPCReply")
						|| ((nextMsg.method.equals("asynchException"))
								&& (nextMsg.methodArgs.length == 2)
								&& (ActorRequest.class
										.isInstance(nextMsg.methodArgs[0])) && (Exception.class
								.isInstance(nextMsg.methodArgs[1]))))
					return true;
			}

			return false;
		}
	}

	/**
	 * This inner class searches our mail queue for a properly formed
	 * "asynchException" message with a "cause" equal to a reference cause.
	 */
	class WaitAsynchExcept implements QueueSearch, Serializable {
		/**
	 * 
	 */
		private static final long serialVersionUID = 4902492096605734715L;
		/**
		 * The "cause" we are checking exceptions for.
		 */
		ActorRequest ourCause = null;

		/**
		 * Constructor which sets up the <em>ourCause</em> field.
		 */
		public WaitAsynchExcept(ActorRequest cause) {
			ourCause = cause;
		}

		/**
		 * We run this method on elements of our <em>mailQueue</em> and return
		 * true if there is a legal "asynchException" message with a "cause"
		 * equal to the cause we are associated with.
		 */
		public boolean queueEvalPred(Object arg) {
			if (arg instanceof ActorMsgRequest) {
				ActorMsgRequest nextMsg = (ActorMsgRequest) arg;
				return ((nextMsg.method.equals("asynchException"))
						&& (nextMsg.methodArgs.length == 2)
						&& (ActorRequest.class
								.isInstance(nextMsg.methodArgs[0]))
						&& (Exception.class.isInstance(nextMsg.methodArgs[1])) && (ourCause
						.equals(nextMsg.methodArgs[0])));
			}

			return false;
		}
	}

}
