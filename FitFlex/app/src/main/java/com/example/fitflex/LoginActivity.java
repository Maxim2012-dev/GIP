package com.example.fitflex;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout email_layout, wachtwoord_layout;
    private TextInputEditText email, wachtwoord;
    private Button loginknop;
    private TextView vergetenWachtwoord, maakAccount, loginText;
    private ProgressBar progressBarL;
    private ConstraintLayout loginLayout;
    private Animation shakeAnimation;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initViews();
        setListeners();

    }

    private void initViews() {

        email_layout = findViewById(R.id.login_email_layout);
        wachtwoord_layout = findViewById(R.id.login_wachtwoord_layout);
        email = findViewById(R.id.login_email);
        wachtwoord = findViewById(R.id.login_wachtwoord);
        loginknop = findViewById(R.id.loginknop);
        vergetenWachtwoord = findViewById(R.id.vergetenWachtwoord);
        loginText = findViewById(R.id.login_text);
        maakAccount = findViewById(R.id.maakAccount);
        progressBarL = findViewById(R.id.progressBarL);
        loginLayout = findViewById(R.id.login_layout);

        progressBarL.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        shakeAnimation = AnimationUtils.loadAnimation(this,
                R.anim.shake);

    }

    private void setListeners() {

        loginknop.setOnClickListener(this);
        vergetenWachtwoord.setOnClickListener(this);
        maakAccount.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginknop:
                checkValidation();
                break;
            case R.id.vergetenWachtwoord:
                gaNaarWachtwoordVergetenPagina();
                break;
            case R.id.maakAccount:
                gaNaarRegistreerPagina();
                break;
        }
    }

    private void gaNaarWachtwoordVergetenPagina() {

        Intent intent = new Intent(LoginActivity.this, WachtwoordVergeten.class);

        Pair[] pairs = new Pair[4];

        pairs[0] = new Pair<View, String>(loginText, "login_text");
        pairs[1] = new Pair<View, String>(email_layout, "email");
        pairs[2] = new Pair<View, String>(vergetenWachtwoord, "checkbox");
        pairs[3] = new Pair<View, String>(maakAccount, "lid");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private void gaNaarRegistreerPagina() {

        Intent intent = new Intent(LoginActivity.this, RegistreerActivity.class);

        Pair[] pairs = new Pair[6];

        pairs[0] = new Pair<View, String>(loginText, "login_text");
        pairs[1] = new Pair<View, String>(email_layout, "email");
        pairs[2] = new Pair<View, String>(wachtwoord_layout, "wachtwoord");
        pairs[3] = new Pair<View, String>(vergetenWachtwoord, "checkbox");
        pairs[4] = new Pair<View, String>(loginknop, "button");
        pairs[5] = new Pair<View, String>(maakAccount, "lid");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
        startActivity(intent, options.toBundle());

    }

    private void checkValidation() {

        String getEmailId = email.getText().toString();
        String getWachtwoord = wachtwoord.getText().toString();

        progressBarL.setVisibility(View.VISIBLE);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getWachtwoord.equals("") || getWachtwoord.length() == 0) {

            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(this, findViewById(R.id.login_layout),
                    "Vul beide velden in!", "error");
            progressBarL.setVisibility(View.GONE);

        } else {

            mAuth.signInWithEmailAndPassword(getEmailId, getWachtwoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        progressBarL.setVisibility(View.GONE);

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {

                        progressBarL.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }

}


