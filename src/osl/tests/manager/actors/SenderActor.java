// All this actor does is continually send messages to another target
// actor.  Useful for testing migration.

package osl.tests.manager.actors;

import osl.manager.Actor;
import osl.manager.ActorName;

public class SenderActor extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531321286845494280L;

	// "method" should be a one argument method which takes an Integer
	// giving the sequence number of the message being sent.
	public void sendTo(ActorName target, String method, Integer numToSend,
			Integer delayInMS) throws Exception {
		for (int i = 0; i < numToSend.intValue(); i++) {
			send(target, method, i);
			Thread.sleep(delayInMS.intValue());
		}
	}
}
