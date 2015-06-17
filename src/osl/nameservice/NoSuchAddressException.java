//  Copyright Notice.
// 
//  NoSuchAddressException - exception, indicating an invalid address
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

package osl.nameservice;

/**
 * This class defines an exception which is thrown when someone tries to
 * register, remove or lookup a binding to an address, over which the name
 * service is not authoritative.
 * 
 * @author Thomas Heide Clausen <voop@cs.auc.dk>
 * @version $Revision: 1.1 $ ($Date: 1998/06/12 21:32:35 $)
 */

public class NoSuchAddressException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4153460453151714118L;

	public NoSuchAddressException(String s) {
		super(s);
	}
}
