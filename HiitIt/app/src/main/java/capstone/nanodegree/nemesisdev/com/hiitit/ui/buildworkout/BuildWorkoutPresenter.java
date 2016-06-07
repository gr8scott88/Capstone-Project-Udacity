package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import android.content.Context;
import android.widget.Toast;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface BuildWorkoutPresenter {

    int onWorkoutChanged(int activeTime, int breakTime, int rounds);

    void onStartClicked(Workout w);

    boolean saveWorkout(Workout w);


    boolean verifyInput(String name, String activeTime, String breakTime, String rounds);




}
