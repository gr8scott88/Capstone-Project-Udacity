package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface WorkoutView {

    void populateWorkoutDetails();
    void pauseWorkout();
    void resumeWorkout();
    void endWorkout(HistoryItem h);
    void saveWorkoutDetails(HistoryItem h);

    void onTimeChanged(String time);
    void onRoundChanged(String round);
    void onStatusChanged(String status);
    void onWorkoutCompleted();
    void onEndWorkoutClicked();

    void goButtonPressed();
    void showMessage(String msg);

}
