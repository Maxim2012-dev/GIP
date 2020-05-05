package com.example.fitflex.dummy;

import java.util.List;

public class Track {
    public String email;
    public String datum;
    public List<TrackData> data;

    public void setData(List<TrackData> data) {
        this.data = data;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        return "Track{" +
                "email='" + email + '\'' +
                ", datum=" + datum +
                ", data=" + data +
                '}';
    }

    public class TrackData{
        public String oefening_naam;
        public int aantal_reps;

        public void setOefening_naam(String oefening_naam) {
            this.oefening_naam = oefening_naam;
        }

        public void setAantal_reps(int aantal_reps) {
            this.aantal_reps = aantal_reps;
        }

        @Override
        public String toString() {
            return "TrackData{" +
                    "oefening_naam='" + oefening_naam + '\'' +
                    ", aantal_repetities=" + aantal_reps +
                    '}';
        }
    }
}
