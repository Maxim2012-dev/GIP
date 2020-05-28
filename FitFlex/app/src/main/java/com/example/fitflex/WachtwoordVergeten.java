package com.example.fitflex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class WachtwoordVergeten extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private EditText emailId;
    private TextView bevestig, terug;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wachtwoord_vergeten);

        initViews();
        setListeners();

    }

    private void initViews() {

        view = findViewById(R.id.wachtwoord_vergeten_layout);
        emailId = findViewById(R.id.geregistreerde_email);
        bevestig = findViewById(R.id.bevestig);
        terug = findViewById(R.id.terug);

        mAuth = FirebaseAuth.getInstance();

    }

    private void setListeners() {

        terug.setOnClickListener(this);
        bevestig.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.terug:
                onBackPressed();
                break;
            case R.id.bevestig:
                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        if (getEmailId.equals("") || getEmailId.length() == 0) {

            new CustomToast().Show_Toast(this, view,
                    "Gelieve uw e-mailadres in te voeren.", "error");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {

            new CustomToast().Show_Toast(this, view,
                    "Dit e-mailadres is ongeldig.", "error");

        } else {

            mAuth.sendPasswordResetEmail(getEmailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @SuppressLint("ShowToast")
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        new CustomToast().Show_Toast(getApplicationContext(), view, "Email succesvol verzonden!", "succes");

                    } else {

                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);

                    }

                }
            });

        }

    }

}
