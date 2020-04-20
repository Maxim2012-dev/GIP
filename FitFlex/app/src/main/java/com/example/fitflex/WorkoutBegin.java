package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutBegin extends AppCompatActivity {

    Workout huidigeWorkout;

    private TextView workoutText;
    private Button beginknop;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_begin);

        getSupportActionBar().setTitle("Beginnen?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        workoutText = findViewById(R.id.workoutText);
        beginknop = findViewById(R.id.beginknop);

        workoutText.setText("Ben je klaar om " + huidigeWorkout.getNaam() + " te doen?");

    }
}
