package osl.tests.handler.simple;

import osl.handler.RequestClient;
import osl.nameservice.Name;
import osl.transport.PhysicalAddress;

/**
 * We require a separate interface to define the methods which we allow for
 * remote requests. This interface just defines the test methods.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/06/12 21:33:04 $)
 */
public interface CustomRequestClient extends RequestClient {

	public void test1();

	public String test2();

	public void test3(String arg1, PhysicalAddress arg2);

	public Name test4(String arg1, PhysicalAddress arg2);

	public Object test7();

}
