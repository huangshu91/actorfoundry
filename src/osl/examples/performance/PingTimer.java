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

/**
 * This pinger is useful for timing message latency. Basically, we send pings
 * one at a time and wait until the reply is received before sending the next
 * ping. If you divide the total time by the number of pings and pongs sent,
 * you'll get an estimate of total message latency.
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1999/03/06 21:18:35 $)
 */

public class PingTimer extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1609755424986592668L;
	int numToSend;
	int numPongs = -1;
	long startTime;
	long endTime;
	ActorName target;

	// The default constructor is fine for this class

	/**
	 * This method is called by an external actor with the number of messages to
	 * be blasted and a target ping actor to send them to.
	 * 
	 * @param <b>num</b> The number of ping messages to send.
	 */
	@message
	public void start(Integer num) {
		numToSend = num.intValue();
		try {
			target = create(osl.examples.performance.PingTimer.class);
		} catch (RemoteCodeException e) {
		}

		numPongs = -1;

		startTime = System.currentTimeMillis();
		try {
			call(stdout, "println", "Starting ping blast, number = " + num);
		} catch (RemoteCodeException e) {
		}
		pong();
	}

	/**
	 * This method receives the messages sent from start. An immediate reply
	 * (i.e. a "pong" message) is sent to the caller.
	 * 
	 * @param <b>caller</b> The name of the actor pinging this actor.
	 */
	@message
	public void ping(ActorName caller) {
		send(caller, "pong");
	}

	/**
	 * This method catches replies from the pinged actor.
	 */
	@message
	public void pong() {
		numPongs++;
		if (numPongs == numToSend) {
			endTime = System.currentTimeMillis();
			String result = "Test Complete:\n";
			result += " Start Time: " + startTime + "\n";
			result += " End Time  : " + endTime + "\n";
			result += " Ms/Msg    : "
					+ ((endTime - startTime) / ((float) numToSend * 2));
			try {
				call(stdout, "println", result);
			} catch (RemoteCodeException e) {
			}
		} else
			send(target, "ping", self());
	}
}
