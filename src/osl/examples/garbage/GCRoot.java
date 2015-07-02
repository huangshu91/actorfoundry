package osl.examples.garbage;

import java.util.ArrayList;
import java.lang.System;

import osl.manager.*;
import osl.service.*;
import osl.service.yp.*;
import osl.util.*;
import osl.manager.annotations.message;

// root actor for gc tests
public class GCRoot extends Actor{

	private static final long serialVersionUID = -8367585405344485012L;

	public ArrayList<ActorName> localconns = new ArrayList<ActorName>();

    public ArrayList<ActorName> remoteconns = new ArrayList<ActorName>();

    public String remoteAddr = "";
    
    public boolean isRemote = false;

    public static int NUM_LOCAL = 100;
    public static int NUM_REMOTE = 5;
    public static int NUM_CYCLE = 10;
    
    public static int NUM_ITERATION = 10;

    //run test with non-local actors
    @message
    public void boot(String remote) throws RemoteCodeException {
    	isRemote = true;
        //send(stdout, "println", "worldthree!");
        remoteAddr = remote;
        send(stdout, "println", "testing remote boot");
        //return;
        
        try {
			ActorManagerName remoteName = (ActorManagerName) invokeService(
			            YP.name, "ypLookupRemoteManager", remoteAddr);
			
			for (int i = 0; i < GCRoot.NUM_REMOTE; i++) {
	            ActorName remChild = create(remoteName, GCChild.class);
	            remoteconns.add(remChild);
	        }
			
			//no local for now
			
		} catch (ServiceNotFoundException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    }

    //local only tests
    @message
    public void boot() throws RemoteCodeException {
        for (int i = 0; i < GCRoot.NUM_LOCAL; i++) {
            ActorName t_one = create(GCChild.class);
            localconns.add(t_one);
            send(t_one, "initialize");
        }
        System.out.println(localconns.size()+ " :: num children one");
        //loop();
    }
    
    public void loop() throws RemoteCodeException {
    	try {
    		Thread.sleep(1000); //wait 1s
    		for (int i = 0; i < NUM_ITERATION; i++) {
        		Thread.sleep(5000); //wait 5s between each topology change
        		
        		System.out.println("iterate time: " + System.currentTimeMillis());
        		//call(stdout, "println", "iterate time: " + System.currentTimeMillis());
        		
        		//drop some local conns, send rest an iterate message
        		for (int j = 0; j < localconns.size(); j++) {
        			//send(localconns.get(j), "iterate");
        		}
        		
        		for (int j = 0; j < NUM_CYCLE; j++) {
        			ActorName cycle = create(GCChild.class);
        			//send(cycle, "createCycle");
        		}
        		
        		//drop some remote conns, send rest an iterate message
        		if (isRemote) {
        			
        		}
    		}
    		System.out.println("Done!");
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    }
    
    @message
    public void iterate() throws RemoteCodeException {
    	try {
			wait(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
