package osl.foundry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import osl.manager.ActorCreateRequest;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.RemoteActorManager;
import osl.scheduler.Scheduler;
import osl.util.ArgumentUtil;
import osl.util.MethodStructure;
import osl.util.MethodStructureVector;

/**
 * This class is used to start a foundry node. A node is started by building the
 * configuration specifed by a "foundry.conf" file. By default, this file is
 * assumed to be in /etc although you may change this by specifying the
 * appropriate command line option. A configuration file optionally specifies a
 * port number followed by a set of modules which define the functional
 * components of the foundry (a foundry.conf file also specifies the classpath
 * to use to start the foundry but that's only used by the shell scripts which
 * start the foundry classes). Each module is created in the order it is
 * specified and then initialized by calling the initialization function which
 * best matches the "arguments" field in the corresponding foundry.conf entry.
 * Once all modules have been started this class waits on the specified port for
 * any further interactions.
 * 
 * @author Mark Astley
 * @version $Revision: 1.13 $ ($Date: 1999/12/17 18:15:42 $)
 */

public class FoundryStart {
	/**
	 * This field holds a reference to the main thread running in foundry start.
	 * This thread is used as the key for creating the System log.
	 */
	// public static Thread sysLog;
	private static String actorImplementorString = "osl.manager.basic.BasicActorImpl";

	public static final boolean useNative = System.getProperty(
			"osl.foundry.useNative", "no").equals("yes");

	/**
	 * This field holds the current version number of the foundry. This is set
	 * at run-time by inspecting the osl.foundry.version property which is set
	 * by the "startfoundry" script when a node is started. If the property
	 * happens to be unset for some reason, then we use the value "unknown".
	 */
	public static final String foundryVersion = System.getProperty(
			"osl.foundry.version", "1.0");

	private static String nextToken(StringTokenizer st) {
		if (st != null && st.hasMoreTokens()) {
			return st.nextToken();
		}
		return null;
	}

