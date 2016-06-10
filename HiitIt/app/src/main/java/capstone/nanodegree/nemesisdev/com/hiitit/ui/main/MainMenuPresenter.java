package capstone.nanodegree.nemesisdev.com.hiitit.ui.main;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/3/2016.
 */
public interface MainMenuPresenter {

    void onMenuItemSelected(int id);

    Workout loadMostRecentWorkout();

    int loadMostRecentWorkoutId();

}
