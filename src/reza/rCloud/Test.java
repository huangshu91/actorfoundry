 /**
  * @author Reza Shiftehfar
  * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
  */
 
package reza.rCloud;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * @author Reza Shiftehfar
 * @version $Revision: 1.0 $ ($Date: 2012/12/22 $)
 */
public class Test extends Actor {

	public Test() {
	}

	@message
	public void boot() throws RemoteCodeException{
		ActorName platform1 = create(rCloud_Hadoop_Platform.class);
		ActorName requester = create (Requester.class);		
		send(requester, "start", platform1);
	}
}
