package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class WorkoutProgress extends AppCompatActivity {

    private TextView rondeText;
    private TextView naamHuidigeOefening;
    private TextView timerText;
    private Button klaarknop;

    private CountDownTimer countDownTimer;
    private Chronometer chronometer;

    private Workout huidigeWorkout;

    private Dialog klaarDialog;
    private Button gaTerugKnop;
    private TextView boodschap;

    private int aantalRondes;
    private long tijdTussenRonde;
    private long tijdTussenOefening;
    private long resterendeTijdInMillis;
    private boolean oefeningBezig = false;

    private int index = 0;
    private int ronde = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_progress);

        initViews();

        klaarknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oefeningBezig) {

                    startOefening();

                } else {

                    zetTimer();

                }

            }
        });

    }

    private void zetTimer() {

        //timer zichtbaar, chrono onzichtbaar en huidige oefening onzichtbaar
        chronometer.setVisibility(View.GONE);
        timerText.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setVisibility(View.GONE);

        //knop disabled en timer initialiseren
        klaarknop.setEnabled(false);

        index++;

        //Als je aan de laatste oefening zit
        if (index == huidigeWorkout.getOefeningen().size()) {

            index = 0;
            ronde++;

            //rust ronde
            resterendeTijdInMillis = tijdTussenRonde;
            startTimer();

            if (ronde > aantalRondes) {

                beëindigWorkout();

            } else {

                rondeText.setText("Ronde " + ronde);

            }

        //Als je niet aan laatste oefening zit
        } else {

            //rust oefening
            resterendeTijdInMillis = tijdTussenOefening;
            startTimer();

        }

    }

    private void startOefening() {

        //timer onzichtbaar, chrono zichtbaar, huidige oefening zichtbaar en knop enabled
        timerText.setVisibility(View.GONE);
        chronometer.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setVisibility(View.VISIBLE);
        klaarknop.setEnabled(true);

        //chrono resetten en starten
        resetChronometer();
        startChronometer();

        //De huidige oefening tonen
        naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(index).getNaam());
        oefeningBezig = false;

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        chronometer = findViewById(R.id.chronometer);
        chronometer.setVisibility(View.VISIBLE);

        timerText = findViewById(R.id.timerText);
        timerText.setVisibility(View.GONE);

        rondeText = findViewById(R.id.rondeText);
        naamHuidigeOefening = findViewById(R.id.naamHuidigeOefening);

        klaarknop = findViewById(R.id.klaarknop);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        klaarDialog = new Dialog(this);

        aantalRondes = huidigeWorkout.getAantalRondes();
        tijdTussenRonde = Integer.parseInt(huidigeWorkout.getRustNaRonde()) * 1000;
        tijdTussenOefening = Integer.parseInt(huidigeWorkout.getRustNaOefening()) * 1000;

        rondeText.setText("Ronde " + ronde);

        naamHuidigeOefening.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(index).getNaam());

        startChronometer();

    }

    @SuppressLint("SetTextI18n")
    private void beëindigWorkout() {

        Random random = new Random();

        klaarknop.setEnabled(false);
        klaarDialog.setContentView(R.layout.klaar_dialog);
        klaarDialog.setCancelable(false);
        gaTerugKnop = klaarDialog.findViewById(R.id.gaTerugKnop);
        boodschap = klaarDialog.findViewById(R.id.boodschap);

        int tempRandom = random.nextInt(3);

        switch (tempRandom) {
            case 0:
                boodschap.setText("Super gedaan!");
                break;
            case 1:
                boodschap.setText("Wauw!!!");
                break;
            case 2:
                boodschap.setText("Uitstekend!");
                break;
            case 3:
                boodschap.setText("Ga zo door!");
                break;
        }

        gaTerugKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        klaarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        klaarDialog.show();

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

                startOefening();

            }

        }.start();

    }

    private void updateTimerText() {

        int minuten = (int) resterendeTijdInMillis / 1000 / 60;
        int seconden = (int) resterendeTijdInMillis / 1000 % 60;

        String resterendeTijdFormat = String.format(Locale.getDefault(), "%02d:%02d", minuten, seconden);

        timerText.setText(resterendeTijdFormat);

    }

}
