package com.example.fitflex.dummy;

import java.util.List;

public class Track {
    public String email;
    public long timestamp;
    public List<TrackData> data;

    public void setData(List<TrackData> data) {
        this.data = data;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Track{" +
                "email='" + email + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }

    public class TrackData{
        public String workout_name;
        public int number_of_reps;

        public void setNumber_of_reps(int number_of_reps) {
            this.number_of_reps = number_of_reps;
        }

        public void setWorkout_name(String workout_name) {
            this.workout_name = workout_name;
        }

        @Override
        public String toString() {
            return "TrackData{" +
                    "workout_name='" + workout_name + '\'' +
                    ", number_of_reps=" + number_of_reps +
                    '}';
        }
    }
}
