package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.Converter;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.HistoryConverter;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.WorkoutConverter;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Scott on 5/16/2016.
 */
public class LocalDataWrapper implements DataWrapper {
    private static final String TAG = "LocalDataWrapper";
    private ContentResolver mContentResolver;
    private Converter<Workout> mWorkoutConverter;
    private Converter<HistoryItem> mHistoryConverter;


    public LocalDataWrapper(ContentResolver contentResolver) {
        mContentResolver = contentResolver;
        mWorkoutConverter = new WorkoutConverter();
        mHistoryConverter = new HistoryConverter();
    }


    @Override
    public List<HistoryItem> getHistory() {
        return null;
    }

    @Override
    public List<Workout> getWorkouts(){
        Uri getWorkoutsURI = HiitContract.WorkoutEntry.CONTENT_URI;
        String sortOrder =  HiitContract.WorkoutEntry._ID + " ASC";

        Cursor cursor = mContentResolver.query(getWorkoutsURI,
                null,
                null,
                null,
                sortOrder);

        List<Workout> workouts = new ArrayList<>();

        if (cursor != null){
            while(cursor.moveToNext()){
                ContentValues values = mWorkoutConverter.convert(cursor);
                Workout workout = mWorkoutConverter.convert(values);
                workouts.add(workout);
            }
        }

        return workouts;

    }



    @Override
    public Uri saveWorkout(Workout w) {
        Uri uri = null;
        try{
            ContentValues cv = mWorkoutConverter.convert(w);
            uri = mContentResolver.insert(HiitContract.WorkoutEntry.CONTENT_URI, cv);
        }catch (Exception e){
            e.printStackTrace();
        }

        return uri;
    }

    @Override
    public Workout getMostRecentWorkout() {

        Uri getWorkoutsURI = HiitContract.WorkoutEntry.CONTENT_URI;
        Uri getHistoryURI = HiitContract.HistoryEntry.CONTENT_URI;

        String sortOrder =  HiitContract.HistoryEntry.COLUMN_DATE + " DESC";
        String[] histProjection = new String[] {HiitContract.HistoryEntry.COLUMN_WORKOUT_KEY};

        Cursor cursor = mContentResolver.query(getHistoryURI,
                histProjection,
                null,
                null,
                sortOrder);

        cursor.moveToFirst();
        int workoutId = cursor.getInt(0);


        ContentValues values = mWorkoutConverter.convert(cursor);
        Workout workout = mWorkoutConverter.convert(values);
        return workout;
    }

    @Override
    public int getMostRecentWorkoutId() {

        Uri getHistoryURI = HiitContract.HistoryEntry.CONTENT_URI;

        String sortOrder =  HiitContract.HistoryEntry.COLUMN_DATE + " DESC";
        String[] histProjection = new String[] {HiitContract.HistoryEntry.COLUMN_WORKOUT_KEY};

        Cursor cursor = mContentResolver.query(getHistoryURI,
                histProjection,
                null,
                null,
                sortOrder);

        cursor.moveToFirst();
        int workoutId = cursor.getInt(0);

        return workoutId;
    }

    @Override
    public Workout getWorkoutById(int id) {
        Uri getWorkoutsByIdURI = HiitContract.WorkoutEntry.buildWorkoutUri(id);
        String sortOrder =  HiitContract.WorkoutEntry._ID + " ASC";

        Cursor cursor = mContentResolver.query(getWorkoutsByIdURI,
                null,
                null,
                null,
                sortOrder);
        cursor.moveToFirst();
        ContentValues values = mWorkoutConverter.convert(cursor);
        Workout workout = mWorkoutConverter.convert(values);
        return workout;
    }

    @Override
    public int deleteWorkout(int id) {
        return mContentResolver.delete(HiitContract.WorkoutEntry.CONTENT_URI, HiitContract.WorkoutEntry._ID + "=?", new String[] {Integer.toString(id)});
    }


    @Override
    public Uri recordHistory(HistoryItem h) {
        //    public HistoryItem(int id, long date, int workoutId, int duration, int activeTime, int difficulty) {
        //HistoryItem h = new HistoryItem(0, date, id, totalDuration, activeTime, 0);
        Uri uri = null;
        try{
            ContentValues cv = mHistoryConverter.convert(h);
            uri = mContentResolver.insert(HiitContract.HistoryEntry.CONTENT_URI, cv);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.v(TAG, "Logged today's workout");
        return uri;

    }
}
