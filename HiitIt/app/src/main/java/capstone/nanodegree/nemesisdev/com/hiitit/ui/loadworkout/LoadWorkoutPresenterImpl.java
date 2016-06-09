package capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.WorkoutDbHelper;

/**
 * Created by Scott on 5/7/2016.
 */


public class LoadWorkoutPresenterImpl implements LoadWorkoutPresenter {

    private static final String TAG = "LoadWorkoutPresenter";
    private LoadWorkoutView mView;
    private LocalDataWrapper mDataWrapper;


    public LoadWorkoutPresenterImpl(LoadWorkoutView view, LocalDataWrapper dataWrapper) {
        mView = view;
        mDataWrapper = dataWrapper;
    }


    @Override
    public void onWorkoutSelected(int workoutId) {

    }


    @Override
    public void loadWorkouts() {
        mView.onWorkoutsLoaded(mDataWrapper.getWorkouts());

    }

    @Override
    public void deleteWorkout(int workoutId) {
        int rowsDeleted = mDataWrapper.deleteWorkout(workoutId);
        if (rowsDeleted == 0){
            mView.showMessage("Failed to delete workout");
        }
    }
}



