package player;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class pathFind {
    static void pathFind(RobotController rc, MapLocation destination) {
        Direction dirToDest = rc.getLocation().directionTo(destination); //Straight line direction to get what paths we want to explore
        Direction[] possiblePath = get180(dirToDest); //Get 180 of direction
    }
    static Direction[] get180(Direction dir) {
        Direction[] result = new Direction[]{dir, dir.rotateLeft(), dir.rotateLeft().rotateLeft(), dir.rotateRight(), dir.rotateRight().rotateRight()}; //Example: if dir == north then result = {north, northwest, west, northeast, east}
        return result;
    }
}
