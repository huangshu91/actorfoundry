package osl.examples.network;

import osl.manager.Actor;
import osl.manager.ActorManagerName;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;
import osl.service.ServiceException;
import osl.service.ServiceNotFoundException;
import osl.service.yp.YP;

public class Net extends Actor {

	
	
	@message
	public void boot(String host) {
		try {
			ActorManagerName remoteName = (ActorManagerName) invokeService(
					YP.name, "ypLookupRemoteManager", host);
			
			for (int i = 0; i < 80; i++) {
				ActorName pinger1 = create(Net.class);
				ActorName pinger2 = create(remoteName,
						Net.class);
				send(pinger1, "start", pinger2);
			}
		} catch (ServiceNotFoundException e) {
			System.err.println("Error: cant find YP service: " + e);
		} catch (ServiceException e) {
			System.err.println("Error invoking YP service: " + e);
		} catch (RemoteCodeException e) {
			System.err.println("Error: create failed: " + e);
		}
	}

	@message
	public void start(ActorName other) throws RemoteCodeException {
		Long val = null;

		val = (Long) call(other, "ping", self(), new Long(System
				.currentTimeMillis()));
		System.out.println(System.currentTimeMillis() - val);
	}

	/**
	 * This method is called to ask me (the actor) if I'm alive. When I get a
	 * ping message, I reply by returning a string. The string is automatically
	 * returned to the caller if I was called via an RPC mechanism.
	 * <p>
	 * 
	 * @param <b>caller</b> The name of the actor pinging this actor.
	 * @param <b>msg</b> A message sent from the calling actor (used for
	 *        debugging).
	 */

	@message
	public Object ping(ActorName caller, Long time) {
		System.out.println(System.currentTimeMillis() - time);
		send(caller, "start", self());
		return System.currentTimeMillis();
	}

}
