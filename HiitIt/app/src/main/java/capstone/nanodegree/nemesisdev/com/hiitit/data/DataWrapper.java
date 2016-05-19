package capstone.nanodegree.nemesisdev.com.hiitit.data;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import rx.Observable;


/**
 * Created by Scott on 5/16/2016.
 */
public interface DataWrapper {

    Observable<List<HistoryItem>> getHistory();
    Observable<List<Workout>> getWorkouts();
    void saveWorkout(Workout w);
    Workout getMostRecent();


}
