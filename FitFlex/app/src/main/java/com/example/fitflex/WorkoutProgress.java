package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class WorkoutProgress extends AppCompatActivity {

    private TextView rondeText;
    private TextView naamHuidigeOefening;
    private TextView timerText;
    private Button klaarknop;

    private CountDownTimer countDownTimer;

    private Workout huidigeWorkout;

    private int aantalRondes;
    private int aantalOefeningen;

    private long tijdTussenRonde;
    private long tijdTussenOefening;
    private long resterendeTijdInMillis;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_progress);

        rondeText = findViewById(R.id.rondeText);
        naamHuidigeOefening = findViewById(R.id.naamHuidigeOefening);
        timerText = findViewById(R.id.timerText);
        klaarknop = findViewById(R.id.klaarknop);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        aantalRondes = huidigeWorkout.getAantalRondes();
        aantalOefeningen = huidigeWorkout.getOefeningen().size();

        tijdTussenRonde = Integer.parseInt(huidigeWorkout.getRustNaRonde()) * 1000;
        tijdTussenOefening = Integer.parseInt(huidigeWorkout.getRustNaOefening()) * 1000;

        for (int i = 1; i <= aantalRondes; i++) {

            if (i > 1) {
                resterendeTijdInMillis = tijdTussenRonde;
                startTimer();
            }
            rondeText.setText("Ronde " + i);

            for (int j = 0; j < aantalOefeningen; j++) {

                naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(j).getNaam());

                klaarknop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        resterendeTijdInMillis = tijdTussenOefening;
                        startTimer();

                    }
                });

            }

        }

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

        String resterendeTijdFormat = String.format(Locale.getDefault(),"%02d:%02d", minuten, seconden);

        timerText.setText(resterendeTijdFormat);

    }

}
