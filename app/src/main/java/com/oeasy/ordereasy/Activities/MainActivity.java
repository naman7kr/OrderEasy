package com.oeasy.ordereasy.Activities;


import android.content.Intent;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.oeasy.ordereasy.Adapters.MainBtmAdapter;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;


public class MainActivity extends BaseActivity {
    private ViewPager mPager;
    private BottomNavigationView mBtmNav;
    MainBtmAdapter adapter;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DatabaseHelper db=new DatabaseHelper(this);


        initialize();

        setToolbar();

        setFab();

        setBottomNavigation();

    }

    private void initialize() {
        mPager=findViewById(R.id.main_vp);
        mBtmNav=findViewById(R.id.main_btmnav);
        db=new DatabaseHelper(this);
    }

    private void setToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar=getToolbar();
        toolbar.setNavigationIcon(R.mipmap.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void setFab() {
        fab=getFab();
        onFabClick();
        checkWaiter();
    }

    private void checkWaiter() {
        if(db.countWaiter()!=0){
            fab.setVisibility(View.GONE);
        }else {
            fab.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if(mPager.getCurrentItem()==2||mPager.getCurrentItem()==1)
        {
            mPager.setCurrentItem(0);
        }
        else{
            finish();
        }
    }

    private void onFabClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScannerActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void setBottomNavigation() {
        adapter=new MainBtmAdapter(getSupportFragmentManager());
        mPager.setAdapter(adapter);
        mBtmNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.main_home:
                        mPager.setCurrentItem(0);
                        fab.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.main_profile:
                        mPager.setCurrentItem(1);
                        fab.setVisibility(View.GONE);
                        return true;
                    case R.id.main_aboutus:
                        mPager.setCurrentItem(2);
                        fab.setVisibility(View.GONE);
                        return true;
                }
                return false;
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mBtmNav.setSelectedItemId(R.id.main_home);

                }

                if(position==1){
                    mBtmNav.setSelectedItemId(R.id.main_profile);

                }
                if(position==2) {
                    mBtmNav.setSelectedItemId(R.id.main_aboutus);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

     //   Utilities.setPageTransitionAnimation(mPager);
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
