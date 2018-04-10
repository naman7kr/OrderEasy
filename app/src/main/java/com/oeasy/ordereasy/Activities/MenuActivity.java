package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

public class MenuActivity extends BaseActivity {
    private ViewPager mPager;
    public TabLayout mTab;
    private MenuTabsAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initialize();
        setToolbar();
        setTabs();

        int sTab = getIntent().getIntExtra("START_TAB", 0);
        mPager.setCurrentItem(sTab);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("Menu");
        toolbar.setNavigationIcon(R.drawable.ic_action_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_cart:
                startActivity(new Intent(this,CartActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
