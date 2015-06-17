package reza.fibonacci;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

/**
 * This class represents the canonical fibonacci example actor. Two methods are
 * defined for this actor:
 * fib: 
 *      This method requests a fib computation from the caller at "client". 
 *      When the computation is complete, this actor will reply
 *      to the caller by invoking the "meth" method which must have a
 *      version which takes a single Integer argument.
 *
 * result:
 *      This method is called by a child fib actor when it has completed a
 *      portion of the fib computation. The receiving actor keeps track of the number
 *      of responses it receives and then forwards the answer to a particular caller.
 *      Note that the main fib server never actually receives a result message.
 * 
 * @author Mark Astley, Reza Shiftehfar <reza@shiftehfar.org>
 * @version 2.0 ( Date: 2012/12/05 )
 */
public class FibonacciActor extends Actor {

    //Number of responses we have received, if we are a worker fib actor
	int numResponses;

    //The first response we have received from a child fib actor.  
    //   We save this value until we receive the second response.
    //   We then send our client the sum of both values.
    private int partialResponse;

    //The name of the receiving client (if we are a worker fib actor)
    private ActorName client;

    //The method to invoke on the receiving client (if we are a worker fib actor)
    private String meth;

    //The default constructor 
	public FibonacciActor() {
	}

   /**
    *  A constructor which tells us who to send the result when we have
    *  received all responses.  We only use this constructor for building
    *  the response portion of a fibonacci request.  I.e. this constructor 
    *  should normally only be called internally.
    */
	public FibonacciActor(ActorName theClient, String theMeth) {
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
	public void boot(Integer nthFib) {
		startTime = System.nanoTime();
		requestedFibNumber = nthFib;
		send(self(), "fib", nthFib, self(), "finished");
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
	public void fib(Integer currentFib, ActorName client, String meth) {
		int theVal = currentFib.intValue();
		ActorName newChild = null;

        // We already know the first 2 Fibonacci numbers:
		if (theVal <= 0) {
			send(client, meth, 0);
		} else if (theVal < 3) {
			send(client, meth, theVal-1);
		} else {
			// Otherwise, create a child to handle the response and resubmit the
			// two sub-problems to yourself.
			try {
				newChild = create(FibonacciActor.class, client, meth);
			} catch (RemoteCodeException e) {
			}

			send(self(), "fib", theVal - 1, newChild, "result");
			send(self(), "fib", theVal - 2, newChild, "result");
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
			partialResponse = currentFib.intValue();
		} else {
			// Send the answer
			send(client, meth, currentFib.intValue() + partialResponse);
		}
	}
}
