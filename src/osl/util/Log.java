package osl.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;

import kilim.Task;
import osl.foundry.FoundryStatus;

/**
 * This class provides a thread logging mechanism which allows threads to output
 * debugging information to entries in the "logs" foundry status directories. In
 * order for debugging output to be generated, each thread must register itself
 * with this class. Later invocations of Log.println (for example) will result
 * in outputting data to the logfile registered for the thread. For the most
 * part, this class behaves as a special form of <tt>java.io.PrintWriter</tt>.
 * <p>
 * 
 * A thread registers itself by specifying a "category" in the
 * <em>logThread</em> method. A category corresponds to the name of a log file
 * in the log status directory. To allow demultiplexing, a unique
 * <em>String</em> of the form (Category-ID) is associated with each thread
 * registered in a category. You can then grep the category log file to get the
 * isolated output of a particular thread. Note that java imposes some sort of
 * limit on the number of open file descriptors in a virtual machine. I haven't
 * figured out exactly how this limit relates to the limit for the system yet.
 * So in the meantime, be aware that you may be limited in the number of
 * categories you may open.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/03/06 21:18:37 $)
 */

public class Log implements Runnable {
	/**
	 * An array of number padding strings. Indexed by the length of the source
	 * string. I.e. if the source string has length 0, then padString[0] should
	 * be used to generate the correct padding.
	 */
	static final String[] padString = { "00000", "0000", "000", "00", "0", "" };

	/**
	 * This table maps threads to the logging output stream they are associated
	 * with.
	 */
	static final Hashtable<Object, LogThreadEntry> logDir = new Hashtable<Object, LogThreadEntry>();

	/**
	 * This table maps categories to the unique <em>Integer</em> which should be
	 * used to form the name of the next thread logged in this category.
	 */
	static final Hashtable<String, LogCategory> categories = new Hashtable<String, LogCategory>();

	/**
	 * This value sets the delay between cleanup cycles of the logging
	 * mechanism. When a cleanup is necessary, we steal cycles from a thread
	 * calling one of our methods. To avoid punishing an unlucky thread when a
	 * cleanup is in process, each thread is only required to cycle through a
	 * fixed number of threads in logDir.
	 */
	static final long timeoutCycle = 10000; // 10 seconds

	public static final boolean logEnabled = false;

	/**
	 * This method is called to start a cleanup thread for the logging system.
	 * The cleanup thread wakes up every <em>timeoutCycle</em> milliseconds and
	 * removes any stopped threads from the log table. This allows these threads
	 * to be GC'd when all references have been relinquished.
	 */
	public void run() {
		Enumeration<Object> cleanStat = null;
		Thread next = null;
		Object temp;

		// Log.println("<Log.run>: Cleanup thread started");

		// This thread runs until the JVM dies
		while (true) {
			// Start a new cleanup cycle for Threads. Actors can't be GC'ed this
			// way
			// Log.println("<Log.run>: Starting new GC cycle...");
			cleanStat = logDir.keys();

			for (next = null; cleanStat.hasMoreElements();) {
				temp = cleanStat.nextElement();
				if (!(temp instanceof Thread))
					continue;
				next = (Thread) temp;
				if ((next != null) && (!next.isAlive())) {
					// Log.println("<Log.run>: Removing thread " + next);
					logDir.remove(next);
				}
			}

			// These assignments are done to expedite GC
			cleanStat = null;
			next = null;

			// Log.println("<Log.run>: GC cycle complete...");

			try {
				Thread.sleep(timeoutCycle);
			} catch (InterruptedException e) {
				// Ignore but log it
				// Log.println("<Log.run>: Unexpected interrupt exception:" +
				// e);
			}
		}
	}

