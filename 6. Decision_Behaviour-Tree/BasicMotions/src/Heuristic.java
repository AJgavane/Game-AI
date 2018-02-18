import processing.core.PVector;

public class Heuristic {
	private int goalNode;
	private PVector goalPosition;

	public Heuristic(int goalNode) {
		super();
		this.goalNode = goalNode;
	}
	
	public Heuristic(int goalNode, PVector goalPosition) {
		super();
		this.goalNode = goalNode;
		this.goalPosition = goalPosition;
	}

	public int estimate(int node){
		int gx = this.goalNode/30;
		int gy = this.goalNode % 30;
		int nx = node/30;
		int ny = node%30;
		return (int) Math.sqrt((gx-nx)*(gx-nx) + (gy-ny)*(gy-ny)); 
	}
}
