package com.example.fitflex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OefeningAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Oefening> oefeningen;
    TextView mijnWorkoutOefNaam;
    TextView hoeveelheidReps;

    private OnListItemClickListener onListItemClickListener;

    public OefeningAdapter(Context context, ArrayList<Oefening> oefeningen) {

        mContext = context;
        this.oefeningen = oefeningen;

    }

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        }

        Oefening tempOef = (Oefening) getItem(position);

        mijnWorkoutOefNaam = convertView.findViewById(R.id.mijnWorkoutOefNaam);
        hoeveelheidReps = convertView.findViewById(R.id.hoeveelheidReps);

        mijnWorkoutOefNaam.setText(tempOef.getNaam());
        hoeveelheidReps.setText(tempOef.getAantalReps() + " reps");

        convertView.findViewById(R.id.verwijder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onListItemClickListener!=null){
                    onListItemClickListener.onItemRemove(position);
                }
            }
        });

        return convertView;
    }

    public interface  OnListItemClickListener{
        public void onItemRemove(int position);
    }
}
