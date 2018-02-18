import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class ObstacleAvoidance {
	
	private PApplet parent;
	CollisionDetector collisionDetector = new CollisionDetector();
	private float size = 20.0f;
	private float avoidDistance = 1f*size;
	private float lookAhead = 2.1f*size;
	
	public ObstacleAvoidance() {
		super();
	}
	
	public ObstacleAvoidance(PApplet parent, float size) {
		super();
		this.parent = parent;
		this.size = size;
		this.lookAhead = 3*size;
		this.avoidDistance = 1.4f*size;
		
	}
	
	public PVector avoidObstacles( Kinematic k, ArrayList<Obstacle> obstacles) {
		// Calculate ray vectorx
		PVector avoidance = new PVector(0,0);
		
		PVector rayAhead1 = k.getVelocity();
		rayAhead1.normalize();
		rayAhead1.mult(k.getVelocity().mag()*lookAhead);
		rayAhead1.add(k.getPosition());
		parent.fill(255,0,0);
		parent.line(k.getPosition().x, k.getPosition().y, rayAhead1.x, rayAhead1.y);
		
		PVector rayAhead2 = k.getVelocity();
		rayAhead2.normalize();
		rayAhead2.mult(lookAhead/2);
		rayAhead2.add(k.getPosition());
		
		// below are the two methods for obstacle avoidance
//		Collision collision = CollisionDetector.getCollision(k.getPosition(), rayAhead1, rayAhead2, obstacles);
		Collision collision = CollisionDetector.getCollision(k.getPosition(), obstacles);
		if(collision.isCollide()){
			parent.fill(0,0,255);
			parent.ellipse(collision.getPosition().x, collision.getPosition().y, 3, 4);
			avoidance.add(collision.getNormal().mult(1.414f));
			parent.line(collision.getPosition().x, collision.getPosition().y, collision.getPosition().x+avoidance.x*10, collision.getPosition().y+avoidance.y*10);
		}
		return avoidance;
	}
	
	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}
}
