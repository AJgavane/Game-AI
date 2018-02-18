package movement;

import graph.ObstacleAvoidance;
import processing.core.PVector;

public class SteeringArrive {
	private Character character;
	private Target target = new Target();
	private float maxAcceleration = Constants.MAX_ACCELERATION;
	private float maxSpeed = Constants.MAX_SPEED;
	private float radiusOfSatisfaction = Constants.RADIUS_OF_SATISFACTION;
	private float radiusOfDeceleration = Constants.RADIUS_OF_DECELERATION;
	private float timeToTarget = 0.1f;
	
	public SteeringArrive(Character character, Target target) {
		this.character = character;
		this.target = target;
	}
	public SteeringArrive(Character character, PVector target) {
		this.character = character;
		this.target.setPosition(target);
	}

	public SteeringOutput getSteering() {
		SteeringOutput s = new SteeringOutput();	
	
		PVector direction = PVector.sub(target.getPosition(), character.getKinematic().getPosition());
		float distance = direction.mag();
		float targetSpeed;
		if(distance < radiusOfSatisfaction)
			targetSpeed = 0;
		if(distance > radiusOfDeceleration)
			targetSpeed = maxSpeed;
		else 
			targetSpeed = maxSpeed * distance/radiusOfDeceleration;
		PVector targetVel = direction;
		targetVel.normalize();
		targetVel.mult(targetSpeed);
		PVector accel = PVector.sub(targetVel, character.getKinematic().getVelocity());
		accel.div(timeToTarget);
		if(accel.mag() > maxAcceleration) {
			accel.normalize();
			accel.mult(maxAcceleration);
		}
		s.setAngluarAccel(0);
		s.setLinearAccel(accel);
//		if(distance < character.getSize()){
//			character.getKinematic().setOrientation(getNewOrientation(character.getKinematic().getOrientation(),character.getKinematic().getVelocity()));
//			character.getKinematic().setRotation(0);
//			s.resetLinearAccel();
//		}
//		if(character.getKinematic().getPosition().dist(target.getPosition()) < 1.0f){
//			s.resetLinearAccel();
//			character.getKinematic().setVelocity(new PVector(0,0));
//		}
		return s;
	}
	private float getNewOrientation(float orientation, PVector velocity) {
		if(velocity.mag() > 0) 
			return (float) Math.atan2(velocity.y, velocity.x);
		else 
			return orientation;
	}
}
