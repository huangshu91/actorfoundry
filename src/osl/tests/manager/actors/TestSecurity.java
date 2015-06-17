// This simple actor is designed to test the security manager.
// All we do here is attempt to barf something to standard out.
// Hopefully this will cause a security exception since we want to
// restrict these types of interactions to using the standard "system" 
// actors.

package osl.tests.manager.actors;

import java.io.FileOutputStream;

import osl.manager.Actor;

public class TestSecurity extends Actor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7392812992149326447L;

	public void boot() {
		try {
			new FileOutputStream("/tmp/foo");
		} catch (Exception e) {
			// Log.println("Generated exception: " + e);
		}
	}
}
