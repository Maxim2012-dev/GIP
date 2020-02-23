package com.example.fitflex;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WachtwoordVergeten_Fragment extends Fragment implements View.OnClickListener {

    private View view;

    private EditText emailId;
    private TextView submit, back;
    private FragmentManager fragmentManager;

    public WachtwoordVergeten_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wachtwoord_vergeten, container,
                false);
        fragmentManager = getActivity().getSupportFragmentManager();
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {

        emailId = view.findViewById(R.id.registered_emailid);
        submit = view.findViewById(R.id.forgot_button);
        back = view.findViewById(R.id.backToLoginBtn);

    }

    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .replace(R.id.frameContainer, new Login_Fragment())
                        .commit();
                break;

            case R.id.forgot_button:

                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        if (getEmailId.equals("") || getEmailId.length() == 0) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Gelieve uw e-mailadres in te voeren.");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Dit e-mailadres is ongeldig.");

        } else {

            Toast.makeText(getActivity(), "Get Forgot Password.",
                    Toast.LENGTH_SHORT).show();

        }

    }

}
