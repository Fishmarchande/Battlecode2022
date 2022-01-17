package player;
import battlecode.common.*;
import java.util.Random;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer

{

    static int turnCount = 0;
    static RobotPlayer rc;

    /** Array containing all the possible movement directions. */
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


    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        while (true) {
            turnCount += 1;
            try {
                switch (rc.getType()) {
                    case ARCHON:     Archon.run(rc);  break;
                    case MINER:      Miner.run(rc);   break;
                    case SOLDIER:    Soldier.run(rc); break;
                    case LABORATORY: Laboratory.run(rc); break;
                    case WATCHTOWER: Watchtower.run(rc); break;
                    case BUILDER:   Builder.run(rc); break;
                    case SAGE: Sage.run(rc); break;
                }
            } catch (GameActionException e) {


            } catch (Exception e) {

            } finally {
                Clock.yield();
            }
        }
    }
}
