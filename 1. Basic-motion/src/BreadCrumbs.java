import processing.core.PApplet;
import processing.core.PVector;

public class BreadCrumbs {
	
	PApplet parent;
	PVector position = new PVector(80,80);
	
	public BreadCrumbs(PApplet parent) {
		this.parent = parent;
	}
	
	public void display(){
		parent.ellipse(position.x, position.y, 5, 5);
	}

	public void display(PVector position){
		parent.ellipse(position.x, position.y, 5, 5);
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}
}
