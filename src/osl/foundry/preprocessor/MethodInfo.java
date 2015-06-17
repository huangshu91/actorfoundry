package osl.foundry.preprocessor;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
	public String returnType;
	public String name;
	public List<String> argList = new ArrayList<String>();
	public boolean hasDisableConstraint = false;
	public List<DisableConstraint> disableConstraints = new ArrayList<DisableConstraint>();

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name + ":" + returnType + "( ");
		for (String t : argList) {
			stringBuilder.append(t + ", ");
		}
		stringBuilder.append(")\n");
		return stringBuilder.toString();
	}

	public String getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}

	public List<String> getArgList() {
		return argList;
	}

	public boolean isHasDisableConstraint() {
		return hasDisableConstraint;
	}

	public void setHasDisableConstraint(boolean hasDisableConstraint) {
		this.hasDisableConstraint = hasDisableConstraint;
	}

	public List<DisableConstraint> getDisableConstraints() {
		return disableConstraints;
	}

	public void setDisableConstraints(List<DisableConstraint> disableConstraints) {
		this.disableConstraints = disableConstraints;
	}

}
