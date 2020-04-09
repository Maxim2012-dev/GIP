package com.example.fitflex;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    private ArrayList<Oefening> oefeningen = new ArrayList<>();

    public ArrayList<Oefening> getOefeningen() {
        return oefeningen;
    }

    public void setOefeningen(ArrayList<Oefening> oefeningen) {
        this.oefeningen = oefeningen;
    }



}
