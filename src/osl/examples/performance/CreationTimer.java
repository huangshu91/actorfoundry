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
import osl.manager.ActorManagerName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This actor is used to time actor creation. The actor creates instances of
 * itself for the test. The following ashell session may be used to run the test
 * (this example times the creation of 100 actors):
 * 
 * $ ashell > create osl.examples.performance.CreationTimer SV_1 > send SV_1
 * start null 100 > Starting actor creation test ...results...
 * 
 * The results include the total time of the test as well as the average
 * creation time for each actor (in milliseconds/create).
 * 
 * @author Mark Astley
 * @version $Revision: 1.2 $ ($Date: 1999/07/13 02:01:47 $)
 */

public class CreationTimer extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2894472148523735685L;

	// The default constructor is fine for this class

	/**
	 * This method is called to time the creation of multiple actors. Timing may
	 * be performed on local or remote creation.
	 * 
	 * @param <b>loc</b> The <em>ActorManagerName</em> where the new actors
	 *        should be created. Set <b>loc</b>=<em>null</em> to time local
	 *        creation of actors. Set <b>loc</b> to be the
	 *        <em>ActorManagerName</b> of a remote node to time remote
    creation.
	 * @param <b>num</b> The number of actors to create.
	 */
	@message
	public void start(ActorManagerName where, Integer numToCreate) {
		long startTime = 0, endTime = 0;

		try {
			call(stdout, "println", "Starting actor creation test");
			if (where == null) {
				startTime = System.currentTimeMillis();
				for (int i = numToCreate; i > 0; i--)
					create(CreationTimer.class);
				endTime = System.currentTimeMillis();
			} else {
				startTime = System.currentTimeMillis();
				for (int i = numToCreate; i > 0; i--)
					create(where, CreationTimer.class);
				endTime = System.currentTimeMillis();
			}

			String result = "Test Complete:\n";
			result += " Start Time: " + startTime + "\n";
			result += " End Time  : " + endTime + "\n";
			result += " Ms/Create    : "
					+ ((endTime - startTime) / ((float) numToCreate));
			call(stdout, "println", result);

		} catch (Exception e) {
			System.err.println("Error in CreationTimer: " + e);
		}
	}

	/**
	 * This method is called to time the creation of multiple actors. Timing may
	 * be performed on local or remote creation.
	 * 
	 * @param <b>num</b> The number of actors to create.
	 */
	@message
	public void start(Integer numToCreate) throws RemoteCodeException {
		start(null, numToCreate);
	}
}
