package osl.service.shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import osl.manager.Actor;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.service.ServiceName;
import osl.util.Queue;

/**
 * This class actually handles the sessions opened by the shell service.
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1998/10/05 15:47:44 $)
 * @see osl.service.Service
 */

public class ShellSession implements Runnable {
	/**
	 * A reference to the <em>Shell</em> service which created us.
	 */
	Shell parent = null;

	/**
	 * The ID of this session.
	 */
	int ID;

	/**
	 * A hashtable holding all the objects instantiated by this session.
	 */
	Hashtable<Object, Object> symbols;

	/**
	 * A reference to the socket we use to interact with the shell client.
	 */
	Socket client;

	/**
	 * An integer which is used to generate the unique part of each shell
	 * variable name for this shell session.
	 */
	int nextShellName = 0;

	/**
	 * The input stream tokenizer which we use to read new tokens for this
	 * session.
	 */
	// StreamTokenizer inReader = null;
	/**
	 * The input stream reader which we use to parse user input.
	 */
	InputStreamReader inReader = null;

	/**
	 * The output stream to which we should write replies for this session.
	 */
	PrintWriter outWrite;

	/**
	 * The name of the <em>ShellActorImpl</em> associated with this shell
	 * session.
	 */
	ActorName ourActor;

	/**
	 * A direct reference to the <em>ShellActorImpl</em> associated with this
	 * shell session. This value is set by our managing parent after the
	 * <em>ShellActorImpl</em> is created and invokes the shell service with a
	 * reference to itself.
	 */
	ShellActorImpl ourActorRef;

	/**
	 * This queue holds incoming messages we have received through our local
	 * <em>ShellActorImpl</em>. We deliver these messages on request to the
	 * client.
	 */
	Queue<ActorMsgRequest> msgQueue;

	/**
	 * These constants indicate what type of stream should be assigned within
	 * the EstablishStreamConnect inner class.
	 */
	static final int SET_STDIN = 0;
	static final int SET_STDOUT = 1;
	static final int SET_STDERR = 2;

	/**
	 * Class constructor.
	 * 
	 * @param <b>C</b> The <em>Socket</em> we should use to interact with the
	 *        client for this shell.
	 * @param <b>I</b> The <em>int</em> ID associated with this session. This
	 *        value is unique local to this shell service instance.
	 */
	public ShellSession(Shell P, Socket C, int I) {
		parent = P;
		ID = I;
		client = C;
		symbols = new Hashtable<Object, Object>();
		msgQueue = new Queue<ActorMsgRequest>();
	}

	/**
	 * Return a fresh shell variable name.
	 */
	String newName() {
		return "SV_" + nextShellName++;
	}

	/**
	 * This is where a shell session initially starts. We first create a set of
	 * streams for client interaction. A <em>ShellActorImpl</em> is instantiated
	 * locally in order to serve as a surrogate for the shell. When this actor
	 * is created it obtains a direct reference to this instance, and calls
	 * <em>shellMainLoop</em> with its local thread. This gives the shell
	 * session the proper permissions required for calling the "actor" functions
	 * in the local actor manager. When <em>shellMainLoop</em> exits, the shell
	 * session is terminated.
	 */
	public void run() {
		try {
			// Log our output
			// Log.logThread("ShellService", Thread.currentThread());

			// Create streams for the connection to our client.
			InputStream inStream = client.getInputStream();
			OutputStream outStream = client.getOutputStream();

			inReader = new InputStreamReader(inStream);
			outWrite = new PrintWriter(outStream);

			// Before we process any commands, attempt to create a
			// ShellActorImpl for use with this session. If we fail then so
			// does the session.
			try {
				Object[] args = new Object[1];
				args[0] = new Integer(ID);
				ActorCreateRequest shellActorCreate = new ActorCreateRequest(
						null, Actor.getClassRef(), ShellActorImpl.classRef,
						args, null);

				// Note that we don't save the name after the create since it
				// is set automatically by the new actor when it is created.
				parent.ourMgr.managerCreate(shellActorCreate, null);
			} catch (Exception e) {
				outWrite
						.println("Unable to create shell actor, closing connection");
				outWrite.flush();
				client.close();
				// Log.println("Failed to create shell actor: " + e);
				// Log.logExceptionTrace(e);
			}

			// If the actor is created successfully then it will
			// automatically call shellMainLoop.

		} catch (Throwable e) {
			// If we get an exception then just close the stream and write
			// out a message to the monitor window if it exists
			// Log.println("Shell Stream Closed: returned exception " + e);
			// Log.println("Detail message is " + e.getMessage());
			// Log.logExceptionTrace(e);
		}
	}

