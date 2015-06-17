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
package osl.examples.inheritance.buffer;

import java.util.LinkedList;
import java.util.Queue;

import osl.manager.Actor;
import osl.manager.annotations.message;

public class UnboundedBuffer extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6649111070929860492L;

	private Queue<Object> buffer = new LinkedList<Object>();

	@message
	public void put(Object o) {
		buffer.offer(o);
	}

	@message
	public Object get() {
		return buffer.poll();
	}

	@message
	public Integer size() {
		return buffer.size();
	}

}
