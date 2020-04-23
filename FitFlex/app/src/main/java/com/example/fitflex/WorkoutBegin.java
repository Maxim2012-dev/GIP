package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        Toolbar beginToolbar = findViewById(R.id.beginToolbar);
        setSupportActionBar(beginToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        workoutText = findViewById(R.id.workoutText);
        beginknop = findViewById(R.id.beginknop);

        workoutText.setText("Klaar om " + huidigeWorkout.getNaam() + " te doen?");

        beginknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), WorkoutProgress.class));

            }
        });

    }
}
