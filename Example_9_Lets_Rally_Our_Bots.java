package exampleBot;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;


public class Example_9_Lets_Rally_Our_Bots { // << This class must actually be called RobotPlayer, but isnt solely for this demo
	
	public static final boolean Run = false;

	
	public static void run(RobotController rc) {
		try {
			switch (rc.getType()) {
			case HQ:
				while ( true ) { 

					if ( rc.isActive() ) {
						Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation()); // Get the direction to the enemy Base
						MapLocation spawnLocation = rc.getLocation().add(dir); // Get me the next adjacent location in that direction
						
						// Check to see if there's a mine OR another robot at that location
						while ( rc.senseMine(spawnLocation) != null || rc.senseObjectAtLocation(spawnLocation) != null ) {
							dir = dir.rotateLeft(); // If so, rotate until we find a direction with no mines
							spawnLocation = rc.getLocation().add(dir);
						}
						
						rc.spawn(dir); // Spawn a robot in that direction!!
						
					} 

					rc.yield();
				}
			
			case SOLDIER:
				MapLocation enemyBase = rc.senseEnemyHQLocation(); // Get the enemy HQ location
				MapLocation myTarget = getMidPoint(rc.senseHQLocation(), enemyBase); // Lets pick a target Rally point in the center between the two HQs
				while ( true ) {
					
					if ( rc.isActive() ) {
						Direction moveDir = rc.getLocation().directionTo(myTarget); // I want to move in the direction of my Rally Point instead!
						
						while( !rc.canMove(moveDir) ) { // Check to see if i can move there, ie whether or not there's another bot there
							moveDir = moveDir.rotateLeft(); // If not, rotate until i get a direction with no robots
						}
						MapLocation moveLocation = rc.getLocation().add(moveDir); // Get me the next adjacent location in that direction

						if ( rc.senseMine(moveLocation) != null ) { // But first make sure there are no mines
							rc.defuseMine(moveLocation); // If so, defuse it!
						} else {
							rc.move(moveDir); // Move in that direction!
						}
					
						
					}

					rc.yield();
				}
			case ARTILLERY:
				while ( true ) {
					
					if ( rc.isActive() ) {
					}

					rc.yield();
				}
				
			case GENERATOR:
				while ( true ) {
					
					if ( rc.isActive() ) {
					}

					rc.yield();
				}

			case MEDBAY:
				while ( true ) {
					
					if ( rc.isActive() ) {
					}

					rc.yield();
				}

			case SHIELDS:
				while ( true ) {
					
					if ( rc.isActive() ) {
					}

					rc.yield();
				}

			case SUPPLIER:
				while ( true ) {
					
					if ( rc.isActive() ) {
					}

					rc.yield();
				}

			default:
				break;
			
			}
		} catch (Exception e) {
			rc.breakpoint();
			e.printStackTrace();
			//run(rc);
		}
	}
	
	// Get the midpoint between two locations
	private static MapLocation getMidPoint(MapLocation location1, MapLocation location2) {
		int midX = (location1.x+location2.x)/2; // Get the middle value between these two X's
		int midY = (location1.y+location2.y)/2; // Get the middle value between these two Y's
		return new MapLocation(midX, midY); // Construct a new location object 
	}	
}
