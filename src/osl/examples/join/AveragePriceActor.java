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

import java.util.Random;

import osl.manager.Actor;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class AveragePriceActor extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6639052097532463634L;

	/**
	 * This method is called by a client to request a fib computation.
	 * 
	 * @param <b>val</b> An <em>Integer</em> indicating which fibonacci number
	 *        should be computed.
	 */
	@message
	public void boot(Integer val) {
		try {
			Object result = join(AddCustomer.class, msg(
					create(AveragePriceActor.class), "price"), msg(
					create(AveragePriceActor.class), "price"), msg(
					create(AveragePriceActor.class), "price"), msg(
					create(AveragePriceActor.class), "price"));
			System.out.println("Average price: " + (Integer) result * 1.0 / 4);
		} catch (RemoteCodeException e) {
			e.printStackTrace();
		}
	}

	@message
	public Integer price() {
		int value = 1 + new Random().nextInt(100);
		System.out.println("My price: " + value);
		return value;
	}
}
