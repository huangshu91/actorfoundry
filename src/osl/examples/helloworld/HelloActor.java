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

import java.util.List;
import java.util.ArrayList;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class is the "hello" portion of the helloworld example. Only one method
 * is defined for this actor:
 * 
 * <dl>
 * <dt><b>hello</b>()
 * <dd>Print the string "hello", then create a <em>WorldActor</em> and send it
 * the world message.
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/07/18 18:59:38 $)
 * @see HelloBoot
 * @see WorldActor
 */

public class HelloActor extends Actor {

	// Default constructor is ok

	private ActorName otherActor = null;
	
	public String testing = "fjekajfkejkafjekafj";
	
	private List<ActorName> actornameslist = new ArrayList<ActorName>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2090594902402952242L;

	/**
	 * This method is called externally to start the hello world example.
	 * 
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown as a wrapper for any error that occurs while
	 *                attempting to create a <tt>WorldActor</tt>.
	 */
	@message
	public void hello() throws RemoteCodeException {
		call(stdout, "println", "Hello ");
		otherActor = create(WorldActor.class);
		send(otherActor, "world");
		send(otherActor, "world", this.self());
		
		for (int i = 0; i < 2; i++) {
			ActorName worldactor = create(WorldActor.class);
			actornameslist.add(worldactor);
			send(worldactor, "createAcq");
			
			//ActorName worldthree = create(WorldThree.class);
			//actornameslist.add(worldthree);
			//send(worldthree, "worldthree");
		}
		
	}
	
	@message
	public void test() {
		send(stdout, "println", "Testing Hello");
	}

}
