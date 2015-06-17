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

import osl.manager.ActorName;
import osl.manager.annotations.message;

/**
 * This class is equivalent to <em>FibActor</em> except that "result" actors
 * destroy themselves after sending their result. That is, after sending the
 * result, a call to Actor.destroy is made, which should remove the actor from
 * the system.
 * <p>
 * 
 * This version of <em>FibActor</em> serves no other purpose than to test the
 * <em>destroy</em> method with a large number of actors.
 * <p>
 * 
 * @see osl.examples.fibonacci.FibActor
 * @see osl.manager.Actor#destroy
 * @author Mark Astley
 * @version $Revision: 1.2 $ ($Date: 1998/10/05 15:47:32 $)
 */

public class FibSuicide extends FibActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9194294141683439645L;

	/**
	 * The default constructor
	 */
	public FibSuicide() {
	}

	/**
	 * A constructor which tells us who to send the result to when we have
	 * received all responses. We only use this constructor for building the
	 * response portion of a fibonacci request. I.e. this constructor should
	 * normally only be called internally.
	 */
	public FibSuicide(ActorName theClient, String theMeth) {
		client = theClient;
		meth = theMeth;
		numResponses = 0;
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
			destroy("Result actor no longer accessible, removing");
		}
	}
}
