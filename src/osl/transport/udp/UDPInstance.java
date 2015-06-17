package osl.transport.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Enumeration;
import java.util.Hashtable;

import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportMessage;
import osl.util.Debug;
import osl.util.ParameterList;
import osl.util.Queue;
import osl.util.WaitQueue;

/**
 * This class implements an instance of a UDP transport layer connection.
 * Clients use this connection to send messages. The instance is also
 * responsible for receiving incoming messages and invoking the appropriate
 * methods on the receiving client. This class implements the
 * <em>TransportInstance</em> interface.
 * 
 * @author Mark Astley
 * @version $Revision: 1.6 $ ($Date: 1999/01/19 18:43:36 $)
 * @see osl.transport.TransportLayer
 * @see osl.transport.TransportInstance
 * @see UDPTransportLayer
 */

class UDPInstance implements TransportInstance {
	/**
	 * Set these to true if you want debugging output.
	 */
	public static final boolean DEBUG = false;
	public static final boolean RAW_BYTE_DEBUG = false;
	public static final boolean SEND_RCV_DEBUG = false;

	/**
	 * This is the timer used to periodically send ALIVE messages if necessary.
	 */
	long lastALIVESent;

	/**
	 * If <b>true</b> then this connection is reliable. Otherwise it is
	 * unreliable. This field affects whether or not ALIVE messages are sent and
	 * how unacknowledged messages are handled. Reliability features are
	 * communicated in all UDP packets so that reliable and unreliable
	 * connection instances may safely interact with one another. At the moment,
	 * we don't support an unreliable version of this transport.
	 */
	boolean reliable;

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
	 */
	UDPAddress connectionAddr;

	/**
	 * TransportClients periodically add TransportMessages to this queue to
	 * request that messages be sent. The "send" thread periodically checks this
	 * queue to determine if a new message needs to be sent.
	 */
	WaitQueue<Object> msgsToSend;

	/**
	 * This queue is used to hold GC packets. These packets are processed by a
	 * dedicated thread which frees up the main thread from having to waste time
	 * here.
	 */
	WaitQueue<Enumeration<?>> gcPackets;

	/**
	 * The socket used by this transport instance.
	 */
	DatagramSocket socket;

	/**
	 * This hashtable hashes sending addresses of packets we have received (i.e.
	 * contents of receiver field in TransportMessage) to the sequence number of
	 * the last message that was received without missing any intermediary
	 * messages. We use these values to send periodic garbage collection
	 * messages. Note that even if this connection was opened as unreliable will
	 * still need to send GC and messages in case a sender was opened expecting
	 * a reliable connection.
	 */
	Hashtable<UDPAddress, Integer> globalSeqNum;

	/**
	 * This hashtable hashes destination addresses to the next sequence number
	 * that address is expecting. We use these values to order data packets to
	 * different hosts.
	 */
	Hashtable<UDPAddress, Integer> destSeqNum;

	/**
	 * This hashtable hashes sender addresses to a hashtable which hashes global
	 * sequence numbers to instances of UDPDataPacket. Packets received out of
	 * order are held here until the appropriate intermediate packets are
	 * delivered. In the case of an unreliable connection, we periodically empty
	 * this hashtable based on how long incomplete data has been stored in the
	 * table.
	 */
	Hashtable<UDPAddress, Hashtable<Integer, UDPDataPacket>> waitingPackets;

	/**
	 * This queue maintains a list of <em>UDPResend</em> structures that need to
	 * be resent. The send thread dumps these packets to the network each time
	 * it goes through its processing loop.
	 */
	Queue<Object> toResend;

	/**
	 * This hashtable hashes destination addresses to the last seqnumber that
	 * was GC'd for this destination.
	 */
	Hashtable<UDPAddress, Integer> lastGCd;

	/**
	 * This hashtable hashes destination addresses (i.e. instances of
	 * <em>UDPAddress</em>) to a hashtable which hashes sequence numbers to the
	 * original datagram packets corresponding to that sequence number. Messages
	 * are stored in this structure until they are GC'd.
	 */
	Hashtable<UDPAddress, Hashtable<Integer, DatagramPacket>> notGCd;

	/**
	 * This hashtable hashes UDPAddresses to arrays of type UDPDataPacket which
	 * indicate portions of the next complete data sequence we are receiving
	 * from the specified address. Each array is removed and converted into a
	 * UDPMessage when all of its packets have been received.
	 */
	Hashtable<UDPAddress, UDPDataPacket[]> nextMsg;

	boolean needGC = false;

