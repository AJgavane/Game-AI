package graph;
import processing.core.PApplet;
import processing.core.PVector;

public class Obstacle {
	// this class will hold the center of smallest element of the obstacle i.e. one grid
	private PApplet parent;
	private int sizeOfObstacle = 20;  // square grid size
	private PVector center = new PVector(0,0);	// location of the topleft corner of obstacle
	
	/************* Constructors ************/
	public Obstacle(PApplet parent) {
		super();
		this.parent = parent;
	}

	public Obstacle(PApplet parent, int sizeOfObstacle, PVector position) {
		super();
		this.parent = parent;
		this.sizeOfObstacle = sizeOfObstacle;
		this.center.x = position.x + sizeOfObstacle/2 ;
		this.center.y = position.y + sizeOfObstacle/2;
	}
	
	public Obstacle(int sizeOfObstacle, PVector position) {
		this.sizeOfObstacle = sizeOfObstacle;
		this.center.x = position.x + sizeOfObstacle/2 ;
		this.center.y = position.y + sizeOfObstacle/2;
	}

	public Obstacle() {
		super();
	}

	/************* Methods ************/
	public void display() {
		parent.pushMatrix();
		parent.translate(center.x-sizeOfObstacle/2, center.y-sizeOfObstacle/2);
		parent.rect(0, 0, sizeOfObstacle, sizeOfObstacle);
		parent.popMatrix();
	}
	public void display(int count) {
		parent.pushMatrix();
		parent.translate(center.x-sizeOfObstacle/2, center.y-sizeOfObstacle/2);
		parent.fill(120,120,120);
		parent.rect(0, 0, sizeOfObstacle, sizeOfObstacle);
//		parent.fill(0,0,0);
//		parent.text(count,sizeOfObstacle/3,sizeOfObstacle/3);
		parent.popMatrix();
	}
	
	/************* Getter and Setter************/
	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

	public int getSizeOfObstacle() {
		return sizeOfObstacle;
	}

	public void setSizeOfObstacle(int sizeOfObstacle) {
		this.sizeOfObstacle = sizeOfObstacle;
	}

	public PVector getPosition() {
		return center;
	}

	public void setPosition(PVector position) {
		this.center = position;
	}
	
	
}
