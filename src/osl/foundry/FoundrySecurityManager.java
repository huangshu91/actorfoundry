package osl.foundry;

import java.io.FileDescriptor;
import java.net.InetAddress;

/**
 * This class implements the security behavior of the main foundry node. The
 * basic policy is that only the direct actions of an <em>Actor</em> thread are
 * limited. We can't just limit <em>Actor</em> threads in general because the
 * same thread is often used while making up-calls through the various
 * libraries.
 * <p>
 * 
 * The security check for a thread works by looking at the class context for the
 * call to a particular security function. The context is scanned from front to
 * back. If an instance of <em>Actor</em> is encountered before any instance of
 * one of the classes listed below, then a security exception is raised.
 * Similarly, if <b>no</b> instance of one of the "privileged" classes is
 * encountered in the context, then an exception is raised. Otherwise, no
 * exception is thrown and the activity is allowed to continue.
 * <p>
 * 
 * A class is "privileged" if it is an instance of one of the following classes:
 * <p>
 * 
 * <ul>
 * <li>osl.foundry.FoundryStart
 * <li>osl.foundry.FoundryStatus
 * <li>osl.foundry.FoundryHandleReq
 * <li>osl.foundry.SecureThread
 * <li>osl.handler.RequestHandler
 * <li>osl.handler.RequestSession
 * <li>osl.manager.ActorImpl
 * <li>osl.manager.ActorManager
 * <li>osl.nameservice.NameService
 * <li>osl.scheduler.Scheduler
 * <li>osl.service.Service
 * <li>osl.transport.TransportInstance
 * <li>osl.transport.TransportLayer
 * <li>osl.util.Log
 * <li>sun.awt.ScreenUpdater
 * </ul>
 * <p>
 * 
 * All other classes are considered "unprivileged".
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/08/12 04:26:51 $)
 */

public final class FoundrySecurityManager extends SecurityManager {
	// Set this to true for debugging output
	static final boolean DEBUG = false;

	/**
	 * This field caches the Class object for <em>Actor</em>.
	 */
	protected static Class<?> actorClass;

	/**
	 * This field caches the Class object for <em>FoundrySecurityManager</em>.
	 */
	protected static Class<?> ourClass;

	/**
	 * This field holds the complete class names of all the "legal" classes.
	 * That is, classes for which we should allow access.
	 */
	protected static String[] legalClasses = { "osl.foundry.FoundryStart",
			"osl.foundry.FoundryStatus", "osl.foundry.FoundryHandleReq",
			"osl.foundry.SecureThread", "osl.handler.RequestHandler",
			"osl.handler.RequestSession", "osl.manager.ActorImpl",
			"osl.manager.ActorManager", "osl.nameservice.NameService",
			"osl.scheduler.Scheduler", "osl.service.Service",
			"osl.transport.TransportInstance", "osl.transport.TransportLayer",
			"osl.util.Log"
	// , "sun.awt.ScreenUpdater"
	};

	protected static Class<?>[] legalTable;

