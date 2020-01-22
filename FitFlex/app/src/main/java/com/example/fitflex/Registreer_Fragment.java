package com.example.fitflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registreer_Fragment extends Fragment implements View.OnClickListener {

    private View view;
    private EditText naam, email, telefoonnummer, locatie,
            wachtwoord, bevestigWachtwoord;
    private TextView alGebruiker;
    private Button registerknop;
    private CheckBox voorwaarden;

    public Registreer_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registreer_layout, container, false);
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
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getFullName = naam.getText().toString();
        String getEmailId = email.getText().toString();
        String getMobileNumber = telefoonnummer.getText().toString();
        String getLocation = locatie.getText().toString();
        String getPassword = wachtwoord.getText().toString();
        String getConfirmPassword = bevestigWachtwoord.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul alle velden in.");

            // Check if email id valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Het e-mailadres is ongeldig.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Beide wachtwoorden moeten gelijk zijn.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!voorwaarden.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Accepteer de algemene voorwaarden.");

            // Else do signup or do your stuff
        else
            Toast.makeText(getActivity(), "Do SignUp.", Toast.LENGTH_SHORT)
                    .show();

    }


}
