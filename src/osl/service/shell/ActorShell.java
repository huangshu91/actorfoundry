package osl.service.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import osl.foundry.FoundryModule;
import osl.scheduler.Scheduler;
import osl.scheduler.basic.BasicScheduler;
import osl.scheduler.none.NoScheduler;

/**
 * This class is a hacked java class which provides similar functionality to the
 * "fshell" program. The main reason for this program is to get us away from
 * Tcl/Tk which is required for fshell. That is, I anticipate this program
 * replacing fshell in the near future so we can claim a pure Java core system.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 2000/01/13 02:40:45 $)
 */

public class ActorShell implements Runnable {

	static final int MONITOR_STDOUT = 1;
	static final int MONITOR_STDERR = 2;
	static final int MONITOR_STDIN = 3;
	static final int MONITOR_USER = 4;

	/**
	 * If this is a thread monitoring a connection, then this field identifiers
	 * the type of port being monitored.
	 */
	int type;

	/**
	 * The port we use to connect to the shell service.
	 */
	int shellPort = -1;

	/**
	 * The hostname where we connect to.
	 */
	String host = "localhost";

	/**
	 * The socket being monitored if this is a thread.
	 */
	Socket monitor = null;

	/**
	 * The input stream for the socket we are monitoring.
	 */
	InputStream inMonitor = null;

	/**
	 * The output stream for the socket we are monitoring.
	 */
	OutputStream outMonitor = null;

	/**
	 * The output stream for stdin for this shell session. The monitor thread
	 * needs to write to this stream when the "type" command is used.
	 */
	static OutputStream stdinStreamOut = null;

	/**
	 * This buffer is used only by the user monitor thread.
	 */
	BufferedReader shellOutput = null;
	BufferedWriter shellInput = null;

	/**
	 * When a stream thread dies, it will wait here for a signal telling it to
	 * reopen its stream and start over. We use this mechanism as a way to
	 * implement the "reconnect" operation.
	 */
	Integer restart = new Integer(0);

	/**
	 * This is a reference to the ActorShell instance handling stdIn.
	 */
	static ActorShell stdIn = null;

	/**
	 * This is a reference to the ActorShell handling stdOut.
	 */
	static ActorShell stdOut = null;

	/**
	 * This is a reference to the ActorShell handling stdErr.
	 */
	static ActorShell stdErr = null;

	/**
	 * The Actor Implementor string specified in config file
	 */
	private static String actorImplementorString = null;

