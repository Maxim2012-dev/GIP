package com.example.fitflex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MijnWorkouts extends Fragment {

    ArrayList<Workout> workouts;
    WorkoutAdapter workoutAdapter;

    private View view;
    private ListView listView;
    private TextView geenWorkouts;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mijn_workouts, container, false);
        listView = view.findViewById(R.id.workoutLijst);
        geenWorkouts = view.findViewById(R.id.geenWorkouts);
        workouts = ((MyApplication) MijnWorkouts.this.getActivity().getApplication()).getWorkoutlijst();

        if (workouts.size() == 0) {

            geenWorkouts.setText("Je hebt nog geen workouts");

        } else {

            workoutAdapter = new WorkoutAdapter(MijnWorkouts.this.getContext(), workouts);
            listView.setAdapter(workoutAdapter);

        }

        return view;
    }

}
