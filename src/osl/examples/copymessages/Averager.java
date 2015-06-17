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
package osl.examples.copymessages;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Averager extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5767884486820083477L;

	@message
	public Double calculate(int[] list, Integer l, Integer u, Integer totalNum)
			throws RemoteCodeException {
		if (u - l + 1 > 2) {
			ActorName a1 = create(Averager.class);
			ActorName a2 = create(Averager.class);
			// System.out.println(new Integer((l+u)/2)+", "+new
			// Integer(((l+u)/2)+1));

			Double s1 = (Double) callByRef(a1, "calculate", list, l,
					new Integer((l + u) / 2), totalNum);
			// System.out.println("s1: "+s1);
			Double s2 = (Double) callByRef(a2, "calculate", list, new Integer(
					((l + u) / 2) + 1), u, totalNum);
			// System.out.println("s2: "+s2);
			return (s1 + s2);
		} else {
			double sum = 0.0;
			for (int i = l; i <= u; i++) {
				// System.out.println(i+"> "+list[i]);
				sum += list[i];
			}
			return sum / (totalNum + 0.0);
		}

	}
}
