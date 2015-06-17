// $Id: Tester.java,v 1.1 1999/07/13 02:01:49 cvs Exp $

package osl.examples.tester;

import osl.manager.Actor;
import osl.manager.ActorManagerName;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;
import osl.service.yp.YP;

/**
 * Tester is a test-driver actor. Its methods test() and test(n1) run fixed sets
 * of tests from osl/examples. It allots a fixed amount of time per test -- eg 2
 * seconds for single-node tests. See constants N1_cycleTime.
 * <p>
 * 
 * For single-node tests ...
 * <ul>
 * <li>cd foundry
 * <li>bin/startfoundry -conf foundry.conf
 * <li>bin/ashell -conf foundry.conf
 * <li>create osl.examples.tester.Tester <br>
 * send SV_0 test <br>
 * close
 * </ul>
 */

public class Tester extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7777213503687729142L;

	public Tester() {
		super();
	}

	/**
	 * @param otherNode
	 */
	public Tester(String otherNode) {
		super();
		this.otherNode = otherNode;
	}

	/** Progress indicators for test sets */
	int stage1 = 0, stage2 = 20;

	ActorName otherTester;
	/** Node name (IP name) of other node for testSet2 */
	String otherNode;

	// private Class<?>[] noClass = new Class[]{};
	// private Object[] noObject = new Object[]{};
	// ===================================================
	/** Create string with my id, actorname, node, and a note. */
	@message
	public String myId() {
		return "T" + Id.stamp();
	}

	// ===================================================
	/**
	 * Displays identification, time, and text of note, via stdout actor.
	 */
	@message
	public void showId(String note) {
		String Tid = myId() + note;
		send(stdout, "println", Tid);
	}

	// ===================================================
	/**
	 * An inner class with test messages and reference to test method.
	 */
	protected class TestStep {
		/** test-number, for display */
		int id;
		/** Reference to test method to run for this teststep */
		String myMessage;
		/** Start-message and end-message for this teststep */
		String start, done;

		/**
		 * Constructor with method-number and message strings. Uses number
		 * <i>m</i> to construct string "test<i>m</i>", and Class method
		 * getMethod to get reference to test method.
		 */
		public TestStep(int id, String start, String done) {
			this.id = id;
			this.start = start;
			this.done = done;
			myMessage = "test" + id;
		}
	}

	// ===================================================
	/** notes1 is array of TestSteps for single-node tests */
	TestStep[] notes1 = {
			new TestStep(0, "helloworld with HelloActor/WorldActor",
					"helloworld: See \"Hello World!\" in shell output"),

			new TestStep(1, "exceptions with CatchActor/ThrowActor",
					"exceptions: See \"Received exception\" in shell output"),

			new TestStep(2, "FibActor 8 with FibActor boot report",
					"FibActor 8: see \"Final result: 21\" in foundry output"),
			new TestStep(3, "FibActor 9 with local report",
					"FibActor 9: see \"fib test result is 34\" in foundry output"),
			new TestStep(4, "FibSuicide 12 with local report",
					"FibSuicide 12: see \"fib test result is 144\" in foundry output"),

			new TestStep(5, "2 local ping.PingActors",
					"PingActors: See \"Received ping\" and \"is alive\" messages in shell output"),

			new TestStep(
					6,
					"2 local pingpong.PingActors with 12 pings",
					"PingActors: See \"Sent all pings\" and \"Received all pongs\" messages in shell output"),
			new TestStep(7, "Quicksort",
					"Quicksort: Sorted numbers. See output in shell output"),
			new TestStep(8, "Threadring benchmark (Great Language Shoot-out)",
					"Threadring 100000: See \"407\" in shell output"),
			new TestStep(9,
					"Chameneos-redux benchmark (Great Language Shoot-out)",
					"Chameneos-redux for 60000 rendezvous: See output in shell output"),
			new TestStep(10, "Inheritance with HeyActor/WorldActor",
					"Inheritance: See \"Hey World!\" in shell output"),
			new TestStep(
					11,
					"Float typed arguments with Xampi",
					"Float arguments in messages: see \"Xampi relays the value 1.2\" in foundry output"),
			new TestStep(
					12,
					"Local Synchronization Constraints with UnboundedBuffer and BoundedBuffer",
					"Local Synchronization Constraints: see \"UnboundedBuffer matched.\" and \"BoundedBuffer matched.\" in foundry output"),
			new TestStep(
					13,
					"Local Synchronization Constraints with Producer-Consumer-Buffer",
					"Local Synchronization Constraints: see \"BPC 1st test OK!\" and \"BPC 2nd test OK!\" and \"BPC 3rd test OK!\" \"BPC 4th test OK!\" in foundry output"),
			new TestStep(14, "JoinFibActor 22 with join continuations report",
					"FibActor 22: see \"Final result: 17711\" in foundry output"),
			new TestStep(
					15,
					"exceptions with BadMessageActor",
					"exceptions: See \"Received exception .... NoSuchMethodException...\" in shell output"),

			new TestStep(98, "is done", "") };

	/** notes2 is array of TestSteps for two-node tests */
	TestStep[] notes2 = {
			new TestStep(20, "remoteping with local and remote PingActors", ""),
			new TestStep(98, "", ""),
			new TestStep(
					98,
					"",
					"remoteping: See \"Received ping\" and \"is alive\" messages in shell output,\n  and \"Received ping\" and \"pausing before sending reply\" messages in second foundry output"),
			new TestStep(21, "rpcping with local and remote PingActors", ""),
			new TestStep(98, "", ""),
			new TestStep(
					98,
					"",
					"rpcping: see \"Processing start message\" and\n \"finished, return value\" messages in first foundry output"),
			new TestStep(22, "migrate an actor",
					"migrate: see two messages in shell output"),
			new TestStep(
					23,
					"migrate an actor with msg fwd",
					"migrate an actor with msg fwd: see 20 message sets in shell output, and fwd messages in Actor log"),
			new TestStep(24, "CreationTimer",
					"CreationTimer: see five messages in shell output"),

			new TestStep(98, "is done", "") };

	/** Test cycle times, milliseconds */
	int N1_cycleTime = 2000;
	int N2_cycleTime = 2000;

	/** Number of test steps to run */
	int N1_testCount = notes1.length, N1_cycles = 2 + N1_testCount;
	int N2_testCount = notes2.length, N2_cycles = 2 + N2_testCount;

	// ===================================================
	/**
	 * test(n) starts two-node test set.
	 * 
	 * @param <b>n</b> IP name of second node
	 */

	// ===================================================
	/** test() starts single-node test set. */

	@message
	public void test() {
		stage1 = -1;
		try { // Set up ticker for running tests.
			Id.resetTimeZero();
			otherTester = create(Tester.class);
			ActorName ticker = create(Ticker.class);
			send(ticker, "setTicker", N1_cycles, N1_cycleTime, "testSet1",
					self());
			showId("Ticker timer interval is " + N1_cycleTime + " for "
					+ N1_testCount + " tests ");
		} catch (RemoteCodeException e) {
			showId("Can't start test ticker 1 due to " + e);
		}
	}

	@message
	public void test(String otherNode) {
		// this.otherNode = otherNode;
		stage2 = -1;
		try { // Set up ticker for running tests.
			Id.resetTimeZero();
			otherTester = create(Tester.class, otherNode);
			ActorName ticker = create(Ticker.class);
			send(ticker, "setTicker", N2_cycles, N2_cycleTime, "testSet2",
					self());
			showId("Ticker timer interval is " + N2_cycleTime + " for "
					+ N2_testCount + " tests ");
		} catch (RemoteCodeException e) {
			showId("Can't start test ticker 2 due to " + e);
		}
	}

	// ===================================================
	/** testSet1() does a step of the single-node testset. */
	@message
	public void testSet1() {
		stage1 = step(notes1, stage1, N1_testCount);
	}

	// ===================================================
	/** testSet2() does a step of the two-node testset. */
	@message
	public void testSet2() {
		stage2 = step(notes2, stage2, N2_testCount);
	}

	// ===================================================
	/**
	 * step displays and logs test messages and runs a test step. It displays
	 * the end-message for test just run. (If end-message is blank, only
	 * displays timestamp.) Then it displays start-message for next test. (If
	 * start-message is blank, displays nothing.) Then it invokes the next test.
	 * <p>
	 * 
	 * showId writes to stdout. You can turn off any output by changing showId.
	 */
	@message
	public int step(TestStep[] notes, Integer stage, Integer maxStage) {
		// showId("step start, stage="+stage+"   maxStage="+maxStage);
		if (stage >= 0)
			if (notes[stage].done.length() == 0)
				showId(""); // Allow empty end messages -- for long tests
			else
				showId("End test of " + notes[stage].done);

		if (++stage < maxStage) {
			if (notes[stage].start.length() != 0) // Suppress empty start msgs
				showId("Testing " + notes[stage].start); // Say we're starting
			// test

			try {
				// notes[stage].myMethod.invoke(this, noObject); // Start test
				call(otherTester, notes[stage].myMessage);
			} catch (Exception e) {
				showId("Problem in test " + notes[stage].id + "   " + e);
			}
		} else {
			stage = maxStage;
		}
		return stage;
	}

	// ===================================================
	/** run helloworld HelloActor example. */
	@message
	public void test0() {
		try {
			ActorName uut = create(osl.examples.helloworld.HelloActor.class);
			send(uut, "hello");
		} catch (RemoteCodeException e) {
			showId("test0 problem " + e);
		}
	}

	/** run exception CatchActor example. */
	@message
	public void test1() {
		try {
			ActorName uut = create(osl.examples.exception.CatchActor.class);
			send(uut, "boot");
		} catch (RemoteCodeException e) {
			showId("test1 problem " + e);
		}
	}

	/** run fibonacci FibActor example. */
	@message
	public void test2() {
		try {
			ActorName uut = create(osl.examples.fibonacci.FibActor.class);
			send(uut, "boot", 8);
		} catch (RemoteCodeException e) {
			showId("test2 problem " + e);
		}
	}

	/** Aux routine for fibonacci output. */
	@message
	public void fibTestResult(Integer fibResult) {
		showId("fib test result is " + fibResult);
	}

	/** run fibonacci FibActor example. */
	@message
	public void test3() {
		try {
			ActorName uut = create(osl.examples.fibonacci.FibActor.class);
			send(uut, "fib", 9, self(), "fibTestResult");
		} catch (RemoteCodeException e) {
			showId("test3 problem " + e);
		}
	}

	/** run fibonacci FibSuicide example. */
	@message
	public void test4() {
		try {
			ActorName uut = create(osl.examples.fibonacci.FibSuicide.class);
			send(uut, "fib", 12, self(), "fibTestResult");
		} catch (RemoteCodeException e) {
			showId("test4 problem " + e);
		}
	}

	/** run ping PingActor example. */
	@message
	public void test5() {
		try {
			ActorName uut = create(osl.examples.ping.PingActor.class);
			ActorName uuo = create(osl.examples.ping.PingActor.class);
			send(uut, "start", uuo);
		} catch (RemoteCodeException e) {
			showId("test5 problem " + e);
		}
	}

	/** run pingpong PingActor example. */
	@message
	public void test6() {
		try {
			ActorName uut = create(osl.examples.pingpong.PingActor.class);
			ActorName uuo = create(osl.examples.pingpong.PingActor.class);
			send(uut, "start", uuo, 12);
		} catch (RemoteCodeException e) {
			showId("test6 problem " + e);
		}
	}

	/** run quicksort example. */
	@message
	public void test7() {
		try {
			ActorName qst = create(osl.examples.sorting.Quicksort.class);
			send(qst, "boot", 10);
		} catch (RemoteCodeException e) {
			showId("test7 problem " + e);
		}
	}

	/** run Threadring benchmark. */
	@message
	public void test8() {
		try {
			ActorName qst = create(osl.examples.benchmarks.Threadring.class);
			send(qst, "boot", 100000);
		} catch (RemoteCodeException e) {
			showId("test8 problem " + e);
		}
	}

	/** run Chameneos-redux benchmark. */
	@message
	public void test9() {
		try {
			ActorName qst = create(osl.examples.benchmarks.Broker.class);
			send(qst, "boot", 60000);
		} catch (RemoteCodeException e) {
			showId("test9 problem " + e);
		}
	}

	/** run inheritance test example. */
	@message
	public void test10() {
		try {
			ActorName qst = create(osl.examples.inheritance.HeyActor.class);
			send(qst, "hey");
		} catch (RemoteCodeException e) {
			showId("test10 problem " + e);
		}
	}

	@message
	public void test11() {
		try {
			ActorName xampi = create(osl.examples.xampi.Xampi.class);
			send(xampi, "relayPrintFloat", 1.2f);
		} catch (RemoteCodeException e) {
			showId("test11 problem " + e);
		}
	}

	@message
	public void test12() {
		try {
			ActorName bufferTester = create(osl.examples.inheritance.buffer.BufferTester.class);
			send(bufferTester, "test");
		} catch (RemoteCodeException e) {
			showId("test12 problem " + e);
		}
	}

	@message
	public void test13() {
		try {
			ActorName bufferTester = create(osl.examples.producer_consumer.BPCTester.class);
			send(bufferTester, "test");
		} catch (RemoteCodeException e) {
			showId("test13 problem " + e);
		}
	}

	/** run fibonacci JoinFibActor example. */
	@message
	public void test14() {
		try {
			ActorName uut = create(osl.examples.fibonacci.JoinFibActor.class);
			send(uut, "boot", 22);
		} catch (RemoteCodeException e) {
			showId("test14 problem " + e);
		}
	}

	/** run exception BadMessageActor example. */
	@message
	public void test15() {
		try {
			ActorName uut = create(osl.examples.exception.BadMessageActor.class);
			send(uut, "boot");
		} catch (RemoteCodeException e) {
			showId("test15 problem " + e);
		}
	}

	/** run remoteping example. */
	@message
	public void test20() {
		try {
			ActorName uut = create(osl.examples.remoteping.PingBoot.class);
			send(uut, "boot", otherNode);
		} catch (RemoteCodeException e) {
			showId("test20 problem " + e);
		}
	}

	/** run rpcping example. */
	@message
	public void test21() {
		try {
			ActorName uut = create(osl.examples.rpcping.PingBoot.class);
			send(uut, "boot", otherNode);
		} catch (RemoteCodeException e) {
			showId("test21 problem " + e);
		}
	}

	/** migrate an actor from node 0 to node 1 */
	@message
	public void test22() {
		try {
			ActorName uut = create(osl.examples.migrate.MigrateActor.class);
			send(uut, "boot", otherNode);
		} catch (RemoteCodeException e) {
			showId("test22 problem " + e);
		}
	}

	/**
	 * migrate an actor from node 0 to node 1 while sending messages, and
	 * confirm message count.
	 */
	@message
	public void test23() {
		try {
			ActorName uut = create(osl.examples.migrate.MigrateActor2.class);
			send(uut, "boot", otherNode, 20);
		} catch (RemoteCodeException e) {
			showId("test23 problem " + e);
		}
	}

	/** time actor creation by creating CreationTimer instances. */
	@message
	public void test24() {
		try {
			ActorManagerName remoteManager = (ActorManagerName) invokeService(
					YP.name, "ypLookupRemoteManager", otherNode);
			ActorName uut = create(osl.examples.performance.CreationTimer.class);
			send(uut, "start", remoteManager, 20);
		} catch (Exception e) {
			showId("test24 problem " + e);
		}
	}

	// ===================================================
	/**
	 * test98 provides time-markers. It allows use of long tests like rpcping
	 * without slowing other tests down too much. (So, test98 isn't a test, it's
	 * a feature.)
	 */
	@message
	public void test98() {
	}

	/**
	 * test99 should be the last-referenced test in notes1 and notes2. It is an
	 * error-marker, so it's a bug, not a feature, if it runs.
	 */
	@message
	public void test99() {
		showId("No such test!");
	}
	// ===================================================
	/* prototypes for pre-text, post-text, and test method. */
	/*
	 * new TestStep(XX, "YY", "YY:"),
	 * 
	 * public void testXX() { try { ActorName uut =
	 * create(osl.examples.YY.class); send (uut, "start"); } catch
	 * (RemoteCodeException e) { showId("testXX problem " + e); } }
	 */
	// ===================================================
}
