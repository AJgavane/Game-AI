package movement;
import processing.core.PApplet;
import processing.core.PVector;

public class Kinematic {
	private PApplet parent;
	private PVector position ;
	private PVector velocity;
	private float orientation = 0;
	private float rotation = 0;
	
	/* Constructors */
	public Kinematic() {
		super();
		this.position = new PVector(30,30);
		this.velocity = new PVector(1,1);
		this.orientation = (float) (-Math.PI);
		this.rotation = 0.0f;
	}
	
	public Kinematic(PApplet parent) {
		super();
		this.parent = parent;
		this.position = new PVector(30,30);
		this.velocity = new PVector(0,0);
		this.orientation = (float) (-Math.PI/3);
		this.rotation = 0.0f;
	}

//	public Kinematic(PApplet parent, PVector position, PVector velocity, float orientation, float rotation) {
//		super();
//		this.parent = parent;
//		this.position = position;
//		this.velocity = velocity;
//		this.orientation = orientation;
//		this.rotation = rotation;
//	}
	
	/*Methods*/
	public void update(SteeringOutput s, float dt, float maxSpeed){
		PVector veldt = PVector.mult(velocity, dt);
		position.add(veldt);
		orientation += rotation*dt;
		
		position.x = position.x % parent.width;
		if(position.x < 0)
			position.x += parent.width;
		position.y = position.y % parent.height;
		if(position.y < 0)
			position.y += parent.height;
	
		PVector acceldt = PVector.mult(s.getLinearAccel(), dt);
		velocity.add(acceldt);
		orientation += s.getAngluarAccel()*dt;
		if(velocity.mag() > maxSpeed) {
//			System.out.println("MAx: " + velocity);
			velocity.normalize();
			velocity.mult(maxSpeed);
		}
	}
	
	/*Getter & Setter*/
	public PVector getPosition() {
		return position;
	}
	public void setPosition(PVector position) {
		this.position = position;
	}
	public PVector getVelocity() {
		return velocity;
	}
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}	
}
