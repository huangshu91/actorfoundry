//  Copyright Notice.
// 
//  PortOutOfRangeException - exception thrown by the name serices
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

class PortOutOfRangeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5605770598818241640L;

	public PortOutOfRangeException() {
		super();
	}

	public PortOutOfRangeException(String s) {
		super(s);
	}
}
