package player;
import battlecode.common.*;


public class Miner extends RobotPlayer{
    static MapLocation orgin;
    static MapLocation leadSource = null;
    static MapLocation[]leadSources = null;
    static boolean foundLead = false;
    public static void run(RobotController rc) throws GameActionException {

        if(!foundLead){
            leadSources = rc.senseNearbyLocationsWithLead(20);
            foundLead = true;
            leadSource = leadSources[0];
        }
        //if(LeadSources.size < 0){
         //   foundLead = false;
        //}

        while (rc.canMineLead(leadSource)) {
            rc.mineLead(leadSource);
            System.out.println("MINE");
        }

        rc.move(rc.getLocation().directionTo(leadSource));
        }// move towards known leadsource
}
