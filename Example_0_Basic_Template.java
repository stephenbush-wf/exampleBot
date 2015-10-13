package exampleBot;

import battlecode.common.RobotController;


public class Example_0_Basic_Template {
	
	public static final boolean Run = false;

	
	public static void run(RobotController rc) {
		try {
			switch (rc.getType()) {
			case HQ:
				while ( true ) { 

					if ( rc.isActive() ) {
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

	/* .---------------------.
	 * |  Sub-Class Methods  | 
	 * '---------------------' */
	
	
}
