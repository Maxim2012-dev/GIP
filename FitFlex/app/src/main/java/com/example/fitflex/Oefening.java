package com.example.fitflex;

public class Oefening {

    public String naam, moeilijkheid;
    public int aantalReps;

    public Oefening(String naam, String moeilijkheid) {

        this.naam = naam;
        this.moeilijkheid = moeilijkheid;

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getMoeilijkheid() {
        return moeilijkheid;
    }

    public void setMoeilijkheid(String moeilijkheid) {
        this.moeilijkheid = moeilijkheid;
    }

    public int getAantalReps() {
        return aantalReps;
    }

    public void setAantalReps(int aantalReps) {
        this.aantalReps = aantalReps;
    }
}
