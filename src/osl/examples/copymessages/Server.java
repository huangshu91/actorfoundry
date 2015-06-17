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
package osl.examples.copymessages;

import osl.examples.sorting.Quicksort;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class Server extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4826180773007412279L;

	@message
	public void sortAverage(int[] list, ActorName client)
			throws RemoteCodeException {
		ActorName averager = create(Averager.class);
		ActorName sorter = create(Quicksort.class);
		sendByRef(sorter, "sort", list, 0, list.length - 1);
		Double avg = (Double) call(averager, "calculate", list, 0,
				list.length - 1, list.length);
		send(client, "result", avg);
	}

}
