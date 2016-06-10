package capstone.nanodegree.nemesisdev.com.hiitit.ui.loadworkout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;
import capstone.nanodegree.nemesisdev.com.hiitit.data.LocalDataWrapper;
import capstone.nanodegree.nemesisdev.com.hiitit.data.pojo.Workout;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.WorkoutAdapter;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.WorkoutSelectedListener;
import capstone.nanodegree.nemesisdev.com.hiitit.ui.workout.WorkoutActivity;

public class LoadWorkoutActivity extends BaseActivity implements LoadWorkoutView, WorkoutSelectedListener {


    private WorkoutAdapter mAdapter;
    private LoadWorkoutPresenter mPresenter;
    private RecyclerView mWorkoutView;
    private List<Workout> mWorkouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        mPresenter = new LoadWorkoutPresenterImpl(this, new LocalDataWrapper(getContentResolver()));


        initViews();

        mPresenter.loadWorkouts();

    }

    private void initViews(){
        mWorkoutView = (RecyclerView) findViewById(R.id.workout_list);
        mAdapter = new WorkoutAdapter(this);
        mWorkoutView.setAdapter(mAdapter);
        mWorkoutView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onWorkoutSelected(int workoutId) {
        showMessage("Clicked: " + workoutId);
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.putExtra(PASSED_WORKOUT_ID, workoutId);
        intent.putExtra(IS_LOADED_WORKOUT, true);
        startActivity(intent);
    }

    @Override
    public void onWorkoutsLoaded(List<Workout> workouts) {
        mAdapter.setData(workouts);
        mAdapter.notifyDataSetChanged();
        mWorkouts = workouts;
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(LoadWorkoutActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWorkoutDeleted(int workoutId, int pos) {
        mPresenter.deleteWorkout(workoutId);
        mWorkouts.remove(pos);
        mAdapter.removeItem(pos);
        mAdapter.notifyItemRemoved(pos);
        mAdapter.notifyItemRangeChanged(pos, mWorkouts.size());
        //mAdapter.notifyDataSetChanged();
    }


}
