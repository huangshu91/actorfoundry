//  Copyright Notice.
// 
//  LocalNameService - name service for the Actor Foundry.
//  Copyright (C) 1998  Thomas Heide Clausen <voop@cs.auc.dk>
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Library General Public
//  License as published by the Free Software Foundation,
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

package osl.nameservice.subnet;

import java.util.Enumeration;
import java.util.Hashtable;

import osl.nameservice.MalformedNameException;
import osl.nameservice.Name;
import osl.nameservice.NameNotFoundException;
import osl.nameservice.NameService;
import osl.nameservice.NoBindingException;
import osl.nameservice.NoSuchAddressException;
import osl.nameservice.subnet.network.Address;
import osl.nameservice.subnet.network.CallbackClient;
import osl.nameservice.subnet.network.Packet;
import osl.nameservice.subnet.network.UDP.UDPLayer;
import osl.nameservice.subnet.util.NotifyTarget;
import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.util.Debug;

/**
 * This name service implements local information with cache. The name service
 * holds authoritative information about local entities AND cached information
 * about remote entities.
 * 
 * No protocols are responsible for keeping the cache consistant, so the client
 * to this name service must be able to tolerate inconsistancy in the
 * information. (Recover before prevent)
 * 
 * If the name service does not have information (authoritative or otherwise),
 * it will broadcast a query and expect a reply.
 * 
 * @author Thomas Heide Clausen <voop@cs.auc.dk>
 * @version 2.0 (980602)
 */

public class LocalNameService implements CallbackClient, NameService {

	/**
	 * If this flag is set true, debugging information is send to std. out.
	 */
	private boolean debug = false;

	/**
	 * The value of time_to_wait is the number of ms that a thread is waiting
	 * before it expects a result to be present (or to barf an error)
	 * 
	 * This value is multiplied for each query send with no result. - proved to
	 * be smart in many other protocols, and is actually nice on crowded nets
	 * where a packet might be lost now and then.
	 */
	private int time_to_wait = 20011; // Another prime...Dunno why.

	// For test purposes only......To set the above variable.

	public void setTime(int i) {
		this.time_to_wait = i;
	}

	public int getTime() {
		return this.time_to_wait;
	}

	/**
	 * This object serves solely as a lock AND a wait/notify gadget.
	 */
	private NotifyTarget lock;

	/**
	 * The port to which the nameservice binds.
	 */
	// private int port;
	/**
	 * The physical addresse of the node, for which the name service is
	 * authoritative. As we do not care about the structure or contents
	 * 
	 * Name and type changed to be used when a name service may have more
	 * physical adresses - I think.
	 */
	private Hashtable<PhysicalAddress, Hashtable<Object, Object>> myAdresses = null;

	/**
	 * The physical address of this name service (used as source for the
	 * packets).
	 */
	private Address source;

	/**
	 * The physical address to where all queries and replies should be send.
	 * (Aka the broadcast address). Used as the target for the packets.
	 */
	private Address target;

	/**
	 * The next two values are timeout values for the cached information.
	 * last_used_timeout specifies how long information remains in the cache
	 * without being used.
	 * 
	 * last_seen_timeout specifies when the cached information is assumed to be
	 * invalid.
	 */
	/*
	 * private long last_used_timeout; private long last_seen_timeout;
	 */
	/**
	 * "Unfortunately" it is possible to create more names within the same
	 * milisecond, which requires each name to be uniquely identified by a
	 * counter too.
	 */
	private int nextName = 0;

	/**
	 * Unfortunately we are also using the physical address when we generate
	 * names. This worked well when we had only one physical address but now -
	 * now we have more. We COULD just pick one from the hashtables (would still
	 * guarantee unique names), however this would be less-than efficient and
	 * therefore we have this variable, storing a "random" physical address over
	 * which we are authoritative.
	 * 
	 * I can think of an alternative implementation, where we use the physical
	 * address of the name service - but that's not really important at 06:00AM.
	 * This will work!
	 */
	private PhysicalAddress nsAddress = null;

	/**
	 * This table maintains information about local names (authoritative
	 * information).
	 */
	// This has been deprecated as of the new version which supports multiple
	// physical adresses - I think....
	// private Hashtable localNames = null;
	/**
	 * This table maintains information about remote names (unauthoritative
	 * information).
	 */
	private Hashtable<Name, PhysicalAddress> cachedNames = null;

