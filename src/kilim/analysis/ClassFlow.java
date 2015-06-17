/* Copyright (c) 2006, Sriram Srinivasan
 *
 * You may distribute this software under the terms of the license 
 * specified in the file "License"
 */
package kilim.analysis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import kilim.Constants;
import kilim.KilimException;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 * This class reads a .class file (or stream), wraps each method with a
 * MethodFlow object and optionally analyzes it.
 * 
 */
public class ClassFlow extends ClassNode {
	ArrayList<MethodFlow> methodFlows;
	ClassReader cr;
	String classDesc;
	/**
	 * true if any of the methods contained in the class file is pausable.
	 * ClassWeaver uses it later to avoid weaving if isPausable isn't true.
	 */
	private boolean isPausable;
	/**
	 * true if the .class being read is already woven. All woven files have a
	 * pausable annotation on the entire class.
	 */
	private boolean isWoven = false;

	public ClassFlow(InputStream is) throws IOException {
		cr = new ClassReader(is);
	}

	public ClassFlow(String aClassName) throws IOException {
		cr = new ClassReader(aClassName);
	}

	@SuppressWarnings( { "unchecked" })
	public MethodVisitor visitMethod(final int access, final String name,
			final String desc, final String signature, final String[] exceptions) {
		MethodFlow mn = new MethodFlow(this, access, name, desc, signature,
				exceptions);
		super.methods.add(mn);
		return mn;
	}

	public ArrayList<MethodFlow> getMethodFlows() {
		assert (methodFlows != null) : "ClassFlow.analyze not called";
		return methodFlows;
	}

	public ArrayList<MethodFlow> analyze(boolean forceAnalysis)
			throws KilimException {
		// cr.accept(this, ClassReader.SKIP_DEBUG);
		cr.accept(this, false);
		cr = null; // We don't need this any more.
		classDesc = TypeDesc.getInterned("L" + name + ';');
		ArrayList<MethodFlow> flows = new ArrayList<MethodFlow>(methods.size());
		for (Object o : methods) {
			MethodFlow mf = (MethodFlow) o;
			mf.verifyPausables();
			if (mf.isPausable())
				isPausable = true;
			if ((mf.isPausable() || forceAnalysis) && (!mf.isAbstract())) {
				mf.analyze();
			}
			flows.add(mf);
		}
		methodFlows = flows;
		return flows;
	}

	public String getClassDescriptor() {
		return classDesc;
	}

	public String getClassName() {
		return super.name.replace('/', '.');
	}

	public boolean isPausable() {
		getMethodFlows(); // check analyze has been run.
		return isPausable;
	}

	public boolean isWoven() {
		return isWoven;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		AnnotationVisitor ret = super.visitAnnotation(desc, visible);
		if (desc.equals(Constants.D_PAUSABLE)
				|| desc.equals(Constants.D_MESSAGE)) {
			isWoven = true;
		}
		return ret;
	}

	boolean isInterface() {
		return (this.access & Opcodes.ACC_INTERFACE) != 0;
	}
}
