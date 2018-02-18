
public class NodeRecord {
	
	private int node;
	private Connection connection;
	private double costSoFar;
	private double estimatedTotalCost;
	private int category;  // 0 for unvisited, 1 for open and 2 for closed
	
	// Constructors
	public NodeRecord() {
		super();
	}

	public NodeRecord(int node, Connection connection, float costSoFar, float estimatedTotalCost, int category) {
		super();
		this.node = node;
		this.connection = connection;
		this.costSoFar = costSoFar;
		this.estimatedTotalCost = estimatedTotalCost;
		this.category = category;
	}

	// Getter and setters
	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public double getCostSoFar() {
		return costSoFar;
	}

	public void setCostSoFar(double costSoFar) {
		this.costSoFar = costSoFar;
	}

	public double getEstimatedTotalCost() {
		return estimatedTotalCost;
	}

	public void setEstimatedTotalCost(double estimatedTotalCost) {
		this.estimatedTotalCost = estimatedTotalCost;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}	
}
