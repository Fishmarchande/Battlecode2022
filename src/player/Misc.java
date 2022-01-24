package player;
import battlecode.common.*;

public class Misc extends Bot {
    public static boolean tryMove(Direction dir) throws GameActionException {
        if(rc.canMove(dir)){
            rc.move(dir);
            return true;
        }
        else{
            return false;
        }

    }
    public static boolean tryMove(Direction dir, boolean turn) throws GameActionException {
        tryMove(dir);
        Direction newdir = dir.rotateRight();
        if (tryMove(newdir)){
        }
        else{
            for(int i = 0; i < 8; i++) {
                newdir = newdir.rotateRight();
                tryMove(newdir);
            }
        }
        //for(int i = 0; i < 8; i++) {//keep trying to follow the right of the object
        //    newdir = newdir.rotateRight();
        //    if(tryMove(dir)){
        //        return true;
        //    }
        //    rc.setIndicatorString("ROTATING");
        //}
        //rc.setIndicatorString("failed to rotate...");

        return false;
    }
    public static void doWander(Direction oneLine) throws GameActionException{

        rc.setIndicatorString("wandering");
        Direction dir2 = directions[rng.nextInt(directions.length)];


        if (tryMove(oneLine)) { // Move in a straight line determined earlier
            rc.setIndicatorString("Moved OneLine");
        }
        else if (tryMove(dir2)) { // Most likely hit a wall, set new oneLine and move there
            oneLine = dir2;
            rc.setIndicatorString("Moved Random");
        }
    }
}
