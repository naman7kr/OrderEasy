package com.oeasy.ordereasy.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oeasy.ordereasy.Fragments.HomeFragment;
import com.oeasy.ordereasy.Fragments.ProfileFragment;
import com.oeasy.ordereasy.Fragments.AboutUsFragment;

/**
 * Created by Stan on 4/4/2018.
 */

public class MainBtmAdapter extends FragmentPagerAdapter {
    public MainBtmAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ProfileFragment();
            case 2:
                return new AboutUsFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
