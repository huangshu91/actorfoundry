package osl.manager.basic;

import java.io.IOException;
import java.io.OutputStream;

import kilim.pausable;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.manager.RemoteCodeException;
import osl.manager.StreamOutputActor;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.WaitQueue;

/**
 * This class defines the implementation of an actor used to control an output
 * stream on behalf of external actors. We require an implementation (rather
 * than an <em>Actor</em>) because if a security manager is running (i.e.
 * startfoundry was specified with the -secure option), then user-written actors
 * will not have direct access to several standard streams (e.g. System.out).
 * Since instances of <em>ActorImpl</em> are privileged they may control such
 * streams and provide their services to specific actors. The methods exported
 * to external actors is defined by the <em>StreamOutputActor</em> interface. In
 * the current implementation, the first argument passed in the creation request
 * for this actor is used to determine which output stream to manage. If the
 * argument is "out", then <em>System.out</em> is managed. If the argument is
 * "err", then <em>System.err</em> is managed. Otherwise, an error is returned
 * by <em>actorInitialize</em>.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1999/01/19 18:43:34 $)
 * @see osl.manager.basic.StreamOutputActor
 */

public class StreamOutputActorImpl extends ActorImpl implements
		StreamOutputActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 49808218011690044L;

	/**
	 * The <em>OutputStream</em> that should be served to external actors.
	 */
	private OutputStream out;

	/**
	 * The manager which manages this implementation.
	 */
	protected ActorManager ourManager;

	/**
	 * The queue which holds incoming requests.
	 */
	protected WaitQueue<ActorMsgRequest> mailQueue;

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
	 * For this implementation, the manager reference and actor name are saved.
	 * The first constructor argument in the creation request is expected to be
	 * either the strint "out" or "err", indicating which <em>System</em> stream
	 * should be managed. An error is returned if no string is present.
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
	 * @exception java.lang.RuntimeException
	 *                Thrown if no stream was specified in the first constructor
	 *                argument.
	 * @see osl.manager.ActorManager#implInitialize
	 */
	protected void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) {
		ourManager = ourMgr;
		self = you;
		mailQueue = new WaitQueue<ActorMsgRequest>();

		// Pull out the first constructor argument so we can figure out
		// which System stream to manage.
		if ((req.constructorArgs.length == 1)
				&& (req.constructorArgs[0] instanceof String)) {
			if (req.constructorArgs[0].equals("err"))
				out = System.err;
			else if (req.constructorArgs[0].equals("out"))
				out = System.out;
			else
				throw new RuntimeException(
						"Expected first constructor arg to be either \"out\" or \"err\"");
		} else
			throw new RuntimeException(
					"Expected first constructor arg to be either \"out\" or \"err\"");
	}

	/**
	 * This method is called by a manager to deliver a new message to the local
	 * actor. This method is protected so that it has package level protection
	 * and therefore may not be invoked directly by user-written actor code.
	 * <p>
	 * 
	 * For this implementation, we deposit the new message in our mail queue so
	 * that it can be processed in the main run loop.
	 * <p>
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> structure to be delivered.
	 *        This structure must be maintained by the actor as it is required
	 *        if an exception is returned to the manager.
	 */
	protected void actorDeliver(ActorMsgRequest msg) {
		mailQueue.enqueue(msg);
	}

	/**
	 * This method is called by the new manager of an actor implementation just
	 * after migration has occurred. The implementation is expected to perform
	 * any re-initialization necessary after a migration (e.g. rebuilding
	 * transient fields). This method will be called before the implementations
	 * thread is restarted.
	 * <p>
	 * 
	 * This method should never be called for this implementation as
	 * <em>StreamOutputActorImpl</em>s will never migrate. If this method IS
	 * called for some reason then a runtime exception is thrown.
	 * <p>
	 * 
	 * @param <b>ourMgr</b> A reference to the new manager of the implementation
	 *        after migration has occurred.
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 */
	protected void actorPostMigrateRebuild(ActorManager ourMgr) {
		throw new RuntimeException(
				"<StreamOutputActorImpl.actorPostMigrateRebuild> shold NEVER be called!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// Actor Interface Functions:
	//
	// None of these methods should ever be called since no internal
	// actor is ever created. If a method IS called then throw a
	// RuntimeException.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Request a message to be sent. The message argument is forwarded to the
	 * manager. If the RPC field of the message is set to true then the caller
	 * is blocked while the RPC takes place. This method is protected so that it
	 * has package level protection and therefore may not be invoked directly by
	 * user-written actor code.
	 * <p>
	 * 
	 * This method should never be called in this implementation since no
	 * internal actor is ever created.
	 * <p>
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> describing the message to
	 *        send.
	 * @return If this is an RPC request, then the return value from the message
	 *         call is returned. Otherwise, null is returned.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown only if this is an RPC exception which throws an
	 *                exception during invocation. An exception thrown in any
	 *                other case is an error.
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 * @see osl.manager.Actor#send(ActorName, String)
	 * @see osl.manager.Actor#call(ActorName, String)
	 */
	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implCall> should NEVER be called!");
	}

	protected Object implSend(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implSend> should NEVER be called!");
	}

	/**
	 * Request a new actor to be created. The request argument is forwarded to
	 * the manager and the returned name is passed on to the <em>Actor</em>
	 * caller. This method is protected so that it has package level protection
	 * and therefore may not be invoked directly by user-written actor code.
	 * <p>
	 * 
	 * This method should never be called in this implementation since no
	 * internal actor is ever created.
	 * <p>
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
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 * @see osl.manager.Actor#create(String)
	 * @see osl.manager.Actor#create(Class)
	 * @see osl.manager.Actor#create(ActorManagerName, String)
	 * @see osl.manager.Actor#create(ActorManagerName, Class)
	 */
	protected ActorName implCreate(ActorCreateRequest req)
			throws SecurityException, RemoteCodeException {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implCreate> should NEVER be called!");
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
	 * <p>
	 * 
	 * This method should never be called in this implementation since no
	 * internal actor is ever created.
	 * <p>
	 * 
	 * @param <b>loc</b> The <em>ActorManagerName</em> of the node to migrate
	 *        to.
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 * @see osl.manager.Actor#migrate
	 * @see osl.manager.Actor#cancelMigrate
	 */
	protected void implMigrate(ActorManagerName loc) {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implMigrate> should NEVER be called!");
	}

	/**
	 * Request a service invocation on the named node service. This request is
	 * forwarded to the actor manager and the appropriate return value is
	 * provided. This method is protected so that it has package level
	 * protection and therefore may not be invoked directly by user-written
	 * actor code.
	 * <p>
	 * 
	 * This method should never be called in this implementation since no
	 * internal actor is ever created.
	 * <p>
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
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 * @see osl.manager.Actor#invokeService
	 */
	protected Object implInvokeService(ServiceName name, String meth,
			Object[] args) throws ServiceNotFoundException, ServiceException {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implInvokeService> should NEVER be called!");
	}

	/**
	 * Request to remove this actor from the system. Normally, this method will
	 * not return as the actor is immediately removed from the system. Note that
	 * any actor garbage collection process is ignored in this call so that this
	 * actor may be removed even though it is accessible by other actors.
	 * <p>
	 * 
	 * This method should never be called in this implementation since no
	 * internal actor is ever created.
	 * <p>
	 * 
	 * @param <b>reason</b> A <em>String</em> giving a "reason" for the removal.
	 *        This string should normally be appended to the log for the actor
	 *        before removing it from the system.
	 * @exception java.lang.RuntimeException
	 *                Thrown if this method is ever called.
	 * @see osl.manager.Actor#destroy
	 */
	protected void implDestroy(String reason) {
		throw new RuntimeException(
				"<StreamOutputActorImpl.implDestroy> should NEVER be called!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// StreamOutputActor Interface Functions:
	//
	// These methods are required by the StreamOutputActor interface
	// we implement.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Writes a byte to the internal output stream.
	 * 
	 * @param <b>b</b> An <em>Integer</em> giving the byte to write.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte.
	 */
	public void write(Integer b) throws IOException {
		out.write(b.intValue());
	}

	/**
	 * Write the contents of a byte array to the internal output stream.
	 * 
	 * @param <b>b</b> A <em>Byte</em> array of data to write to the output
	 *        stream.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte
	 *                array.
	 */
	public void write(Byte[] b) throws IOException {
		write(b, 0, b.length);
	}

	/**
	 * Write a subsequence of a byte array to the output stream.
	 * 
	 * @param <b>b</b> A <em>Byte</em> array of data from which a subsequence
	 *        will be written to the output stream.
	 * @param <b>off</b> An <em>Integer</em> giving the index in the data to
	 *        start the write from.
	 * @param <b>len</b> An <em>Integer</em> giving the total number of bytes to
	 *        write.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the
	 *                subsequence of the byte array.
	 */
	public void write(Byte[] b, Integer off, Integer len) throws IOException {
		byte data[] = new byte[b.length];
		for (int i = 0; i < b.length; i++)
			data[i] = b[i].byteValue();

		out.write(data, off.intValue(), len.intValue());
	}

	/**
	 * Print a string to the output stream.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void print(String s) throws IOException {
		byte[] bArray = s.getBytes();

		out.write(bArray);
	}

	/**
	 * Print a string followed by a newline character.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void println(String s) throws IOException {
		print(s + "\n");
	}

	/**
	 * Print an object representation to the output stream.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void print(Object s) throws IOException {
		print(s.toString());
	}

	/**
	 * Print an object representation followed by a newline character.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void println(Object s) throws IOException {
		println(s.toString());
	}

	/**
	 * Flush the internal output stream.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while flushing the input
	 *                stream.
	 */
	public void flush() throws IOException {
		out.flush();
	}

	/**
	 * Close the internal output stream.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close() throws IOException {
		out.close();
	}

	// ///////////////////////////////////////////////////////////////////////
	// Main Run Loop
	// ///////////////////////////////////////////////////////////////////////
	/**
	 * The main run loop for this implementation. All messages are processed
	 * within this loop and replies are generated and sent as necessary.
	 */
	public void _runExecute() {
		ActorMsgRequest nextMsg = null;
		Object rVal = null;
		String mName = null;
		Object[] mArgs = null;

		try {
			// Log.println("<StreamOutputActorImpl.run> Stream actor started with name: "
			// + self);

			while (true) {
				if (!mailQueue.empty()) {

					// Get the next message to process
					nextMsg = (ActorMsgRequest) mailQueue.peekFront();
					mName = nextMsg.method;
					mArgs = nextMsg.methodArgs;
					rVal = null;

					try {
						// Figure out which method to process based on the
						// arguments and make the appropriate call. This is
						// currently pretty ugly but, c'est la vie.
						if ((mName.equals("asynchException"))
								&& (mArgs.length == 2)
								&& (mArgs[0] instanceof ActorRequest)
								&& (mArgs[1] instanceof Exception)) {
							// We just log these messages.
							// Log.println("Received exception for request: " +
							// mArgs[0]);
							// Log.println("Exception stack trace follows:");
							// Log.logExceptionTrace((Exception)mArgs[1]);

						} else if ((mName.equals("write"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Integer)) {
							write((Integer) mArgs[0]);

						} else if ((mName.equals("write"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Byte[])) {
							write((Byte[]) mArgs[0]);

						} else if ((mName.equals("write"))
								&& (mArgs.length == 3)
								&& (mArgs[0] instanceof Byte[])
								&& (mArgs[1] instanceof Integer)
								&& (mArgs[2] instanceof Integer)) {
							write((Byte[]) mArgs[0], (Integer) mArgs[1],
									(Integer) mArgs[2]);

						} else if ((mName.equals("print"))
								&& (mArgs.length == 1)) {
							print(mArgs[0]);

						} else if ((mName.equals("println"))
								&& (mArgs.length == 1)) {
							println(mArgs[0]);

						} else if ((mName.equals("flush"))
								&& (mArgs.length == 0)) {
							flush();

						} else if ((mName.equals("close"))
								&& (mArgs.length == 0)) {
							close();

						} else {
							// Throw a NoSuchMethodException. This will be
							// ignored
							// if the original message is asynchException, in
							// which
							// case we just log it.
							String argVals = "(";
							for (int i = 0; i < mArgs.length; i++)
								argVals = argVals
										+ mArgs[0].getClass().toString() + ", ";
							argVals = argVals + ")";
							throw new NoSuchMethodException(mName + argVals);
						}
					} catch (Exception e) {
						// Any exception caught here is sent back to the
						// original
						// caller as an asynchException message.
						Object[] args = new Object[2];
						ActorMsgRequest errMsg = new ActorMsgRequest(self,
								nextMsg.sender, "asynchException", args, false);
						args[0] = nextMsg;
						args[1] = e;
						errMsg.originator = self;

						mgrActorSend(ourManager, errMsg);
					}

					// If this message is an RPC request, then send out the
					// reply message
					if (nextMsg.RPCRequest) {
						Object[] returnIt = new Object[1];
						ActorMsgRequest theReply = new ActorMsgRequest(self,
								nextMsg.sender, "__RPCReply", returnIt, false);
						returnIt[0] = rVal;
						theReply.originator = self;

						mgrActorSend(ourManager, theReply);
					}

					// Dequeue the message once it has been processed.
					mailQueue.dequeue();
					// ActorMsgRequestPool.getInstance().put(mailQueue.dequeue());

				} else
					// See if the mail queue is still empty. If it is then
					// provide a hint to our scheduler and put this thread to
					// sleep. Otherwise we automatically go back and pick off
					// the message.
					synchronized (mailQueue) {
						if (mailQueue.empty()) {
							mailQueue.wait();
						}
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

}
