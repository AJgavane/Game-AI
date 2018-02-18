package movement;

import processing.core.PVector;

public class Constants {
	
	public static final float CHARACTER_SIZE = 30;  // final
	public static final int GRID_SIZE = 40;			// final
	public static final float MAX_SPEED = 9.0f;			// fianl
	public static float MAX_SPEED_MONSTER = 7.0f;			// fianl
	public static final float MAX_ACCELERATION = 2.0f;		// final
	public static final float RADIUS_OF_SATISFACTION = 10.0f;	
	public static final float RADIUS_OF_DECELERATION = 20.0f;
	
	public static final float WANDER_OFFSET = 1.3f*CHARACTER_SIZE;
	public static final float WANDER_RADIUS = 2*CHARACTER_SIZE/2;
	public static final float WANDER_RATE = (float) Math.PI;
	
	public static final float MAX_ANGULAR_ACCELERATION = 0.3f;	// final
	public static final float MAX_ROTATION = 0.4f;
	public static final float RADIANS_OF_SATISFACTION = 0.01f;
	public static final float RADIANS_OF_DECELERATION = 0.3f;
	
	public static final PVector DEFAULT_POSITION = new PVector(60,60);
	public static final PVector DEFAULT_POSITION2 = new PVector(400,400);
	public static final int WANDER_TIME = 50;
	public static final PVector MONSTER_POSITION = new PVector(140,200);
	public static final int PROXIMITY = 300;
	
	// action names
	// Sequence for Monster
	public static boolean BEHAVE_MONSTER = false;
	
	// sequence booleans for scripted path
	public static boolean SCRIPTED_PATH = false; // this will be true when all below are true
	public static boolean GOTO_P1 = true;
	public static boolean GOTO_P2 = false;
	public static boolean GOTO_P3 = false;
	public static boolean GOTO_P4 = false;
		
	public static final PVector P1 = new PVector(60,60);
	public static final PVector P2 = new PVector(220,60);
	public static final PVector P3 = new PVector(220,340);
	public static final PVector P4 = new PVector(60,340);
	
	// selector for below 2 sequence
	public static boolean APRROACH_AND_CATCH = false;
		// this is a sequence
	public static boolean PURSUE = false;
	public static boolean GO_PURSUE = false;
		// this is a sequence
	public static boolean APPROACH_INVERTER = false;
	public static boolean GO_APPROACH = false;
	public static boolean CATCHING = false;
	
}
