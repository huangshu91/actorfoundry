// $Id: Ticker.java,v 1.1 1999/07/13 02:01:49 cvs Exp $

package osl.examples.tester;

import java.io.Serializable;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.annotations.message;

/**
 * Periodically sends a message to one client.
 * 
 * To create Ticker elements: ActorName xyz = create(Ticker.class);
 * 
 * To tell the Ticker xyz to send up to k messages to method M of client C at
 * period t milliseconds, with (optional) parameters p1 ... pj : send (xyz,
 * "setTicker", k, t, M, C, p1 ... pj)
 * 
 * @author James Waldby
 * @version $Revision: 1.1 $ ($Date: 1999/07/13 02:01:49 $)
 */

public class Ticker extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5120711445673771422L;

	private static int tickerCount = 0;

	private int id = -1;
	private int periodsLeft;
	private int cycleMillis;
	private int offsetMillis;
	private String methodName;
	private ActorName client;
	private int paramCount;
	private Serializable p1, p2;

	// ===================================================
	/** My name is ... */
	@message
	public String myId() {
		return "Ticker " + id + "  " + periodsLeft + "@" + cycleMillis + ":"
				+ offsetMillis;
	}

	// ===================================================
	/** Displays id info + text of note. */
	@message
	public void showId(String note) {
		send(stdout, "println", myId() + note);
	}

	// ===================================================
	/** Constructor with no parameters. */
	public Ticker() {
		id = tickerCount++;
	}

	// ===================================================
	/**
	 * setTicker tells the Ticker to send up to k messages to method M of client
	 * C at period t milliseconds, with (optional) parameters p1 ... pj. Note,
	 * if k is 0 or less the number of messages cycles is unlimited. j can be 0,
	 * 1, or 2 in this version.
	 * 
	 * If t is comparable to the underlying scheduler's timeslice, Ticker may
	 * operate erratically.
	 */

	@message
	public void setTicker(Integer k, Integer t, String M, ActorName C) {
		setup(k, t, M, C, null, null, 0);
	}

	@message
	public void setTicker(Integer k, Integer t, String M, ActorName C,
			Serializable p1) {
		setup(k, t, M, C, p1, null, 1);
	}

	@message
	public void setTicker(Integer k, Integer t, String M, ActorName C,
			Serializable p1, Serializable p2) {
		setup(k, t, M, C, p1, p2, 2);
	}

	// ===================================================
	@message
	public void setup(Integer k, Integer t, String M, ActorName C,
			Serializable p1, Serializable p2, Integer paramCount) {
		if (t < 0 || M == null || C == null)
			return;
		if (k <= 0)
			k = -1;
		periodsLeft = k;
		cycleMillis = t;
		offsetMillis = (int) (t - System.currentTimeMillis() % t);

		methodName = M;
		client = C;
		this.paramCount = paramCount;
		this.p1 = p1;
		this.p2 = p2;
		cycle();
	}

	// ===================================================

	@message
	public void cycle() {
		try {
			long delta = offsetMillis - System.currentTimeMillis()
					% cycleMillis;
			if (delta < 1)
				delta += cycleMillis;
			Thread.sleep(delta); // Wait until specified time
		} catch (InterruptedException e) {
		}
		if (periodsLeft > 0)
			--periodsLeft;
		if (periodsLeft != 0) {
			switch (paramCount) {
			case 1:
				send(client, methodName, p1);
				break;
			case 0:
				send(client, methodName);
				break;
			default:
				send(client, methodName, p1, p2);
			}
			send(self(), "cycle");
		}
	}
	// ===================================================
}
