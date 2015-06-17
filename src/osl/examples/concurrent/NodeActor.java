package osl.examples.concurrent;

import java.util.Vector;
import osl.manager.RemoteCodeException;
import osl.manager.ActorName;

public class NodeActor extends osl.manager.Actor {

	public final static int CANDIDATE = 0;
	public final static int CAPTURED = 1;
	public final static int SURRENDERED = 2;
	public final static int ELECTED = 3;
	public final static int KILLED = -1;

	private osl.manager.ActorName id = null;
	private osl.manager.ActorName leader = null;
	private int state = KILLED;
	private Vector<ActorName> candidates = null;
	private Vector<ActorName> iterationCaptured = null;
	private int iterationPos = -1;
	private Vector<ActorName> down = null;

	@osl.manager.annotations.message
	public void init(Vector<ActorName> candidates) throws RemoteCodeException {
		id = self();
		iterationPos = candidates.indexOf(id);
		if (iterationPos >= 0) {
			System.out.println(id + ": I am a candidate");
			state = CANDIDATE;
		}
		this.candidates = candidates;
		iterationCaptured = new Vector<ActorName>(candidates.size());
		down = new Vector<ActorName>(candidates.size());
	}

	@osl.manager.annotations.message
	public void start() throws RemoteCodeException {
		if (candidates.contains(id))
			broadcast("capture");
	}

	@osl.manager.annotations.message
	public void capture(osl.manager.ActorName candidate, Integer pos,
			Vector<ActorName> captured) throws RemoteCodeException {
		if (state == KILLED)
			return;
		if (down.contains(candidate))
			down.remove(candidate);
		switch (state) {
		case CANDIDATE:
			// / NewData = nodeup(node(Candidate),Data),
			if (captured.size() > iterationCaptured.size()
					|| (captured.size() == iterationCaptured.size() && pos > iterationPos)) {
				// we lose
				send(candidate, "accept", id, iterationCaptured);
				System.out.println(id + ": I am captured by " + candidate);
				state = CAPTURED;
				leader = candidate;
			} else
				;
			break;
		case CAPTURED:
			// / {noreply,{captured,nodeup(node(Candidate),Data)}};
			break;
		case SURRENDERED:
			// / nodeup(node(Candidate),Data),
			break;
		case ELECTED:
			// / nodeup(node(Candidate),Data),
			send(candidate, "elect", id);
		}
	}

	@osl.manager.annotations.message
	public void elect(osl.manager.ActorName candidate)
			throws RemoteCodeException {
		if (state == KILLED)
			return;
		if (state == ELECTED) {
			System.err.println("Bug found!!!!!!!!");
			System.err.println(id + " and " + candidate
					+ " both are leader at the same time..");
			System.exit(0);
		}
		if (down.contains(candidate))
			down.remove(candidate);
		leader = candidate;
		System.out.println(id + ": I have surrendered to " + candidate);
		state = SURRENDERED;
		iterationCaptured = new Vector<ActorName>(candidates.size());
		iterationPos = candidates.indexOf(id);
	}

	@osl.manager.annotations.message
	public void accept(ActorName candidate, Vector<ActorName> captured)
			throws RemoteCodeException {
		// / ~= nodeup
		if (state == KILLED)
			return;
		if (down.contains(candidate))
			down.remove(candidate);
		switch (state) {
		case CANDIDATE:
			// / NewData = nodeup(node(Candidate),Data),
			// Inherit all captured
			iterationCaptured.add(candidate);
			for (int i = 0; i < captured.size(); i++) {
				if (!iterationCaptured.contains(captured.get(i)))
					iterationCaptured.add(captured.get(i));
			}
			// check for majority
			checkMajority();
			break;

		case CAPTURED:
			// bail off
			send(leader, "accept", candidate, captured);
			// / {noreply,{captured,nodeup(node(Candidate),Data)}};
			break;
		case SURRENDERED:
			// / nodeup(node(Candidate),Data),
			break;
		case ELECTED:
			// / nodeup(node(Candidate),Data),
			send(candidate, "elect", id);
		}
	}

	@osl.manager.annotations.message
	public void kill() throws RemoteCodeException {
		System.out.println(id + ": I am killed");
		state = KILLED;
		broadcast("down");
	}

	@osl.manager.annotations.message
	public void down(osl.manager.ActorName candidate)
			throws RemoteCodeException {
		if (state == KILLED)
			return;
		if (!down.contains(candidate))
			down.add(candidate);
		if (leader == candidate)
			leader = null;
		// / NewData = nodedown(Ref,Candidate,Data),

		if (state == CANDIDATE) {
			checkMajority();
		} else if (leader == null
				&& (state == SURRENDERED || state == CAPTURED)) {
			broadcast("capture");
			checkMajority();
		}

	}

	private void checkMajority() {
		int numCaptured = iterationCaptured.size() + 1;
		int numCandidates = candidates.size();
		int numDown = down.size();
		if ((numCaptured > numCandidates / 2)
				|| (numCaptured + numDown == numCandidates)) {
			System.out.println(id + ": I am the leader");
			state = ELECTED;
			leader = id;
			broadcast("elect");
		} else {
			state = CANDIDATE;
			System.out.println(id + ": I am a candidate");
		}
	}

	private void broadcast(String msg) {
		if (msg.equals("elect") || msg.equals("down")) {
			for (int i = 0; i < candidates.size(); i++) {
				osl.manager.ActorName proc = (osl.manager.ActorName) candidates
						.get(i);
				if (!proc.equals(id))
					send(proc, msg, id);
			}
		}
		if (msg.equals("capture")) {
			for (int i = 0; i < candidates.size(); i++) {
				osl.manager.ActorName proc = (osl.manager.ActorName) candidates
						.get(i);
				if (!proc.equals(id))
					send(proc, msg, id, iterationPos, iterationCaptured);
			}
		}
	}
}