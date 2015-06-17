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
package osl.examples.migrate;

import osl.manager.Actor;
import osl.manager.ActorManagerName;
import osl.manager.ActorRequest;
import osl.manager.annotations.message;
import osl.service.yp.YP;

/**
 * This class creates an actor which tests the migration support of the foundry.
 * The boot method of this actor should be called with the string name of the
 * machine to which this actor should migrate. The string name is used to
 * consult the YP service for the manager name of a remote node.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1999/07/13 02:01:47 $)
 */

public class MigrateActor extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = 2890319045889719558L;

	/**
	 * The boot method.
	 * 
	 * @param <b>where</b> The <em>String</em> name of the machine that this
	 *        actor should migrate to.
	 */
	@message
	public void boot(String where) {
		ActorManagerName whereTo = null;

		// Catch arbitrary Java errors
		try {

			// Print where we are migrating to
			call(stdout, "println", "Migrating to: " + where);
			System.out.println("REDUNDANT: Migrating to: " + where);

			try {
				whereTo = (ActorManagerName) invokeService(YP.name,
						"ypLookupRemoteManager", where);
			} catch (Exception e) {
				send(stdout, "println", "Error invoking YP service: " + e);
				return;
			}

			migrate(whereTo);

			// Send ourselves a message so we have something to do when we
			// get there
			send(self(), "alive", "made it");
		} catch (Exception f) {
			send(stdout, "println", "Unexpected error, bailing: " + f);
		}
	}

	/**
	 * This method lets the actor tell us that it migrated successfully.
	 */
	@message
	public void alive(String msg) {
		send(stdout, "println", msg);
		System.out.println("REDUNDANT: " + msg);
	}

	/** This will catch any migration exception */
	@message
	public void asynchException(ActorRequest cause, Exception e) {
		send(stdout, "println", "Migrate exception: " + e);
	}

}
