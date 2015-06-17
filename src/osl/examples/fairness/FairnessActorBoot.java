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
package osl.examples.fairness;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class creates an actor which is used to boot the fairthreads example.
 * The <em>boot</em> method of this actor expects a single integer argument
 * which specifies the number of <em>FairthreadActor<em>s to be created.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/10/05 15:47:32 $)
 * @see FairnessActor
 */

public class FairnessActorBoot extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = -1951863218013767093L;

	/**
	 * Every actor class which may serve as a boot actor must define a method
	 * called "boot". This method is called by an <em>ActorManager</em> which
	 * starts the system running. For this example, the boot method creates
	 * <em>numThreads</em> instances of the <em>FairthreadActor</em> and sends
	 * each the <em>start</em> message.
	 * 
	 * @param <b>numThreads</b> The number of <em>FairthreadActor</em>s to
	 *        create. This is actually a command line argument which is parsed
	 *        into an integer (hence the declaration as a <em>String</em>).
	 */
	@message
	public void boot(Integer numActors) throws RemoteCodeException {
		ActorName newActor = null;

		if (numActors <= 0)
			return;

		for (int i = 0; i < numActors; i++) {
			newActor = create(osl.examples.fairness.FairnessActor.class);
			send(newActor, "start", i);
		}
	}
}
