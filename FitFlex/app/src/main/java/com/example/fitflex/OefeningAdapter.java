package com.example.fitflex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OefeningAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Oefening> oefeningen;
    TextView mijnWorkoutOefNaam;
    TextView hoeveelheidReps;

    public OefeningAdapter(Context context, ArrayList<Oefening> oefeningen) {

        mContext = context;
        this.oefeningen = oefeningen;

    }

    @Override
    public int getCount() {
        return oefeningen.size();
    }

    @Override
    public Object getItem(int position) {
        return oefeningen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }

        Oefening tempOef = (Oefening) getItem(position);

        mijnWorkoutOefNaam = convertView.findViewById(R.id.mijnWorkoutOefNaam);
        hoeveelheidReps = convertView.findViewById(R.id.hoeveelheidReps);

        mijnWorkoutOefNaam.setText(tempOef.getNaam());
        hoeveelheidReps.setText(tempOef.getAantalReps() + " reps");

        return convertView;
    }

}
