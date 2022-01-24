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
    public static RobotInfo closestTarget;
    public static int numfriends;
    public static int numenemies;
    public static RobotInfo closestEnemy;
    public static final Random rng = new Random();
    public static int numHostileEnemies;
    public static int numHostileFriends;
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
        int closestTargetLoc = 1000;
        numHostileEnemies = 0; // num of enemies that are dangeorus (not miners and archons)
        numHostileFriends = 0;
        for(RobotInfo r:enemies){// FIND CLOSEST ENEMY, num of dangerous enemies
            if(r.getType() == RobotType.SOLDIER ||r.getType() == RobotType.WATCHTOWER || r.getType() == RobotType.SAGE){
                numHostileEnemies ++;//increase by one if they are one of these types
                if(rc.getLocation().distanceSquaredTo(r.getLocation()) < closestTargetLoc ){//if they are also closest enemy, update it
                    closestTarget = r;
                    closestTargetLoc = rc.getLocation().distanceSquaredTo(r.getLocation());
                }
            }
        }
        for(RobotInfo r:friends){
            if(r.getType() == RobotType.SOLDIER ||r.getType() == RobotType.WATCHTOWER || r.getType() == RobotType.SAGE){
                numHostileEnemies++;
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
