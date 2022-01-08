package player;
import battlecode.common.*;

public class Soldier extends RobotPlayer {
    static MapLocation targetLocation;
    static boolean testing = true;// this checks if we already set our testnig target location
    static boolean scouting = false;
    static int robotId;
    static Direction oneLine;
    static RobotInfo[] enemies;
    public static void run(RobotController rc) throws GameActionException {
        robotId = rc.getID();
        if(turnCount == 1) {
            if (robotId % 10 == 2 || robotId % 10 == 1) {// this just randomly determiens if they should scout, we should make a better system
                rc.setIndicatorString("Scouting");
                scouting = true;
                oneLine = directions[rng.nextInt(directions.length)];// random search taken from miner bot

            }

        }
        if (scouting) {
            rc.setIndicatorString("Scouting");
            Direction dir2 = directions[rng.nextInt(directions.length)];
            if (rc.canMove(oneLine)) { // Move in a straight line determined earlier
                rc.move(oneLine);
                System.out.println("I moved!");
            } else if (rc.canMove(dir2)) { // Most likely hit a wall, set new oneLine and move there
                rc.move(dir2);
                oneLine = dir2;
                System.out.println("I moved in the backup location");
            }
        }
        enemies = rc.senseNearbyRobots(20, rc.getTeam().opponent());
        if(enemies.length > 0 && scouting){
            //time to generate combat report if we are a scout,
            // we should probably make sure that robots in an already known combat don't report new combats
            for(int i = 0; i<9; i++){// look for open combat report
                int a = rc.readSharedArray(i);
                if(a == 0){// unused spot
                    targetLocation = rc.getLocation();
                    rc.setIndicatorString("Writing combat report...");

                    String l = String.valueOf(enemies.length);
                    String x = String.valueOf(targetLocation.x);
                    String y = String.valueOf(targetLocation.y);

                    String write = l + x + y;
                    rc.writeSharedArray(i,Integer.valueOf(write));
                    break;
                }
            }
        }
        // generate combat report


        if(!scouting) {
            for (int i = 0; i < 9; i++) {// look for open combat report
                int a = rc.readSharedArray(i);
                if (a != 0) {// found a combat report!
                    int b = rc.readSharedArray(i);
                    int newy = b%100;//grabbing values
                    int newx = (b%1000-b%100)/100;
                    targetLocation = new MapLocation(newx,newy);
                    rc.setIndicatorString("going to location " + newx + " , " + newy);
                    break;
                }
            }

            rc.move(rc.getLocation().directionTo(targetLocation));

        }
    }
}
