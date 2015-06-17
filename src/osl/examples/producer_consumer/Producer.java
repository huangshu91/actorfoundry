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

import java.util.Random;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Producer extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5681432260723915032L;
	private ActorName buffer;

	public Producer(ActorName buffer) {
		this.buffer = buffer;
	}

	@message
	public void produce() throws RemoteCodeException {
		send(buffer, "put", new Random().nextInt(100) + 1);
	}

	@message
	public void produceN(Integer n) throws RemoteCodeException {
		for (int i = 0; i < n; i++) {
			send(buffer, "put", new Random().nextInt(100) + 1);
		}
	}

	/*
	 * public void start() { System.out.println("Producer.start"); long mils =
	 * System.currentTimeMillis(); long sec = mils / 1000; long secRem = sec %
	 * 100; if (secRem >= 0 && secRem <= 40) { send(self(), "produce"); } else {
	 * send(self(), "start"); } }
	 */
}