	/**
	 * The default constructor. Note that this constructor is only accessible
	 * within the udp package. This prevents clients from creatng their own
	 * instances (which might cause the system to crash). Note also that this
	 * constructor does not create a legal connection. We provide it merely to
	 * block clients from invoking the default constructor illegally.
	 */
	UDPInstance() {
		this(null, null, null, null);
	}

	/**
	 * The only constructor for <em>UDPInstance</em>. Note that this constructor
	 * as well as the default constructor are only accessible within this
	 * package. This prevents clients from creating their own instances (which
	 * might cause the system to crash).
	 * 
	 * @param <b>Sched</b> A reference to the <em>Scheduler</em> for the
	 *        transport layer.
	 * @param <b>R</b> A reference to the <em>TransportClient</em> which will
	 *        receive messages for this connection.
	 * @param <b>A</b> The <em>UDPAddress</em> associated with this connection
	 *        instance.
	 * @param <b>S</b> The <em>DatagramSocket</em> that is used to send and
	 *        receive messages for this connection.
	 */
	UDPInstance(Scheduler Sched, TransportClient R, UDPAddress A,
			DatagramSocket S) {
		lastALIVESent = System.currentTimeMillis();
		reliable = true;
		scheduler = Sched;
		recipient = R;
		connectionAddr = A;
		msgsToSend = new WaitQueue<Object>();
		gcPackets = new WaitQueue<Enumeration<?>>();
		socket = S;
		globalSeqNum = new Hashtable<UDPAddress, Integer>();
		destSeqNum = new Hashtable<UDPAddress, Integer>();
		waitingPackets = new Hashtable<UDPAddress, Hashtable<Integer, UDPDataPacket>>();
		toResend = new Queue<Object>();
		lastGCd = new Hashtable<UDPAddress, Integer>();
		notGCd = new Hashtable<UDPAddress, Hashtable<Integer, DatagramPacket>>();
		nextMsg = new Hashtable<UDPAddress, UDPDataPacket[]>();

		sendThreads = new SendThread[1];
		for (int i = 0; i < sendThreads.length; i++) {
			sendThreads[i] = new SendThread();
			sendThreads[i].start();
		}
	}

	class DestMsg {
		public DestMsg(PhysicalAddress dest, TransportMessage msg) {
			super();
			this.dest = dest;
			this.msg = msg;
		}

		PhysicalAddress dest;
		TransportMessage msg;
	}

	Queue<DestMsg> mailQueue = new Queue<DestMsg>();

	/**
	 * This method is used to start this instance running. Within this method we
	 * create two threads to handle outgoing and incoming messages. The threads
	 * created are instances <em>UDPTransportThread</em> and either run in
	 * either the startSendThread or startRcvThread methods of this class. This
	 * method may be called once the constructor has been called and acceptable
	 * values have been provided for the <em>scheduler</em>, <em>recipient</em>,
	 * <em>connectionAddr</em> and <em>socket</em> fields. Normally, only the
	 * <em>UDPTransportLayer</em> class will call this method.
	 */
	void init() {
		try {
			// Start the send and receive threads and exit
			scheduler.scheduleThread(new Thread(new UDPTransportThread(this,
					UDPTransportThread.RUN_SEND_THREAD)));
			scheduler.scheduleThread(new Thread(new UDPTransportThread(this,
					UDPTransportThread.RUN_RCV_THREAD)));
			scheduler.scheduleThread(new Thread(new UDPTransportThread(this,
					UDPTransportThread.RUN_GC_THREAD)));
		} catch (IllegalThreadStateException e) {
			Debug.exit("Fatal Error: " + e.toString());
		}
	}

