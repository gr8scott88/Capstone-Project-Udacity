package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface WorkoutPresenter {

    Workout loadWorkout(int workoutId);
    void tick();
    void saveWorkoutInfo();
    void startWorkout(Workout w);



}
