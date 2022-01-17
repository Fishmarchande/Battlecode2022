package player;
import battlecode.common.*;
import battlecode.common.GameActionException;
import player.Misc.*;

public class Micro extends Bot {

    public static void doMicro() throws GameActionException {
        System.out.println();
        Direction randomDir = directions[rng.nextInt(directions.length)];// random search taken from miner bot




        //move closer towards first enemy we see

        if(numfriends>numenemies){            // more allies than enemies, we can attack

            Bot.tryAttack(enemies[0].getLocation());


            if(Misc.tryMove(rc.getLocation().directionTo(enemies[0].getLocation()))){//move towards cloesst one

            }
            else{
                rc.move(randomDir);//if we can't move towards said enemy, move randomly (lets improve this)
            }
        }
        else{ // retreat!
            Misc.tryMove(randomDir);
        }



    }
}
