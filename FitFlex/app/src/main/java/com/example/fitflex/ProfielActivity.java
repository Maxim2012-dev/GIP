package com.example.fitflex;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfielActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText email;
    EditText wachtwoord;
    EditText telefoon;
    EditText locatie;
    EditText naam;

    ImageView profiel_foto;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.profiel_email);
        wachtwoord = findViewById(R.id.profiel_wachtwoord);
        telefoon = findViewById(R.id.profiel_telefoonnummer);
        locatie = findViewById(R.id.profiel_locatie);
        naam = findViewById(R.id.profiel_naam);

        progressBar = findViewById(R.id.profiel_progressBar);
        profiel_foto = findViewById(R.id.profiel_foto);

    }

}
