package com.twobonkers.hungry.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twobonkers.hungry.presentation.favourite.FavouritesFragment;
import com.twobonkers.hungry.presentation.feed.FeedFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FeedFragment.newInstance();
            case 1:
                return FavouritesFragment.newInstance();
            default:
                throw new IndexOutOfBoundsException("Invalid position " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
