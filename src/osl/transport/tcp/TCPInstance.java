package osl.transport.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportMessage;
import osl.util.Debug;
import osl.util.ParameterList;
import osl.util.WaitQueue;

/**
 * This class implements an instance of a TCP transport layer connection.
 * Clients use this connection to send messages. The instance is also
 * responsible for receiving incoming messages and invoking the appropriate
 * methods on the receiving client. This class implements the
 * <em>TransportInstance</em> interface.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1998/09/07 23:00:36 $)
 * @see osl.transport.TransportLayer
 * @see osl.transport.TransportInstance
 * @see osl.transport.tcp.TCPTransportLayer
 */

class TCPInstance implements TransportInstance, Runnable {
	/**
	 * The <em>Scheduler</em> for the transport layer. New messages are spawned
	 * on their own threads thus we need to consult the system scheduler to
	 * schedule these threads.
	 */
	Scheduler scheduler;

	/**
	 * The <em>TransportClient</em> instance which should receive incoming
	 * messages for this connection.
	 */
	TransportClient recipient;

	/**
	 * The <em>PhysicalAddress</em> instance associated with this connection.
	 * This address represents the address of the server socket used by this
	 * instance to handle new connection requests from remote TCPInstances.
	 */
	PhysicalAddress connectionAddr;

	/**
	 * The server socket which this instance uses to listen for new incoming
	 * connections. The creator of this instance is expected to set this field.
	 */
	ServerSocket socket;

	/**
	 * Hashtable which hashes physical addresses to the
	 * <em>TCPSocketHandler</em> instance which handles outgoing messages to
	 * this address.
	 */
	Hashtable<PhysicalAddress, TCPHandleOutgoing> outgoing;

	/**
	 * The default constructor. Note that this constructor is only accessible
	 * within the tcp package. This prevents clients from creatng their own
	 * instances (which might cause the system to crash). Note also that this
	 * constructor does not create a legal connection. We provide it merely to
	 * block clients from invoking the default constructor illegally.
	 */
	TCPInstance() {
		this(null, null, null, null);
	}

	/**
	 * The only constructor for <em>TCPInstance</em>. Note that this constructor
	 * as well as the default constructor are only accessible within this
	 * package. This prevents clients from creating their own instances (which
	 * might cause the system to crash).
	 * 
	 * @param <b>Sched</b> A reference to the <em>Scheduler</em> for the
	 *        transport layer.
	 * @param <b>R</b> A reference to the <em>TransportClient</em> which will
	 *        receive messages for this connection.
	 * @param <b>A</b> The <em>TCPAddress</em> associated with this connection
	 *        instance.
	 * @param <b>S</b> The <em>ServerSocket</em> that should be used to listen
	 *        for new incoming connections.
	 */
	TCPInstance(Scheduler Sched, TransportClient R, TCPAddress A, ServerSocket S) {
		scheduler = Sched;
		recipient = R;
		connectionAddr = A;
		socket = S;
		outgoing = new Hashtable<PhysicalAddress, TCPHandleOutgoing>();
	}

	/**
	 * This method is used to start this instance running. Within this method we
	 * create a single thread to listen to connections on the server socket for
	 * this connection. Later threads are created to handle individual
	 * connections. This method may be called once the constructor has been
	 * called and acceptable values have been provided for the
	 * <em>scheduler</em>, <em>recipient</em>, <em>connectionAddr</em> and
	 * <em>socket</em> fields. Normally, only the <em>TCPTransportLayer</em>
	 * class will call this method.
	 */
	void init() {
		try {
			// Start the server thread and exit
			scheduler.scheduleThread(new Thread(this));
		} catch (IllegalThreadStateException e) {
			Debug.exit("Fatal Error: " + e.toString());
		}
	}

