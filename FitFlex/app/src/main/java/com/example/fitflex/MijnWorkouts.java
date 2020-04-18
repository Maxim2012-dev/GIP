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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MijnWorkouts extends Fragment {

    ArrayList<Oefening> oefeningen;
    ArrayList<Workout> workouts;
    WorkoutAdapter workoutAdapter;

    private FirebaseUser fuser;
    private DatabaseReference reff;

    private View view;
    private ListView listView;
    private TextView geenWorkouts;

    private String gebruikersEmail;
    private String naam;
    private int aantalRondes;
    private String rustNaRonde;
    private String rustNaOefening;

    private String naamOefening;
    private String moeilijkheid;
    private int aantalReps;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.mijn_workouts, container, false);
        listView = view.findViewById(R.id.workoutLijst);
        geenWorkouts = view.findViewById(R.id.geenWorkouts);

        workouts = ((MyApplication) this.getActivity().getApplication()).getWorkoutlijst();
        oefeningen = new ArrayList<>();

        if (workouts.size() == 0) {

            geenWorkouts.setText("Je hebt nog geen workouts");

        }

        reff = FirebaseDatabase.getInstance().getReference("GemaakteWorkout");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        final String fuserEmail = fuser.getEmail();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("gebruikersEmail").getValue().equals(fuserEmail)) {

                        gebruikersEmail = fuserEmail;
                        naam = ds.child("naam").getValue(String.class);
                        aantalRondes = ds.child("aantalRondes").getValue(Integer.class);
                        rustNaRonde = ds.child("rustNaRonde").getValue(String.class);
                        rustNaOefening = ds.child("rustNaOefening").getValue(String.class);

                        for (DataSnapshot oefening : ds.child("oefeningen").getChildren()) {

                            naamOefening = oefening.child("naam").getValue(String.class);
                            moeilijkheid = oefening.child("moeilijkheid").getValue(String.class);
                            aantalReps = oefening.child("aantalReps").getValue(Integer.class);

                            Oefening oefeningVanFirebase = new Oefening(naamOefening, moeilijkheid);
                            oefeningVanFirebase.setAantalReps(aantalReps);
                            oefeningen.add(oefeningVanFirebase);

                        }
                        workouts.add(new Workout(gebruikersEmail, naam, aantalRondes, rustNaRonde, rustNaOefening, oefeningen));
                        workoutAdapter = new WorkoutAdapter(MijnWorkouts.this.getContext(), workouts);
                        listView.setAdapter(workoutAdapter);
                        geenWorkouts.setText("Je hebt " + workouts.size() + " workouts");

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;

    }

}
