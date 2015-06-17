package osl.foundry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class maintains the foundry status directory. This directory contains
 * several files which describe particular features of the foundry. At the
 * moment, the directory has the following structure:
 * 
 * <table border=1>
 * <tr>
 * <td><tt>$(STATUS)/foundry</tt></td>
 * <td>The main foundry status directory. The value of STATUS is set in the
 * configuration file for the foundry and defaults to /var. Note that STATUS
 * must already exist, or a run-time error is signalled.
 * </tr>
 * 
 * <tr>
 * <td><tt>$(STATUS)/foundry/logs</tt></td>
 * <td>The thread log directory for each foundry thread. Each thread in the
 * foundry may produce a log by registering itself with the <em>Log</em>. See
 * that class for details.
 * </tr>
 * 
 * </table>
 * 
 * This class is mainly responsible for building and maintaining the foundry
 * status directory structure. Other classes (e.g. <em>osl.util.Log</em>) are
 * responsible for storing files within the status directory.
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/07/13 02:01:50 $)
 */

public class FoundryStatus {
	/**
	 * This static field holds the default value for STATUS. The
	 * <em>FoundryStart</em> class will alter this value if a STATUS entry is
	 * found in the configuration file.
	 */
	static String STATUS = null;

	// Static block to initialize the default location of STATUS
	static {
		String tmp = System.getProperty("user.dir");
		File cur = new File(tmp);

		// while (cur.getParent() != null)
		// cur = new File(cur.getParent());

		// This is a minor kludge to fix an apparent inconsistency in
		// File.getParent(). In the Solaris/UNIX release,
		// File.getParent("/foo")="/", while in the Windows release,
		// File.getParent("C:\foo")="C:". I.e. the trailing "\" is left
		// off, leaving an incorrect directory spec.
		tmp = cur.toString();
		if (tmp.endsWith(File.separator))
			STATUS = tmp + "var";
		else
			STATUS = tmp + File.separator + "var";

		// PRAGMA [debug,osl.foundry.FoundryStatus]
		// System.out.println("Default STATUS is: " + STATUS);
	}

	/**
	 * A set of constants defining each directory. These constants are used by
	 * clients to specify the location of status files.
	 */
	public static final FoundryStatusDir STATDIR_FOUNDRY = new FoundryStatusDir(
			"foundry");
	public static final FoundryStatusDir STATDIR_LOGS = new FoundryStatusDir(
			STATDIR_FOUNDRY, "logs");

	/**
	 * This static hashtable holds all the foundry directories we need to create
	 * and maintain.
	 */
	static Hashtable<FoundryStatusDir, FoundryStatusDir> dirs;

	// Use static code to initialize the hashtable
	static {
		dirs = new Hashtable<FoundryStatusDir, FoundryStatusDir>();
		// makeStatusDir(STATDIR_FOUNDRY);
		// makeStatusDir(STATDIR_LOGS);

		dirs.put(STATDIR_FOUNDRY, STATDIR_FOUNDRY);
		dirs.put(STATDIR_LOGS, STATDIR_LOGS);
	}

	/**
	 * Initialize the foundry status directory. This first cleans out any
	 * existing directory (or displays an error if this is not possible), and
	 * then creates a directory for all the directories in <em>dirs</em>.
	 */
	public static void initStatusDirs() {
		// PRAGMA [debug,osl.foundry.FoundryStatus]
		// System.out.println("<FoundryStatus>: file.separator=" +
		// File.separator);

		// Clean out the old foundry status directory if it exists
		File foundryDir = new File(STATUS + File.separator
				+ (dirs.get(STATDIR_FOUNDRY)).getName());

		// PRAGMA [debug,osl.foundry.FoundryStatus]
		// System.out.println("Checking if " + foundryDir.toString() +
		// " exists");

		if (foundryDir.exists()) {
			// PRAGMA [debug,osl.foundry.FoundryStatus]
			// System.out.println("Exists, deleting...");
			Vector<String> toDelete = new Vector<String>();
			toDelete.addElement(foundryDir.toString());
			if (!recursiveDelete(toDelete)) {
				System.err.println("ERROR: couldn't remove directory: "
						+ foundryDir.toString());
				System.err.println("Foundry Terminated");
				System.exit(1);
			}

			// PRAGMA [debug,osl.foundry.FoundryStatus]
			// System.out.println("Done deleting...");
		}

		// Now create the foundry status directories.
		for (Enumeration<FoundryStatusDir> i = dirs.keys(); i.hasMoreElements();) {

			File toMake = new File(STATUS + File.separator
					+ (i.nextElement()).getName());

			if (!toMake.exists())
				try {
					if (!toMake.mkdirs()) {
						System.err
								.println("ERROR: Can't create status directory: "
										+ toMake.toString());
						System.err.println("Foundry Terminated");
						System.exit(1);
					}
				} catch (SecurityException e) {
					System.err
							.println("Security Exception: Not permitted to create status directory: "
									+ toMake.toString());
					System.err.println("Foundry Terminated");
					System.exit(1);
				}
		}
	}

