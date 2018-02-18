package graph;
import java.util.ArrayList;

import movement.*;
import movement.Character;
import processing.core.PApplet;
import processing.core.PVector;

public class ObstacleAvoidance {
	
	private PApplet parent;
	CollisionDetector collisionDetector = new CollisionDetector();
	private float size = Constants.CHARACTER_SIZE;
	private float avoidDistance = 1.1f*size;
	private float lookAhead = 2f*size;
	
	public ObstacleAvoidance() {
		super();
	}
	
	public ObstacleAvoidance(PApplet parent, float size) {
		super();
		this.parent = parent;
		this.size = size;
		this.lookAhead = 2*size;
		this.avoidDistance = 1.41f*size;
		
	}
	public SteeringOutput getSteering(Character c, ArrayList<Obstacle> obstacles) {
		SteeringOutput s = new SteeringOutput();
		PVector avoidance = new PVector();
		float dynamicLength = c.getKinematic().getVelocity().mag() / Constants.MAX_SPEED;
		PVector rayVector1 = new PVector(0,0);
		rayVector1.add(c.getKinematic().getVelocity());
		rayVector1.normalize();
		rayVector1.mult(dynamicLength);
		rayVector1.add(c.getKinematic().getPosition());
		parent.fill(255,0,0);
		parent.line(c.getKinematic().getPosition().x, c.getKinematic().getPosition().y, 
				rayVector1.x, rayVector1.y);
		
		PVector rayVector2 = new PVector(0,0);
		rayVector2.add(c.getKinematic().getVelocity());
		rayVector2.normalize();
		rayVector2.mult(lookAhead/2);
		rayVector2.add(c.getKinematic().getPosition());
		parent.fill(0,255,0);
		parent.line(c.getKinematic().getPosition().x, c.getKinematic().getPosition().y, 
				rayVector2.x, rayVector2.y);
		
		Collision collision = CollisionDetector.getCollision(c.getKinematic().getPosition(), 
				rayVector1, rayVector2, obstacles);
		
		if(!collision.isCollide())
			return s;
		
		parent.fill(0,0,255);
		parent.ellipse(collision.getPosition().x, collision.getPosition().y, 3, 4);
		avoidance.add(collision.getNormal().mult(1.414f));
		parent.line(collision.getPosition().x, collision.getPosition().y, collision.getPosition().x+avoidance.x*10, collision.getPosition().y+avoidance.y*10);
		
		
		Target target = new Target();
		target.setPosition(collision.getPosition());
		target.setPosition(target.getPosition().add(collision.getNormal().mult(avoidDistance)));
		SteeringArrive arrive = new SteeringArrive(c, target);
		s = arrive.getSteering();
		return s;
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
			parent.line(collision.getPosition().x, collision.getPosition().y, collision.getPosition().x+avoidance.x*1, collision.getPosition().y+avoidance.y*1);
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
