package com.example.fitflex;

import java.util.ArrayList;

public class Workout {

    public String gebruikersEmail;
    public String naam;
    public int aantalRondes;
    public String rustNaRonde;
    public String rustNaOefening;
    public ArrayList<Oefening> oefeningen;

    public Workout(String gebruikersEmail, String naam, int aantalRondes, String rustNaRonde, String rustNaOefening, ArrayList<Oefening> oefeningen) {

        this.gebruikersEmail = gebruikersEmail;
        this.naam = naam;
        this.aantalRondes = aantalRondes;
        this.rustNaRonde = rustNaRonde;
        this.rustNaOefening = rustNaOefening;
        this.oefeningen = oefeningen;

    }

    public String getGebruikersEmail() {
        return gebruikersEmail;
    }

    public void setGebruikersEmail(String gebruikersEmail) {
        this.gebruikersEmail = gebruikersEmail;
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

    public int getAantalRondes() {
        return aantalRondes;
    }

    public void setAantalRondes(int aantalRondes) {
        this.aantalRondes = aantalRondes;
    }

    public String getRustNaRonde() {
        return rustNaRonde;
    }

    public void setRustNaRonde(String rustNaRonde) {
        this.rustNaRonde = rustNaRonde;
    }

    public String getRustNaOefening() {
        return rustNaOefening;
    }

    public void setRustNaOefening(String rustNaOefening) {
        this.rustNaOefening = rustNaOefening;
    }
}
