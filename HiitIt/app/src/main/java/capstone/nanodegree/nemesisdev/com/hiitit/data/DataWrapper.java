package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.net.Uri;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import rx.Observable;


/**
 * Created by Scott on 5/16/2016.
 */
public interface DataWrapper {

    List<HistoryItem> getHistory();
    List<Workout> getWorkouts();
    Workout getWorkoutById(int id);
    Uri saveWorkout(Workout w);
    Workout getMostRecent();


}
