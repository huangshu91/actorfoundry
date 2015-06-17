package osl.manager;

import java.io.Serializable;

import osl.nameservice.Name;

/**
 * Instances of this class are used to represent actor manager "names". An actor
 * manager name is required for manager specific services such as remote
 * creation or migration. We have standardized the representation of actor
 * manager names as different implementations of the <em>ActorManager</em> class
 * require a common representation of one another in order to interact. Manager
 * implementations which require more extensive functionality can extend this
 * class as appropriate. Note that actor manager names must be serializable so
 * that they may be passed through the transport layer.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:12 $)
 * @see ActorManager
 * @see osl.nameservice.Name
 */

public class ActorManagerName implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1530727219967568214L;

	/**
	 * This field indicates the "type" of this actor manager. This information
	 * may be useful to other managers for determining what services are
	 * accessible at a remote manager.
	 */
	public int managerType;

	/**
	 * The nameservice name associated with this manager. This field should be
	 * used when sending messages to this actor using the request handler
	 * classes.
	 */
	public Name managerName;

	// /////////////////////////////////////////////////////////////
	// Known Manager Types
	// These are the "types" of all of the actor manager
	// implementations provided as subpackages of osl.manager.
	// /////////////////////////////////////////////////////////////
	/**
	 * A constant identifying the "basic" implementation of the manager package.
	 * A basic actor manager only supports those abstract services defined in
	 * the <em>ActorManager</em> class and the remote interface defined by the
	 * <em>RemoteActorManage</em> interface.
	 */
	public static final int MGR_BASIC = 0;

	/**
	 * The default constructor. Note that this constructor does not create a
	 * legal name.
	 */
	public ActorManagerName() {
		this(-1, null);
	}

	/**
	 * The usual constructor used to create manager names. This constructor will
	 * usually only be invoked by actor managers when they are instantiated.
	 */
	public ActorManagerName(int type, Name name) {
		managerType = type;
		managerName = name;
	}

	/**
	 * Equality testing for manager names. Careful if you override this.
	 */
	public boolean equals(Object other) {
		return ((other instanceof ActorManagerName)
				&& (((ActorManagerName) other).managerType == managerType) && (((ActorManagerName) other).managerName
				.equals(managerName)));
	}

	/**
	 * Hashing for manager names. Careful if you override this.
	 */
	public int hashCode() {
		return managerType + managerName.hashCode();
	}

	/**
	 * A useful method for debugging.
	 */
	public String toString() {
		return "ActorManagerName: managerType=" + managerType + " managerName="
				+ managerName;
	}

}
