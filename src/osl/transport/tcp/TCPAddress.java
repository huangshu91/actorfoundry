package osl.transport.tcp;

import java.net.InetAddress;

import osl.transport.PhysicalAddress;

/**
 * This class defines the TCP implementation of the PhysicalAddress class
 * expected by the transport layer component. An address is the inet
 * address/port pair of a TCPInstance server socket.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:19 $)
 * @see osl.transport.PhysicalAddress
 */

public class TCPAddress implements PhysicalAddress {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7526343348794650256L;

	/**
	 * The <em>InetAddress</em> for this address.
	 */
	public InetAddress serverAddr;

	/**
	 * The port for this address.
	 */
	public int serverPort;

	/**
	 * Default constructor
	 */
	public TCPAddress() {
		this(null, -1);
	}

	/**
	 * Full constructor
	 * 
	 * @param <b>addr</b> The <em>InetAddress</em> to use for this address.
	 * @param <b>port</b> The port to use for this address.
	 */
	public TCPAddress(InetAddress addr, int port) {
		serverAddr = addr;
		serverPort = port;
	}

	/**
	 * Compares two instances and returns true if they should be considered
	 * "equal".
	 * 
	 * @param <b>other</b> The <em>Object</em> to which we are being compared.
	 * @return Returns <b>true</b> if both objects are <em>TCPAddresses</em>
	 *         with the same <em>serverAddr</em> and <em>serverPort</em>.
	 *         Returns <b>false</b> otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof TCPAddress) {
			TCPAddress theOther = (TCPAddress) other;
			return (serverAddr.equals(theOther.serverAddr) && (serverPort == theOther.serverPort));
		} else
			return false;
	}

	/**
	 * Returns the hashCode for this instace. We require this function so that
	 * TCPAddresses hash properly.
	 */
	public int hashCode() {
		return serverAddr.hashCode() + serverPort;
	}

	/**
	 * The canoncial toString method.
	 */
	public String toString() {
		return "TCPAddress: serverAddr = " + serverAddr + " serverPort = "
				+ serverPort;
	}
}
