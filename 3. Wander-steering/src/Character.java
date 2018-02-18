import processing.core.PApplet;
import processing.core.PVector;

public class Character {
	// Processing thingy
	private PApplet parent;
	// Character variables
	private Kinematic kinematic = new Kinematic(parent);
	private Steering steering = new Steering(parent);
	// Character constants
	private int size = 30;
	private int maxSpeed = 30;
	float maxRotation = 0.4f;
	private int maxAccel = 0;
	int numOfRotation = 1;
	
	// Arrive variables
	float radiusOfSatisfaction = size/2;
	float radiusOfDecelaration = 80;
	float radiusOfSatisfaction_rot = 0.01f;
	float radiusOfDecelaration_rot = 0.3f;
	float timeToTargetVelocity=1 ;
	float delta = 1f;
	float timeToTargetAccel = 0.3f;
	
	float wanderRadius = 2;
	float projectedDistance = 25;
	float wanderRate = (float) Math.PI;
	/** Constructors**/
	public Character() {}
	
	public Character(PApplet parent) {
		this.parent = parent;
	}
	
	public Character(PApplet parent, PVector position, int size) {
		this.parent = parent;
		this.kinematic.setPosition(position);
		this.size = size;
	}
	
	
	/** Methods **/	
	public void steeringArrive(PVector target) {
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
		
		// calculate the orientation. 1)using align 2) getNewOrientatio 3) the one in seek.
		if(kinematic.getVelocity().mag() > 0) {
			float orientation = (float) (Math.atan2(direction.y, direction.x));
			float rotation = orientation - kinematic.getOrientation();
			rotation = mapToRange(rotation);
			float rotationDir = 1;
			if (Math.abs(rotation) > 0)
				rotationDir = rotation/Math.abs(rotation);
			float rotationSize = Math.abs(rotation);
			float goalRotation = 0.3f;
			if(rotationSize < radiusOfSatisfaction_rot) 				
				goalRotation = 0; 
			else if (rotationSize > radiusOfDecelaration_rot)
				goalRotation = maxRotation;
			else 
				goalRotation = maxRotation * rotationSize / radiusOfDecelaration;
			goalRotation *= rotationDir;
			kinematic.setRotation(goalRotation);
			float angularAccel = goalRotation - kinematic.getRotation();
			steering.setAngularAcc(angularAccel/timeToTargetAccel);
		} 
		if (distance < size) {
			kinematic.setOrientation(getNewOrientation(kinematic.getOrientation(), kinematic.getVelocity()));
			kinematic.setRotation(0);
		}
		
		PVector position = kinematic.getPosition();			
			// collision 
			if(position.x > parent.width)  //with left wall
				position.x = 0;
			if(position.x < 0)             // right wall
				position.x = parent.width;
			if(position.y > parent.height) 		// bottom wall
				position.y = 0;
			if(position.y < 0)				// with top wall
				position.y = parent.height;	
		kinematic.setPosition(position);		
	}	
	
	private float mapToRange(float rotation) {
		float rot = (float) (rotation %(2*Math.PI));
		if(Math.abs(rot) <= Math.PI)
			return rot;
		else {
			if (rot > Math.PI)
				return (float) (rot - 2*Math.PI);
			else
				return (float) (rot + 2*Math.PI);
		}
	}

	public void steeringWander() {
		float wanderOrien = (float) ((parent.random(-1,1) - parent.random(-1,1)) * Math.PI);
		float targetOrien = wanderOrien + kinematic.getOrientation();
		
		PVector targetPosition = new PVector((float)Math.cos(kinematic.getOrientation()), (float)Math.sin(kinematic.getOrientation()));
		targetPosition.mult(projectedDistance);
		targetPosition.add(kinematic.getPosition());
		
		PVector displacement = new PVector((float)Math.cos(targetOrien), (float)Math.sin(targetOrien));
		displacement.mult(wanderRadius);
		
		targetPosition.add(displacement);
		steeringArrive(targetPosition);	
			
	}	
	
	private float getNewOrientation(float currOrientation, PVector velocity) {
		if(velocity.mag() > 0) {
			return (float) Math.atan2(velocity.y, velocity.x);
		} else {
			return currOrientation;
		}
	}
	
	public void display() {
		PVector position = this.getKinematic().getPosition();
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.rotate((float) (kinematic.getOrientation()));
		parent.fill(255,255,255);
		parent.ellipse(0, 0, size, size);
		parent.fill(200,200,200);
		parent.triangle(0, -size/2, 0, size/2, size, 0);
		parent.popMatrix();
	}

	public void display(PVector position){
		parent.ellipse(position.x, position.y, size/2, size/2);
	}
	/****************Getter and Setter *******************/
	// 1. Kinematic
	public Kinematic getKinematic() {
		return kinematic;
	}
	public void setKinematic(Kinematic kinematic) {
		this.kinematic = kinematic;
	}
	// 2. Steering
	public Steering getSteering() {
		return steering;
	}
	public void setSteering(Steering steering) {
		this.steering = steering;
	}
	// 3. Size
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	// 4. Max Speed
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	// 5. Max Accelaration
	public int getMaxAccel() {
		return maxAccel;
	}
	public void setMaxAccel(int maxAccel) {
		this.maxAccel = maxAccel;
	}
	
	/************** END of Getter and Setter *************/
	
}
