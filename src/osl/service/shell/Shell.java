package osl.service.shell;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

import osl.foundry.FoundrySecurityManager;
import osl.manager.RemoteActorManager;
import osl.scheduler.Scheduler;
import osl.service.Service;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.ConstructorStructure;
import osl.util.MethodStructure;
import osl.util.MethodStructureVector;

/**
 * This service implements a simple text-based protocol which may be used (with
 * a suitable front-end) as a shell for interacting with the local foundry. The
 * service listens on a port specified in the initialization function, and
 * treats each connection as a new shell session. When/if the connection is
 * closed, all state associated with the session is removed (and possibly GC'd).
 * However, the actors created by the shell remain in the foundry (but they may
 * be GC'd if we ever get it implemented).
 * <p>
 * 
 * This is only a brief description of the shell protocol. Eventually this will
 * be elaborated on in the documentation:
 * <p>
 * 
 * The shell is accessed by opening a TCP stream to the port used to initialize
 * the service. The shell acts as a server and waits for a request before
 * sending any sort of reply down the pipe. Certain requests expect an argument
 * list. For the most part, the syntax of Java is used to parse arguments. In
 * particular, argument syntax has the following interpretation:
 * 
 * <table border=1>
 * 
 * <tr>
 * <td><b>SV_x...x</b></td>
 * <td>A special form referring to an object instantiated within the shell. Each
 * object created by the shell (in response to a request) is returned as an
 * identifier of this form. Essentially, this identifier is an alias for the
 * actual Java reference to the object (i.e. a pointer). If this argument does
 * not correspond to a legal reference (e.g. the argument string is corrupted)
 * then an "IllegalArgumentException" message is returned as part of the
 * response string for the request.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>SVG_x...x</b></td>
 * <td>Same as <b>SV_x...x</b> except that the shell variable is global and
 * refers to a value in the global symbol table. If this argument does not
 * correspond to a legal reference (e.g. the argument string is corrupted) then
 * an "IllegalArgumentException" message is returned as part of the response
 * string for the request.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>true</b></td>
 * <td>Interpreted as the boolean primitive "true" as defined in Java.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>false</b></td>
 * <td>Interpreted as the boolean primitive "false" as defined in Java.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>C:<em>char</em></b></td>
 * <td>Interpreted as the character primitive <em>char</em> as defined in Java.
 * <em>char</em> must correspond to a single character or a syntax error is
 * returned.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>D:number</b></td>
 * <td>Interpreted as the double primitive <em>number</em> as defined in Java.
 * Returns an "IllegalArgumentException" if <em>number</em> does not correspond
 * to a legal Java double.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>F:number</b></td>
 * <td>Interpreted as the float primitive <em>number</em> as defined in Java.
 * Returns an "IllegalArgumentException" if <em>number</em> does not correspond
 * to a legal Java float.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>I:number</b></td>
 * <td>Interpreted as the integer primitive <em>number</em> as defined in Java.
 * Returns an "IllegalArgumentException" if <em>number</em> does not correspond
 * to a legal Java integer.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>L:number</b></td>
 * <td>Interpreted as the long primitive <em>number</em> as defined in Java.
 * Returns an "IllegalArgumentException" if <em>number</em> does not correspond
 * to a legal Java long.</td>
 * </tr>
 * 
 * <tr>
 * <td><b>"<em>string</em>"</b></td>
 * <td>Interpreted as the String <em>string</em> as defined in Java. The usual
 * Java escape rules apply.</td>
 * </tr>
 * </table>
 * <p>
 * 
 * A complete request is specified using the syntax described below and
 * terminated by the symbol SV_END. Currently, the shell responds to the
 * following requests:
 * <p>
 * 
 * <table border=1>
 * <tr>
 * <td align=left><b>instobj <em>className</em> [ <em>arg1</em> ...
 * <em>argn</em> ]</td>
 * <td>Requests the instantiation of a new java object in the shell environment.
 * <em>className</em> refers to the fully qualified class name of the java
 * object. An optional list of arguments may be provided which are passed to the
 * constructor of the object. A single value of the form "SV_x...x" is returned
 * from the request and should be treated as a reference to the new object. If
 * an exception is encountered while creating the object then an exception
 * message is returned instead.</td>
 * </tr>
 * </table>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/12/17 18:15:43 $)
 * @see osl.service.Service
 */

