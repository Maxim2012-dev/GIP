package com.example.fitflex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MaakWorkoutTab extends Fragment {

    private Button volgende;
    private View view;

    public MaakWorkoutTab() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.maak_workout_tab, container, false);
        initViews();
        return view;

    }
    
    private void initViews() {
        

    }
}
