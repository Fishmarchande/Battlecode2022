package player;
import battlecode.common.*;
import battlecode.common.GameActionException;
import player.Misc.*;

public class Micro extends Bot {

    public static void doMicro() throws GameActionException {

        //move closer towards first enemy we see

        tryAttack(closestTarget.getLocation());
        tryAttack(enemies[0].getLocation());
        rc.setIndicatorString("REACHED");
        Misc.tryMove(rc.getLocation().directionTo(enemies[0].getLocation()));
       /* if(tryAttack(closestTarget.getLocation())){
            rc.setIndicatorString("Can attack");
        }
        else{
            tryAttack(enemies[0].getLocation());
            rc.setIndicatorString("Can't attack");
        }
        Misc.tryMove(rc.getLocation().directionTo(enemies[0].getLocation()), true);
        rc.setIndicatorString("move");
        */
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
        Bot.tryAttack(enemies[0].getLocation());

    }
}
