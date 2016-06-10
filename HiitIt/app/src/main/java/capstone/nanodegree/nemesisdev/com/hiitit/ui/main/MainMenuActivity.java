package capstone.nanodegree.nemesisdev.com.hiitit.ui.main;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import junit.framework.Assert;

import butterknife.OnClick;
import capstone.nanodegree.nemesisdev.com.hiitit.AndroidDatabaseManager;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.nanodegree.nemesisdev.com.hiitit.data.HiitContract;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.WorkoutDbHelper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.debug.TestDataBuilder;
import capstone.nanodegree.nemesisdev.com.hiitit.integration.converters.WorkoutConverter;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout.BuildWorkoutActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.history.WorkoutHistoryActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout.LoadWorkoutActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.profile.ProfileActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutActivity;

public class MainMenuActivity extends BaseActivity implements MainMenuView{

    private static final String TAG = "MainMenuActivity";
    private MainMenuPresenter mPresenter;

    private TestDataBuilder mTestDataBuilder;

    @BindView(R.id.button_quickstart) Button mButtonQuickStart;
    @BindView(R.id.button_load_workout) Button mButtonLoadWorkout;
    @BindView(R.id.button_custom_workout) Button mButtonCustomWorkout;
    @BindView(R.id.button_profile) Button mButtonProfile;
    @BindView(R.id.button_exercise_log) Button mButtonExerciseLog;
    @BindView(R.id.button_db_manager) Button mDbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mPresenter = new MainMenuPresenterImpl(this, new LocalDataWrapper(getContentResolver()));
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        Assert.assertNotNull(mButtonQuickStart);
        debugTesting();
    }


    private void debugTesting(){

        mTestDataBuilder  = new TestDataBuilder(this);
        int workoutsAdded  = mTestDataBuilder.insertWorkouts();
        int historyAdded = mTestDataBuilder.insertHistory();

        Log.v(TAG, "Added " + workoutsAdded + " workouts");
        Log.v(TAG, "Added " + historyAdded + " histories");

    }



    @OnClick({ R.id.button_quickstart, R.id.button_load_workout, R.id.button_custom_workout, R.id.button_profile, R.id.button_exercise_log, R.id.button_db_manager })
    public void buttonClicks(View v) {
        mPresenter.onMenuItemSelected(v.getId());
    }

    @Override
    public void openSelectedMenuActivity(int id) {
        Intent intent;
        switch (id){
            case R.id.button_quickstart:
                intent = new Intent(this, WorkoutActivity.class);
                int loadedWorkoutId = mPresenter.loadMostRecentWorkoutId();
                intent.putExtra(PASSED_WORKOUT_ID, loadedWorkoutId);
                intent.putExtra(IS_LOADED_WORKOUT, true);
                startActivity(intent);

                break;

            case R.id.button_load_workout:
                intent = new Intent(this, LoadWorkoutActivity.class);
                startActivity(intent);
                break;

            case R.id.button_custom_workout:
                intent = new Intent(this, BuildWorkoutActivity.class);
                startActivity(intent);
                break;

            case R.id.button_profile:
                intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.button_exercise_log:
                intent = new Intent(this, WorkoutHistoryActivity.class);
                startActivity(intent);
                break;

            case R.id.button_db_manager:
                intent = new Intent(this,AndroidDatabaseManager.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void genericMessage(String message) {
        Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
