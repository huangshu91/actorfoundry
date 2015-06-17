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

import java.util.Random;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Client extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934387546436504024L;

	private ActorName server;

	@message
	public void init(Integer num) throws RemoteCodeException {
		server = create(Server.class);
		send(self(), "test", num);
	}

	@message
	public void test(Integer num) {
		Random random = new Random(System.currentTimeMillis());
		int[] list = new int[num];
		for (int i = 0; i < num; i++) {
			list[i] = random.nextInt(100);
		}
		double sum = 0;
		for (int i = 0; i < list.length; i++) {
			sum += list[i];
		}
		double cavg = sum / (list.length + 0.0);
		System.out.println("Average is: " + cavg);
		sendByRef(server, "sortAverage", list, self());
	}

	@message
	public void result(Double avg) {
		System.out.println("Average by averager: " + avg);
	}
}
