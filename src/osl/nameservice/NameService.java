package osl.nameservice;

import osl.scheduler.Scheduler;
import osl.transport.PhysicalAddress;

/**
 * This interface defines the methods required in order for an object to be
 * treated as a nameservice. In particular, a nameservice must provide a
 * mechanism for creating names, associating them with particular physical
 * addresses, and later updating or removing these bindings. A nameservice is
 * used in conjunction with a request handler in order to implement
 * communication between foundry nodes. Normally, request handler clients do not
 * access the name service directly. Instead, a request handler proxy is used
 * which ensures that the nameservice is accessed correctly and then nameservice
 * entries remain consistent.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:34 $)
 * @see Name
 * @see osl.handler.RequestHandler
 */

public interface NameService {
	/**
	 * Initialize this nameservice instance.
	 * 
	 * @param <b>S</b> A reference to the scheduler which should be used to
	 *        schedule nameservice threads.
	 */
	public void nsInitialize(Scheduler S);

	/**
	 * Add a new address for authoritative bindings.
	 * 
	 * @param <b>addr</b> A <em>PhysicalAddress</em> which may be used for
	 *        authoritative bindings.
	 */
	public void nsAddAddress(PhysicalAddress addr);

	/**
	 * Request a fresh name from the name service. The name is guaranteed to be
	 * unique relative to all currently running instances of the same type of
	 * nameservice.
	 * 
	 * @return A fresh <em>Name</em> instance.
	 */
	public Name nsGenerateName();

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
	 *                Thrown if this nameservice instance can not make
	 *                authoritative bindings for <b>addr</b>.
	 */
	public void nsRegister(Name namu, PhysicalAddress addr)
			throws MalformedNameException, NoSuchAddressException;

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
			NoSuchAddressException;

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
			NameNotFoundException, NoBindingException;

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
			throws MalformedNameException, NoSuchAddressException;

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
	public void nsClear(Name naum) throws MalformedNameException;

}
