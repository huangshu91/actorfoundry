package osl.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * This class is used to make deep copies of arbitrary objects as well as
 * provide a general set of tools for serializing data.
 * <p>
 * 
 * @author Mark Astley
 * @version $Revision: 1.2 $ ($Date: 1999/01/19 18:43:37 $)
 */

public class DeepCopy {

	// ///////////////////////////////////////////////////
	// Utility routines for copying objects
	// ///////////////////////////////////////////////////

	/**
	 * Create a deep copy of an arbitrary object. The deep copy is created by
	 * first serializing and then deserializing the object. This is a costly
	 * process, thus this method should only be used when absolutely necessary.
	 */
	public static Object deepCopy(Serializable arg) throws IOException {

		try {
			if (arg == null)
				return null;

			// Vars needed to serialize
			ByteArrayOutputStream serializeArgStream;
			ObjectOutputStream serializeOut;
			ByteArrayInputStream deserializeArgStream;
			ObjectInputStream serializeIn;
			Object toReturn;

			// First, serialize the message object
			// PRAGMA [debug,osl.util.DeepCopy]
			// Log.println("<DeepCopy.deepCopy> Writing out object to serialize: "
			// + arg);
			serializeArgStream = new ByteArrayOutputStream();
			serializeOut = new ObjectOutputStream(serializeArgStream);
			serializeOut.writeObject(arg);
			serializeOut.flush();

			// PRAGMA [debug,osl.util.DeepCopy]
			// Log.println("<DeepCopy.deepCopy> Reading serialized object");
			deserializeArgStream = new ByteArrayInputStream(serializeArgStream
					.toByteArray());
			serializeIn = new ObjectInputStream(deserializeArgStream);
			toReturn = serializeIn.readObject();

			// PRAGMA [debug,osl.util.DeepCopy]
			// Log.println("<DeepCopy.deepCopy> Returning result");
			return toReturn;
		} catch (ClassNotFoundException e) {
			// This should never happen. Normally, this exception would be
			// thrown during deserialization because the deserializer needed
			// to create an instance of an unknown class. However, since we
			// know we did the serialize first (and hence already have all
			// the appropriate class definitions) this error should never
			// happen. If it does then we have a bug in the foundry.
			Assert.afAssert(false,
					"should never catch ClassNotFoundException here");
			return null;
		}

	}

	/**
	 * Serializes an object and returns the byte array corresponding to the
	 * serialized form.
	 */
	public static byte[] serialize(Serializable arg) throws IOException {

		// Vars needed to serialize
		ByteArrayOutputStream serializeArgStream;
		ObjectOutputStream serializeOut;

		// Serialize the object
		serializeArgStream = new ByteArrayOutputStream();
		serializeOut = new ObjectOutputStream(serializeArgStream);
		serializeOut.writeObject(arg);
		serializeOut.flush();

		return serializeArgStream.toByteArray();
	}

	/**
	 * Deserializes a byte array into an object.
	 * 
	 * @exception java.lang.ClassNotFoundException
	 *                Thrown if the deserialization process requires a class
	 *                definition which cannot be found.
	 */
	public static Object deserialize(byte[] stream) throws IOException,
			ClassNotFoundException {

		// Vars needed to deserialize
		ByteArrayInputStream deserializeArgStream;
		ObjectInputStream serializeIn;
		Object toReturn;

		// Deserialize the object
		deserializeArgStream = new ByteArrayInputStream(stream);
		serializeIn = new ObjectInputStream(deserializeArgStream);
		toReturn = serializeIn.readObject();

		// And return the object
		return toReturn;
	}

	public static HashMap<Class<?>, Class<?>> immutableClasses = null;

	public static boolean isMutableClass(Class<?> className) {
		return immutableClasses.get(className) == null;
	}

	/**
	 * This method takes a class name and determines whether or not the class is
	 * mutable. The native method keeps a static list of classes which are
	 * mutable and does a quick search to see if the given class name is in this
	 * list. This is a useful method for "clone" returns. In particular, it is
	 * not necessary to create deep copies of non-mutable classes. Instead, we
	 * can just copy a link to them, which is much more efficient.
	 */
	public static native boolean isMutable(String className);

	static {
		if (osl.foundry.FoundryStart.useNative) {
			System.err
					.println("Using libdeepcopy native implementation $Revision: 1.2 $ ($Date: 1999/01/19 18:43:37 $)");
			System.loadLibrary("deepcopy");
		} else {
			immutableClasses = new HashMap<Class<?>, Class<?>>(20);
			immutableClasses.put(String.class, String.class);
			immutableClasses.put(Integer.class, Integer.class);
			immutableClasses.put(Double.class, Double.class);
			immutableClasses.put(Float.class, Float.class);
			immutableClasses.put(Long.class, Long.class);
			immutableClasses.put(Boolean.class, Boolean.class);
			immutableClasses.put(Byte.class, Byte.class);
			immutableClasses.put(Short.class, Short.class);
			immutableClasses.put(Character.class, Character.class);
			immutableClasses.put(osl.manager.ActorName.class,
					osl.manager.ActorName.class);
			immutableClasses.put(osl.manager.ActorContext.class,
					osl.manager.ActorContext.class);
		}
	}
}
