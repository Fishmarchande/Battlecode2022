package player;
import battlecode.common.*;


public class Archon extends Bot{
    static int minerCount = 0;
    static boolean panic = false;
    static boolean needHelp = false;
    static boolean stopProd = false;
    static int presumeDead = 0;
    static int totalMiners = 4;
    public static void run(RobotController rc) throws GameActionException {
        /*
        TO DO: new variable of int initially set to 0
        After building a bot (unless panic) add the number of (archons -1) to it
        Only build bots (unless panic) when the variable equals 0
        Decrease the variable by one (if not 0) towards the end
         */
        totalMiners = 5 * rc.getArchonCount();
        int a = rc.readSharedArray(0);
        if(a != 0){
            System.out.println(a);
        }
        int b = rc.readSharedArray(10);
        stopProd = b != 0 && !panic;
        if (stopProd) {
            presumeDead++;
            System.out.println("Stopping production");
        }
        if (presumeDead > 30) {
            presumeDead = 0;
            stopProd = false;
            rc.writeSharedArray(10, 0); //Presume attacked bot is killed
            System.out.println("Defense of archon failed; resuming production");
        }
        Direction dir = directions[rng.nextInt(directions.length)];
            if(rc.canBuildRobot(RobotType.SOLDIER, dir) && panic) {
                rc.buildRobot(RobotType.SOLDIER, dir);
                System.out.println("Panic build active");
            }
            // Let's try to build a miner.
            rc.setIndicatorString("Trying to build a miner");
            if (rc.canBuildRobot(RobotType.MINER, dir) && minerCount < totalMiners && !stopProd) {
                rc.buildRobot(RobotType.MINER, dir);
                minerCount ++;
            }
            if( minerCount > 3 && rc.canBuildRobot(RobotType.SOLDIER, dir) && !stopProd){
                rc.setIndicatorString("Building a soldier");
                rc.buildRobot(RobotType.SOLDIER, dir);
            }
        panic = rc.senseNearbyRobots(-1, rc.getTeam().opponent()).length >= rc.senseNearbyRobots(-1, rc.getTeam()).length; // TO DO: Turn this into a battle report and call reinforcements to defend and workers to repair
        if (panic) {
            rc.writeSharedArray(10,10+rc.getID());
            needHelp = true;
            System.out.println("HELP!");
        } else if (needHelp) {
            needHelp = false;
            rc.writeSharedArray(10, 0);
            System.out.println("Reset panic");
        }
    }
}
