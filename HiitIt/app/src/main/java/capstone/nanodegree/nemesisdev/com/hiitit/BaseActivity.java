package capstone.nanodegree.nemesisdev.com.hiitit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Scott on 5/3/2016.
 */
public class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    public boolean bIsFirstRun = true;

    public static final String PASSED_WORKOUT = "PASSED_WORKOUT";
    public static final String PASSED_WORKOUT_ID = "PASSSED_WORKOUT_ID";
    public static final String IS_LOADED_WORKOUT = "IS_LOADED_WORKOUT";
    private static final String IS_FIRST_RUN = "IS_FIRST_RUN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        bIsFirstRun = sharedPref.getBoolean(IS_FIRST_RUN, true);
    }

    public void setNotFirstRun(){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_FIRST_RUN, false);
        editor.commit();
        Log.v(TAG, "Set ISFIRSTRUN to False");

    }



}
