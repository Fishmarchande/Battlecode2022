package player;
import battlecode.common.*;


public class Archon extends RobotPlayer{
    static int minerCount = 0;
    public static void run(RobotController rc) throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
            // Let's try to build a miner.
            rc.setIndicatorString("Trying to build a miner");
            if (rc.canBuildRobot(RobotType.MINER, dir) && minerCount <4) {
                rc.buildRobot(RobotType.MINER, dir);
                minerCount ++;
            }
            if( minerCount > 3 && rc.canBuildRobot(RobotType.SOLDIER, dir)){
                rc.setIndicatorString("Building a soldier");
                rc.buildRobot(RobotType.SOLDIER, dir);
            }
    }
}
