package osl.foundry;

/**
 * This abstract class is private to this package and may be used to build
 * secure threads within the foundry start code. In particular, all the user
 * need do is extend this class with their own code, etc. The security manager
 * is configured to always accept invocations from this class, as long as an
 * instance of <em>Actor</em> does not appear in the call stack before the
 * instance of this class.
 */

abstract class SecureThread extends Thread {
}