public class Shell extends Service implements Runnable {
	static final boolean DEBUG = true;

	/**
	 * The name of this service. This field is static and final so that it may
	 * be referenced by all user-written actors which need access to this
	 * service. The name is represented as an anonymous class (cool dirty trick,
	 * yes?)
	 */
	public static final ServiceName name = new ServiceName() {
		/**
		 * This redefinition of toString lets us perform the equality check. Not
		 * real efficient but works.
		 */
		public String toString() {
			return "SHELL_SERVICE_NAME";
		}

		/**
		 * The equals function. Always returns true if compared with another
		 * name of
		 */
		public boolean equals(Object other) {
			return (other.toString().equals("SHELL_SERVICE_NAME"));
		}

		/**
		 * The hashcode function. Always returns the same (somewhat arbitrary)
		 * value.
		 */
		public int hashCode() {
			return 8675309;
		}
	};

	/**
	 * This static String array contains the name of all valid command tokens.
	 * The first string in a request must satisfy a string in this lis. The
	 * string is converted to all lowercase before any comparisons are made.
	 */
	static String[] cmdTokens = { "close", "instobj", "invoke", "create",
			"send", "print", "msgqueuesize", "msggetmethod", "msggetargs",
			"msggetsender", "msgisrpc", "msggettags", "msgdelete",
			"makeglobal", "service" };

	/**
	 * These are the int constants corresponding to the strings in
	 * <em>cmdTokens</em>.
	 */
	static final int UNKNOWN_CMD = -1;
	static final int CMD_CLOSE = 0;
	static final int CMD_INSTOBJ = 1;
	static final int CMD_INVOKE = 2;
	static final int CMD_CREATE = 3;
	static final int CMD_SEND = 4;
	static final int CMD_PRINT = 5;
	static final int CMD_MSGQUEUESIZE = 6;
	static final int CMD_MSGGETMETHOD = 7;
	static final int CMD_MSGGETARGS = 8;
	static final int CMD_MSGGETSENDER = 9;
	static final int CMD_MSGISRPC = 10;
	static final int CMD_MSGGETTAGS = 11;
	static final int CMD_MSGDELETE = 12;
	static final int CMD_MAKEGLOBAL = 13;
	static final int CMD_SERVICE = 14;

	/**
	 * The port that we should listen to shell connections on. This is specified
	 * by the initialization method for this service.
	 */
	int port;

	/**
	 * A reference to the ID of the next session. We increment this every time a
	 * new shell session is created. We'll worry about this value wrapping over
	 * later. Note that we use the Integer form of this value as an index into
	 * the "sessions" hashtable.
	 */
	int nextID = 0;

	/**
	 * A reference to our local actor manager.
	 */
	RemoteActorManager ourMgr;

	/**
	 * A reference to the scheduler we should use to schedule our threads.
	 */
	Scheduler ourScheduler;

	/**
	 * A hashtable which keeps track of all our shell sessions.
	 */
	Hashtable<Integer, ShellSession> sessions;

	/**
	 * The <em>Socket</em> we should listen to new connection requests on.
	 */
	ServerSocket requests;

	/**
	 * A hashtable which hashes <em>Class</em> objects to an <em>Object[]</em>
	 * which contains all the constructors for the class. We cache these to
	 * speed up subsequent constructor look ups.
	 */
	static Hashtable<Class<?>, Object[]> classConstructors = new Hashtable<Class<?>, Object[]>();

	/**
	 * A hashtable which hashes <em>Class</em> objects to an <em>Object[]</em>
	 * which contains all the public methods for the class. We cache these to
	 * speed up subsequent method looko ups.
	 */
	static Hashtable<Class<?>, Hashtable<String, Object>> classMethods = new Hashtable<Class<?>, Hashtable<String, Object>>();

