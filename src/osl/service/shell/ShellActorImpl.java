package osl.service.shell;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import kilim.pausable;
import osl.manager.ActorContext;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.manager.RemoteCodeException;
import osl.manager.basic.BasicActorManager;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;

/**
 * This class acts as the actor interface for sessions created by the
 * <em>Shell</em> service. In particular, each shell session is associated with
 * its own <em>ShellActorImpl</em>. A session uses the implementation to receive
 * incoming messages as well as submit requests for sending messages or creating
 * actors. Note that instances of <em>ShellActorImpl</em> can not be migrated.
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/01/19 18:43:36 $)
 * @see Shell
 */

public class ShellActorImpl extends ActorImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4477855964109475749L;

	/**
	 * A static reference to the class object for this class. Saves us from
	 * having to do "Class.forName" lookups all over the place.
	 */
	static Class<?> classRef = null;

	// This is the code which initializes the static field above.
	static {
		try {
			classRef = Class.forName("osl.service.shell.ShellActorImpl");
		} catch (Exception e) {
			System.out
					.println("Fatal error initializing classRef static field: "
							+ e);
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}

	/**
	 * The alias of this actor that is used for interacting with the connected
	 * stdin stream.
	 */
	ActorName stdinName;

	/**
	 * The stdin stream we are connected to. This value will be NULL if the
	 * corresponding server socket was never connected.
	 */
	InputStream stdinStream = null;

	/**
	 * The alias of this actor that is used for interacting with the connected
	 * stdout stream.
	 */
	ActorName stdoutName;

	/**
	 * The stdout stream we are connected to. This value will be NULL if the
	 * corresponding server socket was never connected.
	 */
	OutputStream stdoutStream = null;

	/**
	 * The alias of this actor that is used for interacting with the connected
	 * stderr stream.
	 */
	ActorName stderrName;

	/**
	 * The stderr stream we are connected to. This value will be NULL if the
	 * corresponding server socket was never connected.
	 */
	OutputStream stderrStream = null;

	/**
	 * A reference to the <em>ActorManager</em> which created us. We use this
	 * reference to issue any requests (i.e. sending messages, creating new
	 * actors).
	 */
	ActorManager manager;

	/**
	 * A reference to the <em>ShellSession</em> we are associated with. We
	 * resolve this link within the call to <em>actorInitialize</em>.
	 */
	ShellSession ourSession;

	/**
	 * The create request used to create this actor.
	 */
	ActorCreateRequest create;

	/**
	 * This boolean is true if we are part of an active shell session, and false
	 * when the shell session is closed. Once set to false, the value of
	 * <em>ourSession</em> should be considered invalid.
	 */
	boolean active;

	// ///////////////////////////////////////////////////////////////////////
	// Manager Interface Functions:
	//
	// These methods are intended to be invoked by extensions of
	// the ActorManager abstract class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * This method initializes a new shell actor implementation which will be
	 * associated with a particular <em>ShellSession</em>. Within this method we
	 * immediately call <em>managerServiceInvoke</em> to set up a direct link
	 * with our session. The only constructor argument should be an
	 * <em>Integer</em> giving the ID of the local session we are associated
	 * with.
	 * 
	 * @param <b>ourMgr</b> The <em>ActorManager</em> which should be used by
	 *        this actor implementation to invoke actor services.
	 * @param <b>you</b> The <em>ActorName</em> that should be used as the name
	 *        of the new actor.
	 * @param <b>req</b> The <em>ActorCreateRequest</em> which describes the new
	 *        actor to be created.
	 */
	protected void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) {
		if (!(ourMgr instanceof BasicActorManager))
			throw new RuntimeException(
					"Error: shell actors may only be managed by instances of BasicActorManager");

		manager = ourMgr;
		self = you;
		create = req;
	}

	/**
	 * Receive a new message from our manager. Depending on who the message is
	 * targeted to, we may either deliver the message to the shell session by
	 * calling <em>ShellSession.newShellMsg</em>, or we may perform a stream
	 * operation. In either case, we dump the message if this shell session is
	 * no longer active.
	 * <p>
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> structure to be delivered.
	 *        This structure must be maintained by the actor as it is required
	 *        if an exception is returned to the manager.
	 */
	protected void actorDeliver(ActorMsgRequest msg) {
		Object rVal = null;
		String mName = null;
		Object[] mArgs = null;

		try {

			// PRAGMA [debug,osl.service.shell.ShellActorImpl]
			// Log.println("<ShellActorImpl> Handling request: " + msg);
			// We only deliver the message if we are still active (that is,
			// the shell session we are associated with is still running).
			// Otherwise we just dump the message.
			if (active) {
				// What we do depends on who the message is targetted to
				if (msg.receiver.equals(self)) {
					// give the message to our shell session
					ourSession.newShellMsg(msg);
					return;
				}

				// This message should be to one of our stream aliases so
				// handle it.
				try {

					// Decode the name and args
					mName = msg.method;
					mArgs = msg.methodArgs;
					rVal = null;

					if ((mName.equals("asynchException"))
							&& (mArgs.length == 2)
							&& (mArgs[0] instanceof ActorRequest)
							&& (mArgs[1] instanceof Exception)) {
						// We just log these messages.
						// Log.println(this, "Received exception for request: "
						// + mArgs[0]);
						// Log.println(this, "Exception stack trace follows:");
						// Log.logExceptionTrace(this, (Exception)mArgs[1]);
						return;

					} else if (msg.receiver.equals(stdinName)) {
						// this should be a request to the stdin stream we
						// encapsulate
						// If the stream connection was never answered then just
						// dump the message
						if (stdinStream == null)
							return;

						if ((mName.equals("read")) && (mArgs.length == 0)) {
							rVal = read();

						} else if ((mName.equals("read"))
								&& (mArgs.length == 2)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)) {
							read((ActorName) mArgs[0], (String) mArgs[1]);

						} else if ((mName.equals("read"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Integer)) {
							rVal = read((Integer) mArgs[0]);

						} else if ((mName.equals("read"))
								&& (mArgs.length == 3)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)
								&& (mArgs[2] instanceof Integer)) {
							read((ActorName) mArgs[0], (String) mArgs[1],
									(Integer) mArgs[2]);

						} else if ((mName.equals("skip"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Long)) {
							rVal = skip((Long) mArgs[0]);

						} else if ((mName.equals("skip"))
								&& (mArgs.length == 3)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)
								&& (mArgs[2] instanceof Long)) {
							skip((ActorName) mArgs[0], (String) mArgs[1],
									(Long) mArgs[2]);

						} else if ((mName.equals("available"))
								&& (mArgs.length == 0)) {
							rVal = available();

						} else if ((mName.equals("available"))
								&& (mArgs.length == 2)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)) {
							available((ActorName) mArgs[0], (String) mArgs[1]);

						} else if ((mName.equals("close"))
								&& (mArgs.length == 0)) {
							close();

						} else if ((mName.equals("mark"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Integer)) {
							mark((Integer) mArgs[0]);

						} else if ((mName.equals("reset"))
								&& (mArgs.length == 0)) {
							reset();

						} else if ((mName.equals("markSupported"))
								&& (mArgs.length == 0)) {
							rVal = markSupported();

						} else if ((mName.equals("markSupported"))
								&& (mArgs.length == 2)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)) {
							markSupported((ActorName) mArgs[0],
									(String) mArgs[1]);

						} else if ((mName.equals("readln"))
								&& (mArgs.length == 0)) {
							rVal = readln();

						} else if ((mName.equals("readln"))
								&& (mArgs.length == 2)
								&& (mArgs[0] instanceof ActorName)
								&& (mArgs[1] instanceof String)) {
							readln((ActorName) mArgs[0], (String) mArgs[1]);

						} else {
							// Throw a NoSuchMethodException.
							String argVals = "(";
							for (int i = 0; i < mArgs.length; i++)
								argVals = argVals
										+ mArgs[0].getClass().toString() + ", ";
							argVals = argVals + ")";
							throw new NoSuchMethodException(mName + argVals);
						}
					} else {
						// this is either a request for the stdout or stderr
						// stream we
						// encapsulate
						OutputStream out = (msg.receiver.equals(stdoutName) ? stdoutStream
								: stderrStream);

						// If the stream connection was never answered then just
						// dump the message
						if (out == null)
							return;

						if ((mName.equals("write")) && (mArgs.length == 1)
								&& (mArgs[0] instanceof Integer)) {
							write(out, (Integer) mArgs[0]);

						} else if ((mName.equals("write"))
								&& (mArgs.length == 1)
								&& (mArgs[0] instanceof Byte[])) {
							write(out, (Byte[]) mArgs[0]);

						} else if ((mName.equals("write"))
								&& (mArgs.length == 3)
								&& (mArgs[0] instanceof Byte[])
								&& (mArgs[1] instanceof Integer)
								&& (mArgs[2] instanceof Integer)) {
							write(out, (Byte[]) mArgs[0], (Integer) mArgs[1],
									(Integer) mArgs[2]);

						} else if ((mName.equals("print")) &&
						// (mArgs.length == 1) &&
								// (mArgs[0] instanceof String) ) {
								(mArgs.length == 1)) {
							print(out, mArgs[0].toString());

						} else if ((mName.equals("println")) &&
						// (mArgs.length == 1) &&
								// (mArgs[0] instanceof String) ) {
								(mArgs.length == 1)) {
							println(out, mArgs[0].toString());

						} else if ((mName.equals("flush"))
								&& (mArgs.length == 0)) {
							flush(out);

						} else if ((mName.equals("close"))
								&& (mArgs.length == 0)) {
							close(out);

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
					}

					// If this message is an RPC request, then send out the
					// reply message
					if (msg.RPCRequest) {
						Object[] returnIt = new Object[1];
						ActorMsgRequest theReply = new ActorMsgRequest(
								msg.receiver, msg.sender, "__RPCReply",
								returnIt, false);
						returnIt[0] = rVal;
						theReply.originator = self;
						stampRequest(theReply);

						// PRAGMA [debug,osl.service.shell.ShellActorImpl]
						// Log.println("<ShellActorImpl> Sending reply message: "
						// + theReply);
						mgrActorSend(manager, theReply);
					}

				} catch (Exception e) {
					// Any exception caught here is sent back to the original
					// caller as an asynchException message.
					Object[] args = new Object[2];
					ActorMsgRequest errMsg = new ActorMsgRequest(msg.receiver,
							msg.sender, "asynchException", args, false);
					args[0] = msg;
					args[1] = e;
					errMsg.originator = self;
					stampRequest(errMsg);

					mgrActorSend(manager, errMsg);
				}
			}
		} catch (Throwable e) {
			// Any errors which we fail to trap above are bonafied bugs so
			// we kill ourselves off here. Note that we ignore ThreadDeath
			// in case this thread was being killed for some strange reason.
			if (e instanceof ThreadDeath)
				throw (ThreadDeath) e;
			else
				mgrActorFatalError(manager, new RemoteCodeException(
						"Error in <ShellActorImpl.actorDeliver>:", e));
		}

	}

	/**
	 * As <em>ShellActorImpl</em> can not migrate, this method should never be
	 * called. If it is, then throw a fatal exception.
	 * 
	 * @param <b>ourMgr</b> A reference to the new manager of the implementation
	 *        after migration has occurred.
	 */
	protected void actorPostMigrateRebuild(ActorManager ourMgr) {
		throw new RuntimeException(
				"Shell actor implementations should NEVER migrate!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// 
	// This method required since actor implementations extend Thread
	//
	// ///////////////////////////////////////////////////////////////////////
	/**
	 * The main processing loop for this actor. All we do here is call
	 * <em>ourSession.shellMainLoop</em>. This allows the shell session to begin
	 * processing commands with the proper permissions for invoking the local
	 * "impl" commands.
	 */
	@pausable
	public void execute() {
		try {
			// Log.println("<ShellActorImpl.run>: Requesting session reference from service...");
			Object[] invokeArgs = new Object[1];
			invokeArgs[0] = create.constructorArgs[0];
			ourSession = (ShellSession) mgrActorInvokeService(manager,
					Shell.name, "shellProxyInit", invokeArgs);
		} catch (Exception e) {
			// If we get an error here then we have no reliable way of
			// reporting it because the creator of this actor is set to
			// null. So, just log the error and kill ourselves by calling
			// actorFatalError.
			// Log.println("Error in run loop of shell actor: " + e);

			// Have to do some handstands to log the stack trace.
			CharArrayWriter temp1 = new CharArrayWriter();
			PrintWriter temp2 = new PrintWriter(temp1, true);
			e.printStackTrace(temp2);
			temp2.close();
			temp1.close();
			// Log.println(temp1.toString());

			// kill ourselves
			mgrActorFatalError(manager, e);
		}

		// Now initialize our session actor before running it
		// Log.println("<ShellActorImpl.run>: Received references, initializing session...");
		ourSession.ourActor = self;
		ourSession.ourActorRef = this;
		active = true;

		// Log.println("<ShellActorImpl.run>: Creating stream aliases...");
		stdinName = ((BasicActorManager) manager).actorCreateAlias(this);
		stdoutName = ((BasicActorManager) manager).actorCreateAlias(this);
		stderrName = ((BasicActorManager) manager).actorCreateAlias(this);

		// When the shell loop exits we are considered in active. We also
		// close the standard streams at this point.
		ourSession.shellMainLoop();
		try {
			if (stdinStream != null)
				stdinStream.close();
			if (stdoutStream != null)
				stdoutStream.close();
			if (stderrStream != null)
				stderrStream.close();
		} catch (IOException e) {
			// We ignore these since it probably indicates that the
			// connection has already been closed.
		}
		active = false;

		// Even though our shell session has died at this point. We still
		// need to keep this actor around as our address may have been
		// propagated over the network. So we just suspend the actor here
		// and rely on garbage collection to eventually clean up old shell
		// actors.
		// suspend();

	}

	// ///////////////////////////////////////////////////////////////////////
	// Actor Interface Functions:
	//
	// These methods are intended to be invoked by the Actor class.
	// In this case, however, these methods are actually invoked by
	// an instance of ShellSession.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Request that a message be sent. The sender field is automatically reset
	 * to be this local actor so that we correctly receive error messages
	 * resulting from the send. Note that RPC's are not handled automatically.
	 * That is, the caller will have to parse incoming messages for the __reply
	 * message.
	 * 
	 * @param <b>msg</b> The <em>ActorMsgRequest</em> describing the message to
	 *        send.
	 * @return If this is an RPC request, then the return value from the message
	 *         call is returned. Otherwise, null is returned.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown only if this is an RPC exception which throws an
	 *                exception during invocation. An exception thrown in any
	 *                other case is an error.
	 */
	protected Object implSend(ActorMsgRequest msg) throws RemoteCodeException {
		msg.sender = self;
		stampRequest(msg);
		mgrActorSend(manager, msg);

		// We always return null. It is up to the shell to handle RPC's
		// if it wants to (e.g. by scanning the incoming mail queue).
		return null;
	}

	protected Object implSend(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"ERROR: implSend of ShellActorImpl should NEVER be called!");
	}

	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"ERROR: implCall of ShellActorImpl should NEVER be called!");
	}

	/**
	 * Request a new actor to be created. The "requester" field is automatically
	 * set to the name of this actor so that error messages are correctly
	 * received. The name of the new actor is returned.
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

		req.requester = self;
		stampRequest(req);
		req.context = new ActorContext();

		req.context.stdin = stdinName;
		req.context.stdout = stdoutName;
		req.context.stderr = stderrName;

		return mgrActorCreate(manager, req);
	}

	/**
	 * This method should never be invoked and results in an immediate fatal
	 * error.
	 * 
	 * @param <b>loc</b> The <em>ActorManagerName</em> of the node to migrate
	 *        to.
	 */
	protected void implMigrate(ActorManagerName loc) {
		throw new RuntimeException(
				"Shell actor implementations should NEVER migrate!");
	}

	/**
	 * Invoke a service on behalf of the associated shell session.
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
		return mgrActorInvokeService(manager, name, meth, args);
	}

	/**
	 * Destroy the shell actor. Currently this is just a stub which returns an
	 * exception if it is called. I don't envision shell actor's ever being
	 * destroyed in this way (the SHOULD be garbage collected however).
	 */
	protected void implDestroy(String reason) {
		throw new RuntimeException(
				"ERROR: implDestroy of ShellActorImpl should NEVER be called!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// Methods for implementing stdout, stdin, and stderr shell streams
	//
	// These methods are nearly identical to those required by
	// StreamInputActor and StreamOutputActor except that we specify
	// the local stream which will be used for the interaction.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Read the next byte of data from an input stream. The value returned is an
	 * <em>Integer</em> in the range <tt>0</tt> to <tt>255</tt> (i.e. a
	 * character). This method blocks until a byte of data is available, or
	 * end-of-file is reached. If end-of-file is encountered then a -1 is
	 * returned. Normally, this method will be invoked using the "call" actor
	 * operation, as this is the only way to obtain the data returned from this
	 * method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @return An <em>Integer</em> containing the next byte of data or -1 if
	 *         end-of-file is reached.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream.
	 */
	Integer read() throws IOException {
		return new Integer(stdinStream.read());
	}

	/**
	 * Read the next byte of data from an input stream and send it to a
	 * specified actor. The value returned is an <em>Integer</em> in the range
	 * <tt>0</tt> to <tt>255</tt> (i.e. a character). This method blocks until a
	 * byte of data is available, or end-of-file is reached. If end-of-file is
	 * encountered then a -1 is returned. The result is sent to the actor with
	 * name <b>client</b> by invoking method <b>method</b>. Thus, <b>client</b>
	 * is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Integer</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the data.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the data.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream. For asynchronous calls, this exception is normally
	 *                returned as an invocation of the asynchException" method.
	 * @see osl.manager.StreamInputActor#read()
	 */
	void read(ActorName client, String method) throws IOException {
		send(stdinName, client, method, new Integer(stdinStream.read()));
	}

	/**
	 * Read an array of bytes from the input stream and return them to the
	 * caller. The maximum number of bytes to read is specified by <b>max</b>.
	 * This method blocks until input is available. Normally, this method will
	 * be invoked using the "call" actor operation, as this is the only way to
	 * obtain the data returned from this method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>max</b> An <em>Integer</em> giving the maximum number of bytes
	 *        to read from the stream.
	 * @return A <b>Byte</b> array giving the data read from the stream. The
	 *         return value is <tt>null</tt> if no data was available because
	 *         end-of-file was encountered. Otherwise, the length of the array
	 *         indicates the actual number of bytes read.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream, or if <b>max</b> is less than one.
	 */
	public Byte[] read(Integer max) throws IOException {
		int M = max.intValue();

		if (M < 1)
			throw new IOException("argument max must be greater than zero");

		byte b[] = new byte[M];
		int num = stdinStream.read(b);

		if (num == -1)
			return null;

		Byte[] retValue = new Byte[num];
		for (int i = 0; i < num; i++)
			retValue[i] = new Byte(b[i]);

		return retValue;
	}

	/**
	 * Read an array of bytes from the input stream and send them to a specified
	 * actor. The maximum number of bytes to read is specified by <b>max</b>.
	 * This method blocks until input is available. The result is sent to the
	 * actor with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Byte[]</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the data.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the data.
	 * @param <b>max</b> An <em>Integer</em> giving the maximum number of bytes
	 *        to read from the stream.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream, or if <b>max</b> is less than one.
	 * @see osl.manager.StreamInputActor#read(Integer)
	 */
	public void read(ActorName client, String method, Integer max)
			throws IOException {
		send(stdinName, client, method, read(max));
	}

	/**
	 * Skip over and discard <b>n</b> bytes of data from the input stream.
	 * Depending on the internal <em>InputStream</em>, the actual number of
	 * bytes skipped may vary. The number of bytes skipped is returned as the
	 * result of this method. Normally, this method will be invoked using the
	 * "call" actor operation, as this is the only way to obtain the data
	 * returned from this method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>n</b> A <em>Long</em> giving the number of bytes to be skipped.
	 * @return An <em>Long</em> giving the actual number of bytes skipped.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while skipping bytes.
	 */
	public Long skip(Long n) throws IOException {
		return new Long(stdinStream.skip(n.longValue()));
	}

	/**
	 * Skip over and discard <b>n</b> bytes of data from the input stream.
	 * Depending on the internal <em>InputStream</em>, the actual number of
	 * bytes skipped may vary. The number of bytes skipped is sent to the actor
	 * with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Long</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the number of bytes skipped.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the number of bytes skipped.
	 * @param <b>n</b> A <em>Long</em> giving the number of bytes to be skipped.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while skipping bytes.
	 * @see osl.manager.StreamInputActor#skip(Long)
	 */
	public void skip(ActorName client, String method, Long n)
			throws IOException {
		send(stdinName, client, method, skip(n));
	}

	/**
	 * Return the number of bytes that can be read from the internal input
	 * stream without blocking. The number of bytes available is returned as an
	 * <em>Integer</em> to the caller. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @return An <em>Integer</em> giving the number of bytes that can be read
	 *         from this input stream without blocking.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to
	 *                determine the number of bytes available.
	 */
	public Integer available() throws IOException {
		return new Integer(stdinStream.available());
	}

	/**
	 * Determine the number of bytes that can be read from the internal input
	 * stream without blocking, and send the result to the specified caller. The
	 * number of bytes available is sent to the actor with name <b>client</b> by
	 * invoking method <b>method</b>. Thus, <b>client</b> is expected to define
	 * a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Integer</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the number of bytes available.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the number of bytes available.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to
	 *                determine the number of bytes available.
	 * @see osl.manager.StreamInputActor#available()
	 */
	public void available(ActorName client, String method) throws IOException {
		send(stdinName, client, method, available());
	}

	/**
	 * Close the internal input stream. As this method has no return value, it
	 * may be called either synchronously or asynchronously.
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close() throws IOException {
		stdinStream.close();
	}

	/**
	 * Mark the current position in the internal stream. Later calls to
	 * <em>reset</em> will reposition the internal stream at the last marked
	 * position. A <em>readlimit</em> may be specified which indicates the
	 * number of bytes which may be read before the mark position becomes
	 * invalid. As this method has no return value, it may be called either
	 * synchronously or asynchronously.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>readlimit</b> An <em>Integer</em> indicating the maximum number
	 *        of bytes that can be read before the mark position becomes
	 *        invalid.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while placing the mark.
	 */
	public void mark(Integer readlimit) {
		stdinStream.mark(readlimit.intValue());
	}

	/**
	 * Reposition the internal stream to the position marked by a previous call
	 * to <em>mark</em>. As this method has no return value, it may be called
	 * either synchronously or asynchronously.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @exception java.io.IOException
	 *                Thrown if the internal stream has not been marked, or if
	 *                the previously placed mark has been invalidated.
	 */
	public void reset() throws IOException {
		stdinStream.reset();
	}

	/**
	 * Test if the internal input stream supports the <em>mark</em> and
	 * <em>reset</em> methods. A <em>Boolean</em> is returned to the caller
	 * indicating the result of the query. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @return A <em>Boolean</em> indicating <tt>true</tt> if <em>mark</em> and
	 *         <em>reset</em> are supported, and <tt>false</tt> otherwise.
	 */
	public Boolean markSupported() {
		return new Boolean(stdinStream.markSupported());
	}

	/**
	 * Test if the internal input stream supports the <em>mark</em> and
	 * <em>reset</em> methods. The <em>Boolean</em> result is sent to the actor
	 * with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Boolean</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the <em>markSupported</em> status
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the <em>markSupported</em> status.
	 * @see osl.manager.StreamInputActor#markSupported()
	 */
	public void markSupported(ActorName client, String method) {
		send(stdinName, client, method, markSupported());
	}

	/**
	 * Read a line of characters from the internal input stream. A line is any
	 * sequence of characters terminated by a newline. This call will block
	 * until either a newline terminates a sequence of characters, or
	 * end-of-file is encountered. In either case, a <em>Character</em> array
	 * containing the characters read (minus the newline terminator) is returned
	 * to the caller. If no characters were available (e.g. because end-of-file
	 * was encountered immediately), then <tt>null</tt> is returned. Normally,
	 * this method will be invoked using the "call" actor operation, as this is
	 * the only way to obtain the data returned from this method.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @return A <em>Character</em> array containing all the characters read up
	 *         to a terminating newline or end-of-file, or <tt>null</tt> if no
	 *         characters were available do to end-of-file.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading a line of
	 *                characters.
	 */
	public Character[] readln() throws IOException {
		int current = 0;
		char[] readChars = new char[10];
		int next;

		next = stdinStream.read();
		if (next == -1)
			return null;

		while ((next != -1) && (next != '\n')) {

			readChars[current++] = (char) next;
			if (current == readChars.length) {
				char[] copy = new char[readChars.length * 2];
				System.arraycopy(readChars, 0, copy, 0, current);
				readChars = copy;
			}

			next = (char) stdinStream.read();
		}

		Character[] retVal = new Character[current];
		for (int i = 0; i < current; i++)
			retVal[i] = readChars[i];

		return retVal;

	}

	/**
	 * Read a line of characters from the internal input stream. A line is any
	 * sequence of characters terminated by a newline. This call will block
	 * until either a newline terminates a sequence of characters, or
	 * end-of-file is encountered. In either case, a <em>Character</em> array
	 * containing the characters read (minus the newline terminator) is sent to
	 * the actor with name <b>client</b> by invoking method <b>method</b>. If no
	 * characters were available (e.g. because end-of-file was encountered
	 * immediately), then <tt>null</tt> is sent to <b>client</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Character</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> to use for the operation.
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the line of characters.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which should recieve the line of characters.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading a line of
	 *                characters.
	 * @see osl.manager.StreamInputActor#readln()
	 */
	public void readln(ActorName client, String method) throws IOException {
		send(stdinName, client, method, readln());
	}

	/**
	 * Writes a byte to the internal output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @param <b>b</b> An <em>Integer</em> giving the byte to write.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte.
	 */
	public void write(OutputStream out, Integer b) throws IOException {
		out.write(b.intValue());
	}

	/**
	 * Write the contents of a byte array to the internal output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @param <b>b</b> A <em>Byte</em> array of data to write to the output
	 *        stream.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte
	 *                array.
	 */
	public void write(OutputStream out, Byte[] b) throws IOException {
		write(out, b, 0, b.length);
	}

	/**
	 * Write a subsequence of a byte array to the output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
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
	public void write(OutputStream out, Byte[] b, Integer off, Integer len)
			throws IOException {
		byte data[] = new byte[b.length];
		for (int i = 0; i < b.length; i++)
			data[i] = b[i].byteValue();

		out.write(data, off.intValue(), len.intValue());
	}

	/**
	 * Print a string to the output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void print(OutputStream out, String s) throws IOException {
		byte[] bArray = s.getBytes();

		out.write(bArray);
	}

	/**
	 * Print a string followed by a newline character.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void println(OutputStream out, String s) throws IOException {
		print(out, s + "\n");
		out.flush();
	}

	/**
	 * Flush the internal output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while flushing the input
	 *                stream.
	 */
	public void flush(OutputStream out) throws IOException {
		out.flush();
	}

	/**
	 * Close the internal output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to perform the operation on.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close(OutputStream out) throws IOException {
		out.close();
	}

	/**
	 * A send method similar to that in <em>Actor</em> which allows the
	 * <em>StreamInputActor</em> interface methods to send messags.
	 */
	void send(ActorName sender, ActorName target, String method, Object arg) {
		try {
			Object[] args = new Object[1];
			ActorMsgRequest newMsg = new ActorMsgRequest(sender, target,
					method, args);
			args[0] = arg;
			newMsg.originator = self;
			stampRequest(newMsg);

			mgrActorSend(manager, newMsg);
		} catch (Exception e) {
			throw new RuntimeException("Exception while sending reply: " + e);
		}
	}

	/**
	 * This method allows fatal errors to be signalled by the other classes in
	 * this package.
	 */
	void mgrActorFatalError(Exception e) {
		super.mgrActorFatalError(manager, e);
	}
}
