package com.example.fitflex;

public class Gebruiker {

    public String naam, emailID, telefoonnummer, locatie;
    public int aantalWorkouts, aantalOefeningen;

    public Gebruiker() {
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public int getAantalWorkouts() {
        return aantalWorkouts;
    }

    public void setAantalWorkouts(int aantalWorkouts) {
        this.aantalWorkouts = aantalWorkouts;
    }

    public int getAantalOefeningen() {
        return aantalOefeningen;
    }

    public void setAantalOefeningen(int aantalOefeningen) {
        this.aantalOefeningen = aantalOefeningen;
    }
}