	/**
	 * This method should be called by the "send" thread for this transport
	 * instance. This thread will never exit and will continually monitor the
	 * <em>msgsToSend</em> queue for new outgoing messages.
	 */
	void startSendThread() {
		long cTime = 0;
		long waitTime = 0;

		// PRAGMA [SEND_RCV_DEBUG] Log.println(FoundryStart.sysLog,
		// "<UDPInstance.startSendThread> Send thread started");

		while (true) {
			// Sleep until either a new message needs to be sent or we wake
			// up because we have to send a GC message to clients we have
			// received messages from.
			try {
				synchronized (msgsToSend) {
					if (msgsToSend.empty()) {
						if (needGC) {
							waitTime = Math
									.max(
											5,
											(UDPTransportLayer.SEND_ALIVE_TIMEOUT - (System
													.currentTimeMillis() - lastALIVESent)));
							msgsToSend.wait(waitTime);
						} else
							msgsToSend.wait();
					}
				}
			} catch (InterruptedException e) {
				Debug
						.exit("Fatal Error: sender thread should never be interrupted!!!");
			}

			// See if there is a new message to send out
			// PRAGMA [assert] Assert.assert(msgsToSend.empty(),
			// "this should never happen");

			// Check to see if we should send an ALIVE message
			cTime = System.currentTimeMillis();
			if ((cTime - lastALIVESent) > UDPTransportLayer.SEND_ALIVE_TIMEOUT) {
				// This will be set to true if we had to send an alive message
				// here.
				needGC = false;

				// Scan through the destSeqNum hashtable if there is an entry
				// (A, X) such that there is an entry (A', X') in lastGCd with
				// A = A' and X' < X - 1, then send an alive message.
				Enumeration<UDPAddress> e;
				for (e = destSeqNum.keys(); e.hasMoreElements();) {
					UDPAddress nextAddr = (UDPAddress) e.nextElement();
					Integer nextSeq = (Integer) destSeqNum.get(nextAddr);
					Integer gcLast = (Integer) lastGCd.get(nextAddr);
					if (gcLast == null) {
						gcLast = new Integer(-1);
						lastGCd.put(nextAddr, gcLast);
					}

					// It doesn't make sense to me that nextSeq could be NULL at
					// this point but apparently it can be so I have to put the
					// check here anyway.
					if (nextSeq != null)
						if (gcLast.intValue() < (nextSeq.intValue() - 1)) {

							// PRAGMA [SEND_RCV_DEBUG]
							// Log.println(FoundryStart.sysLog,
							// "<UDPInstance.startSendThread> Sending ALIVE from: "
							// + connectionAddr + " to: " + nextAddr +
							// " for seq num: " + (nextSeq.intValue() - 1));

							generateALIVE(nextAddr, (nextSeq.intValue() - 1));
							needGC = true;
						}
				}
				lastALIVESent = cTime;
			}

			// Now dump out any messages waiting in the toResend queue.
			// These are datagrams that need to be resent because we
			// received a NACK or whatever. Note that we only dump a fixed
			// number of packets here so we don't end up getting stuck in
			// the loop.
			// PRAGMA [assert] Assert.assert(toResend.empty(),
			// "this should never happen");

			// if (!toResend.empty())
			// for(int toDequeue = toResend.numElements(); toDequeue > 0;
			// toDequeue--) {
			// DatagramPacket resendPacket = (DatagramPacket)
			// toResend.dequeue();
			// try {
			// socket.send(resendPacket);
			// } catch(IOException e) {
			// Debug.exit(e.toString());
			// }
			// }
		}
	}