	/**
	 * This static table defines the legal characters that make up a token.
	 * Strings are a special case which may contain any arbitrary sequence of
	 * characters delineated by quotes.
	 */
	static boolean[] legalChars = new boolean[256];

	// Set up the legal chars array here
	static {
		char i;
		int j;

		for (j = 0; j < 256; j++)
			legalChars[j] = false;

		// Lowercase alphabet is ok
		for (i = 'a'; i <= 'z'; i++)
			legalChars[i] = true;

		// Uppercase alphabet is ok
		for (i = 'A'; i <= 'Z'; i++)
			legalChars[i] = true;

		// Numbers are ok
		for (i = '0'; i <= '9'; i++)
			legalChars[i] = true;

		// Certain special characters are ok
		legalChars['_'] = true;
		legalChars['.'] = true;
		legalChars[':'] = true;
		legalChars['+'] = true;
		legalChars['-'] = true;
		legalChars['['] = true;
		legalChars[']'] = true;
		legalChars['<'] = true;
		legalChars['>'] = true;

		// Everything else is "not legal" and is treated as whitespace.
	}

	/**
	 * Parse the next token from the input stream. This method returns a command
	 * token representing the next token read from the stream. We block if there
	 * are not enough characters in the stream to make a complete token. Returns
	 * null if the end of the stream has been reached.
	 */
	CmdToken getNextToken(InputStreamReader in) {
		int next;
		String tokenContents;
		try {
			// The next token starts with the first non-whitespace character
			next = in.read();
			if (next == -1)
				return null;

			while ((next != '"') && (!legalChars[next])) {
				next = in.read();
				if (next == -1)
					return null;
			}

			if (next == '"') {
				// If this is a string then have to do some special processing
				tokenContents = "";

				next = in.read();
				if (next == -1)
					return null;

				while (next != '"') {
					if (next == '\\') {
						// if this is an escape then parse it specially
						next = in.read();
						if (next == -1)
							return null;
						switch (next) {
						case '\\':
							tokenContents = tokenContents + '\\';
							break;
						case 'b':
							tokenContents = tokenContents + '\b';
							break;
						case 'f':
							tokenContents = tokenContents + '\f';
							break;
						case 'n':
							tokenContents = tokenContents + '\n';
							break;
						case 'r':
							tokenContents = tokenContents + '\r';
							break;
						case 't':
							tokenContents = tokenContents + '\t';
							break;
						case '"':
							tokenContents = tokenContents + '"';
							break;
						case '0':
							tokenContents = tokenContents + '0';
							break;
						default:
							return null;
						}

					} else {
						// If not an escape then just add the token as usual
						tokenContents = tokenContents + (char) next;
					}

					// Read the next char and loop
					next = in.read();
					if (next == -1)
						return null;
				}

				return new CmdToken(tokenContents, true);

			} else {
				// otherwise, the token ends when we encounter the first
				// whitespace character
				tokenContents = "" + (char) next;
				next = in.read();
				if (next == -1)
					return null;
				while (legalChars[next]) {
					tokenContents = tokenContents + (char) next;
					next = in.read();
					if (next == -1)
						return null;
				}

				// Done parsing, build a token and return it
				return new CmdToken(tokenContents, false);
			}
		} catch (IOException e) {
			// Log.println("<ShellSession.getNextToken>: received exception " +
			// e);
			return null;
		}

	}