	/**
	 * The next variable represents our network connection.
	 */
	private osl.nameservice.subnet.network.TransportLayer theConnection = null;

	/**
	 * The scheduler, used to schedule threads in the name service.
	 */
	// private Scheduler scheduler;
	/**
	 * The constructor.
	 * 
	 * @param last_used_timeout
	 *            - number of ms after which the cahce may discard unused
	 *            information.
	 * @param last_seen_timeout
	 *            - number of ms after which the cache is assumed invalid.
	 */
	public LocalNameService(long last_used_timeout, long last_seen_timeout) {
		if (debug) {
			System.out.println("***LocalNameService: Starting");
		}
		/*
		 * this.last_used_timeout = last_used_timeout; this.last_seen_timeout =
		 * last_seen_timeout;
		 */this.myAdresses = new Hashtable<PhysicalAddress, Hashtable<Object, Object>>();
		this.cachedNames = new Hashtable<Name, PhysicalAddress>();
	}

	/**
	 * A slightly more usefull constructor. Assumes timeouts as almost 1 minute
	 * (also a prime in ms).
	 */
	public LocalNameService() {
		this(59999, 59999);
	}

	/**
	 * This is the initializer, accepting human-readable and parsable inputs
	 * 
	 * @param s
	 *            the scheduler
	 * @param me
	 *            my ip address
	 * @param bc
	 *            the broadcast address to use
	 * @param p
	 *            the name service portnumber.
	 */
	public void nsInitialize(Scheduler s, String me, String bc, String p) {
		if (debug) {
			System.out.println("***LocalNameService: Initializing");
		}
		try {
			int port = Integer.parseInt(p);
			Address src = new Address(me, port);
			Address tgt = new Address(bc, port);
			nsInitialize(s, src, tgt);
		} catch (Exception e) {
			Debug.exit("Error: " + e);
		}
	}

	/**
	 * Initializes the name service by setting the physical address and the
	 * scheduler. Furthermore the network layer is started.
	 * 
	 * @param s
	 *            the scheduler.
	 * @param tgt
	 *            the target to where all querries should be send.
	 * @param src
	 *            the source address (the address of the name service).
	 */
	private void nsInitialize(Scheduler s, Address src, Address tgt) {
		// this.scheduler = s;
		this.target = tgt;
		this.source = src;
		this.theConnection = new UDPLayer(this, UDPLayer.UNRELIABLE, 1024,
				this.source.getPort(), s, this.source.getAddress());
		this.lock = new NotifyTarget(time_to_wait, false);
		if (debug) {
			System.out.println("***LocalNameService: Initialized");
		}
	}

	public void nsInitialize(Scheduler s) {
		Debug.exit("Name service wrongly initialized");
	}

	/**
	 * Add a valid address to the name service. The name service is considered
	 * as authoritative for addresses, registered by nsAddAddress.
	 * 
	 * Eventually this will be changed to allow multiple authoritative
	 * addresses. For now only one is considered valid.
	 * 
	 * Implements the ability for a node to have more physical addresses.
	 * 
	 * myAdresses is a hashtable, each entry hashed on a physical address. Each
	 * entry in the hashtable is - again - a hashtable of names, bound to this
	 * physical address. This reflects having multiple hashtables "localNames".
	 * 
	 * @param the
	 *            address
	 */
	public synchronized void nsAddAddress(PhysicalAddress addr) {
		// If we do not allready have an entry for
		// this address - if we do not allready have
		// a table of authoritative info for this
		// address - then we add one.
		if (!myAdresses.containsKey(addr)) {
			this.myAdresses.put(addr, new Hashtable<Object, Object>());
			if (debug)
				System.out.println("***LocalNameService: Added " + addr
						+ " as an authoritative addr");
			// TO save ourself a lookup later when generating names.
			// YES, it will be overwritten but that's NOT important.
			// YES it STINKS, but I will eventually come up with
			// something nicer.
			this.nsAddress = addr;
		}
	}

	/**
	 * Returns a unique name.
	 * 
	 * @return Name - a globally unique name.
	 */
	public synchronized Name nsGenerateName() {
		// Originally we used the physical address
		// of the creator - but since we have no way
		// of knowing who that might be, we will
		// use a not-so-nice approximation and just
		// assume "any" of our physical addresses (the
		// last one added, actually). Ugly!!!
		Name name = new NSName(nsAddress, nextName);
		nextName++;
		if (debug) {
			System.out.println("***LocalNameService: Name generated: " + name);
		}
		return name;
	}

