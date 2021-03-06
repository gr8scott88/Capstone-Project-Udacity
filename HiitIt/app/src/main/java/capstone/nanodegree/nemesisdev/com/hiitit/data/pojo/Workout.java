package capstone.nanodegree.nemesisdev.com.hiitit.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 5/7/2016.
 */
public class Workout implements Parcelable{

    private int mId;
    private String mName;
    private int mRounds;
    private int mActiveTime;
    private int mRestTime;
    private int mTotalWorkoutTime;

    public Workout(int cId, String mName, int cRounds, int cActiveTime, int cRestTime) {
        this.mId = cId;
        this.mName = mName;
        this.mRounds = cRounds;
        this.mActiveTime = cActiveTime;
        this.mRestTime = cRestTime;
        this.mTotalWorkoutTime = calculateTotalTime();
    }

    public Workout(String mName, int cRounds, int cActiveTime, int cRestTime) {
        this.mId = 0;
        this.mName = mName;
        this.mRounds = cRounds;
        this.mActiveTime = cActiveTime;
        this.mRestTime = cRestTime;
        this.mTotalWorkoutTime = calculateTotalTime();
    }


    public Workout( String mName, String cRounds, String cActiveTime, String cRestTime) {
        this.mId = 0;
        this.mName = mName;
        this.mRounds = Integer.parseInt(cRounds);
        this.mActiveTime = Integer.parseInt(cActiveTime);
        this.mRestTime = Integer.parseInt(cRestTime);
        this.mTotalWorkoutTime = calculateTotalTime();
    }


    public int getId() {
        return mId;
    }

    public int getRounds() {
        return mRounds;
    }

    public int getActiveTime() {
        return mActiveTime;
    }

    public int getRestTime() {
        return mRestTime;
    }

    public int getTotalWorkoutTime() {
        return mTotalWorkoutTime;
    }

    public String getName() {
        return mName;
    }

    private int calculateTotalTime(){

        //TODO
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mRounds);
        dest.writeInt(mActiveTime);
        dest.writeInt(mRestTime);
        dest.writeInt(mTotalWorkoutTime);
    }


    public static final Parcelable.Creator<Workout> CREATOR
            = new Parcelable.Creator<Workout>() {
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    private Workout(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mRounds = in.readInt();
        mActiveTime = in.readInt();
        mRestTime = in.readInt();
        mTotalWorkoutTime = in.readInt();
    }


    public String workoutDetail(){
        return "Workout " + mId +": " + "Name: " + mName + " Rounds: " + mRounds + " Active Time: " + mActiveTime;
    }



}
