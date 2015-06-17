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
 * Distributed under the license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.examples.remoteping;

import osl.manager.*;
import osl.service.*;
import osl.service.yp.*;
import osl.util.*;
import osl.manager.annotations.message;

/**
 * This class creates an actor which is used to boot the remoteping example.
 * This example is not much different from the original ping example except that
 * we optionally accept the name of a machine on the command line which we use
 * to create a remote <em>PingActor</em>. For example, this version of ping can
 * be run as follows:<br>
 * <br>
 * 
 * java ActorManager -bootstrap osl.examples.remoteping.PingBoot machineName<br>
 * <br>
 * 
 * Two versions of the <em>boot</em> method are provided, one expecting no
 * arguments, and the other expecting a single <em>String</em> argument naming a
 * machine. The boot actor creates two actors, one of which "pings" the other.
 * Upon receiving a ping, a PingActor replies to the sender.
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/07/13 02:01:48 $)
 * @see PingActor
 * @author James Waldby -- updates July 1999
 */
public class PingBoot extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = -2405854083973500908L;

/**
 * Create two local <em>PingActors</em> and invoke the <em>start</em> method
 * on one of them to start them pinging. Note that all publicly accessible
 * actor methods should return void. This version of boot does not take any
 * arguments.
 */
@message
public void boot() {
	ActorName pinger1 = null;
	ActorName pinger2 = null;

	try {
		pinger1 = create(osl.examples.ping.PingActor.class);
		pinger2 = create(osl.examples.ping.PingActor.class);
	} catch (RemoteCodeException e) {
		Log.println("Error: create failed: " + e);
	}

	send(pinger1, "start", pinger2);
}

	/**
	 * Create two <em>PingActors</em> and invoke the <em>start</em> method on
	 * the local one to start them pinging. Note that all publicly accessible
	 * actor methods should return void. This version of boot accepts a single
	 * argument that names the machine where one of the actors should be
	 * created.
	 */
	@message
	public void boot(String node) {
		try {
			ActorManagerName remoteName = (ActorManagerName) invokeService(
					YP.name, "ypLookupRemoteManager", node);
			ActorName pinger1 = create(osl.examples.ping.PingActor.class);
			ActorName pinger2 = create(remoteName,
					osl.examples.ping.PingActor.class);

			send(pinger1, "start", pinger2);
		} catch (ServiceNotFoundException e) {
			Log.println("Error: cant find YP service: " + e);
		} catch (ServiceException e) {
			Log.println("Error invoking YP service: " + e);
		} catch (RemoteCodeException e) {
			Log.println("Error: create failed: " + e);
		}
	}

}
