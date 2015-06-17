// $Id: Id.java,v 1.1 1999/07/13 02:01:48 cvs Exp $

/** Id provides time-stamps and id strings. */

package osl.examples.tester;

import java.text.DecimalFormat;

public class Id {
	private static DecimalFormat form0 = new DecimalFormat("000.000");

	/** timeZero is initialized to current time. */
	private static long timeZero = System.currentTimeMillis();

	// =======================================================
	public Id() {
	}

	// =======================================================
	/** Reset elapsed time to zero */
	public static void resetTimeZero() {
		timeZero = System.currentTimeMillis();
	}

	// =======================================================
	/**
	 * compute and return a time-stamp string, current time - timeZero
	 */
	public static String stamp() {
		form0.setMaximumIntegerDigits(3);
		return " @" + form0.format(elapsedTime() / 1000.0) + "s  ";
	}

	// =======================================================
	/** Return elapsed time in milliseconds */
	public static long elapsedTime() {
		return System.currentTimeMillis() - timeZero;
	}

	// =======================================================
	/** Show a time-stamped note */
	public static void showId(String note) {
		System.out.println(stamp() + note);
	}
}
