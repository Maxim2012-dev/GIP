package com.example.fitflex;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MijnWorkouts extends Fragment {

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

        ((MyApplication) getActivity().getApplication()).setHuidigeWorkout(null);
        workouts = new ArrayList<>();

        if (workouts.size() == 0) {

            geenWorkouts.setText("Je hebt nog geen workouts");

        }
        toonGemaakteWorkouts();

        return view;

    }

    private void toonGemaakteWorkouts() {

        reff = FirebaseDatabase.getInstance().getReference("GemaakteWorkout");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        final String fuserEmail = fuser.getEmail();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("gebruikersEmail").getValue().equals(fuserEmail)) {

                        ArrayList<Oefening> oefeningen = new ArrayList<>();

                        gebruikersEmail = fuserEmail;
                        naam = ds.child("naam").getValue(String.class);
                        aantalRondes = ds.child("aantalRondes").getValue(Integer.class);
                        rustNaRonde = ds.child("rustNaRonde").getValue(String.class);
                        rustNaOefening = ds.child("rustNaOefening").getValue(String.class);

                        if (ds.child("naam").getValue().equals(naam)) {

                            for (DataSnapshot oefening : ds.child("oefeningen").getChildren()) {

                                naamOefening = oefening.child("naam").getValue(String.class);
                                moeilijkheid = oefening.child("moeilijkheid").getValue(String.class);
                                aantalReps = oefening.child("aantalReps").getValue(Integer.class);

                                Oefening oefeningVanFirebase = new Oefening(naamOefening, moeilijkheid);
                                oefeningVanFirebase.setAantalReps(aantalReps);
                                oefeningen.add(oefeningVanFirebase);

                            }
                        }
                        workouts.add(new Workout(gebruikersEmail, naam, aantalRondes, rustNaRonde, rustNaOefening, oefeningen));

                    }

                }
                workoutAdapter = new WorkoutAdapter(MijnWorkouts.this.getContext(), workouts);
                listView.setAdapter(workoutAdapter);
                workoutAdapter.setOnListItemClickListener(new WorkoutAdapter.OnListItemClickListener() {
                    @Override
                    public void onItemRemove(final int position) {

                        removeSelectedItem(reff,position,fuserEmail);

                    }

                    @Override
                    public void onItemClick(int position) {
                        ((MyApplication) getActivity().getApplication()).setHuidigeWorkout(workouts.get(position));
                        startActivity(new Intent(getContext(), WorkoutBegin.class));
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void removeSelectedItem(final DatabaseReference reff,final int position, final String userEmail) {
        new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.cancel_red)
                .setTitle("Ben je zeker?")
                .setMessage("Wil je dit item verwijderen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    if (ds.child("gebruikersEmail").getValue().equals(userEmail)) {
                                        if (ds.child("naam").getValue().equals(workouts.get(position).naam)) {
                                            ds.getRef().removeValue();
                                            workouts.remove(position);
                                            workoutAdapter.notifyDataSetChanged();
                                            if (workouts.size()==0){
                                                geenWorkouts.setText("Nog geen workouts");
                                            }
                                            return;
                                        }

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    })
                .setNegativeButton("Nee",null)
                .show();
        ;

}
}
