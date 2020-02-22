package com.example.fitflex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {

            replaceLoginFragment();

        }

        findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });

    }

    protected void replaceLoginFragment() {

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment())
                .commit();

    }

    @Override
    public void onBackPressed() {

        Fragment Registreer_Fragment = getSupportFragmentManager()
                .findFragmentByTag(Utils.Registreer_Fragment);
        Fragment WachtwoordVergeten_Fragment = getSupportFragmentManager()
                .findFragmentByTag(Utils.WachtwoordVergeten_Fragment);

        if (Registreer_Fragment != null)
            replaceLoginFragment();
        else if (WachtwoordVergeten_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }

}

