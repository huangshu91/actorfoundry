package osl.service.shell;

import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class establishes a socket connection on behalf of one of the "stdio"
 * streams associated with this shell session. Depending on how this class is
 * instantiated, an <em>InputStream</em> or <em>OutputStream</em> will be
 * assigned in the <em>ShellActorImpl</em> which is acting as our proxy.
 * 
 * @author Mark Astley
 * @version $Revision: 1.3 $ ($Date: 1998/08/31 18:59:02 $)
 * @see osl.service.shell.ShellSession
 */

class EstablishStreamConnect implements Runnable {
	/**
	 * The <em>ShellActorImpl</em> we are assigning a stream for.
	 */
	ShellActorImpl ourActorRef;

	/**
	 * The server socket we are waiting for a connection on.
	 */
	ServerSocket connect;

	/**
	 * The stream we should assign to once the connection has been made.
	 */
	int assign;

	/**
	 * The only constructor for this class assigns a socket to listen to and a
	 * connection to make.
	 */
	EstablishStreamConnect(ShellActorImpl ourImpl, ServerSocket S,
			int assignment) {
		if ((assignment < 0) || (assignment > 2))
			throw new RuntimeException("Illegal assignment argument");
		ourActorRef = ourImpl;
		connect = S;
		assign = assignment;
	}

	public void run() {
		Socket acceptor;

		try {
			// Set a 10 minute timeout for connecting to the stream. This
			// lets us clean up unused streams.
			connect.setSoTimeout(10 * 60 * 1000);
			try {
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// switch (assign) {
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect] case
				// ShellSession.SET_STDIN:
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// System.out.println("<EstablishStreamConnect.run> Waiting for STDIN connection");
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// break;
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect] case
				// ShellSession.SET_STDOUT:
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// System.out.println("<EstablishStreamConnect.run> Waiting for STDOUT connection");
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// break;
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect] case
				// ShellSession.SET_STDERR:
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// System.out.println("<EstablishStreamConnect.run> Waiting for STDERR connection");
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect] }
				acceptor = connect.accept();
				// PRAGMA [debug,osl.service.shell.EstablishStreamConnect]
				// System.out.println("<EstablishStreamConnect.run> Connection made");
			} catch (InterruptedIOException e) {
				// If we catch this then we timed out so just exit.
				connect.close();
				return;
			}
			switch (assign) {
			case ShellSession.SET_STDIN:
				ourActorRef.stdinStream = acceptor.getInputStream();
				break;

			case ShellSession.SET_STDOUT:
				ourActorRef.stdoutStream = acceptor.getOutputStream();
				break;

			case ShellSession.SET_STDERR:
				ourActorRef.stderrStream = acceptor.getOutputStream();
			}
		} catch (Exception e) {
			ourActorRef.mgrActorFatalError(e);
		}

		// Once we've made the assignment just exit.
	}
}
