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
package osl.examples.rpcping;

import osl.examples.tester.Id;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class represents a simple ping actor. Two methods are defined for this
 * actor:
 * 
 * <dl>
 * <dt><b>start</b>(<em>ActorName</em>)
 * <dd>This method is called by a <em>PingBoot</em> actor to pass the address of
 * the other ping actor to this instance.
 * <dt><b>ping</b>(<em>ActorName</em>, <em>String</em>)
 * <dd>This method is invoked from one ping actor on the other. Upon receiving
 * the message, the string is printed out and the receiving actor returns a
 * string to the sender (as a reply to an RPC call).
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/07/13 02:01:48 $)
 * @see PingBoot
 * @author James Waldby added Id.stamp refs, July 1999
 */

public class PingActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1238008039780660659L;
	// Save the address of the other ping actor
	ActorName otherPinger;

	// The default constructor is fine for this class

	/**
	 * This method is called by an instance of <em>PingBoot</em> to get the
	 * example rolling.
	 * 
	 * @param <b>other</b> The name of the other <em>PingActor</em>.
	 */
	@message
	public void start(ActorName other) throws RemoteCodeException {
		Object val = null;

		send(stdout, "println", Id.stamp() + "Processing start message...");
		otherPinger = other;

		val = call(otherPinger, "ping", self(), Id.stamp() + "called from "
				+ self());

		send(stdout, "println", Id.stamp() + "finished, return value: " + val);
	}

	/**
	 * This method is called to ask me (the actor) if I'm alive. When I get a
	 * ping message, I reply by returning a string. The string is automatically
	 * returned to the caller if I was called via an RPC mechanism.
	 * <p>
	 * 
	 * @param <b>caller</b> The name of the actor pinging this actor.
	 * @param <b>msg</b> A message sent from the calling actor (used for
	 *        debugging).
	 */
	@message
	public Object ping(ActorName caller, String msg) {
		send(stdout, "println", Id.stamp() + "Received ping (" + msg
				+ ") from " + caller + "...");
		send(stdout, "println", Id.stamp() + "pausing before sending reply");

		int i = 0;
		while (i < 10000000)
			i++;

		send(stdout, "println", Id.stamp() + "Sent reply");
		return Id.stamp() + self().toString() + " is alive";
	}
}
