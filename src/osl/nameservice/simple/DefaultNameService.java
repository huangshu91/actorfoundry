package osl.nameservice.simple;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import osl.nameservice.MalformedNameException;
import osl.nameservice.Name;
import osl.nameservice.NameNotFoundException;
import osl.nameservice.NameService;
import osl.nameservice.NoBindingException;
import osl.nameservice.NoSuchAddressException;
import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;
import osl.transport.TransportClient;
import osl.transport.TransportException;
import osl.transport.TransportInstance;
import osl.transport.TransportLayer;
import osl.transport.TransportMessage;
import osl.util.Assert;
import osl.util.Debug;
import osl.util.QueueSearch;
import osl.util.WaitQueue;

/**
 * This class defines the "default" implementation of the <em>NameService</em>
 * interface. The default implementation correponds to the original
 * implementation of the nameservice in the <em>ActorManager</em> defined in
 * version 1.0b of the foundry. If you are planning on supporting fault-tolerant
 * foundry nodes, or if you expect actors to migrate quite often (e.g. after
 * every message processed) then this is not a terribly great nameservice to
 * use. However, this nameservice SHOULD be useful for most common actor
 * programs.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.8 $ ($Date: 1999/12/29 03:16:21 $)
 * @see osl.nameservice.Name
 * @see osl.nameservice.NameService
 * @see osl.handler.RequestHandler
 * @see DefaultName
 */

public class DefaultNameService implements NameService, TransportClient {
	/**
	 * The transport layer we should use to interact with other default
	 * nameservice instances.
	 */
	TransportLayer ourTransport;

	/**
	 * The transport session this nameservice instance uses to interact through
	 * the transport layer.
	 */
	TransportInstance transInstance;

	/**
	 * The physical address associated with this nameservice instance.
	 */
	PhysicalAddress nsAddr;

	/**
	 * The integer ID of the next name to be created by this nameservice
	 * instance. Note that this puts an upward limit on the number of names
	 * allowed by the system (namely, 2^32 - 1).
	 */
	AtomicInteger nsID;

	/**
	 * Authoritative hashtable. This table maps names to a vector of the
	 * physical addresses to which a name is bound. Newly created names are
	 * automatically given entries in this table.
	 */
	Hashtable<DefaultName, Vector<PhysicalAddress>> authoritative;

	/**
	 * Non-authoritative hashtable (aka Name Cache). This table maps names to
	 * the last PhysicalAddress which was known to be a valid binding for that
	 * name. Note that this information may be erroneous and may be reset by
	 * using the <em>nsClear</em> method (see below).
	 */
	Hashtable<DefaultName, PhysicalAddress> nonauthoritative;

	/**
	 * The integer ID of the next default name protocol message to be sent by
	 * this client. It should be safe to allow this counter to roll-over since
	 * just about all protocol interactions are RPC based.
	 */
	AtomicInteger protoMsgID;

	/**
	 * The queue of incoming protocol messages. Certain threads may be waiting
	 * on this queue for responses to bind or resolve requests. Thus, we make
	 * this queue a wait queue to allow such threads to be notified when new
	 * messages arrived.
	 */
	WaitQueue<Object> incomingMsgs;

	/**
	 * The default constructor.
	 */
	public DefaultNameService() {
		authoritative = new Hashtable<DefaultName, Vector<PhysicalAddress>>();
		nonauthoritative = new Hashtable<DefaultName, PhysicalAddress>();
		protoMsgID = new AtomicInteger(0);
		incomingMsgs = new WaitQueue<Object>();
	}

	/**
	 * This function is required by the interface but will always return a
	 * runtime error.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 */
	public void nsInitialize(Scheduler S) {
		throw new RuntimeException(
				"Error: this nameservice must be initialized using the nsInitialize(Scheduler, TransportLayer) method");
	}

