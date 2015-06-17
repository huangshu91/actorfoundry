package osl.transport.udp;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetAddress;
import java.net.UnknownHostException;

import osl.transport.PhysicalAddress;

/**
 * This class defines the UDP implementation of the PhysicalAddress class
 * expected by the transport layer component. An address is simply a machine
 * name-port pair.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.7 $ ($Date: 1999/07/13 02:01:51 $)
 * @see osl.transport.PhysicalAddress
 */

public class UDPAddress implements PhysicalAddress, Externalizable {
	/*
	 * The <em>InetAddress</em> for this address. <b>IMPORTANT:</b> UDP
	 * addresses are actually cached in serialized form to make message
	 * transport faster. This means that the cache will be invalid if this field
	 * is ever changed. In other words, don't change this field once an instance
	 * of this class has been constructed.
	 */
	public InetAddress hostAddr;

	/**
	 * The port for this address.
	 */
	public int hostPort;

	/**
	 * This static array maps integer indexes to their string representations.
	 * We do this to allow quick translation of numeric IPs into their string
	 * form (so that we can quickly reconstruct InetAddresses from the cache).
	 */
	static String[] mapNumTable;

	// Static initializer for mapping table
	static {
		mapNumTable = new String[256];

		for (int i = 0; i < 256; i++)
			mapNumTable[i] = (new Integer(i)).toString();
	}

	/**
	 * This static table maps IP numbers to their corresponding InetAddress
	 * structures. We use this table as a cache for quickly looking up
	 * InetAddress structures. This lets us avoid extra calls to the InetAddress
	 * constructor. In particular, unless your foundry configuration spans a
	 * large network, the actual InetAddresses required for communication should
	 * correspond to a relatively small set.
	 */
	static InetAddress[][][][][] cachedAddrs = new InetAddress[256][][][][];

	/**
	 * Cached serialized form of this address. Useful for quick serialization
	 * and deserialization.
	 */
	public byte[] cache = { 0, 0, 0, 0, 0, 0 };

	/**
	 * Cached string representation of this address.
	 */
	public String rep;

	/**
	 * Default constructor
	 */
	public UDPAddress() {
	}

	/**
	 * Full constructor
	 * 
	 * @param <b>addr</b> The <em>InetAddress</em> to use for this address.
	 * @param <b>port</b> The port to use for this address.
	 */
	public UDPAddress(InetAddress addr, int port) {
		hostAddr = addr;
		hostPort = port;

		byte[] aBytes = addr.getAddress();

		cache[0] = aBytes[0];
		cache[1] = aBytes[1];
		cache[2] = aBytes[2];
		cache[3] = aBytes[3];
		cache[4] = (byte) (hostPort >>> 8);
		cache[5] = (byte) (hostPort >>> 0);

		rep = "" + (0x000000FF & cache[0]) + "." + (0x000000FF & cache[1])
				+ "." + (0x000000FF & cache[2]) + "." + (0x000000FF & cache[3])
				+ ":" + hostPort;
	}

	/**
	 * Compares two instances and returns true if they should be considered
	 * "equal". We require this function so that UDPAddresses hash correctly.
	 * 
	 * @param <b>other</b> The <em>Object</em> to which we are being compared.
	 * @return Returns <b>true</b> if both objects are <em>UDPAddresses</em>
	 *         with the same <em>hostAddr</em> and <em>hostPort</em>. Returns
	 *         <b>false</b> otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof UDPAddress) {
			UDPAddress theOther = (UDPAddress) other;
			return (hostAddr.equals(theOther.hostAddr) && (hostPort == theOther.hostPort));
		} else
			return false;
	}

	/**
	 * Returns the hashCode for this instace. We require this function so that
	 * UDPAddresses hash properly.
	 */
	public int hashCode() {
		return hostAddr.hashCode() + hostPort;
	}

	/**
	 * The canoncial toString method.
	 */
	public String toString() {
		return rep;
	}

	/**
	 * Returns an InetAddress instance corresponding to the given addrBytes.
	 * This routine first looks in the cachedAddrs table to see if the address
	 * already exists. If it does then it returns the existing instance.
	 * Otherwise, it creates the required address, stores it in the cache, and
	 * returns the new address.
	 */
	static InetAddress findAddress(int i1, int i2, int i3, int i4)
			throws UnknownHostException {
		try {
			return cachedAddrs[i1][i2][i3][i4][0];
		} catch (NullPointerException e) {
			return addToCache(i1, i2, i3, i4);
		}
	}

	/**
	 * Adds a new internet address to the cache. The resulting
	 * <em>InetAddress</em> is returned.
	 */
	static InetAddress addToCache(int i1, int i2, int i3, int i4)
			throws UnknownHostException {
		InetAddress newAddr = InetAddress.getByName(mapNumTable[i1] + "."
				+ mapNumTable[i2] + "." + mapNumTable[i3] + "."
				+ mapNumTable[i4]);

		if (cachedAddrs[i1] == null)
			cachedAddrs[i1] = new InetAddress[256][][][];

		if (cachedAddrs[i1][i2] == null)
			cachedAddrs[i1][i2] = new InetAddress[256][][];

		if (cachedAddrs[i1][i2][i3] == null)
			cachedAddrs[i1][i2][i3] = new InetAddress[256][];

		cachedAddrs[i1][i2][i3][i4] = new InetAddress[1];
		cachedAddrs[i1][i2][i3][i4][0] = newAddr;

		return newAddr;
	}

	// /////////////////////////////////////////////////
	// //// Externalizable Interface Functions
	// /////////////////////////////////////////////////
	/**
	 * Serialize the contents of this class to the output stream.
	 * 
	 * @param <b>out</b> The <em>OutputStream</em> to which we should write this
	 *        instance.
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		out.write(cache);
	}

	/**
	 * Deserialize into a new instnace of <em>UDPAddress</em> by reading from
	 * the given input stream.
	 * <p>
	 * 
	 * @param <b>in</b> The <em>InputStream</em> from which we should
	 *        deserialize this instance.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error is encountered while reading the
	 *                input stream.
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if a class being deserialized from the input stream
	 *                cannot be found by the class loader.
	 * @exception java.lang.ClassCastException
	 *                Thrown if a class deserialized from the input stream had
	 *                an unexpected type.
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {

		// Read directly into our cache
		in.read(cache);

		// Reassemble the host address
		hostAddr = findAddress((0x000000FF & cache[0]),
				(0x000000FF & cache[1]), (0x000000FF & cache[2]),
				(0x000000FF & cache[3]));

		// Reassemble the port
		hostPort = ((0x000000FF & cache[4]) << 8) + (0x000000FF & cache[5]);

		// Reassemble the string representation
		rep = "" + (0x000000FF & cache[0]) + "." + (0x000000FF & cache[1])
				+ "." + (0x000000FF & cache[2]) + "." + (0x000000FF & cache[3])
				+ ":" + hostPort;

	}

}
