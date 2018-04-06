package com.oeasy.ordereasy.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.oeasy.ordereasy.Adapters.MenuTabsAdapter;
import com.oeasy.ordereasy.Fragments.DessertFragment;
import com.oeasy.ordereasy.Fragments.DrinksFragment;
import com.oeasy.ordereasy.Fragments.MainCourseFragment;
import com.oeasy.ordereasy.Fragments.RecommendedFragment;
import com.oeasy.ordereasy.Fragments.StartersFragment;
import com.oeasy.ordereasy.R;

/**
 * Created by Stan on 4/6/2018.
 */

public class MenuActivity extends AppCompatActivity {
    private ViewPager mPager;
    private TabLayout mTab;
    private MenuTabsAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initialize();

        setTabs();
    }

    private void initialize() {
        mTab=findViewById(R.id.menu_tabs);
        mPager=findViewById(R.id.menu_vp);
    }
    private void setTabs() {
        adapter=new MenuTabsAdapter(this,getSupportFragmentManager());
        addTabs();
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);

    }

    private void addTabs() {
        adapter.addFragment(new RecommendedFragment(), "Recommended");
        adapter.addFragment(new StartersFragment(), "Starters");
        adapter.addFragment(new MainCourseFragment(), "Main Course");
        adapter.addFragment(new DessertFragment(), "Dessert");
        adapter.addFragment(new DrinksFragment(), "Drinks");
    }
}
