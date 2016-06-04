package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout.LoadWorkoutView;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutPresenterImpl implements WorkoutPresenter{

    private WorkoutView mView;
    private LocalDataWrapper mDataWrapper;


    public WorkoutPresenterImpl(WorkoutView view, LocalDataWrapper dataWrapper) {
        mView = view;
        mDataWrapper = dataWrapper;
    }

    @Override
    public void loadWorkout(int workoutId) {
        mView.onWorkoutLoaded(mDataWrapper.getWorkoutById(workoutId));
    }

    @Override
    public void tick() {
        
    }
}
