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
package osl.examples.patternmatching;

import java.util.Stack;
import java.util.Vector;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class MatchingActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 149112389673895039L;

	@message
	public void match(Vector<Object> v) {
		System.out.println("received the vector instance");
	}

	@message
	public void match(Stack<Object> s) {
		System.out.println("received the stack instance");
	}

	@message
	public void boot() throws SecurityException, RemoteCodeException {
		Vector<Object> v = new Stack<Object>();
		ActorName ma = create(MatchingActor.class);
		send(ma, "match", v);
	}

}
