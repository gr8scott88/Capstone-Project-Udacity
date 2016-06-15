package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.utils.WorkoutAudioManager;
import capstone.nanodegree.nemesisdev.com.hiitit.utils.WorkoutStateManager;

/**
 * Created by Scott on 6/9/2016.
 */
public class WorkoutTimer extends Service {


    private static final String TAG = "TimerService";
    public enum STATUS {READY, SET, GO, PREPARING, RESTING, WORKING, COOLDOWN, PAUSED, COMPLETE, KILL};

    private boolean bIsBound;
    private boolean bIsDead = false;
    public boolean bIsRunning = false;
    public boolean bIsWaitingToDie = false;

    private static final long TICK_INTERVAL = 1 * 1000; // 1 second

    public static final String WORKOUTTIMER_BR = "intervaltrainer.nemesisdev.com.intervaltrainer.timerservice_br";
    Intent bi = new Intent(WORKOUTTIMER_BR);

    private final IBinder mBinder = new LocalBinder();

    private int mTimeElapsed = 0;
    private int mTotalWorkoutTime;


    private WorkoutStateManager mStateManager;
    private WorkoutAudioManager mAudioManager;

    private Workout mWorkout;
    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    public WorkoutTimer() {};

    public void configureService(Workout w){
        mStateManager = new WorkoutStateManager(w);
        mAudioManager = new WorkoutAudioManager(this, "2");
        mTotalWorkoutTime = w.getTotalWorkoutTime();
    }


    public void resetTimerIfNecessary(){
        if (bIsDead){
            mTimeElapsed = 0;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        bIsBound = true;
        return mBinder;
    }

    public void UnBind(){bIsBound = false;}

    public class LocalBinder extends Binder {
        WorkoutTimer getService() {
            // Return this instance of LocalService so clients can call public methods
            return WorkoutTimer.this;
        }

    }


    private WorkoutRunnable timerRunnable = new WorkoutRunnable();

    public class WorkoutRunnable implements Runnable
    {
        private boolean killMe = false;

        public void run()
        {
            if(killMe)
                return;
            incrementTime();
            killIfNecessary();
            mHandler.postDelayed(this, 1000);
        }

        private void killRunnable()
        {
            killMe = true;
        }

        private void restartRunnable(){
            killMe = false;
        }
    }


    private void killIfNecessary(){
        if (mTimeElapsed > mTotalWorkoutTime+10*60) {
            if(bIsBound){
                //TODO write complete status to log file?
                Log.v(TAG, "Killing current service because workout has been complete for 10 minutes");
                mHandler.removeCallbacks(timerRunnable);
                timerRunnable.killRunnable();
                bIsDead = true;
                stopSelf();
            }

        }else{
            mStateManager.updateCurrentTime(mTimeElapsed);
            if (mStateManager.getStatusChanged()){
                mAudioManager.playStatus(mStateManager.getStatus());
                mStateManager.resetChangedStatus();

            }
            //TODO check statemanager
            broadcastInfoToActivity();

        }
    }

    private void incrementTime(){
        mTimeElapsed++;
    }

    public void broadcastInfoToActivity(){
        //Only send the broadcast if this service is currently bound to an activity
        if (bIsBound){

            bi.putExtra("timeleft", (mTimeElapsed - mTotalWorkoutTime));
            bi.putExtra("currentstatus", mStateManager.getStatusAsString());
            bi.putExtra("currentround", mStateManager.getCurrentRound());
            bi.putExtra("timeelapsed", mTimeElapsed);
            sendBroadcast(bi);

            if (mStateManager.getStatus() == STATUS.KILL){
                bi.putExtra("timercomplete", true);
                mHandler.removeCallbacks(timerRunnable);
                timerRunnable.killRunnable();
                stopSelf();
            }
        }
    }

    public boolean startTimer(){
        mHandler.postDelayed(timerRunnable, 1000);
        return true;
    }

    public boolean stopTimer(){
        mHandler.removeCallbacks(timerRunnable);
        timerRunnable.killRunnable();
        bIsDead = true;
        return true;
    }



}

