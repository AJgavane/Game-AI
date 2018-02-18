import processing.core.PApplet;
import processing.core.PVector;

public class FlockingBehavior extends PApplet{

	public static void main(String[] args) {
		PApplet.main("FlockingBehavior");
	}
	
	PVector seekLocation = new PVector(320,240);
	Character wanderer1 = new Character(this, seekLocation, 30);
	Character wanderer2 = new Character(this);
	float currTime, pastTime = 0;
	int num = 30;
	Boids boids = new Boids(this,num);
	
	public void settings(){
        size(512,512);
    }

    public void setup(){
        fill(120,50,240);
        noStroke();
        boids.initBoids();
    }

    public void draw(){
    	fill(128);
    	rect(0, 0, width, height); 
    	
    	/* For simple flocking Comment out below 2 wanderers else they will appear in the flock*/
    	currTime = millis();
    	// Wanderer 1
        fill(10,100f);
        wanderer1.steeringWander();
        fill(255);
        wanderer1.display();
        wanderer1.getKinematic().update(wanderer1.getSteering(), (currTime-pastTime)/100);
        
        // Wanderer 2
        fill(10,100f);
        wanderer2.steeringWander();
        fill(155);
        wanderer2.display();
        wanderer2.getKinematic().update(wanderer2.getSteering(), (currTime-pastTime)/100);
        
        pastTime = currTime;     
    	
        boids.dispayBoids();
        /* For simple flocking uncomment below boids.move() method.
           move(param) ; if param = 0 then the flock is not bounded
           in the rectangle otherwise they bounces off the walls*/
        boids.move(1);
        
        /* For Flocking with one wanderer uncomment below method call*/
//        boids.move(wanderer1.getKinematic().getPosition());
        
        /* For Flocking wiht 2 wanderer uncomment below method call*/
//        boids.move(wanderer1.getKinematic().getPosition(), wanderer2.getKinematic().getPosition());
    }  
    
    public void mousePressed() {
    	seekLocation.x = mouseX;
    	seekLocation.y = mouseY;
    }
}
