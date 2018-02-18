package movement;

import processing.core.PVector;

public class KinematicArrive {
	private Character character;
	private Target target = new Target();
	float maxSpeed = 30f;
	float radiusOfSatisfaction = 10;
	float radiusOfDeceleration = 30;
	float timeToTarget = 0.25f;
	
	public KinematicArrive(Character character, Target target) {
		super();
		this.character = character;
		this.target = target;
	}
	
	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput s = new KinematicSteeringOutput();
		PVector direction = PVector.sub(target.getPosition(), character.getKinematic().getPosition());
		float distance = direction.mag();
		float goalSpeed;
		if(distance < radiusOfSatisfaction)
			goalSpeed = 0;
		else if (distance > radiusOfDeceleration)
			goalSpeed = maxSpeed;
		else 
			goalSpeed = maxSpeed * distance / radiusOfDeceleration;
		direction.normalize();
		s.setVelocity(direction.mult(goalSpeed));
		float angle = getNewOrientation(character.getKinematic().getOrientation(), s.getVelocity());
		character.getKinematic().setOrientation(angle);
		s.setRotation(0);
		
		return s;
	}
	private float getNewOrientation(float orientation, PVector velocity) {
		if(velocity.mag() > 0) 
			return (float) Math.atan2(velocity.y, velocity.x);
		else 
			return orientation;
	}
}
