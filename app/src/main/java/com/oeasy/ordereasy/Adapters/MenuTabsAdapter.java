package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oeasy.ordereasy.Fragments.DessertFragment;
import com.oeasy.ordereasy.Fragments.DrinksFragment;
import com.oeasy.ordereasy.Fragments.MainCourseFragment;
import com.oeasy.ordereasy.Fragments.RecommendedFragment;
import com.oeasy.ordereasy.Fragments.StartersFragment;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stan on 4/6/2018.
 */

public class MenuTabsAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public MenuTabsAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
    public void addFragment(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
}
