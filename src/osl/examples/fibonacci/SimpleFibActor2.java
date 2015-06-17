package osl.examples.fibonacci;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class SimpleFibActor2 extends Actor {

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
  public SimpleFibActor2() {
  }

  /**
   *  A constructor which tells us who to send the result to when we have
   *  received all responses.  We only use this constructor for building
   *  the response portion of a fibonacci request.  I.e. this constructor 
   *  should normally only be called internally.
   */
  public SimpleFibActor2(ActorName theClient, String theMeth) {
    client = theClient;
    meth   = theMeth;
    numResponses = 0;
  }

  /**
   * The boot method is the initial method to start the Actor program
   * This boot method, receives an argument and start the code
   * @param n
   * 		the nth fibonacci number to be calculated
   */
  @message
  public void boot(Integer n) throws SecurityException, RemoteCodeException {
    ActorName root = create (SimpleFibActor2.class, self(), "finished");
    send(root, "fib", n);
  }

  // This method is called only when the final value is calculated 
  @message
  public void finished(Integer n, Integer finalVal) {
    send(stdout, "println", n+"th Fibonacci number is: " + finalVal);
  }

    
  // This method is called by a client to request a fib computation.
  @message
  public void fib(Integer val) {    
    // We already know the first 2 Fibonacci numbers:
    if (val <= 0) {
      send(client, meth, 0);
    } else if (val < 3) {
      send(client, meth, val-1);
    } else {
      // Otherwise, create a 2 child actors to calculate the sub-problems:
      try {
				ActorName newChild1 = create(SimpleFibActor2.class, self(), "result");
				send(newChild1, "fib", val - 1);
				ActorName newChild2 = create(SimpleFibActor2.class, self(), "result");
				send(newChild2, "fib", val - 2);
      } catch (RemoteCodeException e) {
	  }
    }
  }

  /**
   * This method is called from another child actor to pass a partial
   * result.  We wait until we have two such results, then sum them, and
   * return the result to our client.
   *
   * @param val
   *       The Integer partial result passed from a child fib actor.
   */
  @message
  public void result(Integer val) {
    if (numResponses == 0) {
      numResponses++;
      partialResponse = val;
    } else {
      send(client, meth, val + partialResponse);
    }
  }
}