	/**
	 * Initialize this nameservice instance.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 * @param <b>T</b> A reference to the transport layer which should be used
	 *        for interactions between nameservice instances.
	 */
	public void nsInitialize(Scheduler S, TransportLayer T) {
		// This implementation of the nameservice doesn't use any
		// independent threads so we only save a reference to the
		// transport layer.
		ourTransport = T;

		// Now create a transport session and initialize our fields.
		try {
			transInstance = ourTransport.transportOpen(this);
		} catch (TransportException e) {
			Debug.exit("Fatal error while opening transport session: " + e);
		}

		nsAddr = transInstance.transportGetAddress();
		nsID = new AtomicInteger();

		// PRAGMA
		// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
		// Log.println("NS PhysAddress is: " + nsAddr);
	}

	/**
	 * Add a new address for authoritative bindings. This is just a stub in the
	 * simple implementation.
	 * 
	 * @param <b>addr</b> A <em>PhysicalAddress</em> which may be used for
	 *        authoritative bindings.
	 */
	public void nsAddAddress(PhysicalAddress addr) {
	}

	/**
	 * Request a fresh name from the name service. The name is guaranteed to be
	 * unique relative to all currently running instances of the same type of
	 * nameservice.
	 * 
	 * @return A fresh <em>Name</em> instance.
	 */
	public Name nsGenerateName() {
		try {
			// We generate a fresh name by creating a DefaultName
			// structure and storing it in our hash table with a new,
			// empty vector of bound addresses.
			DefaultName newName = new DefaultName(nsAddr, nsID
					.getAndIncrement());
			// authoritative.put(newName, new Vector<PhysicalAddress>());
			return newName;
		} catch (Exception e) {
			throw new RuntimeException("ERROR: can't create name: " + e);
		}
	}

