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
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class BiddingActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2802820643478683842L;

	@message
	public Integer bid() {
		int value = 1 + new Random().nextInt(100);
		System.out.println("My bid: " + value);
		return value;
	}
}
