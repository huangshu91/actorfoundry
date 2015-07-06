package osl.examples.garbage;

import java.util.ArrayList;

import osl.manager.*;
import osl.service.*;
import osl.service.yp.*;
import osl.util.*;
import osl.manager.annotations.message;


public class GCChild extends Actor{

	private static final long serialVersionUID = 2867091899418826587L;

	public ArrayList<ActorName> localconns = new ArrayList<ActorName>();

    public ArrayList<ActorName> remoteconns = new ArrayList<ActorName>();

    public static int NUM_LOCAL = 0;
    public static int NUM_REMOTE = 0;
    public static int CHURN_RATE = 5;

    public static int REM_CHURN = 1;
    
    public static int NUM_DROP = NUM_LOCAL/GCRoot.NUM_ITERATION;
    
    //public boolean cycleOrigin = false;

    //local initialize
    @message
    public void initialize() throws RemoteCodeException {
    	System.out.println("initialize child");
    	for (int i = 0; i < GCChild.NUM_LOCAL; i++) {
    		ActorName gcset = create(GCChild.class);
    		localconns.add(gcset);
    	}
    }
    
    //remote initialize
    @message
    public void initialize(ActorManagerName rem) throws RemoteCodeException {
        System.out.println("initialize remote child");
    	for (int i = 0; i < GCChild.NUM_LOCAL; i++) {
    		//ActorName gcset = create(GCChild.class);
    		//localconns.add(gcset);
    	}
    	
        try {
    	    for (int i = 0; i < GCChild.NUM_REMOTE; i++) {
    		    //ActorName remgcset = create(rem, GCChild.class);
                //remoteconns.add(remgcset);
    	    }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @message
    public void iterate() throws RemoteCodeException {
    	//send(stdout, "println", "iterate");
    	
    	for (int i = 0; i < GCChild.CHURN_RATE*2; i++) {
    		localconns.remove(i);
    	}

        for (int i = 0; i < GCChild.REM_CHURN; i++) {
            remoteconns.remove(i);
        }
    	
    	for (int i = 0; i < GCChild.CHURN_RATE; i++) {
    		ActorName gcset = create(GCChild.class);
    		localconns.add(gcset);
    	}
    }
    
    @message
    public void iterate(String remote) throws RemoteCodeException {
    	send(stdout, "println", "iterate "+remote);
        send(stdout, "println", "name: " + self());
    }
    
    //local garbage cycle
    @message
    public void createCycle() throws RemoteCodeException {
    	//cycleOrigin = true;
    	ActorName next_cycle = create(GCChild.class);
    	localconns.add(next_cycle);
    	send(next_cycle, "cycle", self());
    }
    
    @message
    public void cycle(ActorName orig) throws RemoteCodeException {
    	localconns.add(orig);
    }
    
    //remote garbage cycle
    @message
    public void createCycle(ActorManagerName rem) throws RemoteCodeException {
    	try {
            ActorName next_cycle = create(rem, GCChild.class);
            remoteconns.add(next_cycle);
            send(next_cycle, "cycle", self());

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}