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
package osl.examples.refmessages;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class RefMessenger extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1790141280414746974L;

	@message
	public void boot() throws RemoteCodeException {
		ActorName a2 = create(RefMessenger.class);
		StringBuffer data = new StringBuffer("Hi ");
		callByRef(a2, "relayPrint", data);
		send(stdout, "println", data.toString());
	}

	@message
	public void relayPrint(StringBuffer item) throws RemoteCodeException {
		call(stdout, "println", "Messenger relays " + item);
		item.append("there!");
	}
}
