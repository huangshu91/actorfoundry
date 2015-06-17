package osl.examples.sor;

import java.io.Serializable;

import osl.manager.ActorName;

public class Border implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8707865978369362979L;
	public Border(ActorName[] borderActors) {
		this.borderActors = borderActors;
	}
	public ActorName[] borderActors;
	
	public Border() {
	}	
}

