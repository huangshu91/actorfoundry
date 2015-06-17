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
package osl.examples.pingpong;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.annotations.message;

/**
 * Yet another pinger. This one is used for blasting messages as fast as
 * possible. A nice class to use for timing tests.
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/06/26 17:39:41 $)
 */

public class PingActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8126004158573047383L;

	/**
	 * The number of pings we will send. Used to determine termination.
	 */
	int numToSend;

	/**
	 * The number of ping messages we have received.
	 */
	int numReceived = 0;

	/**
	 * The number of pong's we've received.
	 */
	int numPongs = 0;

	// The default constructor is fine for this class

	/**
	 * This method is called by an external actor with the number of messages to
	 * be blasted and a target ping actor to send them to.
	 * 
	 * @param <b>num</b> The number of ping messages to send.
	 */
	@message
	public void start(ActorName dest, Integer toSend) {
		numToSend = toSend;
		send(stdout, "println", "Starting ping blast");
		while (toSend > 0) {
			if ((toSend % 100) == 0) {
				send(stdout, "println", "Remaining to send: " + toSend);
			}
			send(dest, "ping", self());
			toSend--;
		}
		send(stdout, "println", "Sent all pings, number = " + numToSend);
	}

	/**
	 * This method receives the messages sent from start. An immediate reply
	 * (i.e. a "pong" message) is sent to the caller.
	 * 
	 * @param <b>caller</b> The name of the actor pinging this actor.
	 */
	@message
	public void ping(ActorName caller) {
		numReceived++;
		if ((numReceived % 100) == 0) {
			send(stdout, "println", "Messages received: " + numReceived);
		}
		send(caller, "pong");
	}

	/**
	 * This method catches replies from the pinged actor.
	 */
	@message
	public void pong() {
		numPongs++;
		if ((numPongs % 100) == 0) {
			send(stdout, "println", "Pongs received: " + numPongs);
		}
		if (numPongs == numToSend) {
			send(stdout, "println", "Received all pongs");
			numPongs = 0;
			numToSend = 0;
		}
	}
}
