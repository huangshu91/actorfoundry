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
import osl.manager.ActorRequest;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

import osl.manager.ActorManagerName;
import osl.service.yp.YP;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 * 
 */
public class DistFibActor extends Actor {
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

	private String lbNode;

	/**
	 * The default constructor
	 */
	public DistFibActor() {
	}

	/**
	 * A constructor which tells us who to send the result to when we have
	 * received all responses. We only use this constructor for building the
	 * response portion of a fibonacci request. I.e. this constructor should
	 * normally only be called internally.
	 */
	public DistFibActor(ActorName theClient, String theMeth, String node) {
		client = theClient;
		meth = theMeth;
		numResponses = 0;
		lbNode = node;
	}

	private long startTime = 0;

	/**
	 * Just a test
	 * 
	 * @throws RemoteCodeException
	 * @throws SecurityException
	 */
	@message
	public void boot(Integer startVal, String node) throws SecurityException,
			RemoteCodeException {
						startTime = System.nanoTime();

		ActorName root = create(DistFibActor.class, self(), "finished", node);
		sendByRef(root, "fib", startVal);
	}

	@message
	public void finished(Integer finalVal) {
		sendByRef(stdout, "println", "Final result: " + finalVal + " after " + ((System.nanoTime() - startTime) / 1000000000) + "s");
	}

	@message
	public void asynchException(ActorRequest cause, Exception e) {
		// Default behavior is to print the exception to the log.
		// Log.println("<Actor.asynchException>: received exception: " + e +
		// " with cause: " + cause);
		// Log.logExceptionTrace(e);
		System.err.println("<Actor.asynchException>: received exception: " + e
				+ " with cause: " + cause);
		e.printStackTrace();
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
			ActorName newChild1 = null;

			// TODO remove the hardcoded threshold and address
			if (val == 28) {
				try {
					ActorManagerName remoteManager = (ActorManagerName) invokeService(
							YP.name, "ypLookupRemoteManager", lbNode);
					newChild1 = create(remoteManager, SimpleFibActor.class,
							self(), "result");
				} catch (Exception e) {
					newChild1 = create(DistFibActor.class, self(), "result",
							lbNode);
				}
			} else {

				newChild1 = create(DistFibActor.class, self(), "result", lbNode);
			}
			sendByRef(newChild1, "fib", val - 2);
			ActorName newChild2 = create(DistFibActor.class, self(),
					"result", lbNode);
			sendByRef(newChild2, "fib", val - 1);
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