	/**
	 * Bind the given name to the given physical address. This registration
	 * constitutes authoritative information. That is, the registration is
	 * stored permanently rather than in a local cache. Note that a name may
	 * have multiple bindings.
	 * 
	 * @param <b>namu</b> The <em>Name</em> instance to bind.
	 * @param <b>addr</b> The <em>PhysicalAddress</em> which the name argument
	 *        should be bound to. Henceforth, this <b>addr</b> will be a valid
	 *        target for any messages sent to <b>namu</b>.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public void nsRegister(Name namu, PhysicalAddress addr)
			throws MalformedNameException, NoSuchAddressException {
		// This is a little complicated since we use the creator of
		// names to store authoritative information, but the basic
		// protocol is this (now you know why this is the "not so
		// spiffy" implementation if things migrate a lot):
		// 1. If the name was created locally then store the new
		// binding in our authoritative table and return.
		// 2. Otherwise, block the calling thread and send an update
		// request to the owner (i.e. creator) of the name. When
		// this request has been acknowledged, store the new
		// binding in our local cache and unblock the calling
		// thread.
		try {
			DefaultName toReg = (DefaultName) namu;

			// If we created the name (and it wasn't spoofed), then
			// store the binding and exit.
			if (toReg.creatorAddr.equals(nsAddr)) {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("Binding name " + toReg + " to address " + addr);
				Vector<PhysicalAddress> bindings = null;
				if (!authoritative.containsKey(namu)) {
					bindings = new Vector<PhysicalAddress>();
					authoritative.put(toReg, bindings);
				} else
					bindings = (Vector<PhysicalAddress>) authoritative
							.get(toReg);
				if (!bindings.contains(addr))
					bindings.addElement(addr);
				return;
			}

			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("We didn't create name: " + namu +
			// ", sending bind request to creating nameservice");

			// Otherwise start the protocol with the real creator of
			// the name. The calling thread blocks while we engage in
			// the binding.
			int msgID = protoMsgID.getAndIncrement();
			DefaultNameMsg bindReq = new DefaultNameMsg(
					DefaultNameMsg.DNMSG_BIND_REQUEST, toReg, msgID, addr);
			DNFindMsg waiter = new DNFindMsg(msgID);
			TransportMessage tMsg = new TransportMessage(DefaultNameMsg
					.serializeMsg(bindReq));
			Object[] found = null;

			// Sound out the bind request and block.
			transInstance.transportSend(toReg.creatorAddr, tMsg);
			synchronized (incomingMsgs) {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("Sent request, waiting for reply");
				found = incomingMsgs.search(waiter);
				while (found.length == 0) {
					incomingMsgs.wait();
					found = incomingMsgs.search(waiter);
				}
			}

			// Once we get here, we know that the bind ack has been
			// received. If the bind ack contains an exception then
			// throw it out of this method. Otherwise, remove the ack
			// from the message queue, store the binding in our local
			// cache, and return.
			if (found.length != 1)
				Debug
						.exit("Fatal Error: Received more than one bind ack for this request!");

			DefaultNameMsg ackMsg = (DefaultNameMsg) found[0];
			if (ackMsg.error != null) {
				if (ackMsg.error instanceof MalformedNameException)
					throw (MalformedNameException) ackMsg.error;
				else
					Debug.exit("Fatal Error: received remote exception: "
							+ ackMsg.error);
			}

			incomingMsgs.remove(found[0]);
			nonauthoritative.put(toReg, addr);

		} catch (ClassCastException e) {
			// We get to this exception if:
			// 1. The namu argument is not a legal subclass of
			// DefaultName. This could happen if the client
			// mistakenly passes the wrong type of name to this
			// instance.
			throw new MalformedNameException(
					"Name argument must be a subclass of DefaultName");
		} catch (NullPointerException e) {
			// We get to this exception if:
			// 1. The namu argument is null.
			// 2. The hash entry for namu is null (i.e. the name was
			// not really created locally, this might be a spoof
			// attempt).
			throw new MalformedNameException(
					"Name argument is malformed DefaultName instance");
		} catch (TransportException e) {
			// This can only be caused by the send, if we get this
			// then it's fatal so barf
			Debug
					.exit("Fatal Error: transport failed while sending bind request:"
							+ e);
		} catch (InterruptedException e) {
			// This can occur if a waiting thread is interrupted in
			// the synchronized block above. That's tres bad so barf.
			Debug
					.exit("Fatal Error: blocked request thread should never be interrupted!"
							+ e);
		} catch (IOException e) {
			// This gets thrown if we have problems serializing the
			// outgoing bind request. We can't tolerate this so barf.
			Debug
					.exit("Fatal Error: can't serialize bind request message:"
							+ e);
		}
	}

	/**
	 * Remove a binding between a name and a physical address. Bindings may only
	 * be removed for names which are bound in authoritative tables. That is, a
	 * name may only be unbound if it was previously bound by the same instance
	 * of the nameservice.
	 * 
	 * @param <b>namu</b> The <em>Name</em> to unbind.
	 * @param <b>addr</b> The <em>PhysicalAddress</em> which should no longer be
	 *        associated with this name.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NameNotFoundException
	 *                Thrown if the given name was never bound by this instance
	 *                of the nameservice.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public void nsRemove(Name namu, PhysicalAddress addr)
			throws MalformedNameException, NameNotFoundException,
			NoSuchAddressException {
		try {
			DefaultName toReg = (DefaultName) namu;

			// If we created the name (and it wasn't spoofed), then
			// see if there is a binding for it.
			if (toReg.creatorAddr.equals(nsAddr)) {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("<DefaultNameService.nsRemove> Name is local, unbinding name: "
				// + namu + " with addr: " + addr);
				Vector<PhysicalAddress> bindings = authoritative.get(namu);
				bindings.removeElement(addr);
				return;
			}

			// Otherwise, nuke our local table entry for this name
			nsClear(namu);

			// and start the protocol with the real creator of the
			// name so that the appropriate binding is removed.
			int msgID = protoMsgID.getAndIncrement();
			DefaultNameMsg bindReq = new DefaultNameMsg(
					DefaultNameMsg.DNMSG_REMOVE_REQUEST, toReg, msgID, addr);
			DNFindMsg waiter = new DNFindMsg(msgID);
			TransportMessage tMsg = new TransportMessage(DefaultNameMsg
					.serializeMsg(bindReq));
			Object[] found = null;

			// Sound out the remove request and block.
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.nsRemove> Name is not maintained locally, sending remote remove request with ID: "
			// + msgID);
			transInstance.transportSend(toReg.creatorAddr, tMsg);
			synchronized (incomingMsgs) {
				found = incomingMsgs.search(waiter);
				while (found.length == 0) {
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("<DefaultNameService.nsRemove> No reply for message with ID "
					// + msgID + ", waiting...");
					incomingMsgs.wait();
					found = incomingMsgs.search(waiter);
				}
			}

			// Once we get here, we know that the remove ack has been
			// received. If we get an exception back then pass it on
			// to the client. Otherwise, remove the ack from the
			// message queue and return.
			if (found.length != 1)
				Debug
						.exit("Fatal Error: Received more than one remove ack for this request!");

			DefaultNameMsg ackMsg = (DefaultNameMsg) found[0];
			if (ackMsg.error != null) {
				if (ackMsg.error instanceof MalformedNameException)
					throw (MalformedNameException) ackMsg.error;
				else if (ackMsg.error instanceof NameNotFoundException)
					throw (NameNotFoundException) ackMsg.error;
				else
					Debug.exit("Fatal Error: received remote exception: "
							+ ackMsg.error);
			}

			incomingMsgs.remove(found[0]);
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.nsRemove> Name removed, returning");

		} catch (ClassCastException e) {
			// We get to this exception if:
			// 1. The namu argument is not a legal subclass of
			// DefaultName. This could happen if the client
			// mistakenly passes the wrong type of name to this
			// instance.
			throw new MalformedNameException(
					"Name argument must be a subclass of DefaultName");
		} catch (NullPointerException e) {
			// We get to this exception if:
			// 1. The namu argument is null.
			// 2. The hash entry for namu is null (i.e. the name was
			// not really created locally, this might be a spoof
			// attempt).
			throw new MalformedNameException(
					"Name argument is malformed DefaultName instance");
		} catch (TransportException e) {
			// This can only be caused by the send, if we get this
			// then it's fatal so barf
			Debug
					.exit("Fatal Error: transport failed while sending remove request:"
							+ e);
		} catch (InterruptedException e) {
			// This can occur if a waiting thread is interrupted in
			// the synchronized block above. That's tres bad so barf.
			Debug
					.exit("Fatal Error: blocked request thread should never be interrupted!"
							+ e);
		} catch (IOException e) {
			// This gets thrown if we have problems serializing the
			// outgoing bind request. We can't tolerate this so barf.
			Debug.exit("Fatal Error: can't serialize remove request message:"
					+ e);
		}
	}

	/**
	 * Requests the nameservice to resolve the binding of the given name.
	 * 
	 * @param <b>namu</b> The <em>Name</em> instance to be resolved.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NameNotFoundException
	 *                Thrown if no binding exists for the given name.
	 * @exception osl.nameservice.NoBindingException
	 *                Thrown if the given name currently has no associated
	 *                <em>PhysicalAddress</em> binding.
	 */
	public PhysicalAddress nsLookup(Name namu) throws MalformedNameException,
			NameNotFoundException, NoBindingException {
		try {
			DefaultName toReg = (DefaultName) namu;

			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("Processing lookup request for name: " + namu);

			// If we created the name (and it wasn't spoofed), then
			// see if there is a binding for it.
			if (toReg.creatorAddr.equals(nsAddr)) {
				Vector<PhysicalAddress> bindings = authoritative.get(namu);

				// If there is no binding for this name then throw a
				// NoBindingException and exit, otherwise return the
				// first binding we find.
				if (bindings.size() == 0)
					throw new NoBindingException("Name " + namu
							+ " currently has no bindings");
				else {
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("Name found, returning address: " +
					// bindings.elementAt(0));
					return (PhysicalAddress) bindings.elementAt(0);
				}
			}

			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("Name not created local, checking cache...");

			// If we aren't the creator then return our local cache
			// entry (if we have one).
			if (nonauthoritative.containsKey(toReg)) {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("Cache entry found, returning");
				return (PhysicalAddress) nonauthoritative.get(toReg);
			}

			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("Name not in cache, sending request to creator...");

			// If all else fails, block and send a request to the
			// creator of the name, then reset our local cache.
			int msgID = protoMsgID.getAndIncrement();
			DefaultNameMsg bindReq = new DefaultNameMsg(
					DefaultNameMsg.DNMSG_RESOLVE, toReg, msgID, null);
			DNFindMsg waiter = new DNFindMsg(msgID);
			TransportMessage tMsg = new TransportMessage(DefaultNameMsg
					.serializeMsg(bindReq));
			Object[] found = null;

			// Send out the resolve request and block.
			transInstance.transportSend(toReg.creatorAddr, tMsg);
			synchronized (incomingMsgs) {
				found = incomingMsgs.search(waiter);
				while (found.length == 0) {
					incomingMsgs.wait();
					found = incomingMsgs.search(waiter);
				}
			}

			// Once we get here, we know that the resolve ack has been
			// received. If we get an exception back then pass it on to the
			// client. Otherwise, remove the ack from the message queue,
			// store the result in our local cache, and return the resulting
			// address.
			if (found.length != 1)
				Debug
						.exit("Fatal Error: Received more than one resolve ack for this request!");

			DefaultNameMsg ackMsg = (DefaultNameMsg) found[0];
			if (ackMsg.error != null) {
				if (ackMsg.error instanceof MalformedNameException)
					throw (MalformedNameException) ackMsg.error;
				else if (ackMsg.error instanceof NameNotFoundException)
					throw (NameNotFoundException) ackMsg.error;
				else if (ackMsg.error instanceof NoBindingException)
					throw (NoBindingException) ackMsg.error;
				else
					Debug.exit("Fatal Error: received remote exception: "
							+ ackMsg.error);
			}

			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("Returned from remote query with name: " +
			// ackMsg.bind);

			incomingMsgs.remove(found[0]);
			nonauthoritative.put(toReg, ackMsg.bind);

			return ackMsg.bind;

		} catch (ClassCastException e) {
			// We get to this exception if:
			// 1. The namu argument is not a legal subclass of
			// DefaultName. This could happen if the client
			// mistakenly passes the wrong type of name to this
			// instance.
			throw new MalformedNameException(
					"Name argument must be a subclass of DefaultName");
		} catch (NullPointerException e) {
			// We get to this exception if:
			// 1. The namu argument is null.
			// 2. The hash entry for namu is null (i.e. the name was
			// not really created locally, this might be a spoof
			// attempt).
			throw new MalformedNameException(
					"Name argument is malformed DefaultName instance");
		} catch (TransportException e) {
			// This can only be caused by the send, if we get this
			// then it's fatal so barf
			Debug
					.exit("Fatal Error: transport failed while sending resolve request:"
							+ e);
		} catch (InterruptedException e) {
			// This can occur if a waiting thread is interrupted in
			// the synchronized block above. That's tres bad so barf.
			Debug
					.exit("Fatal Error: blocked request thread should never be interrupted!"
							+ e);
		} catch (IOException e) {
			// This gets thrown if we have problems serializing the
			// outgoing bind request. We can't tolerate this so barf.
			Debug.exit("Fatal Error: can't serialize resolve request message:"
					+ e);
		}

		return null;
	}

	/**
	 * Requests the nameservice to determine whether or not the given name is
	 * bound locally to the given physical address. This amounts to determining
	 * whether or not the binding <b>namu</b>-><b>localBind</b> is part of the
	 * authoritative information for this nameservice instance.
	 * 
	 * @param <b>namu</b> The <em>Name</em> to be looked up.
	 * @param <b>localBind</b> The <em>PhysicalAddress<em> which
     should be verified as a local binding of the given name.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 * @exception osl.nameservice.NoSuchAddressException
	 *                Thrown if this nameservice instance does not maintain
	 *                authoritative bindings for <b>addr</b>.
	 */
	public boolean nsLocal(Name namu, PhysicalAddress localBind)
			throws MalformedNameException, NoSuchAddressException {
		try {
			DefaultName toReg = (DefaultName) namu;

			// If we created the name (and it wasn't spoofed), then
			// see if there is a binding for it.
			if (toReg.creatorAddr.equals(nsAddr)) {
				Vector<PhysicalAddress> bindings = authoritative.get(namu);

				// Now see if we can find the given address as one of
				// the local bindings. If so then return true.
				return bindings.contains(localBind);
			} else
				return false;

		} catch (ClassCastException e) {
			// We get to this exception if:
			// 1. The namu argument is not a legal subclass of
			// DefaultName. This could happen if the client
			// mistakenly passes the wrong type of name to this
			// instance.
			throw new MalformedNameException(
					"Name argument must be a subclass of DefaultName");
		} catch (NullPointerException e) {
			// We get to this exception if:
			// 1. The namu argument is null.
			// 2. The hash entry for namu is null (i.e. the name was
			// not really created locally, this might be a spoof
			// attempt).
			throw new MalformedNameException(
					"Name argument is malformed DefaultName instance");
		}
	}

	/**
	 * Instructs the nameservice to clear non-authoritative about the given
	 * name. Typically, this means clearing any cache entries associated with
	 * the given name. Note that no action is taken if <b>namu</b> refers to an
	 * authoritative entry.
	 * 
	 * @param <b>namu</b> The <em>Name</em> for which non-authoritative
	 *        information should be cleared.
	 * @exception osl.nameservice.MalformedNameException
	 *                Thrown if the given name argument is an illegal name
	 *                representation. This may occur if a name from another
	 *                nameservice is used or if a client attempts to spoof an
	 *                illegal name.
	 */
	public void nsClear(Name naum) throws MalformedNameException {
		nonauthoritative.remove(naum);
	}

	/**
	 * Called by our transport layer when a new message has been received for
	 * this nameservice instance.
	 * 
	 * @param <b>target</b> The <em>TransportInstance</em> reference that this
	 *        message is targeted for. By assumption, the client is the owner of
	 *        this instance.
	 * @param <b>msg</b> The <em>TransportMessage</em> that was received for
	 *        this connection.
	 */
	public void transportReceive(TransportInstance target, TransportMessage msg) {
		DefaultNameMsg reqMsg = null;
		DefaultNameMsg reply = null;
		TransportMessage tMsg = null;

		// Setup this thread to be logged to the local System log
		try {
			// Log.logThread("System", Thread.currentThread());
		} catch (Exception e) {
			Assert.afAssert(false, "Error logging incoming transport thread: "
					+ e);
			// Log.println(FoundryStart.sysLog,
			// "Error logging incoming transport thread: " + e);
		}

		// Deserialize the message and figure out how to process it.
		try {
			reqMsg = DefaultNameMsg.deserializeMsg(msg.contents);
		} catch (Exception e) {
			// This is always fatal so crash. If we wanted to make
			// this secure we would just ignore this error.
			// Otherwise, evil clients could kill us by sending
			// garbage.
			Debug.exit("Fatal Error: Can't enqueue incoming message: " + e);
		}

		// PRAGMA
		// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
		// Log.println("<DefaultNameService.transportReceive> Processing incoming message with ID: "
		// + reqMsg.bindID);
		switch (reqMsg.type) {
		case DefaultNameMsg.DNMSG_UNKNOWN:
			// Just ignore this one.
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.transportReceive> Received unknown query");
			break;

		case DefaultNameMsg.DNMSG_BIND_REQUEST:
		case DefaultNameMsg.DNMSG_RESOLVE:
		case DefaultNameMsg.DNMSG_REMOVE_REQUEST:
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.transportReceive> Received bind, resolve or remove request with ID: "
			// + reqMsg.bindID);

			// Now handle the bind request by treating it as a local
			// request. Pass any exception back to the caller using the
			// error field.
			try {

				// First create a reply message.
				reply = new DefaultNameMsg(DefaultNameMsg.DNMSG_UNKNOWN, null,
						reqMsg.bindID, null);

				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("<DefaultNameService.transportReceive> Prepared to handle, checking type");

				switch (reqMsg.type) {
				case DefaultNameMsg.DNMSG_BIND_REQUEST:
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("<DefaultNameService.transportReceive> calling local nsRegister");
					nsRegister(reqMsg.target, reqMsg.bind);
					reply.type = DefaultNameMsg.DNMSG_BIND_ACK;
					break;

				case DefaultNameMsg.DNMSG_RESOLVE:
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("<DefaultNameService.transportReceive> Looking up name: "
					// + reqMsg.target);
					reply.bind = nsLookup(reqMsg.target);
					reply.type = DefaultNameMsg.DNMSG_RESOLVE_REPLY;
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("<DefaultNameService.transportReceive> Returning bound address: "
					// + reply.bind);
					break;

				case DefaultNameMsg.DNMSG_REMOVE_REQUEST:
					// PRAGMA
					// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
					// Log.println("<DefaultNameService.transportReceive> calling local nsRemove");
					nsRemove(reqMsg.target, reqMsg.bind);
					reply.type = DefaultNameMsg.DNMSG_REMOVE_ACK;
					break;

				default:
					Assert.afAssert(false, "something really bad happened");
				}

			} catch (Exception e) {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("<DefaultNameService.transportReceive> Error while processing request: "
				// + e);
				reply.error = e;
			}

			try {
				// PRAGMA
				// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
				// Log.println("<DefaultNameService.transportReceive> Sending reply message");

				tMsg = new TransportMessage(DefaultNameMsg.serializeMsg(reply));
				transInstance.transportSend(msg.sender, tMsg);
			} catch (IOException e) {
				// This is caused by a serialization error. It's also fatal (for
				// now).
				Debug.exit("Fatal Error: Can't serialize outgoing ack message"
						+ e);
			} catch (TransportException e) {
				// This is fatal for now.
				Debug.exit("Fatal Error: Can't send ack message" + e);
			}
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.transportReceive> Reply message sent, returning to caller");
			break;

		case DefaultNameMsg.DNMSG_BIND_ACK:
		case DefaultNameMsg.DNMSG_RESOLVE_REPLY:
		case DefaultNameMsg.DNMSG_REMOVE_ACK:
			// PRAGMA
			// [debug,osl.nameservice.NameService,osl.nameservice.simple.DefaultNameService]
			// Log.println("<DefaultNameService.transportReceive> Received an ack message for ID "
			// + reqMsg.bindID + ", queueing");

			// A thread is waiting for this reply so put it on the
			// incoming queue.
			incomingMsgs.enqueue(reqMsg);
			break;

		default:
			// Anything else is fatal
			Debug.exit("Fatal Error: Unknown message type: " + reqMsg.type);
		}
	}

	/**
	 * Called if the transport layer encounters an exception while attempting to
	 * send a previously queued message. If a client holds several connections
	 * it is their responsibility to demultiplex incoming messages as
	 * appropriate. The <b>target</b> field may be used to determine which
	 * connection this message is associated with.
	 * 
	 * @param <b>target</b> The <em>TransportInstance</em> reference that the
	 *        original message was sent from. By assumption, the client is the
	 *        owner of this instance.
	 * @param <b>msg</b> The original <em>TransportMessage</em> that was queued
	 *        to be sent to the remote host.
	 * @param <b>error</b> The <em>TransportException</em> which encapsulates
	 *        the error encountered during transmission.
	 */
	public void transportException(TransportInstance target,
			TransportMessage msg, TransportException error) {
		// Just a stub for now
	}

	// ///////////////////////////////////////////////////////////
	// ///////// INNER CLASSES ///////////////////////////////////
	// ///////////////////////////////////////////////////////////

	/**
	 * This is a class which is used to facilitate searches of the local
	 * <em>incomingMsgs</em> queue. The search is based on the <em>bindID</em>
	 * field of each message stored in the queue.
	 */
	class DNFindMsg implements QueueSearch {
		/**
		 * The message ID we are searching for in the queue.
		 */
		int searchID;

		/**
		 * The only constructor for this class. Takes the message ID which we
		 * are looking for in the queue.
		 */
		public DNFindMsg(int ID) {
			searchID = ID;
		}

		/**
		 * This function defines a predicate which takes an element of a
		 * <em>Queue</em> as an argument and returns <b>true</b> or <b>false</b>
		 * depending on whether or not the object satisfies the predcicate.
		 */
		public boolean queueEvalPred(Object arg) {
			return ((arg instanceof DefaultNameMsg) && ((DefaultNameMsg) arg).bindID == searchID);
		}
	}
}
