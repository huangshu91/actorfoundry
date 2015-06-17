//  Copyright Notice.
// 
//  NSPacket - the databits passed between name services
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

package osl.nameservice.subnet;

import java.io.Serializable;

import osl.nameservice.Name;
import osl.transport.PhysicalAddress;

/**
 * This class implements a packet passed between services at an upper level of
 * the protocol stack.
 * 
 * The class represents an immutable unit (sort of), and does only a little
 * sanity check on the valuse it is assigned.
 * 
 * @author <voop@cs.auc.dk> Thomas Heide Clausen
 * @version 1.0b
 */
public class NSPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2468176937790054524L;

	/**
	 * If this flag is set true, debugging information is send to std. out.
	 */
	private boolean debug = false;

	/**
	 * Those constants define the packet type
	 */
	public static final int QUERY = 0;
	public static final int REPLY = 1;

	/**
	 * Those three private variables represents the type and contents of the
	 * packet. They are declared private to ensure that a data packet is
	 * immutable.
	 */
	private int type;
	private Name name;
	private PhysicalAddress paddr;

	/**
	 * The packet is initialized with the correct values by the constructor. The
	 * type of the packet is checked to be one of the two types (query or reply)
	 * and an exception is thrown to indicate otherwise.
	 * 
	 * This constructor is used only when we are generating a query!
	 * 
	 * @param int type
	 * @param Name
	 *            name
	 */
	public NSPacket(int type, Name name) {
		if (type == QUERY) {
			this.type = type;
			this.name = name;
			if (debug) {
				System.out.println("***Packet: Initialized with valid type");
			}
		} else {
			if (debug) {
				System.out.println("***Packet: Initialized with invalid type");
			}
		}
	}

	/**
	 * The packet is initialized with the correct values by the constructor. The
	 * type of the packet is checked to be one of the two types (query or reply)
	 * and an exception is thrown to indicate otherwise.
	 * 
	 * @param int type
	 * @param Name
	 *            name
	 * @param PhysicalAddress
	 *            paddr;
	 */
	public NSPacket(int type, Name name, PhysicalAddress paddr) {
		if (type == REPLY) {
			this.type = type;
			this.name = name;
			this.paddr = paddr;

			if (debug) {
				System.out.println("***Packet: Initialized with valid type");
			}
		} else {
			if (debug) {
				System.out.println("***Packet: Initialized with invalid type");
			}
		}
	}

	/**
	 * This method provides read-only access to the name Object stored in the
	 * packet.
	 * 
	 * @returns Name
	 */
	public Name getName() {
		return this.name;
	}

	/**
	 * This method provides read-only access to the addr Object stored in the
	 * packet.
	 * 
	 * @returns PhysicalAddress
	 */
	public PhysicalAddress getAddr() {
		return this.paddr;
	}

	/**
	 * This method provides read-only access to the type of the packet. The
	 * values are sanity checked by the constructor.
	 * 
	 * @returns int
	 */
	public int type() {
		return this.type;
	}

}
