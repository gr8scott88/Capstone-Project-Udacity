package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.main.MainMenuActivity;

public class WorkoutActivity extends BaseActivity implements WorkoutView {

    private Workout mWorkout;
    private WorkoutPresenter mPresenter;


    @BindView(R.id.workout_current_time) TextView mCurrentTime;
    @BindView(R.id.current_cycle_string) TextView mCurrentRound;
    @BindView(R.id.workout_status) TextView mCurrentStatus;
    @BindView(R.id.time_remaining_string) TextView mTimeRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        mPresenter = new WorkoutPresenterImpl(this, new LocalDataWrapper(getContentResolver()));


        try{
            mWorkout = getIntent().getParcelableExtra("PASSEDWORKOUT");
        }catch (Exception e){
            e.printStackTrace();
        }

        if (mWorkout == null){
            try{
                int loadedId = getIntent().getIntExtra("LOADEDWORKOUT", -1);
                mPresenter.loadWorkout(loadedId);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
    }


    private void initViews(){


    }

    @Override
    public void onWorkoutLoaded(Workout w) {
        mWorkout = w;
        mCurrentStatus.setText("READY");
        mCurrentTime.setText(mWorkout.getActiveTime()+"");
        mCurrentRound.setText("1 of " + mWorkout.getRounds());
        mTimeRemaining.setText(mWorkout.getTotalWorkoutTime()+"");
    }

    public void onWorkoutLoaded(){
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
