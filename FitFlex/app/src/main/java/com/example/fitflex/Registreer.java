package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registreer extends AppCompatActivity {

    EditText naam;
    EditText voornaam;
    EditText email;
    EditText gebruikersnaam;
    EditText wachtwoord;
    Button registreerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registreer);

        naam = findViewById(R.id.naam);
        voornaam = findViewById(R.id.voornaam);
        email = findViewById(R.id.email);
        gebruikersnaam = findViewById(R.id.gebruikersnaam);
        wachtwoord = findViewById(R.id.wachtwoord);
        registreerBtn = findViewById(R.id.registreerBtn);

        registreerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (naam.getText().toString().isEmpty()) {

                    naam.setError("Gelieve uw naam in te voeren");

                } else if (voornaam.getText().toString().isEmpty()) {

                    voornaam.setError("Gelieve uw voornaam in te voeren");

                } else if (email.getText().toString().isEmpty()) {

                    email.setError("Gelieve uw e-mailadres in te voeren");

                } else if (gebruikersnaam.getText().toString().isEmpty()) {

                    gebruikersnaam.setError("Gelieve een gebruikersnaam te kiezen");

                } else if (wachtwoord.getText().toString().isEmpty()) {

                    wachtwoord.setError("Gelieve een wachtwoord te kiezen");

                } else {

                    Toast.makeText(getApplicationContext(), "Succesvol geregistreerd", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);

                }

            }
        });
    }
}
