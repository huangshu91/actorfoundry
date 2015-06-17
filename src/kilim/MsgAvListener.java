/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */

package kilim;

/**
 * @see kilim.Mailbox
 */
public interface MsgAvListener {
	void msgAvailable(Mailbox MB);
}
