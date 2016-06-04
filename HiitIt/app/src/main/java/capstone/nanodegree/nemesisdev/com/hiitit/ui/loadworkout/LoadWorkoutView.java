package capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface LoadWorkoutView {

    void onWorkoutsLoaded(List<Workout> workouts);

}
