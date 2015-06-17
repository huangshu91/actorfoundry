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
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This actor is used to catch exceptions thrown by the <em>ThrowActor</em>
 * class. We use this class for testing/demonstrating exception handling in the
 * foundry. This class is also the boot class for this example.
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1999/07/13 02:01:46 $)
 * @see ThrowActor
 * 
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 * 
 */

public class CatchActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 943290841178767745L;
	ActorName target = null;
	Long theCause = null;

	// The default constructor is fine

	/**
	 * This method should be used to start the example.
	 */
	@message
	public void boot() {

		// The outer try-catch block catches arbitrary Java errors.

		// The first attempt to create will cause an exception because
		// there is no default constructor. Once we receive that
		// exception we proceed to create the actor normally.
		try {
			target = create(osl.examples.exception.ThrowActor.class);
		} catch (RemoteCodeException e) {
			// Create errors usually cause RemoteCodeExceptions with a
			// nested exception giving the actual error. The nested
			// exception in this case will be a NoSuchMethodException.
			// Print it out and then create the actor again (correctly this
			// time).
			try {
				call(stdout, "println", "Caught create exception: " + e.detail);
				target = create(osl.examples.exception.ThrowActor.class, "foo");
			} catch (RemoteCodeException e1) {
				send(stdout, "println", "boot method failed, bailing: " + e1);
			}
		}

		// Now trigger an asynchronous exception
		send(target, "methodAsynch");

		// And save the request in "theCause"
		theCause = getLastRequestID();
	}

	/** Catch the asynchronous exception. */
	@message
	public void asynchException(ActorRequest cause, Exception e) {
		Throwable inner = null;

		// The outer try-catch block catches arbitrary Java errors.
		try {
			// The first thing we catch here is the asynchronous exception.
			// The foundry passes down the exception by wrapping it in a
			// RemoteCodeException. The inner exception will actually be a
			// InvocationTargetException. We know all this for this
			// example. Normally, however, you should check this before
			// casting.
			if (e instanceof RemoteCodeException) {
				inner = ((RemoteCodeException) e).detail;
			}
			// inner = ((InvocationTargetException) inner).getTargetException();
			if (cause.ID == theCause && cause.originator == self()) {
				call(stdout, "println", "Received asynch msg exception: "
						+ inner);

				// Ok, now generate a "synchronous" exception
				call(target, "methodSynch");
			}
		} catch (RemoteCodeException g) {
			inner = g.detail;
			try {
				call(stdout, "println", "Received synch msg exception: "
						+ inner);
			} catch (RemoteCodeException e1) {
				send(stdout, "println",
						"asynchException method failed, bailing: " + e1);
			}
			// If we get something else then bail out.
		}
	}
}
