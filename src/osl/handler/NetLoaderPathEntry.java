package osl.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class is used to store information about classpath entries that we need
 * to search when we look up clases. The main purpose of this class to make
 * things more efficient since class loading may be a common activity.
 */
class NetLoaderPathEntry {
	/**
	 * The type of this path. Currently we only support zip files and regular
	 * directory names. The types are: 0 - zip file path 1 - directory path
	 */
	int type = 0;

	/**
	 * If this path points to a zip file, then this is a pointer to the file
	 * structure which can be used to get entries from it.
	 */
	ZipFile zipData = null;

	/**
	 * If this path points to a zip file, then this is an index of all the files
	 * available in the zip entry.
	 */
	Hashtable<String, String> zipEntries = null;

	/**
	 * If this path is a regular directory, then this string gives the name of
	 * the directory. If this is a zip file, then this string gives the name of
	 * the zip file.
	 */
	String dirTop = null;

	/**
	 * The only constructor for this class. Takes a String classpath as an
	 * argument, and builds an appropriate structure given the type of argument.
	 */
	NetLoaderPathEntry(String path) {

		dirTop = path;

		// Check if this is a zip file first
		try {
			zipData = new ZipFile(path);
		} catch (Exception e) {
			zipData = null;
		}

		if (zipData != null) {
			// Ok, this is a zip file. Set the type and make an index of
			// all its entries. This will make lookups much faster in the
			// future.
			type = 0;
			zipEntries = new Hashtable<String, String>();

			// PRAGMA [debug,osl.foundry.FoundryLoader]
			// Log.println("Path is a zip file, building index...");

			for (Enumeration<?> i = zipData.entries(); i.hasMoreElements();) {
				ZipEntry next = (ZipEntry) i.nextElement();

				// Skip if this is a standard java entry
				if (!next.getName().startsWith("java" + File.separator))
					// PRAGMA [debug,osl.foundry.FoundryLoader] {
					// PRAGMA [debug,osl.foundry.FoundryLoader]
					// Log.out.println("Adding entry " + next.getName());
					zipEntries.put(next.getName(), next.getName());
				// PRAGMA [debug,osl.foundry.FoundryLoader] }
			}

			// When we're done, force a GC in case the ZipEntries hold too
			// many file descriptors.
			zipData = null;
			System.gc();
		} else {
			// Otherwise, we treat this path as a directory name and just
			// save the root directory.
			type = 1;

			// Add the file separator to the end of the path so that we
			// can just append the className when we do a lookup
			if (dirTop.charAt(dirTop.length() - 1) != NetLoader.fileSep)
				dirTop = dirTop + NetLoader.fileSep;
		}

	}

	/**
	 * Return the class data for a class in this path structure. Returns null if
	 * the class cannot be found, otherwise returns the raw byte data for the
	 * class. Note that it is the responsibility of the caller to transform the
	 * raw className into a file name for lookup.
	 */
	byte[] findClass(String className) {
		byte[] classData = null;
		int numRead = 0;

		if (type == 0) {
			// PRAGMA [debug,osl.foundry.FoundryLoader]
			// Log.println("<FoundryPath.findClass> Looking for " + className +
			// " in ZipFile " + dirTop);
			// This is a zip file path. If we have the entry then return
			// it. Otherwise, return null.
			if (zipEntries.containsKey(className)) {
				try {
					zipData = new ZipFile(dirTop);
					ZipEntry value = zipData.getEntry(className);
					InputStream reader = null;
					// byte[] temp = null;
					int next = 0;

					// PRAGMA [assert] Assert.assert(value != null,
					// "should be zip entry for " + className +
					// " but there isn't");
					reader = zipData.getInputStream(value);

					// PRAGMA [debug,osl.foundry.FoundryLoader]
					// Log.println("<FoundryPath.findClass> " + value.getSize()
					// + " bytes available from zip file");
					numRead = (int) value.getSize();
					classData = new byte[numRead];

					// ARGHHH! So the InputStream returned from
					// ZipFile.getInputStream is very picky about whether or not
					// it returns compressed or uncompressed bytes. If you use
					// "read()" then you get uncompressed bytes back. If you
					// use "read(byte[])" or "read(byte[],int,int)" then you get
					// compressed bytes back. "available()" returns the number
					// of COMPRESSED bytes, so only "getSize()" is a reasonable
					// measure of file size.
					for (next = 0; next < numRead; next++)
						classData[next] = (byte) reader.read();

					// PRAGMA [debug,osl.foundry.FoundryLoader]
					// Log.println("<FoundryPath.findClass> Read " + numRead +
					// " uncompressed bytes from zip file");

					// We do this last step because of the limit on the number
					// of file descriptors. Annoying but fact of life.
					zipData = null;
					value = null;
					reader = null;
					System.gc();

					// PRAGMA [debug,osl.foundry.FoundryLoader]
					// Log.println("<FoundryPath.findClass> Entry found, returning class data");

					return classData;
				} catch (Exception e) {
					// Log.println("<FoundryPath.findClass> Error reading zip file "
					// + dirTop + ":" + e);
				}
			}
		} else if (type == 1) {
			// This is a directory path. See if the requested class file
			// exists and return it if it does.
			FileInputStream R = null;

			// PRAGMA [debug,osl.foundry.FoundryLoader]
			// Log.println("<FoundryPath.findClass> Reading from file " + dirTop
			// + className);
			try {
				R = new FileInputStream(dirTop + className);
			} catch (FileNotFoundException e) {
				// Didn't find it so return null
				return null;
			}

			// If we get here then we must have the correct class file so
			// read it in
			try {
				classData = new byte[R.available()];
				R.read(classData);
				R.close();
				R = null;
				return classData;
			} catch (Exception e) {
				// Log.println("<FoundryPath.findClass> Error reading class file "
				// + dirTop + className + ":" + e);
			}
		}

		return null;
	}
}
