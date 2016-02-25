package com.learninghorizon.skibuddy.tracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.learninghorizon.skibuddy.R;

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected static final String FRAGMENT_TAG = "SingleFragmentActivity.Fragment";
    
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout fl = new FrameLayout(this);
        fl.setId(R.id.fragmentContainer);
        setContentView(fl);
        
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
        }
    }
}
