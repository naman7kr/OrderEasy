package com.oeasy.ordereasy.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.oeasy.ordereasy.Adapters.MainBtmAdapter;
import com.oeasy.ordereasy.Others.ZoomOutPageTransformer;
import com.oeasy.ordereasy.R;


public class MainActivity extends AppCompatActivity {
    private ViewPager mPager;
    private BottomNavigationView mBtmNav;
    MainBtmAdapter adapter;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setBottomNavigation();
        onFabClick();
        //checkCameraPermission();
    }

    private void onFabClick() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ScannerActivity.class));
            }
        });
    }



    private void initialize() {
        mPager=findViewById(R.id.main_vp);
        mBtmNav=findViewById(R.id.main_btmnav);
        fab=findViewById(R.id.main_fab);

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
                        return true;
                    case R.id.main_profile:
                        mPager.setCurrentItem(1);
                        return true;
                    case R.id.main_aboutus:
                        mPager.setCurrentItem(2);
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

        setPageTransitionAnimation();
    }

    private void setPageTransitionAnimation() {
        mPager.setPageTransformer(true,new ZoomOutPageTransformer());
    }


}