	/**
	 * We use this thread as a dummy value for keying logging messages within
	 * the security manager. All messages are logged to the "Security" log-file.
	 */
	private static final SecureThread logThread = new SecureThread() {
		public void run() {
			while (true)
				try {
					synchronized (this) {
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	};

	// This static block initializes all the class tables given above.
	static {
		try {
			actorClass = Class.forName("osl.manager.Actor");
			ourClass = Class.forName("osl.foundry.FoundrySecurityManager");

			legalTable = new Class[legalClasses.length];
			for (int i = 0; i < legalClasses.length; i++) {
				legalTable[i] = Class.forName(legalClasses[i]);
			}

			// Also add our private thread to the error logging system.
			// logThread.start();
			// Log.logThread("Security", logThread);

		} catch (Exception e) {
			System.err.println("Fatal error while starting security manager: "
					+ e);
			System.exit(-1);
		}
	}

	public FoundrySecurityManager() {
		try {
			// Also add our private thread to the error logging system.
			logThread.start();
			// Log.logThread("Security", logThread);
		} catch (Exception e) {
			System.err.println("Fatal error while starting security manager: "
					+ e);
			System.exit(-1);
		}

	}

	/**
	 * This method returns true if the current thread (according to the class
	 * context) should be allowed access, and false otherwise.
	 */
	protected boolean verifyThread() {
		// Here's how the test works:
		// 1. Start scanning from the front of the context.
		// 2. Return true if:
		// a. We encounter a class which is NOT an instance of
		// FoundrySecurityManager
		// b. The class is an instance of class which is a member of
		// one of the packages given above.
		// c. We encounter this class BEFORE we encounter any
		// instance of Actor.
		// 3. Otherwise return false.
		Class<?>[] curContext = getClassContext();
		int i, j;
		String errorContext;

		// Get out quickly if we know this is a secure thread.
		if (Thread.currentThread() instanceof SecureThread)
			return true;

		if (DEBUG) {
			System.out.println("Checking security stack:");
			for (i = 0; i < curContext.length; i++)
				System.out.println("  " + curContext[i].getName());
		}

		// Scan the current context for a privileged class
		for (i = 0; i < curContext.length; i++)
			// This next check lets us avoid contexts which correspond to
			// the FoundrySecurityManager calling its own methods.
			if (!curContext[i].isInstance(this)) {
				// Do the check for a privileged class first. This is
				// necessary in case certain actor classes are added to the
				// privileged list because these classes will fail the next
				// test even if they are privileged.
				for (j = 0; j < legalTable.length; j++) {
					if (legalTable[j].isAssignableFrom(curContext[i])) {
						if (DEBUG)
							System.out.println("Returning true");
						return true;
					}
				}

				// Not a privileged class, we can skip the rest of the testing
				// if we find an instance of ACtor here
				if (actorClass.isAssignableFrom(curContext[i])) {
					// Ok, we encountered Actor before encountering a legal
					// instance of one of the classes above so log the security
					// failure and return false.
					errorContext = "";
					for (j = 0; j < curContext.length; j++)
						errorContext = errorContext + curContext[j].toString()
								+ "\n";
					// Log.println(logThread,
					// "Security check failed, context follows:\n" +
					// errorContext);
					System.out.println("Security failure, check log");

					if (DEBUG)
						System.out.println("Returning false");
					return false;
				}
			}

		// If we end up here then this method was called from some random
		// class so log the security failure and return false.
		errorContext = "";
		for (j = 0; j < curContext.length; j++)
			errorContext = errorContext + curContext[j].toString() + "\n";
		// Log.println(logThread, "Security check failed, context follows:\n" +
		// errorContext);
		System.out.println("(HERE) Security failure, check log");

		if (DEBUG)
			System.out.println("Returning false");
		return false;

	}

	// ///////////////////////////////////////////////////////////////
	// Check Methods - these are all overridden from SecurityManager
	// ///////////////////////////////////////////////////////////////

	/**
	 * Check if the calling thread is allowed to create a class loader.
	 * 
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to create a
	 *                new class loader.
	 */
	public void checkCreateClassLoader() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the calling thread can modify the thread <b>g</b>.
	 * 
	 * @param <b>g</b> The thread to be checked.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to modify
	 *                the thread.
	 * @see java.lang.System#getSecurityManager()
	 * @see java.lang.Thread#resume()
	 * @see java.lang.Thread#setDaemon(boolean)
	 * @see java.lang.Thread#setName(java.lang.String)
	 * @see java.lang.Thread#setPriority(int)
	 * @see java.lang.Thread#stop()
	 * @see java.lang.Thread#suspend()
	 */
	public void checkAccess(Thread g) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the calling thread can modify the thread group <b>g</b>.
	 * 
	 * @param <b>g</b> The thread group to be checked.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to modify
	 *                the thread group.
	 * @see java.lang.System#getSecurityManager()
	 * @see java.lang.ThreadGroup#destroy()
	 * @see java.lang.ThreadGroup#resume()
	 * @see java.lang.ThreadGroup#setDaemon(boolean)
	 * @see java.lang.ThreadGroup#setMaxPriority(int)
	 * @see java.lang.ThreadGroup#stop()
	 * @see java.lang.ThreadGroup#suspend()
	 */
	public void checkAccess(ThreadGroup g) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to halt the JVM.
	 * 
	 * @param <b>status</b> The exit status.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to halt the
	 *                Java Virtual Machine with the specified status.
	 * @see java.lang.Runtime#exit(int)
	 * @see java.lang.System#getSecurityManager()
	 */
	public void checkExit(int status) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to create a subprocess.
	 * 
	 * @param <b>cmd</b> The system command to execute.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to create a
	 *                subprocess.
	 * @see java.lang.Runtime#exec(java.lang.String)
	 * @see java.lang.Runtime#exec(java.lang.String, java.lang.String[])
	 * @see java.lang.Runtime#exec(java.lang.String[])
	 * @see java.lang.Runtime#exec(java.lang.String[], java.lang.String[])
	 * @see java.lang.System#getSecurityManager()
	 */
	public void checkExec(String cmd) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to load a dynamic library.
	 * 
	 * @param <b>lib</b> The name of the library to load.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to
	 *                dynamically link the library.
	 * @see java.lang.Runtime#load(java.lang.String)
	 * @see java.lang.Runtime#loadLibrary(java.lang.String)
	 * @see java.lang.System#getSecurityManager()
	 */
	public void checkLink(String lib) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is alowed to read from a file.
	 * 
	 * @param <b>fd</b> The file descriptor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified file descriptor.
	 * @see java.io.FileDescriptor
	 */
	public void checkRead(FileDescriptor fd) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to read a file.
	 * 
	 * @param <b>file</b> The file name.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified file.
	 */
	public void checkRead(String file) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to read a file given a security context.
	 * 
	 * @param <b>file</b> The file name.
	 * @param <b>context</b> A security context.
	 * @exception java.lang.SecurityException
	 *                Thrown if the specified security context does not have
	 *                permission to read the specified file.
	 * @see java.lang.SecurityManager#getSecurityContext()
	 */
	public void checkRead(String file, Object context) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to write to a file.
	 * 
	 * @param <b>fd</b> A file descriptor.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified file descriptor.
	 * @see java.io.FileDescriptor
	 */
	public void checkWrite(FileDescriptor fd) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to write to a file.
	 * 
	 * @param <b>file</b> The filename.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified file.
	 */
	public void checkWrite(String file) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to delete a file.
	 * 
	 * @param <b>file</b> The filename.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to delete
	 *                the file.
	 * @see java.io.File#delete()
	 * @see java.lang.System#getSecurityManager()
	 */
	public void checkDelete(String file) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to open a socket connection.
	 * 
	 * @param <b>host</b> The host name port to connect to.
	 * @param <b>port</b> The protocol port to connect to.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to open a
	 *                socket connection to the specified <code>host</code> and
	 *                <code>port</code>.
	 */
	public void checkConnect(String host, int port) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to open a socket connection.
	 * 
	 * @param <b>host</b> The host name port to connect to.
	 * @param <b>port</b> The protocol port to connect to.
	 * @param <b>context</b> A security context.
	 * @exception java.lang.SecurityException
	 *                Thrown if the specified security context does not have
	 *                permission to open a socket connection to the specified
	 *                <code>host</code> and <code>port</code>.
	 * @see java.lang.SecurityManager#getSecurityContext()
	 */
	public void checkConnect(String host, int port, Object context) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to way for a connection on a port.
	 * 
	 * @param <b>port</b> The port number.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to listen on
	 *                the specified port.
	 */
	public void checkListen(int port) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to accept a socket connection.
	 * 
	 * @param <b>host</b> The host name of the socket connection.
	 * @param <b>port</b> The port number of the socket connection.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to accept
	 *                the connection.
	 * @see java.lang.System#getSecurityManager()
	 * @see java.net.ServerSocket#accept()
	 */
	public void checkAccept(String host, int port) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to perform an IP multicast.
	 * 
	 * @param <b>multicast</b> Internet address to be used.
	 * @exception java.lang.SecurityException
	 *                Thrown if a security error has occurred.
	 */
	public void checkMulticast(InetAddress maddr) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to perform an IP multicast.
	 * 
	 * @param <b>multicast</b> Internet address to be used.
	 * @param <b>ttl</b> Value in use, if it is multicast send.
	 * @exception SecurityException
	 *                if a security error has occurred.
	 */
	public void checkMulticast(InetAddress maddr, byte ttl) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to access or modify system properties.
	 * 
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access or
	 *                modify the system properties.
	 * @see java.lang.System#getProperties()
	 * @see java.lang.System#setProperties(java.util.Properties)
	 */
	public void checkPropertiesAccess() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to access or modify a system property.
	 * 
	 * @param <b>key</b> A system property key.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified system property.
	 * @see java.lang.System#getProperty(java.lang.String)
	 */
	public void checkPropertyAccess(String key) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to create a top-level window.
	 * 
	 * @param <b>window</b> The new window to be created.
	 * @return <code>true</code> if the caller is trusted to put up top-level
	 *         windows; <code>false</code> otherwise.
	 * @exception java.lang.SecurityException
	 *                Thrown if creation is disallowed entirely.
	 * @see java.awt.Window
	 */
	public boolean checkTopLevelWindow(Object window) {
		return verifyThread();
	}

	/**
	 * Check if the caller is allowed to initiate a print job request.
	 */
	public void checkPrintJobAccess() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller can access the system clipboard.
	 */
	public void checkSystemClipboardAccess() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller can get access to the AWT event queue.
	 */
	public void checkAwtEventQueueAccess() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if caller is allowed to access a package.
	 * 
	 * @param <b>pkg</b> The package name.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to access
	 *                the specified package.
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	public void checkPackageAccess(String pkg) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to define a class in a package.
	 * 
	 * @param <b>pkg</b> The package name.
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to define
	 *                classes in the specified package.
	 * @see java.lang.ClassLoader#loadClass(java.lang.String, boolean)
	 */
	public void checkPackageDefinition(String pkg) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Check if the caller is allowed to set a socket factory.
	 * 
	 * @exception java.lang.SecurityException
	 *                Thrown if the caller does not have permission to specify a
	 *                socket factory or a stream handler factory.
	 * @see java.net.ServerSocket#setSocketFactory(java.net.SocketImplFactory)
	 * @see java.net.Socket#setSocketImplFactory(java.net.SocketImplFactory)
	 * @see java.net.URL#setURLStreamHandlerFactory(java.net.URLStreamHandlerFactory)
	 */
	public void checkSetFactory() {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Tests if a client is allowed to access members. If access is denied,
	 * throw a SecurityException. Contrary to the default implementation in
	 * <em>java.lang.SecurityManager</em>, we allow the access if the caller is
	 * a privileged class.
	 */
	public void checkMemberAccess(Class<?> clazz, int which) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Don't know what exactly this is used for but according to
	 * <em>java.lang.SecurityManager</em>: "Tests access to certain operations
	 * for a security API action".
	 */
	public void checkSecurityAccess(String action) {
		if (!verifyThread())
			throw new SecurityException();
	}

	/**
	 * Add a new class to the set of privileged classes. Only a currently
	 * privileged class may add another privileged class. A
	 * <em>SecurityException</em> is thrown if a non-privileged class attempts
	 * to call this method.
	 */
	public void addPrivilegedClass(Class<?> toAdd) {
		if (!verifyThread())
			throw new SecurityException();

		// Synchronize while changing legal table to avoid race conditions
		// between two simultaneous calls.
		synchronized (this) {
			Class<?>[] newLegalTable = new Class[legalTable.length + 1];
			System
					.arraycopy(legalTable, 0, newLegalTable, 0,
							legalTable.length);
			newLegalTable[legalTable.length] = toAdd;
			legalTable = newLegalTable;

			// Log.println(logThread, "Added new privileged class: " + toAdd);
		}
	}

}
