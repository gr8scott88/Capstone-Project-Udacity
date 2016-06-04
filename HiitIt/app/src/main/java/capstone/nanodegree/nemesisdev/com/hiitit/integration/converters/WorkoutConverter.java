package capstone.nanodegree.nemesisdev.com.hiitit.integration.converters;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import static capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.getContentValuesFrom;

/**
 * Created by Scott on 5/11/2016.
 */
public class WorkoutConverter implements Converter<Workout> {

    @Override
    public ContentValues convert(Cursor c) {
        return getContentValuesFrom(c);
    }

    @Override
    public ContentValues convert(Workout workout) {
        if (workout == null){
            return null;
        }

        ContentValues values = new ContentValues();
        //values.put(HiitContract.WorkoutEntry._ID, workout.getId());
        values.put(HiitContract.WorkoutEntry.COLUMN_WORKOUT_NAME, workout.getName());
        values.put(HiitContract.WorkoutEntry.COLUMN_ROUNDS, workout.getRounds());
        values.put(HiitContract.WorkoutEntry.COLUMN_ACTIVE_TIME, workout.getActiveTime());
        values.put(HiitContract.WorkoutEntry.COLUMN_REST_TIME, workout.getRestTime());
        return values;

    }

    @Override
    public Workout convert(@NonNull ContentValues values) {

        int id = values.getAsInteger(HiitContract.WorkoutEntry._ID);
        String name = values.getAsString(HiitContract.WorkoutEntry.COLUMN_WORKOUT_NAME);
        int rounds = values.getAsInteger(HiitContract.WorkoutEntry.COLUMN_ROUNDS);
        int activeTime = values.getAsInteger(HiitContract.WorkoutEntry.COLUMN_ACTIVE_TIME);
        int restTime = values.getAsInteger(HiitContract.WorkoutEntry.COLUMN_REST_TIME);

        Workout workout = new Workout(id, name, rounds, activeTime, restTime);
        return workout;

    }
}
