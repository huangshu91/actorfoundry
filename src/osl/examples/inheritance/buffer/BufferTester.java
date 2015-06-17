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
package osl.examples.inheritance.buffer;

import java.util.Random;
import java.util.Vector;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class BufferTester extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7471480863517155167L;

	@message
	public void test() {
		try {
			ActorName unboundedBuffer = create(UnboundedBuffer.class);
			Vector<Integer> unbInts = new Vector<Integer>();
			for (int i = 0; i < 20; i++) {
				Integer k = new Random().nextInt(100) + 1;
				unbInts.add(k);
				send(unboundedBuffer, "put", k);
			}
			for (int i = 0; i < 20; i++) {
				Object res = call(unboundedBuffer, "get");
				unbInts.remove(res);
			}
			if (unbInts.size() == 0) {
				System.out.println("UnboundedBuffer matched.");
			} else {
				System.err.println("Testing UnboundedBuffer FAILED!");
			}
			// one another call. should be null!
			Object anotherRes = call(unboundedBuffer, "get");
			if (anotherRes != null) {
				System.err
						.println("Testing additional call to UnboundedBuffer FAILED!");
			}

			Vector<Integer> bInts = new Vector<Integer>();
			ActorName boundedBuffer = create(BoundedBuffer.class, 10);
			call(boundedBuffer, "init");
			for (int i = 0; i < 20; i++) {
				Integer k = new Random().nextInt(100) + 1;
				send(boundedBuffer, "put", new Integer(k));
				bInts.add(k);
			}
			/*
			 * Integer bsize = (Integer) call(boundedBuffer, "size"); if (bsize
			 * == 10) { System.out.println("BoundedBuffer size OK!"); } else {
			 * System.err.println("BoundedBuffer size test FAILED!"); }
			 */
			for (int i = 0; i < 20; i++) {
				Object res = call(boundedBuffer, "get");
				bInts.remove(res);
			}
			if (unbInts.size() == 0) {
				System.out.println("BoundedBuffer matched.");
			} else {
				System.err.println("Testing BoundedBuffer FAILED!");
			}
			anotherRes = call(boundedBuffer, "get");
			if (anotherRes != null) {
				System.err
						.println("Testing additional call to BoundedBuffer FAILED!");
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (RemoteCodeException e) {
			e.printStackTrace();
		}
	}

}
