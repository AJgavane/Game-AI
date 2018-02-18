import processing.core.PApplet;
import processing.core.PVector;

public class Boids {
	private PApplet parent;
	private int numOfBoids = 30;
	private Character[] boids;
	
	// constructors
	public Boids(PApplet parent, int numOfBoids) {
		this.parent = parent;
		this.numOfBoids = numOfBoids;
		this.boids = new Character[numOfBoids];
	}
	
	// Methods
	public void initBoids() {
		int size = 0;
		for(int i = 0 ; i < numOfBoids; i++){
			size = (int) parent.random(4, 20);			
			boids[i] = new Character(this.parent, new PVector(parent.width/(i+1), parent.random(0,parent.height)), size, i);
		}
	}
	
	// Credits: http://www.vergenet.net/~conrad/boids/pseudocode.html
	// I have referred to the pseudo code from this web site along with the notes 
	public void move(int bound) {
		PVector vec1 = new PVector();
		PVector vec2 = new PVector();
		PVector vec3 = new PVector();
		PVector velocity = new PVector();
		Character c = new Character();
		for(Character b: boids) {
			vec1 = flyTowardsCM(b);
			vec1.mult(0.3f);
			vec2 = collisionAvoidance(b);
			vec2.mult(0.4f);
			vec3 = velocityMatching(b);
			vec3.mult(0.3f);
			if(bound == 0){
				unboundTheBoids(b);
			} else {
				boundTheBoids(b);
			}
			
			PVector initPos = b.getKinematic().getPosition();
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec1));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec2));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec3));
			
			// Controlling the maxSpeed of the character
			if(b.getKinematic().getVelocity().mag() > b.getMaxSpeed()) 
				(b.getKinematic().getVelocity().normalize()).mult(b.getMaxSpeed());				
			
			b.getKinematic().setPosition( b.getKinematic().getPosition().add(b.getKinematic().getVelocity()));
			// Orienting the character in the direction of the motion.
			PVector dirOrientation = b.getKinematic().getVelocity();
			b.getKinematic().setOrientation((float) Math.atan2(dirOrientation.y, dirOrientation.x));
		}
	}
	
	// matching the average velocity of the flock
	private PVector velocityMatching(Character bj) {
		PVector pvj = new PVector(0,0);		
		for(Character b: boids)
			if(b.getIndex() != bj.getIndex())
				pvj.add(b.getKinematic().getVelocity());
		pvj.div(numOfBoids-1);
		pvj.sub(bj.getKinematic().getVelocity());
		pvj.div(numOfBoids);
		
		return pvj;
	}

	// separation: collision avoidance
	private PVector collisionAvoidance(Character bj) {
		PVector c = new PVector(0,0);
		for(Character b: boids){
			if(b.getIndex() != bj.getIndex()) {
				PVector dist = PVector.sub(b.getKinematic().getPosition(),bj.getKinematic().getPosition());
				if( dist.mag() < (b.getSize()/2 + bj.getSize()/2)) {
					c.sub(dist);
					c.div(10);
				}
			}
		}
		return c;
	}

	// Cohession
	private PVector flyTowardsCM(Character bj) {
		PVector pcj = new PVector(0,0);
		for(Character b: boids)
			if (b.getIndex() != bj.getIndex()) 
				pcj.add(b.getKinematic().getPosition());
		pcj.div(numOfBoids-1);
		pcj.sub(bj.getKinematic().getPosition());
		pcj.div(10000);
		return pcj;
	}
	
	// bouded by the walls
	private void boundTheBoids(Character b) {
		PVector velocity = new PVector();
		if(b.getKinematic().getPosition().x < 0){ 
			velocity.set(1,b.getKinematic().getVelocity().y);
			b.getKinematic().setVelocity(velocity);
		} else if (b.getKinematic().getPosition().x > parent.width) {
			velocity.set(-1, b.getKinematic().getVelocity().y);
			b.getKinematic().setVelocity(velocity);
		}
		
		if(b.getKinematic().getPosition().y < 10){ 
			velocity.set(b.getKinematic().getVelocity().x, 1);
			b.getKinematic().setVelocity(velocity);
		} else if (b.getKinematic().getPosition().y > parent.height) {
			velocity.set(b.getKinematic().getVelocity().x, -1);
			b.getKinematic().setVelocity(velocity);
		}
	}

	// unbounded
	private void unboundTheBoids(Character b) {
		PVector velocity = new PVector();
		if(b.getKinematic().getPosition().x < 0)
			b.getKinematic().setPosition(new PVector(parent.width,b.getKinematic().getPosition().y));
		else if (b.getKinematic().getPosition().x > parent.width) 
			b.getKinematic().setPosition(new PVector(0,b.getKinematic().getPosition().y));
		
		if(b.getKinematic().getPosition().y < 0)
			b.getKinematic().setPosition(new PVector(b.getKinematic().getPosition().x, parent.height));
		else if (b.getKinematic().getPosition().y > parent.height) 
			b.getKinematic().setPosition(new PVector(b.getKinematic().getPosition().x, 0));
	}

	public void dispayBoids() {
		for(Character b: boids){
			b.display();
		}
	}

	public void move(PVector target) {
		PVector vec1 = new PVector();
		PVector vec2 = new PVector();
		PVector vec3 = new PVector();
		PVector vec4 = new PVector();
		PVector velocity = new PVector();
		Character c = new Character();
		for(Character b: boids) {
			vec1 = flyTowardsCM(b);
			vec1.mult(0.2f);
			vec2 = collisionAvoidance(b);
			vec2.mult(0.3f);
			vec3 = velocityMatching(b);
			vec3.mult(0.2f);
			vec4 = flyTowardsTarget(b, target);
			vec4.mult(0.3f);
			
			unboundTheBoids(b);
			
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec1));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec2));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec3));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec4));
			
			// Controlling the maxSpeed of the character
			if(b.getKinematic().getVelocity().mag() > b.getMaxSpeed()) 
				(b.getKinematic().getVelocity().normalize()).mult(b.getMaxSpeed());				
			
			b.getKinematic().setPosition( b.getKinematic().getPosition().add(b.getKinematic().getVelocity()));
			// Orienting the character in the direction of the motion.
			PVector dirOrientation = b.getKinematic().getVelocity();
			b.getKinematic().setOrientation((float) Math.atan2(dirOrientation.y, dirOrientation.x));
		}
	}

	private PVector flyTowardsTarget(Character b, PVector target) {
		PVector result = PVector.sub(target, b.getKinematic().getPosition());
		return result.div(100);
	}

	public void move(PVector target1, PVector target2) {
		// TODO Auto-generated method stub
		PVector vec1 = new PVector();
		PVector vec2 = new PVector();
		PVector vec3 = new PVector();
		PVector vec4 = new PVector();
		PVector velocity = new PVector();
		Character c = new Character();
		for(Character b: boids) {
			vec1 = flyTowardsCM(b);
			vec1.mult(0.2f);
			vec2 = collisionAvoidance(b);
			vec2.mult(0.3f);
			vec3 = velocityMatching(b);
			vec3.mult(0.2f);
			vec4 = flyTowardsClosestTarget(b, target1, target2);
			vec4.mult(0.3f);
			
			unboundTheBoids(b);
			
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec1));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec2));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec3));
			b.getKinematic().setVelocity( b.getKinematic().getVelocity().add(vec4));
			
			// Controlling the maxSpeed of the character
			if(b.getKinematic().getVelocity().mag() > b.getMaxSpeed()) 
				(b.getKinematic().getVelocity().normalize()).mult(b.getMaxSpeed());				
			
			b.getKinematic().setPosition( b.getKinematic().getPosition().add(b.getKinematic().getVelocity()));
			// Orienting the character in the direction of the motion.
			PVector dirOrientation = b.getKinematic().getVelocity();
			b.getKinematic().setOrientation((float) Math.atan2(dirOrientation.y, dirOrientation.x));
		}
	}

	private PVector flyTowardsClosestTarget(Character b, PVector target1, PVector target2) {
		
		PVector result1 = PVector.sub(target1, b.getKinematic().getPosition());
		PVector result2 = PVector.sub(target2, b.getKinematic().getPosition());
		if(result1.mag() < result2.mag())
			return result1.div(100);
		else
			return result2.div(100);
	}
}
