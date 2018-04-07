package com.oeasy.ordereasy.Activities;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.oeasy.ordereasy.R;

/**
 * Created by Stan on 4/6/2018.
 */

public class BaseActivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar=findViewById(R.id.include_toolbar);

        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
    public FloatingActionButton getFab(){
        return fab;
    }

}