	/**
	 * This method should be called to start the "receive" thread for this
	 * object. This thread will never exit and will usually be blocked on the
	 * associated <em>socket</em> waiting for incoming messages.
	 */
	public void startRcvThread() {
		byte[] inRawData = new byte[UDPTransportLayer.PACKET_SIZE];
		DatagramPacket nextPacket = new DatagramPacket(inRawData,
				UDPTransportLayer.PACKET_SIZE);
		int packetSeq = 0;
		int packetType = 0;
		byte[] packetIn = null;
		int curStreamNum = 0;
		int streamLength = 0;
		int totBytes = 0;
		UDPDataPacket nextDataPack = null;
		byte[] nextRawData = null;
		UDPAddress senderAddr = null;
		Hashtable<Integer, DatagramPacket> heldMsgs = null;
		Integer gClast = null;

		try {
			while (true) {
				// Wait for a new packet
				socket.receive(nextPacket);

				// Read the type of the new packet
				packetIn = nextPacket.getData();
				// streamIn.reset();
				// serialIn = new ObjectInputStream(streamIn);
				// packetType = serialIn.readInt();
				packetType = readByte(packetIn, 0);

				// Take appropriate action based on the type of packet
				switch (packetType) {
				case UDPTransportLayer.MESSAGE_TYPE_DATA:
					// PRAGMA [DEBUG]
					// Debug.out.println("Received a DATA packet");

					// Decypher the header of this packet, extract the data
					// portion, and build a UDPDataPacket structure
					senderAddr = new UDPAddress(nextPacket.getAddress(),
							nextPacket.getPort());
					packetSeq = readInt(packetIn, 1);
					curStreamNum = readShort(packetIn, 5);
					streamLength = readShort(packetIn, 7);
					totBytes = readShort(packetIn, 9);
					nextRawData = new byte[UDPTransportLayer.PACKET_SIZE - 11];
					System.arraycopy(packetIn, 11, nextRawData, 0,
							UDPTransportLayer.PACKET_SIZE - 11);
					nextDataPack = new UDPDataPacket(packetSeq, curStreamNum,
							streamLength, totBytes, nextRawData);

					// PRAGMA [SEND_RCV_DEBUG] Log.println(FoundryStart.sysLog,
					// "<UDPInstance.startRcvThread> Received data packet - sender: "
					// + senderAddr + " seq_num: " + packetSeq);

					// PRAGMA [DEBUG]
					// Debug.out.println("*** UDPInstance ***: received data packet - sender: "
					// + senderAddr + " seq_num: " + packetSeq);
					// PRAGMA [DEBUG] Debug.out.println("Packet seq num    : " +
					// packetSeq);
					// PRAGMA [DEBUG] Debug.out.println("Current stream num: " +
					// curStreamNum);
					// PRAGMA [DEBUG] Debug.out.println("Total stream len  : " +
					// streamLength);
					// PRAGMA [DEBUG] Debug.out.println("Total num bytes   : " +
					// totBytes);
					// PRAGMA [RAW_BYTE_DEBUG] {
					// PRAGMA [RAW_BYTE_DEBUG] byte[] rawD1 = nextRawData;
					// PRAGMA [RAW_BYTE_DEBUG] String rawD2 = "";
					// PRAGMA [RAW_BYTE_DEBUG] for(int q=0; q<rawD1.length; q++)
					// PRAGMA [RAW_BYTE_DEBUG] rawD2 = rawD2 + rawD1[q] + " ";
					// PRAGMA [RAW_BYTE_DEBUG]
					// Debug.out.println("Received raw bytes: \n" + rawD2);
					// PRAGMA [RAW_BYTE_DEBUG] }

					// Handle the new packet based on the address of the sender
					handleNewDataPacket(senderAddr, nextDataPack);

					break;

				case UDPTransportLayer.MESSAGE_TYPE_GC:
					// PRAGMA [DEBUG] Debug.out.println("Received a GC packet");

					// Figure out who we can GC messages for, also grab the last
					// message number we GCd for this receiver
					senderAddr = new UDPAddress(nextPacket.getAddress(),
							nextPacket.getPort());
					packetSeq = readInt(packetIn, 1);

					// Dump the packet parameters into the gcPackets queue which
					// should automatically wake up the GC thread.
					gcPackets.enqueue((new ParameterList(senderAddr,
							new Integer(packetSeq))).enumerate());

					break;

				case UDPTransportLayer.MESSAGE_TYPE_ALIVE:
					// PRAGMA [DEBUG]
					// Debug.out.println("Received an ALIVE packet");

					// Figure out who is sending the message and the sequence
					// number of the last data packet they sent.
					senderAddr = new UDPAddress(nextPacket.getAddress(),
							nextPacket.getPort());
					packetSeq = readInt(packetIn, 1);
					gClast = (Integer) globalSeqNum.get(senderAddr);
					if (gClast == null) {
						gClast = new Integer(0);
						globalSeqNum.put(senderAddr, gClast);
					}

					if (packetSeq >= gClast.intValue())
						// If packetSeq >= gClast then we missed a message so
						// send a
						// NACK with value gClast.
						generateNACK(senderAddr, gClast.intValue());
					else
						// Otherwise, it may be that the sender missed our last
						// GC
						// message so send another
						generateGC(senderAddr, gClast.intValue() - 1);

					break;

				case UDPTransportLayer.MESSAGE_TYPE_NACK:
					// PRAGMA [DEBUG]
					// Debug.out.println("Received a NACK packet");

					// Figure out who is missing a packet and which one
					senderAddr = new UDPAddress(nextPacket.getAddress(),
							nextPacket.getPort());
					packetSeq = readInt(packetIn, 1);

					// Two cases: either the packet was missed and we still have
					// it, or this is a late NACK message and we've already GC'd
					// the packet.
					heldMsgs = (Hashtable<Integer, DatagramPacket>) notGCd
							.get(senderAddr);
					if (heldMsgs == null) {
						heldMsgs = new Hashtable<Integer, DatagramPacket>();
						notGCd.put(senderAddr, heldMsgs);
					}
					if (heldMsgs.containsKey(new Integer(packetSeq)))
						socket.send((DatagramPacket) heldMsgs.get(new Integer(
								packetSeq)));
					// toResend.enqueue(heldMsgs.get(new Integer(packetSeq)));

					// Need to wake up the sender thread if it isn't already
					// awake
					// synchronized (msgsToSend) {
					// msgsToSend.notifyAll();
					// }

					break;

				default:
					Debug.exit("ERROR: unknown packet type: " + packetType);
				}
			}
		} catch (Exception e) {
			Debug.exit(e.toString());
		}

	}

