package capstone.nanodegree.nemesisdev.com.hiitit.integration.converters;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Scott on 5/11/2016.
 */
public interface Converter<T> {
    ContentValues convert(Cursor cursor);
    ContentValues convert(T object);
    T convert(ContentValues values);
}

