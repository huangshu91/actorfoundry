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

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JList;

import osl.manager.Actor;
import osl.manager.annotations.message;

public class MaxWordsActor extends Actor {

	private static final long serialVersionUID = -37592767506038512L;

	private JList list;

	@message
	public void init(JList list) {
		this.list = list;
	}

	@message
	public void showMaxWords(Object[] l) {
		/*
		 * List<Object> copy = new ArrayList<Object>(); for(Object o : l) {
		 * copy.add(o); } Collections.sort(copy, new SReverseC());
		 * Vector<Object> v = new Vector<Object>(10); int i = 0; for (Object o :
		 * copy.toArray()) { v.addElement(o); i++; if (i == 10) { break; } }
		 * list.setListData(v);
		 */

		Arrays.sort(l, new SReverseC());
		Vector<Object> v = new Vector<Object>(10);
		int i = 0;
		for (Object o : l) {
			v.addElement(o);
			i++;
			if (i == 10) {
				break;
			}
		}
		list.setListData(v);

	}

	class SReverseC implements Comparator<Object> {

		@Override
		public int compare(Object o1, Object o2) {
			if (o1.toString().length() < o2.toString().length())
				return 1;
			if (o1.toString().length() > o2.toString().length())
				return -1;
			return 0;
		}

	}
}
