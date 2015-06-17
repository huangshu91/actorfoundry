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

import java.awt.Color;

import javax.swing.JLabel;

import osl.manager.Actor;
import osl.manager.annotations.message;

public class ColorActor extends Actor {

	private static final long serialVersionUID = 4359213904269282850L;

	private JLabel label;

	@message
	public void init(JLabel label) {
		this.label = label;
	}

	@message
	public void showColors(Object[] l) {
		for (Object o : l) {
			label.setBackground(Color.getHSBColor(
					(float) ((o.hashCode() + 0.0) / 100.0), (float) ((o
							.hashCode() + 0.0) / 180.0),
					(float) ((o.hashCode() + 0.0) / 256.0)));
		}
	}

	@message
	public void showColorOne(Object o) {
		label.setBackground(Color.getHSBColor(
				(float) ((o.hashCode() + 0.0) / 100.0),
				(float) ((o.hashCode() + 0.0) / 180.0),
				(float) ((o.hashCode() + 0.0) / 256.0)));
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
