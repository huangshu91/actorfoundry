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
 * Distributed under the license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.manager;

import kilim.PauseReason;
import osl.util.Queue;

/**
 * @author Rajesh Karmani <rkumar8@illinois.edu> Created on Jan 15, 2009
 * 
 */
public class Empty_MbReason implements PauseReason {

	final Queue<Object> mbx;

	public Empty_MbReason(Queue<Object> mb) {
		mbx = mb;
	}

	public boolean isValid() {
		// The pauseReason is "Empty" if the mbox has no message
		return mbx.empty();
	}

	public String toString() {
		return " Waiting for msg = " + isValid()
		// + ", notified: " + notified
		;
	}

}
