package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.Intent;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutPresenterImpl implements WorkoutPresenter{

    private WorkoutView mView;
    private LocalDataWrapper mDataWrapper;


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
        //    public HistoryItem(int id, long date, int workoutId, int duration, int activeTime, int difficulty) {
        HistoryItem h = new HistoryItem(0, millis, id, totalDuration, activeTime, -1);
        mDataWrapper.recordHistory(h);
    }

    @Override
    public void startWorkout(Workout w) {

    }

    @Override
    public void processBroadcasts(Intent i) {
            if (i.getExtras() != null) {
                if (i.getExtras().size() > 2){
                    String currentStatus = i.getStringExtra(ServiceContract.CURRENT_STATUS);
                    if (currentStatus == null) {
                        currentStatus = "n/a";
                    }
                    int currentRound = i.getIntExtra(ServiceContract.CURRENT_ROUND, 0);
                    int timeElapsed = i.getIntExtra(ServiceContract.TOTAL_TIME_ELAPSED, 0);
                    int stepTimeRemaining = i.getIntExtra(ServiceContract.STEP_TIME_REMAINING, 0);
                    mView.onStatusChanged(currentStatus);
                    mView.onTimeChanged(Integer.toString(timeElapsed), Integer.toString(stepTimeRemaining));
                    mView.onRoundChanged(Integer.toString(currentRound));
                }else{
                    boolean isComplete = i.getBooleanExtra(ServiceContract.WORKOUT_COMPLETE, false);
                    if (isComplete){
                        int wid = i.getIntExtra(ServiceContract.WORKOUT_ID, -1);
                        int activeTime = i.getIntExtra(ServiceContract.COMPLETED_ACTIVE_TIME, -1);
                        int totalDuration = i.getIntExtra(ServiceContract.TOTAL_DURATION, -1);

                        saveWorkoutInfo(wid, activeTime, totalDuration);
                    }else{
                        int timeElapsed = i.getIntExtra(ServiceContract.TOTAL_TIME_ELAPSED, 0);
                        int stepTimeRemaining = i.getIntExtra(ServiceContract.STEP_TIME_REMAINING, 0);
                        mView.onTimeChanged(Integer.toString(timeElapsed), Integer.toString(stepTimeRemaining));
                    }

                }





                //updateUI(currentStatus, timeRemaining, timeElapsed, currentRound);

                //mTimeElapsedField.setText("Time elapsed : "+ timeRemaining);
            }
    }
}
