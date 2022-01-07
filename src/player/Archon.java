package player;
import battlecode.common.*;


public class Archon extends RobotPlayer{
    public static void run(RobotController rc) throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
            // Let's try to build a miner.
            rc.setIndicatorString("Trying to build a miner");
            if (rc.canBuildRobot(RobotType.MINER, dir)) {
                rc.buildRobot(RobotType.MINER, dir);
            }
    }
}
