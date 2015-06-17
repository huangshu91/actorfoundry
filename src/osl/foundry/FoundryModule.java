package osl.foundry;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class is used to build module descriptions based on the definitions in a
 * foundry configuration file. A static convenience function is provided which
 * tokenizes a string and returns and appropriate <em>FoundryModule</em>
 * structure or null if the line could not be parsed as a module description. It
 * is up to the client to filter out comments, empty lines, and CLASSPATH
 * declarations.
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1998/08/12 04:26:51 $)
 */
public class FoundryModule {
	// Module types
	public static final int MOD_PORT = 0;
	public static final int MOD_SCHEDULER = 1;
	public static final int MOD_TRANSPORT = 2;
	public static final int MOD_NAMESERVICE = 3;
	public static final int MOD_HANDLER = 4;
	public static final int MOD_MANAGER = 5;
	public static final int MOD_SERVICE = 6;
	public static final int MOD_STATUS = 7;
	public static final int MOD_EVENT = 8;

	// Module string names (used for parsing)
	static final String PORT_STRING = "PORT";
	static final String SCHEDULER_STRING = "SCHEDULER";
	static final String TRANSPORT_STRING = "TRANSPORT";
	static final String NAMESERVICE_STRING = "NAMESERVICE";
	static final String HANDLER_STRING = "HANDLER";
	static final String MANAGER_STRING = "MANAGER";
	static final String SERVICE_STRING = "SERVICE";
	static final String STATUS_STRING = "STATUS";
	static final String EVENT_STRING = "EVENT";

	/**
	 * The type of module parsed.
	 */
	public int modType = -1;

	/**
	 * The set of arguments provided after the module declaration.
	 */
	public String[] args;

	/**
	 * The line number where this module appeared. Useful for debugging.
	 */
	public int line = -1;

	/**
	 * A convenient static function for parsing a string into a module
	 * description.
	 */
	public static FoundryModule scanConfigLine(String line, int lineNum) {
		StringTokenizer st = new StringTokenizer(line);
		String next = null;
		FoundryModule ret = new FoundryModule();

		if (!st.hasMoreTokens())
			return null;

		ret.line = lineNum;
		next = st.nextToken();
		if (next.equals(PORT_STRING))
			ret.modType = MOD_PORT;
		else if (next.equals(SCHEDULER_STRING))
			ret.modType = MOD_SCHEDULER;
		else if (next.equals(TRANSPORT_STRING))
			ret.modType = MOD_TRANSPORT;
		else if (next.equals(NAMESERVICE_STRING))
			ret.modType = MOD_NAMESERVICE;
		else if (next.equals(HANDLER_STRING))
			ret.modType = MOD_HANDLER;
		else if (next.equals(MANAGER_STRING))
			ret.modType = MOD_MANAGER;
		else if (next.equals(SERVICE_STRING))
			ret.modType = MOD_SERVICE;
		else if (next.equals(STATUS_STRING))
			ret.modType = MOD_STATUS;
		else if (next.equals(EVENT_STRING))
			ret.modType = MOD_EVENT;
		else
			return null;

		ret.args = new String[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++)
			ret.args[i] = st.nextToken();

		return ret;
	}

	/**
	 * A convenient static function for generating module descriptions.
	 */
	public static void buildModules(Vector<FoundryModule> mods) {
		FoundryModule ret = new FoundryModule();

		// PORT_STRING
		ret.modType = MOD_PORT;
		ret.args = new String[] { "1250" };
		mods.add(ret);

		// SCHEDULER_STRING
		ret = new FoundryModule();
		ret.modType = MOD_SCHEDULER;
		ret.args = new String[] {
				"osl.scheduler.continuations.FairContinuationsBasedScheduler",
				"scheduler" };
		mods.add(ret);

		// TRANSPORT_STRING
		ret = new FoundryModule();
		ret.modType = MOD_TRANSPORT;
		ret.args = new String[] { "osl.transport.udp.UDPTransportLayer",
				"transport", "@scheduler", "127.0.0.1" };
		mods.add(ret);

		// NAMESERVICE_STRING
		ret = new FoundryModule();
		ret.modType = MOD_NAMESERVICE;
		ret.args = new String[] { "osl.nameservice.simple.DefaultNameService",
				"ns", "@scheduler", "@transport" };
		mods.add(ret);

		// HANDLER_STRING
		ret = new FoundryModule();
		ret.modType = MOD_HANDLER;
		ret.args = new String[] { "osl.handler.RequestHandler", "handler",
				"@scheduler", "@transport", "@ns" };
		mods.add(ret);

		// MANAGER_STRING
		ret = new FoundryModule();
		ret.modType = MOD_MANAGER;
		ret.args = new String[] { "osl.manager.basic.BasicActorManager",
				"manager", "@scheduler", "@handler" };
		mods.add(ret);

		// SERVICE_STRING
		ret = new FoundryModule();
		ret.modType = MOD_SERVICE;
		ret.args = new String[] { "osl.service.yp.YP", "yp", "@scheduler",
				"@manager", "@transport", "@handler", "11970" };
		mods.add(ret);

		ret = new FoundryModule();
		ret.modType = MOD_SERVICE;
		ret.args = new String[] { "osl.service.shell.Shell", "shell",
				"@scheduler", "@manager", "11971" };
		mods.add(ret);

		// STATUS_STRING
		ret = new FoundryModule();
		ret.modType = MOD_STATUS;
		ret.args = new String[] { "./logs" };
		mods.add(ret);

		// EVENT_STRING
		/*
		 * ret = new FoundryModule(); ret.modType = MOD_EVENT; ret.args =
		 * {"./logs"}; mods.add(ret);
		 */

	}

}
