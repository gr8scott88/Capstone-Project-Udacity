package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.main.MainMenuActivity;

public class WorkoutActivity extends BaseActivity implements WorkoutView {
    private Workout mWorkout;
    private WorkoutPresenter mPresenter;
    private boolean bIsBound = false;
    private static final String TAG = "WORKOUTACTIVIYT";

    @BindView(R.id.workout_current_time) TextView mCurrentTime;
    @BindView(R.id.current_cycle_string) TextView mCurrentRound;
    @BindView(R.id.workout_status) TextView mCurrentStatus;
    @BindView(R.id.time_elapsed_string) TextView mTimeElapsedField;
    @BindView(R.id.button_end_workout) Button mButtonEndWorkout;
    @BindView(R.id.image_status_indicator) ImageView mStatusIndicator;
    @BindView(R.id.workout_main_window) RelativeLayout mMainWindow;

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.processBroadcasts(intent); // or whatever method used to update your GUI fields
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            WorkoutTimer2.LocalBinder binder = (WorkoutTimer2.LocalBinder) service;
            WorkoutTimer2 timer = binder.getService();
            mPresenter.attachTimer(timer, mWorkout);
            bIsBound = true;
            Log.v(TAG, "Service Bound");
        }

        public void onServiceDisconnected(ComponentName arg0) {
            bIsBound = false;
        }

    };


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


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(br, new IntentFilter(WorkoutTimer2.TIMERSERVICE_BR));
        Intent serviceIntent = new Intent(this, WorkoutTimer2.class);
        startService(serviceIntent);
        boolean success = getApplicationContext().bindService(serviceIntent,mConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bIsBound) {
            Log.v(TAG, "Service Unbound");
            mPresenter.unBindTimer();
            //mWorkoutTimer.UnBind();
            getApplicationContext().unbindService(mConnection);
            bIsBound = false;
        }
        unregisterReceiver(br);

    }

    @OnClick(R.id.button_end_workout)
    @Override
    public void onEndWorkoutClicked() {
        mPresenter.stopTimer();
        mPresenter.workoutEnded();
    }

   @OnClick(R.id.workout_main_window)
    public void onMainWindowClicked(){
        mPresenter.mainWindowClicked();
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
        //mCurrentStatus.setText("READY");
        //mCurrentTime.setText(mWorkout.getActiveTime()+"");
        mCurrentRound.setText("1 of " + mWorkout.getRounds());
        mTimeElapsedField.setText(mWorkout.getTotalWorkoutTime()+"");
    }



    @Override
    public void onTimeChanged(String elapsedTime, String stepTime) {
        mCurrentTime.setText(stepTime);
        mTimeElapsedField.setText(elapsedTime);
    }

    @Override
    public void onRoundChanged(String round) {
        mCurrentRound.setText(round + " of " + mWorkout.getRounds());
    }

    @Override
    public void onStatusChanged(String status) {
        switch (status){
            case "GO":
                mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_go));
                break;

            case "COMPLETE":
                mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_complete));
                break;

            case "REST":
                mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_rest));
                break;

            default:
                mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_rest));
                break;

        }

        Log.v(TAG, "Setting text to current status");
        mCurrentStatus.setText(status);
    }

    @Override
    public void showComplete() {
        mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_complete));
        mCurrentStatus.setText("COMPLETE");
        mCurrentTime.setText("TAP TO VIEW RESULTS");
        
    }

    @Override
    public void startWorkout() {
        mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_rest));
        mCurrentStatus.setText("READY");
        mCurrentTime.setText("1");
    }

    @Override
    public void pauseWorkout() {
        mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_paused));
        mCurrentStatus.setText("PAUSED");
    }

    @Override
    public void resumeWorkout() {
        mStatusIndicator.setImageDrawable(getDrawable(R.drawable.status_image_go));
    }

    @Override
    public void endWorkout() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
