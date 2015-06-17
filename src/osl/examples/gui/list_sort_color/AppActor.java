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

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class AppActor extends Actor implements CaretListener {

	private static final long serialVersionUID = -4342027846140409745L;

	private AppFrame appFrame;

	private ActorName dictActorName;

	private ActorName hasherActorName;

	private ActorName listActorName;

	private ActorName maxWordsActorName;

	@message
	public void init(String dictFile) throws RemoteCodeException {
		appFrame = new AppFrame(this);
		appFrame.setVisible(true);
		dictActorName = create(DictActor.class);
		sendByRef(dictActorName, "init", dictFile);
		hasherActorName = create(ColorActor.class);
		sendByRef(hasherActorName, "init", appFrame.jLabel1);
		listActorName = create(ListActor.class);
		sendByRef(listActorName, "init", appFrame.jList1);
		maxWordsActorName = create(MaxWordsActor.class);
		sendByRef(maxWordsActorName, "init", appFrame.jList2);
	}

	/**
	 * The message received from Dict Actor containing all the words matched
	 * 
	 * @param l
	 */
	@message
	public void matchedWords(Object[] l) {
		send(listActorName, "populateList", l);
		int i = 0;
		for (Object o : l) {
			send(hasherActorName, "showColorOne", o.toString());
			i++;
			if (i == 5) {
				break;
			}
		}
		send(maxWordsActorName, "showMaxWords", l);
	}

	@Override
	public void caretUpdate(CaretEvent e) {
		String t = appFrame.jTextField1.getText().trim();
		if (!t.equals(lastText)) {
			lastText = t;
			sendByRef(dictActorName, "getMatchedWords", t, self());
		}
	}

	private String lastText = "";
}
