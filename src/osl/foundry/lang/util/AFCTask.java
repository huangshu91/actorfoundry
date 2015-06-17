/**
 * Developed by: The Open Systems Lab
 *             University of Illinois at Urbana-Champaign
 *             Department of Computer Science
 *             Urbana, IL 61801
 *             http://osl.cs.uiuc.edu
 *
 * Contact: http://osl.cs.uiuc.edu/af
 *
 * Copyright (c) 1998-2009
 * The University of Illinois Board of Trustees.
 *    All Rights Reserved.
 * 
 * Distributed under license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.foundry.lang.util;

import java.io.File;
import java.util.Vector;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import osl.foundry.lang.ActorFoundryCompiler;

/**
 * @author Amin Shali <shali1@cs.uiuc.edu> Dec 29, 2008
 * 
 */
public class AFCTask extends Task {
	private String outputDir;

	/**
	 * @return the outputDir
	 */
	public String getOutputDir() {
		return outputDir;
	}

	/**
	 * @param outputDir
	 *            the outputDir to set
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	Vector<FileSet> filesets = new Vector<FileSet>();

	public void addFileSet(FileSet fileset) {
		if (!filesets.contains(fileset)) {
			filesets.add(fileset);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		DirectoryScanner ds;
		Vector<String> args = new Vector<String>();
		args.add("-o");
		args.add(getOutputDir());
		for (FileSet fileset : filesets) {
			ds = fileset.getDirectoryScanner(getProject());
			File dir = ds.getBasedir();
			String[] filesInSet = ds.getIncludedFiles();
			for (String filename : filesInSet) {
				File file = new File(dir, filename);
				args.add(file.getAbsolutePath());
			}
		}
		ActorFoundryCompiler afc = new ActorFoundryCompiler();
		afc.compile(args.toArray(new String[0]));
	}

}
