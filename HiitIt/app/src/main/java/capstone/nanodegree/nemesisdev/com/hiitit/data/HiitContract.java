package capstone.nanodegree.nemesisdev.com.hiitit.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by Scott on 5/7/2016.
 */
public class HiitContract {


    public static enum DIFFICULTY {EASY, MEDIUM, HARD, IMPOSSIBLE};

    public static final String CONTENT_AUTHORITY = "capstone.nanodegree.nemesisdev.com.hiitit";
    //android:name="capstone.nanodegree.nemesisdev.com.hiitit.data.WorkoutProvider"
    //android:authorities="capstone.nanodegree.nemesisdev.com.hiitit"

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_WORKOUT = "workout";

    public static final String PATH_HISTORY = "history";


    public static final class WorkoutEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORKOUT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_WORKOUT;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_WORKOUT;


        public static final String TABLE_NAME = "workouts";

        public static final String COLUMN_WORKOUT_NAME = "workout_name";

        public static final String COLUMN_ROUNDS = "rounds";

        public static final String COLUMN_ACTIVE_TIME = "active_time";

        public static final String COLUMN_REST_TIME = "rest_time";

        public static Uri buildWorkoutUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static int getIdFromUri(Uri uri){
            List<String> segments = uri.getPathSegments();
            return Integer.parseInt(segments.get(1));
        }

    }



    public static final class HistoryEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_HISTORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_HISTORY;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+PATH_HISTORY;


        public static final String TABLE_NAME = "history";

        public static final String COLUMN_WORKOUT_KEY = "workout_id";

        public static final String COLUMN_DATE = "date";

        public static final String DURATION = "workout_time";

        public static final String COLUMN_ACTIVE_TIME = "active_time";

        public static final String COLUMN_DIFFICULTY = "difficulty";



        public static Uri buildHistoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWorkoutHistoryFromWindow(int length){
            return CONTENT_URI.buildUpon().appendQueryParameter(COLUMN_DATE, Integer.toString(length)).build();
        }
    }


    //Created as part of a team during a Google Hackathon
    
    /**
     * Given a {@link Cursor} already moved to a certain position, returns a
     * {@link ContentValues} object containing the column names and column values found in the
     * {@link Cursor} instance.
     *
     * <p>This method uses the cursors {@link Cursor#getColumnNames()},
     * {@link Cursor#getColumnIndex(String)}, and {@link Cursor#getType(int)} methods
     * to populate the {@link ContentValues} object dynamically.</p>
     *
     * <p>Values of type {@link Cursor#FIELD_TYPE_NULL} are not populated, since we don't have
     * enough information to determine the corresponding column's type.</p>
     *
     * @param cursor to get {@link ContentValues} for
     * @return {@link ContentValues} object.
     */
    public static ContentValues getContentValuesFrom(Cursor cursor){
        ContentValues values = new ContentValues();
        for(String columnName : cursor.getColumnNames()){
            int columnIndex = cursor.getColumnIndex(columnName);
            switch(cursor.getType(columnIndex)){
                case Cursor.FIELD_TYPE_BLOB:
                    values.put(columnName, cursor.getBlob(columnIndex));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    values.put(columnName, cursor.getFloat(columnIndex));
                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    values.put(columnName, cursor.getLong(columnIndex));
                    break;
                case Cursor.FIELD_TYPE_NULL:
                    //  We won't populate null values because we don't know their type.
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    values.put(columnName, cursor.getString(columnIndex));
                    break;
                default:
                    throw new UnsupportedOperationException("Unsupported Value Type:  " + cursor.getType(columnIndex));
            }
        }
        return values;
    }

}
