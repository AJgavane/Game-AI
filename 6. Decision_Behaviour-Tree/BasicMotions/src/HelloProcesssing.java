import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class HelloProcesssing extends PApplet{

	public static void main(String[] args) {
		 PApplet.main("HelloProcesssing");
	}
	
	int gridSize = 30;
	int windowWidth = 900;
	int windowHeight = 900;
	PVector seekLocation = new PVector(120,120);
	ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	Obstacles obstacles = new Obstacles(this, obstacleList);
	Character character = new Character(this, 
			new PVector(seekLocation.x,seekLocation.y) ,gridSize/2);
	
	float currTime, pastTime = 0;
	Graph graph = new Graph(this, new ArrayList<GraphNode>());
	int numOfVertices = windowHeight/gridSize;
	ArrayList<Connection> path = null;
	ArrayList<Integer> orderedPathVertices = null;
	FollowPath fp = new FollowPath(this, 2, gridSize, numOfVertices);
	
	
	public void settings(){
        size(windowWidth,windowHeight);
    }
	
    public void setup(){
        noStroke();    
        createRoom();  
        createGraph();
        System.out.println(numOfVertices);
//        printGraph(graph);
    }

	

	public void draw(){ 	
    	
    	fill(220,220,220);
    	rect(0,0, width, height);
//		obstacles.showGrid(windowWidth, windowHeight, gridSize);
//		fill(100,100,100);
		obstacles.display(obstacles.getObstacles()); 
//		fill(0,255,0);
//    	graph.display(graph.getGraph());
   	
    	currTime = millis();
    	fill(10,100f);
    	if(path != null )
    		fp.followPath(character, orderedPathVertices, obstacles);
    	fill(255);
    	character.display();
    	character.getKinematic().update(character.getSteering(), (currTime-pastTime)/100);
    	pastTime = currTime;
    }
    
    public void mousePressed() {
    	seekLocation.x = mouseX;
    	seekLocation.y = mouseY;
    	System.out.println("seekLocation: " + quantize(seekLocation));
    	fp.setCurrentIndex(0);
    	path = PathFinder.pathFindAStar(graph, quantize(character.getKinematic().getPosition()), quantize(seekLocation), new Heuristic(quantize(seekLocation)));
    	printPath(path);
    	if(path != null){
    		orderedPathVertices = getOrderedList(path);
    		printList(orderedPathVertices);
    	}
    }
    
   

	private ArrayList<Integer> getOrderedList(ArrayList<Connection> path) {
		if(path.size() == 0)
			return null;
    	ArrayList<Integer> list = new ArrayList<>();
    	int node;
    	for(int i = 0; i < path.size(); i++) {
    		if(i == 0){
    			node = path.get(i).getFromNode();
    			list.add(node);
    		}
    		node = path.get(i).getToNode();
    		list.add(node);
    	}
		return list;
	}

	private int quantize(PVector position) {
    	int x = (int) (position.x/gridSize);
    	int y = (int) (position.y/gridSize);
		return x*numOfVertices+y;
	}

	private void createRoom() {
    	 obstacles.createWall(windowWidth, windowHeight, gridSize);
//       create obstacles
    	 obstacles.createObstacleAt(7*gridSize, 1*gridSize, 1, 7, gridSize);
    	 
    	 // room 1
    	 obstacles.createObstacleAt(17*gridSize, 1*gridSize, 1, 7, gridSize);
    	 obstacles.createObstacleAt(22*gridSize, 8*gridSize, 7, 1, gridSize);
    	 // room1 furniture
    	 obstacles.createObstacleAt(21*gridSize, 4*gridSize, 5, 1, gridSize);
    	 
    	 // Room 2 
    	 obstacles.createObstacleAt(1*gridSize, 18*gridSize, 10, 1, gridSize);
    	 obstacles.createObstacleAt(10*gridSize, 19*gridSize, 1, 3, gridSize);
    	 obstacles.createObstacleAt(10*gridSize, 26*gridSize, 1, 3, gridSize);
    	 // room 2 furniture
    	 obstacles.createObstacleAt(3*gridSize, 21*gridSize, 4, 1, gridSize);
    	 obstacles.createObstacleAt(3*gridSize, 26*gridSize, 4, 1, gridSize);
    	 obstacles.createObstacleAt(6*gridSize, 22*gridSize, 1, 4, gridSize);
    	 
    	 // Room 3
    	 obstacles.createObstacleAt(18*gridSize, 18*gridSize, 11, 1, gridSize);
    	 obstacles.createObstacleAt(18*gridSize, 19*gridSize, 1, 3, gridSize);
    	 obstacles.createObstacleAt(18*gridSize, 26*gridSize, 1, 3, gridSize);
    	 // room 3 furniture
    	 obstacles.createObstacleAt(24*gridSize, 21*gridSize, 1, 5, gridSize);
    	 obstacles.createObstacleAt(22*gridSize, 23*gridSize, 2, 1, gridSize);
    	 obstacles.createObstacleAt(25*gridSize, 23*gridSize, 2, 1, gridSize);
    	 
    	 
    	 // Adding furniture in living and dining room
    	 obstacles.createObstacleAt(11*gridSize, 12*gridSize, 5, 3, gridSize);
    	 obstacles.createObstacleAt(12*gridSize, 11*gridSize, 3, 1, gridSize);
    	 obstacles.createObstacleAt(13*gridSize, 10*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(12*gridSize, 15*gridSize, 3, 1, gridSize);
    	 obstacles.createObstacleAt(13*gridSize, 16*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(10*gridSize, 13*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(16*gridSize, 13*gridSize, 1, 1, gridSize);
    	 
    	 obstacles.createObstacleAt(13*gridSize, 23*gridSize, 3, 3, gridSize);
	}

    private void createGraph() {
    	int count = 0;
    	for(int x = 1 ; x < numOfVertices-1; x++) {
    		for(int y =1; y < numOfVertices-1; y++){
    			count = 0;
    			PVector position = new PVector(x*gridSize+gridSize/2, y*gridSize+gridSize/2);
    			if( !isObstacle(position, obstacles.getObstacles()) ){
	    			GraphNode gNode = new GraphNode(this, x*numOfVertices+y, position,  new ArrayList<Connection>());
	    			//check all its 8 neighbours and add a node if its not an obstacle
	    			count = 0;
	    			if( !isObstacle(new PVector((x-1)*gridSize+gridSize/2, y*gridSize+gridSize/2), obstacles.getObstacles())){
	    				gNode.addConnection((x-1)*numOfVertices+y,10);
	    				count++;
	    			}
	    			
	    			if( !isObstacle(new PVector((x+1)*gridSize+gridSize/2, y*gridSize+gridSize/2), obstacles.getObstacles())){
	    				gNode.addConnection( (x+1)*numOfVertices+y, 10);
	    				count++;
	    			}
	    			
	    			if( !isObstacle(new PVector((x)*gridSize+gridSize/2, (y+1)*gridSize+gridSize/2), obstacles.getObstacles())){
	    				gNode.addConnection( (x)*numOfVertices+y+1, 10);
	    				count++;
	    			}
	    			
	    			if( !isObstacle(new PVector((x)*gridSize+gridSize/2, (y-1)*gridSize+gridSize/2), obstacles.getObstacles())){
	    				gNode.addConnection((x)*numOfVertices+y-1, 10);
	    				count++;
	    			}
	    			if(count == 4) {
	    				if( !isObstacle(new PVector((x-1)*gridSize+gridSize/2, (y-1)*gridSize+gridSize/2), obstacles.getObstacles())){
		    				gNode.addConnection((x-1)*numOfVertices+y-1,15);
		    			}
		    			
		    			if( !isObstacle(new PVector((x+1)*gridSize+gridSize/2, (y-1)*gridSize+gridSize/2), obstacles.getObstacles())){
		    				gNode.addConnection( (x+1)*numOfVertices+y-1, 15);
		    			}
		    			
		    			if( !isObstacle(new PVector((x-1)*gridSize+gridSize/2, (y+1)*gridSize+gridSize/2), obstacles.getObstacles())){
		    				gNode.addConnection( (x-1)*numOfVertices+y+1, 15);
		    			}
		    			
		    			if( !isObstacle(new PVector((x+1)*gridSize+gridSize/2, (y+1)*gridSize+gridSize/2), obstacles.getObstacles())){
		    				gNode.addConnection((x+1)*numOfVertices+y+1, 15);
		    			}
	    			}
	    			graph.addGraphNode(gNode);    			
    			}
    		}
		}// end i loop
	}

	private boolean isObstacle(PVector position, ArrayList<Obstacle> obstacles) {
		for(Obstacle ob: obstacles){
			if(position.equals(ob.getPosition()))
				return true;
		}
		return false;
	}
	
	private static void printGraph(Graph graph) {
		System.out.println(graph.getGraph().size());
		for( GraphNode gn : graph.getGraph() ) {
			for( Connection c1 : gn.getConnection())
				System.out.println(c1.getFromNode() + " " +  c1.getToNode() + " " + c1.getCost());
		}
	}
	
	private void printList(ArrayList<Integer> list) {
		for(int i =0; i<list.size(); i++){
			System.out.println(list.get(i));
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
