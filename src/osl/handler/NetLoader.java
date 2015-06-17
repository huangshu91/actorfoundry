package osl.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import osl.foundry.FoundryStatus;
import osl.foundry.FoundryStatusDir;

/**
 * This class allows for run-time additions to the classes used in a running
 * foundry node. We implement this functionality in order to allow classes to be
 * loaded over the network. Currently, a very simple approach is used whereby a
 * private class directory is transparently added to the classpath of a running
 * node. Any classes required at run-time are stored in this directory so that
 * they can be discovered by the JVM class loader. In the future, we'll require
 * a more involved approach (probably necessitating a custom class loader) as
 * we'd also like to be able to <b>remove</b> classes at run-time.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/10/05 15:57:10 $)
 */

public class NetLoader {

	/**
	 * This <em>Vector</em> holds all the class path entries currently in use
	 * for this loader. Each entry is an instance of FoundryPathEntry and is
	 * used to search for a desired class.
	 */
	static Vector<NetLoaderPathEntry> classPaths = new Vector<NetLoaderPathEntry>();

	/**
	 * This field saves the file name separator for this system. We need this
	 * when we attempt to track down classes during loading.
	 */
	static char fileSep = System.getProperty("file.separator").charAt(0);

	/**
	 * The foundry status directory where we store classes loaded over the
	 * network.
	 */
	static FoundryStatusDir dynLoc;

	/**
	 * The default constructor.
	 */
	static void initLoader() {
		// Create a foundry status area to store dynamically loaded
		// classes.
		dynLoc = new FoundryStatusDir(FoundryStatus.STATDIR_FOUNDRY,
				"dynclasses");
		FoundryStatus.makeStatusDir(dynLoc);

		boolean inPath = true;
		char sep = System.getProperty("path.separator").charAt(0);
		String path = System.getProperty("java.class.path") + sep
				+ dynLoc.getName();
		String nextPath = "";
		int next = 0;

		// Build foundry path entries for the local class path. Note that
		// we add our private path as well so that we can find dynamically
		// loaded class data at run-time.
		while (next < path.length()) {
			while ((next < path.length()) && inPath)
				if (path.charAt(next) == sep)
					inPath = false;
				else
					nextPath = nextPath + path.charAt(next++);

			// Ignore empty paths
			if (!nextPath.equals(""))
				classPaths.addElement(new NetLoaderPathEntry(nextPath));

			next++;
			nextPath = "";
			inPath = true;
		}

	}

	/**
	 * This method allows a secure client to add a new class to the JVM. We
	 * guarantee security by explicitly calling the local security manager
	 * before doing anything here. Note that the new class is actually just
	 * copied to our private class area. We let the JVM track down the class
	 * when it needs it.
	 * 
	 * @exception osl.foundry.ClassExistsException
	 *                Thrown if a class named <em>className</em> has already
	 *                been loaded by the JVM.
	 */
	static void addLocalClass(String className, byte[] classData)
			throws ClassExistsException, IOException {

		// Check if this thread is allowed to add classes at runtime.
		// Note that we use the "checkCreateClassLoader" option to do
		// this.
		SecurityManager check = System.getSecurityManager();
		if (check != null)
			check.checkCreateClassLoader();

		// See if we already have a class with the specified name
		try {
			if (Class.forName(className) != null) {
				throw new ClassExistsException(className);
			}
		} catch (ClassNotFoundException e) {
			// We SHOULD end up here if the class has not been loaded yet.
			// In this case, we just ignore the exception.
		}

		// See if a class with the given name has already been added to
		// our local class area.
		String fileName = FoundryStatus.getDirTop() + fileSep
				+ dynLoc.getName() + fileSep + className.replace('.', fileSep)
				+ ".class";
		File newFile = new File(fileName);

		// PRAGMA [debug,osl.handler.NetLoader]
		// Log.println("<NetLoader.addLocalClass>: Checking for class: " +
		// fileName);
		if (newFile.exists()) {
			// PRAGMA [debug,osl.handler.NetLoader]
			// Log.println("<NetLoader.addLocalClass>: Class exists, barfing: "
			// + newFile.exists());
			throw new ClassExistsException(className);
		}

		// Ok, file doesn't exist so create the appropriate directories and
		// store the file.
		StringTokenizer splitter = new StringTokenizer(className, ".");
		String nextD = null;
		String curD = FoundryStatus.getDirTop() + fileSep + dynLoc.getName();

		while (splitter.countTokens() > 1) {
			nextD = splitter.nextToken();
			curD += fileSep + nextD;
		}

		newFile = new File(curD);
		if (!newFile.exists())
			newFile.mkdirs();

		// Now create the class file and store the classData in it.
		FileOutputStream writeIt = new FileOutputStream(curD + fileSep
				+ splitter.nextToken() + ".class");
		writeIt.write(classData);
		writeIt.close();

		// PRAGMA [logDynamicClasses]
		// Log.println("<FoundryLoader.addLocalClass>: Added new local class: "
		// + className);
	}

	/**
	 * This method allows a local client to get the raw class data (i.e. .class
	 * file) for a particular class. This is useful if a class needs to be
	 * shipped outside the current JVM.
	 * 
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if the class <em>className</em> cannot be found by
	 *                this loader.
	 */
	static byte[] getLocalClass(String className) throws ClassNotFoundException {

		Enumeration<NetLoaderPathEntry> i = null;
		NetLoaderPathEntry path = null;
		byte[] classData = null;
		String target = null;

		// Make sure the caller is allowed to do this stuff.
		SecurityManager check = System.getSecurityManager();
		if (check != null)
			check.checkCreateClassLoader();

		// Attempt to find the class data the same way the class loader
		// does. Namely, search the class path. Note that this should
		// work even for the standard Java classes, although why these
		// classes would need to be shipped to another JVM is beyond me.
		target = className.replace('.', fileSep) + ".class";
		for (i = classPaths.elements(); i.hasMoreElements()
				&& (classData == null);) {
			path = i.nextElement();
			classData = path.findClass(target);
		}

		// If we get here and classData != null, then we found the class
		// so return it. Otherwise throw a ClassNotFoundException.
		if (classData != null)
			return classData;

		throw new ClassNotFoundException(className);
	}

}
