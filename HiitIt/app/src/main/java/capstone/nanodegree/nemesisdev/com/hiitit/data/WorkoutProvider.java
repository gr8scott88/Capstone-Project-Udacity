package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Scott on 5/7/2016.
 */
public class WorkoutProvider extends ContentProvider {

    private static final String TAG = "WORKOUTPROVIDER";

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private WorkoutDbHelper mDbHelper;

    static final int WORKOUTS = 100;
    static final int WORKOUTS_BY_ID = 101;

    static final int HISTORY = 200;
    static final int HISTORY_FROM_DATE = 201;

    private static final String sHistoryPastDate =
            HiitContract.HistoryEntry.TABLE_NAME + "." + HiitContract.HistoryEntry.COLUMN_DATE + ">= ? ";

    private static final String sModifyWorkoutById = HiitContract.WorkoutEntry.TABLE_NAME + "." + HiitContract.WorkoutEntry._ID + "=? ";


    @Override
    public boolean onCreate() {
        mDbHelper = new WorkoutDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        Cursor manualCursor;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORKOUTS:
                Log.v(TAG, "Querying All Workouts");

                retCursor = mDbHelper.getReadableDatabase().query(
                        HiitContract.WorkoutEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );



                break;
            case HISTORY_FROM_DATE:
                Log.v(TAG, "Querying Histories by date");
                retCursor = mDbHelper.getReadableDatabase().query(
                        HiitContract.HistoryEntry.TABLE_NAME,
                        projection,
                        sHistoryPastDate,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case HISTORY:
                Log.v(TAG, "Querying All Histories");
                retCursor = mDbHelper.getReadableDatabase().query(
                        HiitContract.HistoryEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;

            case WORKOUTS_BY_ID:
                Log.v(TAG, "Querying workout by id");
                int id = HiitContract.WorkoutEntry.getIdFromUri(uri);
                retCursor = mDbHelper.getReadableDatabase().query(
                        HiitContract.WorkoutEntry.TABLE_NAME,
                        projection,
                        sModifyWorkoutById,
                        new String[] {Integer.toString(id)},
                        null,
                        null,
                        sortOrder
                );



                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        };

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }



    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case WORKOUTS:
                return HiitContract.WorkoutEntry.CONTENT_TYPE;
            case HISTORY:
                return  HiitContract.HistoryEntry.CONTENT_TYPE;
            case HISTORY_FROM_DATE:
                return HiitContract.HistoryEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case WORKOUTS: {
                long _id = db.insert(HiitContract.WorkoutEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = HiitContract.WorkoutEntry.buildWorkoutUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case HISTORY: {
                long _id = db.insert(HiitContract.HistoryEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = HiitContract.HistoryEntry.buildHistoryUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case WORKOUTS:
                rowsDeleted = db.delete(HiitContract.WorkoutEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HISTORY:
                rowsDeleted = db.delete(HiitContract.HistoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match){
            case WORKOUTS:
                db.beginTransaction();

                try{
                    for (ContentValues value : values){
                        long _id = db.insert(HiitContract.WorkoutEntry.TABLE_NAME, null, value);
                        if (_id != -1){
                            Log.v(TAG, "Inserting: " + value.toString());
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            case HISTORY:
                db.beginTransaction();
                try{
                    for (ContentValues value : values){
                        long _id = db.insert(HiitContract.HistoryEntry.TABLE_NAME, null, value);
                        if (_id != -1){
                            Log.v(TAG, "Inserting: " + value.toString());
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }


    }

    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = HiitContract.CONTENT_AUTHORITY;

        //Match when general WORKOUT is requested
        matcher.addURI(authority, HiitContract.PATH_WORKOUT, WORKOUTS);

        //Match when general HISTORY is requested
        matcher.addURI(authority, HiitContract.PATH_HISTORY, HISTORY);

        //Match when a WORKOUT with a specific ID is requested
        matcher.addURI(authority, HiitContract.PATH_WORKOUT + "/*", WORKOUTS_BY_ID);

        return matcher;
    }


    @Override
    @TargetApi(11)
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }
}
