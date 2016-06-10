package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.main.MainMenuActivity;

public class WorkoutActivity extends BaseActivity implements WorkoutView {

    private Workout mWorkout;
    private WorkoutPresenter mPresenter;

    private static final String TAG = "WORKOUTACTIVIYT";

    @BindView(R.id.workout_current_time) TextView mCurrentTime;
    @BindView(R.id.current_cycle_string) TextView mCurrentRound;
    @BindView(R.id.workout_status) TextView mCurrentStatus;
    @BindView(R.id.time_remaining_string) TextView mTimeRemaining;
    @BindView(R.id.button_end_workout) Button mButtonEndWorkout;
    @BindView(R.id.button_workout_go) ImageButton mButtonGo;
    @BindView(R.id.go_button_background) FrameLayout mGoButtonBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mPresenter = new WorkoutPresenterImpl(this, new LocalDataWrapper(getContentResolver()));


        try{
            mWorkout = getIntent().getParcelableExtra(PASSED_WORKOUT);
            if (mWorkout != null){
                Log.v(TAG, "Loaded workout via passed parcelable workout");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        if (mWorkout == null){
            try{
                int loadedId = getIntent().getIntExtra(PASSED_WORKOUT_ID, -1);
                mWorkout = mPresenter.loadWorkout(loadedId);
                if (mWorkout != null){
                    Log.v(TAG, "Loaded workout via passed id");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        try{
            boolean isLoaded = getIntent().getBooleanExtra(IS_LOADED_WORKOUT, false);
            if (isLoaded){
                showMessage("Loaded workout " + mWorkout.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        ButterKnife.setDebug(true);
        ButterKnife.bind(this);

        initViews();

    }



    @OnClick(R.id.button_end_workout)
    @Override
    public void onEndWorkoutClicked() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        mPresenter.saveWorkoutInfo();
        startActivity(intent);
    }

    @OnClick(R.id.button_workout_go)
    @Override
    public void goButtonPressed() {
        mGoButtonBackground.setVisibility(View.GONE);
        mPresenter.startWorkout(mWorkout);
    }

    private void initViews(){
        if (mWorkout != null){
            populateWorkoutDetails();
        }else{
            showMessage("Failed to load workout");
        }
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(WorkoutActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateWorkoutDetails() {
        Log.v(TAG, "Loaded workout: " +  mWorkout.getName());
        mCurrentStatus.setText("READY");
        mCurrentTime.setText(mWorkout.getActiveTime()+"");
        mCurrentRound.setText("1 of " + mWorkout.getRounds());
        mTimeRemaining.setText(mWorkout.getTotalWorkoutTime()+"");
    }

    @Override
    public void pauseWorkout() {
        //No Activity Needed, handled by presenter
    }

    @Override
    public void resumeWorkout() {
        //No Activity Needed, handled by presenter
    }

    @Override
    public void endWorkout(HistoryItem h) {
        saveWorkoutDetails(h);
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void saveWorkoutDetails(HistoryItem h) {

    }

    @Override
    public void onTimeChanged(String time) {
        mCurrentTime.setText(time);
    }

    @Override
    public void onRoundChanged(String round) {
        mCurrentRound.setText(round + " of " + mWorkout.getRounds());
    }

    @Override
    public void onStatusChanged(String status) {
        mCurrentStatus.setText(status);
    }

    @Override
    public void onWorkoutCompleted() {

    }
}
