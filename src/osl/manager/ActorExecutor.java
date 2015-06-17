package osl.manager;

import java.io.Serializable;

import kilim.pausable;
import osl.util.LSCQueue;

public class ActorExecutor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7134041447182808258L;
	public static final Integer DISABLED_FLAG = new Integer(0);
	protected LSCQueue<Object> mailQueue;

	public ActorExecutor(Actor actor, LSCQueue<Object> q) {
		this.mailQueue = q;
	}

	@pausable
	public Object execute(ActorMsgRequest nextMsg) throws Exception {
		// if we reach here that means we didn't find the matching method in any
		// child class
		// return __process(msgName);
		throw new NoSuchMethodException("No message matching \""
				+ nextMsg.method + "\" in this actor.");
		// return new MessageProcessResult("", false);
	}

	/*
	 * private Object __process(String msgName) throws Exception { throw new
	 * NoSuchMethodException("No method matching \"" + msgName +
	 * "\" in this object"); }
	 */

}
