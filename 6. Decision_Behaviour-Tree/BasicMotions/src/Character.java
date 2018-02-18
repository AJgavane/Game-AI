import processing.core.PApplet;
import processing.core.PVector;

public class Character {
	private PApplet parent;
	// character variables
	private Kinematic kinematic = new Kinematic(parent);
	private Steering steering = new Steering(parent);
	
	// Character constants
	private int size = 20;
	private int maxSpeed = kinematic.defaultMaxSpeed;
	private int maxAcceleration = 1;
	
	float maxRotation = 0.4f;
	int numOfRotation = 1;
	
	// Arrive Variables
	float radiusOfSatisfaction = size/2;
	float radiusOfDecelaration = 0;
	float radiusOfSatisfaction_rot = 0.01f;
	float radiusOfDecelaration_rot = 0.3f;
	float timeToTargetVelocity = 1;
	float delta = 1f;
	float timeToTargetAccelaration = 0.3f;
	
	/* Constructor */
	public Character() {
		super();
	}
	
	public Character(PApplet parent) {
		super();
		this.parent = parent;		
	}
	
	public Character(PApplet parent, PVector position, int size) {
		super();
		this.parent = parent;
		this.size = size;
		this.kinematic.setPosition(position);
	}	
	
	/* MEthods */
	public void arrive(PVector target, Obstacles obstacles){
		//  do a collision detection
		ObstacleAvoidance oA = new ObstacleAvoidance(parent, this.size);
		PVector avoidance = oA.avoidObstacles( kinematic, obstacles.getObstacles());
		// delgate to arrive
		kinematic.setPosition(kinematic.getPosition().add(avoidance));
		
		PVector direction = PVector.sub(target, kinematic.getPosition());
		float distance = direction.mag();
		float goalSpeed;
		if(distance < radiusOfSatisfaction)
			goalSpeed = 0;
		else if (distance > radiusOfDecelaration)
			goalSpeed = maxSpeed;
		else 
			goalSpeed = maxSpeed * distance / radiusOfDecelaration;
		direction.normalize();
		kinematic.setVelocity(direction.mult(goalSpeed));
		
		// calculate the orientation 
		if(kinematic.getVelocity().mag() > 0) {
			float orientation = (float) (Math.atan2(direction.y, direction.x));
			float rotation = orientation - kinematic.getOrientation();
			rotation = mapToRange(rotation);
			float rotationDir = 1;
			
			if(Math.abs(rotation) > 0)
				rotationDir = rotation/Math.abs(rotation);
			
			float rotationSize = Math.abs(rotation);
			float goalRotation =0.3f;
			if(rotationSize < radiusOfSatisfaction_rot)
				goalRotation = 0;
			else if(rotationSize > radiusOfDecelaration_rot)
				goalRotation = maxRotation;
			else
				goalRotation = maxRotation * rotationSize / radiusOfDecelaration_rot;
			
			goalRotation *= rotationDir;
			kinematic.setRotation(goalRotation);
			float angularAccel = goalRotation - kinematic.getRotation();
			steering.setAngularAcc(angularAccel/timeToTargetAccelaration);
		}
		if(distance < size){
			kinematic.setOrientation(getNewOrientation(kinematic.getOrientation(),kinematic.getVelocity()));
			kinematic.setRotation(0);
		}
		if(kinematic.getVelocity().mag() == 0 && !kinematic.getPosition().equals(target)){
			kinematic.setVelocity(PVector.sub(target, kinematic.getPosition()));
		}
	}
	
	private float getNewOrientation(float orientation, PVector velocity) {
		if(velocity.mag() > 0) 
			return (float) Math.atan2(velocity.y, velocity.x);
		else 
			return orientation;
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

	public void display() {

		PVector position = this.kinematic.getPosition();
		PVector velocity = this.kinematic.getVelocity();
		float mag = velocity.mag();
		parent.pushMatrix();
		parent.translate(position.x, position.y);
//		parent.strokeWeight(3);
//		parent.line(0, 0,  mag*velocity.x, mag*velocity.y);
		parent.strokeWeight(1);
		parent.rotate(this.kinematic.getOrientation());
		parent.fill(240,240,240);
		parent.ellipse(0, 0, size, size);
		parent.fill(80,80,80);
		parent.triangle(0, -size/2, 0, size/2, size, 0);
		
		parent.popMatrix();
	}
	
	/* Getters and Setters*/

	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void setKinematic(Kinematic kinematic) {
		this.kinematic = kinematic;
	}

	public Steering getSteering() {
		return steering;
	}

	public void setSteering(Steering steering) {
		this.steering = steering;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getMaxAcceleration() {
		return maxAcceleration;
	}

	public void setMaxAcceleration(int maxAcceleration) {
		this.maxAcceleration = maxAcceleration;
	}
	
}
