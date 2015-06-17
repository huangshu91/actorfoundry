package osl.examples.sor;
import osl.manager.*;

public class SorPeer extends osl.manager.Actor {
	private osl.manager.ActorName [] sorActors = null  ;
	private int s = 0  ;
	private int partStart;
	private osl.manager.ActorName  sorRunner;
	@osl.manager.annotations.message
	public void boot(Integer  s, Integer  ps, Double [][] matrixPart, Border  b, osl.manager.ActorName  sr) 
			throws osl.manager.RemoteCodeException {
		int pos = -1  ;
		int c = 0  ;
		this.partStart  = ps ;
		this.s  = s ;
		this.sorRunner  = sr ;
		this.sorActors   = new osl.manager.ActorName[s  *(s -ps +1 ) ]  ;
		osl.manager.ActorName [] myBorder = new osl.manager.ActorName[s ] ;
				
		long t1, t2;
		t1  =System.currentTimeMillis()  ;
//		System.out.println("aaaaaaaaaaaaaaaaa" )  ;
		for (int i = 0 ; i  < s ; i ++) {
	//		System.out.print("^ " +b.borderActors[i ] ) ;
			this.sorActors[i *(s -ps +1 ) ]  = b.borderActors[i ] ;
		}
//		System.out.print("" ) ;
	//	System.out.println("bbbbbbbbbbbbbbb" )  ;
		for (int i = 0  ; i   < s  ; i ++) { // rows
			c   = (i +ps )  %2  ;   
			for (int j = 1  ; j   < s -ps +1  ; j ++) { // columns
				pos   = i  *(s -ps +1 )  +j  ;
				c   = 1  -c  ;
				sorActors[pos ]  = create(Sor.class , pos  , matrixPart[i ][j -1 ]  , c  , s , s -ps +1 , 
									SorUtil.OMEGA  , self() , Boolean.TRUE ) ;
				if (j  == 1 ) {
//				System.out.println("cccccccccccc") ;
					myBorder[i ]  = sorActors[pos ] ;
//				System.out.println("dddddddddddddd") ;
				}
			}
		}
				
		t2  =System.currentTimeMillis()  ;
	//	System.out.println("actor creation time: "  +(t2  -t1  )  )  ;


	//	System.out.println("My border size: " +myBorder.length )  ;
		
		sendByRef(sr, "border", new Border(myBorder ) ) ;
		
		t1  =System.currentTimeMillis()  ;
		for (int i = 0  ; i   < s  ; i ++) { // rows
			for (int j = 1  ; j   < s -ps +1  ; j ++) { // columns
//				System.out.println(">>>>>>>> "+sorActors[pos]);
				pos   = i  *(s -ps +1 )  +j ;
				sendByRef(sorActors[pos ], "start", SorUtil.JACOBI_NUM_ITER , sorActors ) ;
			}
		}
		t2  =System.currentTimeMillis()  ;
//		System.out.println("send start msg time: "  +(t2  -t1  )  )  ;
//		for (int i = 0; i < sorActors.length; i++) {
//			System.out.println("*** "+sorActors[i]);
//		}
	}
	@osl.manager.annotations.message
	public void asynchException(ActorRequest cause, Exception e) {
		// Default behavior is to print the exception to the log.
		e.printStackTrace();
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
		if (returned  == s  *(s -partStart) ) {
			System.out.println("returned: " +returned) ;
			Double  gTotal = 0.0 ;
			for (int i=0 ; i <s ; i ++) {
            	for (int j=0 ; j <s-partStart ; j ++) {
//					System.out.println(","+(i*(s-partStart)+j)) ;
                  	gTotal  += retValues.get(i*(s-partStart)+j);
                }
			}
			send(sorRunner, "result", new Integer(-1), new Integer(-1), gTotal, totalMsgRcv);
//			SorUtil.jgfValidate(gTotal , n ) ;
		//	System.out.println("totalMsgRcv: " +totalMsgRcv ) ;    
 		}
	}



}
