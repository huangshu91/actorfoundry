package osl.manager;

import java.io.IOException;

/**
 * This interface defines the behavior of an actor used to control an input
 * stream on behalf of external actors. The interface is patterned after
 * <em>java.io.InputStream</em> with a few additional methods which provide
 * convenient mechanisms for reading lines of text from the input stream.
 * <p>
 * 
 * If a security manager is controlling the foundry (i.e. startfoundry was
 * specified with the -secure option), then user-written actors will not have
 * direct access to several standard streams. Thus, instances of
 * <em>StreamInputActor</em> are normally created with sufficient privileges in
 * order to serve as stream proxies for external actors.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.1 $ ($Date: 1998/06/12 21:32:17 $)
 * @see java.io.InputStream
 */

public interface StreamInputActor {
	/**
	 * Read the next byte of data from an input stream. The value returned is an
	 * <em>Integer</em> in the range <tt>0</tt> to <tt>255</tt> (i.e. a
	 * character). This method blocks until a byte of data is available, or
	 * end-of-file is reached. If end-of-file is encountered then a -1 is
	 * returned. Normally, this method will be invoked using the "call" actor
	 * operation, as this is the only way to obtain the data returned from this
	 * method.
	 * <p>
	 * 
	 * @return An <em>Integer</em> containing the next byte of data or -1 if
	 *         end-of-file is reached.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream.
	 */
	public Integer read() throws IOException;

	/**
	 * Read the next byte of data from an input stream and send it to a
	 * specified actor. The value returned is an <em>Integer</em> in the range
	 * <tt>0</tt> to <tt>255</tt> (i.e. a character). This method blocks until a
	 * byte of data is available, or end-of-file is reached. If end-of-file is
	 * encountered then a -1 is returned. The result is sent to the actor with
	 * name <b>client</b> by invoking method <b>method</b>. Thus, <b>client</b>
	 * is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Integer</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the data.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the data.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream. For asynchronous calls, this exception is normally
	 *                returned as an invocation of the asynchException" method.
	 * @see osl.manager.StreamInputActor#read()
	 */
	public void read(ActorName client, String method) throws IOException;

	/**
	 * Read an array of bytes from the input stream and return them to the
	 * caller. The maximum number of bytes to read is specified by <b>max</b>.
	 * This method blocks until input is available. Normally, this method will
	 * be invoked using the "call" actor operation, as this is the only way to
	 * obtain the data returned from this method.
	 * <p>
	 * 
	 * @param <b>max</b> An <em>Integer</em> giving the maximum number of bytes
	 *        to read from the stream.
	 * @return A <b>Byte</b> array giving the data read from the stream. The
	 *         return value is <tt>null</tt> if no data was available because
	 *         end-of-file was encountered. Otherwise, the length of the array
	 *         indicates the actual number of bytes read.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream, or if <b>max</b> is less than one.
	 */
	public Byte[] read(Integer max) throws IOException;

	/**
	 * Read an array of bytes from the input stream and send them to a specified
	 * actor. The maximum number of bytes to read is specified by <b>max</b>.
	 * This method blocks until input is available. The result is sent to the
	 * actor with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Byte[]</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the data.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the data.
	 * @param <b>max</b> An <em>Integer</em> giving the maximum number of bytes
	 *        to read from the stream.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading the attached
	 *                stream, or if <b>max</b> is less than one.
	 * @see osl.manager.StreamInputActor#read(Integer)
	 */
	public void read(ActorName client, String method, Integer max)
			throws IOException;

	/**
	 * Skip over and discard <b>n</b> bytes of data from the input stream.
	 * Depending on the internal <em>InputStream</em>, the actual number of
	 * bytes skipped may vary. The number of bytes skipped is returned as the
	 * result of this method. Normally, this method will be invoked using the
	 * "call" actor operation, as this is the only way to obtain the data
	 * returned from this method.
	 * <p>
	 * 
	 * @param <b>n</b> A <em>Long</em> giving the number of bytes to be skipped.
	 * @return An <em>Long</em> giving the actual number of bytes skipped.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while skipping bytes.
	 */
	public Long skip(Long n) throws IOException;

	/**
	 * Skip over and discard <b>n</b> bytes of data from the input stream.
	 * Depending on the internal <em>InputStream</em>, the actual number of
	 * bytes skipped may vary. The number of bytes skipped is sent to the actor
	 * with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Long</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the number of bytes skipped.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the number of bytes skipped.
	 * @param <b>n</b> A <em>Long</em> giving the number of bytes to be skipped.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while skipping bytes.
	 * @see osl.manager.StreamInputActor#skip(Long)
	 */
	public void skip(ActorName client, String method, Long n)
			throws IOException;

	/**
	 * Return the number of bytes that can be read from the internal input
	 * stream without blocking. The number of bytes available is returned as an
	 * <em>Integer</em> to the caller. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @return An <em>Integer</em> giving the number of bytes that can be read
	 *         from this input stream without blocking.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to
	 *                determine the number of bytes available.
	 */
	public Integer available() throws IOException;

