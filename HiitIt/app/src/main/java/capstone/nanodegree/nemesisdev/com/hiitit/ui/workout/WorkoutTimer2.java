package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.HistoryItem;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.utils.WorkoutAudioManager;
import capstone.nanodegree.nemesisdev.com.hiitit.utils.WorkoutManager2;

/**
 * Created by Scott on 6/13/2016.
 */
public class WorkoutTimer2 extends Service {
    private static final String TAG = "WORKOUTTIMER2";
    private boolean bIsBound;
    private final IBinder mBinder = new LocalBinder();
    private int mTimeElapsed = 0;
    private int mCurrentStepTimeLeft = 1;
    private Handler mHandler = new Handler();
    private WorkoutManager2 mManager;
    private WorkoutAudioManager mAudioManager;
    private boolean bIsComplete = false;

    public static final String TIMERSERVICE_BR = "capstone.nanodegree.nemesisdev.com.hiitit.timerservice_br";
    Intent bi = new Intent(TIMERSERVICE_BR);

    public WorkoutTimer2() {};


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        bIsBound = true;
        return mBinder;
    }

    public void UnBind(){bIsBound = false;}

    public class LocalBinder extends Binder {
        WorkoutTimer2 getService() {
            // Return this instance of LocalService so clients can call public methods
            return WorkoutTimer2.this;
        }
    }

    public void setupService(Workout w){
        mManager = new WorkoutManager2(w);
        Log.v(TAG, mManager.toString());
        mAudioManager = new WorkoutAudioManager(this, "2");
        broadcastInfoToActivity(1);
        //printWorkoutStatus();
    }

    private TimerRunnable timerRunnable = new TimerRunnable();

    public class TimerRunnable implements Runnable
    {
        private boolean killMe = false;

        public void run()
        {
            if(killMe) {
                Log.v(TAG, "Killing runnable");
                return;

            }

            int status = mManager.isStatusChange(mTimeElapsed);

            printWorkoutStatus(status);
            //Log.v(TAG, Integer.toString(status));
            if(status == 0){
                broadcastInfoToActivity(0);
            }else if (status == 1){
                mAudioManager.playChime();
                mCurrentStepTimeLeft = mManager.getDuration();
                broadcastInfoToActivity(1);
            }else if (status == 2){
                bIsComplete = true;
                broadcastInfoToActivity(2);

            }
            incrementTime();

            //killIfNecessary();
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


    public void incrementTime(){
        mTimeElapsed++;
        mCurrentStepTimeLeft--;
        //printWorkoutStatus();
        //Log.v(TAG, "Total Time Elapsed: " + mTimeElapsed);
        //Log.v(TAG, "Current Step Time Left: " + mCurrentStepTimeLeft);
    };

    private void printWorkoutStatus(){
        String workoutString = String.format("Status: %s, Round: %d, Time Elapsed: %d, Next Step Time: %d, Step Time Remaining: %d", mManager.getNewStatus(), mManager.getCurrentRound(), mTimeElapsed, mManager.getTransitionTime(), mCurrentStepTimeLeft);
        Log.v(TAG, workoutString);
    }

    private void printWorkoutStatus(int status){
        String workoutString = String.format("Current Step: %d, Status Compare: %d, Status: %s, Round: %d, Time Elapsed: %d, Next Step Time: %d, Step Time Remaining: %d", mManager.getCurrentStep(), status, mManager.getNewStatus(), mManager.getCurrentRound(), mTimeElapsed, mManager.getTransitionTime(), mCurrentStepTimeLeft);
        Log.v(TAG, workoutString);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public void broadcastInfoToActivity(int status){
        //Only send the broadcast if this service is currently bound to an activity
        if (bIsBound){
           if(status == 0){
               bi.putExtra(ServiceContract.TOTAL_TIME_ELAPSED, mTimeElapsed);
               bi.putExtra(ServiceContract.STEP_TIME_REMAINING, mCurrentStepTimeLeft);
               sendBroadcast(bi);
           }else if (status == 1){
               bi.putExtra(ServiceContract.CURRENT_STATUS, mManager.getNewStatus());
               bi.putExtra(ServiceContract.CURRENT_ROUND, mManager.getCurrentRound());
               bi.putExtra(ServiceContract.TOTAL_TIME_ELAPSED, mTimeElapsed);
               bi.putExtra(ServiceContract.STEP_TIME_REMAINING, mCurrentStepTimeLeft);
               sendBroadcast(bi);
           }else if (status == 2){
               bi.putExtra(ServiceContract.COMPLETED_ACTIVE_TIME, mManager.getActiveTime());
               bi.putExtra(ServiceContract.WORKOUT_ID, mManager.getWorkoutId());
               bi.putExtra(ServiceContract.WORKOUT_COMPLETE, true);
               bi.putExtra(ServiceContract.TOTAL_DURATION, mTimeElapsed);
               sendBroadcast(bi);
           }
        }
    }

    public boolean startTimer(){
        mHandler.postDelayed(timerRunnable, 1000);
        return true;
    }

    public boolean stopTimer(){
        timerRunnable.killRunnable();
        mHandler.removeCallbacks(timerRunnable);
        stopSelf();
        //mTimeElapsed = 0;
        return true;
    }

    public HistoryItem getCompeletedHistoryItem(){
        long millis = System.currentTimeMillis() % 1000;
        Log.v(TAG, "Current sytem time in millis: " + millis);
        HistoryItem h = new HistoryItem(0, millis, mManager.getWorkoutId(),mTimeElapsed,  mManager.getActiveTime(), -1);
        return h;
    }

}
