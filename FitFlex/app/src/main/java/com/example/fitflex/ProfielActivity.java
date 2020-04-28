package com.example.fitflex;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfielActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private DatabaseReference reff;

    private EditText email;
    private EditText wachtwoord;
    private EditText telefoon;
    private EditText locatie;
    private TextView naam;
    private Button uitlogknop;
    private Button updateknop;

    String validatieEmail;
    String gebruikersID;

    String _NAAM, _EMAIL, _WACHTWOORD, _TELEFOON, _LOCATIE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        initViews();

        toonGebruikersInfo();

        uitlogknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logGebruikerUit();
            }
        });

        updateknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGebruikersInfo();
            }
        });

    }

    private void updateGebruikersInfo() {

        if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(wachtwoord.getText().toString())
                || TextUtils.isEmpty(telefoon.getText().toString()) || TextUtils.isEmpty(locatie.getText().toString())) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profielcontainer), "Eén of meerdere velden zijn leeg", "error");

        } else if (isEmailVeranderd() || isWachtwoordVeranderd()
                || isTelefoonVeranderd() || isLocatieVeranderd()) {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profielcontainer), "Gegevens gewijzigd!", "succes");

        } else {

            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profielcontainer), "Gegevens zijn identiek", "error");

        }

    }

    private boolean isLocatieVeranderd() {

        if (!_LOCATIE.equals(locatie.getText().toString())) {
            reff.child(gebruikersID).child("locatie").setValue(locatie.getText().toString());
            _LOCATIE = locatie.getText().toString();
            return true;
        } else {
            return false;
        }

    }

    private boolean isTelefoonVeranderd() {

        if (!_TELEFOON.equals(telefoon.getText().toString())) {
            reff.child(gebruikersID).child("telefoon").setValue(telefoon.getText().toString());
            _TELEFOON = telefoon.getText().toString();
            return true;
        } else {
            return false;
        }

    }

    private boolean isWachtwoordVeranderd() {

        if (!_WACHTWOORD.equals(wachtwoord.getText().toString())) {
            reff.child(gebruikersID).child("wachtwoord").setValue(wachtwoord.getText().toString());
            _WACHTWOORD = wachtwoord.getText().toString();
            return true;
        } else {
            return false;
        }

    }

    private boolean isEmailVeranderd() {

        if (!_EMAIL.equals(email.getText().toString())) {
            reff.child(gebruikersID).child("emailID").setValue(email.getText().toString());
            _EMAIL = email.getText().toString();
            return true;
        } else {
            return false;
        }

    }

    private void initViews() {

        reff = FirebaseDatabase.getInstance().getReference("Gebruiker");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        validatieEmail = fuser.getEmail();
        gebruikersID = fuser.getUid();

        naam = findViewById(R.id.profiel_naam);
        email = findViewById(R.id.profiel_email);
        wachtwoord = findViewById(R.id.profiel_wachtwoord);
        telefoon = findViewById(R.id.profiel_telefoonnummer);
        locatie = findViewById(R.id.profiel_locatie);
        uitlogknop = findViewById(R.id.uitlogknop);
        updateknop = findViewById(R.id.updateknop);

    }

    private void logGebruikerUit() {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profielcontainer), "Succesvol uitgelogd", "succes");

    }

    public void toonGebruikersInfo() {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("emailID").getValue().equals(validatieEmail)) {

                        _EMAIL = validatieEmail;
                        _WACHTWOORD = ds.child("wachtwoord").getValue(String.class);
                        _TELEFOON = ds.child("telefoonnummer").getValue(String.class);
                        _LOCATIE = ds.child("locatie").getValue(String.class);
                        _NAAM = ds.child("naam").getValue(String.class);

                        email.setText(validatieEmail);
                        wachtwoord.setText(_WACHTWOORD);
                        telefoon.setText(_TELEFOON);
                        locatie.setText(_LOCATIE);
                        naam.setText(_NAAM);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
