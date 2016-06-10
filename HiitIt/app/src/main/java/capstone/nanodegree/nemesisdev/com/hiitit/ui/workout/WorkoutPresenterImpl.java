package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

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
    public Workout loadWorkout(int workoutId) {
        return mDataWrapper.getWorkoutById(workoutId);
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void saveWorkoutInfo() {

    }

    @Override
    public void startWorkout(Workout w) {

    }
}