	/**
	 * The main method which starts the shell running. Use -help as a command
	 * line option to see all the switches.
	 */
	public static void main(String[] argv) {
		LineNumberReader confInput = null;
		String nextLine = null;
		FoundryModule nextMod = null;
		String configFile = null;
		int shellPort = -1;
		String host = "localhost";
		Scheduler S = null;

		// First parse all the command line options
		for (int i = 0; i < argv.length; i++)
			if (argv[i].equals("-config")) {
				// Save the next argument as the name of the config file
				// PRAGMA [assert] Assert.assert(i+1 < argv.length,
				// "Assertion failed: missing filename after -config should be caught by ashell startup script");
				configFile = argv[++i];
			} else if (argv[i].equals("-host")) {
				// Save the next argument as the name of the target host
				// PRAGMA [assert] Assert.assert(i+1 < argv.length,
				// "Assertion failed: missing hostname after -host should be caught by ashell startup script");
				host = argv[++i];
			}

		// The config file is supplied via stdin, so scan it in order to
		// find the port for the shell service.
		try {
			confInput = new LineNumberReader(new FileReader(configFile));

			nextLine = confInput.readLine();
			while (nextLine != null) {
				// Remove leading spaces
				// while (nextLine.startsWith(" ", 0) && (nextLine.length() >
				// 0))
				// nextLine = nextLine.substring(1);
				nextLine = nextLine.trim();

				if (nextLine.length() > 0) {
					if (nextLine.startsWith("ACTOR_IMPLEMENTOR")) {
						StringTokenizer st = new StringTokenizer(nextLine);
						st.nextToken(); // skipping the first token which is
										// ACTOR_IMPLEMENTOR
						String implClass = st.nextToken();
						if (implClass != null) {
							actorImplementorString = implClass;
						} else {
							// The module specification was fouled up so barf
							System.out
									.println("Syntax error in config file at line "
											+ confInput.getLineNumber());
							System.exit(1);
						}
					}
					// If this is true then this isn't a blank line
					else if ((!nextLine.startsWith("#", 0))
							&& (!nextLine.startsWith("CLASSPATH"))
							&& (!nextLine.startsWith("INIT_ACTOR_MESSAGE"))
							&& (!nextLine.startsWith("JAVAFLAGS"))) {

						// Ok, this should be a module specification so read it
						nextMod = FoundryModule.scanConfigLine(nextLine,
								confInput.getLineNumber());

						if (nextMod != null) {
							if ((nextMod.modType == FoundryModule.MOD_SERVICE)
									&& (nextMod.args != null)
									&& (nextMod.args.length == 5)
									&& (nextMod.args[0]
											.equals("osl.service.shell.Shell")))
								// If this is the specification for the shell
								// SERVICE,
								// then parse out the port.
								try {
									shellPort = (new Integer(nextMod.args[4]))
											.intValue();
								} catch (NumberFormatException e) {
									System.out
											.println("At line "
													+ confInput.getLineNumber()
													+ ": expected integer value for shell service port");
									System.exit(1);
								}

						} else {
							// The module specification was fouled up so barf
							System.out
									.println("Syntax error in config file at line "
											+ confInput.getLineNumber());
							System.exit(1);
						}
					}
				}

				nextLine = confInput.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: config file " + configFile
					+ " doesn't exist or not readable");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error reading config file " + configFile);
			System.exit(1);
		}

		// If the port was never set, then barf
		if (shellPort == -1) {
			System.out
					.println("Error: SERVICE osl.service.shell.Shell declaration not found in config file");
			System.exit(1);
		}

		// Open a connection to the shell service.
		Socket shellInteract = null;
		Socket stdOut = null;
		Socket stdErr = null;
		Socket stdIn = null;
		InetAddress hostAddr = null;

		// Startup the scheduler specified in the config file.
		if (System.getProperty("os.name").equals("Windows 95"))
			S = new NoScheduler();
		else
			S = new BasicScheduler();

		// IMPORTANT: We ALWAYS call the no args version of the scheduler.
		S.schedulerInitialize();

		try {
			hostAddr = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			System.out.println("Error: can't resolve host name: " + hostAddr);
			System.exit(1);
		}

		try {
			shellInteract = new Socket(hostAddr, shellPort);

			// First line from shell service contains the three ports for
			// the other streams
			ActorShell userThread = new ActorShell();
			userThread.shellPort = shellPort;
			userThread.host = host;
			userThread.monitor = shellInteract;
			userThread.shellOutput = new BufferedReader(new InputStreamReader(
					shellInteract.getInputStream()));
			userThread.shellInput = new BufferedWriter(new OutputStreamWriter(
					shellInteract.getOutputStream()));
			userThread.type = MONITOR_USER;
			String portSpecs = userThread.shellOutput.readLine();

			// Now open ports for stdin, stdout and stderr
			int stdInPort = (new Integer(portSpecs.substring(0, portSpecs
					.indexOf(" ")))).intValue();
			portSpecs = portSpecs.substring(portSpecs.indexOf(" ") + 1,
					portSpecs.length());
			int stdOutPort = (new Integer(portSpecs.substring(0, portSpecs
					.indexOf(" ")))).intValue();
			portSpecs = portSpecs.substring(portSpecs.indexOf(" ") + 1,
					portSpecs.length());
			int stdErrPort = (new Integer(portSpecs.substring(0, portSpecs
					.indexOf(" ")))).intValue();

			stdIn = new Socket(hostAddr, stdInPort);
			stdOut = new Socket(hostAddr, stdOutPort);
			stdErr = new Socket(hostAddr, stdErrPort);

			// Now spawn three extra threads to handle these streams.
			ActorShell handler = new ActorShell();
			handler.type = MONITOR_STDIN;
			handler.monitor = stdIn;
			ActorShell.stdIn = handler;
			S.scheduleThread(new Thread(handler));
			handler = new ActorShell();
			handler.type = MONITOR_STDOUT;
			handler.monitor = stdOut;
			ActorShell.stdOut = handler;
			S.scheduleThread(new Thread(handler));
			handler = new ActorShell();
			handler.type = MONITOR_STDERR;
			handler.monitor = stdErr;
			ActorShell.stdErr = handler;
			S.scheduleThread(new Thread(handler));

			// Finally, start the user monitor thread.
			S.scheduleThread(new Thread(userThread));

		} catch (IOException e) {
			System.out.println("Error connecting to shell service: " + e);
			System.exit(1);
		}
	}

