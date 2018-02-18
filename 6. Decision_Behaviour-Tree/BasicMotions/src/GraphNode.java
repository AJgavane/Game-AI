import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class GraphNode {
	
	private PApplet parent;
	private int nodeId;
	
	private PVector position = new PVector(0,0);
	private ArrayList<Connection> connections;
	
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
	
	public GraphNode(PApplet parent, int node, PVector position, ArrayList<Connection> connection) {
		super();
		this.parent = parent;
		this.nodeId = node;
		this.connections = connection;
		this.position = position;
	}

	public boolean hasNode(int nodeId) {
		return nodeId == this.nodeId;
	}
	
	public void addConnection(int toNode, double cost ){
		connections.add( new Connection(nodeId, toNode, cost) );
	}
	
	public void display(int count) {
		parent.pushMatrix();
		parent.translate(position.x, position.y);
		parent.fill(00,200,00);
		parent.rect(0, 0, 5, 5);
		parent.fill(0,0,0);
		parent.text(nodeId,30/3,30/3);
//		for(Connection c: this.connections){
//			int x = c.getToNode()/30;
//			int y = c.getToNode()%30;
//			parent.line(0, 0, x*30+15 - position.x, y*30+15 - position.y);
//		}
		parent.popMatrix();
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
	
	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	
}
