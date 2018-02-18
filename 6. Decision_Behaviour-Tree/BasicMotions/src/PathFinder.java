import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PathFinder {

	private static int UNVISITED = 0;
	private static int OPEN = 1;
	private static int CLOSE = 2;
	public static void main(String[] args) {

		Random random = new Random();
		int numberOfVertices = 10000;
		System.out.println("Creating Graph..");
		Graph graph = new Graph(new ArrayList<GraphNode>());
		createGraph(graph, numberOfVertices, 0.3);
		System.out.println("Graph Created");
//		printGraph(graph);		
		int start = 0, goal = 0;
		while( start == goal) {
			start = random.nextInt(numberOfVertices);
			goal = random.nextInt(numberOfVertices);
		}
		System.out.println(start + " -> " + goal);
		ArrayList<Connection> path = null;
		System.out.println("A* Working..");
		long startTime = System.currentTimeMillis();
		
		path = pathFindAStar(graph, start, goal, new Heuristic(goal));
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time taken: " + totalTime);
		
		printPath(path);
		
		ArrayList<Connection> path1 = null;
		System.out.println("Dijkstra Working..");
		long startTime1 = System.currentTimeMillis();
		path1 = pathFindDijkstra(graph, start, goal);
		long endTime1   = System.currentTimeMillis();
		totalTime = endTime1 - startTime1;
		System.out.println("Time taken: " + totalTime);
		
		printPath(path1);
	}

	private static ArrayList<Connection> pathFindDijkstra(Graph graph, int start, int goal) {
		// TODO Auto-generated method stub
		NodeRecord startRecord = new NodeRecord();
		startRecord.setNode(start);
		startRecord.setConnection(null);
		startRecord.setCostSoFar(0);
		startRecord.setCategory(OPEN);
		
		ArrayList<NodeRecord> open = new ArrayList<NodeRecord>();
		ArrayList<NodeRecord> close = new ArrayList<NodeRecord>();
		open.add(startRecord);
		NodeRecord current = null;
		double endNodeCost = 0;
		while(open.size() > 0){
			current = getSmallestCSFElementFromList(open);
			if(current.getNode() == goal){
				break;
			}
			GraphNode node = graph.getNodeById(current.getNode());
			ArrayList<Connection> connections = node.getConnection();
			for( Connection connection :connections){
				// get the cost estimate for end node
				int endNode = connection.getToNode();
				endNodeCost = current.getCostSoFar() + connection.getCost();
				NodeRecord endNodeRecord = new NodeRecord();
				// if node is closed skip  it
				if(listContains(close, endNode))
					continue;
				// or if the node is in open list
				else if (listContains(open,endNode)){
					endNodeRecord = findInList(open, endNode);
					// print
					// if we didn't get shorter route then skip
					if(endNodeRecord.getCostSoFar() <= endNodeCost) {
						continue;
					}
				}
				// else node is not visited yet
				else {
					endNodeRecord = new NodeRecord();
					endNodeRecord.setNode(endNode);
				}
				//update the node
				endNodeRecord.setCostSoFar(endNodeCost);
				endNodeRecord.setConnection(connection);
				// add it to open list
				if(!listContains(open, endNode)){
					open.add(endNodeRecord);
				}
			}// end of for loop for connection
			open.remove(current);
			close.add(current);
		}// end of while loop for open list
		if(current.getNode() != goal)
			return null;
		else { //get the path
			ArrayList<Connection> path = new ArrayList<>();
			while(current.getNode() != start){
				path.add(current.getConnection());
				int newNode = current.getConnection().getFromNode();
				current = findInList(close, newNode);
			}
			Collections.reverse(path);
			return path;
		}
	}

	static ArrayList<Connection> pathFindAStar(Graph graph, int start, int goal, Heuristic heuristic) {
		NodeRecord startRecord = new NodeRecord();
		startRecord.setNode(start);
		startRecord.setConnection(null);
		startRecord.setCostSoFar(0);
		startRecord.setEstimatedTotalCost(heuristic.estimate(start));
		startRecord.setCategory(OPEN);
		
		ArrayList<NodeRecord> open = new ArrayList<NodeRecord>();
		ArrayList<NodeRecord> close = new ArrayList<NodeRecord>();
		open.add(startRecord);
		NodeRecord current = null;
		double endNodeCost = 0;
		while(open.size() > 0) {
			current = getSmallestETCElementFromList(open);
			if(current.getNode() == goal){
				break;
			}
			GraphNode node = graph.getNodeById(current.getNode());
			ArrayList<Connection> connections = node.getConnection();
			
			for( Connection connection :connections){
				// get the cost estimate for end node
				int endNode = connection.getToNode();
				endNodeCost = current.getCostSoFar() + connection.getCost();
				double endNodeHeuristic;
				NodeRecord endNodeRecord = new NodeRecord();
				// if node is closed we may have to skip or remove it from the closed list
				if(listContains(close, endNode)){
					endNodeRecord = findInList(close, endNode);
					// print
					// if we didn't get shorter route then skip
					if(endNodeRecord.getCostSoFar() <= endNodeCost) {
						continue;
					}
					close.remove(endNodeRecord);
					endNodeHeuristic = endNodeRecord.getEstimatedTotalCost() - endNodeRecord.getCostSoFar();
				}
				// if node is in open list and we've not found a better route
				else if(listContains(open,endNode)){
					endNodeRecord = findInList(open, endNode);
					// if we didn't get shorter route then skip
					if(endNodeRecord.getCostSoFar() <= endNodeCost) {
						continue;
					}
					endNodeHeuristic = endNodeRecord.getEstimatedTotalCost() - endNodeRecord.getCostSoFar();
				}
				// else we've got an unvisited node
				else {
//					System.out.println(endNode + " in unvisited");
					endNodeRecord = new NodeRecord();
					endNodeRecord.setNode(endNode);
					// here we need to calculate the heuristic
					endNodeHeuristic = heuristic.estimate(endNode);
				}
				
				// Update the node
				endNodeRecord.setCostSoFar(endNodeCost);
				endNodeRecord.setEstimatedTotalCost(endNodeCost + endNodeHeuristic);
				endNodeRecord.setConnection(connection);
				// add it to open list
				if(!listContains(open, endNode)){
					open.add(endNodeRecord);
				}
			} // end connection loop
			open.remove(current);
			close.add(current);
		} // end while
		if(current.getNode() != goal)
			return null;
		else { //get the path
			ArrayList<Connection> path = new ArrayList<>();
			while(current.getNode() != start){
				path.add(current.getConnection());
				int newNode = current.getConnection().getFromNode();
				current = findInList(close, newNode);
			}
			Collections.reverse(path);
			return path;
		}
		
	}

	private static NodeRecord findInList(ArrayList<NodeRecord> list, int nodeId) {
		// TODO Auto-generated method stub
		for(NodeRecord node : list){
			if (node.getNode() == nodeId)
				return node;
		}
		return null;
	}

	private static boolean listContains(ArrayList<NodeRecord> list, int nodeId) {
		for(NodeRecord node : list){
			if (node.getNode() == nodeId)
				return true;
		}
		return false;
	}

	private static NodeRecord getSmallestCSFElementFromList(ArrayList<NodeRecord> open) {
		NodeRecord result = new NodeRecord();
		double csfMin = Integer.MAX_VALUE;
		for( NodeRecord node: open){
			if(node.getCostSoFar() < csfMin){
				result = node;
				csfMin = node.getCostSoFar();
			}
		}
		return result;
	}

	private static NodeRecord getSmallestETCElementFromList(ArrayList<NodeRecord> open) {
		NodeRecord result = new NodeRecord();
		double etcMin = Integer.MAX_VALUE;
		for( NodeRecord node: open){
			if(node.getEstimatedTotalCost() < etcMin){
				result = node;
				etcMin = node.getEstimatedTotalCost();
			}
		}
		return result;
	}

	private static void createGraph(Graph graph, int numOfVertices, double probabilityOfMakingEdge) {
		
		Random random = new Random();
		for(int fromNode = 0 ; fromNode < numOfVertices; fromNode++) {
			GraphNode gNode = new GraphNode(fromNode, new ArrayList<Connection>());
			for(int toNode = 0; toNode < numOfVertices; toNode++){
				if(toNode == fromNode)
					continue;
				if(Math.random() < probabilityOfMakingEdge) { // we will make an edge from node i -> j 
//					Connection c = new Connection(fromNode, toNode, 2);
					gNode.addConnection(toNode, random.nextInt(19) + 1);
				}
			} // end j loop
			graph.addGraphNode(gNode);
		}// end i loop
	}
	
	private static void printGraph(Graph graph) {
		System.out.println(graph.getGraph().size());
		for( GraphNode gn : graph.getGraph() ) {
			for( Connection c1 : gn.getConnection())
				System.out.println(c1.getFromNode() + " " +  c1.getToNode() + " " + c1.getCost());
		}
	}

	private static void printNodeList(ArrayList<NodeRecord> open) {
		System.out.println(open.size());
		for( NodeRecord node: open) {
			System.out.println("NodeId: " + node.getNode()  + " csf: " + node.getCostSoFar() + " etc:" + node.getEstimatedTotalCost() + " cat:" + node.getCategory());
		}
	}

	private static void printPath(ArrayList<Connection> path) {
		System.out.println("***************THE PATH IS********");
		if(path != null) {
			for(Connection c: path){
				System.out.println( c.getFromNode() + " " + c.getToNode()+ " " + c.getCost() );
			}
		} else {
			System.out.println("noPathExists");
		}
		System.out.println("***************THE PATH IS********");
	}

}
