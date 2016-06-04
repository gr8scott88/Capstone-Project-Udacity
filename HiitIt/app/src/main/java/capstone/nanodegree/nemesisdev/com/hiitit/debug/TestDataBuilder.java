package capstone.nanodegree.nemesisdev.com.hiitit.debug;

import android.content.ContentValues;
import android.content.Context;

import java.util.Vector;

import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.HistoryConverter;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.WorkoutConverter;

/**
 * Created by Scott on 5/18/2016.
 */
public class TestDataBuilder {

    private WorkoutConverter mWorkoutConverter = new WorkoutConverter();
    private HistoryConverter mHistoryConverter = new HistoryConverter();

    private Context mContext;

    public TestDataBuilder(Context context) {
        mContext = context;
    }


    public int insertWorkouts(){

        try {
            // Insert the new weather information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(3);

            ContentValues workoutValues = new ContentValues();


            //  public Workout(int cId, String mName, int cRounds, int cActiveTime, int cRestTime) \
            Workout w1 = new Workout(0, "Tabata", 8, 20, 10);
            Workout w2 = new Workout(1, "Cardio", 15, 2, 1);
            Workout w3 = new Workout(2, "Afterburn", 30, 60, 30);

            cVVector.add(mWorkoutConverter.convert(w1));
            cVVector.add(mWorkoutConverter.convert(w2));
            cVVector.add(mWorkoutConverter.convert(w3));

            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            int addedEntries = mContext.getContentResolver().bulkInsert(HiitContract.WorkoutEntry.CONTENT_URI, cvArray);
            return addedEntries;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }



    public int insertHistory(){


        try {
            // Insert the new weather information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(3);

            ContentValues historyValues = new ContentValues();

            //public HistoryItem(int id, long date, int workoutId, int duration, int activeTime, int difficulty) {
            HistoryItem h1 = new HistoryItem(0, 300, 1, 60, 30, 0);
            HistoryItem h2 = new HistoryItem(1, 600, 2, 70, 40, 1);
            HistoryItem h3 = new HistoryItem(2, 900, 1, 80, 50, 2);

            cVVector.add(mHistoryConverter.convert(h1));
            cVVector.add(mHistoryConverter.convert(h2));
            cVVector.add(mHistoryConverter.convert(h3));

            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            int addedEntries = mContext.getContentResolver().bulkInsert(HiitContract.HistoryEntry.CONTENT_URI, cvArray);
            return addedEntries;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }
}
