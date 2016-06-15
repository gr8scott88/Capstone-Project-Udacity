package capstone.nanodegree.nemesisdev.com.hiitit.utils;

import java.util.ArrayList;
import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 6/13/2016.
 */
public class WorkoutManager2 {
    public enum STATUS {
        READY ("READY"),
        SET ("SET"),
        GO ("GO"),
        PREPARING ("PREPARING"),
        RESTING ("RESTING"),
        WORKING ("WORKING"),
        COOLDOWN ("COOLDOWN"),
        PAUSED ("PAUSED"),
        COMPLETE ("COMPLETE"),
        KILL ("KILL");

        private final String name;

        private STATUS(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return (otherName == null) ? false : name.equals(otherName);
        }

        public String toString() {
            return this.name;
        }


    };

    private int mActiveTime = 0;
    private int mWorkoutId;

    private List<WorkoutEvent> mEvents;
    private int currentStep = 0;

    private class WorkoutEvent {

        private int mTime;
        private STATUS mStatus;
        private int mRound;
        private int mDuration;

        public WorkoutEvent(int time, STATUS  status, int round, int duration) {
            mTime = time;
            mStatus = status;
            mRound = round;
            mDuration = duration;
        }

        public int getTime() {
            return mTime;
        }

        public STATUS getStatus() {
            return mStatus;
        }

        public int getRound() {
            return mRound;
        }

        public int getDuration() {
            return mDuration;
        }

        @Override
        public String toString() {
            return "Time: " + mTime + ", Status: " + mStatus + ", Round: " + mRound + ", Duration: " + mDuration;
        }
    }


    public WorkoutManager2(Workout w) {
        mEvents = buildEventList(w);
        mWorkoutId = w.getId();
    }

    private List<WorkoutEvent> buildEventList(Workout w){
        int eventTime = 2;
        int rounds = w.getRounds();
        int activeTime = w.getActiveTime();
        int restTime = w.getRestTime();

        List<WorkoutEvent> events = new ArrayList<WorkoutEvent>();
        events.add(new WorkoutEvent(1, STATUS.READY, 0, 1));
        events.add(new WorkoutEvent(2, STATUS.SET, 0, 1));
        events.add(new WorkoutEvent(3, STATUS.GO, 1, activeTime));


        for (int r = 1; r < rounds; r++){
            eventTime = eventTime + activeTime;
            events.add(new WorkoutEvent(eventTime, STATUS.RESTING, r, restTime));
            eventTime = eventTime + restTime;
            if (r < rounds){
                events.add(new WorkoutEvent(eventTime, STATUS.GO, r+1, activeTime));
            }

        }
        eventTime = eventTime + activeTime;
        events.add(new WorkoutEvent(eventTime, STATUS.COMPLETE, rounds, 1));
        return events;

    }

    public int isStatusChange(int time){
        if (mEvents.get(currentStep).getStatus() == STATUS.GO){
            mActiveTime++;
        }

        if (time >= mEvents.get(currentStep).getTime() + mEvents.get(currentStep).getDuration()){
            if (mEvents.get(currentStep).getStatus() == STATUS.COMPLETE){
                return 2;
            }else{
                currentStep++;
                return 1;
            }

        }
        return  0;
    }

    public String getNewStatus(){
        return mEvents.get(currentStep).getStatus().toString();
    }

    public int getCurrentRound(){
        return mEvents.get(currentStep).getRound();
    }

    public int getNewDuration(){
        return mEvents.get(currentStep).getDuration();
    }

    @Override
    public String toString() {
        String s = "";
        for (WorkoutEvent e : mEvents){
            s = s + e.toString() + "\n";
        }

        return s;
    }

    public int getActiveTime() {
        return mActiveTime;
    }

    public  int getWorkoutId(){
        return mWorkoutId;
    }



}
