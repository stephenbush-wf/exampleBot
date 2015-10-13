package exampleBot;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;


public class Example_2_Oops_Dont_Spawn_On_Mines { // << This class must actually be called RobotPlayer, but isnt solely for this demo
	
	public static final boolean Run = false;
	
	
	public static void run(RobotController rc) {
		try {
			switch (rc.getType()) {
			case HQ:
				while ( true ) { 

					if ( rc.isActive() ) {
						Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation()); // Get the direction to the enemy Base
						MapLocation spawnLocation = rc.getLocation().add(dir); // Get me the next adjacent location in that direction
						
						while ( rc.senseMine(spawnLocation) != null) { // Check to see if there's a mine at that location
							dir = dir.rotateLeft(); // If so, rotate until we find a direction with no mines
							spawnLocation = rc.getLocation().add(dir);
						}
						
						rc.spawn(dir); // Spawn a robot in that direction!!
						
					} 

					rc.yield();
				}
			
			case SOLDIER:
				while ( true ) {
					
					if ( rc.isActive() ) {
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
}
