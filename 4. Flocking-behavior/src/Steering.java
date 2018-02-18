import processing.core.PApplet;
import processing.core.PVector;

public class Steering {
	// processing thingy
	private PApplet parent;
	// steering variables
	private PVector linearAcc = new PVector(0,0);
	private float angularAcc = 0;
	
	/** Constructors **/
	public Steering() {}
	
	public Steering (PApplet parent) {
		this.parent = parent;
	}
	
	public Steering (PVector linearAcc, float angularAcc) {
		this.linearAcc = linearAcc;
		this.angularAcc = angularAcc;
	}

	/****************Getter and Setter *******************/
	// 1. Linear Accelaration
	public PVector getLinearAcc() {
		return linearAcc;
	}
	public void setLinearAcc(PVector linearAcc) {
		this.linearAcc = linearAcc;
	}
	// 2. Angular accelaration
	public float getAngularAcc() {
		return angularAcc;
	}
	public void setAngularAcc(float angularAcc) {
		this.angularAcc = angularAcc;
	}
	/************** END of Getter and Setter *************/
}
