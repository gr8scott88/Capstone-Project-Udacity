package capstone.nanodegree.nemesisdev.com.hiitit.integration.converters;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import static capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.getContentValuesFrom;
/**
 * Created by Scott on 5/18/2016.
 */
public class HistoryConverter implements Converter<HistoryItem> {

    @Override
    public ContentValues convert(Cursor c) {
        return getContentValuesFrom(c);
    }

    @Override
    public ContentValues convert(HistoryItem hist) {
        if (hist == null){
            return null;
        }

        ContentValues values = new ContentValues();
        //values.put(HiitContract.HistoryEntry._ID, hist.getId());
        values.put(HiitContract.HistoryEntry.COLUMN_WORKOUT_KEY, hist.getWorkoutId());
        values.put(HiitContract.HistoryEntry.COLUMN_DATE, hist.getDate());
        values.put(HiitContract.HistoryEntry.COLUMN_ACTIVE_TIME, hist.getActiveTime());
        values.put(HiitContract.HistoryEntry.DURATION, hist.getDuration());
        values.put(HiitContract.HistoryEntry.COLUMN_DIFFICULTY, hist.getDifficulty());
        return values;

    }

    @Override
    public HistoryItem convert(@NonNull ContentValues values) {

        int id = values.getAsInteger(HiitContract.HistoryEntry._ID);
        int workoutId = values.getAsInteger(HiitContract.HistoryEntry._ID);
        int activeTime = values.getAsInteger(HiitContract.HistoryEntry.COLUMN_ACTIVE_TIME);
        int duration = values.getAsInteger(HiitContract.HistoryEntry.DURATION);
        int difficulty = values.getAsInteger(HiitContract.HistoryEntry.COLUMN_DIFFICULTY);
        long date = values.getAsLong(HiitContract.HistoryEntry.COLUMN_DATE);

        HistoryItem hist = new HistoryItem(id, date, workoutId, duration, activeTime, difficulty);
        return hist;

    }
}
