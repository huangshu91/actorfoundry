package osl.manager;

import osl.handler.RequestClient;
import osl.service.Service;
import osl.service.ServiceException;
import osl.service.ServiceName;
import osl.service.ServiceNotFoundException;

/**
 * This interface defines the externally visible portion of the
 * <em>ActorManager</em> class. In particular, actor manager implementations are
 * expected to implement this interface in order to provide access to external
 * services (off-node or otherwise). Typically, an instance of this interface is
 * used to open one or more <em>RequestHandler</em> sessions, which provide the
 * means for interacting with an actor manager.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/07/18 18:59:46 $)
 * @see ActorManager
 * @see osl.handler.RequestHandler
 */

public interface RemoteActorManager extends RequestClient {

	/**
	 * This method is called to request that a new local actor be created and
	 * managed by the target actor. Both the ID properties and the site field of
	 * the request are ignored and the actor is always created locally. Unlike
	 * <em>actorCreate</em>, the caller may optionally specify the name of the
	 * new actor. This is done to allow more efficient implementations of remote
	 * creation where a name is immediately returned to a local actor although
	 * the creation itself is taking place asynchronously at a remote manager.
	 * Normally this method is called using the RPC features of the request
	 * handler. This is done so that the name of the new actor may be returned
	 * and so that the caller may determine when it is legal to send the new
	 * actor messages. However, this method MAY be called asynchronously (e.g.
	 * if the caller already knows the name of the new actor because the
	 * <em>newName</em> field has been specified) in which case other means will
	 * need to be used to determine when it is legal to send the actor messages.
	 * 
	 * @param <b>request</b> An <em>ActorCreateRequest</em> structure describing
	 *        the new actor to be created. The ID and site fields are ignored
	 *        and the new actor is always created locally.
	 * @param <b>newName</b> An optional argument indicating the desired
	 *        <em>ActorName</em> of the new actor. If null then a new actor name
	 *        is generated, otherwise an attempt is made to use the provided
	 *        name.
	 * @return The <em>ActorName</em> of the newly created actor. This actor is
	 *         a legal target for messages once the caller has received its
	 *         name.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if this manager decides to refuse the creation
	 *                request.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown if the constructor of the new actor throws an
	 *                exception.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if the specified value for <em>newName</em> is
	 *                non-NULL but does not correspond to a legal actor name.
	 * @exception java.lang.IllegalAccessException
	 *                Thrown if the requested class of the new actor does not
	 *                inherit from <em>Actor</em>.
	 */
	public ActorName managerCreate(ActorCreateRequest request, ActorName newName)
			throws RemoteRequestRefusedException, RemoteCodeException,
			IllegalTargetException, IllegalAccessException;

	/**
	 * This method is called to indicate that a new message should be delivered
	 * to a local actor. This method is normally called asynchronously using the
	 * exception handling mechanism of the request layer. Because messages are
	 * processed by actors asynchronously, a separate method is used to return
	 * exceptions resulting from actor message processing.
	 * <p>
	 * 
	 * @param <b>del</b> The <em>ActorMsg</em> structure to deliver to a local
	 *        actor.
	 * @exception osl.manager.IllegalTargetException
	 *                Thrown if either the specified target actor name is
	 *                malformed, or the target is not managed locally.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if the local manager decides to refuse the remote
	 *                request.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown for any other exception encountered while
	 *                attempting to deliver the message.
	 */
	public void managerDeliver(ActorMsgRequest del)
			throws IllegalTargetException, RemoteRequestRefusedException,
			RemoteCodeException;

	/**
	 * This method is called to pass an actor migration request. An actor
	 * migration structure contains all the information necessary to reassemble
	 * an actor on the local manager including the serialized form of the actor
	 * and the actor's name. Normally, this method is called using RPC. Upon
	 * successful completion (i.e. no thrown exceptions), the caller may assume
	 * that the migrated actor is now running on the local manager. Otherwise,
	 * the migration request was not satisfied and the caller should assume that
	 * the actor did not migrate.
	 * 
	 * @param <b>mig</b> The <em>ActorMigrationStructure</em> giving the
	 *        properties of the actor to migrate.
	 * @exception osl.manager.RemoteRequestRefusedException
	 *                Thrown if the local manager decides to refuse the
	 *                migration request.
	 * @exception osl.manager.RemoteCodeException
	 *                Thrown if an error is encountered while attempting to
	 *                restart the migrated actor on the local manager.
	 */
	public void managerMigrate(ActorMigrationStructure mig)
			throws RemoteRequestRefusedException, RemoteCodeException;

	/**
	 * Called externally to register a new node service. The service provider is
	 * responsible for providing a unique service name. Any existing service
	 * with the same name is removed.
	 * 
	 * @param <b>sName</b> The <em>ServiceName</em> of the new service.
	 * @param <b>S</b> A reference to the new <em>Service</em>.
	 */
	public void managerRegisterService(ServiceName sName, Service S);

	/**
	 * Called externally to remove a node service. An exception is thrown if the
	 * named service does not exist.
	 * 
	 * @param <b>sName</b> The name of the service to remove.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no service with the given name is associated
	 *                with this manager.
	 */
	public void managerRemoveService(ServiceName sName)
			throws ServiceNotFoundException;

	/**
	 * Called by other foundry modules (or remote managers) to invoke a local
	 * service. This equivalent to the <em>actorInvokeService</em> method except
	 * that no check is made to ensure that the caller is an actor.
	 * 
	 * @param <b>serviceName</b> The <em>ServiceName</em> of the service to
	 *        invoke.
	 * @param <b>serviceArgs</b> An <em>Object</em> which represents the sole
	 *        argument to pass to the invocation function of the service.
	 * @return An <em>Object</em> representing the value returned by the service
	 *         invocation.
	 * @exception osl.service.ServiceNotFoundException
	 *                Thrown if no instance of the named service can be found on
	 *                this node.
	 * @exception osl.service.ServiceException
	 *                Thrown if the service throws an exception while processing
	 *                the request.
	 */
	public Object managerInvokeService(ServiceName serviceName, String meth,
			Object[] serviceArgs) throws ServiceNotFoundException,
			ServiceException;

	/**
	 * Returns the name of this actor manager.
	 */
	public ActorManagerName managerGetName();
}
