package com.example.fitflex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StelWorkoutSamen extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Oefening> oefeningen;
    OefeningAdapter oefeningAdapter;

    private TextView naam;
    private TextView geenOefeningen;
    private FloatingActionButton toevoegen;
    private ListView listView;
    private ImageView verwijder;

    public StelWorkoutSamen() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stel_workout_samen);

        SharedPreferences sharedPref = this.getSharedPreferences("workout", MODE_PRIVATE);

        initViews();
        setListeners();

        String naamWorkout = sharedPref.getString("naamWorkout", "");
        naam.setText(naamWorkout);

        if (oefeningen.size() == 0) {

            geenOefeningen.setText("Nog geen oefeningen...");

        } else {

            oefeningAdapter = new OefeningAdapter(StelWorkoutSamen.this, oefeningen);
            listView.setAdapter(oefeningAdapter);

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

                                    if (oefeningen.size() == 0) {

                                        geenOefeningen.setText("Nog geen oefeningen");

                                    }
                                    oefeningAdapter.notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("Nee", null)
                            .show();

                }
            });
        }

    }

    private void initViews() {

        naam = findViewById(R.id.naam);
        geenOefeningen = findViewById(R.id.geen_oefeningen);
        listView = findViewById(R.id.workoutOefeningen);
        verwijder = findViewById(R.id.verwijder);

        toevoegen = findViewById(R.id.toevoegen);

        oefeningen = ((MyApplication) this.getApplication()).getOefeningen();

    }

    private void setListeners() {

        toevoegen.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.toevoegen:
                startActivity(new Intent(this, OefeningListActivity.class));
                break;

        }

    }
}
