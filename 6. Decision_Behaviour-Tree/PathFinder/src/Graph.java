import java.util.List;

public class Graph {
	
	private List<GraphNode> graph;

	// Constructors
	public Graph(List<GraphNode> graph) {
		super();
		this.graph = graph;
	}
	
	public Graph() {
		super();
	}
	
	//Methods
	public void addGraphNode(GraphNode gnode){
		this.graph.add(gnode);
	}

	public GraphNode getNodeById(int fromNode) {
		return graph.stream()
				.filter(graph -> graph.hasNode(fromNode))
				.findFirst().orElse(null);
	}

	// Getters and setters
	public List<GraphNode> getGraph() {
		return graph;
	}

	public void setGraph(List<GraphNode> graph) {
		this.graph = graph;
	}

}
