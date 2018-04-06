package com.oeasy.ordereasy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/4/2018.
 */

public class HomeFragment extends Fragment {
    private static final int RECOMMENDED_FLAG=0;
    private static final int DESSERT_FLAG = 3;
    private static final int STARTER_FLAG=1;
    private static final int MAINCOURSE_FLAG=2;

    private RecyclerView rView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<FoodItem> fItem=new ArrayList<>();
    private View recomended;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragemnt, container, false);
        initialize(view);
        setSlider(view);
        setRecomended(view);
        setStarters(view);
        setMainCourse(view);
        setDesserts(view);

        return view;
    }
    private void initialize(View view) {

    }
    private void setSlider(View view) {

    }
    public void setRecomended(View view) {
        rView = view.findViewById(R.id.home_recommended);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),RECOMMENDED_FLAG , getFoodItem(Constants.RECOMMENDED));
        rView.setAdapter(adapter);
    }
    private void setStarters(View view) {
        rView = view.findViewById(R.id.home_starters);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), STARTER_FLAG, getFoodItem(Constants.STARTERS));
        rView.setAdapter(adapter);
    }

    private void setMainCourse(View view) {
        rView = view.findViewById(R.id.home_maincourse);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), MAINCOURSE_FLAG, getFoodItem(Constants.MAIN_COURSE));
        rView.setAdapter(adapter);
    }
    private void setDesserts(View view) {
        rView = view.findViewById(R.id.home_desserts);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), DESSERT_FLAG, getFoodItem(Constants.DESSERT));
        rView.setAdapter(adapter);
    }
    private ArrayList<FoodItem> getFoodItem(int type){
        fItem.clear();
        String name[]={"Chicken Kebab", "Chicken Lollipop","Chicken Majestic", "Herby Paneer Parcels","Vegetarian Bread Katori"};
        String img[]={"http://www.ojass.in/app/Images/HomeEvents/major_business.png",
                "http://www.ojass.in/app/Images/HomeEvents/major_directorscut.png",
                "http://www.ojass.in/app/Images/HomeEvents/major_codemania.png",
                "http://www.ojass.in/app/Images/HomeEvents/noground.png",
                "http://www.ojass.in/app/Images/HomeEvents/major_robowar.png"};
        for(int i=0;i<name.length;i++){
            FoodItem item=new FoodItem();
            item.setImg(img[i]);
            item.setName(name[i]);
            fItem.add(item);
        }
        return fItem;
    }


}
