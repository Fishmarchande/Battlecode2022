package player;
import battlecode.common.*;


public class Miner extends RobotPlayer{
    static MapLocation origin;
    static MapLocation leadSource = null;
    static MapLocation[]leadSources = null;
    static boolean foundLead = false;
    static boolean miningLead = false;
    public static void run(RobotController rc) throws GameActionException {

        if(turnCount == 1){
            origin = rc.getLocation();
        }

        for (Direction dir:directions){
            if(rc.canMineLead(rc.adjacentLocation(dir))){
                rc.mineLead(rc.adjacentLocation(dir));
            }
        }




        leadSources = rc.senseNearbyLocationsWithLead(20); // 100 bytecode, not that bad
        if(leadSources.length > 0){ // if there is a place for us to mine, then lets try to go to the first one
            foundLead = true;
            leadSource = leadSources[0];
        }
        if(foundLead){
            if(rc.canMove(rc.getLocation().directionTo(leadSource))) {
                rc.move(rc.getLocation().directionTo(leadSource));
            }
        }
        if(!foundLead){// looking for lead to mine
            Direction dir = directions[rng.nextInt(directions.length)];
            if (rc.canMove(dir)) {
                rc.move(dir);
                System.out.println("I moved!");
            }
        }









    }
}
