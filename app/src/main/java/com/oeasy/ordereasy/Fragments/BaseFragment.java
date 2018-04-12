package com.oeasy.ordereasy.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.oeasy.ordereasy.Activities.MenuActivity;
import com.oeasy.ordereasy.Adapters.MenuItemAdapter;
import com.oeasy.ordereasy.Interfaces.MenuBtmSearchInterface;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.HidingScrollListener;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/9/2018.
 */

public class BaseFragment extends Fragment {

    private MenuItemAdapter adapter;
    public MenuItemAdapter setAdapter(Context c, ArrayList<FoodItem> items, RecyclerView rView){
        adapter=new MenuItemAdapter(c,items);
        rView.setAdapter(adapter);
        return adapter;
    }

    public void showViews(Toolbar btmToolbar) {
        btmToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }

    public void hideViews(Toolbar btmToolbar) {
        btmToolbar.animate().translationY(btmToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }


}
