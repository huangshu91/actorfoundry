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
package osl.examples.producer_consumer;

import osl.manager.Actor;
import osl.manager.annotations.message;
import osl.util.constraints.Disable;

public class Buffer extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4919437134734706348L;

	private int bufferSize;

	/**
	 * when buffer is full tail is equal to bufferSize when buffer is empty tail
	 * is equal to 0
	 */
	private int tail;

	private Object[] bufferContent;

	public Buffer(Integer bufferSize) {
		this.bufferSize = bufferSize;
		bufferContent = new Object[this.bufferSize];
		this.tail = 0;
		this.bufferReady = false;
	}

	public Buffer() {
		this(10);
	}

	@message
	public void put(Integer x) {
		bufferContent[tail] = x;
		tail++;
	}

	@message
	public void release() {
		bufferContent[tail - 1] = null;
		tail--;
	}

	@message
	public Integer getTail() {
		return tail;
	}

	@message
	public void makeBufferReady() {
		bufferReady = true;
	}

	@message
	public void makeBufferNotReady() {
		bufferReady = false;
	}

	private boolean bufferReady;

	@Disable(messageName = "put")
	public Boolean disablePut(Integer x) {
		if (bufferReady) {
			if (tail == bufferSize)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}

	@Disable(messageName = "release")
	public Boolean disableRelease() {
		if (bufferReady) {
			if (tail == 0)
				return true;
			else
				return false;
		} else {
			return true;
		}
	}
}
