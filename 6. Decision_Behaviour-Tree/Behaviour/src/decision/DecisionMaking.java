package decision;

import java.util.ArrayList;

import graph.Coins;
import movement.Character;
import movement.Constants;
import movement.SteeringOutput;
import processing.core.PVector;

public class DecisionMaking {

	private Character c;
	private ArrayList<Coins> coins;
	
	public DecisionMaking(Character character, ArrayList<Coins> coinList) {
		// TODO Auto-generated constructor stub
		this.c = character;
		this.coins = coinList;
	}
	
	public PVector getSeekLocation() {
		
		PVector pos = c.getKinematic().getPosition();
		PVector targetPosition = null;
		Coins coin = coinInProximity(Constants.PROXIMITY, pos);
//		System.out.println(c.getKinematic().getVelocity().mag() + " " + Constants.MAX_SPEED);
		if( c.getKinematic().getVelocity().mag() > Constants.MAX_SPEED ){
			float theta = c.getKinematic().getOrientation();
			theta -= Math.PI/3;
			c.getKinematic().setOrientation(theta);
			PVector vel = c.getKinematic().getVelocity();
			vel.mult(-0.3f);
			c.getKinematic().setVelocity(vel);
		}
		if( coin != null){
			if(PVector.dist(coin.getPosition(), pos) < 10) {
				coin.setAcquired(true);
			}
			targetPosition = coin.getPosition().copy();		
		} 
		
		return targetPosition;
	}
	
	private Coins coinInProximity(int proximity, PVector position) {
		for( Coins c: coins) {
			if(c.getPosition().dist(position) < proximity && !c.isAcquired()){	
//				System.out.println(c.getPosition().dist(position));
				return c;
			}
		}
		return null;
	}
}
