package osl.manager;

import java.io.IOException;

/**
 * This interface defines the behavior of an actor used to control an output
 * stream on behalf of external actors. The interface is patterned after
 * <em>java.io.OutputStream</em> with a few additional methods which provide
 * convenient mechanisms for displaying strings on the output stream. Note that
 * none of the methods defined in this class return a value. Thus, each method
 * may be called using "send" or "call" according to the synchronization needs
 * of the client.
 * <p>
 * 
 * If a security manager is controlling the foundry (i.e. startfoundry was
 * specified with the -secure option), then user-written actors will not have
 * direct access to several standard streams. Thus, instances of
 * <em>StreamOutputActor</em> are normally created with sufficient privileges in
 * order to serve as stream proxies for external actors.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/06/12 21:32:19 $)
 * @see java.io.OutputStream
 */

public interface StreamOutputActor {
	/**
	 * Writes a byte to the internal output stream.
	 * 
	 * @param <b>b</b> An <em>Integer</em> giving the byte to write.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte.
	 */
	public void write(Integer b) throws IOException;

	/**
	 * Write the contents of a byte array to the internal output stream.
	 * 
	 * @param <b>b</b> A <em>Byte</em> array of data to write to the output
	 *        stream.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the byte
	 *                array.
	 */
	public void write(Byte b[]) throws IOException;

	/**
	 * Write a subsequence of a byte array to the output stream.
	 * 
	 * @param <b>b</b> A <em>Byte</em> array of data from which a subsequence
	 *        will be written to the output stream.
	 * @param <b>off</b> An <em>Integer</em> giving the index in the data to
	 *        start the write from.
	 * @param <b>len</b> An <em>Integer</em> giving the total number of bytes to
	 *        write.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while writing the
	 *                subsequence of the byte array.
	 */
	public void write(Byte b[], Integer off, Integer len) throws IOException;

	/**
	 * Print a string to the output stream.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void print(String s) throws IOException;

	/**
	 * Print a string followed by a newline character.
	 * 
	 * @param <b>s</b> The <em>String</em> to display.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while displaying the string.
	 */
	public void println(String s) throws IOException;

	/**
	 * Flush the internal output stream.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while flushing the input
	 *                stream.
	 */
	public void flush() throws IOException;

	/**
	 * Close the internal output stream.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close() throws IOException;
}
