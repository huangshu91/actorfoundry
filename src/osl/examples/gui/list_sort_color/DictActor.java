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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.annotations.message;

public class DictActor extends Actor {

	private static final long serialVersionUID = -1157423123087897965L;
	private List<String> wordList = new ArrayList<String>();

	@message
	public void init(String dictFile) {
		System.out.println("dict init.....");
		try {
			LineNumberReader lineNumberReader = new LineNumberReader(
					new InputStreamReader(new FileInputStream(
							new File(dictFile))));
			String line = lineNumberReader.readLine();
			while (line != null) {
				wordList.add(line);
				line = lineNumberReader.readLine();
			}
			System.out.println("done loading dict!");
			lineNumberReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@message
	public void getMatchedWords(String q, ActorName client) {
		List<String> retVal = new ArrayList<String>();
		q = q.trim();
		if (q != null && !q.equals("")) {
			for (String is : wordList) {
				if (is.startsWith(q)) {
					retVal.add(is);
				}
			}
		}
		sendByRef(client, "matchedWords", retVal.toArray());
	}
}
