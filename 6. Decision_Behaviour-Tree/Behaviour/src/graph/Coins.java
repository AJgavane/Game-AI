package graph;

import movement.Constants;
import processing.core.PApplet;
import processing.core.PVector;

public class Coins {
	private PApplet parent;
	private PVector position = new PVector();
	private float size = Constants.GRID_SIZE/2;
	private boolean acquired = false;

	public Coins(PApplet parent, PVector position) {
		super();
		this.parent = parent;
		this.position = position;
		this.acquired = false;
	}
	
	public void display() {
		parent.pushMatrix();
		parent.translate(position.x-size/2, position.y-size/2);
		parent.ellipse(0, 0, size, size);
		parent.popMatrix();
	}
	public void display(int count) {
		if(!this.acquired) {
			parent.pushMatrix();
			parent.translate(position.x, position.y);
			parent.fill(120,0,0);
			parent.ellipse(0, 0, size, size);
			parent.fill(0,0,0);
			parent.text(count,0,0);
			parent.popMatrix();
		}
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	public boolean isAcquired() {
		return acquired;
	}

	public void setAcquired(boolean acquired) {
		this.acquired = acquired;
	}	
}
