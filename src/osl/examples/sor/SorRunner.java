package osl.examples.sor;
import osl.manager.*;
import osl.service.yp.*;

public class SorRunner extends osl.manager.Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7199749794231524181L;
	private int n = -1 ;
	private osl.manager.ActorName [] sorActors = null ;
	private int s = 0 ;
	private int part;
	
	long gt1, gt2;
	
	@osl.manager.annotations.message
	public void boot(Integer  n, String node) throws osl.manager.RemoteCodeException, osl.service.ServiceNotFoundException, osl.service.ServiceException {
		gt1 = System.currentTimeMillis();
		int pos = -1 ;
		int c = 0 ;
		this.n  = n ;
		s  = SorUtil.DATA_SIZES[n ] ;
		part  = s  /2 ;
	
		sorActors  = new osl.manager.ActorName[s  *(part +1 ) ] ;
		osl.manager.ActorName [] myBorder = new osl.manager.ActorName[s ] ;
		
		long t1, t2;
		t1 =System.currentTimeMillis() ;
		Double [][] randoms = SorUtil.randomMatrix(s , s ) ;
		t2 =System.currentTimeMillis() ;
//		System.out.println("matrix creation time: " +(t2 -t1 ) ) ;
		t1 =System.currentTimeMillis() ;

		for (int i = 0  ; i   < s  ; i ++) { // rows
			c   = i  %2  ;   
			for (int j = 0  ; j   < part  ; j ++) { // columns
				pos   = i  *(part +1 )  +j  ;
				c   = 1  -c  ;
				sorActors[pos ]  = create(Sor.class , pos  , randoms[i ][j ]  , c  , s ,  part +1 , SorUtil.OMEGA  , self() , Boolean.FALSE ) ;
				if (j  == part -1 ) {
					myBorder[i ]  = sorActors[pos ] ;
				}
			}
		}
		t2 =System.currentTimeMillis() ;
		//System.out.println("actor creation time: " +(t2 -t1 ) ) ;
		t1 =System.currentTimeMillis() ;
/********
		for (int i = 0; i < s; i++) { // rows
			for (int j = 0; j < s; j++) { // columns
//				System.out.println(">>>>>>>> "+sorActors[pos]);
				pos = i*s+j;
				sorActors[pos]<-&start(SorUtil.JACOBI_NUM_ITER, sorActors);			
			}
		}
		t2=System.currentTimeMillis();
		System.out.println("send start msg time: "+(t2-t1));
*********/

		Double [][] partialMatrix = new Double[s ][s -part ] ;
		for (int i = 0 ; i  < s ; i ++) {
			for (int j = 0 ; j  < s -part ; j ++) {
				partialMatrix[i ][j ]  = randoms[i ][j +part ] ;
			}
		}
		
		ActorManagerName remoteManager = (ActorManagerName) invokeService(
				YP.name, "ypLookupRemoteManager", node);
		ActorName sorPeer = create(remoteManager, SorPeer.class);
		
//		osl.manager.ActorName  sorPeer = create(SorPeer.class ) ;
		sendByRef(sorPeer, "boot", s , part , partialMatrix , new Border(myBorder ) , self() ) ;


//		for (int i = 0; i < sorActors.length; i++) {
//			System.out.println("*** "+sorActors[i]);
//		}
	}
	private java.util.Vector<Double> retValues = new java.util.Vector<Double>();
	private int returned = 0 ;
	private int totalMsgRcv = 0 ;
	@osl.manager.annotations.message
	public void result(Integer  x, Integer  y, Double  v, Integer  msgRcv) {
		totalMsgRcv +=msgRcv ;
		returned ++;
//		System.out.println(x+" * "+y);
	//	System.out.println("==== "+v);
		retValues.add(v);
		if (returned  == s  *(part)+1 ) {
			System.out.println("returned: " +returned) ;
			Double  gTotal = 0.0 ;
			for (int i=0 ; i <s ; i ++) {
            	for (int j=0 ; j <part ; j ++) {
                  	gTotal  += retValues.get(i*part+j);
                }
			}
            gTotal  += retValues.get(s  *(part));
//			System.out.println("after returned") ;
			SorUtil.jgfValidate(gTotal , n ) ;
			System.out.println("total time: "+(System.currentTimeMillis()-gt1));
		//	System.out.println("totalMsgRcv: " +totalMsgRcv ) ;    
 		}
	}
	@osl.manager.annotations.message
	public void border(Border  b) throws osl.manager.RemoteCodeException {
//		System.out.println("}}}}}}}}}}}}}}}}}} borderActors.length: " +b.borderActors.length ) ;
		for (int i = 0 ; i  < s ; i ++) {
			this.sorActors[(i +1 ) *(part +1 ) -1 ]  = b.borderActors[i ] ;
		}
		long t1 =System.currentTimeMillis()  ;
		int pos;
		
//		System.out.println(sb);
		for (int i = 0  ; i   < s  ; i ++) { // rows
			for (int j = 0  ; j   < part  ; j ++) { // columns
				pos   = i  *(part +1 )  +j  ;
//				System.out.println(">>>>>>>> "+sorActors[pos]);
				sendByRef(sorActors[pos ], "start", SorUtil.JACOBI_NUM_ITER , sorActors ) ;
			}
		}
		long t2 =System.currentTimeMillis()  ;
//		System.out.println("send start msg time: "  +(t2  -t1  )  )  ;
	}
	@osl.manager.annotations.message
	public void asynchException(ActorRequest cause, Exception e) {
		// Default behavior is to print the exception to the log.
		e.printStackTrace();
	}


}


/*		ActorManagerName remoteManager = (ActorManagerName) invokeService(
				YP.name, "ypLookupRemoteManager", node);*/
/*		ActorName sorPeer = create(remoteManager, SorPeer.class);*/

