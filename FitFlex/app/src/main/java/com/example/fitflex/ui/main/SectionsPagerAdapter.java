package com.example.fitflex.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.fitflex.Overzicht;
import com.example.fitflex.MijnWorkouts;
import com.example.fitflex.MaakWorkoutTab;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new Overzicht();
                break;
            case 1:
                fragment = new MijnWorkouts();
                break;
            case 2:
                fragment = new MaakWorkoutTab();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Overzicht";
            case 1:
                return "Workouts";
            case 2:
                return "Nieuw";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}