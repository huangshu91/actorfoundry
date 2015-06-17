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

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
import osl.manager.annotations.message;

public class ProductCustomer {
	private int partialResponse = 1;

	@message
	public Integer process(Integer resp) {
		partialResponse *= resp;
		return partialResponse;
	}
}
