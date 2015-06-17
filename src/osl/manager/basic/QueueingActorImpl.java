package osl.manager.basic;

import kilim.pausable;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorImpl;
import osl.manager.ActorManager;
import osl.manager.ActorManagerName;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.ActorRequest;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;
import osl.util.Queue;

/**
 * Ok, this class is strictly in the dirty tricks category. All this
 * implementation does is queue incoming messages. It serves no other purpose
 * and should never be run. We use this class as a placeholder for actors that
 * are in the process of migration. Instances of this class hold messages
 * received while another actor is being migrated. After the actor is migrated,
 * the queued messages are forwarded and this actor is nuked.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/07/18 18:59:48 $)
 * @see BasicActorManager
 */

class QueueingActorImpl extends ActorImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7082137798042456361L;
	/**
	 * A queue to hold messages which will later be forwarded to a migrated
	 * actor.
	 */
	Queue<ActorMsgRequest> msgQueue = null;

	/**
	 * The default constructor. Just create a queue to hold incoming messages
	 * which will later be forwarded to a migrated actor.
	 */
	QueueingActorImpl() {
		msgQueue = new Queue<ActorMsgRequest>();
	}

	// ///////////////////////////////////////////////////////////////////////
	// Manager Interface Functions:
	//
	// These methods are intended to be invoked by extensions of
	// the ActorManager abstract class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Stub implementation of initialize. This method should never be called.
	 * Throw a nasty exception if this method IS called so we can debug errors
	 * more easily.
	 */
	protected void actorInitialize(ActorManager ourMgr, ActorName you,
			ActorCreateRequest req) {
		throw new RuntimeException(
				"ERROR: actorInitialize of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Stub implementation of asych exception. This method should never be
	 * called. Throw a nasty exception if this method IS called so we can debug
	 * errors more easily.
	 */
	protected void actorAsynchException(ActorRequest cause, Exception e) {
		throw new RuntimeException(
				"ERROR: actorAsynchException of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Receive a message targetted for a migrating actor. We just queue the
	 * message. It will later be forwarded by our manager.
	 */
	protected void actorDeliver(ActorMsgRequest msg) {
		msgQueue.enqueue(msg);
	}

	/**
	 * Stub implementation of post migrate rebuild. This method should never be
	 * called. Throw a nasty exception if this method IS called so we can debug
	 * errors more easily.
	 */
	protected void actorPostMigrateRebuild(ActorManager ourMgr) {
		throw new RuntimeException(
				"ERROR: actorPostMigrateRebuild of QueueingActorImpl should NEVER be called!");
	}

	// ///////////////////////////////////////////////////////////////////////
	// Actor Interface Functions:
	//
	// These methods are intended to be invoked by the Actor class.
	//
	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Stub implementation of implSend. This method should never be called.
	 * Throw a nasty exception if this method IS called so we can debug errors
	 * more easily.
	 */
	@pausable
	protected Object implCall(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"ERROR: implCall of QueueingActorImpl should NEVER be called!");
	}

	protected Object implSend(ActorName dest, String meth, Object[] args,
			boolean byCopy) {
		throw new RuntimeException(
				"ERROR: implSend of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Stub implementation of implCreate. This method should never be called.
	 * Throw a nasty exception if this method IS called so we can debug errors
	 * more easily.
	 */
	protected ActorName implCreate(ActorCreateRequest req)
			throws SecurityException {
		throw new RuntimeException(
				"ERROR: implCreate of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Stub implementation of implMigrate. This method should never be called.
	 * Throw a nasty exception if this method IS called so we can debug errors
	 * more easily.
	 */
	protected void implMigrate(ActorManagerName loc) {
		throw new RuntimeException(
				"ERROR: implMigrate of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Stub implementation of implInvokeService. This method should never be
	 * called. Throw a nasty exception if this method IS called so we can debug
	 * errors more easily.
	 */
	protected Object implInvokeService(ServiceName name, String meth,
			Object[] args) throws ServiceNotFoundException, ServiceException {
		throw new RuntimeException(
				"ERROR: implInvokeService of QueueingActorImpl should NEVER be called!");
	}

	/**
	 * Stub implementation of implDestroy. This method should never be called.
	 * Throw a nasty exception if this method IS called soo we can debug errors
	 * more easily.
	 */
	protected void implDestroy(String reason) {
		throw new RuntimeException(
				"ERROR: implDestroy of QueueingActorImpl should NEVER be called!");
	}

}
