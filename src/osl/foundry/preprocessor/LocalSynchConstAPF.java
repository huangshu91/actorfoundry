package osl.foundry.preprocessor;

import static com.sun.mirror.util.DeclarationVisitors.NO_OP;
import static com.sun.mirror.util.DeclarationVisitors.getDeclarationScanner;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import osl.manager.annotations.message;
import osl.util.constraints.Disable;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.Declaration;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.Modifier;
import com.sun.mirror.declaration.ParameterDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.ClassType;
import com.sun.mirror.util.SimpleDeclarationVisitor;

public class LocalSynchConstAPF implements AnnotationProcessorFactory {
	// Process any set of annotations
	private static final Collection<String> supportedAnnotations = unmodifiableCollection(Arrays
			.asList("osl.util.constraints.Disable"));

	// No supported options
	private static final Collection<String> supportedOptions = emptySet();

	public Collection<String> supportedAnnotationTypes() {
		return supportedAnnotations;
	}

	public Collection<String> supportedOptions() {
		return supportedOptions;
	}

	public AnnotationProcessor getProcessorFor(
			Set<AnnotationTypeDeclaration> atds,
			AnnotationProcessorEnvironment env) {
		return new LocalSynchConstAP(env, atds);
	}

	private static class LocalSynchConstAP implements AnnotationProcessor {
		private final AnnotationProcessorEnvironment env;

		private final Set<AnnotationTypeDeclaration> atds;

		LocalSynchConstAP(AnnotationProcessorEnvironment env,
				Set<AnnotationTypeDeclaration> atds) {
			this.env = env;
			this.atds = atds;
		}

		public void process() {
			for (Declaration typeDecl : env
					.getDeclarationsAnnotatedWith((AnnotationTypeDeclaration) atds
							.toArray()[0]))
				typeDecl.accept(getDeclarationScanner(
						new DisableMethodVisitor(), NO_OP));
		}

		private static class DisableMethodVisitor extends
				SimpleDeclarationVisitor {

			@SuppressWarnings("unchecked")
			private Boolean checkParameters(MethodDeclaration m,
					TypeDeclaration c) {
				String correspondingMethodName = m.getAnnotation(Disable.class)
						.messageName();
				Collection<MethodDeclaration> methods = (Collection<MethodDeclaration>) c
						.getMethods();
				for (Iterator<MethodDeclaration> iterator = methods.iterator(); iterator
						.hasNext();) {
					MethodDeclaration methodDeclaration = (MethodDeclaration) iterator
							.next();
					if (methodDeclaration.getModifiers().contains(
							Modifier.PUBLIC)
							&& methodDeclaration.getAnnotation(message.class) != null) {
						if (methodDeclaration.getSimpleName().equals(
								correspondingMethodName)) {
							Collection<ParameterDeclaration> p1 = methodDeclaration
									.getParameters();
							Collection<ParameterDeclaration> p2 = m
									.getParameters();
							if (p1.size() == p2.size()) {
								Boolean parametersEqual = true;
								for (Iterator<ParameterDeclaration> iterator2 = p2
										.iterator(), iterator1 = p1.iterator(); iterator2
										.hasNext()
										&& iterator1.hasNext();) {
									ParameterDeclaration parameterDeclaration1 = (ParameterDeclaration) iterator1
											.next();
									ParameterDeclaration parameterDeclaration2 = (ParameterDeclaration) iterator2
											.next();
									if (!parameterDeclaration1.getType()
											.equals(
													parameterDeclaration2
															.getType())) {
										parametersEqual = false;
										break;
									}
								}
								if (parametersEqual) {
									return true;
								}
							}
						}
					}
				}
				return false;
			}

			@Override
			public void visitMethodDeclaration(MethodDeclaration arg0) {

				/*
				 * System.out.println(arg0.getDeclaringType().getQualifiedName()
				 * + ":" + arg0.getSimpleName() + ":" + arg0.getReturnType() +
				 * ":" + arg0.getParameters());
				 */// TODO: check the return type to be java.lang.Boolean
				Boolean returnTypeOK = false;
				if (arg0.getReturnType() instanceof ClassType) {
					ClassType classType = (ClassType) arg0.getReturnType();
					if (classType.getDeclaration().getQualifiedName().equals(
							"java.lang.Boolean")) {
						returnTypeOK = true;
					}
				}
				if (!returnTypeOK) {
					System.out
							.println("ERROR: Invalid return type for disable method in: ");
					System.out
							.println("          "
									+ arg0.getDeclaringType()
											.getQualifiedName() + ":"
									+ arg0.getSimpleName() + ":"
									+ arg0.getParameters());

				}
				Boolean correspondingMethodOK = false;
				Boolean finishCheck = false;
				TypeDeclaration c = arg0.getDeclaringType();
				while (!finishCheck
						&& !c.getQualifiedName().equals("java.lang.Object")) {
					if (checkParameters(arg0, c)) {
						correspondingMethodOK = true;
						break;
					}
					c = ((ClassDeclaration) c).getSuperclass().getDeclaration();
				}

				if (!correspondingMethodOK) {
					System.out
							.println("ERROR: There is no such corresponding method for this disable constraint: ");
					System.out
							.println("          "
									+ arg0.getDeclaringType()
											.getQualifiedName() + ":"
									+ arg0.getSimpleName() + ":"
									+ arg0.getParameters());

				}
			}
		}
	}
}
