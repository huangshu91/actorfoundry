package osl.nameservice;

import java.io.Externalizable;

/**
 * This interface defines the characteristics required for nameservice names. At
 * the moment, a name is simply an entity which implements the <em>Name</em>
 * interface. Typically, names themselves will not be public structures so that
 * they may not be inspected by their owners. This is done to preserve the
 * integrity of the name.
 * 
 * @author Mark Astley
 * @version $Revision: 1.4 $ ($Date: 1999/01/19 18:43:35 $)
 * @see NameService
 */

public interface Name extends Externalizable {
}
