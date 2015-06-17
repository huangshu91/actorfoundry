package osl.transport;

import java.io.Serializable;

/**
 * This is just an interface which is used to encapsulate the
 * implementation-dependent structure of physical addresses. Implementors of
 * <em>TransportLayer</em> should provide a physical address implementation
 * which inherits from this class. Note that <em>PhysicalAddress</em>es must be
 * serializable as they will usually be sent over the network (although that
 * also depends on implementation issues). Also, it is important that
 * implementors of <em>PhysicalAddress</em> correctly support the hashing
 * functions (i.e. <em>equals</em> and <em>hashCode</em>) so that addresses are
 * comparable.
 * 
 * @see TransportLayer
 * @see TransportInstance
 * @see TransportClient
 * @see TransportMessage
 * @see TransportException
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:14 $)
 */

public interface PhysicalAddress extends Serializable {
}
