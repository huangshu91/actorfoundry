package osl.foundry;

import java.io.File;

/**
 * This class is used to create type-safe constants to specify directories in
 * the foundry status directory structure. Note that the constructor uses
 * package protectection so that random constants can't be created by external
 * classes.
 * 
 * @author Mark Astley
 * @version $Revision: 1.5 $ ($Date: 1998/10/05 15:47:35 $)
 */

public final class FoundryStatusDir {
	/**
	 * A reference to the parent of this directory, or NULL if this directory is
	 * rooted at STATUS.
	 */
	FoundryStatusDir parent;

	/**
	 * The name of this directory.
	 */
	String name;

	/**
	 * Package protect the constructor so that only classes in osl.foundry can
	 * instantiate this class.
	 * 
	 * @param <b>N</b> The <em>String</em> name of this directory. The parent
	 *        field is set to <tt>null</tt> indicating that this directory is
	 *        rooted at <tt>STATUS</tt> (defaults to /var).
	 */
	public FoundryStatusDir(String N) {
		this(null, N);
	}

	/**
	 * Package protect the constructor so that only classes in osl.foundry can
	 * instantiate this class.
	 * 
	 * @param <b>P</b> A <em>FoundryStatusDir</em> reference to the parent of
	 *        this directory. A value of <tt>null</tt> indicates that this
	 *        directory is rooted at <tt>STATUS</tt>.
	 * @param <b>N</b> The <em>String</em> name of this directory relative to
	 *        the name of its parent.
	 */
	public FoundryStatusDir(FoundryStatusDir P, String N) {
		parent = P;
		name = N;
	}

	/**
	 * Return the absolute path name of this directory complete with the
	 * appropriate file separators inserted.
	 */
	public String getName() {
		if (parent == null)
			return name;
		else
			return parent.getName() + File.separator + name;
	}

	/**
	 * Return true if two directories have the same name.
	 */
	public boolean equals(Object o) {
		return ((o instanceof FoundryStatusDir) && ((FoundryStatusDir) o)
				.getName().equals(getName()));
	}

	/**
	 * Use the name field to make a hash code for this structure.
	 */
	public int hashCode() {
		return getName().hashCode();
	}
}