	/**
	 * This function is the starting point for the GC thread. We create a
	 * separate GC thread so that the main receive thread is not encumbered by
	 * garbage collection activities. This thread suspends on the
	 * <em>gcPackets</em> queue waiting for GC messages.
	 */
	void startGCThread() {
		Enumeration<?> nextReq;
		UDPAddress senderAddr;
		Integer packetSeq;
		Hashtable<Integer, DatagramPacket> heldMsgs;
		Integer gClast;
		int bound;

		// This thread only exits when the foundry crashes
		while (true) {
			// Wait until there is a new GC packet
			try {
				synchronized (gcPackets) {
					if (gcPackets.empty())
						gcPackets.wait();
				}
			} catch (InterruptedException e) {
				Debug.exit("GC thread should never be interrupted!: "
						+ e.toString());
			}

			nextReq = (Enumeration<?>) gcPackets.dequeue();
			senderAddr = (UDPAddress) nextReq.nextElement();
			packetSeq = (Integer) nextReq.nextElement();
			heldMsgs = (Hashtable<Integer, DatagramPacket>) notGCd
					.get(senderAddr);
			bound = packetSeq.intValue();

			if (heldMsgs == null) {
				heldMsgs = new Hashtable<Integer, DatagramPacket>();
				notGCd.put(senderAddr, heldMsgs);
			}
			gClast = (Integer) lastGCd.get(senderAddr);
			if (gClast == null)
				gClast = new Integer(-1);

			// PRAGMA [DEBUG] Debug.out.println("Message sent from: " +
			// senderAddr);
			// PRAGMA [DEBUG] Debug.out.println("GC messages <= " + packetSeq);

			if (bound > gClast.intValue()) {
				// Now remove any messages satisfying:
				// gClast < msg_num <= packetSeq
				for (int rem = gClast.intValue() + 1; rem <= bound; rem++) {
					// PRAGMA [DEBUG]
					// Debug.out.println("Removing message with ID: " + rem);
					notGCd.remove(new Integer(rem));
				}

				// Update lastGCd
				lastGCd.put(senderAddr, packetSeq);
			}
		}
	}

	/**
	 * This function takes a raw data stream and build a collection of data
	 * packets which are ready to be sent immediately over the wire. Note, the
	 * new nextSeqNum value may be computed by: oldSeqNum +
	 * length_of_returned_array
	 * 
	 * @param <b>nextSeqNum</b> The integer sequence number which should be
	 *        assigned to the first packet in the data stream.
	 * @param <b>rawData</b> The array of raw data that will be included in the
	 *        data packets.
	 * @return An array of UDPTransportLayer.PACKET_SIZE byte arrays.
	 */
	private byte[][] buildDataPackets(int nextSeqNum, byte[] rawData) {
		int curSeqNum = nextSeqNum;
		int currentDataLoc = 0;
		byte[][] returnArray;
		int numRawPackets;
		int toCopy;

		// Figure out how many packets we'll need total
		numRawPackets = rawData.length / UDPTransportLayer.RAW_DATA_SIZE;
		if ((rawData.length % UDPTransportLayer.RAW_DATA_SIZE) > 0)
			numRawPackets++;

		// Build the return array
		returnArray = new byte[numRawPackets][UDPTransportLayer.PACKET_SIZE];

		// Now start building packets
		for (int i = 0; i < numRawPackets; i++, currentDataLoc += UDPTransportLayer.RAW_DATA_SIZE) {

			// Write out message header
			writeByte(returnArray[i], 0, UDPTransportLayer.MESSAGE_TYPE_DATA);
			writeInt(returnArray[i], 1, curSeqNum);
			writeShort(returnArray[i], 5, i);
			writeShort(returnArray[i], 7, numRawPackets);
			writeShort(returnArray[i], 9, rawData.length);
			curSeqNum++;

			// Fill the rest of this packet with data
			toCopy = UDPTransportLayer.PACKET_SIZE - 11;
			if (toCopy > (rawData.length - currentDataLoc))
				toCopy = rawData.length - currentDataLoc;
			System.arraycopy(rawData, currentDataLoc, returnArray[i], 11,
					toCopy);
		}

		// Finally, return the array of packets to the caller
		return returnArray;
	}

