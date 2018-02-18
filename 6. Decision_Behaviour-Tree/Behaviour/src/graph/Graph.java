package graph;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PVector;

public class Graph {
	private PApplet parent;
	private GraphNode[][] graph;

	public Graph(PApplet parent) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
	}

	public GraphNode getNodeById(int i, int j) {
		if (boundsCheck(i, j)) {
			return graph[i][j];
		}
		return null;
	}
	
	public GraphNode[][] getGraph() {
		return graph;
	}
	
	public void display(GraphNode[][] graph) {
		int count = 0;
		for (GraphNode[] gNodes : graph) {
			for (GraphNode gNode : gNodes) {
				gNode.display(count++);
			}
		}		
	}
	
	public boolean boundsCheck(int i, int j) {
		return 0 <= i && i < graph.length && 0 <= j && j < graph[0].length;
	}
	
	public void resetGraph() {
		for (GraphNode[] gNodes : graph) {
			for (GraphNode gNode : gNodes) {
				gNode.resetAlgorithmData();
			}
		}
	}

	public void createGraph(ArrayList<Obstacle> obstacleList, int size, int gridSize) {
		// TODO Auto-generated method stub
		graph = new GraphNode[size][size];
		int halfGridSize = gridSize/2;
		for(int i = 0; i < size ; i++){
			for(int j = 0; j < size ; j++) {
				GraphNode g = new GraphNode(parent);
				g.i = i;
				g.j = j;
				g.center = new PVector(i*gridSize + halfGridSize, j*gridSize+halfGridSize);
				graph[i][j] = g;
				g.isInGraph = true;
			}
		} // end i
		for(int i = 0; i<size; i++) {
			for (int j = 0; j < size ; j++){
				GraphNode g = graph[i][j];
				for(Obstacle ob: obstacleList) {
					if(ob.getPosition().equals(g.center)){
						g.isInGraph = false;
						break;
					}
				}
			}
		} // endi
		createConnections();
	}

	private void createConnections() {
		for(int i = 0; i < graph.length; i++) {
			for(int j = 0; j < graph[i].length; j++) {
//				if(graph[i][j].isInGraph == false)
//					continue;
				boolean connectedAll = true;
				connectedAll &= tryAddNeighbour(graph[i][j], i - 1, j, 1);
				connectedAll &= tryAddNeighbour(graph[i][j], i, j + 1, 1);
				connectedAll &= tryAddNeighbour(graph[i][j], i + 1, j, 1);
				connectedAll &= tryAddNeighbour(graph[i][j], i, j - 1, 1);
				if(connectedAll) {
					tryAddNeighbour(graph[i][j], i + 1, j + 1, 1.4f);
					tryAddNeighbour(graph[i][j], i - 1, j + 1, 1.4f);
					tryAddNeighbour(graph[i][j], i - 1, j - 1, 1.4f);
					tryAddNeighbour(graph[i][j], i + 1, j - 1, 1.4f);
				}
			}
		}
	}

	private boolean tryAddNeighbour(GraphNode graphNode, int i, int j, float cost) {
		if(boundsCheck(i,j) && graph[i][j].isInGraph){
			graphNode.addConnection(graph[i][j], cost);
			return true;
		}
		return false;
	}
	
	public GraphNode getNodeFromCoords(PVector coord) {
		int i = (int) (coord.x /40);
		int j = (int) (coord.y /40);

		return graph[i][j];
	}
	
	public void print() {
		// TODO Auto-generated method stub
		for(int i = 0; i<graph.length; i++) {
			for (int j = 0; j < graph[i].length ; j++){
				GraphNode g = graph[i][j];
				for(Connection c: g.connections) {
//					System.out.println(c.toNode.center + " - " + c.fromNode.center);
				}
			}
		} // endi
	}

}
