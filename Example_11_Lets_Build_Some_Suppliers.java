package exampleBot;

import java.util.Random;

import battlecode.common.Clock;
import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.RobotType;


public class Example_11_Lets_Build_Some_Suppliers { // << This class must actually be called RobotPlayer, but isnt solely for this demo
	
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
					// Changed the rally point to 1/4 of the way across
					MapLocation swarmTarget = getMidPoint(rc.senseHQLocation(), getMidPoint(rc.senseHQLocation(), rc.senseEnemyHQLocation()));
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
				// Create a new random number generator based on the team and the Clock, because Random seeds are preset.
				Random r = new Random((rc.getTeam().ordinal()+2)*Clock.getRoundNum());
				r.nextFloat(); // There's a bug in the seeding for the first number, so consume it 

				// I have a 8% chance of becoming a Supplier!
				if ( r.nextFloat() <= 0.08 ) {
					goBuildASupplier(rc);
				}
						
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
		}
	}
	
	// Move to and then build a supplier
	private static void goBuildASupplier(RobotController rc) {
		try {
			
			// Select a nearby Encampment
			MapLocation[] encampments = rc.senseAllEncampmentSquares();
			MapLocation closest = null;
			// Loop over all of the encampments, looking for one that is empty and close
			for ( MapLocation target : encampments ) {
				if ( rc.canSenseSquare(target) && rc.senseObjectAtLocation(target) != null ) {
					// One of our bots is already here
					continue;
				}
				else {
					// If i have no target yet, or this target is closer than my current, keep it 
					if ( closest == null || rc.getLocation().distanceSquaredTo(target) < rc.getLocation().distanceSquaredTo(closest) ) {
						closest = target;
					}
				}
			}
			while ( true ) {
				
				if ( rc.isActive() ) {
					if ( rc.getLocation().equals(closest) ) {
						// I have arrived!!!
						if ( rc.getTeamPower() > rc.senseCaptureCost() ) rc.captureEncampment(RobotType.SUPPLIER);
					} else {
						
						Direction moveDir = rc.getLocation().directionTo(closest); // I want to move in the direction of my target
						
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
				}
				rc.yield();
			}
		} catch (Exception e) {
			rc.breakpoint();
			e.printStackTrace();
			goBuildASupplier(rc);
		}		
	}

	// Get the midpoint between two locations
	private static MapLocation getMidPoint(MapLocation location1, MapLocation location2) {
		int midX = (location1.x+location2.x)/2; // Get the middle value between these two X's
		int midY = (location1.y+location2.y)/2; // Get the middle value between these two Y's
		return new MapLocation(midX, midY); // Construct a new location object 
	}
}
