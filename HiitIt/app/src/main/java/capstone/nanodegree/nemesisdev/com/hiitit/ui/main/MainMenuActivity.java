package capstone.nanodegree.nemesisdev.com.hiitit.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import junit.framework.Assert;
import junit.framework.Test;

import butterknife.OnClick;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.nanodegree.nemesisdev.com.hiitit.debug.TestDataBuilder;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout.BuildWorkoutActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.history.WorkoutHistoryActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout.LoadWorkoutActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.profile.ProfileActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutActivity;

public class MainMenuActivity extends BaseActivity implements MainMenuView{


    private MainMenuPresenter mPresenter;

    private TestDataBuilder mTestDataBuilder;

    @BindView(R.id.button_quickstart) Button mButtonQuickStart;
    @BindView(R.id.button_load_workout) Button mButtonLoadWorkout;
    @BindView(R.id.button_custom_workout) Button mButtonCustomWorkout;
    @BindView(R.id.button_profile) Button mButtonProfile;
    @BindView(R.id.button_exercise_log) Button mButtonExerciseLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mPresenter = new MainMenuPresenterImpl(this);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        Assert.assertNotNull(mButtonQuickStart);

        mTestDataBuilder  = new TestDataBuilder(this);
        mTestDataBuilder.insertWorkouts();
        mTestDataBuilder.insertHistory();

    }


    @OnClick({ R.id.button_quickstart, R.id.button_load_workout, R.id.button_custom_workout, R.id.button_profile, R.id.button_exercise_log })
    public void buttonClicks(View v) {
        mPresenter.onMenuItemSelected(v.getId());
    }
    @Override
    public void openSelectedMenuActivity(int id) {
        Intent intent;
        switch (id){
            case R.id.button_quickstart:
                intent = new Intent(this, WorkoutActivity.class);
                //TODO: get a real workout ID based on most recent workout
                int loadedWorkoutId = 0;
                intent.putExtra(QUICK_LOAD_WORKOUT, loadedWorkoutId);
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
        }
    }


    @Override
    public void genericMessage(String message) {
        Toast.makeText(MainMenuActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
