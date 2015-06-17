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
package osl.examples.exception;

import osl.manager.ActorName;
import osl.manager.Actor;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class BadMessageActor extends Actor {

	private static final long serialVersionUID = 943290841178767748L;

	/**
	 * This method should be used to start the example.
	 */
	@message
	public void boot() throws RemoteCodeException {

		ActorName target = create(osl.examples.exception.CatchActor.class);
		send(target, "methodAsynchronous");
	}
}