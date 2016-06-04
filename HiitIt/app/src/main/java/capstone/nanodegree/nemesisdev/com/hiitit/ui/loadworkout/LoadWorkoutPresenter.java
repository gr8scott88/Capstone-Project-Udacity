package capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout;

import android.content.ContentResolver;

/**
 * Created by Scott on 5/7/2016.
 */
public interface LoadWorkoutPresenter {

    void onWorkoutSelected(int workoutId);

    void loadWorkouts();

}
