package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.WorkoutEntry;
import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract.HistoryEntry;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutDbHelper extends SQLiteOpenHelper{

    private static final String TAG = "WorkoutDBHelper";
    private static final Boolean mDebug = true;
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "hiit.db";

    public WorkoutDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.v(TAG, "onCreate called");

        if (mDebug){
            Log.v(TAG, "In debug mode, dropping tables");
            String dropWorkouts = "DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME;
            String dropHistory = "DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME;
            db.execSQL(dropWorkouts);
            db.execSQL(dropHistory);
        }


        final String SQL_CREATE_WORKOUT_TABLE = "CREATE TABLE " + WorkoutEntry.TABLE_NAME + " (" +
                WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WorkoutEntry.COLUMN_WORKOUT_NAME + " TEXT UNIQUE NOT NULL, " +
                WorkoutEntry.COLUMN_ROUNDS + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_ACTIVE_TIME + " INTEGER NOT NULL, " +
                WorkoutEntry.COLUMN_REST_TIME + " INTEGER NOT NULL" +
                ");";

        final String SQL_CREATE_HISTORY_TABLE = "CREATE TABLE " + HistoryEntry.TABLE_NAME + " (" +
                HistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HistoryEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_WORKOUT_KEY + " INTEGER NOT NULL, " +
                HistoryEntry.DURATION + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_ACTIVE_TIME + " INTEGER NOT NULL, " +
                HistoryEntry.COLUMN_DIFFICULTY + " INTEGER NOT NULL" +
                ");";

        Log.v(TAG, "Creating Tables");

        db.execSQL(SQL_CREATE_WORKOUT_TABLE);
        db.execSQL(SQL_CREATE_HISTORY_TABLE);

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //TODO: Improve upgrade method, this isn't realistic
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WorkoutEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }

}
