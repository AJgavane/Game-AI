import java.util.List;

import processing.core.PApplet;

public class Graph {
	private PApplet parent;
	private List<GraphNode> graph;

	public Graph(List<GraphNode> graph) {
		super();
		this.graph = graph;
	}
	
	public Graph(PApplet parent, List<GraphNode> graph) {
		super();
		this.graph = graph;
	}
	
	public Graph() {
		super();
	}
	
	public void addGraphNode(GraphNode gnode){
		this.graph.add(gnode);
	}

	public GraphNode getNodeById(int fromNode) {
		return graph.stream()
				.filter(graph -> graph.hasNode(fromNode))
				.findFirst().orElse(null);
	}
	
	public List<GraphNode> getGraph() {
		return graph;
	}

	public void setGraph(List<GraphNode> graph) {
		this.graph = graph;
	}

	public void display(List<GraphNode> graph) {
		int count = 0;
		for(GraphNode gn: graph){
			gn.display(count++);
		}
		
	}
	
	

}
