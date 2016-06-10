package capstone.nanodegree.nemesisdev.com.hiitit.ui.main;

import capstone.nanodegree.nemesisdev.com.hiitit.data.DataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/3/2016.
 */
public class MainMenuPresenterImpl implements MainMenuPresenter{

    private MainMenuView mView;
    private DataWrapper mDataWrapper;

    public MainMenuPresenterImpl(MainMenuView view, DataWrapper dataWrapper) {
        mView = view;
        mDataWrapper = dataWrapper;
    }

    @Override
    public void onMenuItemSelected(int id) {
        mView.openSelectedMenuActivity(id);
    }


    @Override
    public Workout loadMostRecentWorkout() {
        //return mDataWrapper.getMostRecentWorkoutId();

        //TODO

        return null;
    }

    @Override
    public int loadMostRecentWorkoutId() {
        return mDataWrapper.getMostRecentWorkoutId();
    }
}
