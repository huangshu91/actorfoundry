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
package osl.examples.ping;

import osl.examples.tester.Id;
import osl.manager.Actor;
import osl.manager.ActorName;
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
 * the message, the string is printed out and the receiving actor invokes the
 * <em>alive</em> method on the sending actor.
 * <dt><b>alive</b>(<em>String</em>)
 * <dd>This method is invoked from a pinged actor to let the calling actor know
 * that it is alive. When this method is entered it simply prints out the
 * string.
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/07/13 02:01:47 $)
 * @see PingBoot
 */

public class PingActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7450218040676741811L;
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
	public void start(ActorName other) {
		otherPinger = other;
		send(otherPinger, "ping", self(), Id.stamp() + "called from " + self());
	}

	/**
	 * This method is called to ask the actor if it is alive. Upon receiving the
	 * method, this actor replies using the <em>alive</em> method on the caller.
	 * 
	 * @param <b>caller</b> The name of the actor pinging this actor.
	 * @param <b>msg</b> A message sent from the calling actor (used for
	 *        debugging).
	 */
	@message
	public void ping(ActorName caller, String msg) {
		send(stdout, "println", Id.stamp() + "Received ping (" + msg
				+ ") from " + caller + "...");
		send(caller, "alive", Id.stamp() + self().toString() + " is alive");
	}

	/**
	 * This method is called to alert an actor that the other actor it just
	 * pinged is indeed alive.
	 * 
	 * @param <b>reply</b> A string returned by the pinged actor (used for
	 *        debugging).
	 */
	@message
	public void alive(String reply) {
		send(stdout, "println", Id.stamp() + "Received " + reply
				+ " from pinged actor");
	}
}
