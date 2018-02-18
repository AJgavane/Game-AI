import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class BasicMotion extends PApplet {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 PApplet.main("BasicMotion");
    }
	
	Character c1 = new Character(this, new PVector(10, height*4 + 65), 20);
	
	BreadCrumbs[] breadCrumbs;
	ArrayList<PVector> bc = new ArrayList<PVector>();
	int frame = 0;
	
	float currTime, pastTime = 0;
	public void settings(){
        size(640,480);
    }

    public void setup(){
        fill(120,50,240);
        noStroke();
        
    }

    public void draw(){
    	fill(1, 1);
    	rect(0, 0, width, height);       
//        PVector target = new PVector(width/2, height/2);
        currTime = millis();
        fill(10,100f);
        c1.traversePerimeter();
        fill(255);
        c1.display();
        c1.getKinematic().update(c1.getSteering(), (currTime-pastTime)/80);
        pastTime = currTime;
        
        
        frame++;
//        println(frame);
        if(frame % 10 == 0) {
        	PVector position = c1.getKinematic().getPosition();
        	bc.add(new PVector(position.x, position.y));
        }
        
        for(int c =0; c < bc.size(); c++) {        	
        	fill(0,255,0);
        	PVector position =bc.get(c);
        	ellipse(position.x, position.y, 3, 3);
        }

    } 
}
