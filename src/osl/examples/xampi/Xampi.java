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
package osl.examples.xampi;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Xampi extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1790141280414746974L;

	@message
	public void boot() throws RemoteCodeException {
		ActorName a2 = create(Xampi.class);
		send(a2, "relayPrint", "Hi there");
		send(a2, "relayPrintFloat", (float) 4.5);
	}

	@message
	public void relayPrint(String item) {
		send(stdout, "println", "Xampi relays " + item);
	}

	@message
	public void relayPrintFloat(Float item) {
		send(stdout, "println", "Xampi relays the value " + item);
	}
}
