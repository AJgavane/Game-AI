
public class Heuristic {
	private int goalNode;

	// Constructor
	public Heuristic(int goalNode) {
		super();
		this.goalNode = goalNode;
	}
	
	// Methods
	public int estimate(int node){
//		System.out.println("Heuristic: " + node + "->" + this.goalNode + ": " + Math.abs(this.goalNode-node));
		return Math.abs(this.goalNode-node); 
	}
	
	public int estimateManhattan(int node){
		int x1 = node/100;
		int x2 = this.goalNode/100;
		int y1 = node %100;
		int y2 =  this.goalNode % 100;
		int result = Math.abs(x1-x2);
		result += Math.abs(y1-y2);
		return result;
	}
	
	public float estimateEuclidean(int node) {
		int x1 = node/100;
		int x2 = this.goalNode/100;
		int y1 = node %100;
		int y2 =  this.goalNode % 100;
		float result = 0;
		result = (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return result;
	}
}
