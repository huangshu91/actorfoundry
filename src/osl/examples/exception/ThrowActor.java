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

import osl.manager.Actor;
import osl.manager.annotations.message;

/**
 * This actor is used to generate exceptions which are caught by the
 * <em>CatchActor</em> class. We use this class for testing/demonstrating
 * exception handling in the foundry.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/07/13 02:01:47 $)
 * @see CatchActor
 */

public class ThrowActor extends Actor {

	// There is no default constructor, this should throw an exception

	/**
	 * 
	 */
	private static final long serialVersionUID = 2010415472093635263L;

	/**
	 * This constructor is simply used to differentiate it from the default
	 * constructor. The argument <em>dummy</em> is not used.
	 */
	public ThrowActor(String dummy) {
	}

	/**
	 * This method just throws an exception which should be caught
	 * asynchronously by the caller.
	 */
	@message
	public void methodAsynch() {
		throw new RuntimeException("Thrown from ThrowActor.methodAsynch");
	}

	/**
	 * This method just throws an exception which should be directly returned to
	 * the caller since this method is called using RPC. If not, then this
	 * exception will be delivered asynchronously to the caller.
	 */
	@message
	public Object methodSynch() {
		throw new RuntimeException("Thrown from ThrowActor.methodSynch");
	}
}