	/**
	 * This method is used to register a thread for debugging output. It such
	 * register includes a <em>String</em> "category" which provides the header
	 * for the filename for the thread. The complete file name for a thread log
	 * is given by:<br>
	 * <br>
	 * 
	 * <em>Category</em> + <em>UniqueInt</em><br>
	 * <br>
	 * 
	 * where <em>UniqueInt</em> is an integer unique relative to all other
	 * threads in the same <em>Category</em>. The name of the log file is
	 * returned as the result of this method.
	 * 
	 * @param <b>category</b> A <em>String</em> describing the "type" of the
	 *        thread. E.g. Actor, ActorManager, ActorImpl, etc.
	 * @param <b>which</b> A reference to the thread which should be logged.
	 * @return A <em>String</em> giving the name of the file which will log the
	 *         specified thread's output.
	 * @exception java.lang.SecurityException
	 *                Thrown if the specified thread is already being logged.
	 * @exception java.io.IOException
	 *                Thrown if there is an error opening the status directory
	 *                entry for the requested log.
	 */
	public static final String logThread(String category, Thread which)
			throws SecurityException, IOException {

		try {

			if (logDir.get(which) != null)
				throw new SecurityException(
						"Error: specified thread is already being logged!");

			// Find the log category entry or build a new one if the
			// specified entry doesn't exist.
			LogCategory cat = (LogCategory) categories.get(category);
			if (cat == null) {
				PrintWriter newStream = new PrintWriter(FoundryStatus
						.makeStatusFile(FoundryStatus.STATDIR_LOGS, category),
						true);
				cat = new LogCategory(newStream, category);
				categories.put(category, cat);
			}

			// Build a LogThreadEntry for the new thread and add it to the
			// logDir table
			String logFileName = "(" + category + "-"
					+ padNumber((new Integer(cat.id++)).toString()) + "): ";
			LogThreadEntry newEntry = new LogThreadEntry(cat.outStream,
					logFileName);
			logDir.put(which, newEntry);

			return logFileName;
		} catch (Exception e) {
			if (e instanceof SecurityException)
				throw (SecurityException) e;
			else if (e instanceof IOException)
				throw (IOException) e;
			else {
				System.out.println("Error in logThread: " + e);
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	public static final String logThread(String category, Task which)
			throws SecurityException, IOException {

		try {

			if (logDir.get(which) != null)
				throw new SecurityException(
						"Error: specified thread is already being logged!");

			// Find the log category entry or build a new one if the
			// specified entry doesn't exist.
			LogCategory cat = (LogCategory) categories.get(category);
			if (cat == null) {
				PrintWriter newStream = new PrintWriter(FoundryStatus
						.makeStatusFile(FoundryStatus.STATDIR_LOGS, category),
						true);
				cat = new LogCategory(newStream, category);
				categories.put(category, cat);
			}

			// Build a LogThreadEntry for the new thread and add it to the
			// logDir table
			String logFileName = "(" + category + "-"
					+ padNumber((new Integer(cat.id++)).toString()) + "): ";
			LogThreadEntry newEntry = new LogThreadEntry(cat.outStream,
					logFileName);
			logDir.put(which, newEntry);

			return logFileName;
		} catch (Exception e) {
			if (e instanceof SecurityException)
				throw (SecurityException) e;
			else if (e instanceof IOException)
				throw (IOException) e;
			else {
				System.out.println("Error in logThread: " + e);
				e.printStackTrace(System.out);
			}
		}

		return null;
	}

	/**
	 * This method is a convenient way to allow a thread to log the stack trace
	 * of an exception to the current log stream. The calling thread is used to
	 * figure out where to write the output.
	 * 
	 * @param <b>e</b> The <em>Exception</em> to output to the log file.
	 */
	public static final void logExceptionTrace(Throwable e) {
		logExceptionTrace(Thread.currentThread(), e);
	}

	/**
	 * A quick little method for getting the stack trace of an exception. For
	 * some annoying reason, all of the methods defined in Throwable for doing
	 * this require that the string be output to a stream. Very annoying.
	 */
	public static final String getThrowableTrace(Throwable e) {
		CharArrayWriter temp1 = new CharArrayWriter();
		PrintWriter temp2 = new PrintWriter(temp1, true);

		e.printStackTrace(temp2);
		temp2.close();
		temp1.close();

		return temp1.toString();
	}

	/**
	 * This method forces the stack trace of an exception to be logged to the
	 * log stream of a particular thread. This method is useful for logging
	 * events when the current thread is different from the thread who's log
	 * should be written.
	 * 
	 * @param <b>j</b> The <em>Thread</em> which is used to identify the log
	 *        stream to write to.
	 * @param <b>e</b> The <em>Exception</em> to output to the log file.
	 */
	public static final void logExceptionTrace(Thread j, Throwable e) {
		// First generate the string containing the stack trace
		CharArrayWriter temp1 = new CharArrayWriter();
		PrintWriter temp2 = new PrintWriter(temp1, true);
		String outString;

		e.printStackTrace(temp2);
		temp2.close();
		temp1.close();

		outString = temp1.toString();

		// Now output string segments separated by newlines, this has the
		// effect of printing each line of the trace on a separate log
		// line (should be more readable).
		while (outString.indexOf('\n') != -1) {
			// Log.println(j, outString.substring(0, outString.indexOf('\n')));
			try {
				outString = outString.substring(outString.indexOf('\n') + 1);
			} catch (StringIndexOutOfBoundsException f) {
				outString = "";
			}
		}

		if (outString.length() != 0) {
			// Log.println(j, outString);
		}
	}

	/**
	 * A quick and dirty number padder. We want log file listings to be somewhat
	 * readable so we pad log file entries with 0's. We assume the largest
	 * number will be 65536 so we pad with at most 4 0's.
	 */
	static final String padNumber(String num) {
		if ((num.length() >= 0) && (num.length() <= 5))
			return padString[num.length()] + num;
		else
			return num;
	}

	/**
	 * Check whether or not a thread is setup for logging. If it is then return
	 * the <em>PrintWriter</em> which should be used to write log messages,
	 * otherwise return null.
	 * 
	 * @param <b>j</b> The <em>Thread</em> for which we are attempting to find a
	 *        log.
	 */
	static LogThreadEntry getLogFile(Object j) {
		if (!logEnabled) {
			return null;
		}
		try {
			return (LogThreadEntry) logDir.get(j);
		} catch (NullPointerException e) {
			// This can happen if "j" is null
			return null;
		}
	}

	public static String getThreadID(Thread j) {
		try {
			return ((LogThreadEntry) logDir.get(j)).name;
		} catch (NullPointerException e) {
			// This can happen if "j" is null
			throw new RuntimeException("Thread " + j + " not currently logged");
		}
	}

	// /////////////////////////////////////////////////////////////////
	// Log versions of the PrintWriter interface
	// Note that only the println versions are provided. This is
	// because several threads might be writing to a single log
	// category. Thus there is no guarantee that subsequent log
	// messages will be appended to a particular spot in the log file.
	// /////////////////////////////////////////////////////////////////

	/**
	 * Print a boolean to the log stream for a particular thread.
	 */
	public static void println(Thread j, boolean x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a character to the log stream for a particular thread.
	 */
	public static void println(Thread j, char x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print an int to the log stream for a particular thread.
	 */
	public static void println(Thread j, int x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a long to the log stream for a particular thread.
	 */
	public static void println(Thread j, long x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a float to the log stream for a particular thread.
	 */
	public static void println(Thread j, float x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a double to the log stream for a particular thread.
	 */
	public static void println(Thread j, double x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a char array to the log stream for a particular thread.
	 */
	public static void println(Thread j, char x[]) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a string to the log stream for a particular thread.
	 */
	public static void println(Thread j, String x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a string to the log stream for a particular thread.
	 */
	public static void println(Task j, String x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print an object to the log stream for a particular thread.
	 */
	public static void println(Thread j, Object x) {
		LogThreadEntry out = getLogFile(j);
		if (out != null)
			synchronized (out.outStream) {
				out.outStream.print(out.name);
				out.outStream.println(x);
			}
	}

	/**
	 * Print a newline to the log stream for the current thread.
	 */
	public static void println() {
		LogThreadEntry out = getLogFile(Thread.currentThread());
		if (out != null)
			out.outStream.println(out.name);
	}

	/**
	 * Print a boolean to a log stream.
	 */
	public static void println(boolean x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a character to the log stream for the current thread.
	 */
	public static void println(char x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print an int to the log stream for the current thread.
	 */
	public static void println(int x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a long to the log stream for the current thread.
	 */
	public static void println(long x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a float to the log stream for the current thread.
	 */
	public static void println(float x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a double to the log stream for a the current thread.
	 */
	public static void println(double x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a char array to the log stream for the current thread.
	 */
	public static void println(char x[]) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print a string to the log stream for the current thread.
	 */
	public static void println(String x) {
		println(Thread.currentThread(), x);
	}

	/**
	 * Print an object to the log stream for the current thread.
	 */
	public static void println(Object x) {
		println(Thread.currentThread(), x);
	}

}

/**
 * This class is used to store the id and log stream for a thread with logging
 * enabled.
 */
class LogThreadEntry {
	/**
	 * This field contains a reference to the <em>PrintWriter</em> we should be
	 * sending our messages to.
	 */
	PrintWriter outStream;

	/**
	 * This field contains the unique name we should prepend to all our logged
	 * messages.
	 */
	String name;

	/**
	 * Constructor for this class.
	 */
	LogThreadEntry(PrintWriter stream, String nom) {
		outStream = stream;
		name = nom;
	}
}

/**
 * This class stores a reference to the <em>PrintWriter</em> associated with a
 * category, as well as the int which should be associated with the next thread
 * added to the category.
 */
class LogCategory {
	/**
	 * A reference to the <em>PrintWriter</em> for this category.
	 */
	PrintWriter outStream = null;

	/**
	 * The id of the next thread added to this category. This should be
	 * incremented each time a new thread is added.
	 */
	int id = 0;

	/**
	 * The name of this category.
	 */
	String name;

	/**
	 * Constructor for this class.
	 */
	LogCategory(PrintWriter stream, String nom) {
		outStream = stream;
		name = nom;
	}
}
