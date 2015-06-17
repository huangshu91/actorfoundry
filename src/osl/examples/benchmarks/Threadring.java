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
package osl.examples.benchmarks;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 *
 */
public class Threadring extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021339002556170555L;

	public static final int NUM_ACTORS = 503;

	int id;
	ActorName nextActor;

	public Threadring() {
		this(0);
	}

	public Threadring(Integer id) {
		this(id, null);
	}

	public Threadring(Integer id, ActorName next) {
		this.id = id.intValue();
		this.nextActor = next;
	}

	@message
	public void boot(Integer passes) throws RemoteCodeException {
		ActorName[] actors = new ActorName[NUM_ACTORS];

		actors[NUM_ACTORS - 1] = create(Threadring.class, NUM_ACTORS);
		for (int i = NUM_ACTORS - 2; i >= 0; i--)
			actors[i] = create(Threadring.class, i + 1, actors[i + 1]);
		call(actors[NUM_ACTORS - 1], "setNextActor", actors[0]);

		sendByRef(actors[0], "passToken", passes);
	}

	@message
	public void setNextActor(ActorName next) {
		this.nextActor = next;
	}

	@message
	public void passToken(Integer passes) {
		int intPasses = passes.intValue();
		if (intPasses == 0) {
			sendByRef(stdout, "println", id + "");
		} else {
			sendByRef(nextActor, "passToken", passes.intValue() - 1);
		}
	}
}