	/**
	 * Create a new status directory at run-time.
	 * 
	 * @param <b>dir</b> An instance of <em>FoundryStatusDir</em> which
	 *        describes the new directory to create.
	 */
	public static void makeStatusDir(FoundryStatusDir dir) {
		if (!dirs.containsKey(dir)) {
			dirs.put(dir, dir);
			File toMake = new File(STATUS + File.separator + dir.getName());
			toMake.mkdirs();
		}
	}

	/**
	 * This method creates a new status directory file and returns an output
	 * stream which may be used to write to that file.
	 * 
	 * @param <b>where</b> A constant defined at the head of this file which
	 *        indicates the directory in which the new file should reside.
	 * @param <b>name</b> The <em>String</em> name of the new file. If the file
	 *        already exists then it is appended to with the current stream.
	 */
	public static FileOutputStream makeStatusFile(FoundryStatusDir where,
			String name) throws IOException {
		File fileRef = null;

		// See if this file already exists, if it does then we're
		// appending to it.
		fileRef = new File(STATUS + File.separator + where.getName()
				+ File.separator + name);
		if (fileRef.exists())
			return new FileOutputStream(STATUS + File.separator
					+ where.getName() + File.separator + name, true);
		else
			return new FileOutputStream(STATUS + File.separator
					+ where.getName() + File.separator + name, false);
	}

	/**
	 * This method returns an input stream for an existing status directory
	 * file, or throws an error if the requested file does not exist or is
	 * unreadable.
	 * 
	 * @param <b>where</b> A constant defined at the head of this file which
	 *        indicates the directory in which the new file should reside.
	 * @param <b>name</b> The <em>String</em> name of the new file. If the file
	 *        already exists then it is appended to with the current stream.
	 */
	public static FileInputStream getStatusFile(FoundryStatusDir where,
			String name) throws FileNotFoundException, IOException {
		File fileRef = null;

		// See if this file exists, if it doesn't then throw an error,
		// otherwise return the appropriate input stream.
		fileRef = new File(STATUS + File.separator + where.getName()
				+ File.separator + name);
		return new FileInputStream(fileRef);
	}

	/**
	 * Recursively delete all the files and directories specified in the vector
	 * argument.
	 * 
	 * @return <tt>true</tt> if the recursive delete was successful,
	 *         <tt>false</tt> otherwise.
	 */
	static boolean recursiveDelete(Vector<String> files) {
		for (Enumeration<String> e = files.elements(); e.hasMoreElements();) {

			String next = e.nextElement();
			File toDelete = new File(next);
			// PRAGMA [debug,osl.foundry.FoundryStatus]
			// System.out.println("About to delete " + next);

			if (toDelete.exists()) {
				if (!toDelete.isDirectory())
					// PRAGMA [debug,osl.foundry.FoundryStatus] {
					// System.out.println("Regular file - deleting");
					try {
						if (!toDelete.delete()) {
							System.err.println("ERROR: Can't delete file: "
									+ toDelete.toString());
							return false;
						}
					} catch (SecurityException f) {
						System.err
								.println("Security Exception: Not permitted to delete file: "
										+ toDelete.toString());
						return false;
					}
				// PRAGMA [debug,osl.foundry.FoundryStatus] }
				else {
					// PRAGMA [debug,osl.foundry.FoundryStatus]
					// System.out.println("Directory - recursing");
					Vector<String> recurse = new Vector<String>();
					String[] subs = toDelete.list();
					for (int i = 0; i < subs.length; i++)
						recurse.addElement(next + File.separator + subs[i]);

					if (!recursiveDelete(recurse))
						return false;

					try {
						if (!toDelete.delete()) {
							System.err.println("ERROR: Can't delete file: "
									+ toDelete.toString());
							return false;
						}
					} catch (SecurityException f) {
						System.err
								.println("Security Exception: Not permitted to delete file: "
										+ toDelete.toString());
						return false;
					}
				}
			}
		}

		return true;
	}

	public static String getDirTop() {
		return STATUS;
	}
}
