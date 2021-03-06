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
package osl.examples.helloworld;

import java.util.ArrayList;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class is the "world" portion of the helloworld example. Only one method
 * is defined for this actor:
 * 
 * <dl>
 * <dt><b>world</b>()
 * <dd>Print the string "world".
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:31:52 $)
 * @see HelloBoot
 * @see HelloActor
 */

public class WorldActor extends Actor {

	// Default constructor is ok
	
	private ActorName otherActor = null;
	
	private ArrayList<ActorName> listnames = null;
	
	private ArrayList<Integer> filler = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4183610323716216513L;

	/**
	 * This method is usually called by a <em>HelloActor</em> to print out the
	 * "world" message.
	 */
	@message
	public void world() {
		filler = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			filler.add(i);
		}
		send(stdout, "println", "World!");
	}
	
	@message
	public void world(ActorName caller) throws RemoteCodeException {
		call(stdout, "println", "World2!");
		otherActor = caller;
		send(caller, "test");
	}
	
	@message
	public void createAcq() throws RemoteCodeException {
		ActorName throwaway = create(WorldThree.class);
		//send(throwaway, "test", "::3rdgen");
		send(throwaway, "worldthree");
	}
	
	@message
	public void test(String obj) {
		send(stdout, "println", "Testing"+obj);
	}
	
	@message
	public void test() {
		send(stdout, "println", "Testing World");
	}
}
