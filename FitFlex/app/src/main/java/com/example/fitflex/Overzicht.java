package com.example.fitflex;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Overzicht extends Fragment {

    View view;
    BarChart barChart;
    ArrayList<BarEntry> barEntrylijst;
    ArrayList<String> labelnamen;


    ArrayList<GrafiekData> grafiekDatalijst = new ArrayList<>();

    DatabaseReference reff;
    FirebaseAuth mAuth;
    FirebaseUser fUser;
    String gebruikersEmail;

    int aantalReps;
    String datum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.overzicht_layout, container, false);
        barChart = view.findViewById(R.id.barChart);

        barEntrylijst = new ArrayList<>();
        labelnamen = new ArrayList<>();
        vulGrafiekDatalijst();
        prepareChart();

        return view;
    }

    private void prepareChart() {

        for (int i = 0; i < grafiekDatalijst.size(); i++) {

            String datum = grafiekDatalijst.get(i).getDatum();
            int repetities = grafiekDatalijst.get(i).getRepetities();
            barEntrylijst.add(new BarEntry(i, repetities));
            labelnamen.add(datum);

        }

        BarDataSet barDataSet = new BarDataSet(barEntrylijst, "aantal repetities");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        Description description = new Description();
        description.setText("Datum");
        description.setTextSize(20f);
        barChart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        //Zet x-as Formatter
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelnamen));

        //x-as voorkeuren
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelnamen.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();

    }

    private void vulGrafiekDatalijst() {

        reff = FirebaseDatabase.getInstance().getReference("Track");
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        gebruikersEmail = fUser.getEmail();

        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (ds.child("email").getValue().equals(gebruikersEmail)) {

                        datum = ds.child("datum").getValue().toString();

                        for (DataSnapshot oefening : ds.child("data").getChildren()) {

                            aantalReps += oefening.child("aantal_reps").getValue(Integer.class);

                        }

                    }

                }
                grafiekDatalijst.add(new GrafiekData(datum, aantalReps));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
