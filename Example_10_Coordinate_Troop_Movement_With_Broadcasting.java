package exampleBot;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;


public class Example_10_Coordinate_Troop_Movement_With_Broadcasting { // << This class must actually be called RobotPlayer, but isnt solely for this demo
	
	public static final boolean Run = false;
	
	// Channel Constants -- Should NEVER collide.
	static final int BC_SWARM_TARGET_X = 0;
	static final int BC_SWARM_TARGET_Y = 1;
	static final int BC_SWARM_COUNT = 2;

	
	public static void run(RobotController rc) {
		try {
			switch (rc.getType()) {
			case HQ:
				while ( true ) { 
					
					MapLocation swarmTarget = getMidPoint(rc.senseHQLocation(), rc.senseEnemyHQLocation()); // Lets pick a target Rally point in the center between the two HQs
					// When the time is right, Attaaaaaaack!
					if ( rc.readBroadcast(BC_SWARM_COUNT) > 25 ) swarmTarget = rc.senseEnemyHQLocation();
					
					// Each turn, bots who are still alive will increment this count, so that we can know how many there are.
					rc.broadcast(BC_SWARM_COUNT, 0); // But i need to zero it each turn at the HQ
					
					// Broadcast the coordinates for the target!
					rc.broadcast(BC_SWARM_TARGET_X, swarmTarget.x);
					rc.broadcast(BC_SWARM_TARGET_Y, swarmTarget.y);

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
				while ( true ) {
					// Read the target, in case it changes!
					MapLocation myTarget = new MapLocation(rc.readBroadcast(BC_SWARM_TARGET_X), rc.readBroadcast(BC_SWARM_TARGET_Y));
					
					// Increment the swarm count because i am still alive!
					rc.broadcast(BC_SWARM_COUNT, rc.readBroadcast(BC_SWARM_COUNT)+1);
							
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
