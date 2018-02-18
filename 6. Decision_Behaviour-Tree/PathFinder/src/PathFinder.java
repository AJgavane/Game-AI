import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PathFinder {

	private static int OPEN = 1;
	public static void main(String[] args) {

		Random random = new Random();		
		int numberOfVertices;
		System.out.println("Creating Graph..");
		int start = 0, goal = 0;
				
	  /*********** Big Graphs ***************/
		numberOfVertices = 10000;
		Graph graph = new Graph(new ArrayList<GraphNode>());
		createGraph(graph, numberOfVertices, 0.01);
		while( start == goal) {
			start = random.nextInt(numberOfVertices);
			goal = random.nextInt(numberOfVertices);
		} 	
		// end of big graph	 
		
	/*********** Small Graphs Map of cities in Russian ***************/
	/*	numberOfVertices = 25;
		Graph graph = new Graph(new ArrayList<GraphNode>());
		createSmallGraph(graph, numberOfVertices);
		start = 903;
		goal = 8447;	
	// end of small graph
	*/	
		System.out.println("Graph Created");
//		printGraph(graph);		
				
		// if you are checking for arbitrary number of nodes use the run the following block of code.
		System.out.println(start + " -> " + goal);
		ArrayList<Connection> path = null;
		int numOfRuns = 1;
		System.out.println("A* Working with heuristic Node-Distance..");
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < numOfRuns ; i++)
			path = pathFindAStar(graph, start, goal, new Heuristic(goal),0);		
		long endTime   = System.currentTimeMillis();		
		long totalTime = endTime - startTime;	
		System.out.println("Time taken: " + totalTime/numOfRuns);		
		printPath(path);	
		
		/* The next two block of codes are for 2 other heuristics, Preferable
		 *  to run it only if there are 10k vertices.
		System.out.println("A* Working with heuristic Manhattan-Distance..");
		ArrayList<Connection> path1 = null;
		long startTime1 = System.currentTimeMillis();
		for(int i = 0; i < numOfRuns ; i++)
			path1 = pathFindAStar(graph, start, goal, new Heuristic(goal),1);		
		long endTime1   = System.currentTimeMillis();		
		long totalTime1 = endTime1 - startTime1;	
		System.out.println("Time taken: " + totalTime1/numOfRuns);		
		printPath(path1);
		
		System.out.println("A* Working with heuristic Euclidean-Distance..");
		ArrayList<Connection> path2 = null;
		long startTime2 = System.currentTimeMillis();
		for(int i = 0; i < numOfRuns ; i++)
			path2 = pathFindAStar(graph, start, goal, new Heuristic(goal),2);		
		long endTime2   = System.currentTimeMillis();		
		long totalTime2 = endTime2 - startTime2;	
		System.out.println("Time taken: " + totalTime2/numOfRuns);		
		printPath(path2);
		*/
		
		// Next block of code runs Dijkstra on the graph
		ArrayList<Connection> path3 = null;
		System.out.println("Dijkstra Working..");
		long startTime3 = System.currentTimeMillis();
		for(int i = 0; i < numOfRuns ; i++)
			path3 = pathFindDijkstra(graph, start, goal);
		long endTime3   = System.currentTimeMillis();
		totalTime = endTime3 - startTime3;
		System.out.println("Time taken: " + totalTime/numOfRuns);		
		printPath(path3);
		
	}

	

	private static ArrayList<Connection> pathFindDijkstra(Graph graph, int start, int goal) {
		NodeRecord startRecord = new NodeRecord();
		startRecord.setNode(start);
		startRecord.setConnection(null);
		startRecord.setCostSoFar(0);
		startRecord.setCategory(OPEN);
		ArrayList<NodeRecord> open = new ArrayList<NodeRecord>();
		ArrayList<NodeRecord> close = new ArrayList<NodeRecord>();
//		int openSize = 0;
//		int closeSize = 0;
		open.add(startRecord);
		NodeRecord current = null;
		double endNodeCost = 0;
		while(open.size() > 0){
//			if(open.size() > openSize){
//				openSize = open.size();
//			}
//			if(close.size() > closeSize) {
//				closeSize = close.size();
//			}
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
//					System.out.println(endNode + " in openList");
					endNodeRecord = findInList(open, endNode);
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
		
//		System.out.println("open: " + openSize + " close: " + closeSize);
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

	private static ArrayList<Connection> pathFindAStar(Graph graph, int start, int goal, Heuristic heuristic, int heuristicVal) {
		NodeRecord startRecord = new NodeRecord();
		startRecord.setNode(start);
		startRecord.setConnection(null);
		startRecord.setCostSoFar(0);
		startRecord.setEstimatedTotalCost(heuristic.estimate(start));
		startRecord.setCategory(OPEN);
		
		ArrayList<NodeRecord> open = new ArrayList<NodeRecord>();
		ArrayList<NodeRecord> close = new ArrayList<NodeRecord>();
		open.add(startRecord);
//		int openSize = 0;
//		int closeSize = 0;
		NodeRecord current = null;
		double endNodeCost = 0;
		while(open.size() > 0) {
//			if(open.size() > openSize){
//				openSize = open.size();
//			}
//			if(close.size() > closeSize) {
//				closeSize = close.size();
//			}
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
					// print
					// if we didn't get shorter route then skip
					if(endNodeRecord.getCostSoFar() <= endNodeCost) {
						continue;
					}
					endNodeHeuristic = endNodeRecord.getEstimatedTotalCost() - endNodeRecord.getCostSoFar();
				}
				// else we've got an unvisited node
				else {
					endNodeRecord = new NodeRecord();
					endNodeRecord.setNode(endNode);
					// here we need to calculate the heuristic
					if(heuristicVal == 0)
						endNodeHeuristic = heuristic.estimate(endNode);
					else if(heuristicVal == 1)
						endNodeHeuristic = heuristic.estimateManhattan(endNode);
					else
						endNodeHeuristic = heuristic.estimateEuclidean(endNode);
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
//		System.out.println("open: " + openSize + " close: " + closeSize);
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
					gNode.addConnection(toNode, random.nextInt(19) + 1);
				}
			} // end j loop
			graph.addGraphNode(gNode);
		}// end i loop
	}
	
	private static void createSmallGraph(Graph smallGraph, int numberOfVertices) {
		//node1
		GraphNode gNode1 = new GraphNode(903, new ArrayList<Connection>());
		gNode1.addConnection(3809, 545);
		gNode1.addConnection(1211, 151);
		smallGraph.addGraphNode(gNode1);
		
		GraphNode gNode2 = new GraphNode(3809, new ArrayList<Connection>());
		gNode2.addConnection(903, 545);
		gNode2.addConnection(3717, 174);
		smallGraph.addGraphNode(gNode2);
		
		GraphNode gNode3 = new GraphNode(1211, new ArrayList<Connection>());
		gNode3.addConnection(2220, 323);
		gNode3.addConnection(903, 151);
		smallGraph.addGraphNode(gNode3);
		
		GraphNode gNode4 = new GraphNode(2220, new ArrayList<Connection>());
		gNode4.addConnection(2926, 156);
		gNode4.addConnection(1211, 323);
		smallGraph.addGraphNode(gNode4);
		
		GraphNode gNode5 = new GraphNode(3717, new ArrayList<Connection>());
		gNode5.addConnection(3809, 174);
		gNode5.addConnection(2926, 246);
		gNode5.addConnection(4322, 108);
		smallGraph.addGraphNode(gNode5);
		
		GraphNode gNode6 = new GraphNode(628, new ArrayList<Connection>());
		gNode6.addConnection(2926, 363);
		smallGraph.addGraphNode(gNode6);
		
		GraphNode gNode7 = new GraphNode(4322, new ArrayList<Connection>());
		gNode7.addConnection(3717, 100);
		gNode7.addConnection(4229, 83);
		smallGraph.addGraphNode(gNode7);
		
		GraphNode gNode8 = new GraphNode(2926, new ArrayList<Connection>());
		gNode8.addConnection(2220, 156);
		gNode8.addConnection(628, 363);
		gNode8.addConnection(1732, 157);
		gNode8.addConnection(2632, 171);
		gNode8.addConnection(4229, 184);
		gNode8.addConnection(3717, 246);
		gNode8.addConnection(3850, 462);
		smallGraph.addGraphNode(gNode8);
		
		GraphNode gNode9 = new GraphNode(1732, new ArrayList<Connection>());
		gNode9.addConnection(2926, 157);
		gNode9.addConnection(840, 171);
		smallGraph.addGraphNode(gNode9);
		
		GraphNode gNode10 = new GraphNode(840, new ArrayList<Connection>());
		gNode10.addConnection(1732, 171);
		smallGraph.addGraphNode(gNode10);
		
		GraphNode gNode11 = new GraphNode(2632, new ArrayList<Connection>());
		gNode11.addConnection(2926, 171);
		gNode11.addConnection(2240, 174);
		smallGraph.addGraphNode(gNode11);
		
		GraphNode gNode12 = new GraphNode(2240, new ArrayList<Connection>());
		gNode12.addConnection(2632, 174);
		gNode12.addConnection(2052, 135);
		smallGraph.addGraphNode(gNode12);
		
		GraphNode gNode13 = new GraphNode(2052, new ArrayList<Connection>());
		gNode13.addConnection(2240, 135);
		gNode13.addConnection(3850, 212);
		smallGraph.addGraphNode(gNode13);
		
		GraphNode gNode14 = new GraphNode(3850, new ArrayList<Connection>());
		gNode14.addConnection(2052, 212);
		gNode14.addConnection(2926, 462);
		gNode14.addConnection(6052, 462);
		smallGraph.addGraphNode(gNode14);
		
		GraphNode gNode15 = new GraphNode(6052, new ArrayList<Connection>());
		gNode15.addConnection(3850, 462);
		gNode15.addConnection(5266, 335);
		gNode15.addConnection(6342, 208);
		smallGraph.addGraphNode(gNode15);
		
		GraphNode gNode16 = new GraphNode(5266, new ArrayList<Connection>());
		gNode16.addConnection(6052, 335);
		smallGraph.addGraphNode(gNode16);
		
		GraphNode gNode17 = new GraphNode(4229, new ArrayList<Connection>());
		gNode17.addConnection(2926, 184);
		gNode17.addConnection(4322, 83);
		gNode17.addConnection(4440, 192);
		gNode17.addConnection(5625, 224);
		smallGraph.addGraphNode(gNode17);
		
		GraphNode gNode18 = new GraphNode(4440, new ArrayList<Connection>());
		gNode18.addConnection(4229, 192);
		gNode18.addConnection(6342, 382);
		smallGraph.addGraphNode(gNode18);
		
		GraphNode gNode19 = new GraphNode(5625, new ArrayList<Connection>());
		gNode19.addConnection(4229, 224);
		gNode19.addConnection(5933, 217);
		gNode19.addConnection(7128, 209);
		smallGraph.addGraphNode(gNode19);
		
		GraphNode gNode20 = new GraphNode(5933, new ArrayList<Connection>());
		gNode20.addConnection(5625, 217);
		gNode20.addConnection(6342, 111);
		smallGraph.addGraphNode(gNode20);
		
		GraphNode gNode21 = new GraphNode(7128, new ArrayList<Connection>());
		gNode21.addConnection(5625, 209);
		gNode21.addConnection(8428, 116);
		smallGraph.addGraphNode(gNode21);
		
		GraphNode gNode22 = new GraphNode(8428, new ArrayList<Connection>());
		gNode22.addConnection(7128, 116);
		gNode22.addConnection(7538, 180);
		smallGraph.addGraphNode(gNode22);
		
		GraphNode gNode23 = new GraphNode(7538, new ArrayList<Connection>());
		gNode23.addConnection(8428, 180);
		gNode23.addConnection(6342, 251);
		gNode23.addConnection(8447, 157);
		smallGraph.addGraphNode(gNode23);
		
		GraphNode gNode24 = new GraphNode(8447, new ArrayList<Connection>());
		gNode24.addConnection(7538, 157);
		gNode24.addConnection(6342, 342);
		smallGraph.addGraphNode(gNode24);
		
		GraphNode gNode25 = new GraphNode(6342, new ArrayList<Connection>());
		gNode25.addConnection(4440, 382);
		gNode25.addConnection(5933, 111);
		gNode25.addConnection(7538, 251);
		gNode25.addConnection(8447, 342);
		gNode25.addConnection(6052, 208);
		smallGraph.addGraphNode(gNode25);
		
		
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
