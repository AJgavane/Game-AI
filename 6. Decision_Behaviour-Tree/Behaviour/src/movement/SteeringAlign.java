package movement;

import processing.core.PVector;

public class SteeringAlign {
	private Character character;
	private Target target;
	private float maxAngularAcceleration = Constants.MAX_ANGULAR_ACCELERATION;
	private float maxRotation = Constants.MAX_ROTATION;
	private float radiansOfSatisfaction = Constants.RADIANS_OF_SATISFACTION;
	private float radiansOfDeceleration = Constants.RADIANS_OF_DECELERATION;
	private float timeToTarget = 0.1f;
	
	public SteeringAlign(Character character, Target target) {
		this.character = character;
		this.target = target;
	}

	public SteeringOutput getSteering() {
		SteeringOutput s = new SteeringOutput();
		PVector direction = PVector.sub(target.getPosition(), character.getKinematic().getPosition());
		
		float orientation = (float) (Math.atan2(direction.y, direction.x));
		float rotation = orientation - character.getKinematic().getOrientation();
		rotation = mapToRange(rotation);
		float rotationDir = 1;
		float rotationSize = Math.abs(rotation);
		if(rotationSize > 0)
			rotationDir = rotation/Math.abs(rotation);
		
		float goalRotation = 0;
		if(rotationSize < radiansOfSatisfaction)
			goalRotation = 0;
		else if(rotationSize > radiansOfDeceleration)
			goalRotation = maxRotation;
		else
			goalRotation = maxRotation * rotationSize / radiansOfDeceleration;
		
		goalRotation *= rotationDir;
		float angularAccel = goalRotation - character.getKinematic().getRotation();
		angularAccel /= timeToTarget;
		s.setAngluarAccel(angularAccel);
		angularAccel = Math.abs(angularAccel);
		if(angularAccel > maxAngularAcceleration){
			s.setAngluarAccel(s.getAngluarAccel()/angularAccel);
			s.setAngluarAccel(s.getAngluarAccel()*maxAngularAcceleration);
		}
		return s;
	}
	
	private float mapToRange(float rotation) {
		float rot = (float) (rotation % (2*Math.PI));
		if(Math.abs(rot) <= Math.PI)
			return rot;
		else {
			if( rot > Math.PI)
				return (float) (rot - 2*Math.PI);
			else 
				return (float) (rot + 2*Math.PI);
		}
	}

}
