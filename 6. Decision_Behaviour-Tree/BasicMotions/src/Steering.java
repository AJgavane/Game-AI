import processing.core.PApplet;
import processing.core.PVector;

public class Steering {
	private PApplet parent;
	
	// steering varaibles
	private PVector linearAcc = new PVector(1,1);
	private float angularAcc = 0.0f;
	
	/************* Constructor ************/
	public Steering() {
		super();
	}
	
	public Steering(PApplet parent) {
		super();
		this.parent = parent;
	}	

	public Steering(PApplet parent, PVector linearAcc, float angularAcc) {
		super();
		this.parent = parent;
		this.linearAcc = linearAcc;
		this.angularAcc = angularAcc;
	}
	
	/************* Getter and Setter************/
	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

	public PVector getLinearAcc() {
		return linearAcc;
	}

	public void setLinearAcc(PVector linearAcc) {
		this.linearAcc = linearAcc;
	}

	public float getAngularAcc() {
		return angularAcc;
	}

	public void setAngularAcc(float angularAcc) {
		this.angularAcc = angularAcc;
	}
	
	
}
