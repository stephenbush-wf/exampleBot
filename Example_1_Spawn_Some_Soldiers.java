package exampleBot;

import battlecode.common.Direction;
import battlecode.common.RobotController;


public class Example_1_Spawn_Some_Soldiers { // << This class must actually be called RobotPlayer, but isnt solely for this demo
	
	public static final boolean Run = false;

	
	public static void run(RobotController rc) {
		try {
			switch (rc.getType()) {
			case HQ:
				while ( true ) { 

					if ( rc.isActive() ) {
						Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation()); // Get the direction to the enemy Base
						
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
			run(rc);
		}
	}
}
