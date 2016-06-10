package capstone.nanodegree.nemesisdev.com.hiitit.ui.buildworkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.main.MainMenuPresenter;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.utils.TimeManager;

public class BuildWorkoutActivity extends BaseActivity implements BuildWorkoutView{


    @BindView(R.id.newWorkoutName) EditText mWorkoutName;
    @BindView(R.id.exercise_time) EditText mActiveTime;
    @BindView(R.id.break_time) EditText mBreakTime;
    @BindView(R.id.round_count) EditText mRounds;
    @BindView(R.id.calculated_workout_time) TextView mTotalTime;
    @BindView(R.id.button_start_workout) Button mStartWorkout;

    private BuildWorkoutPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter = new BuildWorkoutPresenterImpl(this, new LocalDataWrapper(getContentResolver()));

        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @OnTextChanged({R.id.exercise_time, R.id.break_time, R.id.round_count}) void onTextChanged(CharSequence text){
        mTotalTime.setText(TimeManager.calcWorkoutTimeString(mActiveTime.getText().toString(), mBreakTime.getText().toString(), mRounds.getText().toString()));
    }

    @Override
    public void updateTotalTime(String newTime) {

    }

    @Override
    public void startWorkout(int id) {

    }


    @OnClick(R.id.button_start_workout)
    @Override
    public void onStartWorkoutClicked() {
        String name = mWorkoutName.getText().toString();
        String activeTime = mActiveTime.getText().toString();
        String breakTime = mBreakTime.getText().toString();
        String rounds =  mRounds.getText().toString();
        boolean goodData = mPresenter.verifyInput(name, activeTime, breakTime, rounds);
        if (goodData) {
            Workout w = new Workout(name, rounds, activeTime, breakTime);
            mPresenter.saveWorkout(w);
            startWorkout(w);
        }
    }

    @Override
    public void startWorkout(Workout w) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra(PASSED_WORKOUT, w);
        startActivity(intent);

    }

    @Override
    public void showMessage(String s) {
        Toast.makeText(BuildWorkoutActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
