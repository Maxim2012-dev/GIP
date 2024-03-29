package com.example.fitflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutSettings extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Oefening> oefeningen;
    DatabaseReference reff;

    private long maxID = 0;

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

        Toolbar instellingenToolbar = findViewById(R.id.instellingenToolbar);
        SharedPreferences sharedPref = this.getSharedPreferences("workout", MODE_PRIVATE);
        setSupportActionBar(instellingenToolbar);
        getSupportActionBar().setTitle(sharedPref.getString("naamWorkout", null));

        reff = FirebaseDatabase.getInstance().getReference("GemaakteWorkout");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                maxID = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
        tijdTussenRondes.setText(counterTussenRondes + "");
        tijdTussenOef.setText(counterTussenOef + "");

        oefeningen = ((MyApplication) this.getApplication()).getOefeningen();

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
                if (counterRondes > 1) {
                    counterRondes--;
                    aantalRondes.setText(String.valueOf(counterRondes));
                }
                break;
            case R.id.incrementTussenRondes:
                counterTussenRondes += 5;
                tijdTussenRondes.setText(counterTussenRondes + "");
                break;
            case R.id.decrementTussenRondes:
                if (counterTussenRondes > 5) {
                    counterTussenRondes -= 5;
                    tijdTussenRondes.setText(counterTussenRondes + "");
                }
                break;
            case R.id.incrementTussenOef:
                counterTussenOef += 5;
                tijdTussenOef.setText(counterTussenOef + "");
                break;
            case R.id.decrementTussenOef:
                if (counterTussenOef > 5) {
                    counterTussenOef -= 5;
                    tijdTussenOef.setText(counterTussenOef + "");
                }
                break;
            case R.id.startknop:
                toonPopUp();
                break;

        }
    }

    private void toonPopUp() {

        new AlertDialog.Builder(WorkoutSettings.this)
                .setCancelable(false)
                .setIcon(R.drawable.save)
                .setTitle("Workout opslaan?")
                .setMessage("Weet je zeker dat je deze workout wilt opslaan?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        saveData();
                        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.instellingen), "Workout succesvol opgeslagen!", "succes");
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                    }
                })
                .setNegativeButton("Nee", null)
                .show();

    }

    private void saveData() {

        String gebruikersEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String workoutNaam = getSupportActionBar().getTitle().toString();
        int rondes = Integer.parseInt(aantalRondes.getText().toString());
        String rustNaRonde = tijdTussenRondes.getText().toString();
        String rustNaOefening = tijdTussenOef.getText().toString();

        //Workout object maken
        Workout workout = new Workout(gebruikersEmail, workoutNaam, rondes, rustNaRonde, rustNaOefening, oefeningen);

        reff.child(String.valueOf(maxID + 1)).setValue(workout);

    }
}
