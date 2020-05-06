package com.example.fitflex;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class WachtwoordVergeten_Fragment extends Fragment implements View.OnClickListener {

    private View view;

    private EditText emailId;
    private TextView bevestig, terug;
    private FragmentManager fragmentManager;

    private FirebaseAuth mAuth;

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

        emailId = view.findViewById(R.id.geregistreerde_email);
        bevestig = view.findViewById(R.id.bevestig);
        terug = view.findViewById(R.id.terug);

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

                break;

            case R.id.bevestig:

                submitButtonTask();
                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        if (getEmailId.equals("") || getEmailId.length() == 0) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Gelieve uw e-mailadres in te voeren.", "error");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(getEmailId).matches()) {

            new CustomToast().Show_Toast(getActivity(), view,
                    "Dit e-mailadres is ongeldig.", "error");

        } else {

            mAuth.sendPasswordResetEmail(getEmailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {

                        Toast.makeText(getActivity().getApplicationContext(), "Email succesvol verzonden", Toast.LENGTH_SHORT);

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);

                    }

                }
            });

        }

    }

}
