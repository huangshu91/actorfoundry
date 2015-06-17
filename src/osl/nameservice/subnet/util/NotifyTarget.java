//  Copyright Notice.
// 
//  NotifyTarget - string based wait/notify of threads.
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

// Originally from package voop.concurrency.threads;

package osl.nameservice.subnet.util;

import java.util.Hashtable;

/**
 * This class implements a target for wait/notify. The target is characterized
 * by the fact that only threads waiting on information about a given thing are
 * being notified. The targets are identifyed by a text string.
 * 
 * @author Thomas Heice Clausen <voop@cs.auc.dk>
 * @version 1.0b (980401)
 */

public class NotifyTarget {

	/**
	 * If this flag is set true, debugging information is send to std. out. Can
	 * be set by the constructor!!!!
	 */
	private boolean debug = false;

	/**
	 * time_to_wait is the max. time a thread is allowed to wait for a reply
	 * from the network.
	 */
	private int time_to_wait = 0;

	private Hashtable<String, Object> ht = new Hashtable<String, Object>();

	/**
	 * This is the default constructor
	 * 
	 * @param time
	 *            to wait for notification
	 */
	public NotifyTarget(int time_to_wait) {
		if (debug) {
			System.out.println("*** Notify Target: Initialized with timeout: "
					+ time_to_wait + "ms...");
		}
		this.time_to_wait = time_to_wait;
	}

	/**
	 * This constructor allows the user to explicitly set debugging information.
	 * I hate to modify code!
	 * 
	 * @param time
	 *            to wait for notification
	 * @param debugging
	 *            flag
	 */
	public NotifyTarget(int time_to_wait, boolean debug) {
		this.debug = debug;
		if (debug) {
			System.out.println("*** Notify Target: Debugging ON");
			System.out.println("*** Notify Target: Initialized with timeout: "
					+ time_to_wait + "ms...");
		}
		this.time_to_wait = time_to_wait;

	}

	/**
	 * Deprecated - for backwards compatibility only.
	 * 
	 * Use wait(String name, int ttw) instead!
	 */
	public synchronized void wait(String name) {
		wait(name, time_to_wait);
	}

	/**
	 * This method puts a thread to sleep on an object, indexed by the objects
	 * "tostring", given as parameter to this method.
	 * 
	 * @param the
	 *            string to wait for
	 * @param Amount
	 *            of time to wait.
	 */
	public synchronized void wait(String name, int ttw) {
		if (debug) {
			System.out.println("*** Notify Target: WAIT on entry indexed by: "
					+ name + "...");
		}

		// First we check weather there exists an object
		// for that name. If not then we create one.
		if (ht.containsKey(name)) {
			if (debug) {
				System.out
						.println("*** Notify Target:\t\tFound entry to wait on indexed by: "
								+ name + "...");
			}

		} else {
			ht.put(name, new Object());
			if (debug) {
				System.out
						.println("*** Notify Target:\t\tCreated entry to wait on indexed by: "
								+ name + "...");
				System.out.println("*** Notify Target:\t\tSize is now: "
						+ ht.size());
			}
		}

		// Now we know, that we can get the object
		// associated with the name.
		if (debug) {
			if (ht.containsKey(name)) {
				System.out
						.println("*** Notify Target:\t\tFound entry to wait on indexed by: "
								+ name + "...");
			} else {
				System.out
						.println("*** Notify Target:\t\tERROR. Could not find entry to wait on indexed by: "
								+ name + "...");
			}
		}
		Object myObject = ht.get(name);

		// Finally we let the thread wait on that object.
		try {
			synchronized (myObject) {
				myObject.wait(ttw);
			}
		} catch (InterruptedException e) {
			System.out
					.println("Error: Thread should not be interrupted..." + e);
		}

		if (debug) {
			System.out
					.println("*** Notify Target:\t\tWoke up on entry indexed by: "
							+ name + "...");
		}

		// And when we wake up we remove the object from the
		// hashtable - if it is there.
		if (ht.containsKey(name)) {
			if (debug) {
				System.out
						.println("*** Notify Target:\t\tRemoves entry indexed by: "
								+ name + "...");
			}
			ht.remove(name);
		}

	}

	/**
	 * This method takes the name (string representation) identifying the (set
	 * of) threads waiting for this notification.
	 * 
	 * @param the
	 *            string being notified
	 */

	public void notify(String name) {
		if (debug) {
			System.out
					.println("*** Notify Target: NOTIFY on entry indexed by: "
							+ name + "...");
		}

		// First check if a target exists for this.
		if (ht.containsKey(name)) {
			if (debug) {
				System.out
						.println("*** Notify Target:\t\tFound entry indexed by: "
								+ name + ". Notifying...");
			}
			Object myObject = ht.get(name);
			synchronized (myObject) {
				myObject.notifyAll();
			}
		} else {
			if (debug) {
				System.out
						.println("*** Notify Target:\t\tNo target indexed by: "
								+ name + "...");
			}
		}

	}

	/**
	 * This method identifies if a thred is wating for notification on a given
	 * string.
	 * 
	 * @param the
	 *            string
	 */
	public boolean waitsFor(String target) {
		return ht.containsKey(target);
	}
}
