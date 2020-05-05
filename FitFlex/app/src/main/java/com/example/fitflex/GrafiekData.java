package com.example.fitflex;

public class GrafiekData {

    String datum;
    int repetities;

    public GrafiekData(String datum, int repetities) {

        this.datum = datum;
        this.repetities = repetities;

    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getRepetities() {
        return repetities;
    }

    public void setRepetities(int repetities) {
        this.repetities = repetities;
    }

}
