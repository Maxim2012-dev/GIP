package com.example.fitflex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.fitflex.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar homeToolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(homeToolbar);

        floatingActionButton = findViewById(R.id.fab);
        mAuth = FirebaseAuth.getInstance();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setIcon(R.drawable.graphic_eq);
        tabs.getTabAt(1).setIcon(R.drawable.toc);
        tabs.getTabAt(2).setIcon(R.drawable.add_box);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfielActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            logGebruikerUit();
        }
        return true;
    }

    private void logGebruikerUit() {

        new AlertDialog.Builder(HomeActivity.this)
                .setCancelable(false)
                .setIcon(R.drawable.logout_green)
                .setTitle("Uitloggen?")
                .setMessage("Weet je zeker dat je wilt uitloggen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mAuth.signOut();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.home), "Succesvol uitgelogd", "succes");

                    }
                })
                .setNegativeButton("Nee", null)
                .show();

    }
}