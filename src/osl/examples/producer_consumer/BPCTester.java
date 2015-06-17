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

public class BPCTester extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8386012575255301905L;

	@message
	public void test() throws RemoteCodeException {
		ActorName buffer = create(Buffer.class, new Integer(10));
		ActorName producer = create(Producer.class, buffer);
		ActorName consumer = create(Consumer.class, buffer);

		call(buffer, "makeBufferNotReady");
		send(consumer, "consume");
		send(consumer, "consume");
		send(consumer, "consume");
		Integer t = (Integer) call(buffer, "getTail");
		if (t != 0) {
			System.err.println("BPC 1st test FAILED!");
		} else {
			System.out.println("BPC 1st test OK!");
		}
		call(buffer, "makeBufferReady");

		call(producer, "produceN", new Integer(20));
		t = (Integer) call(buffer, "getTail");
		if (t != 10) {
			System.err.println("BPC 2nd test FAILED!");
		} else {
			System.out.println("BPC 2nd test OK!");
		}

		for (int i = 0; i < 7; i++) {
			send(consumer, "consume");
		}
		t = (Integer) call(buffer, "getTail");
		if (t != 10) {
			System.err.println("BPC 3rd test FAILED!");
		} else {
			System.out.println("BPC 3rd test OK!");
		}

		for (int i = 0; i < 6; i++) {
			send(consumer, "consume");
		}
		call(consumer, "consume");
		t = (Integer) call(buffer, "getTail");
		if (t != 3) {
			System.err.println("BPC 4th test FAILED!");
		} else {
			System.out.println("BPC 4th test OK!");
		}
	}
}