	/**
	 * Register a binding between a NSName and the UDPAddress, given in the
	 * constructor. Modifies authoritative information about a name!
	 * 
	 * @param the
	 *            name.
	 * @param the
	 *            physical address, to which the name is bound.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public synchronized void nsRegister(Name name, PhysicalAddress addr)
			throws MalformedNameException, NoSuchAddressException {
		// check if we are authoritative over the address:
		if (myAdresses.containsKey(addr)) {
			// Okay, we were. Now get the correct hashtable
			// and add the name
			Hashtable<Object, Object> myTable = myAdresses.get(addr);
			myTable.put(name, addr);
			if (debug) {
				System.out.println("***LocalNameService: Name registered "
						+ name + " at physical address " + addr);
			}
		} else {
			throw new NoSuchAddressException(
					"Tried to register at unauthorized address");
		}
	}

	/**
	 * Removes a binding between a NSName and the UDPAddress, given in the
	 * constructor. Modifies authoritative informatuon about a name!
	 * 
	 * @param the
	 *            name.
	 * @param the
	 *            physical address to which the name is no longer bound
	 * @exception osl.nameservice.NameNotFoundException
	 *                Thrown if the given name was never bound by this instance
	 *                of the nameservice.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public synchronized void nsRemove(Name name, PhysicalAddress addr)
			throws NameNotFoundException, NoSuchAddressException {
		// First: are we authoritative at all?
		if (!myAdresses.containsKey(addr)) {
			throw new NoSuchAddressException(
					"Tried to remove binding to unauthorized address");
		}
		// If we get here, then yes we are. Now - do we have the name?
		// First we get the table. Then we check if we have the name and - if so
		// - we nuke it.
		Hashtable<Object, Object> myTable = myAdresses.get(addr);
		if (!myTable.containsKey(name)) {
			throw new NameNotFoundException("Name not found");
		}
		myTable.remove(name);
		if (debug) {
			System.out.println("***LocalNameService: Name removed");
		}
	}

	/**
	 * Looks up to which UDPAddress a name is bound. The algorithm goes: 1)
	 * Check if the name is bound to the LOCAL UDPAddress (check if
	 * authoritative information is present)
	 * 
	 * If not, then:
	 * 
	 * 2) Check if valid cached information is present.
	 * 
	 * If not, then:
	 * 
	 * 3) Issue a query, and put the thread to wait for reply.
	 * 
	 * An exception is thrown if no binding can be identified.
	 * 
	 * @param Name
	 *            - the name in query
	 * @return PhysicalAddress - the address to which the name is bound
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NameNotFoundException
	 *                Thrown if no binding exists for the given name.
	 * @exception osl.nameservice.NoBindingException
	 *                Thrown if the given name currently has no associated
	 *                <em>PhysicalAddress</em> binding.
	 */

	public synchronized PhysicalAddress nsLookup(Name name)
			throws NameNotFoundException, MalformedNameException,
			NoBindingException {
		// Check authoritative information
		//
		// This has become slightly more difficult: now we need
		// to check all our physical addresses (iterate through
		// the table of ours). It is slightly more expensive now
		// than it was before.
		Enumeration<Hashtable<Object, Object>> myEnum = myAdresses.elements();
		while (myEnum.hasMoreElements()) {
			Hashtable<Object, Object> myTable = myEnum.nextElement();
			if (myTable.containsKey(name)) {
				return (PhysicalAddress) myTable.get(name);
			}
		}
		// Okay no luck with authoritative information....

		// Check cached information (phew, only one lookup)
		if (cachedNames.containsKey(name)) {
			return (PhysicalAddress) cachedNames.get(name);
		}

		// Now we query at most three times before we give up.
		for (int i = 0; i < 3; i++) {
			// Okay. Now we make the thread wait.
			synchronized (lock) {
				query(name);
				// Use some sort of incremental wait - not to wait
				// forever if the first packet was just lost by accident.
				lock.wait(name.toString(), ((i = 1) * time_to_wait));
			}
			if (cachedNames.containsKey(name)) {
				return (PhysicalAddress) cachedNames.get(name);
			}
		}

		throw new NoBindingException("No binding");
	}

