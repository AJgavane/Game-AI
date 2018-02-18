
public class Connection {
	private double cost;
	private int fromNode;
	private int toNode;
	
	public Connection(int fromNode, int toNode, double d) {
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

	public int getFromNode() {
		return fromNode;
	}

	public void setFromNode(int fromNode) {
		this.fromNode = fromNode;
	}

	public int getToNode() {
		return toNode;
	}

	public void setToNode(int toNode) {
		this.toNode = toNode;
	}

}
