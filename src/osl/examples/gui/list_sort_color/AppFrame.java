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

import javax.swing.event.CaretListener;

/**
 * AppFrame.java
 * @author Amin Shali
 *
 * Created on November 2, 2008
 */
public class AppFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4872025999157017270L;

	/** Creates new form AppFrame */
	public AppFrame(CaretListener caretListener) {
		initComponents();
		jTextField1.addCaretListener(caretListener);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		jTextField1 = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList2 = new javax.swing.JList();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jScrollPane1.setViewportView(jList1);

		jLabel1.setBackground(new java.awt.Color(102, 102, 255));
		jLabel1.setForeground(new java.awt.Color(255, 153, 153));
		jLabel1.setText(" ");
		jLabel1.setOpaque(true);

		jScrollPane2.setViewportView(jList2);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																157,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																114,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jLabel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																166,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jScrollPane2,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																166,
																Short.MAX_VALUE))
										.addGap(29, 29, 29)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				39,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				200,
																				Short.MAX_VALUE))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jTextField1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				220,
																				Short.MAX_VALUE)))
										.addGap(24, 24, 24)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	javax.swing.JLabel jLabel1;
	javax.swing.JList jList1;
	javax.swing.JList jList2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	javax.swing.JTextField jTextField1;
	// End of variables declaration//GEN-END:variables
}
