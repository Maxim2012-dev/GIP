package com.example.fitflex;


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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements View.OnClickListener {

    private static View view;

    private EditText email, wachtwoord;
    private Button loginknop;
    private TextView vergetenWachtwoord, maakAccount;
    private CheckBox toon_verberg_wachtwoord;
    private LinearLayout loginLayout;
    private Animation shakeAnimation;
    private FragmentManager fragmentManager;

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

        email =  view.findViewById(R.id.login_email);
        wachtwoord = view.findViewById(R.id.login_wachtwoord);
        loginknop = view.findViewById(R.id.loginknop);
        vergetenWachtwoord = view.findViewById(R.id.vergetenWachtwoord);
        maakAccount = view.findViewById(R.id.maakAccount);
        toon_verberg_wachtwoord = view.findViewById(R.id.toon_verberg_wachtwoord);
        loginLayout = view.findViewById(R.id.login_layout);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        /*XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            vergetenWachtwoord.setTextColor(csl);
            toon_verberg_wachtwoord.setTextColor(csl);
            maakAccount.setTextColor(csl);
        } catch (Exception e) {
        }*/
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
        String getPassword = wachtwoord.getText().toString();

        Pattern p = Pattern.compile(Utils.regEx);

        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul beide velden in!");

        }
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Dit is geen geldig e-mailadres!");
        else
            Toast.makeText(getActivity(), "Do Login.", Toast.LENGTH_SHORT)
                    .show();

    }

}
