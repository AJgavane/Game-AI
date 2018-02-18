import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class FollowPath {
	
	private PApplet parent;
	private ArrayList<Connection> path = null;
	private int pathOffset = 2;
	private int gridSize = 30;
	private int dimension = 30;
	private int currentIndex = 0;
	
	public FollowPath(PApplet parent, int pathOffset, int gridSize, int dimension) {
		super();
		this.parent = parent;
		this.pathOffset = pathOffset;
		this.gridSize = gridSize;
		this.dimension = dimension;
		this.currentIndex = 0;
	}

	public void followPath(Character c, ArrayList<Integer> path, Obstacles obstacles){
		PVector futurePos = c.getKinematic().getPosition();
		if(PVector.dist(futurePos,getPositionOnPath(path.get(path.size()-1))) < 10){
			return;
		}
		
		PVector vel = new PVector(0,0);
		vel.add(c.getKinematic().getVelocity());
		futurePos.add(vel.mult(0.5f));
		
		currentIndex = getIndexOnPath(currentIndex, futurePos, path);
		
		int targetIndex ;
		if(path.size() - currentIndex <= pathOffset )
			targetIndex = currentIndex+1;
		else 
			targetIndex = currentIndex + pathOffset;
		
		
		if(path.size() == 0)
			return;
		if(targetIndex > path.size()-1){
			targetIndex = currentIndex + 1;
			if(targetIndex > path.size() - 1 || currentIndex > path.size()-1)
				targetIndex = path.size()-1;
		}
		if(currentIndex == 0){
			targetIndex = 1;
		}
		
		PVector target = getPositionOnPath(path.get(targetIndex));
		PVector temp = new PVector(0,0);
		temp.add(target);
		if( temp.sub(futurePos).mag() < 0.2f){
			targetIndex++;
			target = getPositionOnPath(path.get(targetIndex));
		}
		parent.fill(255,0,0);
		parent.ellipse(target.x, target.y, 4, 4);
		c.arrive(target, obstacles);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	private PVector getPositionOnPath(int targetIndex) {
		int x = targetIndex / dimension;
		int y = targetIndex % dimension;
		x = x*gridSize + gridSize/2;
		y = y*gridSize + gridSize/2;
		PVector target = new PVector(x,y);
		return target;
	}

	private int getIndexOnPath(int index, PVector position, ArrayList<Integer> path) {
		int count = 0;
		float distance;
		float minDist = Integer.MAX_VALUE;
		for(int i = index; i< path.size() && i < index + 2 ; i++){
			distance = getDistance(position, path.get(i));
			if(distance < minDist) {
				minDist = distance;
				count = i;
			}
		}
		return count;
	}

	private float getDistance(PVector p1, int nodeId) {
		int x = nodeId/dimension;
		int y = nodeId % dimension;
		PVector p2 = new PVector(x*gridSize, y*gridSize);
		return PVector.dist(p1, p2);
	}
}
