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
	private int maxSpeed = 40;
	float maxRotation = 0.3f;
	private int maxAccel = 0;
	int numOfRotation = 1;
	
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
	public void kinematicSeek(PVector target) {
		// calculate direction
		PVector velocity = PVector.sub(target, kinematic.getPosition());
		
		// set velocity to max speed in that direction
		velocity.normalize();
		velocity.mult(maxSpeed);
		kinematic.setVelocity(velocity);
		
		// calculate the orientation.
		if(kinematic.getVelocity().mag() > 0 ) {
			float orientation = (float) (Math.atan2(velocity.y, velocity.x));
			float goalRotation = orientation - kinematic.getOrientation();
			float direction =  goalRotation/Math.abs(goalRotation);
			if(goalRotation < maxRotation) {				
				kinematic.setRotation(goalRotation);
			} else {
				kinematic.setRotation(direction * maxRotation);
			}
		} 
		// Or Using get Orientation
		if(kinematic.getPosition().mag() < maxSpeed) {
			kinematic.setOrientation(getNewOrientation(kinematic.getOrientation(), kinematic.getVelocity()));
			kinematic.setRotation(0);
		}
	}
	
	public void traversePerimeter() {
		
		PVector position = kinematic.getPosition();	
		if(numOfRotation < 0 && position.x <= size && position.y >= parent.height - 1.5*size)
			return;
		// Seek bottom left - to - bottom right corner
		if(position.x <= parent.width-size && position.y >= parent.height - size){
			numOfRotation--;
//			PApplet.println(numOfRotation + " " + position.x + " " + position.y);
			kinematicSeek(new PVector(parent.width-size, parent.height-size));
        }
		// seek bottom right - to - top right corner
		else if(position.x >= parent.width-size && position.y <= parent.height-size){
        	kinematicSeek(new PVector(parent.width-size, size));
//        	PApplet.println(numOfRotation + " " + position.x + " " + position.y);
        }
        // seek top right - to - top left corner
		else if(position.x >= size && position.y <= size){
//			PApplet.println(numOfRotation + " " + position.x + " " + position.y);
        	kinematicSeek(new PVector(size, size));
        }
        // seek top left - to - bottom left corner
		else {// if (position.x <= size && position.y <= size){
        	kinematicSeek(new PVector(size, parent.height-size));
        }
	}
	
	
	private float getNewOrientation(float currOrientation, PVector velocity) {
		// TODO Auto-generated method stub
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
		parent.fill(255,255,255);
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
