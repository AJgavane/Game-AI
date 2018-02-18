import java.util.ArrayList;

public class GraphNode {
	private int nodeId;
	private ArrayList<Connection> connections;
	
	// Constructors
	public GraphNode(int node, ArrayList<Connection> connection) {
		super();
		this.nodeId = node;
		this.connections = connection;
	}
	
	public GraphNode(int node) {
		super();
		this.nodeId = node;
	}

	public GraphNode() {
		super();
	}
	
	//Methods
	public boolean hasNode(int nodeId) {
		return nodeId == this.nodeId;
	}
	
	public void addConnection(int toNode, double cost ){
		connections.add( new Connection(nodeId, toNode, cost) );
	}

	/* GETTER and SETTER*/
	public int getNode() {
		return nodeId;
	}

	public void setNode(int nodeId) {
		this.nodeId = nodeId;
	}

	public ArrayList<Connection> getConnection() {
		return connections;
	}

	public void setConnection(ArrayList<Connection> connection) {
		this.connections = connection;
	}	
}
