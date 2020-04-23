package com.example.fitflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class StelWorkoutSamen extends AppCompatActivity {

    ArrayList<Oefening> oefeningen;
    OefeningAdapter oefeningAdapter;

    private TextView naam;
    private TextView geenOefeningen;
    private FloatingActionButton toevoegen;
    private ListView listView;
    private ImageView verwijder;
    private Dialog infoDialog;
    private ImageView sluitDialog;
    private Button okKnop;

    private BottomAppBar bottomAppBar;

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
        infoDialog = new Dialog(this);
        bottomAppBar = findViewById(R.id.bar);

        toevoegen = findViewById(R.id.toevoegen);

        oefeningen = ((MyApplication) this.getApplication()).getOefeningen();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.helpInfo:
                toonDialog();
                break;
            case R.id.volgende:
                startActivity(new Intent(this, WorkoutSettings.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void toonDialog() {

        infoDialog.setContentView(R.layout.custom_dialog);
        okKnop = infoDialog.findViewById(R.id.okKnop);

        sluitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        okKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDialog.dismiss();
            }
        });

        infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        infoDialog.show();

    }

    private void setListeners() {

        toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OefeningListActivity.class));
            }
        });

    }
}