	/**
	 * This method is called once the server socket has been successfully
	 * created and we have been properly initialized. Within this method we
	 * listen on our server socket for new connections. Each new connection is
	 * spawned into a separate thread running in an instance of the
	 * <em>TCPSocketHandler</em> class (see below).
	 */
	public void run() {
		Socket request = null;
		ServerSocket newConnection = null;
		TCPHandleIncoming in = null;
		OutputStream out = null;
		InputStream coordStream = null;
		int newPort = 0;
		boolean coord = false;
		int rPort = 0;
		int next = 0;

		while (true) {
			// Wait on a new connection request
			try {
				request = socket.accept();
			} catch (IOException e) {
				Debug.exit("Error accepting new TCPLayer connection: "
						+ e.toString());
			}

			// Debugging code, comment out later.
			// PRAGMA
			// [debug,osl.transport.TransportLayer,osl.transport.tcp.TCPInstance]
			// Debug.out.println("Accepting new incoming connection request");

			// Ok, we've accepted a new connection. We automatically assume
			// that people connecting on this port are requesting that a new
			// connection be created. So we create a new server socket to
			// handle the connection, send the port back to the requester,
			// wait for the connection to close, and then do it all over
			// again. The first two bytes on the input stream are expected
			// to be the port of the server at the requesting host. We need
			// this so we can build a canonical PhysicalAddress for the
			// remote host.
			try {
				newConnection = new ServerSocket(0);
			} catch (IOException e) {
				Debug.exit("Error creating new connection socket: "
						+ e.toString());
			}

			// Read the callers server port
			try {
				coordStream = request.getInputStream();
				next = coordStream.read();
				rPort = next << 8;
				next = coordStream.read();
				rPort += next;
			} catch (IOException e) {
				Debug
						.exit("Error reading request server port: "
								+ e.toString());
			}

			// Now build an object to handle the request
			in = new TCPHandleIncoming(newConnection, this, new TCPAddress(
					request.getInetAddress(), rPort));

			synchronized (in.startLock) {
				try {
					// We can't reply to the requester until we KNOW that
					// incoming
					// handler thread has started so block here until the thread
					// wakes us up.
					scheduler.scheduleThread(new Thread(in));
					in.startLock.wait();
				} catch (InterruptedException e) {
					Debug
							.exit("TCPInstance thread should never be interrupted!: "
									+ e.toString());
				}
			}

			// Ok, the handler thread has started so send back port info to
			// the requester, then wait for the requester to close the
			// request socket.
			newPort = newConnection.getLocalPort();
			try {
				out = request.getOutputStream();
				out.write((newPort & 0xFF00) >> 8);
				out.write((newPort & 0xFF));
				out.flush();
			} catch (IOException e) {
				Debug.exit("Error writing back new connection port " + newPort
						+ ":" + e.toString());
			}

			coord = false;
			while (!coord) {
				coord = true;
				try {
					coordStream.read();
				} catch (IOException e) {
					coord = false;
				}
			}
		}
	}

	// /////////////////////////////////////////////////////////////
	// Methods which implement TransportInstance
	// /////////////////////////////////////////////////////////////
	/**
	 * Obtain the physical address of this instance. This value may be passed in
	 * messages to allow other transport clients to communicate with this
	 * connection.
	 * 
	 * @return The <em>PhysicalAddress</em> by which this transport instance
	 *         receives messages.
	 */
	public PhysicalAddress transportGetAddress() {
		return connectionAddr;
	}

	/**
	 * Attempt to send a message over the connection. The UDP implementation
	 * currently throws no exceptions while attempting a send.
	 * 
	 * @param <b>dest</b> The <em>PhysicalAddress</em> to which the message
	 *        should be sent.
	 * @param <b>msg</b> The <em>TransportMessage</em> instance to be sent.
	 * @exception osl.transport.TransportException
	 *                Thrown if an error is encountered while attempting the
	 *                send. The actual exception thrown is implementation
	 *                dependent and is encapsulated by this exception.
	 */
	public void transportSend(PhysicalAddress dest, TransportMessage msg)
			throws TransportException {
		// See if we have a conneciton to this destination yet, if not
		// then create one.
		TCPHandleOutgoing out = (TCPHandleOutgoing) outgoing.get(dest);
		if (out == null) {
			out = new TCPHandleOutgoing(this, dest);
			scheduler.scheduleThread(new Thread(out));
			outgoing.put(dest, out);
		}

		// Now tell the new connection handler to send the message
		out.toSend.enqueue(msg);
	}

	/**
	 * Configure the transport layer relative to this connection according to
	 * the options specified in <b>params</b>. The options available and
	 * behavior of this method are implementation dependent. Currently,
	 * TCPInstances have no configuration options.
	 * 
	 * @param <b>params</b> A <em>ParameterList</em> instance which lists
	 *        parameters that should be used to customize this connection. The
	 *        list of acceptable parameters is implementation dependent.
	 * @exception osl.transport.TransportException
	 *                Thrown if there is a problem configuring the connection.
	 *                The actual exception thrown is implementation dependent
	 *                and is encapsulated by this exception.
	 */
	public void transportConfigure(ParameterList params)
			throws TransportException {
	}

	// /////////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////////

	/**
	 * This inner class is used to wait on incoming data from a remote instance
	 * of the transport layer.
	 */
	public class TCPHandleIncoming implements Runnable {
		/**
		 * This field is actually a dummy variable that is used as a lock to
		 * synchronize with our parent when we are created.
		 */
		Integer startLock = new Integer(0);

		/**
		 * This field indicates the physical address of the client we are
		 * receiving messages from.
		 */
		PhysicalAddress caller;

		/**
		 * The <em>TCPInstance</em> we are associated with.
		 */
		TCPInstance parent;

		/**
		 * The server socket we should wait on and eventually receive messages
		 * from.
		 */
		ServerSocket initial;

		/**
		 * The socket we receive data on once the remote host has initiated the
		 * connection.
		 */
		Socket receive;

		/**
		 * The only constructor for this class. Just sets up our parent and
		 * initial fields.
		 */
		TCPHandleIncoming(ServerSocket listen, TCPInstance client,
				PhysicalAddress call) {
			parent = client;
			initial = listen;
			caller = call;
		}

