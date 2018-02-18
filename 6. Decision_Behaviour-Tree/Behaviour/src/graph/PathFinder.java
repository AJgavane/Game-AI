package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.BiFunction;

import processing.core.PVector;

public class PathFinder
{
	public static AStarComparator comp = new AStarComparator();
	public static ArrayList<PVector> runAStar(GraphNode src, GraphNode dest, BiFunction<GraphNode, GraphNode, Float> heuristic)
	{
		src.costSoFar = 0;
		src.cameFrom = null;
		src.heuristic = heuristic.apply(src, dest);
		src.estimatedTotalCost = src.heuristic;
		
		ArrayList<GraphNode> open = new ArrayList<>();
		ArrayList<GraphNode> close = new ArrayList<>();
		
		open.add(src);
		GraphNode current = null;
		float endNodeCost = 0;
		
		while(open.size() > 0) {

			current = getSmallestCSFElementFromList(open);
			if(current == dest){
				break;
			}
		
			ArrayList<Connection> connections = current.connections;
			
			for( Connection connection :connections){
				// get the cost estimate for end node
				GraphNode endNode = connection.toNode;
				endNodeCost = current.costSoFar + connection.cost;
				float endNodeHeuristic;
				// if node is closed we may have to skip or remove it from the closed list
				if(close.contains(endNode)){
					// print
					// if we didn't get shorter route then skip
					if(endNode.costSoFar <= endNodeCost) {
						continue;
					}
					close.remove(endNode);
					endNodeHeuristic = endNode.estimatedTotalCost - endNode.costSoFar;
				}
				// if node is in open list and we've not found a better route
				else if(open.contains(endNode)){
					// if we didn't get shorter route then skip
					if(endNode.costSoFar <= endNodeCost) {
						continue;
					}
					endNodeHeuristic = endNode.estimatedTotalCost - endNode.costSoFar;
				}
				// else we've got an unvisited node
				else {
					endNodeHeuristic = heuristic.apply(endNode, dest);
				}
				
				// Update the node
				endNode.costSoFar = endNodeCost;
				endNode.estimatedTotalCost = endNodeCost + endNodeHeuristic;
				endNode.cameFrom = current;
				// add it to open list
				if(!open.contains(endNode)){
					open.add(endNode);
				}
			} // end connection loop
			open.remove(current);
			close.add(current);
		} // end while
		if(current != dest){
			System.out.println("Not a destination!");
			return null;
		}
		else {
			ArrayList<PVector> path = new ArrayList<>();
			while (current != null) {
				path.add(new PVector(current.center.x, current.center.y));
				current = current.cameFrom;
			}
			Collections.reverse(path);
			return path;
		}		
	}
	
	private static GraphNode getSmallestCSFElementFromList(ArrayList<GraphNode> open) {
		GraphNode result = null;
		double csfMin = Integer.MAX_VALUE;
		for( GraphNode node: open){
			if(node.costSoFar < csfMin){
				result = node;
				csfMin = node.costSoFar;
			}
		}
		return result;
	}
	
	static class AStarComparator implements Comparator<GraphNode>
	{
		@Override
		public int compare(GraphNode node1, GraphNode node2) {
			return new Float(node1.estimatedTotalCost).compareTo(node2.estimatedTotalCost);
		}
	}
}