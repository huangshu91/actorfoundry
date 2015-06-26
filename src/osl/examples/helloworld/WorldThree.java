package osl.examples.helloworld;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class WorldThree extends Actor{

	@message
	public void worldthree() throws RemoteCodeException {
		send(stdout, "println", "worldthree!");
	}
	
}
