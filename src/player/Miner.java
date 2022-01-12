package player;
import battlecode.common.*;


public class Miner extends RobotPlayer{
    static MapLocation origin;
    static MapLocation leadSource = null;
    static MapLocation[]leadSources = null;
    static boolean foundLead = false;
    //static boolean miningLead = false; // UNUSED
    static Direction oneLine = null;
    static boolean skip = false;
    static int runAway = 0;
    static RobotInfo[] enemies;
    static boolean danger = false;
    static int avoidIndex = 0;
    public static void run(RobotController rc) throws GameActionException {

        if(turnCount == 1){
            origin = rc.getLocation();
            oneLine = directions[rng.nextInt(directions.length)];
        }

        if (runAway > 0) {
            runAway--;
        }
        if (runAway == 0 && skip) {
            skip = false;
        }
        enemies = rc.senseNearbyRobots(-1, rc.getTeam().opponent());
        if (enemies.length > 0) {
            danger = false;
            for (int i = 0; i<enemies.length; i++) {
                if(enemies[i].getType() == RobotType.SOLDIER || enemies[i].getType() == RobotType.SAGE || enemies[i].getType() == RobotType.WATCHTOWER) {
                    danger = true;
                    avoidIndex = i;
                }
            }
            if (danger) {
                skip = true; //Don't mine when getting chased
                if (rc.canMove(rc.getLocation().directionTo(enemies[avoidIndex].getLocation()).opposite())) {
                    rc.move(rc.getLocation().directionTo(enemies[avoidIndex].getLocation()).opposite()); //Run opposite direction from enemy
                    System.out.println("Saw an hostile, running away");
                }
            }
        }
        for (Direction dir:directions){
            if(rc.canMineLead(rc.adjacentLocation(dir)) && rc.senseLead(rc.adjacentLocation(dir)) == 1) {
                skip = true;
                runAway += 20;
            }
            else if(rc.canMineLead(rc.adjacentLocation(dir)) && rc.senseLead(rc.adjacentLocation(dir)) > 1){
                rc.mineLead(rc.adjacentLocation(dir));
            }
        }


        if(turnCount % 4 == 0) { // Check for lead sources (within sense range of 20 radii squared) every 4 turns and put them in the array leadSources
            leadSources = rc.senseNearbyLocationsWithLead(-1); // 100 bytecode, not that bad
        }
        if(leadSources.length > 0 && !skip){ // if there is a place for us to mine, then lets try to go to the first one
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