	/**
	 * This function figures out what to do with a new data packet. There are
	 * basically two options. If this packet was expected next (as determined by
	 * its sequence number), then we use it to start or finish building a new
	 * message for the user. If, on the other hand, this packet was received out
	 * of order then we store it in the waiting area and generate a NACK message
	 * to request the missing pockets.
	 */
	void handleNewDataPacket(UDPAddress sender, UDPDataPacket packet) {
		Hashtable<Integer, UDPDataPacket> waitArea = null;
		Integer nextExpected = null;
		UDPDataPacket[] newMsg = null;

		// Store the message in the waiting area
		waitArea = (Hashtable<Integer, UDPDataPacket>) waitingPackets
				.get(sender);
		if (waitArea == null) {
			waitArea = new Hashtable<Integer, UDPDataPacket>();
			waitingPackets.put(sender, waitArea);
		}
		waitArea.put(new Integer(packet.seqNum), packet);
		// PRAGMA [DEBUG]
		// Debug.out.println("*** UDPInstance ***: New packet put in waiting area");

		// Figure out the next packet we expect from this sender
		nextExpected = (Integer) globalSeqNum.get(sender);
		if (nextExpected == null) {
			nextExpected = new Integer(0);
			globalSeqNum.put(sender, nextExpected);
		}
		// PRAGMA [DEBUG]
		// Debug.out.println("*** UDPInstance ***: Next expected packet is " +
		// nextExpected);

		// Now keep processing packets until we either run out or we can't
		// find the next expected packet
		while ((!waitArea.isEmpty()) && (waitArea.containsKey(nextExpected))) {

			// Get the next ready packet. Don't forget to delete it out of
			// the wait area.
			UDPDataPacket nextPack = (UDPDataPacket) waitArea.get(nextExpected);
			waitArea.remove(nextExpected);
			// PRAGMA [DEBUG]
			// Debug.out.println("*** UDPInstance ***: Handling packet " +
			// nextPack);

			// If there is already a message being constructed for this
			// sender, then add the new packet to the end. Otherwise this
			// data packet must be the beginning of a new message.
			newMsg = (UDPDataPacket[]) nextMsg.get(sender);

			if (newMsg == null) {
				// Ok, this is the start of a new message. Need to figure out
				// how big the new message will be and allocate the
				// appropriate space for it.
				// PRAGMA [DEBUG]
				// Debug.out.println("Starting new user level message");
				newMsg = new UDPDataPacket[nextPack.totPackets];
				nextMsg.put(sender, newMsg);
			}

			// Add the new packet
			newMsg[nextPack.streamNum] = nextPack;

			// Now check to see if the new message is completed. If so then
			// build a UDPMessage and store it in the msgsReceived queue.
			if ((nextPack.streamNum + 1) == nextPack.totPackets) {
				// PRAGMA [DEBUG]
				// Debug.out.println("User level message complete, delivering");
				buildNewRcvdMsg(sender, newMsg);
				nextMsg.remove(sender);
			}

			// Increment next expected
			nextExpected = new Integer(nextExpected.intValue() + 1);
			globalSeqNum.put(sender, nextExpected);
		}

		// If we couldn't find the next message then generate a NACK
		if (!waitArea.isEmpty())
			generateNACK(sender, nextExpected.intValue());
	}

	/**
	 * This function build a UDPMessage structure out of an array of
	 * UDPDataPackets which represent the in-order segmentation of a message
	 * into individual stream packets. To handle the message, we spawn a new
	 * thread and call the <em>transportReceive</em> method in the client for
	 * this connection.
	 */
	void buildNewRcvdMsg(UDPAddress sender, UDPDataPacket[] newStream) {
		byte[] rawData = new byte[newStream[0].totBytes];

		// First copy all the raw data into a byte stream
		for (int i = 0, j = 0, k = newStream[0].totBytes; i < newStream.length; i++, j += UDPTransportLayer.RAW_DATA_SIZE, k -= UDPTransportLayer.RAW_DATA_SIZE)
			System.arraycopy(newStream[i].rawData, 0, rawData, j, Math.min(k,
					UDPTransportLayer.RAW_DATA_SIZE));

		// Now build a new message and deliver it to the client for this
		// connection.
		// PRAGMA [SEND_RCV_DEBUG] {
		// PRAGMA [SEND_RCV_DEBUG] String range="[ ";
		// PRAGMA [SEND_RCV_DEBUG] for(int foo=0; foo<newStream.length; foo++)
		// PRAGMA [SEND_RCV_DEBUG] range=range + newStream[foo].seqNum + " ";
		// PRAGMA [SEND_RCV_DEBUG] range=range+"]";
		// PRAGMA [SEND_RCV_DEBUG] Log.println(FoundryStart.sysLog,
		// "<UDPInstance.buildNewRcvdMsg> Delivering message with sender: " +
		// sender + " receiver: " + connectionAddr+
		// " and packet range: "+range);
		// PRAGMA [SEND_RCV_DEBUG] }

		scheduler.scheduleThread(new Thread(new UDPDeliverThread(this,
				recipient,
				new TransportMessage(sender, connectionAddr, rawData))));

		// PRAGMA [SEND_RCV_DEBUG] Log.println(FoundryStart.sysLog,
		// "<UDPInstance.buildNewRcvdMsg> Delivery thread started, returning");

	}

