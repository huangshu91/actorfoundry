package osl.util;

import java.io.PrintWriter;

/**
 * This class provides a simple debugging class which statically stores an
 * output stream and provides an <em>exit</em> method for exiting out of the
 * java run-time environment. By redirecting the output stream for this class
 * you can, for example, send debugging output to a file rather than stdout. The
 * <em>exit</em> method also dumps the stack which greatly reduces the usual
 * sleuthing necessary to discover where an error has occurred.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:26 $)
 */

public class Debug {
	/**
	 * The <em>out</em> field specifies where debugging output will be sent.
	 * Normally, this is hardcoded to <code>System.out</code>. This can be
	 * changed, for example, by using the <b>-out</b> command line option when
	 * instantiating an <em>ActorManager</em>.
	 */
	public static final PrintWriter out = new PrintWriter(System.out, true);

	/**
	 * Exit the java run-time environment and return an error condition.
	 * 
	 * @return <b>void</b>
	 */
	public static void exit() {
		exit("");
	}

	/**
	 * Print a user-supplied string to the channel specified by
	 * <code>osl.foundry.Debug.out</code> and exit the java run-time environment
	 * signaling an error.
	 * 
	 * @param <b>msg</b> A <em>String</em> indicating the message to be
	 *        displayed
	 * @return <b>void</b>
	 */
	public static void exit(String msg) {
		Debug.out.println(msg);
		Thread.dumpStack();
		System.exit(1);
	}

}
