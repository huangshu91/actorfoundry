package reza.fibonacci;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class represents another implementation of the fibonacci example actor. 
 * It has the same methods but here the main actor creates two new worker actors
 * to perform the sub-calculations.
 * 
 * @author Rajesh Karmani <rkumar8@cs.uiuc.edu>, 
 *         Reza Shiftehfar <reza@shiftehfar.org>
 * @version 2.0 ( Date: 2012/12/05 )
 */
public class FibonacciActor2 extends Actor {

    //Number of responses we have received, if we are a worker fib actor
	private int numResponses;


    //The first response we have received from a child fib actor.  
    //   We save this value until we receive the second response.
    //   We then send our client the sum of both values.
	private int partialResponse;

    //The name of the receiving client (if we are a worker fib actor)
	private ActorName client;

    //The method to invoke on the receiving client (if we are a worker fib actor)
	private String meth;

    //The default constructor 
	public FibonacciActor2() {
	}

   /**
    *  A constructor which tells us who to send the result when we have
    *  received all responses.  We only use this constructor for building
    *  the response portion of a fibonacci request.  I.e. this constructor 
    *  should normally only be called internally.
    */
	public FibonacciActor2(ActorName theClient, String theMeth) {
		client = theClient;
		meth = theMeth;
		numResponses = 0;
	}

	private long startTime = 0;
	private int requestedFibNumber = -1;  //default value to show unacceptable
		
   /**
    * The boot() method is the initial method to start an Actor program
    * This boot method, receives an argument and start the code
    *
    * @param nthFib
    * 		the nth fibonacci number to be calculated
    */	
	@message
	public void boot(Integer nthFib) throws SecurityException, 
											RemoteCodeException {
		startTime = System.nanoTime();
		requestedFibNumber = nthFib;
		ActorName root = create(FibonacciActor2.class, self(), "finished");
		sendByRef(root, "fib", nthFib);
	}

    // This method is called only when the final value is calculated 
	@message
	public void finished(Integer finalVal) {		
		send(stdout, "println", requestedFibNumber + "th Fibonacci number is: " 
								+ finalVal + " calculated in " 
								+ ((System.nanoTime() - startTime) / 1000000) + "ms");

	}

	/**
	 * This method is called by a client to request a fib computation.
	 * 
	 * @param currentFib
	 *             indicating which fibonacci number should be computed in this call
	 * @param client
	 *              the name of the client who is waiting for a
	 * @param meth
	 *       name of the method to be invoked on the client when finishied
	 */
	@message
	public void fib(Integer currentFib) throws RemoteCodeException {

        // We already know the first 2 Fibonacci numbers:
		if (currentFib == 0) {
			sendByRef(client, meth, 0);
		} else if (currentFib < 3) {
			sendByRef(client, meth, currentFib-1);
		} else {
			// Otherwise, create two children to handle the two sub-problems and
			//act yourself as the manager to add up their results
			ActorName newChild1 = create(FibonacciActor2.class, self(), "result");
			sendByRef(newChild1, "fib", currentFib - 1);
			ActorName newChild2 = create(FibonacciActor2.class, self(), "result");
			sendByRef(newChild2, "fib", currentFib - 2);
		}
	}

	/**
	 * This method is called from another child actor to pass a partial result.
	 * We wait until we have two such results, sum them, and return the result
	 * to our client.
	 * 
	 * @param currentFib
	 *            the partial result passed from a child fib actor.
	 */
	@message
	public void result(Integer currentFib) {
		if (numResponses == 0) {
			numResponses++;
			partialResponse = currentFib;
		} else {
			// Send the answer
			sendByRef(client, meth, currentFib + partialResponse);
		}
	}
}
