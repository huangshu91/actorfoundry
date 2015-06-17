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
package osl.examples.join;

import osl.manager.Actor;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class MinimumBidActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5695396155125012920L;

	@message
	public void boot(Integer val) {
		try {
			Object result = join(MinCustomer.class, msg(
					create(BiddingActor.class), "bid"), msg(
					create(BiddingActor.class), "bid"), msg(
					create(BiddingActor.class), "bid"), msg(
					create(BiddingActor.class), "bid"));
			System.out.println("Minimum bid: " + (Integer) result);
		} catch (RemoteCodeException e) {
			e.printStackTrace();
		}
	}
}
