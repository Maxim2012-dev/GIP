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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    private View view;
    private Animation shakeAnimation;
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

            geenOefeningen.setText("Nog geen oefeningen...\n\n" +
                                    "Druk op de plusknop!");

        } else {

            oefeningAdapter = new OefeningAdapter(StelWorkoutSamen.this, oefeningen);
            listView.setAdapter(oefeningAdapter);
            oefeningAdapter.setOnListItemClickListener(new OefeningAdapter.OnListItemClickListener() {
                @Override
                public void onItemRemove(int position) {
                    onRemoveItemClick(position);
                }
            });

        }

    }

    private void onRemoveItemClick(int position) {
        final int item = position;

        new AlertDialog.Builder(StelWorkoutSamen.this)
                .setIcon(R.drawable.cancel_red)
                .setTitle("Ben je zeker?")
                .setMessage("Wil je dit item verwijderen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        oefeningen.remove(item);

                        if (oefeningen.size() == 0) {

                            geenOefeningen.setText("Voeg opnieuw oefeningen toe!");

                        }
                        oefeningAdapter.notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Nee", null)
                .show();
    }

    private void initViews() {

        view = findViewById(R.id.toegevoegdeOef);
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        geenOefeningen = findViewById(R.id.geen_oefeningen);
        listView = findViewById(R.id.workoutOefeningen);
        verwijder = findViewById(R.id.verwijder);
        infoDialog = new Dialog(this);
        bottomAppBar = findViewById(R.id.bar);

        setSupportActionBar(bottomAppBar);

        toevoegen = findViewById(R.id.toevoegen);

        oefeningen = ((MyApplication) this.getApplication()).getOefeningen();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

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
                checkValidatie();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void checkValidatie() {

        if (oefeningen.size() >= 1) {

            startActivity(new Intent(this, WorkoutSettings.class));

        } else {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.toegevoegdeOef), "Voeg eerst oefeningen toe", "error");
            view.startAnimation(shakeAnimation);

        }

    }

    private void toonDialog() {

        infoDialog.setContentView(R.layout.help_dialog);
        infoDialog.setCancelable(false);
        okKnop = infoDialog.findViewById(R.id.okKnop);
        sluitDialog = infoDialog.findViewById(R.id.sluitDialog);

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

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });

    }
}
