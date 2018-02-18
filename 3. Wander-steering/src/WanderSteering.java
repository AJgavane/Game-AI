import processing.core.PApplet;
import processing.core.PVector;

public class WanderSteering extends PApplet{

	public static void main(String[] args) {
		PApplet.main("WanderSteering");
	}
	
	Character c1 = new Character(this);
	PVector seekLocation = new PVector(320,240);
	float currTime, pastTime = 0;
	
	public void settings(){
        size(512,512);
    }

    public void setup(){
        fill(120,50,240);
        noStroke();
        c1.getKinematic().setOrientation((float) Math.random());
    }

    public void draw(){
    	fill(1, 1);
    	rect(0, 0, width, height);       
        currTime = millis();
        fill(10,100f);
        c1.steeringWander();
        fill(255);
        c1.display();
        c1.getKinematic().update(c1.getSteering(), (currTime-pastTime)/100);
        pastTime = currTime;
    }  
    
    public void mousePressed() {
    	seekLocation.x = mouseX;
    	seekLocation.y = mouseY;
    }

}
