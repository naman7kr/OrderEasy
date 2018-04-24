package com.oeasy.ordereasy.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.oeasy.ordereasy.Adapters.MenuItemAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;

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

    public void showViews(LinearLayout btmToolbar) {
        btmToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }

    public void hideViews(LinearLayout btmToolbar) {
        btmToolbar.animate().translationY(btmToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }


}
