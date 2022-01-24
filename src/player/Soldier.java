package player;
import battlecode.common.*;
import static player.communication.*; // IDK WHY THIS IS NECCESARY TODO FIX THIS
import static player.Misc.*;

public class Soldier extends Bot {
    static MapLocation targetLocation = null;
    static MapLocation reportLocation = null;
    static boolean testing = true;// this checks if we already set our testnig target location
    static boolean scouting = false;
    static Direction oneLine;
    static boolean followingReport = false;
    static boolean onSite = false;
    static int turnsWithoutCombat = 0;
    static int currentCr = -1;
    static int closestLoc;
    static boolean isScout;

    public static void run(RobotController rc) throws GameActionException {
        if(turnCount == 0){
            Bot.init(rc);
            isScout = (rng.nextInt(15) == 1);
        }
        Bot.updateInfo();




        robotId = rc.getID();
        if(turnCount == 1) {
            oneLine = directions[rng.nextInt(directions.length)];// random search taken from miner bot
        }




        if(enemies.length > 0){// generate combat report

            turnsWithoutCombat = 0;// WE ARE IN COMBAT, TIME TO MICRO!
            Micro.doMicro(); //



            //time to generate combat report
            // we should probably make sure that robots in an already known combat don't report new combats
            for(int i = 0; i<9; i++){// look for open combat report
                int a = rc.readSharedArray(i);

                if(a!=0){               //first lets make sure we aren't too close to any existing combat reports
                    if (rc.getLocation().isWithinDistanceSquared(getCombatReportLoc(a), 25)) { // within 5 tiles of another report?
                        System.out.println("Already a combat report here");
                        break;
                    }
                }
                else{
                    // unused spot, nothing nearby
                    reportLocation = rc.getLocation();
                    rc.setIndicatorString("Writing combat report...");
                    rc.writeSharedArray(i,writeCombatReport(reportLocation));
                    break;
                }
            }
        }

        if(!isScout) {// , look for combat and fight!

            if(!followingReport) { // if we haven't found combat yet, lets go to nearest report
                for (int i = 0; i < 9; i++) {// look for open combat report
                    closestLoc = 1000000; // Closest known location (arbitarily high value given), we go to closest
                    int a = rc.readSharedArray(i);
                    if (a != 0) {// found a combat report!
                        MapLocation l = getCombatReportLoc(a);
                        if (rc.getLocation().distanceSquaredTo(l) < closestLoc ){// only go if this is closest CR
                            closestLoc = rc.getLocation().distanceSquaredTo(l);
                            targetLocation = getCombatReportLoc(a);
                            rc.setIndicatorString("going to location" + String.valueOf(a));
                            followingReport = true;
                            currentCr = i; // keep track of our combat report
                        }

                        targetLocation = getCombatReportLoc(a);
                        rc.setIndicatorString("going to location" + String.valueOf(a));
                        followingReport = true;
                        currentCr = i; // keep track of our combat report
                    }
                }
            }
            if(targetLocation!= null) {
                rc.setIndicatorString(("Moving to site ") + (targetLocation));
                if (rc.canMove(rc.getLocation().directionTo(targetLocation))) {
                    rc.move(rc.getLocation().directionTo(targetLocation));
                } else {
                    if (rc.canMove(rc.getLocation().directionTo(targetLocation).rotateRight())) {

                        rc.move(rc.getLocation().directionTo(targetLocation).rotateRight());
                    }
                }
            }
            if(followingReport) {
                if (rc.getLocation().isWithinDistanceSquared(targetLocation, 9)) { // if we are already at a
                    rc.setIndicatorString("Already on site");                                                            // combat site, don't look for another one
                    onSite = true;
                }
            }
        }

        if(onSite){
            rc.setIndicatorString("ON SITE");// we are at a location where combat has been reported, fight for x turns,
                    // and if we find nothing then destroy current report and move on
            if(enemies.length > 0){

            }
            else{
                turnsWithoutCombat ++;
            }
            if(turnsWithoutCombat>5){
                onSite = false;
                rc.writeSharedArray(currentCr,0);
                followingReport = false;
                rc.setIndicatorString("No more enemies it appears");
                targetLocation = null;
            }
            //we need to randomly wander probably

        }
        if (!onSite && enemies.length == 0 && !followingReport) {
            doWander(oneLine);
        }
    }
}
