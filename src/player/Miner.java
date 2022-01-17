package player;
import battlecode.common.*;

import java.util.Random;


public class Miner {
    static int turnCount = 0;
    static MapLocation origin;
    static MapLocation leadSource = null;
    static MapLocation[]leadSources = null;
    static boolean foundLead = false;
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };
    static Direction oneLine = null;
    public static final Random rng = new Random();
    static RobotInfo[] enemies;

    static boolean danger = false;
    static int avoidIndex = 0;

    public static void run(RobotController rc) throws GameActionException {

        if(turnCount == 1){
            origin = rc.getLocation();
            oneLine = directions[rng.nextInt(directions.length)];
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
                if (rc.canMove(rc.getLocation().directionTo(enemies[avoidIndex].getLocation()).opposite())) {
                    rc.move(rc.getLocation().directionTo(enemies[avoidIndex].getLocation()).opposite()); //Run opposite direction from enemy
                    System.out.println("Saw an hostile, running away");
                }
            }
        }
        for (Direction dir:directions){
            if(rc.canMineLead(rc.adjacentLocation(dir)) && rc.senseLead(rc.adjacentLocation(dir)) > 1){
                rc.mineLead(rc.adjacentLocation(dir));
            }
            else if (rc.canMineLead(rc.getLocation()) && rc.senseLead(rc.getLocation()) > 1) {
                rc.mineLead(rc.getLocation());
            }
        }

        if(turnCount % 4 == 0) { // Check for lead sources (within sense range of 20 radii squared) every 4 turns and put them in the array leadSources
            leadSource = null;
            leadSources = rc.senseNearbyLocationsWithLead(-1); // 100 bytecode, not that bad
            int i = 0;
            for (MapLocation source:leadSources) {
                if(leadSources.length>0 && rc.canSenseLocation(source)) {
                    if (!rc.getLocation().equals(source) && rc.senseNearbyRobots(source, 0, rc.getTeam()).length > 0 || rc.senseLead(source) == 1) {
                        leadSources[i] = null;
                    } else {
                        leadSource = leadSources[i];
                    }
                }
                i++;
            }
        }
        // if there is a place for us to mine, then lets try to go to the first one
        foundLead = leadSource != null;
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







        turnCount++;

    }
}
