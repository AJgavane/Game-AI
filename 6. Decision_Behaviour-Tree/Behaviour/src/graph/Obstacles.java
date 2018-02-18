package graph;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

public class Obstacles {
	
	private PApplet parent;
	private	ArrayList<Obstacle> obstacles;
	
	/************* Constructors ************/
	public Obstacles(PApplet parent, ArrayList<Obstacle> obstacles) {
		super();
		this.parent = parent;
		this.obstacles = obstacles;
	}

	public Obstacles() {
		super();
	}

	//input dimension of the screen
	public void createWall(int x, int y, int gridSize){
		for (int i = 0; i < x; i+=gridSize) {
			for (int j = 0; j < y ; j+=gridSize) {
				    // vertical wall                  // horizontal wall
				if((i==0 || i == x - gridSize) || (i!= 0 && i != x-gridSize && (j == 0 || j == y-gridSize))){
					Obstacle ob = new Obstacle(parent, gridSize, new PVector(i,j));
//					ob.display();
					this.obstacles.add(ob);
				}
			}
		}		
	}
	
	public void showGrid(int x, int y, int gridSize){
		int count = 0;
		for (int i = 0; i < x; i+=gridSize) {
			for (int j = 0; j < y; j+=gridSize) {
				Obstacle ob = new Obstacle(parent, gridSize, new PVector(i,j));
				ob.display(count++);
			}
		}		
	}
	
	// i/p: location and size of the obstacle(x,y)
	public void createObstacleAt(int x, int y, int gridX, int gridY, int gridSize) {
		int xmax = x + gridX * gridSize;
		int ymax = y + gridY * gridSize;
		
		for (int i = x; i < xmax; i+=gridSize) {
			for (int j = y; j < ymax; j+=gridSize) {
				Obstacle ob = new Obstacle(parent, gridSize, new PVector(i,j));
//				ob.display();
				this.obstacles.add(ob);
			}
		}	
	}
	
	public void display(ArrayList<Obstacle> obstacles) {
		int count = 0;
		for( Obstacle obstacle: obstacles) {
			obstacle.display(count++);
		}
	}

	/************* Getter and Setter************/
	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}
}
