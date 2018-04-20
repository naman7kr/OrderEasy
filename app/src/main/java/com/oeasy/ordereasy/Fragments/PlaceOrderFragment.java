package com.oeasy.ordereasy.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oeasy.ordereasy.Adapters.CartPlaceOrderAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/14/2018.
 */

public class PlaceOrderFragment extends Fragment {
    private RecyclerView rView;
    private ArrayList<FoodItem> list;
    private DatabaseHelper db;
    CartPlaceOrderAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_place_order,container,false);
        initialize(view);

        setCartDetails();

        setData();

        onPlaceOrderClick();
        return view;
    }

    private void initialize(View view) {
        rView=view.findViewById(R.id.place_order_rv);
        db= new DatabaseHelper(getContext());
        list=new ArrayList<>();
    }

    private void setCartDetails() {
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartPlaceOrderAdapter(getContext(), getList());
        rView.setAdapter(adapter);
    }

    public ArrayList<FoodItem> getList() {
        return list;
    }

    private void setData() {
        ArrayList<FoodItem> temp=db.getAllFoodItems();
        for(int i=0;i<temp.size();i++){
            list.add(temp.get(i));
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
    }
    private void onPlaceOrderClick() {

    }
}
