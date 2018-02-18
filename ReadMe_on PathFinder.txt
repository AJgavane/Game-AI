1. 	PathFinder folder include the code for DIjkstra and A* algorithm, ie. the problems 1,2 & 3. Run this codes normally in java.
	WHile running big graph, if you are trying any other heuristics (commented in PathFinder class) make sure the numberOfVertices = 10000;
	else, euclidean distance and Manhattan distance calculations may fail. For node-distance heuristic it will work fine for any value
	of numberOfVertices.
2.	If you want to change the final and goal for small graph, then please refer the graph picture in the report and enter the 
	exact node-Id, else the algorithm will give you an error stating null point error, since that vertex with any other node-Id 
	does not exists.
3. 	For game part, the last part, I've used Euclidean Heuristic. Since this gave me the best results and looks good. For obstacle avoidance
	part, you can choose one of the 2 types of collision prediction method. It is well commented in the code, and you'll find it in 
	OBstacleAvoidance class.