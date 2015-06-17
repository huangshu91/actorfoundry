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
package osl.examples.gui.list_sort_color;

import javax.swing.JList;

import osl.manager.Actor;
import osl.manager.annotations.message;

public class ListActor extends Actor {

	private static final long serialVersionUID = 3564063198753494550L;

	private JList list;

	@message
	public void init(JList list) {
		this.list = list;
	}

	@message
	public void populateList(Object[] l) {
		list.setListData(l);
	}

}
