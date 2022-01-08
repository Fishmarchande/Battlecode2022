package player;
import battlecode.common.*;

public class Soldier extends RobotPlayer {
    static MapLocation targetLocation;
    static boolean testing = true;// this checks if we already set our testnig target location

    public static void run(RobotController rc) throws GameActionException {

        if(testing){
            targetLocation = new MapLocation(rc.getLocation().x - 15, rc.getLocation().y+10);
            rc.setIndicatorString("Picked Location to go to, setting broadcast...");

            String x = String.valueOf(targetLocation.x);
            String y = String.valueOf(targetLocation.y);
            String write = "0" + x + y;
                    rc.setIndicatorString("Picked   location "+ write+ " , seting broadcast...");
            rc.writeSharedArray(0,Integer.valueOf(write));
            testing = false;
        }
        rc.move(rc.getLocation().directionTo(targetLocation));


    }
}
