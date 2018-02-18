package graph;
import java.util.ArrayList;

import processing.core.PVector;

public class CollisionDetector {
	public static float avoidanceForce = 1.414f;
	
	public CollisionDetector() {
		super();
	}
// Reference: https://gamedevelopment.tutsplus.com/tutorials/understanding-steering-behaviors-collision-avoidance--gamedev-7777
	public static Collision getCollision(PVector position, PVector rayAhead1, PVector rayAhead2, ArrayList<Obstacle> obstacles){
		Collision collision = new Collision();
		
		PVector coPosition = new PVector(0,0);
		float minDist = Integer.MAX_VALUE;
		float size = 0;
		
		float distance1, distance2;
		for(Obstacle ob: obstacles) {
			distance1 = PVector.dist(ob.getPosition(), rayAhead1);
			distance2 = PVector.dist(ob.getPosition(), rayAhead2);
			boolean collide = doesItCollide(position, rayAhead1, rayAhead2, ob);
			if(collide && (coPosition.mag() == 0 || PVector.dist(position, ob.getPosition()) < PVector.dist(position, coPosition))) {
				coPosition = ob.getPosition().copy();
				size = ob.getSizeOfObstacle();
				collision.setCollide(true);
			}
		}
		if(!collision.isCollide())
			return collision;
		collision.setPosition(coPosition);
		PVector norm = new PVector(0,0); 
		norm.x = rayAhead1.x - coPosition.x;
		norm.y = rayAhead1.y - coPosition.y;
		norm.normalize().mult(avoidanceForce);
		collision.setNormal(norm);	
		
		return collision;
	}

	private static PVector getNormal(PVector position, PVector coPosition, float size) {
		float minDist = Integer.MAX_VALUE;
		PVector sqSides[] = new PVector[4];
		sqSides[0] = new PVector(coPosition.x-size/2,coPosition.y);
		sqSides[1] = new PVector(coPosition.x+size/2,coPosition.y);
		sqSides[2] = new PVector(coPosition.x,coPosition.y+size/2);
		sqSides[3] = new PVector(coPosition.x,coPosition.y-size/2);
		PVector min = new PVector(0,0);
		float dist;
		for(int i = 0 ; i < 4 ; i++){
			dist = PVector.dist(sqSides[i], position);
			if( dist < minDist){
				minDist = dist;
				min = sqSides[i];
			}
		}
		PVector normal = new PVector(0,0);
		normal.add(min.sub(coPosition));
		return normal.normalize();
	}
	
	private static boolean doesItCollide(PVector position, PVector rayAhead1, PVector rayAhead2, Obstacle ob) {
		return (PVector.dist(ob.getPosition(), position) <= 1f*ob.getSizeOfObstacle()  ||
				PVector.dist(ob.getPosition(), rayAhead1) <= 1f*ob.getSizeOfObstacle() || 
				PVector.dist(ob.getPosition(), rayAhead2) <= 1f*ob.getSizeOfObstacle() 
				);
	}
	public static Collision getCollision(PVector position, ArrayList<Obstacle> obstacles) {
		Collision collision = new Collision();
		PVector coPosition = new PVector(0,0);
		int size = 20;
		for(Obstacle ob: obstacles) {
			float dist = PVector.dist(position, ob.getPosition());
			if(dist < ob.getSizeOfObstacle()){				
				coPosition = ob.getPosition();
				size = ob.getSizeOfObstacle();
				collision.setCollide(true);
			}
		}
		if(!collision.isCollide())
			return collision;
		collision.setPosition(coPosition);
		
		PVector norm = new PVector(0,0); 
		PVector diff = new PVector(0,0);
		diff.add(coPosition);
		diff.sub(position);
			norm.x =  position.x - coPosition.x;
			norm.y =  position.y - coPosition.y;
		norm.normalize();
		collision.setNormal(norm);	
		return collision;
	}

}
