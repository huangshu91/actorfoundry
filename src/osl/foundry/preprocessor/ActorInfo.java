package osl.foundry.preprocessor;

import java.util.ArrayList;
import java.util.List;

public class ActorInfo {
	public String name;
	public String packageName;
	public String superClassName;
	public List<MethodInfo> methodList = new ArrayList<MethodInfo>();
	public List<MethodInfo> inheritedMethodList = new ArrayList<MethodInfo>();
	List<DisableConstraint> disableConstraints = new ArrayList<DisableConstraint>();

	public void populateMessageDisables() {
		List<DisableConstraint> hasNoMethodList = new ArrayList<DisableConstraint>();
		for (DisableConstraint d : disableConstraints) {
			boolean hasMethod = false;
			for (MethodInfo m : methodList) {
				if (m.name.equals(d.messageName)
						&& m.argList.toString().equals(d.argList.toString())) {
					m.disableConstraints.add(d);
					m.setHasDisableConstraint(true);
					hasMethod = true;
					break;
				}
			}
			if (!hasMethod) {
				hasNoMethodList.add(d);
			}
		}
		for (DisableConstraint d : hasNoMethodList) {
			boolean hasMethod = false;
			for (MethodInfo m : inheritedMethodList) {
				if (m.getName().equals(d.getMessageName())) {
					m.disableConstraints.add(d);
					m.setHasDisableConstraint(true);
					hasMethod = true;
					break;
				}
			}
			if (!hasMethod) {
				MethodInfo m = new MethodInfo();
				m.argList = d.argList;
				m.name = d.messageName;
				m.disableConstraints.add(d);
				m.setHasDisableConstraint(true);
				inheritedMethodList.add(m);
			}
		}
	}

	public boolean isHasDisableConstraints() {
		return disableConstraints.size() > 0;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name + ", " + packageName + ", " + superClassName
				+ "\n");
		for (MethodInfo mi : methodList) {
			stringBuilder.append(mi);
		}
		return stringBuilder.toString();
	}

	public String getName() {
		return name;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getSuperClassName() {
		return superClassName;
	}

	public List<MethodInfo> getMethodList() {
		return methodList;
	}

	public List<MethodInfo> getInheritedMethodList() {
		return inheritedMethodList;
	}
}
