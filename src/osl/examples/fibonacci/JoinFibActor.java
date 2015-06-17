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
package osl.examples.fibonacci;

import osl.examples.join.AddCustomer;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class JoinFibActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4568298542270889002L;

	@message
	public void boot(Integer startVal) throws SecurityException,
			RemoteCodeException {
		ActorName root = create(JoinFibActor.class);
		Integer result = (Integer) callByRef(root, "fib", startVal);
		sendByRef(stdout, "println", "Fib(" + startVal + ") = " + result);
	}

	/**
	 * This method is called by a client to request a fib computation.
	 * 
	 * @param <b>val</b> An <em>Integer</em> indicating which fibonacci number
	 *        should be computed.
	 */
	@message
	public Integer fib(Integer val) throws RemoteCodeException {
		if (val == 0) {
			return 0;
		} else if (val < 3) {
			return 1;
		} else {
				ActorName newChild1 = create(JoinFibActor.class);
				ActorName newChild2 = create(JoinFibActor.class);
				Integer result = (Integer) join(AddCustomer.class, msgByRef(newChild1,
						"fib", val - 1), msgByRef(newChild2, "fib", val - 2));
				return result;
		}
	}
}
