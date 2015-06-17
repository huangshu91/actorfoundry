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

import osl.examples.benchmarks.Chameneos.Colour;
import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>
 * @author Amin Shali <shali1@cs.uiuc.edu>
 *
 */
public class Broker extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5021339002556170557L;

	public static final Chameneos.Colour[][] groups = new Chameneos.Colour[][] {
			{ Chameneos.Colour.blue, Chameneos.Colour.red,
					Chameneos.Colour.yellow },
			{ Chameneos.Colour.blue, Chameneos.Colour.red,
					Chameneos.Colour.yellow, Chameneos.Colour.red,
					Chameneos.Colour.yellow, Chameneos.Colour.blue,
					Chameneos.Colour.red, Chameneos.Colour.yellow,
					Chameneos.Colour.red, Chameneos.Colour.blue } };

	int totalRendezvous;
	ActorName firstHooker;
	Chameneos.Colour firstColor;

	private Integer tr;

	private static void printColours() {
		printColours(Colour.blue, Colour.blue);
		printColours(Colour.blue, Colour.red);
		printColours(Colour.blue, Colour.yellow);
		printColours(Colour.red, Colour.blue);
		printColours(Colour.red, Colour.red);
		printColours(Colour.red, Colour.yellow);
		printColours(Colour.yellow, Colour.blue);
		printColours(Colour.yellow, Colour.red);
		printColours(Colour.yellow, Colour.yellow);
	}

	private static void printColours(Colour c1, Colour c2) {
		System.out.println(c1 + " + " + c2 + " -> "
				+ Chameneos.doCompliment(c1, c2));
	}

	@message
	public void boot(Integer total) throws RemoteCodeException {
		tr = total;
		totalRendezvous = total;
		printColours();
		System.out.println();
		ActorName[] actors = new ActorName[groups[0].length];
		creatures = actors;
		totalCreatures = groups[0].length;
		totalStoppedCreatures = 0;
		for (int i = 0; i < groups[0].length; i++) {
			System.out.print(" " + groups[0][i]);
			actors[i] = create(Chameneos.class, self(), groups[0][i]);
			sendByRef(actors[i], "start");
		}
		System.out.println();

	}

	@message
	public void secondRound(Integer total) throws RemoteCodeException {
		totalRendezvous = total;
		ActorName[] actors2 = new ActorName[groups[1].length];
		creatures = actors2;
		totalCreatures = groups[1].length;
		totalStoppedCreatures = 0;
		for (int i = 0; i < groups[1].length; i++) {
			System.out.print(" " + groups[1][i]);
			actors2[i] = create(Chameneos.class, self(), groups[1][i]);
			sendByRef(actors2[i], "start");
		}
		System.out.println();
	}

	private int totalCreatures;
	private int totalStoppedCreatures;
	private int round = 1;

	private ActorName[] creatures;

	int tt = 0;

	@message
	public void hook(ActorName other, Chameneos.Colour c)
			throws RemoteCodeException {
		if (totalRendezvous == 0) {
			// sendByRef(other, "stop");
			totalStoppedCreatures++;
			if (totalCreatures == totalStoppedCreatures) {
				// int tt = 0;
				for (ActorName creature : creatures) {
					System.out.println(call(creature, "to_String"));
					tt += (Integer) call(creature, "getCount");
				}
				System.out.println(Chameneos.getNumber(tt));
				System.out.println();
				if (round == 1) {
					round = 2;
					tt = 0;
					send(self(), "secondRound", tr);
				}
			}
			return;
		}

		if (firstHooker == null) {
			firstHooker = other;
			firstColor = c;
		} else {
			sendByRef(firstHooker, "hook", other, c);
			sendByRef(other, "hook", firstHooker, firstColor);
			firstHooker = null;
			totalRendezvous--;
		}
	}

}
