package graph;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class GraphNode {
	
	private PApplet parent;
	public PVector center;
	public int i, j;
	public boolean isInGraph;
	public ArrayList<Connection> connections = new ArrayList<>();
	
	// algorithm data
	public float costSoFar, heuristic, estimatedTotalCost;
	public boolean visited;
	public GraphNode cameFrom;
	
	public GraphNode(PApplet parent) {
		this.parent = parent;
	}

	public void resetAlgorithmData() {
		costSoFar = estimatedTotalCost = Float.POSITIVE_INFINITY;
		heuristic = 0;
		cameFrom = null;
		visited = false;
	}
	
	public void addConnection(GraphNode toNode, float cost ){
		connections.add( new Connection(this, toNode, cost) );
	}
	
	public void display(int count) {
		parent.pushMatrix();
		parent.translate(center.x, center.y);
		parent.fill(00,200,00);
		parent.rect(0, 0, 5, 5);
		parent.fill(200,0,0);
//		System.out.println(connections.size());
		for(Connection c: connections){
			PVector p = c.toNode.center;
//			parent.line(0, 0, p.x - center.x, p.y - center.y);
		}
		parent.popMatrix();
	}

	@Override
	public String toString() {
		return "GraphNode [center=" + center + ", i=" + i + ", j=" + j + "]";
	}
	
}