	/**
	 * This function builds a NACK datagram addressed to the given
	 * <em>sender</em> requesting a resend of the data packet with sequence
	 * number <em>missingNum</em>. The NACK message is automatically stored in
	 * the <em>toResend</em> queue so that it is sent the next time the sender
	 * thread gets a chance.
	 */
	void generateNACK(UDPAddress sender, int missingNum) {
		// PRAGMA [DEBUG] Debug.out.println("Sending NACK message to " + sender
		// + " with val " + missingNum);
		generateControlMessage(sender, missingNum,
				UDPTransportLayer.MESSAGE_TYPE_NACK);
	}

	/**
	 * This function builds a GC datagram addressed to the given <em>sender</em>
	 * alerting the receiver that it may GC certain UDPDataPacket structures.
	 * The GC message is automatically stored in the <em>toResend</em> queue so
	 * that it is sent the next time the sender thread gets a chance.
	 */
	void generateGC(UDPAddress sender, int gcNum) {
		// PRAGMA [DEBUG] Debug.out.println("Sending GC message to " + sender +
		// " with val " + gcNum);
		generateControlMessage(sender, gcNum, UDPTransportLayer.MESSAGE_TYPE_GC);
	}

	/**
	 * This function builds an ALIVE datagram addressed to the given
	 * <em>sender</em> alerting the receiver to the value of the last packet
	 * sent. This value tells the receiver whether or not it has received every
	 * packet sent by the sender. The ALIVE message is automatically stored in
	 * the <em>toResend</em> queue so that it is sent the next time the sender
	 * thread gets a chance.
	 */
	void generateALIVE(UDPAddress sender, int aliveNum) {
		// PRAGMA [DEBUG] Debug.out.println("Sending ALIVE message to " + sender
		// + " with val " + aliveNum);
		generateControlMessage(sender, aliveNum,
				UDPTransportLayer.MESSAGE_TYPE_ALIVE);
	}

