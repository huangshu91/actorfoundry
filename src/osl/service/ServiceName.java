package osl.service;

/**
 * By implementing this interface, service builders indicate which objects
 * should be treated as service names. Typically, names themselves will not be
 * public structures so that they may not be inspected by their owners. This is
 * done to preserve the integrity of the name. Note that it is important that
 * names hash correctly as many copies of a single name may exist, but all names
 * should lead to the invocation of the same service.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:32:55 $)
 * @see Service
 */

public interface ServiceName {
}
