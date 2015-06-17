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
package osl.examples.fibonacci;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class SimpleFibActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7267102811799684994L;

	/**
	 * The number of responses we have received if we are a worker fib actor
	 */
	private int numResponses;

	/**
	 * The first response we have received from a child fib actor. We save this
	 * value until we receive the second response, and send our client the sum
	 * of both values.
	 */
	private int partialResponse;

	/**
	 * The name of the receiving client (if we are a worker fib actor)
	 */
	private ActorName client;

	/**
	 * The method to invoke on the receiving client (if we are a worker fib
	 * actor)
	 */
	private String meth;

	/**
	 * The default constructor
	 */
	public SimpleFibActor() {
	}

	/**
	 * A constructor which tells us who to send the result to when we have
	 * received all responses. We only use this constructor for building the
	 * response portion of a fibonacci request. I.e. this constructor should
	 * normally only be called internally.
	 */
	public SimpleFibActor(ActorName theClient, String theMeth) {
		client = theClient;
		meth = theMeth;
		numResponses = 0;
	}

	private long startTime = 0;
	/**
	 * Just a test
	 * 
	 * @throws RemoteCodeException
	 * @throws SecurityException
	 */
	@message
	public void boot(Integer startVal) throws SecurityException,
			RemoteCodeException {
		startTime = System.nanoTime();
		ActorName root = create(SimpleFibActor.class, self(), "finished");
		sendByRef(root, "fib", startVal);
	}

	@message
	public void finished(Integer finalVal) {
		
		sendByRef(stdout, "println", "Final result: " + finalVal + " after " + ((System.nanoTime() - startTime) / 1000000000) + "s");
	}

	/**
	 * This method is called by a client to request a fib computation.
	 * 
	 * @param <b>val</b> An <em>Integer</em> indicating which fibonacci number
	 *        should be computed.
	 */
	@message
	public void fib(Integer val) throws RemoteCodeException {

		// If val < 3 then we know the answer so just return it
		if (val == 0) {
			sendByRef(client, meth, 0);
		} else if (val < 3) {
			sendByRef(client, meth, 1);
		} else {
			// Otherwise, create a child to handle the response and resubmit the
			// two sub-problems.
			ActorName newChild1 = create(SimpleFibActor.class, self(), "result");
			sendByRef(newChild1, "fib", val - 1);
			ActorName newChild2 = create(SimpleFibActor.class, self(), "result");
			sendByRef(newChild2, "fib", val - 2);
		}
	}

	/**
	 * This method is called from another child actor to pass a partial result.
	 * We wait until we have two such results, sum them, and return the result
	 * to our client.
	 * 
	 * @param <b>val</b> The <em>Integer</em> partial result passed from a child
	 *        fib actor.
	 */
	@message
	public void result(Integer val) {
		if (numResponses == 0) {
			numResponses++;
			partialResponse = val;
		} else {
			sendByRef(client, meth, val + partialResponse);
		}
	}
}
