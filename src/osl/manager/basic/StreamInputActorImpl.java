package osl.manager.basic;

import java.io.IOException;

import kilim.pausable;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.manager.RemoteCodeException;
import osl.manager.StreamInputActor;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.WaitQueue;

/**
 * This class defines the implementation of an actor used to manage an input
 * stream on behalf of external actors. We require an implementation (rather
 * than an <em>Actor</em>) because if a security manager is running (i.e.
 * startfoundry was specified with the -secure option), then user-written actors
 * will not have direct access to several standard streams (e.g. System.out).
 * Since instances of <em>ActorImpl</em> are privileged they may control such
 * streams and provide their services to specific actors. The interface exported
 * to external actors is defined by the <em>StreamInputActor</em> interface. In
 * the current implementation, this actor will always use <em>System.in</em> as
 * the encapsulated input stream.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.2 $ ($Date: 1998/07/18 18:59:48 $)
 * @see osl.manager.basic.StreamInputActor
 */

public class StreamInputActorImpl extends ActorImpl implements StreamInputActor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1178506284039882972L;

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
	 * For this implementation, the manager reference and actor name are saved,
	 * but the actor create request is discarded since no internal actor is
	 * actually created.
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
	 * @see osl.manager.ActorManager#implInitialize
	 */
	protected void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) {
		ourManager = ourMgr;
		self = you;
		mailQueue = new WaitQueue<ActorMsgRequest>();
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
	 * <em>StreamInputActorImpl</em>s will never migrate. If this method IS
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
				"<StreamInputActorImpl.actorPostMigrateRebuild> shold NEVER be called!");
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
	protected Object implSend(ActorName dest, String meth, Object[] args,
			boolean byCopy) throws RemoteCodeException {
		throw new RuntimeException(
				"<StreamInputActorImpl.implSend> should NEVER be called!");
	}

	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"<StreamInputActorImpl.implCall> should NEVER be called!");
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
				"<StreamInputActorImpl.implCreate> should NEVER be called!");
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
				"<StreamInputActorImpl.implMigrate> should NEVER be called!");
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
				"<StreamInputActorImpl.implInvokeService> should NEVER be called!");
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
				"<StreamInputActorImpl.implDestroy> should NEVER be called!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// StreamInputActor Interface Functions:
	//
	// These methods are required by the StreamInputActor interface
	// we implement.
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
	 * @return An <em>Integer</em> containing the next byte of data or -1 if
	 *         end-of-file is reached.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream.
	 */
	public Integer read() throws IOException {
		return System.in.read();
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
	public void read(ActorName client, String method) throws IOException {
		send(client, method, System.in.read());
	}

	/**
	 * Read an array of bytes from the input stream and return them to the
	 * caller. The maximum number of bytes to read is specified by <b>max</b>.
	 * This method blocks until input is available. Normally, this method will
	 * be invoked using the "call" actor operation, as this is the only way to
	 * obtain the data returned from this method.
	 * <p>
	 * 
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
		int num = System.in.read(b);

		if (num == -1)
			return null;

		Byte[] retValue = new Byte[num];
		for (int i = 0; i < num; i++)
			retValue[i] = b[i];

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
		send(client, method, read(max));
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
	 * @param <b>n</b> A <em>Long</em> giving the number of bytes to be skipped.
	 * @return An <em>Long</em> giving the actual number of bytes skipped.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while skipping bytes.
	 */
	public Long skip(Long n) throws IOException {
		return System.in.skip(n.longValue());
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
		send(client, method, skip(n));
	}

	/**
	 * Return the number of bytes that can be read from the internal input
	 * stream without blocking. The number of bytes available is returned as an
	 * <em>Integer</em> to the caller. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @return An <em>Integer</em> giving the number of bytes that can be read
	 *         from this input stream without blocking.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to
	 *                determine the number of bytes available.
	 */
	public Integer available() throws IOException {
		return System.in.available();
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
		send(client, method, available());
	}

	/**
	 * Close the internal input stream. As this method has no return value, it
	 * may be called either synchronously or asynchronously.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close() throws IOException {
		System.in.close();
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
	 * @param <b>readlimit</b> An <em>Integer</em> indicating the maximum number
	 *        of bytes that can be read before the mark position becomes
	 *        invalid.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while placing the mark.
	 */
	public void mark(Integer readlimit) {
		System.in.mark(readlimit.intValue());
	}

	/**
	 * Reposition the internal stream to the position marked by a previous call
	 * to <em>mark</em>. As this method has no return value, it may be called
	 * either synchronously or asynchronously.
	 * <p>
	 * 
	 * @exception java.io.IOException
	 *                Thrown if the internal stream has not been marked, or if
	 *                the previously placed mark has been invalidated.
	 */
	public void reset() throws IOException {
		System.in.reset();
	}

	/**
	 * Test if the internal input stream supports the <em>mark</em> and
	 * <em>reset</em> methods. A <em>Boolean</em> is returned to the caller
	 * indicating the result of the query. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @return A <em>Boolean</em> indicating <tt>true</tt> if <em>mark</em> and
	 *         <em>reset</em> are supported, and <tt>false</tt> otherwise.
	 */
	public Boolean markSupported() {
		return new Boolean(System.in.markSupported());
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
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the <em>markSupported</em> status
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the <em>markSupported</em> status.
	 * @see osl.manager.StreamInputActor#markSupported()
	 */
	public void markSupported(ActorName client, String method) {
		send(client, method, markSupported());
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

		next = System.in.read();
		if (next == -1)
			return null;

		while ((next != -1) && (next != '\n')) {

			readChars[current++] = (char) next;

			if (current == readChars.length) {
				char[] copy = new char[readChars.length * 2];
				System.arraycopy(readChars, 0, copy, 0, current);
				readChars = copy;
			}

			next = (char) System.in.read();
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
		send(client, method, readln());
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
			// Log.println("<StreamInputActorImpl.run> Stream actor started with name: "
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

						} else if ((mName.equals("read"))
								&& (mArgs.length == 0)) {
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

	/**
	 * A send method similar to that in <em>Actor</em> which allows the
	 * <em>StreamInputActor</em> interface methods to send messags.
	 */
	void send(ActorName target, String method, Object arg) {
		try {
			Object[] args = new Object[1];
			ActorMsgRequest newMsg = new ActorMsgRequest(self, target, method,
					args);
			args[0] = arg;
			newMsg.originator = self;

			mgrActorSend(ourManager, newMsg);
		} catch (Exception e) {
			throw new RuntimeException("Exception while sending reply: " + e);
		}
	}

}
