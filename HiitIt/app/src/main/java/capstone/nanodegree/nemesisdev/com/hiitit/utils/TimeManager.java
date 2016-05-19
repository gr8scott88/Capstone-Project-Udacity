package capstone.nanodegree.nemesisdev.com.hiitit.utils;

/**
 * Created by Scott on 5/11/2016.
 */



public class TimeManager {

    private static int cSecInMin = 60;

    public String secondsToMinSec(int seconds){

        int mins = (int)seconds/cSecInMin;
        int secs = seconds - mins*cSecInMin;

        String minString = "00";
        String secString = "00";

        if (mins >=10){
            minString = Integer.toString(mins);
        }else{
            minString = "0"+Integer.toString(mins);
        }

        if (secs >=10){
            secString = Integer.toString(secs);
        }else{
            secString = "0"+Integer.toString(secs);
        }

        return minString+":"+secString;
    }





}