	/**
	 * Determine the number of bytes that can be read from the internal input
	 * stream without blocking, and send the result to the specified caller. The
	 * number of bytes available is sent to the actor with name <b>client</b> by
	 * invoking method <b>method</b>. Thus, <b>client</b> is expected to define
	 * a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Integer</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the number of bytes available.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the number of bytes available.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to
	 *                determine the number of bytes available.
	 * @see osl.manager.StreamInputActor#available()
	 */
	public void available(ActorName client, String method) throws IOException;

	/**
	 * Close the internal input stream. As this method has no return value, it
	 * may be called either synchronously or asynchronously.
	 * 
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while attempting to close
	 *                the stream.
	 */
	public void close() throws IOException;

	/**
	 * Mark the current position in the internal stream. Later calls to
	 * <em>reset</em> will reposition the internal stream at the last marked
	 * position. A <em>readlimit</em> may be specified which indicates the
	 * number of bytes which may be read before the mark position becomes
	 * invalid. As this method has no return value, it may be called either
	 * synchronously or asynchronously.
	 * <p>
	 * 
	 * @param <b>readlimit</b> An <em>Integer</em> indicating the maximum number
	 *        of bytes that can be read before the mark position becomes
	 *        invalid.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while placing the mark.
	 */
	public void mark(Integer readlimit);

	/**
	 * Reposition the internal stream to the position marked by a previous call
	 * to <em>mark</em>. As this method has no return value, it may be called
	 * either synchronously or asynchronously.
	 * <p>
	 * 
	 * @exception java.io.IOException
	 *                Thrown if the internal stream has not been marked, or if
	 *                the previously placed mark has been invalidated.
	 */
	public void reset() throws IOException;

	/**
	 * Test if the internal input stream supports the <em>mark</em> and
	 * <em>reset</em> methods. A <em>Boolean</em> is returned to the caller
	 * indicating the result of the query. Normally, this method will be invoked
	 * using the "call" actor operation, as this is the only way to obtain the
	 * data returned from this method.
	 * <p>
	 * 
	 * @return A <em>Boolean</em> indicating <tt>true</tt> if <em>mark</em> and
	 *         <em>reset</em> are supported, and <tt>false</tt> otherwise.
	 */
	public Boolean markSupported();

	/**
	 * Test if the internal input stream supports the <em>mark</em> and
	 * <em>reset</em> methods. The <em>Boolean</em> result is sent to the actor
	 * with name <b>client</b> by invoking method <b>method</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Boolean</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the <em>markSupported</em> status
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which will accept the <em>markSupported</em> status.
	 * @see osl.manager.StreamInputActor#markSupported()
	 */
	public void markSupported(ActorName client, String method);

	/**
	 * Read a line of characters from the internal input stream. A line is any
	 * sequence of characters terminated by a newline. This call will block
	 * until either a newline terminates a sequence of characters, or
	 * end-of-file is encountered. In either case, a <em>Character</em> array
	 * containing the characters read (minus the newline terminator) is returned
	 * to the caller. If no characters were available (e.g. because end-of-file
	 * was encountered immediately), then <tt>null</tt> is returned. Normally,
	 * this method will be invoked using the "call" actor operation, as this is
	 * the only way to obtain the data returned from this method.
	 * <p>
	 * 
	 * @return A <em>Character</em> array containing all the characters read up
	 *         to a terminating newline or end-of-file, or <tt>null</tt> if no
	 *         characters were available do to end-of-file.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading a line of
	 *                characters.
	 */
	public Character[] readln() throws IOException;

	/**
	 * Read a line of characters from the internal input stream. A line is any
	 * sequence of characters terminated by a newline. This call will block
	 * until either a newline terminates a sequence of characters, or
	 * end-of-file is encountered. In either case, a <em>Character</em> array
	 * containing the characters read (minus the newline terminator) is sent to
	 * the actor with name <b>client</b> by invoking method <b>method</b>. If no
	 * characters were available (e.g. because end-of-file was encountered
	 * immediately), then <tt>null</tt> is sent to <b>client</b>. Thus,
	 * <b>client</b> is expected to define a method with signature:
	 * <p>
	 * 
	 * <blockquote><code>
     * public <em>type</em> <b>method</b>(<em>Character</em>);
     * </code></blockquote>
	 * 
	 * where <em>type</em> may be any legal return type. Any error resulting
	 * from the sending of the result (e.g. NoSuchMethodException,
	 * RemoteCodeException, etc) is ignored by the <em>StreamInputActor</em>
	 * (but it IS logged to the Actor log file). Normally, this method is used
	 * by actors wishing to perform asynchronous I/O.
	 * <p>
	 * 
	 * @param <b>client</b> The <em>ActorName</em> of the actor which should
	 *        receive the line of characters.
	 * @param <b>method</b> The <em>String</em> name of the method in
	 *        <b>client</b> which should recieve the line of characters.
	 * @exception java.io.IOException
	 *                Thrown if an I/O error occurs while reading a line of
	 *                characters.
	 * @see osl.manager.StreamInputActor#readln()
	 */
	public void readln(ActorName client, String method) throws IOException;
}
