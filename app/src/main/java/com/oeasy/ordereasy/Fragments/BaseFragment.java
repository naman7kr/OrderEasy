package com.oeasy.ordereasy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.oeasy.ordereasy.Activities.MenuActivity;
import com.oeasy.ordereasy.Adapters.MenuItemAdapter;
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
    public void setSearchToolbar(RecyclerView rView,ArrayList<FoodItem> list, final Toolbar btmToolbar) {

        rView.setOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews(btmToolbar);

            }

            @Override
            public void onShow() {
                showViews(btmToolbar);
            }
        });
        setToolbarIcons(btmToolbar,list,rView);
    }

    public void setToolbarIcons(final Toolbar toolbar, final ArrayList<FoodItem> list, final RecyclerView rView) {
        toolbar.inflateMenu(R.menu.menu_search);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    if(item.getItemId()==R.id.menu_item_search)
               {
                   android.support.v7.widget.SearchView searchView= (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
                   searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
                       @Override
                       public boolean onQueryTextSubmit(String query) {
                           return false;
                       }

                       @Override
                       public boolean onQueryTextChange(String newText) {
                           ArrayList<FoodItem> tempList=new ArrayList<>();
                           tempList.clear();
                           for(FoodItem temp: list){
                               if(temp.getName().toLowerCase().contains(newText.toLowerCase())){
                                   tempList.add(temp);
                               }
                           }
                           MenuItemAdapter adapter= new MenuItemAdapter(getContext(),tempList);
                           rView.setAdapter(adapter);
                           return true;
                       }
                   });
               }

                return true;
            }
        });
    }

    private void showViews(Toolbar btmToolbar) {
        btmToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }

    private void hideViews(Toolbar btmToolbar) {
        btmToolbar.animate().translationY(btmToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
    }
}
