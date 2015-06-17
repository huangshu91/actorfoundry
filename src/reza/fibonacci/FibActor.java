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
package reza.fibonacci;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class represents the canonical fibonacci example actor. Two methods are
 * defined for this actor:
 * 
 * <dl>
 * <dt><b>fib</b>(<em>Integer</em>, <em>ActorName</em>, <em>String</em>)
 * <dd>This method requests a fib computation from the caller at
 * <em>ActorName</em>. When the computation is complete, this actor will reply
 * to the caller by invoking the <em>String</em> method which must have a
 * version which takes a single <em>Integer</em> argument.
 * <dt><b>result</b>(<em>Integer</em>)
 * <dd>This method is called by a child fib actor when it has completed a
 * portion of the fib computation. The receiving actor keeps track of the number
 * of responses it receives and then forwards the answer to a particular caller.
 * Note that the main fib server never actually receives a result message.
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1998/07/18 18:59:38 $)
 */

public class FibActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4273990623751326142L;

	/**
	 * The number of responses we have received if we are a worker fib actor
	 */
	int numResponses;

	/**
	 * The first response we have received from a child fib actor. We save this
	 * value until we receive the second response, and send our client the sum
	 * of both values.
	 */
	int partialResponse;

	/**
	 * The name of the receiving client (if we are a worker fib actor)
	 */
	ActorName client;

	/**
	 * The method to invoke on the receiving client (if we are a worker fib
	 * actor)
	 */
	String meth;

	/**
	 * The default constructor
	 */
	public FibActor() {
	}

	/**
	 * A constructor which tells us who to send the result to when we have
	 * received all responses. We only use this constructor for building the
	 * response portion of a fibonacci request. I.e. this constructor should
	 * normally only be called internally.
	 */
	public FibActor(ActorName theClient, String theMeth) {
		client = theClient;
		meth = theMeth;
		numResponses = 0;
	}

	/**
	 * Just a test
	 */
	@message
	public void boot(Integer startVal) {
		send(self(), "fib", startVal, self(), "finished");
	}

	@message
	public void finished(Integer finalVal) {
		send(stdout, "println", "Final result: " + finalVal);
	}

	/**
	 * This method is called by a client to request a fib computation.
	 * 
	 * @param <b>val</b> An <em>Integer</em> indicating which fibonacci number
	 *        should be computed.
	 * @param <b>client</b> The <em>ActorName</em> of the client waiting for a
	 *        response.
	 * @param <b>meth</b> A <em>String</em> naming the method to be invoked on
	 *        the client with the response.
	 */
	@message
	public void fib(Integer val, ActorName client, String meth) {
		int theVal = val.intValue();
		ActorName newChild = null;

		// If val < 3 then we know the answer so just return it
		if (theVal == 0) {
			send(client, meth, new Integer(0));
		} else if (theVal < 3) {
			send(client, meth, new Integer(1));
		} else {
			// Otherwise, create a child to handle the response and resubmit the
			// two sub-problems.

			try {
				newChild = create(FibActor.class, client, meth);
			} catch (RemoteCodeException e) {
			}

			send(self(), "fib", new Integer(theVal - 1), newChild, "result");
			send(self(), "fib", new Integer(theVal - 2), newChild, "result");
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
			partialResponse = val.intValue();
		} else {
			// Send the answer
			send(client, meth, new Integer(val.intValue() + partialResponse));
		}
	}
}
