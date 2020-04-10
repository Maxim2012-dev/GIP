package com.example.fitflex;

import java.util.ArrayList;

public class Workout {

    public String naam;
    public ArrayList<Oefening> oefeningen;

    public Workout(String naam, ArrayList<Oefening> oefeningen) {

        this.naam = naam;
        this.oefeningen = oefeningen;

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public ArrayList<Oefening> getOefeningen() {
        return oefeningen;
    }

    public void setOefeningen(ArrayList<Oefening> oefeningen) {
        this.oefeningen = oefeningen;
    }
}
