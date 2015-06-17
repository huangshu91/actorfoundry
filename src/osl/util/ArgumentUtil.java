package osl.util;

import java.util.StringTokenizer;
import java.util.Vector;

public class ArgumentUtil {

	public static Object[] parseArguments(String s) {
		StringTokenizer stringTokenizer = new StringTokenizer(s);
		return parseArguments(stringTokenizer);
	}

	public static Object[] parseArguments(StringTokenizer stringTokenizer) {
		Vector<Object> out = new Vector<Object>();

		String t;
		while (stringTokenizer.hasMoreTokens()) {
			t = stringTokenizer.nextToken();
			if (t.equals("true")) {
				out.add(Boolean.TRUE);
			} else if (t.equals("false")) {
				out.add(Boolean.FALSE);
			} else if (t.equals("null")) {
				out.add(null);
			} else if (t.charAt(0) == '\'' && t.length() == 3
					&& t.charAt(2) == '\'') {
				out.add(t.charAt(1));
			} else if (Character.isDigit(t.charAt(0))) {
				try {
					out.add(Integer.valueOf(t));
				} catch (NumberFormatException e) {
					//e.printStackTrace();
					try {
						out.add(Double.valueOf(t));
					} catch (NumberFormatException e1) {
						//e1.printStackTrace();
						out.add(t);
					}
				}
			} else {
				out.add(t);
			}
		}

		return out.toArray();

	}

}
