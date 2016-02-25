package com.learninghorizon.skibuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.learninghorizon.skibuddy.fragments.AttendiesSessions;

/**
 * Created by ramnivasindani on 12/2/15.
 */
public class AttendiesSkiSessions extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendies_fragment);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.attendies_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AttendiesSessions firstFragment = new AttendiesSessions();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.attendies_fragment_container, firstFragment).commit();
        }
    }
}
