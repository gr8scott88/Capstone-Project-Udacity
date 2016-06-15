package capstone.nanodegree.nemesisdev.com.hiitit.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutTimer;

/**
 * Created by Scott on 3/15/2015.
 */
public class WorkoutAudioManager {
    private SoundPool mSoundPool;
    HashMap<Integer, Integer> soundPoolMap;
    int soundID = 1;
    private int mChimeID;
    private int mReadyID;
    private int mSetID;
    private int mGoID;
    private int mPrepareID;
    private int mWorkID;
    private int mRestID;
    private int mCooldownID;
    private int mWorkoutCompleteID;

    private Context mContext;
    private String mAudioType;

    public WorkoutAudioManager(Context ctx, String audioType) {
        this.mContext = ctx;
        this.mAudioType = audioType;
        this.buildSoundPool();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSoundPool = new SoundPool(10, android.media.AudioManager.STREAM_MUSIC, 0);
        }


        mChimeID = mSoundPool.load(mContext, R.raw.chime_1_short, 1);
        mReadyID = mSoundPool.load(mContext, R.raw.ready, 1);
        mSetID = mSoundPool.load(mContext, R.raw.set, 1);
        mGoID = mSoundPool.load(mContext, R.raw.go, 1);
        mPrepareID = mSoundPool.load(mContext, R.raw.prepare, 1);
        mWorkID = mSoundPool.load(mContext, R.raw.work, 1);
        mRestID = mSoundPool.load(mContext, R.raw.rest, 1);
        mCooldownID = mSoundPool.load(mContext, R.raw.cooldown, 1);
        mWorkoutCompleteID = mSoundPool.load(mContext, R.raw.workoutcomplete, 1);
    }

    public void playSound(int soundID) {
        if (mAudioType == "1"){
            //No Audio
        }else if(mAudioType.equals("2")){
            if (mSoundPool != null) {
                mSoundPool.play(mChimeID, 1, 1, 1, 0, 1);
            }
        }else if(mAudioType.equals("3")){
            if (mSoundPool != null) {
                mSoundPool.play(soundID, 1, 1, 1, 0, 1);
            }
        }
    }

    public void playChime(){
        playSound(mChimeID);
    }

    public void playReady(){
        playSound(mReadyID);
    }

    public void playSet(){
        playSound(mSetID);
    }

    public void playGo(){
        playSound(mGoID);
    }

    public void playPrepare(){
        playSound(mPrepareID);
    }

    public void playWork(){
        playSound(mWorkID);
    }

    public void playRest(){
        playSound(mRestID);
    }

    public void playCooldown(){
        playSound(mCooldownID);
    }

    public void playComplete(){
        playSound(mWorkoutCompleteID);
    }

    public void playStatus(WorkoutTimer.STATUS status){
        switch(status){
            case READY:
                playReady();
                break;

            case SET:
                playSet();
                break;

            case GO:
                playGo();
                break;

            case PREPARING:
                playPrepare();
                break;

            case RESTING:
                playRest();
                break;

            case WORKING:
                playWork();
                break;

            case COOLDOWN:
                playCooldown();
                break;

            case COMPLETE:
                playComplete();
                break;
        }
    }

}
