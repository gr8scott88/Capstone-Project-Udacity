package capstone.nanodegree.nemesisdev.com.hiitit.utils;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutTimer;

/**
 * Created by Scott on 3/30/2015.
 */
public class WorkoutStateManager {

    //private enum STATUS {READY, SET, GO, PREPARING, RESTING, WORKING, COOLDOWN, PAUSED, COMPLETE};
    private WorkoutTimer.STATUS mStatus = WorkoutTimer.STATUS.READY;
    private boolean bStatusChanged = true;

    private int mCurrentRound = 0;
    private int mRestTime;
    private int mActiveTime;
    private int mPrepareTime;
    private int mTotalRounds;
    private int mCooldownTime;
    private int mTotalWorkoutTime;
    private int mTimeElapsed = 0;
    private int mNextStatusChange = 1;
    private int mLastStatusChange = 0;

    public WorkoutStateManager(Workout workout) {
        processWorkout(workout);
    }

    private void processWorkout(Workout w){
        mRestTime = w.getRestTime();
        mActiveTime = w.getActiveTime();
        mPrepareTime = 10;
        mTotalRounds = w.getRounds();
        mCooldownTime = 10;
        mTotalWorkoutTime = w.getTotalWorkoutTime();

    }


    public WorkoutTimer.STATUS getStatus(){
        return mStatus;
    }

    public String getStatusAsString(){
        return mStatus.toString();
    }

    public boolean getStatusChanged(){
        return bStatusChanged;
    }

    public void resetChangedStatus(){
        bStatusChanged = false;
    }

    public int getCurrentRound(){
        return mCurrentRound;
    }


    public void updateCurrentTime(int timeFromService){
        mTimeElapsed = timeFromService;
        manageState();
    }

    private void notifyStatusChanged(){
        bStatusChanged = true;
        mLastStatusChange = mTimeElapsed;
    }

    public void manageState(){
        if ((mTimeElapsed-mLastStatusChange) > mNextStatusChange){
            switch(mStatus){
                case READY:
                    mStatus = WorkoutTimer.STATUS.SET;
                    notifyStatusChanged();
                    mNextStatusChange = 1;
                    break;

                case SET:
                    mStatus = WorkoutTimer.STATUS.GO;
                    notifyStatusChanged();
                    mNextStatusChange = 1;
                    break;

                case GO:
                    mStatus = WorkoutTimer.STATUS.PREPARING;
                    notifyStatusChanged();
                    mNextStatusChange = mPrepareTime;
                    break;

                case PREPARING:
                    mStatus = WorkoutTimer.STATUS.WORKING;
                    notifyStatusChanged();
                    mNextStatusChange = mActiveTime;
                    mCurrentRound++;
                    break;

                case WORKING:
                    if (mCurrentRound >= mTotalRounds){
                        mStatus = WorkoutTimer.STATUS.COOLDOWN;
                        notifyStatusChanged();
                        mNextStatusChange = mCooldownTime;
                    }else{
                        mStatus = WorkoutTimer.STATUS.RESTING;
                        notifyStatusChanged();
                        mNextStatusChange = mRestTime;
                    }
                    break;

                case RESTING:
                    mStatus = WorkoutTimer.STATUS.WORKING;
                    mCurrentRound++;
                    notifyStatusChanged();
                    mNextStatusChange = mActiveTime;
                    break;

                case COOLDOWN:
                    mStatus = WorkoutTimer.STATUS.COMPLETE;
                    notifyStatusChanged();
                    mNextStatusChange = 5;
                    break;

                case COMPLETE:
                    notifyStatusChanged();
                    mStatus = WorkoutTimer.STATUS.KILL;
                    mNextStatusChange = 10;
                    //TODO need to kill service
                    break;

            }
        }else{
            //Continue with the current status
        }
    }

}
