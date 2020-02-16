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
import com.google.firebase.database.FirebaseDatabase;

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

                // Call checkValidation method
                checkValidation();
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
    private void checkValidation() {

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

            // Check if both password should be equal
        else if (!getBevestigWachtwoord.equals(getWachtwoord))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Beide wachtwoorden moeten gelijk zijn.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!voorwaarden.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Accepteer de algemene voorwaarden.");

            // Else do signup or do your stuff
        else

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(getEmailId, getWachtwoord)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {

                                Gebruiker gebruiker = new Gebruiker(
                                        getNaam,
                                        getEmailId,
                                        getTelefoonnummer,
                                        getLocatie,
                                        getWachtwoord
                                );

                                FirebaseDatabase.getInstance().getReference("Gebruikers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(gebruiker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(getContext(), R.string.succesvolGeregistreerd, Toast.LENGTH_LONG).show();
                                            fragmentManager.beginTransaction()
                                                    .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                                                    .replace(R.id.frameContainer, new Login_Fragment())
                                                    .commit();

                                        } else {

                                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });

                            } else {

                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

    }


}
