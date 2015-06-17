package osl.util;

/**
 * This class is meant to provide the same services as good ol' C/C++ assert
 * macros. Unfortunately, there is no graceful way to do this so we end up
 * having to write a class. The class below is a derivative of a similar class I
 * found in a Java "tech tip". You can find the original class (and other
 * interesting ones) at:
 * <p>
 * 
 * http://developer.javasoft.com/developer/javaInDepth/TechTips/index.html
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/09/07 23:00:36 $)
 */

public class Assert {
	/**
	 * This method gets called when an assertion fails. Its purpose is to stop
	 * execution and print a stack trace indicating where the assertion failed.
	 */
	private static void fail(String msg) {
		try {
			System.err.println("Assertion failed: " + msg);
			Throwable e = new Throwable();
			e.printStackTrace();

			// Print out logging information for the failing thread.
			// Log.println("Assertion failed: " + msg);
			// Log.logExceptionTrace(e);
		} catch (Throwable f) {
			// We ignore any exception here since we're about to exit
		}

		System.exit(1);
	}

	/**
	 * Check if a boolean condition is true or false. If false then we fail.
	 */
	public static void afAssert(boolean b, String msg) {
		if (!b)
			fail(msg);
	}

	/**
	 * Check if an integer is zero. This version is provided so that we can use
	 * C-style assert statements where true and false are based on integral
	 * values.
	 */
	public static void afAssert(long lng, String msg) {
		if (lng == 0L)
			fail(msg);
	}

	/**
	 * Check if a float is zero. This version is provided so that we can use
	 * C-style assert statements where true and false are based on integral
	 * values.
	 */
	public static void afAssert(double dbl, String msg) {
		if (dbl == 0.0)
			fail(msg);
	}

	/**
	 * Check if an object reference is non-null.
	 */
	public static void afAssert(Object ref, String msg) {
		if (ref == null)
			fail(msg);
	}
}
