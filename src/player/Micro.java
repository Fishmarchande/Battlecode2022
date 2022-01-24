package player;
import battlecode.common.*;
import battlecode.common.GameActionException;
import player.Misc.*;

public class Micro extends Bot {

    public static void doMicro() throws GameActionException {
        System.out.println();
        Direction randomDir = directions[rng.nextInt(directions.length)];// random search taken from miner bot


        //move closer towards first enemy we see

        rc.setIndicatorString(numHostileEnemies+" enemies, friends:" + numHostileFriends);

        if(numHostileEnemies<(numHostileFriends+1)){            // more allies than enemies, we can attack
            rc.setIndicatorString("AAH");
            Bot.tryAttack(closestTarget.getLocation());
            Bot.tryAttack(enemies[0].getLocation());
            rc.setIndicatorString(enemies[0].getLocation().toString());

            if(Misc.tryMove(rc.getLocation().directionTo(closestTarget.getLocation()),true)); //move towards cloesst one


        }
        else{ // retreat!
            retreat();
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
