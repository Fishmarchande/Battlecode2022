package player;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;

public class Micro extends Bot {
    public static int retreatTimes = 0;
    public static void doMicro() throws GameActionException {
        System.out.println();
        // random search taken from miner bot


        //move closer towards first enemy we see

        rc.setIndicatorString(numHostileEnemies+" enemies, friends:" + numHostileFriends);

        if(numHostileEnemies<(numHostileFriends+1)){            // more allies than enemies, we can attack
            rc.setIndicatorString("AAH");
            Bot.tryAttack(closestTarget.getLocation());
            Bot.tryAttack(enemies[0].getLocation());
            rc.setIndicatorString(enemies[0].getLocation().toString());
            retreatTimes = 0;
            Misc.tryMove(rc.getLocation().directionTo(closestTarget.getLocation()), true);//move towards the closest one


        }
        else if (retreatTimes < 3){ // retreat!
            retreat();
        } else {
            retreatTimes = 0;
            Bot.tryAttack(closestTarget.getLocation());
        }



    }
    public static void retreat() throws GameActionException {
        for (Direction d:directions){// look in each adjacent location
            MapLocation l = rc.adjacentLocation(d);
            if(!l.isWithinDistanceSquared(closestEnemy.getLocation(), 20)){//if the location is within shooting distance of an enemy, we can't retreat there
                //we can retreat here
                Misc.tryMove(d);
                return;
            }
        }
        //if we reached here, it means there is no place to retreat to
        //we are surrounded!
        Bot.tryAttack(closestTarget.getLocation());


    }
}
