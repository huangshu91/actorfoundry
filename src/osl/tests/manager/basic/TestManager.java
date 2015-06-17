package osl.tests.manager.basic;

import osl.handler.RequestHandler;
import osl.manager.ActorCreateRequest;
import osl.manager.ActorManager;
import osl.manager.ActorMsgRequest;
import osl.manager.ActorName;
import osl.manager.basic.BasicActorManager;
import osl.nameservice.simple.DefaultNameService;
import osl.scheduler.Scheduler;
import osl.scheduler.basic.BasicScheduler;
import osl.transport.TransportLayer;
import osl.transport.udp.UDPTransportLayer;

/**
 * A simple test of the basic actor manager implementation using the HelloWorld
 * example.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/08/12 04:27:24 $)
 */

public class TestManager implements Runnable {
	Scheduler theScheduler = null;
	String classType = null;
	String method = null;
	Object[] args = null;
	String host = null;

	// Call this to start the example running
	// Command line args:
	// 1. The string naming the class of actor to create
	// 2. The string naming the method to invoke (usually "boot")
	// 3-n. The arguments to pass to the method invoked
	//
	// Only works with actors that have a valid default constructor.
	public static void main(String[] argv) {
		try {

			if (argv.length < 3)
				throw new RuntimeException(
						"Args: hostname classType method [methodArgs]");

			TestManager foo = new TestManager();
			foo.theScheduler = new BasicScheduler();
			foo.host = argv[0];
			foo.classType = argv[1];
			foo.method = argv[2];

			foo.args = new Object[argv.length - 3];
			for (int i = 0; i < argv.length - 3; i++)
				foo.args[i] = argv[i + 3];

			foo.theScheduler.schedulerInitialize();

			// Finally, create an instance to run the test battery
			foo.theScheduler.scheduleThread(new Thread(foo));
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	// Call this to start the modules and send a request
	public void run() {
		try {

			// Create the needed modules
			TransportLayer theLayer = new UDPTransportLayer();
			DefaultNameService theNS = new DefaultNameService();
			RequestHandler theHandler = new RequestHandler();
			ActorManager theManager = new BasicActorManager();

			// Now initialize the modules
			((UDPTransportLayer) theLayer).transportInitialize(theScheduler,
					host);
			theNS.nsInitialize(theScheduler, theLayer);
			theHandler.handlerInitialize(theScheduler, theLayer, theNS);
			theManager.managerInitialize(theScheduler, theHandler);

			// Ok, now submit a request to create a HelloBoot actor
			ActorCreateRequest createReq = new ActorCreateRequest(null, Class
					.forName(classType), Class
					.forName("osl.manager.basic.BasicActorImpl"),
					new Object[0], null);
			ActorName newAct = theManager.managerCreate(createReq, null);

			// Good, now submit a message request and that should be it.
			ActorMsgRequest newMsg = new ActorMsgRequest(null, newAct, method,
					args, false);
			theManager.managerDeliver(newMsg);
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}
	}
}
