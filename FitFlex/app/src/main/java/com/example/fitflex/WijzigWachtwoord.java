package com.example.fitflex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WijzigWachtwoord extends AppCompatActivity {

    private EditText nieuwWachtwoord;
    private Button wijzigKnop;
    private FirebaseAuth mAuth;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wijzig_wachtwoord);

        nieuwWachtwoord = findViewById(R.id.nieuwWachtwoord);
        wijzigKnop = findViewById(R.id.wijzigKnop);
        mAuth = FirebaseAuth.getInstance();

        wijzigKnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wijzigWachtwoord();
            }
        });

    }

    private void wijzigWachtwoord() {

        if (mAuth.getCurrentUser() != null) {

            email = mAuth.getCurrentUser().getEmail();

        }

        if (nieuwWachtwoord.getText().toString().isEmpty()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.wachtwoord_wijzigen_layout),
                    "Voer het veld in!", "error");

        } else if (!Utils.WACHTWOORD_PATROON.matcher(nieuwWachtwoord.getText()).matches()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.wachtwoord_wijzigen_layout),
                    "Het wachtwoord moet minstens 6 karakters bevatten met:\n" +
                            "- min. 1 cijfer\n" +
                            "- min. 1 kleine letter\n" +
                            "- min. 1 hoofdletter", "error");

        } else {

            mAuth.getCurrentUser().updatePassword(nieuwWachtwoord.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.wachtwoord_wijzigen_layout),
                                "Wachtwoord succesvol gewijzigd!", "succes");

                        startActivity(new Intent(getApplicationContext(), ProfielActivity.class));

                    } else {

                        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.wachtwoord_wijzigen_layout),
                                "Er is iets misgelopen!", "error");

                    }
                }
            });

        }

    }
}
