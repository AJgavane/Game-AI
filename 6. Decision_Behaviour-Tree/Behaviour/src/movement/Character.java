package movement;

import processing.core.PApplet;
import processing.core.PVector;

public class Character {
	private PApplet parent;
	private Kinematic kinematic;
	private int size = 20;
	public int numOfRotation = 1;
	
	/*Constructors*/
	public Character(PApplet parent, PVector position, int size) {
		super();
		this.parent = parent;
		this.size = size;
		this.kinematic = new Kinematic(this.parent);
		this.kinematic.setPosition(position);
	}	
		
	public Character(PApplet parent, Kinematic kinematic) {
		super();
		this.parent = parent;
		this.kinematic = kinematic;
	}
	
	public Character(Kinematic kinematic) {
		super();
		this.kinematic = kinematic;
	}
	
	public Character() {
		super();
	}	
	
	/*Methods*/
	public SteeringOutput traversePath(float dt) {
		PVector position = kinematic.getPosition();
		SteeringOutput s = new SteeringOutput();
		if(numOfRotation < 0 && position.x < 400 && position.y > 700)
			return s;
//		System.out.println(position);
		// Seek bottom left - to - bottom right corner
		if(position.x <= 400 && position.y < 699){
			numOfRotation--;
//			System.out.println("top to bottom " + this.kinematic.getPosition());
			Target target = new Target(new PVector(400, 700));
			SteeringArrive arrive = new SteeringArrive(this, target);
			SteeringAlign align = new SteeringAlign(this, target);
			SteeringOutput s1 = arrive.getSteering();
			SteeringOutput s2 = align.getSteering();
			s = SteeringOutput.add(s1, s2);
        }
		// seek bottom right - to - top right corner
		else if(position.x < 700 && position.y <= 700){
//			System.out.println("bottom to right" + this.kinematic.getPosition());
			Target target = new Target(new PVector(700, 700));
			SteeringArrive arrive = new SteeringArrive(this, target);
			SteeringAlign align = new SteeringAlign(this, target);
			SteeringOutput s1  = arrive.getSteering();
			SteeringOutput s2 = align.getSteering();
			s = SteeringOutput.add(s1, s2);
        }
        // seek top right - to - top left corner
		else if(position.x > 400 && position.y > 699){
//			System.out.println("bottom to top" + this.kinematic.getPosition());
			PVector p = new PVector(700,400);
			Target target = new Target(p);
			SteeringArrive arrive = new SteeringArrive(this, target);
			SteeringAlign align = new SteeringAlign(this, target);
			SteeringOutput s1  = arrive.getSteering();
			SteeringOutput s2 = align.getSteering();
			s = SteeringOutput.add(s1, s2);
        }
        // seek top left - to - bottom left corner
		else if (position.x >= 400 && position.y >= 400){
//			System.out.println("<=400 & <=400 " + this.kinematic.getPosition());
			Target target = new Target(new PVector(400, 400));
			SteeringArrive arrive = new SteeringArrive(this, target);
			SteeringAlign align = new SteeringAlign(this, target);
			SteeringOutput s1  = arrive.getSteering();
			SteeringOutput s2 = align.getSteering();
			s = SteeringOutput.add(s1, s2);
        }	
		return s;
	}
	
	public void display() {

		PVector position = this.kinematic.getPosition();
		PVector velocity = this.kinematic.getVelocity();
		float mag = velocity.mag();
		parent.pushMatrix();
		
		parent.translate(position.x, position.y);
//		parent.strokeWeight(3);
//		parent.line(0, 0,  mag*velocity.x, mag*velocity.y);
//		parent.strokeWeight(1);
		parent.rotate(this.kinematic.getOrientation());
		parent.fill(240,240,240);
		parent.ellipse(0, 0, size, size);
		parent.fill(80,80,80);
		parent.triangle(0, -size/2, 0, size/2, size, 0);
		
		parent.popMatrix();
	}
	
	public void displayE() {

		PVector position = this.kinematic.getPosition();
		PVector velocity = this.kinematic.getVelocity();
		float mag = velocity.mag();
		parent.pushMatrix();
		
		parent.translate(position.x, position.y);
		parent.rotate(this.kinematic.getOrientation());
		parent.fill(240,40,40);
		parent.ellipse(0, 0, size, size);
		parent.fill(140,80,80);
		parent.triangle(0, -size/2, 0, size/2, size, 0);
		
		parent.popMatrix();
	}
	
	/*Getter Setters*/
	public PApplet getParent() {
		return parent;
	}

	public void setParent(PApplet parent) {
		this.parent = parent;
	}

	public Kinematic getKinematic() {
		return kinematic;
	}

	public void setKinematic(Kinematic kinematic) {
		this.kinematic = kinematic;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void reset() {
		// TODO Auto-generated method stub
		this.kinematic.setPosition(new PVector(Constants.DEFAULT_POSITION.x, Constants.DEFAULT_POSITION.y));
	}

	
	
	
}
