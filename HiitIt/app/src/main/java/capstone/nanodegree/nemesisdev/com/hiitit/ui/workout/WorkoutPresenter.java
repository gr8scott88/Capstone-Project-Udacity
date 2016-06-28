package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.Intent;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface WorkoutPresenter {

    Workout loadWorkout(int workoutId);
    void tick();
    void saveWorkoutInfo(int id, int activeTime, int duration);

    void saveWorkoutInfo(HistoryItem historyItem);
    void attachTimer(WorkoutTimer2 timer, Workout w);
    void startTimer();
    void stopTimer();
    void unBindTimer();
    void processBroadcasts(Intent i);
    void startWorkout();
    void pauseWorkout();
    void unPauseWorkout();
    void workoutEnded();
    void mainWindowClicked();

}
