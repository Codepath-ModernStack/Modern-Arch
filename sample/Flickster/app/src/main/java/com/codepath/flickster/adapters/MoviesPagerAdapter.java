package com.codepath.flickster.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.flickster.fragments.NowPlayingListFragment;
import com.codepath.flickster.fragments.TopRatedListFragment;

public class MoviesPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = MoviesPagerAdapter.class.getSimpleName();
    private static final int PAGE_COUNT = 2;

    private String tabTitles[] = new String[]{"Now Playing", "Top Rated"};
    private Context context;

    public MoviesPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NowPlayingListFragment.newInstance();
            case 1:
                return TopRatedListFragment.newInstance();
        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
