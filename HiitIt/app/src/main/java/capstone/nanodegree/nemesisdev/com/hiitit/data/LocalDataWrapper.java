package capstone.nanodegree.nemesisdev.com.hiitit.data;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import rx.Observable;

/**
 * Created by Scott on 5/16/2016.
 */
public class LocalDataWrapper implements DataWrapper {

    WorkoutDbHelper mHelper;


    public LocalDataWrapper(WorkoutDbHelper helper) {
        mHelper = helper;
    }

    @Override
    public Observable<List<HistoryItem>> getHistory() {
        return null;
    }

    @Override
    public Observable<List<Workout>> getWorkouts() {
        return null;
    }

    @Override
    public void saveWorkout(Workout w) {

    }

    @Override
    public Workout getMostRecent() {
        return null;
    }
}
