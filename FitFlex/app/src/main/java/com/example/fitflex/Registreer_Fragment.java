package com.example.fitflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registreer_Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText naam, email, telefoonnummer, locatie,
            wachtwoord, bevestigWachtwoord;
    private TextView alGebruiker;
    private Button registerknop;
    private CheckBox voorwaarden;
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;

    private long maxGebruikersNr;
    DatabaseReference reff;
    private FirebaseAuth mAuth;

    public Registreer_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registreer_layout, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews();
        setListeners();
        return view;
    }

    // Initialize all views
    private void initViews() {

        naam = view.findViewById(R.id.naam);
        email = view.findViewById(R.id.email);
        telefoonnummer = view.findViewById(R.id.telefoonnummer);
        locatie = view.findViewById(R.id.locatie);
        wachtwoord = view.findViewById(R.id.wachtwoord);
        bevestigWachtwoord = view.findViewById(R.id.bevestigWachtwoord);
        registerknop = view.findViewById(R.id.registerknop);
        alGebruiker = view.findViewById(R.id.alGebruiker);
        voorwaarden = view.findViewById(R.id.voorwaarden);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        reff = FirebaseDatabase.getInstance().getReference("Gebruiker");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    maxGebruikersNr = dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mAuth = FirebaseAuth.getInstance();

    }

    // Set Listeners
    private void setListeners() {
        registerknop.setOnClickListener(this);
        alGebruiker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerknop:
                Gebruiker gebruiker = new Gebruiker();
                // Call checkValidation method
                checkValidation(gebruiker);
                break;

            case R.id.alGebruiker:

                // Replace login fragment
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frameContainer, new Login_Fragment())
                        .commit();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation(Gebruiker gebruiker) {

        // Get all edittext texts
        final String getNaam = naam.getText().toString();
        final String getEmailId = email.getText().toString();
        final String getTelefoonnummer = telefoonnummer.getText().toString();
        final String getLocatie = locatie.getText().toString();
        final String getWachtwoord = wachtwoord.getText().toString();
        String getBevestigWachtwoord = bevestigWachtwoord.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getNaam.equals("") || getNaam.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getTelefoonnummer.equals("") || getTelefoonnummer.length() == 0
                || getLocatie.equals("") || getLocatie.length() == 0
                || getWachtwoord.equals("") || getWachtwoord.length() == 0
                || getBevestigWachtwoord.equals("")
                || getBevestigWachtwoord.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul alle velden in.");

            // Check if email id valid or not
        /*else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Het e-mailadres is ongeldig.");*/


        else if (!getBevestigWachtwoord.equals(getWachtwoord))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Beide wachtwoorden moeten gelijk zijn.");


        else if (!voorwaarden.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Accepteer de algemene voorwaarden.");


        else {

            gebruiker.setNaam(getNaam);
            gebruiker.setEmailID(getEmailId);
            gebruiker.setLocatie(getLocatie);
            gebruiker.setWachtwoord(getWachtwoord);
            gebruiker.setTelefoonnummer(getTelefoonnummer);

            reff.child(String.valueOf(maxGebruikersNr + 1)).setValue(gebruiker);

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(getEmailId, getWachtwoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Succesvol aangemaakt", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}


