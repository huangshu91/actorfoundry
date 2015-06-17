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
package osl.foundry.lang;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenRewriteStream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Amin Shali <shali1@cs.uiuc.edu> Dec 29, 2008
 * 
 */
public class ActorFoundryCompiler {

	private static Options options = new Options();

	static {
		Option outputOption = new Option("o", "output-dir", true,
				"Output directory for generated files.");
		outputOption.setRequired(true);
		options.addOption(outputOption);
	}

	public void compile(String[] args) {
		CommandLineParser commandLineParser = new GnuParser();
		try {
			CommandLine commandLine = commandLineParser.parse(options, args);
			String outputDirStr = commandLine.getOptionValue("o");
			File outputDir = new File(outputDirStr);
			if (!outputDir.exists()) {
				outputDir.mkdirs();
			}
			for (String f : commandLine.getArgs()) {
				File actorFile = new File(f);
				FileInputStream actorFileStream = new FileInputStream(actorFile);
				StringBuffer sb = generateJavaCode(actorFileStream);
				File packageDir = new File(outputDir.getAbsolutePath()
						+ File.separator
						+ packageQName.replace(".", File.separator));
				if (!packageDir.exists()) {
					packageDir.mkdirs();
				}
				File outFile = new File(packageDir.getAbsolutePath()
						+ File.separator
						+ actorFile.getName().substring(0,
								actorFile.getName().indexOf(".actor"))+".java");
				outFile.createNewFile();
				FileOutputStream outStream = new FileOutputStream(outFile);
				outStream.write(sb.toString().getBytes());
				outStream.close();
				actorFileStream.close();
			}
			System.out.println("Compilation finished.");

		} catch (ParseException e) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("afc [OPTIONS] [FILES]", options);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (RecognitionException e) {
			System.err.println(e.getMessage());
		}

	}

	private String packageQName = "";
	private StringBuffer generateJavaCode(FileInputStream actorFile)
			throws FileNotFoundException, IOException, RecognitionException {
		ANTLRInputStream input = new ANTLRInputStream(actorFile);
		ActorFoundryLexer lexer = new ActorFoundryLexer(input);
		TokenRewriteStream tokens = new TokenRewriteStream(lexer);
		ActorFoundryParser parser = new ActorFoundryParser(tokens);
		parser.compilationUnit();
		packageQName = (String) parser.getPackageQName();
		return new StringBuffer(tokens.toString());
	}

	public static void main(String[] args) throws Exception {
		ActorFoundryCompiler afCompiler = new ActorFoundryCompiler();
		afCompiler.compile(args);
	}

}
