package player;
import battlecode.common.*;


public class Archon extends RobotPlayer{
    static int minerCount = 0;
    static boolean panic = false;
    public static void run(RobotController rc) throws GameActionException {

        int a = rc.readSharedArray(0);
        if(a != 0){
            System.out.println(a);
        }

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
        panic = rc.senseNearbyRobots(-1, rc.getTeam().opponent()).length >= rc.senseNearbyRobots(-1, rc.getTeam()).length; // TO DO: Turn this into a battle report and call reinforcements to defend and workers to repair
    }
}
