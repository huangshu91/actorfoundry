//  Copyright Notice.
// 
//  Packet - a packet, as used by the simple network layer
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

import osl.nameservice.subnet.NSPacket;

/**
 * This class implements a general packet with envolope (source, target,
 * priority) and data.
 * 
 * A packet is immutable, but informantion can be extracted by public methods.
 * 
 * @author Thomas Heide Clausen <voop@cs.auc.dk>
 * @version 0.1 (980508)
 */

public class Packet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4089347328481622855L;

	/**
	 * Set this variable to true to turn on debugging output to std.out.
	 */
	private transient boolean debug = false;

	/**
	 * The next three variables implements the envelope information. The source
	 * and the target is the routing information of the packet. The priority is
	 * optional for e.g. packet priority routing / scheduling.
	 * 
	 * The intention of the priority is that 0 is normal priority. Higher number
	 * = higher priority and vice versa. However the Packet leaves the
	 * interpretation for whoever uses it.
	 */

	private Address source;
	private Address target;

	/**
	 * This is the data part of the packet. The data are a NSPacket - less
	 * general but faster.
	 */
	private NSPacket data;

	/**
	 * This constructor allows the priority to be set.
	 * 
	 * @param Address
	 *            source - Address of the source of this packet.
	 * @param Address
	 *            target - Address of the target of this packet.
	 * @param int priority - Packet priority.
	 * @param NSPacket
	 *            data - The data part of the packet.
	 */
	public Packet(Address source, Address target, int priority, NSPacket data) {
		this.source = source;
		this.target = target;
		this.data = data;

		if (debug) {
			System.out.println("***Packet: Created");
		}
	}

	/**
	 * This is the constructor when not using priority (that is: with default
	 * priority 0
	 * 
	 * @param Address
	 *            source - Address of the source of this packet.
	 * @param Address
	 *            target - Address of the target of this packet.
	 * @param NSPacket
	 *            data - The data part of the packet.
	 */
	public Packet(Address source, Address target, NSPacket data) {
		this(source, target, 0, data);
	}

	/**
	 * The following method allows inspection of the target.
	 * 
	 * @returns Address the target of this packet.
	 */
	public Address target() {
		return target;
	}

	/**
	 * The following method allows inspection of the source.
	 * 
	 * @returns Address the source of this packet.
	 */
	public Address source() {
		return source;
	}

	/**
	 * The following method returns a copy of the data portion
	 */
	public NSPacket getData() {
		return this.data;
	}

}
