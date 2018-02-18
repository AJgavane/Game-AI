import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import decision.BehaviourTree;
import decision.DecisionMaking;
import decision.DecisionTree;
import graph.Coins;
import graph.Connection;
import graph.FollowPath;
import graph.Graph;
import graph.GraphNode;
import graph.Heuristic;
import graph.Obstacle;
import graph.ObstacleAvoidance;
import graph.Obstacles;
import graph.PathFinder;
import movement.*;
import movement.Character;
import processing.core.PApplet;
import processing.core.PVector;

public class Homework3 extends PApplet{

	public static void main(String[] args) {
		 PApplet.main("Homework3");
	}
		
	int gridSize = Constants.GRID_SIZE;
	int windowWidth = 800;
	int windowHeight = 800;

	PVector seekLocation = Constants.DEFAULT_POSITION;
	PVector enemyLocation = Constants.MONSTER_POSITION;
	
	Character character = new Character(this, new PVector(Constants.DEFAULT_POSITION.x, Constants.DEFAULT_POSITION.y) ,gridSize/2);
	Character monster = new Character(this, new PVector(enemyLocation.x,enemyLocation.y) ,gridSize/2);
	float currTime, pastTime = 0;
	
	ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	Obstacles obstacles = new Obstacles(this, obstacleList);
	Graph graph = new Graph(this);
	int numOfVertices = windowHeight/gridSize;
	ArrayList<Connection> path = null;
	ArrayList<PVector> orderedPathVertices = new ArrayList<>();
	FollowPath fp = new FollowPath(this, 2, gridSize, numOfVertices);
	FollowPath fpMonster = new FollowPath(this, 2, gridSize, numOfVertices);
	ArrayList<PVector> monsterPath = new ArrayList<>();
	ArrayList<Coins> coinList = new ArrayList<Coins>();
	int numberOfCoinsAcquired = 0;
	PrintWriter pw;
	
	// states
	boolean stateArrive = false;
	boolean stateWander = true;
	
