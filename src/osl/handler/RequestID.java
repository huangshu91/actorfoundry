package osl.handler;

/**
 * This class specifies the identification associated with an asynchronous
 * request. Instances of this class may be used to identify requests which
 * result in asynchronous exceptions. Note that request IDs may later become
 * invalid when the original request has safely completed. This is indicated by
 * the <em>valid</em> flag and may be tested by clients using the
 * <em>isValid</em> predicate. If <em>isValid</em> returns false, then it is
 * deemed safe to remove any references to the id so that it may be garbage
 * collected. Note that IDs may remain valid indefinitely. Note also that
 * request IDs are only unique relative to the sessions they are associated
 * with, request IDs are meaningless if they are transmitted in messages to
 * other sessions.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:03 $)
 * @see RequestHandler
 */

public class RequestID {
	/**
	 * Indicates whether or not this ID is valid. If true then the request
	 * associated with this ID may still be in progress (and hence may still
	 * throw an exception).
	 */
	boolean valid;

	/**
	 * ID value.
	 */
	long id;

	/**
	 * The default constructor. Note that this constructor does not construct a
	 * legal <em>RequestID</em>.
	 */
	public RequestID() {
		this(false, -1);
	}

	/**
	 * The usual constructor used to build request IDs.
	 * 
	 * @param <b>V</b> A <em>boolean</em> indicating whether or not this id is
	 *        valid.
	 * @param <b>I</b> A <em>long</em> which uniquely identifies this ID
	 *        relative to all others delivered by the same session.
	 */
	public RequestID(boolean V, long I) {
		valid = V;
		id = I;
	}

	/**
	 * Returns true if this ID is valid, false otherwise. This method is meant
	 * to be used by request session clients to determine ID validity.
	 * 
	 * @return <b>True</b> if this id is valid, <b>false</b> otherwise.
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * Returns true if two request IDs should be considered equal and false
	 * otherwise.
	 * 
	 * @param <b>other</b> The object to which this object should be compared.
	 * @return <b>True</b> if both objects are <em>RequestID</em>s and have the
	 *         same <em>id</em>, <b>false</b> otherwise.
	 */
	public boolean equals(Object other) {
		return ((other instanceof RequestID) && (((RequestID) other).id == id));
	}

	/**
	 * Returns the hashcode for this id. The hashcode is simply the value of the
	 * id field truncated to an integer.
	 * 
	 * @return The hash value for this id.
	 */
	public int hashCode() {
		return (int) (id & 0xFFFF);
	}
}
