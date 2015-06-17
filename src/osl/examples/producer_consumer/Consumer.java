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
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Consumer extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -735351962941393373L;
	private ActorName buffer;

	public Consumer(ActorName buffer) {
		this.buffer = buffer;
	}

	@message
	public void consume() throws RemoteCodeException {
		send(buffer, "release");
	}

	/*
	 * public void start() { System.out.println("Consumer.start"); long mils =
	 * System.currentTimeMillis(); long sec = mils / 1000; long secRem = sec %
	 * 100; if (secRem >= 0 && secRem <= 40) { send(self(), "consume"); } else {
	 * send(self(), "start"); } }
	 */
}