	/**
	 * The run loop for threads monitoring a connection.
	 */
	public void run() {
		char data;
		int val;

		try {
			switch (type) {
			case MONITOR_STDOUT:
				// For now, we just write stdout to wherever the shell happens
				// to be running. Eventually we will route this depending on
				// whether we are running in console mode or window mode.
				while (true) {
					if (inMonitor == null)
						inMonitor = monitor.getInputStream();

					while (true) {
						val = inMonitor.read();
						if (val != -1) {
							data = (char) val;
							System.out.print(data);
						} else
							break;
					}

					inMonitor = null;
					synchronized (restart) {
						try {
							restart.wait();
						} catch (InterruptedException e) {
							// We should never end up here. If we do then crash
							// the system.
							System.out.println("Unexpected exception: " + e);
							e.printStackTrace();
							System.exit(1);
						}
					}
				}

			case MONITOR_STDERR:
				// For now, we just write stderr to wherever the shell happens
				// to be running. Eventually we will route this depending on
				// whether we are running in console mode or window mode.
				while (true) {
					if (inMonitor == null)
						inMonitor = monitor.getInputStream();

					while (true) {
						val = inMonitor.read();
						if (val != -1) {
							data = (char) val;
							System.err.print(data);
						} else
							break;
					}

					inMonitor = null;
					synchronized (restart) {
						try {
							restart.wait();
						} catch (InterruptedException e) {
							// We should never end up here. If we do then crash
							// the system.
							System.out.println("Unexpected exception: " + e);
							e.printStackTrace();
							System.exit(1);
						}
					}
				}

			case MONITOR_STDIN:
				// Only the "type" command makes use of stdIn, and the
				// MONITOR_USER thread does that directly so we don't need to
				// do anything here other than set up an output stream.
				if (stdinStreamOut == null)
					stdinStreamOut = monitor.getOutputStream();
				break;

			case MONITOR_USER:
				// Now we just spin here reading input from the user until we
				// see the exit command.
				String nextCommand;
				String nextResponse;
				ActorShellParser userInput = new ActorShellParser(System.in);
				if (actorImplementorString != null) {
					userInput.createImpl = ActorShell.actorImplementorString;
				}

				while (true) {
					// Output a prompt and ask the parser for input from the
					// user.
					System.out.print("> ");
					try {
						nextCommand = userInput.ShellCommand();

						// Check for special cases before sending the command
						if (nextCommand.equals("NOP")) {
							// Do nothing, just return.

						} else if (nextCommand.startsWith("type!")) {
							// Send string to stdin for this shell session
							String actual = nextCommand.substring(5) + "\n";

							stdinStreamOut.write(actual.getBytes());
							stdinStreamOut.flush();

						} else if (nextCommand.equals("close")) {
							// Ok, just exit. This will automatically close all
							// the
							// streams at the shell service end.
							System.exit(0);

						} else if (nextCommand.equals("reconnect")) {
							// Close the current connection if it exists
							try {
								shellInput.write("close SV_END \n");
								shellInput.flush();
							} catch (Exception e) {
								// We may get an exception if the connection is
								// already closed. In this case, just ignore it.
							}

							// Now open a new socket
							InetAddress hostAddr = InetAddress.getByName(host);
							monitor = new Socket(hostAddr, shellPort);
							shellOutput = new BufferedReader(
									new InputStreamReader(monitor
											.getInputStream()));
							shellInput = new BufferedWriter(
									new OutputStreamWriter(monitor
											.getOutputStream()));
							String portSpecs = shellOutput.readLine();
							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("Reconnected to shell service");

							// Now open ports for stdin, stdout and stderr
							int stdInPort = (new Integer(portSpecs.substring(0,
									portSpecs.indexOf(" ")))).intValue();
							portSpecs = portSpecs.substring(portSpecs
									.indexOf(" ") + 1, portSpecs.length());
							int stdOutPort = (new Integer(portSpecs.substring(
									0, portSpecs.indexOf(" ")))).intValue();
							portSpecs = portSpecs.substring(portSpecs
									.indexOf(" ") + 1, portSpecs.length());
							int stdErrPort = (new Integer(portSpecs.substring(
									0, portSpecs.indexOf(" ")))).intValue();
							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("Obtained new ports for streams");

							// And reset the monitors for the stream handling
							// threads.
							stdIn.monitor = new Socket(hostAddr, stdInPort);
							stdOut.monitor = new Socket(hostAddr, stdOutPort);
							stdErr.monitor = new Socket(hostAddr, stdErrPort);
							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("Opened new sockets for streams");

							// Now tell the stream threads to restart
							// Skip stdIn for now since it doesn't do anything
							// yet
							// Setup stdOut
							while (stdOut.inMonitor != null) {
								Thread.sleep(5);
							}
							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("stdOut is ready...");
							synchronized (stdOut.restart) {
								stdOut.restart.notify();
							}

							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("stdOut initialized");

							// Setup stdErr
							while (stdErr.inMonitor != null) {
								Thread.sleep(5);
							}
							synchronized (stdErr.restart) {
								stdErr.restart.notify();
							}

							// PRAGMA [debug,osl.service.shell.ActorShell]
							// System.out.println("Streams have been restarted");

							// That's it, next time through the loop we should
							// be
							// reading from the new connection.

						} else {
							// Otherwise, nextCommand should be a complete shell
							// service command. So send it and output the
							// response.
							shellInput.write(nextCommand + "\n");
							shellInput.flush();
							nextResponse = shellOutput.readLine();
							System.out.println(nextResponse);
						}
					} catch (ParseException e) {
						// If we get here then the user goofed when entering a
						// command. Print out the exception and loop back
						// around.
						System.out.println(e.getMessage());

						// Have to clear out the available text before
						// attempting
						// to process the next user command. Otherwise, the
						// parser goes into a rather nasty loop.
						while (System.in.available() > 0)
							System.in.read();

						// This is to reset the parser so that we don't get a
						// million error messages after the erroneous parse.
						userInput.ReInit(System.in);
					}
				}

			}
		} catch (Exception e) {
			System.err.println("Fatal error in monitor thread: " + e);
			System.exit(1);
		}
	}
}
