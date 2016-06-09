package capstone.nanodegree.nemesisdev.com.hiitit.utils;

/**
 * Created by Scott on 5/11/2016.
 */



public class TimeManager {

    private static int cSecInMin = 60;


    private TimeManager(){

    }

    public static String secondsToMinSec(int seconds){

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

    public static int minAndSecToSeconds(int min, int sec){
        return 60*min + sec;
    }


    public static int minSecToSeconds(String minsec){int mins = 0;
        int secs = 0;
        try {
            String[] minSecStrings = minsec.split(":");
            if (minSecStrings.length == 1){
                mins = 0;
                secs = Integer.parseInt(minSecStrings[0]);
            }else{
                mins = Integer.parseInt(minSecStrings[0]);
                secs = Integer.parseInt(minSecStrings[1]);

            }
            return minAndSecToSeconds(mins, secs);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1000;
    }

    public static String calcWorkoutTimeString(int activeTime, int restTime, int rounds){
        if (activeTime <= 0 || restTime <= 0 || rounds <= 0){
            return "Invalid Entry";
        }
        int secs = calcWorkoutTimeSec(activeTime, restTime, rounds);
        return secondsToMinSec(secs);
    }

    public static int calcWorkoutTimeSec(int activeTime, int restTime, int rounds){
        return (activeTime+restTime) * rounds - restTime;
    }

    public static String calcWorkoutTimeString(String activeTime, String restTime, String roundString){
        int activeSecs = 0;
        int restSecs = 0;
        int rounds = 0;

        try{
             activeSecs = Integer.parseInt(activeTime);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            restSecs = Integer.parseInt(restTime);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            rounds = Integer.parseInt(roundString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return calcWorkoutTimeString(activeSecs, restSecs, rounds);
    }

}
