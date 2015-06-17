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

import java.io.Serializable;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;
import osl.util.constraints.Disable;

public class BoundedBuffer extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2729475866823254892L;

	private int numOfItems;
	private int maxNumOfItems;

	private ActorName unboundedBuffer;

	public BoundedBuffer(Integer max) throws RemoteCodeException {
		this.maxNumOfItems = max;
		this.numOfItems = 0;
	}

	@message
	public void init() throws RemoteCodeException {
		unboundedBuffer = create(UnboundedBuffer.class);
	}

	@message
	public Object get() throws RemoteCodeException {
		numOfItems--;
		return call(unboundedBuffer, "get");
	}

	@message
	public void put(Object o) throws RemoteCodeException {
		call(unboundedBuffer, "put", (Serializable) o);
		numOfItems++;
	}

	@Disable(messageName = "put")
	public Boolean countPut(Object o) {
		if (numOfItems >= maxNumOfItems) {
			return true;
		}
		return false;
	}

	@message
	public Integer size() throws RemoteCodeException {
		Integer ret = (Integer) call(unboundedBuffer, "size");
		return ret;
	}

}
