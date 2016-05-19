package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.WorkoutEntry;
import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.HistoryEntry;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutDbHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 0;

    static final String DATABASE_NAME = "hiit.db";

    public WorkoutDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WORKOUT_TABLE = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " (" +
                WorkoutEntry._ID + " INTEGER PRIMARY KEY, " +
                WorkoutEntry.COLUMN_WORKOUT_NAME + " TEXT UNIQUE NOT NULL, " +
                WorkoutEntry.COLUMN_ROUNDS + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_ACTIVE_TIME + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_REST_TIME + " INTEGER NOT NULL " +
                " ):";

        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " (" +
                HistoryEntry._ID + " INTEGER PRIMARY KEY, " +
                HistoryEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_WORKOUT_KEY + " INTEGER NOT NULL, " +
                HistoryEntry.DURATION + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_ACTIVE_TIME + " INTEGER NOT NULL " +
                HistoryEntry.COLUMN_DIFFICULTY + " INTEGER NOT NULL " +
                " ):";

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //TODO: Improve upgrade method, this isn't realistic
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
