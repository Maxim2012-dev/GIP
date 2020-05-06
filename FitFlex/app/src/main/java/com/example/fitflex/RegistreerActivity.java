package com.example.fitflex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistreerActivity extends AppCompatActivity {

    private EditText naam, email, telefoonnummer, locatie,
            wachtwoord, bevestigWachtwoord;
    private TextView alGebruiker;
    private Button registerknop;
    private CheckBox voorwaarden;
    private ProgressBar progressBarR;

    private long maxGebruikersNr;
    DatabaseReference reff;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registreer_layout);

        initViews();
        setListeners();

    }

    private void initViews() {

        naam = findViewById(R.id.naam);
        email = findViewById(R.id.email);
        telefoonnummer = findViewById(R.id.telefoonnummer);
        locatie = findViewById(R.id.locatie);
        wachtwoord = findViewById(R.id.wachtwoord);
        bevestigWachtwoord = findViewById(R.id.bevestigWachtwoord);
        registerknop = findViewById(R.id.registerknop);
        alGebruiker = findViewById(R.id.alGebruiker);
        voorwaarden = findViewById(R.id.voorwaarden);
        progressBarR = findViewById(R.id.progressBarR);

        progressBarR.setVisibility(View.GONE);

        reff = FirebaseDatabase.getInstance().getReference("Gebruiker");
        mAuth = FirebaseAuth.getInstance();

    }

    private void setListeners() {

        registerknop.setOnClickListener((View.OnClickListener) this);
        alGebruiker.setOnClickListener((View.OnClickListener) this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerknop:
                Gebruiker gebruiker = new Gebruiker();
                checkValidation(gebruiker);
                break;
            case R.id.alGebruiker:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

    }

    private void checkValidation(final Gebruiker gebruiker) {

        final String getNaam = naam.getText().toString();
        final String getEmailId = email.getText().toString();
        final String getTelefoonnummer = telefoonnummer.getText().toString();
        final String getLocatie = locatie.getText().toString();
        final String getWachtwoord = wachtwoord.getText().toString();
        final String getBevestigWachtwoord = bevestigWachtwoord.getText().toString();

        //Checken of email al bestaat

        //Alles ingevuld?
        if (getNaam.equals("") || getNaam.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getTelefoonnummer.equals("") || getTelefoonnummer.length() == 0
                || getLocatie.equals("") || getLocatie.length() == 0
                || getWachtwoord.equals("") || getWachtwoord.length() == 0
                || getBevestigWachtwoord.equals("")
                || getBevestigWachtwoord.length() == 0) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Vul alle velden in.", "error");

            //Email
        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Het e-mailadres is ongeldig.", "error");


            //Telefoonnummer
        } else if (getTelefoonnummer.length() > 10) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Het telefoonnummer is ongeldig.", "error");


            //Wachtwoord
        } else if (!Utils.WACHTWOORD_PATROON.matcher(getWachtwoord).matches()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Het wachtwoord moet minstens 6 karakters bevatten met:\n" +
                            "- min. 1 cijfer\n" +
                            "- min. 1 kleine letter\n" +
                            "- min. 1 hoofdletter", "error");


            //Bevestig wachtwoord
        } else if (!getBevestigWachtwoord.equals(getWachtwoord)) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Beide wachtwoorden moeten gelijk zijn.", "error");


            //Voorwaarden
        } else if (!voorwaarden.isChecked()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout),
                    "Accepteer de algemene voorwaarden.", "error");


            //Registreer
        } else {

            progressBarR.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(getEmailId, getWachtwoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        gebruiker.setNaam(getNaam);
                        gebruiker.setEmailID(getEmailId);
                        gebruiker.setLocatie(getLocatie);
                        gebruiker.setWachtwoord(getWachtwoord);
                        gebruiker.setTelefoonnummer(getTelefoonnummer);

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userid = user.getUid();

                        reff.child(userid).setValue(gebruiker);

                        progressBarR.setVisibility(View.GONE);
                        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.registreer_layout), "Succesvol geregistreerd!", "succes");

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    } else {

                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

}