	public static void main(String[] argv) {
		try {
			Vector<FoundryModule> mods = new Vector<FoundryModule>();
			boolean open = false;
			Hashtable<String, Object> modAliases = new Hashtable<String, Object>();
			RemoteActorManager localMgr = null;
			boolean secure = false;
			Scheduler localSched = null;
			int port = 1250;
			int maxPri = Thread.MAX_PRIORITY;
			String initActor = null;
			String initMessage = null;
			Object[] initParameters = null;

			// Setup the system log thread
			// sysLog = Thread.currentThread();

			// Bump our priority up to the max. This ensures that we won't
			// accidentally be pre-empted or starved while running the start
			// code.
			Thread.currentThread().setPriority(maxPri);

			// First parse all the command line options

			String configFile = null;
			String sParams = "";
			for (int i = 0; i < argv.length; i++) {
				if (argv[i].equals("-open")) {
					open = true;
				} else if (argv[i].equals("-secure")) {
					secure = true;
				} else if (argv[i].equals("-config")) {
					if (argv.length > i + 1
							&& (!(argv[i + 1].equals("-open")
									|| argv[i + 1].equals("-secure") || argv[i + 1]
									.equals("-config"))))
						configFile = argv[++i];
					else {
						printUsage();
						System.exit(0);
					}
				} else {
					if (initActor == null) {
						initActor = argv[i];
						if (argv.length > i + 1
								&& (!(argv[i + 1].equals("-open")
										|| argv[i + 1].equals("-secure") || argv[i + 1]
										.equals("-config"))))
							initMessage = argv[++i];
						else {
							printUsage();
							System.exit(0);
						}
					} else // if it comes here, it has to be arguments for the
							// message
					{
						sParams += argv[i];
					}
				}
			}
			if (!open && initActor == null && configFile == null) {
				printUsage();
				System.exit(0);
			}

			if (initActor != null)
				initParameters = ArgumentUtil.parseArguments(sParams);

			if (configFile == null) {
				// System.err.println("Please specify the config file: -config <filename>");
				// System.exit(0);

				// Read defaults from FoundryModule
				FoundryModule.buildModules(mods);
			} else {
				File confFile = new File(configFile);
				if (!confFile.exists()) {
					System.err.println("Configuration file '" + configFile
							+ "' does not exist.");
					System.exit(0);
				} else {

					PseudoLineNumberReader confInput = null;
					String nextLine = null;
					FoundryModule nextMod = null;

					// Now scan the config file one line at a time
					try {
						// PRAGMA [debug]
						// System.err.println("Opening input stream to read config file");
						// confInput = new PseudoLineNumberReader(new
						// InputStreamReader(System.in));
						confInput = new PseudoLineNumberReader(
								new InputStreamReader(new FileInputStream(
										configFile)));

						// PRAGMA [debug]
						// System.err.println("Starting to read first line");
						nextLine = confInput.readLine();
						if (nextLine.equals("_FOUNDRY_EOF_"))
							nextLine = null;
						while (nextLine != null) {
							nextLine = nextLine.trim();
							if (nextLine.startsWith("ACTOR_IMPLEMENTOR")) {
								StringTokenizer st = new StringTokenizer(
										nextLine);
								st.nextToken(); // skipping the first token
												// which is
								// ACTOR_IMPLEMENTOR
								String implClass = st.nextToken();
								if (implClass != null) {
									actorImplementorString = implClass;
								} else {
									// The module specification was fouled up so
									// barf
									System.out
											.println("Syntax error in config file at line "
													+ confInput.getLineNumber());
									System.exit(1);
								}
							}

							if (nextLine.startsWith("INIT_ACTOR_MESSAGE")) {
								StringTokenizer st = new StringTokenizer(
										nextLine);
								nextToken(st); // skipping the first token which
												// is
								// ACTOR_IMPLEMENTOR
								initActor = nextToken(st);
								initMessage = nextToken(st);
								if (initActor == null || initMessage == null) {
									// The module specification was fouled up so
									// barf
									System.out
											.println("Syntax error in config file at line "
													+ confInput.getLineNumber());
									System.exit(1);
								}

								initParameters = ArgumentUtil
										.parseArguments(st);
							}

							// PRAGMA [debug]
							// System.err.println("Processing line: " +
							// nextLine);

							// Remove leading spaces
							// while (nextLine.startsWith(" ", 0) &&
							// (nextLine.length() > 0))
							// nextLine = nextLine.substring(1);

							// If this is true then this isn't a blank line
							if (nextLine.length() > 0) {
								if ((!nextLine.startsWith("#", 0))
										&& (!nextLine.startsWith("CLASSPATH"))
										&& (!nextLine.startsWith("JAVAFLAGS"))
										&& (!nextLine
												.startsWith("INIT_ACTOR_MESSAGE"))
										&& (!nextLine
												.startsWith("ACTOR_IMPLEMENTOR"))) {
									// Ok, this should be a module specification
									// so read it
									nextMod = FoundryModule
											.scanConfigLine(nextLine, confInput
													.getLineNumber());

									if (nextMod != null) {
										// If this is a port specification then
										// change our port,
										// otherwise add the module to our list
										if (nextMod.modType == FoundryModule.MOD_PORT)
											try {
												port = (new Integer(
														nextMod.args[0]))
														.intValue();
											} catch (ArrayIndexOutOfBoundsException e) {
												System.out
														.println("At line "
																+ confInput
																		.getLineNumber()
																+ ": expected integer after PORT keyword");
												System.exit(1);
											} catch (NumberFormatException e) {
												System.out
														.println("At line "
																+ confInput
																		.getLineNumber()
																+ ": expected integer after PORT keyword");
												System.exit(1);
											}

										// If this is a status specification
										// then change the
										// default status directory in
										// FoundryStatus.STATUS
										else if (nextMod.modType == FoundryModule.MOD_STATUS) {
											// One minor kludge. Make sure the
											// STATUS directory
											// spec doesn't include a trailing
											// file separator
											// (since the separator is added
											// automatically by
											// FoundryStatus).
											if (nextMod.args[0]
													.endsWith(File.separator)) {
												int where = nextMod.args[0]
														.lastIndexOf(File.separator);
												// PRAGMA [assert]
												// Assert.assert(where != -1,
												// "trailing file separator detected but can't find index");
												FoundryStatus.STATUS = nextMod.args[0]
														.substring(0, where);
											} else
												FoundryStatus.STATUS = nextMod.args[0];

											FoundryStatus.initStatusDirs();

											// Otherwise this must be a regular
											// module so add it
											// to the "to instantiate" list
										} else
											mods.addElement(nextMod);
									} else {
										System.out
												.println("Syntax error in config file at line "
														+ confInput
																.getLineNumber());
										System.exit(1);
									}
								}
							}

							// PRAGMA [debug]
							// System.err.println("Reading next line");
							nextLine = confInput.readLine();
							if ((nextLine != null)
									&& (nextLine.equals("_FOUNDRY_EOF_")))
								nextLine = null;
						}

					} catch (IOException e) {
						if (confInput != null)
							System.out.println("At line: "
									+ confInput.getLineNumber());
						System.out.println("Error reading config file: " + e);
						System.exit(1);
					}
				}
			}

			// PRAGMA [debug]
			// System.err.println("Finished processing config file");
			// If we get here then we're ready to start the foundry.

			// Before creating any modules, initialize the status
			// directories so that modules may use them. Also, start the
			// security manager since we are now entering external code
			// regions.
			// FoundryStatus.initStatusDirs();
			// Log.logThread("System", sysLog);

			if (secure)
				System.setSecurityManager(new FoundrySecurityManager());

			Method modInit = null;
			Object[] buildArgs = null;
			Object modInstance = null;
			Class<?> modClass = null;
			String modInitName = null;
			String modTypeName = null;

			Iterator<FoundryModule> iterator = mods.iterator(); // ;
																// theMods.hasMoreElements();
																// ) {
			while (iterator.hasNext()) {
				FoundryModule nextMOD = iterator.next();

				if (nextMOD.modType == FoundryModule.MOD_PORT) {
					try {
						port = (new Integer(nextMOD.args[0])).intValue();
					} catch (NumberFormatException e) {
						System.out
								.println("Expected integer for value of Port Number");
						System.exit(1);
					}
					continue;
				}

				// If this is a status specification then change the
				// default status directory in FoundryStatus.STATUS
				else if (nextMOD.modType == FoundryModule.MOD_STATUS) {
					// One minor kludge. Make sure the STATUS directory
					// spec doesn't include a trailing file separator
					// (since the separator is added automatically by
					// FoundryStatus).
					/*
					 * if (nextMOD.args[0].endsWith(File.separator)) { int where
					 * = nextMOD.args[0].lastIndexOf(File.separator); // PRAGMA
					 * [assert] Assert.assert(where != -1,
					 * "trailing file separator detected but can't find index");
					 * FoundryStatus.STATUS = nextMOD.args[0].substring(0,
					 * where); } else FoundryStatus.STATUS = nextMOD.args[0];
					 * 
					 * // Otherwise this must be a regular module so add it //
					 * to the "to instantiate" list
					 * FoundryStatus.initStatusDirs();
					 */
					continue;
				}

				switch (nextMOD.modType) {
				case FoundryModule.MOD_SCHEDULER:
					modInitName = "schedulerInitialize";
					modTypeName = "SCHEDULER";
					break;
				case FoundryModule.MOD_TRANSPORT:
					modInitName = "transportInitialize";
					modTypeName = "TRANSPORT";
					break;
				case FoundryModule.MOD_NAMESERVICE:
					modInitName = "nsInitialize";
					modTypeName = "NAMESERVICE";
					break;
				case FoundryModule.MOD_HANDLER:
					modInitName = "handlerInitialize";
					modTypeName = "HANDLER";
					break;
				case FoundryModule.MOD_MANAGER:
					modInitName = "managerInitialize";
					modTypeName = "MANAGER";
					break;
				case FoundryModule.MOD_SERVICE:
					// We do not launch services for closed system
					if (!open)
						continue;
					modInitName = "serviceInitialize";
					modTypeName = "SERVICE";
					break;
				case FoundryModule.MOD_EVENT:
					modInitName = "eventInitialize";
					modTypeName = "EVENT";
					break;
				}

				// Make sure we at least have the classtype and an alias
				if (nextMOD.args.length < 2) {
					System.out.println("Error: Module specification at line "
							+ nextMOD.line
							+ " requires at least a classname and an alias");
					System.exit(1);
				}

				// Now build the rest of the object arguments
				buildArgs = aliasArgs(modAliases, nextMOD.args, 2, nextMOD.line);

				if (modTypeName.equals("SCHEDULER")) {
					Object[] tempArgs = Arrays.copyOf(buildArgs,
							buildArgs.length + 1);
					tempArgs[buildArgs.length] = open;
					buildArgs = tempArgs;
				}

				// Construct the new object and save it in the aliases table.
				try {
					modClass = Class.forName(nextMOD.args[0]);
					modInstance = modClass.newInstance();
					modAliases.put(nextMOD.args[1], modInstance);
				} catch (Exception e) {
					System.out
							.println("Error: Failed to instantiate module at line "
									+ nextMOD.line + " with exception: " + e);
					if (e instanceof InvocationTargetException)
						System.out.println("Encapsulated exception is: "
								+ ((InvocationTargetException) e)
										.getTargetException());
					System.exit(1);
				}

				// Now track down an appropriate initialization method for
				// this method. The method we use depends on the type of
				// module.
				try {
					modInit = findInitMethod(modInstance, modInitName,
							buildArgs);
				} catch (NoSuchMethodException nsm) {
					System.err.println("ERROR: Can't start module "
							+ nextMOD.args[1] + " (" + modTypeName + ")");
					throw nsm;
				}

				if (modInit == null)
					System.out.println("Error: Couldn't find appropriate "
							+ modInitName + " method for module at line "
							+ nextMOD.line);
				try {

					// Initialize the module
					modInit.invoke(modInstance, buildArgs);

					// Generate logfile entry for startup sequence.
					// (voop@cs.auc.dk)
					// Log.println(sysLog, "Module " + nextMOD.args[1] + " (" +
					// modTypeName + ")...initialized");

				} catch (Exception e) {
					System.out
							.println("Error: Failed to initialize module at line "
									+ nextMOD.line + " with exception: " + e);
					if (e instanceof InvocationTargetException)
						System.out.println("Encapsulated exception is: "
								+ ((InvocationTargetException) e)
										.getTargetException());
					System.exit(1);
				}

				// Save a reference to the first manager we find
				if ((nextMOD.modType == FoundryModule.MOD_MANAGER)
						&& (localMgr == null))
					localMgr = (RemoteActorManager) modInstance;

				// Save a reference to the first scheduler we find
				if ((nextMOD.modType == FoundryModule.MOD_SCHEDULER)
						&& (localSched == null))
					localSched = (Scheduler) modInstance;
			}

			// Generate logfile entry for startup sequence.
			// (voop@cs.auc.dk)
			// Log.println(sysLog,"Start code complete...waiting for requests\n");

			// If we have a scheduler, then start a cleanup thread in the
			// log.
			if (localSched != null) {
				// Thread logger = new Thread(new Log(), "logger");
				// localSched.scheduleThread(logger);
				// Log.logThread("System", logger);
			}

			if (initActor != null) {
				ActorCreateRequest mainActorCreateRequest = new ActorCreateRequest(
						null, Class.forName(initActor), Class
								.forName(actorImplementorString), null, null);
				ActorName mainActorName = localMgr.managerCreate(
						mainActorCreateRequest, null);

				ActorMsgRequest mainActorMsgRequest = new ActorMsgRequest(
						mainActorName, mainActorName, initMessage,
						initParameters);

				localMgr.managerDeliver(mainActorMsgRequest);
			}

			if (open) {
				// Open a TCP port to wait for requests
				ServerSocket reqSock = null;
				Socket nextReq = null;

				try {
					reqSock = new ServerSocket(port);
				} catch (IOException e) {
					System.out.println("Error opening request socket on port "
							+ port + ": " + e);
					System.exit(1);
				}

				while (true) {
					nextReq = reqSock.accept();
					(new Thread(new FoundryHandleReq(nextReq, modAliases,
							localMgr))).start();
				}
			}

		} catch (Exception e) {
			System.out.println("Fatal error while starting foundry: " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * This static function returns an array of object instances by scanning a
	 * String argument list and looking up the appropriate objects in the given
	 * table.
	 */
	static Object[] aliasArgs(Hashtable<String, Object> table, String[] args,
			int start, int line) {
		if (start >= args.length)
			return new Object[0];

		// Create something to hold all the args we find
		Vector<Object> newArgs = new Vector<Object>();

		for (int i = start; i < args.length; i++) {
			// What we do next depends on the type of the arg
			if (args[i].charAt(0) == '@') {
				// Ok, this is an alias de-reference. So look it up and add
				// it to the vector
				Object next = table.get(args[i].substring(1));
				if (next == null) {
					System.out.println("Can't find reference for alias "
							+ args[i] + " in line " + line + " of config file");
					System.exit(1);
				} else
					newArgs.addElement(next);
			} else {
				// Default case, just add the argument as a string
				newArgs.addElement(args[i]);
			}
		}

		// Finally, copy out the args and return them

		return newArgs.toArray();
	}

	public static void printUsage() {
		System.out
				.println("Usage: java [JAVA_OPTIONS] osl.foundry.FoundryStart [-secure (default = insecure) | "
						+ "-open (default = closed) | -config <config file>] actor message [args...]");
	}

	/**
	 * This static method returns the appropriate initialization method for the
	 * given object and the given set of arguments.
	 * 
	 * @exception java.lang.NoSuchMethodException
	 *                Thrown if the given method can not be found.
	 */
	public static Method findInitMethod(Object theObj, String methName,
			Object[] args) throws NoSuchMethodException {
		MethodStructureVector candidates = new MethodStructureVector();
		Method[] theMeths = null;

		// Look up and store our public methods in sorted order
		theMeths = theObj.getClass().getMethods();

		for (int i = 0; i < theMeths.length; i++)
			if (theMeths[i].getName().equals(methName))
				candidates.insertElement(new MethodStructure(theMeths[i],
						theMeths[i].getParameterTypes()));

		MethodStructure[] refArray = new MethodStructure[candidates.size()];
		candidates.copyInto(refArray);

		// Now search the meth array for the one we need
		Method toInvoke = null;
		boolean found = false;

		// Grab the method we are supposed to invoke, barf if we can't
		// find any such method
		for (int i = 0; (i < refArray.length) && (!found); i++) {
			if (args == null) {
				if (refArray[i].argTypes.length == 0) {
					found = true;
					toInvoke = refArray[i].meth;
					break;
				} else
					continue;
			} else if (refArray[i].argTypes == null)
				continue;
			else if (args.length != refArray[i].argTypes.length)
				continue;

			found = true;
			toInvoke = refArray[i].meth;
			for (int j = 0; j < refArray[i].argTypes.length; j++)
				if ((args[j] != null)
						&& (!refArray[i].argTypes[j].isInstance(args[j]))) {
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

}

/**
 * This class used to receive interactions for requests.
 */
class FoundryHandleReq implements Runnable {
	Socket connect = null;

	// private Hashtable<String, Object> mods = null;
	// RemoteActorManager localMgr = null;

	public FoundryHandleReq(Socket line, Hashtable<String, Object> modules,
			RemoteActorManager L) {
		connect = line;
		// mods = modules;
		// localMgr = L;
	}

	public void run() {
		try {
			InputStream inStream = connect.getInputStream();
			OutputStream outStream = connect.getOutputStream();
			LineNumberReader inReader = new LineNumberReader(
					new InputStreamReader(inStream));
			String nextReq = null;
			BufferedWriter outWrite = new BufferedWriter(
					new OutputStreamWriter(outStream));
			StringTokenizer parse = null;
			String prompt = "foundry [" + connect.getLocalPort() + "]>";

			while (true) {
				// Print out the initial prompt
				outWrite.write(prompt, 0, prompt.length());
				outWrite.flush();

				// Read a line of input
				nextReq = inReader.readLine();
				parse = new StringTokenizer(nextReq);

				// And figure out what to do with it. We ignore the line if
				// it is blank.
				if (parse.hasMoreTokens()) {
					String nextCmd = parse.nextToken();

					if ((nextCmd.equals("close")) && (!parse.hasMoreTokens())) {
						// Close the control connection
						connect.close();
						return;
					} else {
						// Barf out an error
						String errString = "Error: unknown request -> "
								+ nextReq + "\n";
						outWrite.write(errString, 0, errString.length());
						outWrite.flush();
					}
				}

			}
		} catch (Exception e) {
			// If we get an exception then just close the stream and write
			// out a message to the monitor window if it exists
		}
	}
}

/**
 * This is an alternative implementation of LineNumberReader. For some reason,
 * the Java implementation is broken on Windows NT. We don't do anything here
 * besides readLines and keep track of line numbers.
 */
class PseudoLineNumberReader {

	/**
	 * The reader from which we should read characters.
	 */
	Reader in;

	/**
	 * The current line number.
	 */
	int lineNo;

	/**
	 * Temporary holder for next available character.
	 */
	int nextChar = -1;

	/**
	 * Boolean to hold status of nextChar.
	 */
	boolean initialized = false;

	/**
	 * Constructor takes the same arguments as LineNumberReader
	 */
	public PseudoLineNumberReader(Reader in) {
		this.in = in;
		this.lineNo = -1;
	}

	/**
	 * Read a line from the reader. Returns null if eof reached.
	 */
	public String readLine() throws IOException {
		String result = "";

		if ((!initialized) || (nextChar != -1))
			lineNo++;

		if (!initialized)
			nextChar = in.read();

		while (nextChar != -1) {
			switch (nextChar) {
			case '\n':
				initialized = false;
				return result;

			case '\r':
				nextChar = in.read();
				initialized = (nextChar == '\n');
				return result;

			default:
				result += (char) nextChar;
			}

			nextChar = in.read();
		}

		initialized = true;
		if (result == "")
			return null;
		else
			return result;

	}

	/**
	 * Return the current line number.
	 */
	public int getLineNumber() {
		return lineNo;
	}
}
