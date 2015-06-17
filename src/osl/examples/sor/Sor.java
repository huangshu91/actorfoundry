package osl.examples.sor;

import osl.util.constraints.*;
import osl.manager.*;

public class Sor extends osl.manager.Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8664260829851384165L;
	private int pos;
	private int x;
	private int y;
	private double value = 0;
	private int color;
	private int n = 0;
	private osl.manager.ActorName[] sorActors;
	private int[] neighbors;
	private osl.manager.ActorName sorRunner;
	private int nx = 0;
	private int ny = 0;
	private Boolean peer;

	public Sor(Integer p, Double v, Integer c, Integer nx, Integer ny,
			Double omega, osl.manager.ActorName sorRunner, Boolean peer) {
		this.pos = p;
		this.x = p / ny;
		this.y = p % ny;
		this.value = v;
		this.color = c;
		this.omega_over_four = omega * 0.25;
		this.one_minus_omega = 1.0 - omega;
		this.nx = nx;
		this.ny = ny;
		this.sorRunner = sorRunner;
		this.peer = peer;

		if (this.x > 0 && this.x < nx - 1 && this.y > 0 && this.y < ny - 1) {
			neighbors = new int[4];
			neighbors[0] = calPos(x, y + 1);
			neighbors[1] = calPos(x + 1, y);
			neighbors[2] = calPos(x, y - 1);
			neighbors[3] = calPos(x - 1, y);
		} else if ((this.x == 0 || this.x == (nx - 1))
				&& (this.y == 0 || this.y == (ny - 1))) {
			neighbors = new int[2];
			neighbors[0] = x == 0 ? calPos(x + 1, y) : calPos(x - 1, y);
			neighbors[1] = y == 0 ? calPos(x, y + 1) : calPos(x, y - 1);
		} else if ((this.x == 0 || this.x == (nx - 1))
				|| (this.y == 0 || this.y == (ny - 1))) {
			neighbors = new int[3];
			if (x == 0 || x == nx - 1) {
				neighbors[0] = x == 0 ? calPos(x + 1, y) : calPos(x - 1, y);
				neighbors[1] = calPos(x, y + 1);
				neighbors[2] = calPos(x, y - 1);
			} else {
				neighbors[0] = y == 0 ? calPos(x, y + 1) : calPos(x, y - 1);
				neighbors[1] = calPos(x + 1, y);
				neighbors[2] = calPos(x - 1, y);
			}
		}
	}

	private int calPos(int x, int y) {
		return x * ny + y;
	}

	private int iter = 0;
	private int maxIter;
	private int msgRcv = 0;

	@osl.manager.annotations.message
	public Integer getColor() {
		return color;
	}

	private boolean started = false;

	@Disable(messageName = "goValue")
	public Boolean disableGoValue(Double v) {
		// System.out.println("dddddddddddddddddd");
		return !started;
	}

	@osl.manager.annotations.message
	public void start(Integer mi, osl.manager.ActorName[] sactors)
			throws osl.manager.RemoteCodeException {
		this.sorActors = sactors;
		// for (int i = 0; i < sactors.length; i++) {
		// System.out.println("+++ "+sactors[i]);
		// }
		maxIter = mi;
		// System.out.println("maxIter: "+maxIter);

		if (color == 1) {
			for (int i = 0; i < neighbors.length; i++) {
				// System.out.println(pos +" (" +x +"," +y +";" +(peer ?"p" :"s"
				// ) +"): " +"> " +neighbors[i ] +","
				// +call(sorActors[neighbors[i ] ], "getColor") ) ;
				// System.out.println(sorActors[neighbors[i]]);
				send(sorActors[neighbors[i]], "goValue", this.value);
			}
			iter++;
			msgRcv++;
			// System.out.println("red");
		} else {
			// System.out.println("black");
		}
		started = true;
		gt1 = System.currentTimeMillis();
	}

	long gt1 = 0, gt2 = 0, acGt = 0;

	private double omega_over_four;
	private double one_minus_omega;
	private int receivedVals = 0;
	private int sum = 0;

	@osl.manager.annotations.message
	public void goValue(Double v) {
		msgRcv++;
		if (iter < maxIter) {
			receivedVals++;
			// System.out.println("(" +x +"," +y +";" +(peer ?"p" :"s" ) +")"
			// +"r:" +sx +"," +sy +";" +(speer ?"p" :"s" ) ) ;
			sum += v;
			if (receivedVals == neighbors.length) {
				this.value = omega_over_four * sum + one_minus_omega
						* this.value;
				// System.out.println(x +"," +y +";" +this.color +";"
				// +receivedVals +";" +(peer ?"p" :"s" ) ) ;
				sum = 0;
				receivedVals = 0;
				for (int i = 0; i < neighbors.length; i++) {
					send(sorActors[neighbors[i]], "goValue", this.value);
				}
				iter++;
				gt2 = System.currentTimeMillis();
				// System.out.println("("+x+","+y+") "+"gt: "+(gt2-gt1));
				acGt += gt2 - gt1;
				gt1 = System.currentTimeMillis();
			}
			if (iter == maxIter) {
				send(sorRunner, "result", x, y, this.value, new Integer(msgRcv));
				// System.out.println("accum gt: "+(acGt));
			}
		}
		// else {
		// System.out.println("++++ "+iter);
		// }
	}

	@osl.manager.annotations.message
	public void asynchException(ActorRequest cause, Exception e) {
		// Default behavior is to print the exception to the log.
		e.printStackTrace();
	}

}