	/**
	 * This method returns true iff there exists a binding between the name and
	 * the local node.
	 * 
	 * @param The
	 *            name
	 * @param the
	 *            physical address to bind the name to
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public synchronized boolean nsLocal(Name name, PhysicalAddress localBind)
			throws MalformedNameException, NoSuchAddressException {
		// Grr, we have to do this again....iterate and...
		Enumeration<Hashtable<Object, Object>> myEnum = myAdresses.elements();
		while (myEnum.hasMoreElements()) {
			Hashtable<Object, Object> myTable = myEnum.nextElement();
			if (myTable.containsKey(name)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * This method removes any cached information about a given NSName
	 * 
	 * @param The
	 *            name
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 */
	public synchronized void nsClear(Name name) throws MalformedNameException {
		cachedNames.remove(name);
	}

	/**
	 * Our callback enqueues incomming information. Information (queries and
	 * replies) send from ourself are filtered off to avoid spending cycles on
	 * them.
	 * 
	 * @param the
	 *            incomming packet
	 */
	public void callback(Packet p) {
		// Check that the packet has not been send by ourself....
		if (!p.source().equals(source)) {

			NSPacket myPacket = p.getData();

			// Extract type of packet.
			int type = myPacket.type();

			switch (type) {
			case NSPacket.QUERY:
				// If it is a query, we want to check if we
				// have local info about the name. If so,
				// then we send a reply,
				// otherwise we just ignore it.
				if (debug) {
					System.out
							.println("***LocalNameService: Query received...");
				}

				// Iteration and stuff......
				Enumeration<Hashtable<Object, Object>> myEnum = myAdresses
						.elements();
				while (myEnum.hasMoreElements()) {
					Hashtable<Object, Object> myTable = myEnum.nextElement();
					if (myTable.containsKey(myPacket.getName())) {
						reply(myPacket.getName(), (PhysicalAddress) myTable
								.get(myPacket.getName()));

						if (debug) {
							System.out
									.println("***LocalNameService: Reply generated...");
						}
					}
				}
				break;

			case NSPacket.REPLY:
				// If is is a reply, we check if we have a
				// cached entry we update the information
				// (regardless if it has changed).
				// We COULD use the same thread to check for
				// expired entries, but expecting quite
				// a number of those invocations, it would
				// be just a source for other strange conditions.
				if (debug) {
					System.out
							.println("***LocalNameService: Reply received...");
					System.out.println("\t" + (myPacket.getName()));
				}
				if (cachedNames.containsKey(myPacket.getName())
						|| lock.waitsFor((myPacket.getName()).toString())) {
					cachedNames.remove(myPacket.getName());
					cachedNames.put(myPacket.getName(), myPacket.getAddr());
					lock.notify((myPacket.getName()).toString());
					if (debug) {
						System.out.print("\tCache entry updated...");
						System.out.println("size of cache: "
								+ cachedNames.size());
					}
				}

				break;
			}
		}
	}

	/**
	 * This method generates a query and sends to the transport layer.
	 * 
	 * @param NSName
	 */
	private void query(Name name) {
		if (debug) {
			System.out.println("***LocalNameService: Issue querry...");
		}

		/**
		 * The next few lines generate a packet and serialize it onto a
		 * bytestream.
		 */
		try {
			NSPacket myPacket = new NSPacket(NSPacket.QUERY, name);
			Packet toSend = new Packet(source, target, myPacket);
			if (debug) {
				System.out.println("***LocalNameService: message composed");
			}
			theConnection.send(toSend);
		} catch (Exception e) {
			Debug.exit("Unable to send query " + e);
		}
		if (debug) {
			System.out.println("\tMessage send...");
		}
	}

	/**
	 * This method generates a reply, and sends it by the transport layer
	 * 
	 * @param Name
	 */
	private void reply(Name name, PhysicalAddress addr) {
		try {
			NSPacket myPacket = new NSPacket(NSPacket.REPLY, name, addr);
			Packet toSend = new Packet(source, target, myPacket);
			if (debug) {
				System.out.println("***LocalNameService: message composed");
			}
			theConnection.send(toSend);
		} catch (Exception e) {
			Debug.exit("Unable to  send reply" + e);
		}
		if (debug) {
			System.out.println("\tMessage send...");
		}
	}
}
