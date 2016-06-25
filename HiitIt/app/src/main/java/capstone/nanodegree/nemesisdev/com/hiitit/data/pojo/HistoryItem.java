package capstone.nanodegree.nemesisdev.com.hiitit.data.pojo;

/**
 * Created by Scott on 5/16/2016.
 */
public class HistoryItem {
    private int mId;
    private long mDate;
    private int mDuration;
    private int mActiveTime;
    private int mDifficulty;
    private int mWorkoutId;


    public HistoryItem(int id, long date, int workoutId, int duration, int activeTime, int difficulty) {
        mId = id;
        mDate = date;
        mDuration = duration;
        mActiveTime = activeTime;
        mDifficulty = difficulty;
        mWorkoutId = workoutId;
    }

    public int getId() {
        return mId;
    }

    public int getWorkoutId() {
        return mWorkoutId;
    }

    public long getDate() {
        return mDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public int getActiveTime() {
        return mActiveTime;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public void setActiveTime(int activeTime) {
        mActiveTime = activeTime;
    }

    public void setDifficulty(int difficulty) {
        mDifficulty = difficulty;
    }

    @Override
    public String toString() {
        return String.format("History: Date: %d%n, ID: %d, Duration: %d, Active Time: %d, Difficulty: %d", mDate, mId, mDuration, mActiveTime, mDifficulty);
    }
}
