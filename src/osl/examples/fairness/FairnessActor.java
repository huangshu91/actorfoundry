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
import osl.manager.annotations.message;

/**
 * This class represents an actor instantiated by the FairthreadsBoot actor. A
 * single method is defined for this actor:
 * 
 * <dl>
 * 
 * <dt><b>start</b>(<em>Integer</em>)
 * <dd>This method is called by a <em>FairthreadsBoot</em> actor and passes a
 * single <em>Integer</em> as an argument. Upon entering this method, the
 * receiving actor stays within an infinite loop and simply prints out its
 * integer argument.
 * 
 * </dl>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:31:47 $)
 * @see FairnessActorBoot
 * 
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 * 
 */

public class FairnessActor extends Actor {

	// The default constructor is fine for this class

	/**
	 * 
	 */
	private static final long serialVersionUID = 1918027794945565709L;

	/**
	 * This method is called by an instance of <em>FairthreadsBoot</em> to get
	 * the example rolling.
	 * 
	 * @param <b>myInt</b> The <em>Integer</em> to print out in our tight loop.
	 */
	@message
	public void start(Integer myInt) {
		int counter = 0;
		while (true) {
			send(stdout, "print", myInt + " ");
			counter++;
			if (counter == 100) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter = 0;
			}
		}
	}
}
