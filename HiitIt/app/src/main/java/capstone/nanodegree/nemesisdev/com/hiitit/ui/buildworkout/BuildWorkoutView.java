package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface BuildWorkoutView {

    void updateTotalTime(String newTime);

    void startWorkout(int id);


    void startWorkout(Workout w);

    void onStartWorkoutClicked();

    void showMessage(String s);

}
