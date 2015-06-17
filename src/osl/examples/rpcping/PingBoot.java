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

import osl.manager.Actor;
import osl.manager.ActorManagerName;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;
import osl.service.ServiceException;
import osl.service.ServiceNotFoundException;
import osl.service.yp.YP;

/**
 * This class creates an actor which is used to boot the ping example. In
 * particular, the ping example can be run as follows:<br>
 * <br>
 * 
 * java ActorManager -bootstrap osl.examples.rpcping.PingBoot<br>
 * <br>
 * 
 * This is perhaps the simplest example of an actor program. The boot actor
 * simply creates two actors, one of which "pings" the other. Upon receiving a
 * ping, an PingActor replies to the sender. Unlike the other ping examples, the
 * sending actor uses a "call" (RPC mechanism) to perform the send and the
 * receiver returns a string (which is automatically returned to the caller).
 * <p>
 * 
 * @author Mark Astley
 * @version 0.2 (10/30/97)
 * @see PingActor
 */

public class PingBoot extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = 4372919819985344670L;

	/**
	 * This version allows one of the actors to be created remotely. This
	 * requires the YP service to be present.
	 */
	@message
	public void boot(String host) {
		try {
			ActorManagerName remoteName = (ActorManagerName) invokeService(
					YP.name, "ypLookupRemoteManager", host);
			ActorName pinger1 = create(osl.examples.rpcping.PingActor.class);
			ActorName pinger2 = create(remoteName,
					osl.examples.rpcping.PingActor.class);
			send(pinger1, "start", pinger2);
		} catch (ServiceNotFoundException e) {
			System.err.println("Error: cant find YP service: " + e);
		} catch (ServiceException e) {
			System.err.println("Error invoking YP service: " + e);
		} catch (RemoteCodeException e) {
			System.err.println("Error: create failed: " + e);
		}
	}

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
		ActorName pinger1 = create(osl.examples.rpcping.PingActor.class);
		ActorName pinger2 = create(osl.examples.rpcping.PingActor.class);

		send(pinger1, "start", pinger2);
	}
}
