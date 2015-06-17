package osl.examples.concurrent;

import java.util.Vector;
import osl.manager.RemoteCodeException;
import osl.manager.ActorName;
import osl.manager.annotations.message;

public class Controller extends osl.manager.Actor {

	@message
	public void boot(Integer N) throws RemoteCodeException {
		Vector<ActorName> procs = new Vector<ActorName>(N);
		ActorName next = null;
		int i;
		for (i = 0; i < N; i++) {
			next = create(NodeActor.class);
			procs.add(next);
		}

		for (i = 0; i < N; i++) {
			next = (osl.manager.ActorName) procs.get(i);
			call(next, "init", procs);
		}

		for (i = 0; i < N; i++) {
			next = (osl.manager.ActorName) procs.get(i);
			send(next, "start");
		}

		// Random killing and reviving. Things get interesting here.
		// ActorName simulator = create(Controller.class);
		// send(simulator, "simulate1", procs);
	}

	@message
	public void simulate1(Vector<ActorName> procs) throws RemoteCodeException {
		ActorName next = null;

		for (int i = 0; i < procs.size(); i++) {
			next = (ActorName) procs.get(i);
			send(next, "kill");
		}
		for (int i = 0; i < procs.size(); i++) {
			next = (ActorName) procs.get(i);
			call(next, "init", procs);
			send(next, "start");
		}

	}

	@message
	public void simulate2(Vector<ActorName> procs) throws RemoteCodeException {
		ActorName next = (ActorName) procs.get(1);
		send(next, "kill");

	}

}