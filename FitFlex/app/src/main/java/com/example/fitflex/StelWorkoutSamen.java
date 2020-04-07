package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StelWorkoutSamen extends AppCompatActivity implements View.OnClickListener {

    public static List<Oefening> oefeningen = new ArrayList<>();

    private TextView naam;
    private TextView geenOefeningen;
    private Button toevoegen;
    private Button volgende;

    public StelWorkoutSamen() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stel_workout_samen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        naam = findViewById(R.id.naam);
        geenOefeningen = findViewById(R.id.geen_oefeningen);
        toevoegen = findViewById(R.id.toevoegen);
        volgende = findViewById(R.id.volgende);

        if (oefeningen.size() == 0) {

            geenOefeningen.setText("Nog geen oefeningen...");

        }

        Intent i = getIntent();
        String naamWorkout = i.getStringExtra("naamWorkout");
        getSupportActionBar().setTitle(naamWorkout);

        naam.setText(naamWorkout);

        setListeners();

    }

    private void setListeners() {

        toevoegen.setOnClickListener(this);
        volgende.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.toevoegen:
                startActivity(new Intent(this, OefeningListActivity.class));
                break;
            case R.id.volgende:


        }

    }
}