	/**
	 * This is the main processing loop for this shell session. The loop is
	 * actually executed by the thread allocated to a <em>ShellActorImpl</em>
	 * instance. This is done so that permissions are set correctly when the
	 * user requests to send messages or create actors.
	 */
	void shellMainLoop() {
		Queue<CmdToken> tokens = null;
		CmdToken token = null;

		try {
			// Log.println("<ShellSession.shellMainLoop>: Starting main shell processing loop...");

			// Spin until the local actor name has been set
			symbols.put("SV_self", ourActor);

			// Spawn off three threads to listen on the three stream sockets
			// we'll create to handle stdin, stdout and stderr respectively.
			ServerSocket newStreamListen;
			String portSpecs = "";

			newStreamListen = new ServerSocket(0);
			portSpecs = portSpecs + newStreamListen.getLocalPort() + " ";
			parent.ourScheduler.scheduleThread(new Thread(
					new EstablishStreamConnect(ourActorRef, newStreamListen,
							SET_STDIN), "stdin"));

			newStreamListen = new ServerSocket(0);
			portSpecs = portSpecs + newStreamListen.getLocalPort() + " ";
			parent.ourScheduler.scheduleThread(new Thread(
					new EstablishStreamConnect(ourActorRef, newStreamListen,
							SET_STDOUT), "stdout"));

			newStreamListen = new ServerSocket(0);
			portSpecs = portSpecs + newStreamListen.getLocalPort() + " SV_END";
			parent.ourScheduler.scheduleThread(new Thread(
					new EstablishStreamConnect(ourActorRef, newStreamListen,
							SET_STDERR), "stderr"));

			// Now write out the port specs before we start anything else
			outWrite.println(portSpecs);
			outWrite.flush();

			// Stay in the processing loop until we hit a fatal error or the
			// connection is closed by the user
			while (true) {

				// Read a line of input and break it into tokens
				tokens = new Queue<CmdToken>();
				token = getNextToken(inReader);
				while ((token != null) && (!token.text.equals("SV_END"))) {
					tokens.enqueue(token);
					token = getNextToken(inReader);
				}

				if (token == null) {
					// Log.println("<ShellSession.shellMainLoop>: Shell Stream Closed, terminating...");
					return;
				}

				// Parse the command and attempt to perform it
				if (tokens.numElements() == 0) {
					outWrite.println("SV_ERROR command string empty SV_END");
					outWrite.flush();
				} else {
					String cmd = ((CmdToken) tokens.dequeue()).text;
					switch (parent.parseCommand(cmd)) {

					case Shell.CMD_CLOSE:
						// End this session and remove the reference to
						// ourselves so we can be GC'd.
						outWrite.println("SV_END");
						outWrite.flush();
						parent.sessions.remove(new Integer(ID));
						client.close();
						// Log.println("<ShellSession.shellMainLoop>: Shell Stream Closed, terminating...");
						return;

					case Shell.CMD_INSTOBJ:
						// Instantiate an arbitrary Java object
						cmdInstObj(tokens);
						break;

					case Shell.CMD_INVOKE:
						// Invoke a method on an object stored in a shell
						// variable
						cmdInvoke(tokens);
						break;

					case Shell.CMD_CREATE:
						// Create a new actor and save the name in a new shell
						// variable
						cmdCreate(tokens);
						break;

					case Shell.CMD_SEND:
						// Send a message to an actor
						cmdSend(tokens);
						break;

					case Shell.CMD_PRINT:
						// Output the result of "toString" when invoked on an
						// object stored in a shell variable.
						cmdPrint(tokens);
						break;

					case Shell.CMD_MSGQUEUESIZE:
						// Return the current size of the incoming mail queue
						cmdMqueueSize(tokens);
						break;

					case Shell.CMD_MSGGETMETHOD:
						// Output the string naming the method of the first
						// message in the mail queue. Returns an error if the
						// mail queue is empty.
						cmdMsgGetMethod(tokens);
						break;

					case Shell.CMD_MSGGETARGS:
						// Extract and create a new shell variable to hold each
						// argument passed in the first message in the mail
						// queue. Returns an error if the mail queue is empty.
						cmdMsgGetArgs(tokens);
						break;

					case Shell.CMD_MSGGETSENDER:
						// Extract and save the actor name of the sender in a
						// shell variable. Returns an error if the mail queue is
						// empty.
						cmdMsgGetSender(tokens);
						break;

					case Shell.CMD_MSGISRPC:
						// Outputs "true" if the first message in the mail queue
						// represents an RPC request, and "false" otherwise.
						// Returns an error if the mail queue is empty.
						cmdMsgIsRPC(tokens);
						break;

					case Shell.CMD_MSGGETTAGS:
						// Create a set of (key, value) shell variable pairs to
						// hold the contents of the tag table for the first
						// message in the mail queue. Returns an error if the
						// mail queue is empty.
						cmdMsgGetTags(tokens);
						break;

					case Shell.CMD_MSGDELETE:
						// Delete the first message in the mail queue. Returns
						// an
						// error if the mail queue is empty.
						cmdMsgDelete(tokens);
						break;

					case Shell.CMD_MAKEGLOBAL:
						// Store the value of a shell variable into the global
						// variable table. Returns the name of the new global
						// variable.
						cmdMakeGlobal(tokens);
						break;

					case Shell.CMD_SERVICE:
						// Invoke a local node service. Returns the result of
						// the
						// invocation in a new shell variable.
						cmdService(tokens);
						break;

					default:
						// Don't understand the command so barf
						outWrite.println("SV_ERROR unknown command \"" + cmd
								+ "\" SV_END");
						outWrite.flush();
					}
				}
			}
		} catch (Exception e) {
			// If we get an exception then just close the stream and write
			// out a message to the monitor window if it exists
			try {
				client.close();
			} catch (Exception f) {
				// Since this is just cleanup code, we ignore any exception
				// here.
			}
			// Log.println("<ShellSession.shellMainLoop>: Shell Stream Closed: returned exception "
			// + e);
			// Log.logExceptionTrace(e);
		}
	}

