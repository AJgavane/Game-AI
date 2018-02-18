package movement;
import processing.core.PVector;

public class SteeringOutput {
	@Override
	public String toString() {
		return "SteeringOutput [linearAccel=" + linearAccel + ", angluarAccel=" + angluarAccel + "]";
	}


	private PVector linearAccel;// = new PVector(0,0);
	private float angluarAccel; //= 0;
	
	
	public SteeringOutput() {
		super();
		this.linearAccel = new PVector(0,0);
		this.angluarAccel = 0;
	}

	public SteeringOutput(PVector linearAccel, float angluarAccel) {
		super();
		this.linearAccel = linearAccel;
		this.angluarAccel = angluarAccel;
	}
	
	public PVector getLinearAccel() {
		return linearAccel;
	}
	public void setLinearAccel(PVector linearAccel) {
		this.linearAccel = linearAccel;
	}
	public void resetLinearAccel() {
		this.linearAccel = new PVector(0,0);
	}
	public float getAngluarAccel() {
		return angluarAccel;
	}
	public void setAngluarAccel(float angluarAccel) {
		this.angluarAccel = angluarAccel;
	}
	

	public static SteeringOutput add(SteeringOutput s1, SteeringOutput s2) {
		// TODO Auto-generated method stub
		SteeringOutput result = new SteeringOutput();
		result.setLinearAccel(PVector.add(s1.getLinearAccel(), s2.getLinearAccel()));
		result.setAngluarAccel(s1.getAngluarAccel() + s2.getAngluarAccel());
		return result;
	}	
}
