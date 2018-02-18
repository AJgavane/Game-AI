import processing.core.PApplet;
import processing.core.PVector;

public class Kinematic {
	// processing thingy
	private PApplet parent;
	// kinematic variables
	private PVector position = new PVector(120, 120);
	private PVector velocity = new PVector(1,1);
	private float orientation = (float) (Math.PI/2);
	private float rotation = 0.1f;
	
	/** Constructors **/
	public Kinematic() {}
	
	public Kinematic(PApplet parent) {
		this.parent = parent;
	}
	
	public Kinematic( PVector position, PVector velocity, float orientation, float rotation) {
		this.position = position;
		this.velocity = velocity;
		this.orientation = orientation;
		this.rotation = rotation;
	}
	
	/** Methods**/
	public void update(Steering s, float time ) {
		// update position and orientation
		position.add(velocity.mult(time));
		orientation += rotation*time;
		orientation = (float) (orientation );
		
		// update velocity and roation	
		PVector linAcc = s.getLinearAcc();
		float angAcc = s.getAngularAcc();
		velocity.add(linAcc.mult(time));
		rotation += angAcc * time;		
	}

	/****************Getter and Setter *******************/
	// 1. position
	public PVector getPosition() {
		return position;
	}
	public void setPosition(PVector position) {
		this.position = position;
	}
	// 2. Velocity
	public PVector getVelocity() {
		return velocity;
	}
	public void setVelocity(PVector velocity) {
		this.velocity = velocity;
	}
	// 3. Orientation
	public float getOrientation() {
		return orientation;
	}
	public void setOrientation(float orientation) {
		this.orientation = orientation;
	}
	// 4. Rotation
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	/**************** END of Getter and Setter *******************/	
}
