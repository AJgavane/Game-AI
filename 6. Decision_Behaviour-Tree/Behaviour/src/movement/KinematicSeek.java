package movement;

import processing.core.PVector;

public class KinematicSeek {
	private Character character;
	private Target target = new Target();
	
	float maxSpeed = 10.0f;
	
	public KinematicSeek(Character character, Target target) {
		super();
		this.character = character;
		this.target = target;
	}

	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput s = new KinematicSteeringOutput();
		PVector v = PVector.sub(target.getPosition(), character.getKinematic().getPosition());
		v.normalize();
		v.mult(maxSpeed);
		s.setVelocity(v);
		float angle = getNewOrientation(character.getKinematic().getOrientation(), s.getVelocity());
		character.getKinematic().setOrientation(angle);
//		System.out.println(v.toString());
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
