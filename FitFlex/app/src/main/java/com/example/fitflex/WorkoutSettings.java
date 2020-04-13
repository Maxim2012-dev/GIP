package com.example.fitflex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutSettings extends AppCompatActivity implements View.OnClickListener {

    private ImageView incrementRondes;
    private ImageView decrementRondes;
    private TextView aantalRondes;

    private ImageView incrementTussenRondes;
    private ImageView decrementTussenRondes;
    private TextView tijdTussenRondes;

    private ImageView incrementTussenOef;
    private ImageView decrementTussenOef;
    private TextView tijdTussenOef;

    private Button startknop;
    private int counterRondes;
    private int counterTussenRondes;
    private int counterTussenOef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_settings);

        initViews();
        setListeners();

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        incrementRondes = findViewById(R.id.incrementRondes);
        decrementRondes = findViewById(R.id.decrementRondes);
        aantalRondes = findViewById(R.id.aantalRondes);

        incrementTussenRondes = findViewById(R.id.incrementTussenRondes);
        decrementTussenRondes = findViewById(R.id.decrementTussenRondes);
        tijdTussenRondes = findViewById(R.id.tijdTussenRondes);

        incrementTussenOef = findViewById(R.id.incrementTussenOef);
        decrementTussenOef = findViewById(R.id.decrementTussenOef);
        tijdTussenOef = findViewById(R.id.tijdTussenOef);

        startknop = findViewById(R.id.startknop);

        counterRondes = 3;
        counterTussenRondes = 60;
        counterTussenOef = 15;
        aantalRondes.setText(String.valueOf(counterRondes));
        tijdTussenRondes.setText(counterTussenRondes + "s");
        tijdTussenOef.setText(counterTussenOef + "s");


    }

    private void setListeners() {

        incrementRondes.setOnClickListener(this);
        decrementRondes.setOnClickListener(this);

        incrementTussenRondes.setOnClickListener(this);
        decrementTussenRondes.setOnClickListener(this);

        incrementTussenOef.setOnClickListener(this);
        decrementTussenOef.setOnClickListener(this);

        startknop.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.incrementRondes:
                counterRondes++;
                aantalRondes.setText(String.valueOf(counterRondes));
                break;
            case R.id.decrementRondes:
                if (counterRondes > 0) {
                    counterRondes--;
                    aantalRondes.setText(String.valueOf(counterRondes));
                }
                break;
            case R.id.incrementTussenRondes:
                counterTussenRondes += 5;
                tijdTussenRondes.setText(counterTussenRondes + "s");
                break;
            case R.id.decrementTussenRondes:
                if (counterTussenRondes > 0) {
                    counterTussenRondes -= 5;
                    tijdTussenRondes.setText(counterTussenRondes + "s");
                }
                break;
            case R.id.incrementTussenOef:
                counterTussenOef += 5;
                tijdTussenOef.setText(counterTussenOef + "s");
                break;
            case R.id.decrementTussenOef:
                if (counterTussenOef > 0) {
                    counterTussenOef -= 5;
                    tijdTussenOef.setText(counterTussenOef + "s");
                }
                break;
            case R.id.startknop:

                new AlertDialog.Builder(WorkoutSettings.this)
                        .setIcon(R.drawable.ic_save)
                        .setTitle("Workout bijhouden?")
                        .setMessage("Wil je de gemaakte workout opslaan?")
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                saveData();
                                Toast.makeText(getApplicationContext(), "Workout opgeslagen!", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Nee", null)
                        .show();

                startActivity(new Intent(getApplicationContext(), WorkoutProgress.class));
                break;

        }
    }

    private void saveData() {

        SharedPreferences sharedPref = this.getSharedPreferences("workout", MODE_PRIVATE);

        ArrayList<Oefening> oefeningen = ((MyApplication) this.getApplication()).getOefeningen();
        String workoutnaam = sharedPref.getString("naamWorkout", "");

        Workout workout = new Workout(workoutnaam, oefeningen);

        ((MyApplication) this.getApplication()).getWorkoutlijst().add(workout);

    }
}
