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

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {

            replaceLoginFragment();

        }

        //Onclick sluiticoon om af te sluiten
        findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });

    }

    // Replace Login Fragment with animation
    protected void replaceLoginFragment() {

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment())
                .commit();

    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment Registreer_Fragment = getSupportFragmentManager()
                .findFragmentByTag(Utils.Registreer_Fragment);
        Fragment WachtwoordVergeten_Fragment = getSupportFragmentManager()
                .findFragmentByTag(Utils.WachtwoordVergeten_Fragment);

        // Check if both are null or not
        // If both are not null then replace login fragment else do backpressed
        // task

        if (Registreer_Fragment != null)
            replaceLoginFragment();
        else if (WachtwoordVergeten_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }

}

