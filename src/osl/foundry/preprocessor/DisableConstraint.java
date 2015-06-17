package osl.foundry.preprocessor;

import java.util.ArrayList;
import java.util.List;

public class DisableConstraint {
	public String getMessageName() {
		return messageName;
	}

	public String returnType;
	public String name;
	public List<String> argList = new ArrayList<String>();
	public String messageName;

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

}
