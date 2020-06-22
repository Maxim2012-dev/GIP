package com.example.fitflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fitflex.dummy.Track;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class WorkoutProgress extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference reff;
    private int aantalWorkouts;
    private int aantalOefeningen;

    private TextView rondeText;
    private TextView naamHuidigeOefening;
    private TextView reps;
    private TextView timerText;
    private Button klaarknop;

    private int sound;
    private SoundPool soundPool;
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

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final String validatieEmail = mAuth.getCurrentUser().getEmail();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("emailID").getValue().equals(validatieEmail)) {

                        aantalWorkouts = ds.child("aantalWorkouts").getValue(Integer.class);
                        aantalOefeningen = ds.child("aantalOefeningen").getValue(Integer.class);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (savedInstanceState != null) {

            resterendeTijdInMillis = savedInstanceState.getLong("milisOver");
            oefeningBezig = savedInstanceState.getBoolean("oefeningBezig", oefeningBezig);
            index = savedInstanceState.getInt("index", index);
            ronde = savedInstanceState.getInt("ronde", ronde);

        }

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

    @SuppressLint("SetTextI18n")
    private void zetTimer() {

        //timer zichtbaar, chrono onzichtbaar en huidige oefening onzichtbaar
        //reps onzichtbaar
        chronometer.setVisibility(View.GONE);
        timerText.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setVisibility(View.GONE);
        reps.setVisibility(View.GONE);

        //knop disabled en timer initialiseren
        klaarknop.setEnabled(false);

        index++;
        if (huidigeWorkout.getOefeningen().size() - 1 >= index) {
            reps.setText(huidigeWorkout.getOefeningen().get(index).getAantalReps() + " repetities");
        }
        //Als je aan de laatste oefening zit
        if (index == huidigeWorkout.getOefeningen().size()) {

            index = 0;
            ronde++;

            if (ronde > aantalRondes) {

                beëindigWorkout();

            } else {

                //rust ronde
                resterendeTijdInMillis = tijdTussenRonde;
                timerText.setTextColor(Color.BLACK);
                startTimer();
                rondeText.setText("Maak je klaar voor ronde " + ronde + "!");

            }

            //Als je niet aan laatste oefening zit
        } else {

            //rust oefening
            resterendeTijdInMillis = tijdTussenOefening;
            startTimer();

        }

    }

    @SuppressLint("SetTextI18n")
    private void startOefening() {

        //timer onzichtbaar, chrono zichtbaar, huidige oefening zichtbaar en knop enabled
        //reps zichtbaar
        timerText.setVisibility(View.GONE);
        chronometer.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setVisibility(View.VISIBLE);
        klaarknop.setEnabled(true);
        reps.setVisibility(View.VISIBLE);

        //ronde tonen
        rondeText.setText("Ronde " + ronde);

        //timerkleur op zwart
        timerText.setTextColor(Color.BLACK);

        //chrono resetten en starten
        resetChronometer();
        startChronometer();

        //De huidige oefening tonen
        naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(index).getNaam());
        reps.setText(huidigeWorkout.getOefeningen().get(index).getAantalReps() + " repetities");
        oefeningBezig = false;

    }

    @SuppressLint("SetTextI18n")
    private void initViews() {

        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference("Gebruiker");

        chronometer = findViewById(R.id.chronometer);
        chronometer.setVisibility(View.VISIBLE);

        //Geluidje initialiseren
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        sound = soundPool.load(this, R.raw.countdown, 1);

        timerText = findViewById(R.id.timerText);
        timerText.setVisibility(View.GONE);

        rondeText = findViewById(R.id.rondeText);
        naamHuidigeOefening = findViewById(R.id.naamHuidigeOefening);
        reps = findViewById(R.id.reps);

        klaarknop = findViewById(R.id.klaarknop);

        huidigeWorkout = ((MyApplication) this.getApplication()).getHuidigeWorkout();

        klaarDialog = new Dialog(this);

        aantalRondes = huidigeWorkout.getAantalRondes();
        tijdTussenRonde = Integer.parseInt(huidigeWorkout.getRustNaRonde()) * 1000;
        tijdTussenOefening = Integer.parseInt(huidigeWorkout.getRustNaOefening()) * 1000;

        rondeText.setText("Ronde " + ronde);

        naamHuidigeOefening.setVisibility(View.VISIBLE);
        naamHuidigeOefening.setText(huidigeWorkout.getOefeningen().get(index).getNaam());
        reps.setText(huidigeWorkout.getOefeningen().get(index).getAantalReps() + " repetities");

        startChronometer();

    }

    @SuppressLint("SetTextI18n")
    private void beëindigWorkout() {

        final String uID = mAuth.getCurrentUser().getUid();

        reff.child(uID).child("aantalWorkouts").setValue(aantalWorkouts + 1);
        reff.child(uID).child("aantalOefeningen").setValue(aantalOefeningen + (huidigeWorkout.getAantalRondes() * huidigeWorkout.getOefeningen().size()));

        onWorkoutCompletion();

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

    private void onWorkoutCompletion() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Track");

        String key = databaseReference.push().getKey();

        Track track = new Track();
        track.setDatum(geefDatum());
        track.setEmail(huidigeWorkout.gebruikersEmail);

        List<Track.TrackData> trackData = new ArrayList<>();

        for (Oefening oefening : huidigeWorkout.getOefeningen()) {
            Track.TrackData trackData1 = new Track().new TrackData();
            trackData1.setAantal_reps(oefening.aantalReps);
            trackData1.setOefening_naam(oefening.naam);
            trackData.add(trackData1);
        }
        track.setData(trackData);

        databaseReference.child(key).setValue(track);

    }

    private String geefDatum() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String datum = sdf.format(new Date());

        return datum;

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

        if (minuten == 0 && seconden == 4) {
            timerText.setTextColor(Color.RED);
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }

        timerText.setText(resterendeTijdFormat);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("milisOver", resterendeTijdInMillis);
        outState.putBoolean("oefeningBezig", oefeningBezig);
        outState.putInt("index", index);
        outState.putInt("ronde", ronde);
    }

}
