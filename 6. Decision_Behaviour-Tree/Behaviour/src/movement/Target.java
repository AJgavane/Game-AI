package movement;

import processing.core.PVector;

public class Target {
	private Kinematic kinematic = new Kinematic();
	private PVector position = new PVector(0,0);

	public Target(PVector position) {
		this.position = position;
	}

	public Target() {
		super();
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void setKinematic(Kinematic kinematic) {
		this.kinematic = kinematic;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}
	
	
}
