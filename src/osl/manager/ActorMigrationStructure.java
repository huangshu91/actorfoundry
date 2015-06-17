package osl.manager;

import java.io.Serializable;

/**
 * This class defines a structure for encapsulating an actor in the process of
 * migration. This structure is passed between actor managers during a migration
 * and reassembled at the target manager.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:12 $)
 * @see ActorManager
 * @see ActorName
 */

public class ActorMigrationStructure implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7923097296664385795L;

	/**
	 * The <em>ActorManagerName</em> of the manager sending the actor to be
	 * migrated.
	 */
	public ActorManagerName sender;

	/**
	 * The <em>Actor</em> to restart after migration.
	 */
	public ActorImpl toMigrate;

	/**
	 * The most useful constructor for this class.
	 */
	public ActorMigrationStructure(ActorManagerName S, ActorImpl A) {
		sender = S;
		toMigrate = A;
	}
}
