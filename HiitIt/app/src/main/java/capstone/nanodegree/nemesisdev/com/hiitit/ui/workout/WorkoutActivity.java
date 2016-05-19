package capstone.nanodegree.nemesisdev.com.hiitit.ui.workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import capstone.nanodegree.nemesisdev.com.hiitit.BaseActivity;
import capstone.nanodegree.nemesisdev.com.hiitit.R;

public class WorkoutActivity extends BaseActivity implements WorkoutView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
    }
}
