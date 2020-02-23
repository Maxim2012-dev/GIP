package com.example.fitflex;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements View.OnClickListener {

    private View view;

    private EditText email, wachtwoord;
    private Button loginknop;
    private TextView vergetenWachtwoord, maakAccount;
    private CheckBox toon_verberg_wachtwoord;
    private ProgressBar progressBarL;
    private LinearLayout loginLayout;
    private Animation shakeAnimation;
    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;

    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        email = view.findViewById(R.id.login_email);
        wachtwoord = view.findViewById(R.id.login_wachtwoord);
        loginknop = view.findViewById(R.id.loginknop);
        vergetenWachtwoord = view.findViewById(R.id.vergetenWachtwoord);
        maakAccount = view.findViewById(R.id.maakAccount);
        toon_verberg_wachtwoord = view.findViewById(R.id.toon_verberg_wachtwoord);
        progressBarL = view.findViewById(R.id.progressBarL);
        loginLayout = view.findViewById(R.id.login_layout);

        progressBarL.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

    }

    private void setListeners() {
        loginknop.setOnClickListener(this);
        vergetenWachtwoord.setOnClickListener(this);
        maakAccount.setOnClickListener(this);

        toon_verberg_wachtwoord
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        if (isChecked) {

                            toon_verberg_wachtwoord.setText(R.string.verberg_pwd);

                            wachtwoord.setInputType(InputType.TYPE_CLASS_TEXT);
                            wachtwoord.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());
                        } else {
                            toon_verberg_wachtwoord.setText(R.string.toon_pwd);

                            wachtwoord.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            wachtwoord.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginknop:
                checkValidation();
                break;

            case R.id.vergetenWachtwoord:

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new WachtwoordVergeten_Fragment()).commit();
                break;
            case R.id.maakAccount:

                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new Registreer_Fragment()).commit();
                break;
        }

    }

    private void checkValidation() {

        String getEmailId = email.getText().toString();
        String getWachtwoord = wachtwoord.getText().toString();

        progressBarL.setVisibility(View.VISIBLE);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getWachtwoord.equals("") || getWachtwoord.length() == 0) {

            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul beide velden in!");

        } else {

            mAuth.signInWithEmailAndPassword(getEmailId, getWachtwoord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        progressBarL.setVisibility(View.GONE);

                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {

                        Toast.makeText(getContext(), "Wachtwoord incorrect.", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }

}
