package osl.foundry.preprocessor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Type;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;

public class ExecutorCodeGen extends Doclet {

	private static final String OUTPUT_DIR_OPT = "-outdir";

	private static String readOptions(String[][] options) {
		String tagName = null;
		for (int i = 0; i < options.length; i++) {
			String[] opt = options[i];
			if (opt[0].equals(OUTPUT_DIR_OPT)) {
				tagName = opt[1];
			}
		}
		return tagName;
	}

	public static int optionLength(String option) {
		if (option.equals(OUTPUT_DIR_OPT)) {
			return 2;
		}
		return 0;
	}

	public static boolean validOptions(String options[][],
			DocErrorReporter reporter) {
		boolean foundTagOption = false;
		for (int i = 0; i < options.length; i++) {
			String[] opt = options[i];
			if (opt[0].equals(OUTPUT_DIR_OPT)) {
				if (foundTagOption) {
					reporter.printError("Only one " + OUTPUT_DIR_OPT
							+ " option allowed.");
					return false;
				} else {
					foundTagOption = true;
				}
			}
		}
		if (!foundTagOption) {
			reporter.printError("Usage: javadoc " + OUTPUT_DIR_OPT
					+ " out_dir -doclet ListTags ...");
		}
		return foundTagOption;
	}

	public static File createOutputFile(String outDirName, ActorInfo actorInfo) {
		File fileOutDir = new File(outDirName
				+ actorInfo.getPackageName().replace('.', File.separatorChar)
				+ File.separator);
		if (!fileOutDir.exists())
			fileOutDir.mkdirs();
		File f = new File(fileOutDir.getAbsolutePath() + File.separator
				+ actorInfo.getName() + "Executor.java");
		return f;
	}

	public static boolean start(RootDoc root) {

		String outDirName = readOptions(root.options());
		outDirName += File.separator;

		ClassDoc[] classes = root.classes();
		for (ClassDoc classDoc : classes) {
			if (classDoc.isInterface())
				continue;
			Type superClassType = classDoc.superclassType();
			while (!superClassType.qualifiedTypeName().equals(
					"java.lang.Object")) {
				if (superClassType.qualifiedTypeName().equals(
						"osl.manager.Actor")) {
					// TODO
					ActorInfo actorInfo = extractActorInfo(classDoc);
					// System.out.println(actorInfo);

					Template template = null;
					String templateFile = "ActorExecutor.vm";
					try {
						Velocity.init("velocity.properties");
						template = Velocity.getTemplate(templateFile);
						VelocityContext context = new VelocityContext();
						context.put("actorInfo", actorInfo);
						/*
						 * BufferedWriter writer = new BufferedWriter( new
						 * OutputStreamWriter(System.out));
						 */

						FileWriter writer = new FileWriter(createOutputFile(
								outDirName, actorInfo));

						if (template != null) {
							template.merge(context, writer);
						}

						writer.flush();
					} catch (ResourceNotFoundException rnfe) {
						System.out
								.println("Example : error : cannot find template "
										+ templateFile);
					} catch (ParseErrorException pee) {
						System.out
								.println("Example : Syntax error in template "
										+ templateFile + ":" + pee);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				} else {
					superClassType = superClassType.asClassDoc()
							.superclassType();
				}
			}
		}
		return true;

	}

	private static ActorInfo extractActorInfo(ClassDoc classDoc) {
		ActorInfo actorInfo = new ActorInfo();
		actorInfo.name = classDoc.typeName();
		actorInfo.packageName = classDoc.containingPackage().name();
		actorInfo.superClassName = classDoc.superclassType()
				.qualifiedTypeName();
		actorInfo.methodList = new ArrayList<MethodInfo>();

		MethodDoc[] methodDocs = classDoc.methods();
		for (MethodDoc methodDoc : methodDocs) {
			if (isMessage(methodDoc)) {
				MethodInfo methodInfo = new MethodInfo();
				actorInfo.methodList.add(populateMethodInfo(methodInfo,
						methodDoc));
			} else if (isDisable(methodDoc)) {
				DisableConstraint disableConstraint = new DisableConstraint();
				actorInfo.disableConstraints.add(populateDisableConstraint(
						disableConstraint, methodDoc));
			}
		}
		actorInfo.populateMessageDisables();
		return actorInfo;
	}

	private static DisableConstraint populateDisableConstraint(
			DisableConstraint disableConstraint, MethodDoc methodDoc) {
		disableConstraint.name = methodDoc.name();
		disableConstraint.returnType = methodDoc.returnType()
				.qualifiedTypeName();
		disableConstraint.argList = new ArrayList<String>();
		Parameter[] parameters = methodDoc.parameters();
		for (Parameter p : parameters) {
			disableConstraint.argList.add(p.type().qualifiedTypeName()
					+ p.type().dimension());
		}
		disableConstraint.messageName = getMessageNameForDisableConst(methodDoc);
		return disableConstraint;
	}

	private static MethodInfo populateMethodInfo(MethodInfo methodInfo,
			MethodDoc methodDoc) {
		methodInfo.name = methodDoc.name();
		methodInfo.returnType = methodDoc.returnType().qualifiedTypeName();
		methodInfo.argList = new ArrayList<String>();
		Parameter[] parameters = methodDoc.parameters();
		for (Parameter p : parameters) {
			methodInfo.argList.add(p.type().qualifiedTypeName()
					+ p.type().dimension());
		}
		return methodInfo;
	}

	private static Boolean isMessage(MethodDoc methodDoc) {
		if (methodDoc.isPublic()) {
			AnnotationDesc[] annotationDescs = methodDoc.annotations();
			for (AnnotationDesc ad : annotationDescs) {
				if (ad.annotationType().qualifiedTypeName().equals(
						"osl.manager.annotations.message")
						|| ad.annotationType().qualifiedTypeName().equals(
								"kilim.pausable")) {
					return true;
				}
			}
		}
		return false;
	}

	private static Boolean isDisable(MethodDoc methodDoc) {
		if (methodDoc.isPublic()) {
			AnnotationDesc[] annotationDescs = methodDoc.annotations();
			for (AnnotationDesc ad : annotationDescs) {
				if (ad.annotationType().qualifiedTypeName().equals(
						"osl.util.constraints.Disable")) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getMessageNameForDisableConst(MethodDoc methodDoc) {
		if (methodDoc.isPublic()) {
			AnnotationDesc[] annotationDescs = methodDoc.annotations();
			for (AnnotationDesc ad : annotationDescs) {
				if (ad.annotationType().qualifiedTypeName().equals(
						"osl.util.constraints.Disable")) {
					for (ElementValuePair ev : ad.elementValues()) {
						if (ev.element().name().equals("messageName")) {
							return ev.value().toString().replaceAll("\"", "");
						}
					}
					return null;
				}
			}
		}
		return null;
	}

}
