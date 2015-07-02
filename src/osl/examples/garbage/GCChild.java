package osl.examples.garbage;

import java.util.ArrayList;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class GCChild extends Actor{

	private static final long serialVersionUID = 2867091899418826587L;

	public ArrayList<ActorName> localconns = new ArrayList<ActorName>();

    public ArrayList<ActorName> remoteconns = new ArrayList<ActorName>();

    public static int NUM_LOCAL = 100;
    public static int NUM_REMOTE = 5;
    public static int CHURN_RATE = 5;
    
    public static int NUM_DROP = NUM_LOCAL/GCRoot.NUM_ITERATION;
    
    //public boolean cycleOrigin = false;

    //local initialize
    @message
    public void initialize() throws RemoteCodeException {
    	//System.out.println("initialize child");
    	for (int i = 0; i < GCChild.NUM_LOCAL; i++) {
    		ActorName gcset = create(GCChild.class);
    		localconns.add(gcset);
    	}
    }
    
    //remote initialize
    @message
    public void initialize(String remote) throws RemoteCodeException {
    	for (int i = 0; i < GCChild.NUM_LOCAL; i++) {
    		//ActorName gcset = create(GCChild.class);
    		//localconns.add(gcset);
    	}
    	
    	for (int i = 0; i < GCChild.NUM_REMOTE; i++) {
    		
    	}
    }
    
    @message
    public void iterate() throws RemoteCodeException {
    	//send(stdout, "println", "iterate");
    	
    	for (int i = 0; i < GCChild.CHURN_RATE*2; i++) {
    		localconns.remove(i);
    	}
    	
    	for (int i = 0; i < GCChild.CHURN_RATE; i++) {
    		//ActorName gcset = create(GCChild.class);
    		//localconns.add(gcset);
    	}
    }
    
    @message
    public void iterate(String remote) throws RemoteCodeException {
    	
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
    public void createCycle(String remote) throws RemoteCodeException {
    	
    }
}