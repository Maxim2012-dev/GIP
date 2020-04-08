package com.example.fitflex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Oefening> oefeningen;
    TextView mijnWorkoutOefNaam;
    TextView moeilijkheid;

    public CustomAdapter(Context context, ArrayList<Oefening> oefeningen) {

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }

        Oefening tempOef = (Oefening) getItem(position);

        mijnWorkoutOefNaam = convertView.findViewById(R.id.mijnWorkoutOefNaam);
        moeilijkheid = convertView.findViewById(R.id.moeilijkheid);

        mijnWorkoutOefNaam.setText(tempOef.getNaam());
        moeilijkheid.setText(tempOef.getMoeilijkheid());

        return convertView;
    }

}
