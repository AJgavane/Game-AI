package movement;

import processing.core.PVector;

public class SteeringSeek {
	private Character character;
	private Target target;
	private float maxAcceleration = Constants.MAX_ACCELERATION;
	
	public SteeringSeek(Character character, Target target) {
		this.character = character;
		this.target = target;
	}

	public SteeringOutput getSteering() {
		SteeringOutput steering = new SteeringOutput();
		PVector accel = PVector.sub(target.getPosition(), character.getKinematic().getPosition());
		accel.normalize();
		accel.mult(maxAcceleration);
		steering.setLinearAccel(accel);
		steering.setAngluarAccel(0);
		return steering;
	}
}
