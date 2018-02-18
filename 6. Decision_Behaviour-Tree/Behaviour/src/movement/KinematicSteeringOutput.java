package movement;

import processing.core.PVector;

public class KinematicSteeringOutput {
	private PVector velocity = new PVector(0,0);
	private float rotation = 0;
	
	public KinematicSteeringOutput() {
		super();
	}
	
	public PVector getVelocity() {
		return velocity;
	}
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