	/**
	 * Receive a new message from our associated <em>ShellActorImpl</em>. At the
	 * moment we just put the message in our local message queue.
	 */
	void newShellMsg(ActorMsgRequest msg) {
		msgQueue.enqueue(msg);
	}

	// /////////////////////////////////////////////////////////
	// /// Implementations of shell commands
	// /////////////////////////////////////////////////////////

	/**
	 * Handle the "instobj" shell command. Instantiates a new object of an
	 * arbitrary class using the given set of arguments. Saves the new object in
	 * a fresh shell variable and outputs the name of the variable to the
	 * client.
	 */
	void cmdInstObj(Queue<CmdToken> tokens) {
		// Arg 1 - Interpreted as the fully qualified class name of the object
		// to create
		// Arg 2 -> Arg N (optional) - Arguments to pass to the constructor
		if (tokens.numElements() < 1) {
			outWrite
					.println("SV_ERROR usage: instobj className [arg1 ... argN] SV_END");
			outWrite.flush();
			return;
		}

		// Get the class name and attempt to convert it
		Class<?> toCreate = null;
		String className = null;
		try {
			className = ((CmdToken) tokens.dequeue()).text;
			toCreate = Class.forName(className);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception while resolving class: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		// Now get and parse all the arguments
		Object[] args = new Object[tokens.numElements()];
		int i = 0;
		String nextArg = null;

		while (tokens.numElements() > 0) {
			nextArg = ((CmdToken) tokens.dequeue()).text;
			try {
				args[i] = parent.parseArgument(nextArg, symbols);
			} catch (Exception e) {
				outWrite.println("SV_ERROR exception parsing argument: " + e
						+ " SV_END");
				outWrite.flush();
				return;
			}
			i++;
		}

		// Once the arguments have been parsed we need to
		// track down the appropriate constructor to invoke to
		// the create the new object.
		Constructor<?> toInvoke = null;

		try {
			toInvoke = parent.findConstructor(toCreate, args);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception finding constructor: " + e
					+ " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}

		// Last, create the new object and save it in a new
		// shell variable. Return the name of the shell
		// variable as the result.
		String newSym = null;

		try {
			newSym = newName();
			symbols.put(newSym, toInvoke.newInstance(args));
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception creating object: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		outWrite.println(newSym + " SV_END");
		outWrite.flush();
	}

	/**
	 * Handle the "invoke" shell command. Invokes an arbitrary method on an
	 * object stored in a shell variable. If the method has no return value then
	 * "null" is printed on the output stream. Otherwise a new shell variable is
	 * created to hold the return value and the name of the new variable is
	 * printed on the output stream.
	 */
	void cmdInvoke(Queue<CmdToken> tokens) {
		// arg 1 -> the name of the shell variable which contains the
		// object to print
		// arg 2 -> the name of the method to invoke on the target object
		// arg 3...arg N -> the arguments to pass to the target method.
		// These may be arguments of the form used in
		// "instobj".
		if (tokens.numElements() < 2) {
			outWrite
					.println("SV_ERROR usage: invoke shellVar method [arg1 ... argN] SV_END");
			outWrite.flush();
			return;
		}

		Object targObj = null;
		String targMeth = null;

		// Attempt to parse out the target object and method
		try {
			targObj = parent.parseArgument(((CmdToken) tokens.dequeue()).text,
					symbols);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception parsing shell var: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		targMeth = ((CmdToken) tokens.dequeue()).text;

		// Now parse out the rest of the arguments and convert them to
		// objects
		Object[] args = new Object[tokens.numElements()];
		int i = 0;
		String nextArg = null;

		while (tokens.numElements() > 0) {
			nextArg = ((CmdToken) tokens.dequeue()).text;
			try {
				args[i] = parent.parseArgument(nextArg, symbols);
			} catch (Exception e) {
				outWrite.println("SV_ERROR exception parsing argument: " + e
						+ " SV_END");
				outWrite.flush();
				return;
			}
			i++;
		}

		// Finally, track down the method to invoke and invoke it
		Method toInvoke = null;
		Object result = null;

		try {
			toInvoke = parent.findMethod(targObj.getClass(), targMeth, args);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception finding method: " + e
					+ " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}

		String newSym = null;

		try {
			result = toInvoke.invoke(targObj, args);
			if (result == null) {
				outWrite.println("null SV_END");
				outWrite.flush();
			} else {
				newSym = newName();
				symbols.put(newSym, result);
				outWrite.println(newSym + " SV_END");
				outWrite.flush();
			}
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception invoking method: " + e
					+ " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}
	}

	/**
	 * Handle the "print" shell command. Equivalent to invoking "toString" on an
	 * object stored in shell variable. Prints the result to the output stream.
	 */
	void cmdPrint(Queue<CmdToken> tokens) {
		// arg 1 -> the name of the shell variable which contains the
		// object to print.
		if (tokens.numElements() != 1) {
			outWrite.println("SV_ERROR usage: print shellVar SV_END");
			outWrite.flush();
			return;
		}

		// Get the shell var name and translate it into an object
		String shellVar = null;
		Object ref = null;
		try {
			shellVar = ((CmdToken) tokens.dequeue()).text;
			ref = parent.parseArgument(shellVar, symbols);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception parsing shell var: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		// Finally, invoke toString and printout the result
		if (ref == null) {
			outWrite.println("null SV_END");
			outWrite.flush();
			return;
		}

		outWrite.println(ref.toString() + " SV_END");
		outWrite.flush();
	}

	/**
	 * Handle the "create" shell command. Creates a new actor and stores the
	 * name of the actor in a new shell variable. The name of this variable is
	 * printed on the output stream.
	 */
	void cmdCreate(Queue<CmdToken> tokens) {
		// arg 1 -> the node on which to create the new actor
		// arg 2 -> the fully qualified class name of the implementation
		// class of the new actor (should be an extension of
		// ActorImpl).
		// arg 3 -> the fully qualified class name of the behavior of the
		// new actor (should be an extension of Actor).
		// arg 4 ... arg N -> Arguments to pass to the constructor of the
		// new actor.
		if (tokens.numElements() < 3) {
			outWrite
					.println("SV_ERROR usage: create node actorImplClass actorClass [arg1 ... argN]  SV_END");
			outWrite.flush();
			return;
		}

		// Build an ActorCreateRequest and submit it to our local manager.
		ActorCreateRequest newActor = new ActorCreateRequest();
		Class<?> actorImplType = null;
		Class<?> actorBehType = null;
		ActorManagerName where = null;

		try {
			where = (ActorManagerName) parent.parseArgument(((CmdToken) tokens
					.dequeue()).text, symbols);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception parsing node argument: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		try {
			String actorImpl = ((CmdToken) tokens.dequeue()).text;
			String actorBeh = ((CmdToken) tokens.dequeue()).text;

			actorImplType = Class.forName(actorImpl);
			actorBehType = Class.forName(actorBeh);
		} catch (Throwable e) {
			if (e instanceof ThreadDeath)
				throw (ThreadDeath) e;
			else {
				outWrite
						.println("SV_ERROR error resolving implementation or behavior type: "
								+ e + " SV_END");
				outWrite.flush();

				// Log.logExceptionTrace(e);
				return;
			}
		}

		Object[] args = new Object[tokens.numElements()];
		int i = 0;
		String nextArg = null;

		while (tokens.numElements() > 0) {
			nextArg = ((CmdToken) tokens.dequeue()).text;
			try {
				args[i] = parent.parseArgument(nextArg, symbols);
			} catch (Exception e) {
				outWrite.println("SV_ERROR exception parsing argument "
						+ nextArg + " : " + e + " SV_END");
				outWrite.flush();
				return;
			}
			i++;
		}

		newActor.behToCreate = actorBehType;
		newActor.implToCreate = actorImplType;
		newActor.constructorArgs = args;
		newActor.site = where;

		// Submit the request and save the return value
		try {
			ActorName theActor = ourActorRef.implCreate(newActor);
			String newSym = newName();

			symbols.put(newSym, theActor);
			outWrite.println(newSym + " SV_END");
			outWrite.flush();
		} catch (Exception e) {
			Throwable f = e;
			if (e instanceof RemoteCodeException)
				f = ((RemoteCodeException) e).detail;
			outWrite.println("SV_ERROR exception creating new actor: " + f
					+ " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}
	}

	/**
	 * Handle the "send" shell command. Sends a message to an actor who's name
	 * is contained in a shell variable. A message consists of a method name and
	 * a set of arguments to pass to the invoked method.
	 */
	void cmdSend(Queue<CmdToken> tokens) {
		// arg 1 -> a shell variable holding the name of the target actor.
		// arg 2 -> a string naming the method to invoke on the target
		// actor.
		// arg 3 ... arg N -> Arguments to pass to the method invoked on
		// the target actor.
		if (tokens.numElements() < 2) {
			outWrite
					.println("SV_ERROR usage: send shellVar meth [arg1 ... argN] SV_END");
			outWrite.flush();
			return;
		}

		// Parse out and resolve the shell variable giving the name of the
		// target actor.
		ActorName target = null;
		String actName = null;
		String method = null;

		try {
			actName = ((CmdToken) tokens.dequeue()).text;
			target = (ActorName) parent.parseArgument(actName, symbols);
		} catch (ClassCastException e) {
			outWrite.println("SV_ERROR target argument " + actName
					+ " must be an ActorName: " + e + " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		} catch (Exception e) {
			outWrite.println("SV_ERROR error parsing symbol " + actName + " : "
					+ e + " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}

		// Parse out the target method for the message
		method = ((CmdToken) tokens.dequeue()).text;

		// Parse out and resolve the arguments to the message
		Object[] args = new Object[tokens.numElements()];
		int i = 0;
		String nextArg = null;

		while (tokens.numElements() > 0) {
			nextArg = ((CmdToken) tokens.dequeue()).text;
			try {
				args[i] = parent.parseArgument(nextArg, symbols);
			} catch (Exception e) {
				outWrite.println("SV_ERROR exception parsing argument "
						+ nextArg + " : " + e + " SV_END");
				outWrite.flush();

				// Log.logExceptionTrace(e);
				return;
			}
			i++;
		}

		// Finally, construct a message and send it using our associated
		// ShellActorImpl
		ActorMsgRequest theMsg = new ActorMsgRequest();
		theMsg.receiver = target;
		theMsg.method = method;
		theMsg.methodArgs = args;
		theMsg.RPCRequest = false; // May want to change this eventually

		try {
			ourActorRef.implSend(theMsg);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception sending message: " + e
					+ " SV_END");
			outWrite.flush();

			// Log.logExceptionTrace(e);
			return;
		}

		// If there are no errors then just send the SV_END response
		outWrite.println("SV_END");
		outWrite.flush();

	}

	/**
	 * Handle the "msgqueuesize" command. Returns an integer giving the current
	 * size of the incoming mail queue.
	 */
	void cmdMqueueSize(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msgqueuesize SV_END");
			outWrite.flush();
			return;
		}

		// Display the queue size and exit
		outWrite.println(msgQueue.numElements() + " SV_END");
		outWrite.flush();

	}

	/**
	 * Handle the "msggetmethod" command. Returns the string naming the method
	 * of the first message in the mail queue. Returns an error if the mail
	 * queue is empty.
	 */
	void cmdMsgGetMethod(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msggetmethod SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// Extract method field and send it to caller
		try {
			ActorMsgRequest first = (ActorMsgRequest) msgQueue.peekFront();

			outWrite.println(first.method + " SV_END");
			outWrite.flush();
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception extracting first message: "
					+ e + " SV_END");
			outWrite.flush();
		}

	}

	/**
	 * Handle the "msggetargs" command. Creates a new shell variable to hold
	 * each argument passed in the first message in the mail queue. Returns an
	 * error if the mail queue is emtpy.
	 */
	void cmdMsgGetArgs(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msggetargs SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// Extract and create a shell variable to hold each argument
		try {
			int i;
			String result = "", newVar;
			ActorMsgRequest first = (ActorMsgRequest) msgQueue.peekFront();

			for (i = 0; i < first.methodArgs.length; i++) {
				newVar = newName();
				symbols.put(newVar, first.methodArgs[i]);
				result = result + newVar + " ";
			}

			outWrite.println(result + "SV_END");
			outWrite.flush();
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception extracting first message: "
					+ e + " SV_END");
			outWrite.flush();
		}

	}

	/**
	 * Handle the "msggetsender" command. Creates a new shell variable which
	 * holds the actor name of the sender of the first message in the mail
	 * queue. Returns an error if the mail queue is emtpy.
	 */
	void cmdMsgGetSender(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msggetsender SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// Extract and create a shell variable to hold the sender
		try {
			String sender;
			ActorMsgRequest first = (ActorMsgRequest) msgQueue.peekFront();

			sender = newName();
			symbols.put(sender, first.sender);
			outWrite.println(sender + " SV_END");
			outWrite.flush();
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception extracting first message: "
					+ e + " SV_END");
			outWrite.flush();
		}

	}

	/**
	 * Handle the "msgisrpc" command. Outputs "true" if the first message in the
	 * mail queue represents an RPC request, and "false" otherwise. Returns an
	 * error if the mail queue is empty.
	 */
	void cmdMsgIsRPC(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msgisrpc SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// Extract and output the status of the RPCRequest field
		try {
			ActorMsgRequest first = (ActorMsgRequest) msgQueue.peekFront();

			outWrite.println(first.RPCRequest + " SV_END");
			outWrite.flush();
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception extracting first message: "
					+ e + " SV_END");
			outWrite.flush();
		}

	}

	/**
	 * Handle the "msggettags" command. Builds a set of (key, value) shell
	 * variable pairs to hold the contents of the tag table. That is, an even
	 * number of new shell variables is always created. Returns an error if the
	 * mail queue is empty.
	 */
	void cmdMsgGetTags(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msggettags SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// If the tags table is non-null, then extract and create the
		// (key, value) pairs for the message.
		try {
			ActorMsgRequest first = (ActorMsgRequest) msgQueue.peekFront();

			if (first.tags != null) {
				String result = "";

				for (Enumeration<?> e = first.tags.keys(); e.hasMoreElements();) {
					String keyName, valName;
					Object nextVal;

					keyName = newName();
					valName = newName();

					nextVal = e.nextElement();
					symbols.put(keyName, nextVal);
					symbols.put(valName, first.tags.get(nextVal));
					result = result + keyName + " " + valName + " ";
				}

				outWrite.println(result + "SV_END");
				outWrite.flush();
			} else {
				outWrite.println("SV_END");
				outWrite.flush();
			}

		} catch (Exception e) {
			outWrite.println("SV_ERROR exception extracting first message: "
					+ e + " SV_END");
			outWrite.flush();
		}

	}

	/**
	 * Handle the "msgdelete" command. Deletes the first message in the mail
	 * queue. Returns an error if the mail queue is empty.
	 */
	void cmdMsgDelete(Queue<CmdToken> tokens) {
		// This method takes no arguments
		if (tokens.numElements() != 0) {
			outWrite.println("SV_ERROR usage: msgdelete SV_END");
			outWrite.flush();
			return;
		}

		// Check that mail queue is non-empty
		if (queueEmpty())
			return;

		// Dequeue the first message and return the "ok" string
		msgQueue.dequeue();
		outWrite.println("SV_END");
		outWrite.flush();
	}

	/**
	 * Handle the "makeglobal" command. Stores the value of the shell variable
	 * argument into the global variable table for the shell service. Returns
	 * the name of the new global variable or an error if the global var does
	 * not exist.
	 */
	void cmdMakeGlobal(Queue<CmdToken> tokens) {
		// Arg 1 -> The argument to make global. The only constraint is
		// that this argument must be parsable by Shell.parseArgument.
		if (tokens.numElements() != 1) {
			outWrite.println("SV_ERROR usage: makeglobal object SV_END");
			outWrite.flush();
			return;
		}

		// Get the argument and parse it
		String theArg = null;
		Object ref = null;
		try {
			theArg = ((CmdToken) tokens.dequeue()).text;
			ref = parent.parseArgument(theArg, symbols);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception parsing argument " + theArg
					+ ": " + e + " SV_END");
			outWrite.flush();
			return;
		}

		// Create a new global variable for this argument, set its value
		// in the global hashtable, and return the name of the new var to
		// the caller.
		String globVar = parent.newGlobalName();
		parent.globalShellVars.put(globVar, ref);
		outWrite.println(globVar + " SV_END");
		outWrite.flush();
	}

	/**
	 * Handle the "service" command. Invokes a service and stores the result in
	 * a new shell variable. Returns the name of the new global variable or
	 * "null" if the invocation had no result.
	 */
	void cmdService(Queue<CmdToken> tokens) {
		// arg 1 -> an object which should resolve to the name of the
		// service to invoke (i.e. an instance of ServiceName).
		// arg 2 -> the name of the method to invoke on the
		// service (i.e. should resolve to a String).
		// arg 2 ... arg N -> the set of objects which should be passed to
		// the invoked service method.

		if (tokens.numElements() < 2) {
			outWrite
					.println("SV_ERROR usage: service service-name meth [arg1 ... argN] SV_END");
			outWrite.flush();
			return;
		}

		ServiceName servName = null;
		String meth = null;
		Object result = null;
		String newSym = null;

		// Attempt to parse out the service name
		try {
			servName = (ServiceName) parent.parseArgument(((CmdToken) tokens
					.dequeue()).text, symbols);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception parsing service name: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		// Parse out the service method to invoke
		meth = ((CmdToken) tokens.dequeue()).text;

		// Now parse out the arguments to pass to the service method
		Object[] args = new Object[tokens.numElements()];
		int i = 0;
		String nextArg = null;

		while (tokens.numElements() > 0) {
			nextArg = ((CmdToken) tokens.dequeue()).text;
			try {
				args[i] = parent.parseArgument(nextArg, symbols);
			} catch (Exception e) {
				outWrite.println("SV_ERROR exception parsing argument "
						+ nextArg + " : " + e + " SV_END");
				outWrite.flush();

				// Log.logExceptionTrace(e);
				return;
			}
			i++;
		}

		// Finally, invoke the service
		try {
			result = ourActorRef.implInvokeService(servName, meth, args);
		} catch (Exception e) {
			outWrite.println("SV_ERROR exception invoking service: " + e
					+ " SV_END");
			outWrite.flush();
			return;
		}

		if (result == null) {
			outWrite.println("null SV_END");
			outWrite.flush();
		} else {
			newSym = newName();
			symbols.put(newSym, result);
			outWrite.println(newSym + " SV_END");
			outWrite.flush();
		}
	}

	/**
	 * Helper method for checking if the mail queue is empty. This method is
	 * used in several places to verify the mail queue is non-empty before
	 * performing a particular action. Returns "true" if the mail queue is
	 * emtpy, and false otherwise.
	 */
	boolean queueEmpty() {
		if (msgQueue.empty()) {
			outWrite.println("SV_ERROR mail queue empty SV_END");
			outWrite.flush();

			return true;
		}

		return false;
	}

	// ////////////////////////////////////////////////////
	// Inner Classes
	// ////////////////////////////////////////////////////

	/**
	 * This is just a container class for holding the contents and type of
	 * parsed tokens.
	 */
	class CmdToken {
		/**
		 * The literal text of a token.
		 */
		String text;

		/**
		 * True if the token corresponds to a quoted string, otherwise this
		 * token represents a "word".
		 */
		boolean isString;

		/**
		 * Constructor used to build instances of this container.
		 */
		public CmdToken(String foo, boolean bar) {
			text = foo;
			isString = bar;
		}
	}

}
