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

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class creates an actor which is used to boot the ping example. In
 * particular, the ping example can be run as follows:<br>
 * <br>
 * 
 * java ActorManager -bootstrap osl.examples.ping.PingBoot<br>
 * <br>
 * 
 * This is perhaps the simplest example of an actor program. The boot actor
 * simply creates two actors, one of which "pings" the other. Upon receiving a
 * ping, an PingActor replies to the sender.
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1998/10/05 15:47:33 $)
 * @see PingActor
 */

public class PingBoot extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = 7814677063317936133L;

	/**
	 * Every actor class which may serve as a boot actor must define a method
	 * called "boot". This method is called by an <em>ActorManager</em> which
	 * starts the system running. For this example, all our boot method does is
	 * create two <em>PingActors</em> and invoke the <em>start</em> method on
	 * one of them to start them pinging. Note that all publicly accessible
	 * actor method should return void.
	 */
	@message
	public void boot() throws RemoteCodeException {
		ActorName pinger1 = null;
		ActorName pinger2 = null;

		pinger1 = create(osl.examples.ping.PingActor.class);
		pinger2 = create(osl.examples.ping.PingActor.class);

		send(pinger1, "start", pinger2);
	}
}
