package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StelWorkoutSamen extends AppCompatActivity {

    private TextView naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stel_workout_samen);

        naam = findViewById(R.id.naam);

        Intent i = getIntent();
        String naamWorkout = i.getStringExtra("naamWorkout");
        getSupportActionBar().setTitle(naamWorkout);

        naam.setText(naamWorkout);

    }
}
