package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Locale;

public class WorkoutProgress extends AppCompatActivity {

    private TextView rondeText;
    private TextView naamHuidigeOefening;
    private TextView timerText;
    private Button klaarknop;

    private CountDownTimer countDownTimer;
    private Chronometer chronometer;

    private Workout huidigeWorkout;

    private int aantalRondes;

    private long tijdTussenRonde;
    private long tijdTussenOefening;
    private long resterendeTijdInMillis;

    private int index = 0;
    private int ronde = 1;
    private boolean workoutBezig = true;
    private boolean oefeningBezig;
    private boolean timerBezig = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_progress);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setVisibility(View.GONE);

        timerText = findViewById(R.id.timerText);
        timerText.setVisibility(View.GONE);

        rondeText = findViewById(R.id.rondeText);
        naamHuidigeOefening = findViewById(R.id.naamHuidigeOefening);

        klaarknop = findViewById(R.id.klaarknop);
        klaarknop.setVisibility(View.VISIBLE);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        aantalRondes = huidigeWorkout.getAantalRondes();

        tijdTussenRonde = Integer.parseInt(huidigeWorkout.getRustNaRonde()) * 1000;
        tijdTussenOefening = Integer.parseInt(huidigeWorkout.getRustNaOefening()) * 1000;

        while (workoutBezig) {

            if (ronde < aantalRondes) {

                if (index == huidigeWorkout.getOefeningen().size()) {

                    index = 0;
                    ronde++;

                }

                rondeText.setText("Ronde " + ronde);

                naamHuidigeOefening.setVisibility(View.VISIBLE);
                naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(index).getNaam());
                oefeningBezig = true;
                klaarknop.setEnabled(true);
                chronometer.setVisibility(View.VISIBLE);
                startChronometer();

                while (oefeningBezig) {

                    klaarknop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            resetChronometer();

                            klaarknop.setEnabled(false);
                            naamHuidigeOefening.setVisibility(View.GONE);

                            timerText.setVisibility(View.VISIBLE);
                            resterendeTijdInMillis = tijdTussenOefening;
                            startTimer();

                            oefeningBezig = false;
                            timerBezig = true;

                            index++;

                        }
                    });

                }

                while (timerBezig) {

                    if (resterendeTijdInMillis == 0) {

                        timerBezig = false;
                        timerText.setVisibility(View.GONE);

                    }

                }

            }

        }

    }

    public void startChronometer() {

        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    public void resetChronometer() {

        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());

    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(resterendeTijdInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                resterendeTijdInMillis = millisUntilFinished;
                updateTimerText();

            }

            @Override
            public void onFinish() {

                klaarknop.setEnabled(true);

            }
        }.start();

        klaarknop.setEnabled(false);

    }

    private void updateTimerText() {

        int minuten = (int) resterendeTijdInMillis / 1000 / 60;
        int seconden = (int) resterendeTijdInMillis / 1000 % 60;

        String resterendeTijdFormat = String.format(Locale.getDefault(), "%02d:%02d", minuten, seconden);

        timerText.setText(resterendeTijdFormat);

    }

}
