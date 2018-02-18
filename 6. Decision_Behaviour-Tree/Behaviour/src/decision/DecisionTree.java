package decision;

import java.util.ArrayList;

import graph.Obstacle;
import movement.Character;
import movement.Constants;
import processing.core.PVector;

public class DecisionTree {

	private Character monster;
	private Character player;
	private ArrayList<Obstacle> obstalces;
	public DecisionTree(Character monster, Character character, ArrayList<Obstacle> obstacleList) {
		this.monster = monster;
		this.player = character;
		this.obstalces = obstacleList;
	}
	public PVector getSeekLocation() {
		PVector pos = monster.getKinematic().getPosition();
		float threshold = 10;
		PVector seekLocation = new PVector();
		if(Constants.GOTO_P3 == false) {
			if (Constants.GOTO_P2 == false){
				if(pos.dist(Constants.P1) < threshold){
					Constants.GOTO_P2 = true;
				}
				seekLocation = Constants.P1;
			} 
			else {
				if(pos.dist(Constants.P2) < threshold){
					Constants.GOTO_P3 = true;
				}
				seekLocation = Constants.P2;
			}
		}
		else {
			if(Constants.SCRIPTED_PATH == false){
				if(Constants.GOTO_P4 == false) {
					if(pos.dist(Constants.P3) < threshold){
						Constants.GOTO_P4 = true;
					}
					seekLocation = Constants.P3;
				} 
				else {
					if(pos.dist(Constants.P4) < threshold){
						Constants.SCRIPTED_PATH = true;
					}
					seekLocation = Constants.P4;
				}
			} else {
				if (Constants.APPROACH_INVERTER == false) {
					seekLocation = approach();
				}
				else {
					if(Constants.APRROACH_AND_CATCH == false){
						System.out.println("catching");
						seekLocation = catchPlayer();
					} else {
						Constants.BEHAVE_MONSTER = true;
					}
				}
			}
		}
		return seekLocation;
	}
	
	private PVector catchPlayer() {
		Constants.CATCHING = true;
		Constants.MAX_SPEED_MONSTER = 13.0f;
		if(playerNotInRange()){
			Constants.APPROACH_INVERTER = false;
			return monster.getKinematic().getPosition();
		}
		if(player.getKinematic().getPosition().dist(monster.getKinematic().getPosition()) < 20 ){
			Constants.APRROACH_AND_CATCH = true;
		}
		PVector dest = player.getKinematic().getPosition().copy();
		return dest;
	}
	
	private boolean playerNotInRange() {
		if( monster.getKinematic().getPosition().dist(player.getKinematic().getPosition()) > 200)
			return true;
		return false;
	}
	
	private PVector approach() {
		PVector seekLocation = Constants.MONSTER_POSITION;
		if(playerNotInRange()) {
			seekLocation = player.getKinematic().getPosition();
		} else {
			Constants.APPROACH_INVERTER = true;
		}
		return seekLocation;
	}
}
