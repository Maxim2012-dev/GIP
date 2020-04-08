package com.example.fitflex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class StelWorkoutSamen extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Oefening> oefeningen = new ArrayList<>();
    CustomAdapter customAdapter;

    private TextView naam;
    private TextView geenOefeningen;
    private Button toevoegen;
    private Button volgende;
    private ListView listView;
    private ImageView verwijder;

    public StelWorkoutSamen() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stel_workout_samen);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        naam = findViewById(R.id.naam);
        geenOefeningen = findViewById(R.id.geen_oefeningen);
        toevoegen = findViewById(R.id.toevoegen);
        volgende = findViewById(R.id.volgende);
        listView = findViewById(R.id.workoutOefeningen);
        verwijder = findViewById(R.id.verwijder);

        oefeningen.add(new Oefening("Push Up", "Makkelijk"));

        Intent i = getIntent();
        String naamWorkout = i.getStringExtra("naamWorkout");
        getSupportActionBar().setTitle(naamWorkout);

        naam.setText(naamWorkout);

        setListeners();

        if (oefeningen.size() == 0) {

            geenOefeningen.setText("Nog geen oefeningen...");

        } else {

            customAdapter = new CustomAdapter(StelWorkoutSamen.this, oefeningen);
            listView.setAdapter(customAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    final int item = position;

                    new AlertDialog.Builder(StelWorkoutSamen.this)
                            .setIcon(R.drawable.ic_cancel_red)
                            .setTitle("Ben je zeker?")
                            .setMessage("Wil je dit item verwijderen?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    oefeningen.remove(item);
                                    customAdapter.notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("Nee", null)
                            .show();

                }
            });
        }

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
                break;

        }

    }
}
