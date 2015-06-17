//  Copyright Notice.
// 
//  Helper - thread-spawning helper for UDPLayer
//  Copyright (C) 1998  Thomas Heide Clausen <voop@cs.auc.dk>
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Library General Public
//  License as published by the Free Software Foundation;
//  version 2 of the License.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Library General Public License for more details.
//
//  You should have received a copy of the GNU Library General Public
//  License along with this library; if not, write to the
//  Free Software Foundation, Inc., 59 Temple Place - Suite 330,
//  Boston, MA  02111-1307, USA./  
// *************************************************************************

package osl.nameservice.subnet.network.UDP;

/**
 * This class is used by the UDPLayer when starting auxilary threads for
 * receiving and sending messages as well as performing callbacks.
 * 
 * @author Thomas Heide Clausen <voop@cs.auc.dk>
 * @version 2.0b
 */

class Helper implements Runnable {

	/**
	 * This variable holds the reference to the UDP-layer, which we are helping.
	 */
	private UDPLayer master;

	/**
	 * This variable holds information about which task the thread will perform.
	 */
	private int task;

	/**
	 * These definitions are..
	 */
	public static final int RECEIVE = 0;
	public static final int SEND = 1;
	public static final int CALLBACK = 2;

	/**
	 * The default and only costructor specifies which UDP-layer to invoke the
	 * method in, and which task the thread will perform.
	 * 
	 * @param the
	 *            UDP-layer
	 * @param the
	 *            task for the thread.
	 */
	public Helper(UDPLayer u, int task) {
		this.master = u;
		this.task = task;
	}

	public void run() {
		switch (task) {
		case SEND:
			master.sendThread();
			break;

		case RECEIVE:
			master.receiveThread();
			break;

		case CALLBACK:
			master.callbackThread();
			break;
		}
	}
}
