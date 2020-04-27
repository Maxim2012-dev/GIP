package com.example.fitflex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        initViews();

        toonGebruikersInformatie();

        uitlogknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logGebruikerUit();
            }
        });

        updateknop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO update gebruikersinfo
            }
        });

    }

    private void initViews() {

        reff = FirebaseDatabase.getInstance().getReference("Gebruiker");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        validatieEmail = fuser.getEmail();

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

    public void toonGebruikersInformatie() {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("emailID").getValue().equals(validatieEmail)) {

                        email.setText(validatieEmail);
                        wachtwoord.setText(ds.child("wachtwoord").getValue(String.class));
                        telefoon.setText(ds.child("telefoonnummer").getValue(String.class));
                        locatie.setText(ds.child("locatie").getValue(String.class));
                        naam.setText(ds.child("naam").getValue(String.class));

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
