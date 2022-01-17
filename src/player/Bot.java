package player;
import battlecode.common.*;

import java.util.Random;
import player.Misc.*;

public class Bot {


    static int turnCount = 0;
    public static RobotController rc;
    public static int turnCreated;
    public static Team friend;
    public static Team enemy;
    static int robotId;
    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };
    public static RobotInfo[] enemies;
    public static RobotInfo[] friends;
    public static int numfriends;
    public static int numenemies;
    public static RobotInfo closestEnemy;
    public static final Random rng = new Random();

    protected static void init(RobotController r ) throws GameActionException{
        turnCount = 0;
        rc = r;
        turnCreated = rc.getRoundNum();
        friend = rc.getTeam();
        enemy = rc.getTeam().opponent();
        robotId = rc.getID();

    }
    public static void updateInfo(){// update regularly use information
        turnCount++;
        enemies = rc.senseNearbyRobots(20, rc.getTeam().opponent());
        friends = rc.senseNearbyRobots(20, rc.getTeam());
        numfriends = friends.length;
        numenemies = enemies.length;

        int closestLoc = 1000;
        for(RobotInfo r:enemies){// FIND CLOSEST ENEMY
            if(rc.getLocation().distanceSquaredTo(r.getLocation()) > closestLoc){
                closestEnemy = r;
                closestLoc = rc.getLocation().distanceSquaredTo(r.getLocation());
            }
        }
        //
    }
    public static boolean tryAttack(MapLocation loc) throws GameActionException{
        if(rc.canAttack(loc)){
            rc.attack(loc);
            return true;
        }
        else{
            return false;
        }
    }
}
