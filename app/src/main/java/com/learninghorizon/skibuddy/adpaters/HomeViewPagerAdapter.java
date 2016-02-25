package com.learninghorizon.skibuddy.adpaters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learninghorizon.skibuddy.fragments.EventsFragment;
import com.learninghorizon.skibuddy.fragments.SessionsFragment;

/**
 * Created by ramnivasindani on 11/22/15.
 */
public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Sessions", "Events" };
    private Context context;

    public HomeViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                return SessionsFragment.newInstance(position+1);
            }
            case 1: {
                return EventsFragment.newInstance(position+1);
            }
        }
        return SessionsFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}