	/**
	 * This hashtable maintains the global set of shell variables. These
	 * variables may be accessed by ANY shell session created by this service.
	 */
	Hashtable<Object, Object> globalShellVars = new Hashtable<Object, Object>();

	/**
	 * The integer portion of the ID of the next global shell var.
	 */
	int nextGlobalNameID = 0;

	/**
	 * The default constructor.
	 */
	public Shell() {
		sessions = new Hashtable<Integer, ShellSession>();
		serviceRegisterMethod("shellProxyInit");
	}

	/**
	 * Initialize this service instance. Note that this version of initialize
	 * shouldn't be used to create a shell service. Every shell service requires
	 * that a port be specified. This is done by calling the second form of
	 * initialize.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 * @param <b>M</b> A remote reference to the local actor manager which
	 *        created us.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error initializing this service.
	 */
	public void serviceInitialize(Scheduler S, RemoteActorManager M)
			throws ServiceException {
		// This version of the initialize function is required by the
		// Service interface but should never be called to start this
		// service. If it is then barf an exception.
		throw new ServiceException(
				"ERROR: This version of initialize should never be called!");
	}

	/**
	 * The REAL initializer for this service. This method is the required
	 * initializer in order for the Shell service to work correctly.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 * @param <b>M</b> A remote reference to the local actor manager which
	 *        created us.
	 * @param <b>P</b> A <em>String</em> representation of the port we listen to
	 *        for new shell requests.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error initializing this service.
	 *                Possible errors are: problems parsing the port string,
	 *                errors opening the TCP socket, etc.
	 */
	public void serviceInitialize(Scheduler S, RemoteActorManager M, String P)
			throws ServiceException {
		try {

			// Save some args
			ourMgr = M;
			ourScheduler = S;

			// Register ourselves with the local manager
			M.managerRegisterService(name, this);

			// Try to parse the port argument first. If this doesn't work
			// then this service isn't going to be started
			port = (new Integer(P)).intValue();

			// Good, now attempt to create a new socket so that we can
			// listen for requests
			requests = new ServerSocket(port);

			// Finally, create a thread for ourselves and schedule it to run.
			S.scheduleThread(new Thread(this, "shellService"));

			// Done
		} catch (NumberFormatException e) {
			throw new ServiceException("Error reading port for Shell service",
					e);
		} catch (IOException e) {
			throw new ServiceException(
					"Error opening socket for Shell service", e);
		} catch (Exception e) {
			throw new ServiceException(
					"Error encountered while starting Shell service", e);
		}
	}

	/**
	 * Return the name of this service.
	 * 
	 * @return A <em>ServiceName</em> structure representing the name of this
	 *         service.
	 */
	public ServiceName serviceName() {
		return name;
	}

	/**
	 * This method should only be called by a newly created
	 * <em>ShellActorImpl</em>. The argument should be an Integer giving the ID
	 * of the creating session. The return value is a direct reference to the
	 * <em>ShellSession</em> associated with the calling actor.
	 * 
	 * @param <b>args</b> The argument to passed to the service when it is
	 *        invoked.
	 * @return An <b>Object</b> indicating the result of the service invocation.
	 * @exception osl.service.ServiceException
	 *                Thrown if there is an error invoking this service.
	 */
	public Object shellProxyInit(Integer sessionID) throws ServiceException {
		try {
			// Lookup the session and return it to the caller
			ShellSession session = (ShellSession) sessions.get(sessionID);

			// Log.println("<Shell.shellProxyInit>: returning session pointer to caller...");

			return session;

		} catch (ClassCastException e) {
			throw new ServiceException(
					"Error: invocation arguments have illegal format: " + e);
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error: no shell session found with given ID: " + e);
		}
	}

