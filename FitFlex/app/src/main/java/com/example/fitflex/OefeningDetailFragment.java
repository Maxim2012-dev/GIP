package com.example.fitflex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitflex.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A fragment representing a single Oefening detail screen.
 * This fragment is either contained in a {@link OefeningListActivity}
 * in two-pane mode (on tablets) or a {@link OefeningDetailActivity}
 * on handsets.
 */
public class OefeningDetailFragment extends Fragment {

    private Button toevoegknop;
    private EditText aantalReps;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OefeningDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.naam);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.oefening_detail, container, false);

        aantalReps = rootView.findViewById(R.id.aantalReps);
        toevoegknop = rootView.findViewById(R.id.toevoegknop);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {

            ((TextView) rootView.findViewById(R.id.oefening_detail)).setText(mItem.details);

            rootView.findViewById(R.id.toevoegknop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (aantalReps.getText().toString().trim().isEmpty()) {

                        new CustomToast().Show_Toast(Objects.requireNonNull(getActivity()), rootView, "Voer het aantal reps in!");

                    } else if (Integer.parseInt(aantalReps.getText().toString()) <= 0) {

                        new CustomToast().Show_Toast(Objects.requireNonNull(getActivity()), rootView, "Reps moeten hoger zijn dan 0!");

                    } else {

                        Oefening nieuweOefening = new Oefening(mItem.naam, mItem.moeilijkheid);

                        nieuweOefening.setAantalReps(Integer.parseInt(aantalReps.getText().toString()));

                        ((MyApplication) OefeningDetailFragment.this
                                .getActivity()
                                .getApplication())
                                .getOefeningen()
                                .add(nieuweOefening);

                        startActivity(new Intent(getActivity(), StelWorkoutSamen.class));

                    }

                }
            });

        }
        return rootView;
    }
}