	/**
	 * This function generates a generic control message which may be one of
	 * MESSAGE_TYPE_NACK, MESSAGE_TYPE_ALIVE, or MESSAGE_TYPE_GC. The message is
	 * automatically inserted in the <em>toResend</em> queue so that the
	 * datagram is sent the next time the sending thread executes.
	 */
	void generateControlMessage(UDPAddress sender, int missingNum, int type) {
		DatagramPacket outPacket = null;
		byte[] outData = new byte[UDPTransportLayer.PACKET_SIZE];

		// Write out message header
		writeByte(outData, 0, type);
		writeInt(outData, 1, missingNum);

		// Create new packet
		outPacket = new DatagramPacket(outData, UDPTransportLayer.PACKET_SIZE,
				sender.hostAddr, sender.hostPort);
		try {
			socket.send(outPacket);
		} catch (IOException e) {
			Debug.exit(e.toString());
		}
		// toResend.enqueue(outPacket);

		// Now wake up the sending thread if it isn't already
		// synchronized (msgsToSend) {
		// msgsToSend.notifyAll();
		// }
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

	private SendThread[] sendThreads;

	class SendThread extends Thread {

		@Override
		public void run() {
			while (true) {
				if (mailQueue.empty()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					DestMsg destMsg = mailQueue.dequeue();
					if (destMsg != null) {
						try {
//							long aa1;
//							aa1 = System.currentTimeMillis();
							transportActualSend(destMsg.dest, destMsg.msg);
//							System.out.println(">> "+(System.currentTimeMillis()-aa1));
						} catch (TransportException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	}

	public void transportSend(PhysicalAddress dest, TransportMessage msg)
			throws TransportException {
		mailQueue.enqueue(new DestMsg(dest, msg));

	}

	private void transportActualSend(PhysicalAddress dest, TransportMessage msg)
			throws TransportException {
		try {
			UDPAddress destAddr = (UDPAddress) dest;
			byte[][] outPackets;

			// Construct a set of packets to send by segmenting the user's
			// data into UDPTransportLayer.PACKET_SIZE byte blocks
			int nextSeq;
			Integer temp = (Integer) destSeqNum.get(destAddr);
			if (temp == null)
				nextSeq = 0;
			else
				nextSeq = temp.intValue();

			outPackets = buildDataPackets(nextSeq, msg.contents);
			destSeqNum.put(destAddr, new Integer(nextSeq + outPackets.length));
			// Now blast the packets out. Before sending each one out,
			// save it in the toResend hashtable in case it needs to be
			// resent.
			Hashtable<Integer, DatagramPacket> sentMsgs = (Hashtable<Integer, DatagramPacket>) notGCd
					.get(destAddr);
			if (sentMsgs == null) {
				sentMsgs = new Hashtable<Integer, DatagramPacket>();
				notGCd.put(destAddr, sentMsgs);
			}

			for (int i = 0; i < outPackets.length; i++) {
				DatagramPacket outPacket = new DatagramPacket(outPackets[i],
						UDPTransportLayer.PACKET_SIZE, destAddr.hostAddr,
						destAddr.hostPort);
				sentMsgs.put(new Integer(nextSeq + i), outPacket);
				socket.send(outPacket);
			}

			// Anytime we dump messages into the sent messages table, set
			// needGC to true so we can generate ALIVE messages if
			// necessary.
			needGC = true;

			synchronized (msgsToSend) {
				msgsToSend.notify();
			}
		} catch (Exception e) {
			throw new TransportException("error sending message", e);
		}
	}

	/**
	 * Configure the transport layer relative to this connection according to
	 * the options specified in <b>params</b>. The options available and
	 * behavior of this method are implementation dependent. Currently,
	 * UDPInstances have no configuration options.
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
	// Utility Routines for Building Message Headers
	// /////////////////////////////////////////////////////////////

	void writeByte(byte[] header, int index, int data) {
		header[index] = (byte) data;
	}

	int readByte(byte[] header, int index) {
		return (0x000000FF & header[index]);
	}

	void writeShort(byte[] header, int index, int data) {
		header[index++] = (byte) (data >>> 8);
		header[index] = (byte) (data >>> 0);
	}

	int readShort(byte[] header, int index) {
		return (readByte(header, index) << 8)
				+ (readByte(header, index + 1) << 0);
	}

	void writeInt(byte[] header, int index, int data) {
		header[index++] = (byte) (data >>> 24);
		header[index++] = (byte) (data >>> 16);
		header[index++] = (byte) (data >>> 8);
		header[index] = (byte) (data >>> 0);
	}

	int readInt(byte[] header, int index) {
		return ((readByte(header, index) << 24)
				+ (readByte(header, index + 1) << 16)
				+ (readByte(header, index + 2) << 8) + (readByte(header,
				index + 3) << 0));

	}

	// /////////////////////////////////////////////////////////////
	// Inner Classes
	// /////////////////////////////////////////////////////////////

	/**
	 * This class stores incoming data packets until we are ready to compose
	 * them into a message delivered to the user.
	 */
	public class UDPDataPacket {
		/**
		 * The global sequence number of this packet
		 */
		public int seqNum;

		/**
		 * The stream sequence number of this pocket
		 */
		public int streamNum;

		/**
		 * The total number of stream packets for this message.
		 */
		public int totPackets;

		/**
		 * The total number of data bytes for this message. NOTE: This does NOT
		 * correspond to the length of the <em>rawData</em> array below.
		 */
		public int totBytes;

		/**
		 * The raw data contained in this packet.
		 */
		public byte[] rawData;

		// Constructors
		public UDPDataPacket() {
			this(-1, -1, -1, -1, null);
		}

		public UDPDataPacket(int seq, int stream, int totS, int totB,
				byte[] data) {
			seqNum = seq;
			streamNum = stream;
			totPackets = totS;
			totBytes = totB;
			rawData = data;
		}

		public String toString() {
			return "UDPDataPacket: seqNum = " + seqNum + " streamNum = "
					+ streamNum + " totPackets = " + totPackets
					+ " totBytes = " + totBytes;
		}
	}

}
