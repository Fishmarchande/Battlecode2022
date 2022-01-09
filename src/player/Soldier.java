package player;
import battlecode.common.*;

public class Soldier extends RobotPlayer {
    static MapLocation targetLocation;
    static boolean testing = true;// this checks if we already set our testnig target location
    static boolean scouting = false;
    static int robotId;
    static Direction oneLine;
    static RobotInfo[] enemies;
    static RobotInfo[] friends;
    static boolean followingReport = false;
    static boolean onSite = false;
    static int turnsWithoutCombat = 0;
    static int currentCr = -1;

    public static void run(RobotController rc) throws GameActionException {
        enemies = rc.senseNearbyRobots(20, rc.getTeam().opponent());
        friends = rc.senseNearbyRobots(20, rc.getTeam());
        robotId = rc.getID();
        if(turnCount == 1) {
            if (robotId % 10 == 2 || robotId % 10 == 1) {// this just randomly determiens if they should scout, we should make a better system
                rc.setIndicatorString("Scouting");
                scouting = true;
                oneLine = directions[rng.nextInt(directions.length)];// random search taken from miner bot

            }
        }
        if(enemies.length > 0 && scouting){        // generate combat report

            rc.setIndicatorString("I should try and report a combat");
            //time to generate combat report if we are a scout,
            // we should probably make sure that robots in an already known combat don't report new combats
            for(int i = 0; i<9; i++){// look for open combat report
                int a = rc.readSharedArray(i);
                if(a == 0){// unused spot
                    targetLocation = rc.getLocation();
                    rc.setIndicatorString("Writing combat report...");

                    String l = String.valueOf(enemies.length);
                    String x = String.valueOf(targetLocation.x);
                    if(x.length()== 1){
                        x = "0" + x;
                    }

                    String y = String.valueOf(targetLocation.y);
                    if(y.length() == 1){
                        y = "0" + y;
                    }

                    String write = l + x + y;
                    rc.writeSharedArray(i,Integer.valueOf(write));
                    break;
                }
            }
        }
        if (scouting) {
            Direction dir2 = directions[rng.nextInt(directions.length)];
            if (rc.canMove(oneLine)) { // Move in a straight line determined earlier
                rc.move(oneLine);
            } else if (rc.canMove(dir2)) { // Most likely hit a wall, set new oneLine and move there
                rc.move(dir2);
                oneLine = dir2;
            }
        }



        if(enemies.length > 0){
            RobotInfo target = enemies[0];
            if(rc.canAttack(target.getLocation())){
                rc.attack(target.getLocation());
            }
        }



        if(!scouting) {// if we are not scouts, look for combat and fight!

            if(!followingReport) { // if we haven't found combat yet, lets go to nearest report
                for (int i = 0; i < 9; i++) {// look for open combat report
                    int a = rc.readSharedArray(i);
                    System.out.println("Looking at " + rc.readSharedArray(i));
                    if (a != 0) {// found a combat report!
                        int newy = a % 100;//grabbing values
                        int newx = (a % 1000 - a % 100) / 100;

                        targetLocation = new MapLocation(newx, newy);
                        rc.setIndicatorString("going to location " + newx + " , " + newy);
                        followingReport = true;
                        currentCr = i; // keep track of our combat report
                    }
                }

            }
            if(rc.canMove(rc.getLocation().directionTo(targetLocation))){
                rc.move(rc.getLocation().directionTo(targetLocation));
            }
            else{
                if(rc.canMove(rc.getLocation().directionTo(targetLocation).rotateRight())) {

                    rc.move(rc.getLocation().directionTo(targetLocation).rotateRight());
                }
            }
            if(rc.getLocation().isWithinDistanceSquared(targetLocation, 9)){ // if we are already at a
                rc.setIndicatorString("Already on site");                                                            // combat site, don't look for another one
                onSite = true;
            }
        }

        if(onSite){
            rc.setIndicatorString("ON SITE");// we are at a location where combat has been reported, fight for x turns,
                    // and if we find nothing then destroy current report and move on
            if(enemies.length > 0){
                turnsWithoutCombat = 0;
            }
            else{
                turnsWithoutCombat ++;
            }
            if(turnsWithoutCombat>10){
                onSite = false;
                rc.writeSharedArray(currentCr,0);
                followingReport = false;
                rc.setIndicatorString("No more enemies it appears");
            }
            rc.move(rc.getLocation().directionTo(targetLocation)); // if we are on site, move around randomly to find baddies
        }
    }
}
