package com.example.fitflex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Workout> workouts;
    ImageView foto;
    TextView naamWorkout;
    TextView aantalOefeningen;

    public WorkoutAdapter(Context context, ArrayList<Workout> workouts) {
        mContext = context;
        this.workouts = workouts;
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Object getItem(int position) {
        return workouts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.workout_item, parent, false);
        }

        Workout tempWorkout = (Workout) getItem(position);

        foto = convertView.findViewById(R.id.foto);
        naamWorkout = convertView.findViewById(R.id.naamW);
        aantalOefeningen = convertView.findViewById(R.id.aantalOefeningen);

        foto.setImageResource(R.drawable.logo);
        naamWorkout.setText(tempWorkout.getNaam());
        aantalOefeningen.setText(tempWorkout.getOefeningen().size() + " oefeningen");

        return convertView;
    }
}