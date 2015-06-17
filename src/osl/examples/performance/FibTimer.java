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
package osl.examples.performance;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;
import osl.util.Debug;

/**
 * This actor is used to time calls to the fib method of <em>FibActor</em>. The
 * constructor of this actor requires the <em>ActorName</em> of a
 * <em>FibActor</em>. When the local "fib" method is called, the current time is
 * measured and the call is made. When the final result is received, the current
 * time is measured again and the result is returned (along with the fib value)
 * to the caller.
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1999/03/06 21:18:34 $)
 */

public class FibTimer extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8641013130922999914L;

	/**
	 * The name of the <em>FinActor</em> we should should send requests to.
	 */
	ActorName fibActor;

	/**
	 * The name of the actor which called us and made the original fib request.
	 */
	ActorName caller;

	/**
	 * The method to invoke on the caller with the result. This method should
	 * accept two Integers as arguments.
	 */
	String receiveMethod;

	/**
	 * Temporary var to hold start time of request.
	 */
	long startTime;

	public FibTimer() {
		try {
			fibActor = create(osl.examples.fibonacci.FibActor.class, self(),
					"finished");
		} catch (RemoteCodeException e) {
			Debug.out.println("Error: create failed: " + e);
		}
	}

	/**
	 * The constructor requires the name of an existing <em>FibActor</em>.
	 */
	public FibTimer(ActorName fActor) {
		fibActor = fActor;
	}

	/**
	 * Just a test
	 */
	@message
	public void boot(Integer startVal) {
		send(self(), "fib", startVal, self(), "finished");
	}

	/**
	 * This method will be called when the result is available from the fib
	 * actor.
	 */
	@message
	public void finished(Integer finalVal) {
		long endTime = System.currentTimeMillis();
		// send(caller, receiveMethod, finalVal, new Integer((int)(endTime -
		// startTime)));
		send(stdout, "println", "Received the result (" + finalVal + ") after "
				+ new Integer((int) (endTime - startTime)) + "ms");
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
		startTime = System.currentTimeMillis();
		send(fibActor, "fib", val, client, meth);
	}

}
