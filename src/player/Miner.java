package player;
import battlecode.common.*;


public class Miner extends RobotPlayer{
    static MapLocation origin;
    static MapLocation leadSource = null;
    static MapLocation[]leadSources = null;
    static boolean foundLead = false;
    //static boolean miningLead = false; // UNUSED
    static Direction oneLine = null;
    public static void run(RobotController rc) throws GameActionException {

        if(turnCount == 1){
            origin = rc.getLocation();
            oneLine = directions[rng.nextInt(directions.length)];
        }

        for (Direction dir:directions){
            if(rc.canMineLead(rc.adjacentLocation(dir)) && rc.senseLead(rc.adjacentLocation(dir)) > 1){
                rc.mineLead(rc.adjacentLocation(dir));
            }
        }



        if(turnCount % 4 == 0) { // Check for lead sources (within sense range of 20 radii squared) every 4 turns and put them in the array leadSources
            leadSources = rc.senseNearbyLocationsWithLead(20); // 100 bytecode, not that bad
        }
        if(leadSources.length > 0){ // if there is a place for us to mine, then lets try to go to the first one
            foundLead = true;
            leadSource = leadSources[0];
        } else {
            foundLead = false;
        }
        if(foundLead){
            if(rc.canMove(rc.getLocation().directionTo(leadSource))) {
                rc.move(rc.getLocation().directionTo(leadSource));
            }
        }
        if(!foundLead){// looking for lead to mine
            Direction dir2 = directions[rng.nextInt(directions.length)];
            if (rc.canMove(oneLine)) { // Move in a straight line determined earlier
                rc.move(oneLine);
            } else if (rc.canMove(dir2)) { // Most likely hit a wall, set new oneLine and move there
                rc.move(dir2);
                oneLine = dir2;
            }
        }









    }
}
