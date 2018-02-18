package movement;

import processing.core.PVector;

public class KinematicWander {
	private Character character;
	float maxSpeed = Constants.MAX_SPEED;
	float maxRotation = 0.5f;
	
	
	public KinematicWander(Character character) {
		super();
		this.character = character;
	}

	public KinematicSteeringOutput getSteering() {
		KinematicSteeringOutput s = new KinematicSteeringOutput();
		PVector v = angleAsVector(character.getKinematic().getOrientation());
		v.mult(maxSpeed);
		float theta = (float) ((Math.random() - Math.random())*maxRotation);
		s.setRotation(theta);
		s.setVelocity(v);
		return s;
	}

	private PVector angleAsVector(float theta) {
		// TODO Auto-generated method stub
		PVector result = new PVector(0,0);
		result.x = (float) Math.sin(theta);
		result.y = (float) Math.cos(theta);
		return result;
	}
}
