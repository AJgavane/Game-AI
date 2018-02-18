package movement;

import java.util.ArrayList;

import graph.Obstacle;
import graph.Obstacles;
import processing.core.PVector;

public class SteeringWander {
	
	private Character character;
	private float wanderOffset = Constants.WANDER_OFFSET;
	private float wanderRadius = Constants.WANDER_RADIUS;
	private float wanderRate = Constants.WANDER_RATE;
	private float maxAcceleration = Constants.MAX_ACCELERATION;
	float wanderOrientation = 0;
	
	public SteeringWander(Character character) {
		this.character = character;
	}

	public SteeringOutput getSteering() {
//		SteeringOutput s = new SteeringOutput();
		wanderOrientation = (float) (randomBinomial() * wanderRate);
		
		float targetOrien = wanderOrientation + character.getKinematic().getOrientation();
		
		PVector targetPosition = new PVector((float)Math.cos(character.getKinematic().getOrientation()), 
				(float)Math.sin(character.getKinematic().getOrientation()));
		targetPosition.mult(wanderOffset);
		targetPosition.add(character.getKinematic().getPosition());
		
		PVector displacement = angleAsVector(targetOrien);
		displacement.mult(wanderRadius);
		
		targetPosition.add(displacement);
		Target target = new Target();
		target.setPosition(targetPosition);
//		System.out.println(targetPosition);
		SteeringArrive arrive = new SteeringArrive(character, target);
		SteeringOutput s1 = arrive.getSteering();	
		SteeringAlign align = new SteeringAlign(character, target);
		SteeringOutput s2 = align.getSteering();
		SteeringOutput s = SteeringOutput.add(s1, s2);
		return s;
	}
	
	public SteeringOutput getLocation(ArrayList<Obstacle> obstacles) {
		wanderOrientation = (float) (randomBinomial() * wanderRate);
		
		float targetOrien = wanderOrientation + character.getKinematic().getOrientation();
		
		PVector targetPosition = new PVector((float)Math.cos(character.getKinematic().getOrientation()), 
				(float)Math.sin(character.getKinematic().getOrientation()));
		targetPosition.mult(wanderOffset);
		targetPosition.add(character.getKinematic().getPosition());
		
		PVector displacement = angleAsVector(targetOrien);
		displacement.mult(wanderRadius);
		
		targetPosition.add(displacement);
		Target target = new Target();
		target.setPosition(targetPosition);
		SteeringAlign align = new SteeringAlign(character, target);
		SteeringOutput s2 = align.getSteering();
		
		if(isInObstacle(targetPosition,obstacles)){	
//			System.out.println("targetInOBstacle " + targetPosition);
			return null;
		}
		SteeringArrive arrive = new SteeringArrive(character, target);
		SteeringOutput s1 = arrive.getSteering();	
//		SteeringAlign align = new SteeringAlign(character, target);
//		SteeringOutput s2 = align.getSteering();
		SteeringOutput s = SteeringOutput.add(s1, s2);
		return s;
	}
	
	public PVector getRandomLocation(ArrayList<Obstacle> obstacles) {
		wanderOrientation = (float) (randomBinomial() * wanderRate);
		
		float targetOrien = wanderOrientation + character.getKinematic().getOrientation();
		
		PVector targetPosition = new PVector((float)Math.cos(character.getKinematic().getOrientation()), 
				(float)Math.sin(character.getKinematic().getOrientation()));
		targetPosition.mult(wanderOffset);
		targetPosition.add(character.getKinematic().getPosition());
		
		PVector displacement = angleAsVector(targetOrien);
		displacement.mult(wanderRadius);
		
		targetPosition.add(displacement);
		Target target = new Target();
		target.setPosition(targetPosition);
		SteeringAlign align = new SteeringAlign(character, target);
		SteeringOutput s2 = align.getSteering();
		
		if(!isInObstacle(targetPosition,obstacles)){			
			return targetPosition;
		} else {
			return targetPosition;
		}
	}

	private boolean isInObstacle(PVector targetPosition, ArrayList<Obstacle> obstacles) {
		// TODO Auto-generated method stub
		float distance = Float.POSITIVE_INFINITY;
		for(Obstacle ob: obstacles) {
			if(targetPosition.dist(ob.getPosition()) < distance){
				distance = targetPosition.dist(ob.getPosition());
			}
		}
		if(distance < Constants.GRID_SIZE)
			return true;
		return false;
	}

	private float randomBinomial() {
		float result = (float) (Math.random() - Math.random());
		return result;
	}
	
	private PVector angleAsVector(float theta) {
		// TODO Auto-generated method stub
		PVector result = new PVector(0,0);
		result.x = (float) Math.cos(theta);
		result.y = (float) Math.sin(theta);
		return result;
	}

	

	
}
