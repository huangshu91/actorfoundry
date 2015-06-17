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

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class creates an actor which is used to boot the helloworld example. The
 * boot actor creates a <em>HelloActor</em> which, upon receiving the
 * <em>hello</em> message, prints "hello" and creates a
 * <em>WorldActor</em.  The <em>WorldActor</em> is sent the <em>world</em>
 * message which tells it to print "world". I call this the actor equivalent of
 * the hello world example.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/10/05 15:47:32 $)
 * @see HelloActor
 * @see WorldActor
 */

public class HelloBoot extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = -9127746295183894232L;

	/**
	 * Every actor class which may serve as a boot actor must define a method
	 * called "boot". This method is called by an <em>ActorManager</em> which
	 * starts the system running.
	 */
	@message
	public void boot() throws RemoteCodeException {
		ActorName helloActor = create(osl.examples.helloworld.HelloActor.class);
		send(helloActor, "hello");
	}
}
