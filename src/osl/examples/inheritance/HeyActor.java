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
package osl.examples.inheritance;

import osl.examples.helloworld.HelloActor;
import osl.examples.helloworld.WorldActor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class HeyActor extends HelloActor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3882497044730054053L;

	@message
	public void hey() throws RemoteCodeException {
		ActorName other = null;
		call(stdout, "print", "Hey ");
		other = create(WorldActor.class);
		send(other, "world");
	}

}
