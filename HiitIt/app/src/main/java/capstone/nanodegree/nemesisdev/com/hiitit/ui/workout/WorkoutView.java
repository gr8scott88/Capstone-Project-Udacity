package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface WorkoutView {

    void populateWorkoutDetails();
    void onTimeChanged(String timeElapsed, String stepTime);
    void onRoundChanged(String round);
    void onStatusChanged(String status);
    void onEndWorkoutClicked();
    void showComplete();
    void showMessage(String msg);
    void startWorkout();
    void pauseWorkout();
    void resumeWorkout();
    void endWorkout();

}
