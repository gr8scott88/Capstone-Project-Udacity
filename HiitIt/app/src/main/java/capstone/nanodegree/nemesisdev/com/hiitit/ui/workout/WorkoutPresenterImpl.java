package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.Intent;
import android.util.Log;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutPresenterImpl implements WorkoutPresenter{
    private static final String TAG = "WORKOUTPRESENTER";
    private WorkoutView mView;
    private LocalDataWrapper mDataWrapper;
    private WorkoutTimer2 mWorkoutTimer;
    private boolean bWorkoutComplete = false;
    private boolean bIsPaused = false;
    private boolean bIsStarted = false;

    public WorkoutPresenterImpl(WorkoutView view, LocalDataWrapper dataWrapper) {
        mView = view;
        mDataWrapper = dataWrapper;
    }

    @Override
    public Workout loadWorkout(int workoutId) {
        return mDataWrapper.getWorkoutById(workoutId);
    }

    @Override
    public void tick() {
        
    }



    @Override
    public void saveWorkoutInfo(int id, int activeTime, int totalDuration) {
        long millis = System.currentTimeMillis() % 1000;
        Log.v(TAG, "Current sytem time in millin: " + millis);
        //    public HistoryItem(int id, long date, int workoutId, int duration, int activeTime, int difficulty) {
        HistoryItem h = new HistoryItem(0, millis, id, totalDuration, activeTime, -1);
        Log.v(TAG, "Saving History Item: " + h.toString());
        mDataWrapper.recordHistory(h);
    }

    @Override
    public void saveWorkoutInfo(HistoryItem h) {
        Log.v(TAG, "Saving History Item: " + h.toString());
        mDataWrapper.recordHistory(h);
    }

    @Override
    public void attachTimer(WorkoutTimer2 timer, Workout workout) {
        mWorkoutTimer = timer;
        mWorkoutTimer.setupService(workout);

    }

    @Override
    public void processBroadcasts(Intent i) {
        if (!bWorkoutComplete){
            if (i.getExtras() != null) {
                boolean isComplete = i.getBooleanExtra(ServiceContract.WORKOUT_COMPLETE, false);
                if (isComplete){
                    bWorkoutComplete = true;
                    mWorkoutTimer.stopTimer();
                    Log.v(TAG, "WORKOUT_COMPLETE!!!!");
                    //int wid = i.getIntExtra(ServiceContract.WORKOUT_ID, -1);
                    //int activeTime = i.getIntExtra(ServiceContract.COMPLETED_ACTIVE_TIME, -1);
                    //int totalDuration = i.getIntExtra(ServiceContract.TOTAL_DURATION, -1);
                    //saveWorkoutInfo(wid, activeTime, totalDuration);
                    saveWorkoutInfo(mWorkoutTimer.getCompeletedHistoryItem());
                    mView.showComplete();

                }else{
                    int currentRound = i.getIntExtra(ServiceContract.CURRENT_ROUND, -1);
                    if (currentRound == -1){
                        int timeElapsed = i.getIntExtra(ServiceContract.TOTAL_TIME_ELAPSED, 0);
                        int stepTimeRemaining = i.getIntExtra(ServiceContract.STEP_TIME_REMAINING, 0);
                        mView.onTimeChanged(Integer.toString(timeElapsed), Integer.toString(stepTimeRemaining));
                    }else{
                        String currentStatus = i.getStringExtra(ServiceContract.CURRENT_STATUS);
                        if (currentStatus == null) {
                            currentStatus = "n/a";
                        }

                        int timeElapsed = i.getIntExtra(ServiceContract.TOTAL_TIME_ELAPSED, 0);
                        int stepTimeRemaining = i.getIntExtra(ServiceContract.STEP_TIME_REMAINING, 0);
                        mView.onStatusChanged(currentStatus);
                        mView.onTimeChanged(Integer.toString(timeElapsed), Integer.toString(stepTimeRemaining));
                        mView.onRoundChanged(Integer.toString(currentRound));
                    }
                }
            }
        }else{
            Log.v(TAG, "Stopping timer");
            mWorkoutTimer.stopTimer();
        }

    }

    @Override
    public void workoutEnded() {
        bWorkoutComplete = true;
        saveWorkoutInfo(mWorkoutTimer.getCompeletedHistoryItem());
        mView.showComplete();
    }

    @Override
    public void startTimer() {
        mWorkoutTimer.startTimer();
    }

    @Override
    public void stopTimer() {
        mWorkoutTimer.stopTimer();
    }

    @Override
    public void pauseWorkout() {
        mWorkoutTimer.pauseTimer();
        mView.pauseWorkout();
        bIsPaused = true;
    }

    @Override
    public void startWorkout() {
        bIsStarted = true;
        mView.startWorkout();
        startTimer();
    }

    @Override
    public void unPauseWorkout() {
        bIsPaused = false;
        mView.resumeWorkout();
        startTimer();
    }

    @Override
    public void unBindTimer() {
        mWorkoutTimer.UnBind();
    }

    @Override
    public void mainWindowClicked() {
        if (bWorkoutComplete){
            mView.endWorkout();
        }else if (!bIsStarted){
            startWorkout();
        }else if (bIsPaused){
            unPauseWorkout();
        }else{
            pauseWorkout();
        }
    }
}
