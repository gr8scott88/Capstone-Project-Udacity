package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public interface BuildWorkoutPresenter {

    int onWorkoutChanged(int activeTime, int breakTime, int rounds);

    void onStartClicked(Workout w);



}
