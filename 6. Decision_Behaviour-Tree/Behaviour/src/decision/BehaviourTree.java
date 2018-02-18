package decision;

import java.util.ArrayList;

import graph.Obstacle;
import graph.Obstacles;
import movement.Character;
import movement.Constants;
import movement.SteeringWander;
import processing.core.PVector;

public class BehaviourTree {
	private Character monster;
	private Character player;
	private ArrayList<Obstacle> obstacles;
	private float approachDist = 300;
	
	public BehaviourTree(Character monster, Character player, ArrayList<Obstacle> obstacleList) {
		this.monster = monster;
		this.player = player;
		this.obstacles = obstacleList;
	}

	public PVector getSeekLocation() {
		PVector seekLocation = Constants.MONSTER_POSITION;
		if(Constants.BEHAVE_MONSTER == false) {
			if(Constants.SCRIPTED_PATH == false){
				seekLocation = followScriptedPath();
			}
			if(Constants.SCRIPTED_PATH && Constants.APRROACH_AND_CATCH == false){
				seekLocation = approachOrCatch();
			}
			if(Constants.SCRIPTED_PATH && Constants.APRROACH_AND_CATCH){
				Constants.BEHAVE_MONSTER  = true;
			}
		}
		return seekLocation;
	}

	private PVector approachOrCatch() {
		PVector seekLocation = Constants.MONSTER_POSITION;
		if(Constants.APPROACH_INVERTER == false) {
			seekLocation = approachIfNotInRange();
		} else {
			seekLocation = catchPlayer();
		}
		return seekLocation;
	}

	private PVector catchPlayer() {
		Constants.CATCHING = true;
		if(playerNotInRange()){
			Constants.APPROACH_INVERTER = false;
			return monster.getKinematic().getPosition();
		}
		if(player.getKinematic().getPosition().dist(monster.getKinematic().getPosition()) < 20 ){
			Constants.APRROACH_AND_CATCH = true;
		}
		Constants.MAX_SPEED_MONSTER = 13.0f;
		PVector dest = player.getKinematic().getPosition().copy();
		return dest;
	}

	private PVector approachIfNotInRange() {
		PVector seekLocation = Constants.MONSTER_POSITION;
		if(playerNotInRange()) {
			seekLocation = approachEnemy();
		} else {
			Constants.APPROACH_INVERTER = true;
		}
		return seekLocation;
	}

	private PVector approachEnemy() {
		Constants.MAX_SPEED_MONSTER = 7.0f;
		return player.getKinematic().getPosition();
	}

	private boolean playerNotInRange() {
		if( monster.getKinematic().getPosition().dist(player.getKinematic().getPosition()) > approachDist)
			return true;
		return false;
	}

	private PVector followScriptedPath() {
		float threshold = 20;
		PVector pos = monster.getKinematic().getPosition();
		if(Constants.GOTO_P1) {
			if(Constants.GOTO_P2) {
				if(Constants.GOTO_P3){
					if(Constants.GOTO_P4){			
						if(pos.dist(Constants.P4) < threshold){
							System.out.println("GOTO_SP");
							Constants.SCRIPTED_PATH = true;
						}
						return Constants.P4;
					}
					if(pos.dist(Constants.P3) < threshold){
						System.out.println("GOTO_P4");
						Constants.GOTO_P4 = true;
					}
					return Constants.P3;
				}
				if(pos.dist(Constants.P2) < threshold){
					System.out.println("GOTO_P3");
					Constants.GOTO_P3 = true;
				}
				return Constants.P2;
			}
			if(pos.dist(Constants.P1) < threshold){
				System.out.println("GOTO_P2");
				Constants.GOTO_P2 = true;
			}
			return Constants.P1;
		}
		return Constants.DEFAULT_POSITION;
	}
	
}
