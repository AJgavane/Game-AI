package graph;

public class Connection {
	public float cost;
	public GraphNode fromNode;
	public GraphNode toNode;
	
	public Connection(GraphNode fromNode, GraphNode toNode, float d) {
		super();
		this.cost = d;
		this.fromNode = fromNode;
		this.toNode = toNode;
	}

	public Connection() {
		super();
	}

	public double getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}
}