		/**
		 * This is where most of the work is done for this class. We sleep here
		 * waiting for incoming messages. When a message is received we deliver
		 * it our client as indicated by the parent field.
		 */
		public void run() {
			InputStream in = null;
			int expect = 0;
			byte[] data = null;
			int next = 0;

			// Synch with our parent to let it know we are alive and waiting
			// on our connection.
			synchronized (startLock) {
				startLock.notifyAll();
			}

			// Wait for a connection
			try {
				receive = initial.accept();
			} catch (IOException e) {
				Debug.exit("Error waiting for new connection: " + e.toString());
			}

			// Once we get the connection just spin waiting for data on the
			// input stream
			try {
				in = receive.getInputStream();
				while (true) {
					// First two bytes should indicate the length of the data to
					// expect
					next = in.read();
					expect = next << 8;
					next = in.read();
					expect += next;

					// Now read in "expect" bytes from the stream
					data = new byte[expect];
					for (int i = 0; i < expect; i++) {
						data[i] = (byte) in.read();
					}

					// Good, now create a thread to invoke on the receiver and
					// schedule it.
					parent.scheduler.scheduleThread(new Thread(
							new TCPDeliverThread(parent, parent.recipient,
									new TransportMessage(caller,
											connectionAddr, data))));

					// Now spin around and do it all over again
				}
			} catch (IOException e) {
				Debug.exit("Error reading data from stream: " + e.toString());
			}
		}
	}

	/**
	 * This inner class is used to handle outgoing messages to a particular
	 * destination. One instance of this class is created for each unique remote
	 * instance of the TCP transport layer.
	 */
	public class TCPHandleOutgoing implements Runnable {
		/**
		 * This is the address of the server we are sending messages to. We need
		 * this field because server addresses are the only values that are
		 * actually manipulated by clients.
		 */
		PhysicalAddress remoteServer;

		/**
		 * The <em>TCPInstance</em> we are associated with.
		 */
		TCPInstance parent;

		/**
		 * The socket we actually use to send data to the remote object.
		 */
		Socket channel;

		/**
		 * The queue where outgoing messages should be placed. This is a wait
		 * queue which should alert us when a new message needs to be sent out.
		 */
		WaitQueue<TransportMessage> toSend;

		/**
		 * The only constructor for this class. First initialize our fields and
		 * then build a connection to the remote host. This is done by issuing a
		 * request to the remote hosts server, sending the port of our local
		 * server, and then waiting for the port of a remote socket to handle
		 * our connection.
		 */
		TCPHandleOutgoing(TCPInstance client, PhysicalAddress call)
				throws TransportException {
			int newPort = 0;
			parent = client;
			remoteServer = call;
			toSend = new WaitQueue<TransportMessage>();

			Debug.out.println("Building new outgoing request");

			// First bind to the remote server
			TCPAddress S = (TCPAddress) call;
			TCPAddress P = (TCPAddress) parent.connectionAddr;
			try {
				channel = new Socket(S.serverAddr, S.serverPort);
			} catch (IOException e) {
				throw new TransportException(
						"Error creating out channel to remote host " + call, e);
			}

			// Once we get the server, send the port of our local server
			try {
				OutputStream out = channel.getOutputStream();
				out.write((P.serverPort & 0xFF00) >> 8);
				out.write((P.serverPort & 0xFF));
				out.flush();
			} catch (IOException e) {
				throw new TransportException("Error sending server port "
						+ P.serverPort, e);
			}

			// Now wait for two bytes giving the port of the new remote
			// connection
			try {
				InputStream in = channel.getInputStream();
				newPort = (in.read() << 8);
				newPort += in.read();
			} catch (IOException e) {
				throw new TransportException("Error receiving remote port:", e);
			}

			// Once we have the remote port, kill the socket and open a new
			// one to the listed port
			try {
				channel.close();
			} catch (IOException e) {
				throw new TransportException("Error closing server link:", e);
			}
			try {
				channel = new Socket(S.serverAddr, newPort);
			} catch (IOException e) {
				throw new TransportException(
						"Error creating new connection socket: ", e);
			}

			// We're done
		}

		/**
		 * All we do here is wait until someone dumps something in the "toSend"
		 * queue. When that happens we first send out two bytes indicating the
		 * length of the stream, then we send the stream itself.
		 */
		public void run() {
			OutputStream out = null;
			int expect = 0;
			TransportMessage next = null;

			try {
				out = channel.getOutputStream();
			} catch (IOException e) {
				Debug
						.exit("Fatal error: lost socket connection in out handler:"
								+ e.toString());
			}

			// Spin forever
			while (true) {
				// See if we have anything to send
				synchronized (toSend) {
					if (toSend.empty())
						try {
							toSend.wait();
						} catch (InterruptedException e) {
							Debug
									.exit("Error: waiting socket handler should never be interrupted!:"
											+ e.toString());
						}
				}

				// Looks like we got something so send it
				try {
					next = (TransportMessage) toSend.dequeue();
					expect = next.contents.length;
					out.write(((expect & 0xFF00) >> 8));
					out.write((expect & 0xFF));
					out.write(next.contents);
					out.flush();
				} catch (IOException e) {
					Debug.exit("Error writing out message data: "
							+ e.toString());
				}

				// Now loop again
			}
		}
	}
}
