package com.example.fitflex;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class ProfielActivity extends AppCompatActivity {

    private FirebaseUser fuser;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private EditText email;
    private EditText wachtwoord;
    private EditText telefoon;
    private EditText locatie;
    private EditText naam;
    private ImageView profiel_foto;
    private ProgressBar progressBar;

    String validatieEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);

        getSupportActionBar().setTitle("Profiel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Gebruiker");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        validatieEmail = user.getEmail();

        email = findViewById(R.id.profiel_email);
        wachtwoord = findViewById(R.id.profiel_wachtwoord);
        telefoon = findViewById(R.id.profiel_telefoonnummer);
        locatie = findViewById(R.id.profiel_locatie);
        naam = findViewById(R.id.profiel_naam);

        progressBar = findViewById(R.id.profiel_progressBar);
        profiel_foto = findViewById(R.id.profiel_foto);

        progressBar.setVisibility(View.GONE);

        getGebruikersInformatie();

    }

    public void getGebruikersInformatie() {

        reference.addValueEventListener(new ValueEventListener() {
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
