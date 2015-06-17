//  Copyright Notice.
// 
//  Address - A physical address as used by the network layer
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

// Originally from package voop.network.simple;

package osl.nameservice.subnet.network;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This class implements the physical address as used by any of the simple
 * network layers. Basically it is an immutable IP:PORT pair.
 * 
 * @author Thomas Heide Clausen <voop@cs.auc.dk>
 * @version 0.1 (980508)
 */

public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203543108936347682L;

	/**
	 * Set this variable to true to turn on debugging output to std.out.
	 */
	private transient boolean debug = false;

	/**
	 * Those two variables implement the state of this object. They are declared
	 * private as we want a physical address to be immutable.
	 * 
	 * Copies of the two variables are returned by getAddress() and getPort()
	 * methids.
	 */

	private InetAddress myIP;
	private int myPort;

	/**
	 * This is the default and only constructor. Asside from being the only way
	 * to modify the address and port, there are some sanity checks preventing
	 * e.g. negative or otherwise out of range port numbers.
	 * 
	 * To encapsulate the concept of the strange java.net.InetAddress, the ip is
	 * given as a string (which may be either a fqhn or an IP.
	 * 
	 * @param String
	 *            ip - the string representation of an IP address.
	 * @param int port - the port.
	 * @exception PortOutOfRangeException
	 *                Thrown if the requested port is out of the range of legal
	 *                IP ports.
	 * @exception UnknownHostException
	 *                Thrown if the host name given by <b>ip</b> can not be
	 *                resolved.
	 */

	public Address(String ip, int myPort) throws PortOutOfRangeException,
			UnknownHostException {
		if ((myPort >= 0) && (myPort <= 65535)) {
			this.myPort = myPort;
		} else {
			throw new PortOutOfRangeException("Invalid port number.");
		}
		myIP = InetAddress.getByName(ip);

		if (debug) {
			System.out.println("***Address: created.");
			System.out.println("\tIP: " + myIP.toString());
			System.out.println("\tPort: " + myPort);
		}
	}

	/**
	 * Abstraction to provide read-only access to the port
	 * 
	 * @returns int The portnumber.
	 */
	public int getPort() {
		return myPort;
	}

	/**
	 * Abstraction to provide read-only access to the inet address.
	 * 
	 * @returns InetAddress The InetAddress.
	 */
	public InetAddress getAddress() {
		return myIP;
	}

	/**
	 * The generic toString method.
	 */
	public String toString() {
		return ("IP: " + myIP.toString() + " Port: " + myPort);
	}

	/**
	 * The generic equals() method.
	 */
	public boolean equals(Address p) {
		return (myIP.equals(p.getAddress()) && (myPort == p.getPort()));
	}

}
