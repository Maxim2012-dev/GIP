package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    TextView registerLink;
    EditText gebruikersnaam;
    EditText wachtwoord;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = findViewById(R.id.registerLink);
        gebruikersnaam = findViewById(R.id.gebruikersnaam);
        wachtwoord = findViewById(R.id.wachtwoord);
        loginBtn = findViewById(R.id.loginBtn);

        SpannableString registreer = new SpannableString("Registreer");
        registreer.setSpan(new UnderlineSpan(), 0, registreer.length(), 0);
        registerLink.setText(registreer);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Registreer.class);
                startActivity(intent);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gebruikersnaam.getText().toString().isEmpty()) {

                    gebruikersnaam.setError("Gelieve uw gebruikersnaam op te geven");

                } else if (wachtwoord.getText().toString().isEmpty()) {

                    wachtwoord.setError("Gelieve uw wachtwoord op te geven");

                } else {

                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);

                }

            }
        });

    }
}
