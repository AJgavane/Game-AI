package graph;

import movement.*;
import movement.Character;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class FollowPath {
	
	private PApplet parent;
	private ArrayList<Connection> path = null;
	private int pathOffset = 2;
	private int gridSize = Constants.GRID_SIZE;
	private int dimension = 20;
	private int currentIndex = 0;
	private float predictTime = 0.2f;
	
	public FollowPath(PApplet parent, int pathOffset, int gridSize, int dimension) {
		super();
		this.parent = parent;
		this.pathOffset = pathOffset;
		this.gridSize = gridSize;
		this.dimension = dimension;
		this.currentIndex = 0;
	}

	public SteeringOutput followPath(Character c, ArrayList<PVector> path, Obstacles obstacles){
//		ObstacleAvoidance oA = new ObstacleAvoidance(parent, c.getSize());
//		SteeringOutput s = oA.getSteering(c, obstacles.getObstacles());
		SteeringOutput s = new SteeringOutput();
		if(path == null || path.size() < 1)
			return s;
//		PVector futurePos = PVector.add(c.getKinematic().getPosition(),
//							c.getKinematic().getVelocity().mult(predictTime));
		currentIndex = getClosestIndex(currentIndex, c.getKinematic().getPosition(), path);
		//		PVector positionOnPath = path.get(currentIndex);
		if(getDistance(path.get(currentIndex), c.getKinematic().getPosition()) < c.getSize())
			currentIndex += 2;
		if(currentIndex >= path.size()){
			currentIndex = path.size() - 1;
		}		
		Target target= new Target(path.get(currentIndex));
//		System.out.println(target.getPosition());
		parent.fill(255,0,0);
		parent.ellipse(target.getPosition().x, target.getPosition().y, 4, 4);
		SteeringArrive arrive = new SteeringArrive(c, target);
		SteeringOutput s1 = arrive.getSteering();
//		SteeringSeek seek = new SteeringSeek(c, target);
//		SteeringOutput s1 = seek.getSteering();
		SteeringAlign align = new SteeringAlign(c,target);
		SteeringOutput s2 = align.getSteering();
		 s = SteeringOutput.add(s1, s2);
		return s;
	}

	
	private int getClosestIndex(int idx, PVector position, ArrayList<PVector> path) {
		if(path == null)
			return 0;
		int index = 0;
		int count = 0;
		float len = Float.POSITIVE_INFINITY;
		for(int i = idx; i < path.size(); i++){
			if(count++ == 3)
				break;
			float distance = getDistance(position, path.get(i));
			if(distance < len) {
				len = distance;
				index = i;
			}
		}
//		System.out.println("index " + index);
		return index;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	private float getDistance(PVector p1, PVector p2) {
		return PVector.dist(p1, p2);
	}
}
