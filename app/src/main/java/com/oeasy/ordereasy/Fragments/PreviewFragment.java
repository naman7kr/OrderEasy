package com.oeasy.ordereasy.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oeasy.ordereasy.Adapters.HorizontalRecyclerViewAdapter;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/14/2018.
 */

public class PreviewFragment extends Fragment {
    private RecyclerView mHorizontalRecyclerView;
    private LinearLayoutManager horizontalLayoutManager;
    private HorizontalRecyclerViewAdapter horizontalAdapter;
    private RecyclerView mVerticalRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private VerticalRecyclerViewAdapter verticalAdapter;
    private ArrayList<WaiterModel> waitersList;
    private ArrayList<FoodItem> itemsList;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);


        initialize(view);

        setCartLayout();
        setWaitersLayout();
       // getCartItems();
//        getWaiters();
        setRatingToDatabase();
      //  updateItemOnPageChange();
        return view;
    }
    private void initialize(View view) {
        mVerticalRecyclerView=view.findViewById(R.id.verticalRecyclerView);
        mHorizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);
        waitersList=new ArrayList<>();
        itemsList=new ArrayList<>();
        db = new DatabaseHelper(getContext());
    }
    private void setRatingToDatabase() {

    }
    private void setWaitersLayout() {
        horizontalAdapter = new HorizontalRecyclerViewAdapter(getContext(),getWaiters());
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);
        horizontalAdapter.notifyDataSetChanged();
    }

    private void setCartLayout() {
        verticalAdapter = new VerticalRecyclerViewAdapter(getContext(),getCartItems());
        verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
        mVerticalRecyclerView.setAdapter(verticalAdapter);
    }
    public ArrayList<FoodItem> getCartItems() {
            ArrayList<FoodItem> cList=db.getAllFoodItems();
            for(int i=0;i<cList.size();i++){
                cList.get(i).setTag(0);
                itemsList.add(cList.get(i));
                Log.e("preview cart",cList.get(i).getName());
            }
            ArrayList<FoodItem> bList=db.getBillItems();
            for(int i=0;i<bList.size();i++){
                bList.get(i).setTag(1);
                itemsList.add(bList.get(i));
                Log.e("preview bill",bList.get(i).getName());
            }
            return itemsList;
    }
    public ArrayList<WaiterModel> getWaiters() {
        ArrayList<WaiterModel> list=db.getAllWaiters();
        for(int i =0 ;i<list.size();i++){
            waitersList.add(list.get(i));
            Log.e("LOL",list.get(i).getName());
        }
        return waitersList;
    }
}
