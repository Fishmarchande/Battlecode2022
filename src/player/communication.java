package player;
import battlecode.common.*;
public class communication extends RobotPlayer{
    public static int writeCombatReport(MapLocation loc){// writing combat report
        String x = String.valueOf(loc.x);
        if(x.length()== 1){
            x = "0" + x;
        }

        String y = String.valueOf(loc.y);
        if(y.length() == 1){
            y = "0" + y;
        }

        String write = "0" + x + y;
        return Integer.valueOf(write);

    }
    public static MapLocation getCombatReportLoc(int report){

        int newy = report % 100;//grabbing values
        int newx = (report % 10000 - report % 100) / 100;

        return new MapLocation(newx, newy);
    }

        /*public int writeCombatReport ( int enemyCount, MapLocation loc){
            String l = String.valueOf(enemyCount);
            String x = String.valueOf(loc.x);
            if(x.length()== 1){
                x = "0" + x;
            }

            String y = String.valueOf(loc.y);
            if(y.length() == 1){
                y = "0" + y;
            }

            String write = "0" + x + y;
            return Integer.valueOf(write);
        }*/
}
