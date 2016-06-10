package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import android.content.Context;
import android.content.Intent;

import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

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
        mView.startWorkout(w);
    }


    @Override
    public boolean saveWorkout(Workout w) {
        mDataWrapper.saveWorkout(w);
        return false;
    }

    @Override
    public boolean verifyInput(String name, String activeTime, String breakTime, String rounds) {
        if (name.length() == 0){
            mView.showMessage("Invalid Name");
            return false;
        }

        try{
            int active = Integer.parseInt(activeTime);
            if (active <= 0){
                mView.showMessage("Active time must be greater than zero");
                return false;
            }
        }catch (Exception e){
            mView.showMessage("Invalid Active Time");
            e.printStackTrace();
            return false;
        }

        try{
            int breakT = Integer.parseInt(breakTime);
            if (breakT <= 0){
                mView.showMessage("Break Time must be greater than zero");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            mView.showMessage("Invalid Break Time");
            return false;
        }

        try{
            int roundCt = Integer.parseInt(rounds);
            if (roundCt <= 0){
                mView.showMessage("Rounds must be greater than zero");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            mView.showMessage("Invalid Rounds");
            return false;
        }


        //TODO: Validate name is unique in table


        return true;
    }
}
