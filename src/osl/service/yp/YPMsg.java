package osl.service.yp;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import osl.manager.ActorManagerName;

/**
 * This is a simple YP service which allows local actors to look up the
 * addresses of remote actor managers by specifying an IP or hostname. The
 * manager name returned is always that returned by the YP service running on
 * the remote node. If there is no YP service then the service call will hang.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/03/09 21:00:27 $)
 * @see osl.service.yp.YP
 */

public class YPMsg implements Externalizable {
	/**
	 * The type of this message.
	 */
	public int type = 0;

	/**
	 * If this is a query then this is the ID of the query. If this is a
	 * response then this is the ID of the original query.
	 */
	public int ID = 0;

	/**
	 * If this is a response then this field holds the name returned by the
	 * sending YP service.
	 */
	public ActorManagerName response = null;

	/**
	 * The defaul constructor. Need this or serialization won't work!
	 */
	public YPMsg() {
	}

	/**
	 * The full constructor.
	 * 
	 * @param <b>oSend</b> The <em>PhysicalAddress</em> of the original sender
	 *        of this message. We need this data so that replies are correctly
	 *        routed in the case of forwarded requests.
	 * @param <b>dName</b> The <em>Name</em> of the receiver.
	 */
	public YPMsg(int T, int I, ActorManagerName R) {
		type = T;
		ID = I;
		response = R;
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
		// Write out the type and ID
		out.writeInt(type);
		out.writeInt(ID);

		// If this is a response then write out the reponse value
		if (type == YP.YP_RESPONSE)
			out.writeObject(response);
	}

	/**
	 * Deserialize into a new instnace of <em>YPMesg</em> by reading from the
	 * given input stream.
	 * 
	 * @param <b>in</b> The <em>InputStream</em> from which we should
	 *        deserialize this instance.
	 * @exception java.io.IOException
	 *                Thrown if there is an I/O error encountered while reading
	 *                data from the input stream.
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if an object read of the input stream could not be
	 *                found.
	 */
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// Read in the type and ID
		type = in.readInt();
		ID = in.readInt();

		// If this is a response then read in the reponse
		if (type == YP.YP_RESPONSE)
			response = (ActorManagerName) in.readObject();
		else
			response = null;
	}
}
