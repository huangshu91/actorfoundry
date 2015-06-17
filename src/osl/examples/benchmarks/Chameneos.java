/**
 * Developed by: The Open Systems Lab
 *             University of Illinois at Urbana-Champaign
 *             Department of Computer Science
 *             Urbana, IL 61801
 *             http://osl.cs.uiuc.edu
 *
 * Contact: http://osl.cs.uiuc.edu/af
 *
 * Copyright (c) 1998-2009
 * The University of Illinois Board of Trustees.
 *    All Rights Reserved.
 * 
 * Distributed under license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.examples.benchmarks;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 * @author Amin Shali <shali1@cs.uiuc.edu>
 *
 */
public class Chameneos extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021339002556170556L;

	public enum Colour {
		blue, red, yellow
	}

	static Colour doCompliment(Colour c1, Colour c2) {
		switch (c1) {
		case blue:
			switch (c2) {
			case blue:
				return Colour.blue;
			case red:
				return Colour.yellow;
			case yellow:
				return Colour.red;
			}
		case red:
			switch (c2) {
			case blue:
				return Colour.yellow;
			case red:
				return Colour.red;
			case yellow:
				return Colour.blue;
			}
		case yellow:
			switch (c2) {
			case blue:
				return Colour.red;
			case red:
				return Colour.blue;
			case yellow:
				return Colour.yellow;
			}
		}

		throw new RuntimeException("Error");
	}

	int myHooks, selfHooks;
	Colour myColor;

	ActorName broker;

	public Chameneos(ActorName b, Colour c) {
		broker = b;
		myColor = c;
	}

	@message
	public void start() {
		sendByRef(broker, "hook", self(), myColor);
	}

	@message
	public void hook(ActorName other, Colour c) throws RemoteCodeException {
		myColor = doCompliment(myColor, c);

		if (self() == other)
			selfHooks++;
		myHooks++;
		this.start();
		// send(self(), "start");
	}

	@message
	public String to_String() {
		return String.valueOf(myHooks) + getNumber(selfHooks);
	}

	@message
	public void stop() {
		sendByRef(stdout, "println", toString());
	}

	private static final String[] NUMBERS = { "zero", "one", "two", "three",
			"four", "five", "six", "seven", "eight", "nine" };

	static String getNumber(int n) {
		StringBuilder sb = new StringBuilder();
		String nStr = String.valueOf(n);
		for (int i = 0; i < nStr.length(); i++) {
			sb.append(" ");
			sb.append(NUMBERS[Character.getNumericValue(nStr.charAt(i))]);
		}

		return sb.toString();
	}

	@message
	public int getCount() {
		return myHooks;
	}
}
