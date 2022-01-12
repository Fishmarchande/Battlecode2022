package player;
import battlecode.common.*;

public class Misc extends RobotPlayer {
    public static boolean tryMove(RobotController rc, Direction dir) throws GameActionException {
        if(rc.canMove(dir)){
            rc.move(dir);
            return true;
        }
        else{
            return false;
        }

    }
    public static boolean tryAttack(RobotController rc, MapLocation loc) throws GameActionException{
        if(rc.canAttack(loc)){
            rc.attack(loc);
            return true;
        }
        else{
            return false;
        }
    }
    public static void doWander(RobotController rc, Direction oneLine) throws GameActionException{

        rc.setIndicatorString("wandering");
        Direction dir2 = directions[rng.nextInt(directions.length)];
        if (rc.canMove(oneLine)) { // Move in a straight line determined earlier
            rc.move(oneLine);
        } else if (rc.canMove(dir2)) { // Most likely hit a wall, set new oneLine and move there
            rc.move(dir2);
            oneLine = dir2;
        }
    }
}
