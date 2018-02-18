import processing.core.PVector;

public class Collision {
	private PVector position = new PVector(0,0);
	private PVector normal= new PVector(0,0);
	private boolean collide = false;
	
	public Collision() {
		super();
		this.collide = false;
	}
	
	public boolean isCollide() {
		return collide;
	}

	public void setCollide(boolean collide) {
		this.collide = collide;
	}

	public PVector getPosition() {
		return position;
	}
	public void setPosition(PVector position) {
		this.position = position;
	}
	public PVector getNormal() {
		return normal;
	}
	public void setNormal(PVector normal) {
		this.normal = normal;
	}
	
}
