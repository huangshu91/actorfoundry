package osl.manager;

import java.io.Serializable;

/**
 * This class defines the context, or surroundings, of an actor implementation
 * (i.e. an instance of <em>ActorImpl</em>). An instance of this class is passed
 * within an <em>ActorCreateRequest</em>. Currently, a context contaings the
 * following information:
 * <p>
 * 
 * <ul>
 * <li>The <em>ActorName</em>s of the "stdout", "stdin" and "stderr" actors that
 * handle the external output, input and error streams for this actor.
 * 
 * </ul>
 * <p>
 * 
 * Actor implementations utilize this information in an implementation-dependent
 * fashion.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/06/12 21:32:10 $)
 * @see ActorImpl
 * @see Actor
 */

public class ActorContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8085962637341105197L;

	/**
	 * The name of the "stdout" output stream actor for the new implementation.
	 * The "stdout" actor is always an instance of <em>StreamOutputActor</em>.
	 * 
	 * @see osl.manager.StreamOutputActor
	 */
	public ActorName stdout;

	/**
	 * The name of the "stdin" input stream actor for the new implementation.
	 * The "stdin" actor is always an instance of <em>StreamInputActor</em>.
	 * 
	 * @see osl.manager.StreamInputActor
	 */
	public ActorName stdin;

	/**
	 * The name of the "stderr" output stream actor for the new implementation.
	 * The "stderr" actor is always an instance of <em>StreamOutputActor</em>.
	 * 
	 * @see osl.manager.StreamOutputActor
	 */
	public ActorName stderr;
}
