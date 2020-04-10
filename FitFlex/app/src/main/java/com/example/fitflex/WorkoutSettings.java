package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        }

    }
}
