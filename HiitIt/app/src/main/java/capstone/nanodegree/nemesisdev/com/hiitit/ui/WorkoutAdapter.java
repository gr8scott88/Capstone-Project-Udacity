package capstone.nanodegree.nemesisdev.com.hiitit.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;

/**
 * Created by Scott on 5/30/2016.
 */

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutHolder> {

    private static final int INITIAL_CAPACITY = 20;

    private WorkoutSelectedListener mListener;
    private List<Workout> mData = new ArrayList<>(INITIAL_CAPACITY);


    public WorkoutAdapter(WorkoutSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public WorkoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_workout, parent, false);
        return new WorkoutHolder(view);

    }

    @Override
    public void onBindViewHolder(WorkoutHolder holder, int position) {
        Workout workout = mData.get(position);

        try{
            holder.workoutName.setText(workout.getName());
        }catch (Exception e){
            holder.workoutName.setText("NULL");
            e.printStackTrace();
        }

        try{
            holder.workoutRounds.setText(workout.getRounds()+"");
        }catch (Exception e){
            holder.workoutRounds.setText("NULL");
            e.printStackTrace();
        }

        try{
            holder.workoutActiveTime.setText(workout.getActiveTime()+"");
        }catch (Exception e){
            holder.workoutActiveTime.setText("NULL");
            e.printStackTrace();
        }

        try{
            holder.workoutRestTime.setText(workout.getRestTime()+"");
        }catch (Exception e){
            holder.workoutRestTime.setText("NULL");
            e.printStackTrace();
        }

        try{
            holder.workoutId = workout.getId();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {return mData != null ? mData.size() : 0; }


    public void setData(@NonNull List<Workout> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    class WorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        int workoutId;
        TextView workoutName;
        TextView workoutRounds;
        TextView workoutActiveTime;
        TextView workoutRestTime;
        TextView workoutTotalTime;

        public WorkoutHolder(View view){
            super(view);
            workoutName = (TextView) view.findViewById(R.id.item_workout_name);
            workoutRounds = (TextView) view.findViewById(R.id.item_workout_rounds);
            workoutActiveTime = (TextView) view.findViewById(R.id.item_workout_active_time);
            workoutRestTime = (TextView) view.findViewById(R.id.item_workout_rest_time);
        }

        @Override
        public void onClick(View v) {mListener.onWorkoutSelected(workoutId);}
    }

}