	public Homework3()
	{
		try {
			pw = new PrintWriter("temp.txt");
			pw.println("GOTO_P1\t" + "GOTO_P2\t" + "GOTO_P3\t" + "GOTO_P4\t" +
					"SCRIPTED_PATH\t" +  "APPROACH_AND_CATCH\t" + "APPROACH_INVERT\t" + "CATCHING\t" +
					"PLAYER_NOT_IN_RANGE\t" + "ACTION");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void settings(){
        size(windowWidth,windowHeight);
    }
	
    public void setup(){
        noStroke();    
        createRoom(); 
        graph.createGraph(obstacleList, windowWidth/gridSize, gridSize);
        createCoins();
        initializeMonsterStates();
        printCOinDistance(Constants.DEFAULT_POSITION);
    }

	public void draw(){ 
		if( getNumberOfCoinsLeft() == 0){
    		System.out.println("ResetCoins");
    		resetCoins();
    		character.reset();
    	}
    	
    	fill(140,140,140);
    	rect(0,0, width, height);
    	obstacles.display(obstacles.getObstacles());
//    	graph.display(graph.getGraph());
    	
    	currTime = millis();
    	moveCharacter((currTime - pastTime)/100);
    	character.display();
    	fill(200,0,0);
    	moveMonster((currTime - pastTime)/100);
    	monster.displayE();
    	fill(10,100f);
    	fill(255);
    	pastTime = currTime;
    	displayCoins(coinList);
    	
    }

	BehaviourTree bTree = null;
	DecisionTree dTree = null;
    private void moveMonster( float dt) {
    	
    	Kinematic k = monster.getKinematic();
		SteeringOutput s = new SteeringOutput();
		bTree = new BehaviourTree(monster, character, obstacleList);
		PVector seek = bTree.getSeekLocation();
//		dTree = new DecisionTree(monster, character, obstacleList);
//		PVector seek = dTree.getSeekLocation();
		if(seek != null){
			monsterPath = runAStar(monster.getKinematic().getPosition(), seek);
	    	fpMonster.setCurrentIndex(0);
    	}
		if(monsterPath != null){
			s = fpMonster.followPath(monster, monsterPath, obstacles);
			fpMonster.setCurrentIndex(0);
		}
		extractData();
		if(Constants.BEHAVE_MONSTER){
			delay(100);
			System.out.println("REintialize");
			System.out.println();
			character.getKinematic().setPosition(new PVector(Constants.DEFAULT_POSITION.x, Constants.DEFAULT_POSITION.y));
			System.out.println(character.getKinematic().getPosition());
			System.out.println(Constants.DEFAULT_POSITION);
    		resetCoins(); 	
			initializeMonsterStates();			
		}
		k.update(s, dt, Constants.MAX_SPEED_MONSTER);
	}

	private void moveCharacter( float dt) {
		Kinematic k = character.getKinematic();
		SteeringOutput s = new SteeringOutput();
		SteeringWander wander = new SteeringWander(character);
		// gets the closest coin
		DecisionMaking dTree = new DecisionMaking(character, coinList);
		seekLocation = dTree.getSeekLocation();
//		System.out.println(seekLocation);
		orderedPathVertices = null;
		if(seekLocation != null){
			orderedPathVertices = runAStar(character.getKinematic().getPosition(), seekLocation);
	    	fp.setCurrentIndex(0);
    	} else 
    		s = wander.getLocation(obstacleList);
		if( s == null){
			seekLocation = Constants.DEFAULT_POSITION2;
		}
		if(seekLocation != null){
			orderedPathVertices = runAStar(character.getKinematic().getPosition(), seekLocation);
	    	fp.setCurrentIndex(0);
    	}
		if(orderedPathVertices != null)
			s = fp.followPath(character, orderedPathVertices, obstacles);
    	k.update(s, dt, Constants.MAX_SPEED);
	}


	public void mousePressed() {
    	seekLocation.x = mouseX;
    	seekLocation.y = mouseY;
//    	System.out.println("seekLocation: " + quantize(seekLocation));
    	orderedPathVertices = runAStar(character.getKinematic().getPosition(), seekLocation);
    	fp.setCurrentIndex(0);
    	printPath(orderedPathVertices);
    	pw.close();
	}
	
	private void extractData() {
		
		pw.println(Constants.GOTO_P1 + "\t" + Constants.GOTO_P2 + "\t" + Constants.GOTO_P3 + "\t" + Constants.GOTO_P4 + "\t" + 
				 Constants.SCRIPTED_PATH + "\t" + Constants.APRROACH_AND_CATCH + "\t" + Constants.APPROACH_INVERTER + "\t" + 
				 Constants.CATCHING + "\t" +
				 playerNotInRange() + "\t" + getAction());
	}
	
	private String getAction() {
		String action = "";
		if(!Constants.BEHAVE_MONSTER) {
			if(!Constants.SCRIPTED_PATH){
				if(Constants.GOTO_P1){
					action = "GOTO_P1";
				} 
				if (Constants.GOTO_P1 && Constants.GOTO_P2) {
					action = "GOTO_P2";
				}
				if (Constants.GOTO_P1 && Constants.GOTO_P2 && Constants.GOTO_P3) {
					action = "GOTO_P3";
				}
				if (Constants.GOTO_P1 && Constants.GOTO_P2 && Constants.GOTO_P3 && Constants.GOTO_P4) {
					action = "GOTO_P4";
				}
			} else {
				action = "SCRIPTED_PATH";
				if(!Constants.APRROACH_AND_CATCH){
					if(Constants.APPROACH_INVERTER){
						action = "CATCHING";
					} 
					else {
						action = "APPROACH";
					}
				}
			}	
		}
		if(Constants.BEHAVE_MONSTER){
			action = "BEHAVE_MONSTER";
		}
		return action;
	}
	
	private boolean playerNotInRange() {
		// TODO Auto-generated method stub
		if( monster.getKinematic().getPosition().dist(character.getKinematic().getPosition()) > 300)
			return true;
		return false;
	}
	
	private void printPath(ArrayList<PVector> path) {
		// TODO Auto-generated method stub
		System.out.println("Path is************");
		for(PVector v: path){
			System.out.println(v);
		}
	}

	private ArrayList<PVector> runAStar(PVector src, PVector dest) {
		// TODO Auto-generated method stub
		graph.resetGraph();
		GraphNode source = graph.getNodeFromCoords(src);
		GraphNode destination = graph.getNodeFromCoords(dest);
		ArrayList<PVector> path = PathFinder.runAStar(source, destination, Homework3::cartesianHeuristic);
//		System.out.println(Arrays.toString(path.toArray(new Vector3[]{})));
		return path;
	}
	public static float cartesianHeuristic(GraphNode src, GraphNode dest)
	{
		float h = (src.i - dest.i) * (src.i - dest.i) + (src.j - dest.j) * (src.j - dest.j);
		return h;
	}

	private void createCoins() {
		addCoinAt(5*gridSize, 7*gridSize);
		addCoinAt(3*gridSize, 15*gridSize);
		addCoinAt(17*gridSize, 16*gridSize);
		addCoinAt(14*gridSize, 4*gridSize);
	}

	private void addCoinAt(int x, int y) {
		Coins c = new Coins(this, new PVector(x-gridSize/2, y-gridSize/2));
		coinList.add(c);
	}
	
	public void displayCoins(ArrayList<Coins> coinList) {
		int count = 0;
		for( Coins c: coinList) {
			if(c.isAcquired() == false)
				c.display(count++);
		}
	}

	private int getNumberOfCoinsLeft() {
		int count = 0;
		for( Coins c: coinList) {
			if(c.isAcquired() == false)
				count++;
		}
		return count;
	}


	private void createRoom() {
		// TODO Auto-generated method stub
//		System.out.println(windowWidth/gridSize);
		 obstacles.createWall(windowWidth, windowHeight, gridSize);
//       create obstacles
    	 
    	 
    	 // room 1
		 obstacles.createObstacleAt(6*gridSize, 1*gridSize, 1, 7, gridSize);
    	 obstacles.createObstacleAt(6*gridSize, 8*gridSize, 6, 1, gridSize);
//    	 obstacles.createObstacleAt(11*gridSize, 8*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(15*gridSize, 8*gridSize, 4, 1, gridSize);
    	 // room1 furniture
    	 obstacles.createObstacleAt(9*gridSize, 3*gridSize, 1, 2, gridSize);
    	 obstacles.createObstacleAt(9*gridSize, 5*gridSize, 8, 1, gridSize);
    	 obstacles.createObstacleAt(16*gridSize, 3*gridSize, 1, 2, gridSize);
    	 
    	 // Room 2 
    	 obstacles.createObstacleAt(1*gridSize, 12*gridSize, 6, 1, gridSize);
    	 obstacles.createObstacleAt(6*gridSize, 13*gridSize, 1, 2, gridSize);
    	 obstacles.createObstacleAt(6*gridSize, 17*gridSize, 1, 2, gridSize);
    	 // room 2 furniture
    	 obstacles.createObstacleAt(3*gridSize, 21*gridSize, 4, 1, gridSize);
    	 obstacles.createObstacleAt(3*gridSize, 26*gridSize, 4, 1, gridSize);
    	 obstacles.createObstacleAt(6*gridSize, 22*gridSize, 1, 4, gridSize);
    	 
//    	  Adding furniture in living and dining room
    	 obstacles.createObstacleAt(11*gridSize, 12*gridSize, 5, 3, gridSize);
    	 obstacles.createObstacleAt(12*gridSize, 11*gridSize, 3, 1, gridSize);
    	 obstacles.createObstacleAt(13*gridSize, 10*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(12*gridSize, 15*gridSize, 3, 1, gridSize);
    	 obstacles.createObstacleAt(13*gridSize, 16*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(10*gridSize, 13*gridSize, 1, 1, gridSize);
    	 obstacles.createObstacleAt(16*gridSize, 13*gridSize, 1, 1, gridSize);
//    	 System.out.println(obstacles.getObstacles().size());
	}
	   
	private boolean isObstacle(PVector position, ArrayList<Obstacle> obstacles) {
		for(Obstacle ob: obstacles){
			if(position.equals(ob.getPosition()))
				return true;
		}
		return false;
	}
		
		
	private void resetCoins() {
		for( Coins c: coinList) {
			c.setAcquired(false);
		}
		System.out.println(character.getKinematic().getPosition());
		character.reset();
	}
	
	private void initializeMonsterStates() {
		Constants.SCRIPTED_PATH = false;
		Constants.GOTO_P1 = true;
		Constants.GOTO_P2 = false;
		Constants.GOTO_P3 = false;
		Constants.GOTO_P4 = false;
		
		Constants.APRROACH_AND_CATCH = false;
		Constants.APPROACH_INVERTER = false;
		Constants.CATCHING = false;
		Constants.MAX_SPEED_MONSTER = 7.0f;
		Constants.BEHAVE_MONSTER = false;
	}

	private void printCOinDistance(PVector p) {
		for( Coins c: coinList) {
			if(c.isAcquired() == false)
				System.out.println(c.getPosition().dist(p));
		}
	}	
}
