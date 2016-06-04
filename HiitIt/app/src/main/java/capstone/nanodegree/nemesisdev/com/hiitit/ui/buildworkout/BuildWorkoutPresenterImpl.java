package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.main.MainMenuView;

/**
 * Created by Scott on 5/7/2016.
 */
public class BuildWorkoutPresenterImpl implements BuildWorkoutPresenter {

    private BuildWorkoutView mView;
    private LocalDataWrapper mDataWrapper;


    public BuildWorkoutPresenterImpl(BuildWorkoutView view, LocalDataWrapper dataWrapper) {
        mView = view;
        mDataWrapper = dataWrapper;
    }



    @Override
    public int onWorkoutChanged(int activeTime, int breakTime, int rounds) {



        return 12;
    }

    @Override
    public void onStartClicked(Workout w) {
        mDataWrapper.saveWorkout(w);
        mView.onWorkoutStart();
    }
}
