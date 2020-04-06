package com.example.fitflex;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class MaakWorkoutTab extends Fragment implements View.OnClickListener {

    private Button volgende;
    private TextInputLayout naamWorkout;
    private View view;
    private RelativeLayout maakWorkout;
    private Animation shakeAnimation;

    public MaakWorkoutTab() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.maak_workout_tab, container, false);
        initViews();
        setListeners();
        return view;

    }
    
    private void initViews() {

        volgende = view.findViewById(R.id.volgende);
        naamWorkout = view.findViewById(R.id.naamWorkout);
        maakWorkout = view.findViewById(R.id.maak_workout_tab);

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

    }

    private void setListeners() {

        volgende.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String workoutNaam = naamWorkout.getEditText().getText().toString();

        if (workoutNaam.equals("") || workoutNaam.length() == 0) {

            maakWorkout.startAnimation(shakeAnimation);
            Toast.makeText(getContext(), "Voer een naam in!", Toast.LENGTH_SHORT).show();

        } else if (workoutNaam.length() < 4) {

            maakWorkout.startAnimation(shakeAnimation);
            Toast.makeText(getContext(), "Naam moet meer dan drie letters bevatten!", Toast.LENGTH_SHORT).show();

        } else {

            Intent i = new Intent(getActivity(), StelWorkoutSamen.class);
            i.putExtra("naamWorkout", workoutNaam);
            startActivity(i);

        }

    }
}