	/**
	 * The main processing loop for this service. All we do here is spin waiting
	 * for connection requests. Each time we receive one we spawn off a
	 * ShellSession instance to handle it.
	 */
	public void run() {
		Socket nextReq = null;

		try {
			// Log.logThread("ShellService", Thread.currentThread());

			// Add our ShellSession spawned class as one of the privileged
			// classes (only if the security manager is running and is an
			// instance of FoundrySecurityManager).
			SecurityManager mgr = System.getSecurityManager();
			if ((mgr != null) && (mgr instanceof FoundrySecurityManager)) {
				((FoundrySecurityManager) mgr).addPrivilegedClass(Class
						.forName("osl.service.shell.ShellSession"));
				((FoundrySecurityManager) mgr).addPrivilegedClass(Class
						.forName("osl.service.shell.EstablishStreamConnect"));
			}

			// Just spin waiting for new connection requests. Each time we
			// get one, spawn it off into a ShellSession instance.
			while (true) {
				Integer ref = null;
				ShellSession handler = null;

				nextReq = requests.accept();
				ref = new Integer(nextID++);
				handler = new ShellSession(this, nextReq, ref.intValue());
				sessions.put(ref, handler);

				// Log.println("Starting new shell session...");
				// Log.println("Calling IP  : " + nextReq.getInetAddress());
				// Log.println("Calling Port: " + nextReq.getPort());

				Thread sessionThread = new Thread(handler, "sessionThread");
				ourScheduler.scheduleThread(sessionThread);
			}
		} catch (Exception e) {
			// If we get an exception then just close the stream and write
			// out a message to the monitor window if it exists
			// Log.println("Fatal error in shell service: " + e);
			// Log.logExceptionTrace(e);
			// Log.println("Removing this service");
			try {
				ourMgr.managerRemoveService(name);
			} catch (ServiceNotFoundException f) {
				// Log.println("Error removing service: " + f);
			}
		}
	}

	/**
	 * Return the constant corresponding to the command in the argument string,
	 * or -1 if this string does not correspond to a legal argument. The string
	 * is converted to lowercase before any comparisons are done.
	 * 
	 * @param <b>cmd<b> A <em>String</em> representing the command to be parsed.
	 * @return An <em>int</em> constant corresponding to the parsed command.
	 *         This is for use in a switch statement. Returns -1 (i.e.
	 *         UNKNOWN_CMD) if the argument does not correspond to a legal
	 *         command.
	 */
	int parseCommand(String cmd) {
		for (int i = 0; i < cmdTokens.length; i++)
			if (cmdTokens[i].equalsIgnoreCase(cmd))
				return i;

		return -1;
	}

