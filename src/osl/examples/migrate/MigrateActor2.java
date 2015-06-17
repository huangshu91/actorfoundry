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
import osl.manager.ActorName;
import osl.manager.annotations.message;

/**
 * This class is the same as the MigrateActor class except that it creates a
 * separate actor for migration and then continually sends it messages during
 * the migration. If everything is working, none of the messages should be lost
 * during migration.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1999/07/13 02:01:47 $)
 */

public class MigrateActor2 extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = 4064035444974176348L;

	/**
	 * The boot method. The test completes when the actor has been migrated and
	 * all forwarded messages have been received.
	 * 
	 * @param <b>where</b> The <em>String</em> name of the machine that this
	 *        actor should migrate to.
	 * @param <b>num</b> The number of messages to forward after the migration
	 *        occurs (used to test the proper forwarding of messages).
	 */
	@message
	public void boot(String where, Integer num) {
		try {
			// Create another MigrateActor and tell it to move somewhere
			call(stdout, "println", "Creating actor migrate");
			ActorName go = create(osl.examples.migrate.MigrateActor.class);

			call(stdout, "println", "Sending boot and sendNext messages");
			send(go, "boot", where);
			send(self(), "sendNext", go, num);

		} catch (Exception e) {
			send(stderr, "println", "Error in boot method: " + e);
		}
	}

	@message
	public void sendNext(ActorName go, Integer n) {
		if (n > 0) {
			send(go, "alive", n.toString());
			send(stdout, "println", "Sent value: " + n);
			send(self(), "sendNext", go, n - 1);
		} else
			send(stdout, "println", "All messages sent, test complete");
	}
}
