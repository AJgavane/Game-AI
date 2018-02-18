import processing.core.PApplet;
import processing.core.PVector;

public class Kinematic {
	private PApplet parent;
	// kinematic variables
	private PVector position = new PVector(320,320);
	private PVector velocity = new PVector(1,1);
	private float orientation = (float) (Math.PI/2);
	private float rotation = 0.1f;	
	public int defaultMaxSpeed = 8;
	
	/************* Constructors ************/
	public Kinematic() {
		super();
	}

	public Kinematic(PApplet parent) {
		super();
		this.parent = parent;
		this.position = new PVector(30,30);
		this.velocity = new PVector(2,2);
		this.orientation = (float) (Math.PI/3);
		this.rotation = 0.0f;
	}

	public Kinematic(PApplet parent, PVector position, PVector velocity, float orientation, float rotation) {
		super();
		this.parent = parent;
		this.position = position;
		this.velocity = velocity;
		this.orientation = orientation;
		this.rotation = rotation;
	}
	
	/************* Methods ************/
	public void update(Steering s, float time) {
		// update position and orientation
		position.add(velocity.mult(time));
		orientation += rotation*time;
		
		// update velocity and rotation
		PVector linearAccel = s.getLinearAcc();
		float angularAccel = s.getAngularAcc();
		velocity.add(linearAccel.mult(time));
		rotation += angularAccel*time;
	}

	/************* Getter and Setter************/
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