	/**
	 * Parses a string which represents an argument of some sort. The argument
	 * should match the syntax defined for arguments at the head of this file.
	 * Regardless of what the actual argument type is, an Object is returned as
	 * the value of the object. Where primitive types are concerned, an
	 * appropriate Object wrapper is returned to encapsulate the primitive
	 * instance.
	 */
	Object parseArgument(String arg, Hashtable<Object, Object> syms) {
		int startIndex, endIndex, arrayIndex;
		Object deref;
		String base, className, fieldName;
		Class<?> classObj;
		Field fieldObj;

		if ((arg.length() > 3) && (arg.substring(0, 3).equals("SV_"))) {
			// Is this a regular substitution or an array substitution?
			if (arg.indexOf('[') != -1) {
				// If we find a "[" in the string then we assume its an array
				// variable. The integer index is whatever happens to be
				// between [ and ].
				startIndex = arg.indexOf('[');
				endIndex = arg.indexOf(']');
				if (endIndex == -1)
					throw new IllegalArgumentException("shell variable " + arg
							+ " not found");

				arrayIndex = (new Integer(arg.substring(startIndex + 1, arg
						.length() - 1))).intValue();
				base = arg.substring(0, startIndex);
				if (!syms.containsKey(base))
					throw new IllegalArgumentException("shell variable " + base
							+ " not found");

				deref = syms.get(base);
				return Array.get(deref, arrayIndex);

			} else if (syms.containsKey(arg))
				// Assume not an array and return the value if it exists.
				return syms.get(arg);
			else
				// Otherwise this is a malformed shell variable name
				throw new IllegalArgumentException("shell variable " + arg
						+ " not found");
		} else if ((arg.length() > 4) && (arg.substring(0, 4).equals("SVG_"))) {
			// Is this a regular substitution or an array substitution?
			if (arg.indexOf('[') != -1) {
				// If we find a "[" in the string then we assume its an array
				// variable. The integer index is whatever happens to be
				// between [ and ].
				startIndex = arg.indexOf('[');
				endIndex = arg.indexOf(']');
				if (endIndex == -1)
					throw new IllegalArgumentException("global shell variable "
							+ arg + " not found");

				arrayIndex = (new Integer(arg.substring(startIndex + 1,
						endIndex))).intValue();
				base = arg.substring(0, startIndex);
				if (!globalShellVars.containsKey(base))
					throw new IllegalArgumentException("global shell variable "
							+ base + " not found");

				deref = globalShellVars.get(base);
				return Array.get(deref, arrayIndex);

			} else if (globalShellVars.containsKey(arg))
				// Assume not an array and return the value if it exists.
				return globalShellVars.get(arg);
			else
				// Otherwise this is a malformed shell variable name
				throw new IllegalArgumentException("global shell variable "
						+ arg + " not found");
		} else if (arg.equals("true")) {
			// Is this the boolean primitive "true"?
			return Boolean.TRUE;
		} else if (arg.equals("false")) {
			// Is this the boolean primitive "false"?
			return Boolean.FALSE;
		} else if (arg.equals("null")) {
			// Is this the object null?
			return null;
		} else if ((arg.length() > 2) && (arg.substring(0, 2).equals("C:"))) {
			String temp = arg.substring(2);

			// Translate standard C escapes
			if ((temp.length() == 2) && (temp.charAt(0) == '\\')) {
				switch (temp.charAt(1)) {
				case '0':
					return new Character('\0');

				case 'a':
					return new Character((char) 7);

				case 'b':
					return new Character('\b');

				case 't':
					return new Character('\t');

				case 'n':
					return new Character('\n');

				case 'v':
					return new Character((char) 11);

				case 'f':
					return new Character('\f');

				case 'r':
					return new Character('\r');

				case '\\':
					return new Character('\\');

				case '"':
					return new Character('"');

				default:
					throw new IllegalArgumentException(
							"unknown character escape " + temp);
				}

			} else if (temp.length() == 1) {
				// Not a C escape so just grab the first character and
				// return it.
				return new Character(temp.charAt(1));
			} else
				throw new IllegalArgumentException(
						"illegal character constant " + arg);
		} else if ((arg.length() > 2) && (arg.substring(0, 2).equals("D:"))) {
			return new Double(arg.substring(2));
		} else if ((arg.length() > 2) && (arg.substring(0, 2).equals("F:"))) {
			return new Float(arg.substring(2));
		} else if ((arg.length() > 2) && (arg.substring(0, 2).equals("I:"))) {
			return new Integer(arg.substring(2));
		} else if ((arg.length() > 2) && (arg.substring(0, 2).equals("L:"))) {
			return new Long(arg.substring(2));
		} else if ((arg.length() > 1) && (arg.charAt(0) == '<')) {
			className = null;
			fieldName = null;
			endIndex = arg.indexOf('>');
			if (endIndex == -1)
				throw new IllegalArgumentException(
						"malformed class or static field reference: " + arg);

			if (arg.indexOf(':') != -1) {
				// This is a static field reference
				className = arg.substring(1, arg.indexOf(':'));
				fieldName = arg.substring(arg.indexOf(':') + 1, endIndex);
			} else {
				// This is just a class reference
				className = arg.substring(1, endIndex);
			}

			try {
				classObj = Class.forName(className);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"error resolving class reference " + arg
								+ " with error: " + e);
			}

			if (fieldName == null)
				return classObj;

			try {
				fieldObj = classObj.getField(fieldName);
			} catch (Exception e) {
				throw new IllegalArgumentException("error finding field "
						+ fieldName + " in reference " + arg + " with error: "
						+ e);
			}

			try {
				return fieldObj.get(null);
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"error obtaining static field value " + arg
								+ " with error: " + e);
			}

		}

		return arg;
	}

	// Reference types for adjustments made in findConstructor
	static Class<?> booleanType = Boolean.class.getClass();
	static Class<?> byteType = Byte.class.getClass();
	static Class<?> charType = Character.class.getClass();
	static Class<?> doubleType = Double.class.getClass();
	static Class<?> floatType = Float.class.getClass();
	static Class<?> intType = Integer.class.getClass();
	static Class<?> longType = Long.class.getClass();
	static Class<?> shortType = Short.class.getClass();

	/**
	 * Find a constructor for the given class which most closely matches the
	 * given set of arguments. This method is synchronized to avoid the
	 * (probably rare) race condition where two shell instances simultaneously
	 * call this method and begin constructing new constructor tables in
	 * parallel.
	 * 
	 * @param <b>C</b> The target class.
	 * @param <b>args</b> The <em>Object</em> arguments which should be used to
	 *        find an appropriate constructor.
	 */
	synchronized Constructor<?> findConstructor(Class<?> C, Object[] args)
			throws NoSuchMethodException {
		int i, j;
		boolean found = false;
		Constructor<?> toInvoke = null;
		Constructor<?>[] cons = null;
		MethodStructureVector items = null;
		ConstructorStructure next = null;
		Object[] sorted = null;

		// Check the cache first
		sorted = (Object[]) classConstructors.get(C);

		if (sorted == null) {
			// Grab set of constructors, signal error if no public constructors
			cons = C.getConstructors();
			if (cons.length == 0)
				throw new NoSuchMethodException("Class " + C
						+ " has no public constructors!");

			// First build a method structure vector for all the
			// constructors. This automatically sorts the constructors so
			// that the first match is the most specific constructor.
			items = new MethodStructureVector();

			for (i = 0; i < cons.length; i++) {
				// Before creating a constructor structure, translate primitive
				// classes into real classes. This may not be the most
				// correct solution but it's convenient for our purposes.
				Class<?>[] types = cons[i].getParameterTypes();

				for (j = 0; j < types.length; j++)
					if (types[j].equals(Boolean.TYPE))
						types[j] = booleanType;
					else if (types[j].equals(Byte.TYPE))
						types[j] = byteType;
					else if (types[j].equals(Character.TYPE))
						types[j] = charType;
					else if (types[j].equals(Double.TYPE))
						types[j] = doubleType;
					else if (types[j].equals(Float.TYPE))
						types[j] = floatType;
					else if (types[j].equals(Integer.TYPE))
						types[j] = intType;
					else if (types[j].equals(Long.TYPE))
						types[j] = longType;
					else if (types[j].equals(Short.TYPE))
						types[j] = shortType;

				items.insertElement(new ConstructorStructure(cons[i], types));
			}

			sorted = new Object[items.size()];
			items.copyInto(sorted);

			// Cache the constructor for later use
			classConstructors.put(C, sorted);
		}

		// Now scan the constructor list for the most specific constructor
		for (i = 0; ((i < sorted.length) && (!found)); i++) {
			next = (ConstructorStructure) sorted[i];

			// Compare argument list lengths
			if (args.length != next.argTypes.length)
				continue;

			// Found a possible candidate, compare argument types
			found = true;
			toInvoke = next.meth;
			for (j = 0; j < args.length; j++) {
				if (!next.argTypes[j].isInstance(args[j])) {
					found = false;
					break;
				}
			}
		}

		if (!found) {
			String argList = "(";

			if (args == null)
				argList = argList + ")";
			else {
				for (i = 0; i < args.length - 1; i++)
					argList = argList + args[i].getClass() + ", ";
				argList = argList + args[i].getClass() + ")";
			}

			throw new NoSuchMethodException("No constructor for " + C
					+ " with arg types " + argList);
		}

		// Found the constructor, so return it
		return toInvoke;
	}

	/**
	 * Find the <em>Method</em> reference for a public method defined for a
	 * particular class. This method is synchronized to avoid the (probably
	 * rare) race condition where two shell instances simulatenously invoke this
	 * method and begin constructing new method tables (in parallel) for the
	 * same class.
	 * 
	 * @param <b>C</b> The <em>Class</em> reference where we should look for the
	 *        target method.
	 * @param <b>methName</b> A <em>String</em> giving the method to invoke.
	 * @param <b>args</b> An array of <em>Objects</em> giving the arguments that
	 *        will be passed to the target method. We use the class of these
	 *        arguments to find the correct method to invoke. NOTE: if this
	 *        method requires no arguments then args should be an array of
	 *        length 0, NOT NULL.
	 */
	synchronized Method findMethod(Class<?> C, String methName, Object[] args)
			throws NoSuchMethodException {
		Hashtable<String, Object> ourMethods = null;
		int i, j;

		// See if we already have a method structure array for this class,
		// if not then create one.
		ourMethods = classMethods.get(C);

		if (ourMethods == null) {
			// Don't have any method tables yet so build them.
			Method[] theMeths = null;
			Class<?>[] types = null;
			ourMethods = new Hashtable<String, Object>();
			classMethods.put(C, ourMethods);

			// Look up and store the public methods of target class
			theMeths = C.getMethods();

			for (i = 0; i < theMeths.length; i++) {
				// Before inserting the new method array, translate any
				// primitive
				// class types into their Object variants so that we can make
				// calls on them. This won't always be correct but should be
				// good enough for use in the shell.
				types = theMeths[i].getParameterTypes();

				for (j = 0; j < types.length; j++)
					if (types[j].equals(Boolean.TYPE))
						types[j] = booleanType;
					else if (types[j].equals(Byte.TYPE))
						types[j] = byteType;
					else if (types[j].equals(Character.TYPE))
						types[j] = charType;
					else if (types[j].equals(Double.TYPE))
						types[j] = doubleType;
					else if (types[j].equals(Float.TYPE))
						types[j] = floatType;
					else if (types[j].equals(Integer.TYPE))
						types[j] = intType;
					else if (types[j].equals(Long.TYPE))
						types[j] = longType;
					else if (types[j].equals(Short.TYPE))
						types[j] = shortType;

				if (ourMethods.containsKey(theMeths[i].getName()))
					((MethodStructureVector) ourMethods.get(theMeths[i]
							.getName())).insertElement(new MethodStructure(
							theMeths[i], types));
				else {
					MethodStructureVector newVec = new MethodStructureVector();
					ourMethods.put(theMeths[i].getName(), newVec);
					newVec
							.insertElement(new MethodStructure(theMeths[i],
									types));
				}
			}

			for (Enumeration<String> e = ourMethods.keys(); e.hasMoreElements();) {
				String namu = (String) e.nextElement();
				MethodStructureVector methObjs = (MethodStructureVector) ourMethods
						.get(namu);
				MethodStructure[] refArray = new MethodStructure[methObjs
						.size()];

				methObjs.copyInto(refArray);
				ourMethods.put(namu, refArray);
			}
		}

		// Attempt to find a method named "methName"
		MethodStructure[] potMeths = null;
		Method toInvoke = null;
		boolean found = false;

		// Grab the method we are supposed to invoke, barf if we can't
		// find any such method
		potMeths = (MethodStructure[]) ourMethods.get(methName);

		if (potMeths == null)
			throw new NoSuchMethodException("can't find method named: "
					+ methName);

		for (i = 0; (i < potMeths.length) && (!found); i++) {
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
			for (j = 0; j < potMeths[i].argTypes.length; j++)
				if ((args[j] != null)
						&& (!potMeths[i].argTypes[j].isInstance(args[j]))) {
					found = false;
					break;
				}
		}

		if (!found)
			throw new NoSuchMethodException("No method matching " + methName
					+ " in this object");

		// Now invoke the method and send back the return value
		return toInvoke;
	}

	/**
	 * Generate a new global variable ID.
	 */
	String newGlobalName() {
		return "SVG_" + nextGlobalNameID++;
	}
}
