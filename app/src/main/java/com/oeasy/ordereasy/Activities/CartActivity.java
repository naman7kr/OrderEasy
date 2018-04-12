package com.oeasy.ordereasy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.oeasy.ordereasy.Adapters.HorizontalRecyclerViewAdapter;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/6/2018.
 */

public class CartActivity extends BaseActivity {

    private RecyclerView mHorizontalRecyclerView;
    private LinearLayoutManager horizontalLayoutManager;
    private HorizontalRecyclerViewAdapter horizontalAdapter;

    private RecyclerView mVerticalRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private VerticalRecyclerViewAdapter verticalAdapter;
    private DatabaseHelper db;
    ArrayList<FoodItem> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialize();

        horizontalAdapter = new HorizontalRecyclerViewAdapter(fillWithData());
        horizontalLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);


        verticalAdapter = new VerticalRecyclerViewAdapter(fillWithDatav());
        verticalLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
        mVerticalRecyclerView.setAdapter(verticalAdapter);
        getItems();


    }

    private void initialize() {
        mVerticalRecyclerView=findViewById(R.id.verticalRecyclerView);
        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        db = new DatabaseHelper(this);
        arrayList=new ArrayList<>();
    }

    private ArrayList<FoodItem> fillWithDatav() {
        arrayList=new ArrayList<>();
        return arrayList;
    }

    public ArrayList<WaiterModel> fillWithData() {
        ArrayList<WaiterModel> imageModelArrayList = new ArrayList<>();

        WaiterModel waiterModel0=new WaiterModel();
        waiterModel0.setName("Utkarsh");
        imageModelArrayList.add(waiterModel0);

        WaiterModel waiterModel1=new WaiterModel();
        waiterModel1.setName("Utkarsh");
        imageModelArrayList.add(waiterModel1);

        WaiterModel waiterModel2=new WaiterModel();
        waiterModel2.setName("Utkarsh");
        imageModelArrayList.add(waiterModel2);

        WaiterModel waiterModel3=new WaiterModel();
        waiterModel3.setName("Utkarsh");
        imageModelArrayList.add(waiterModel3);

        WaiterModel waiterModel4=new WaiterModel();
        waiterModel4.setName("Utkarsh");
        imageModelArrayList.add(waiterModel4);

        WaiterModel waiterModel5=new WaiterModel();
        waiterModel5.setName("Utkarsh");
        imageModelArrayList.add(waiterModel5);

        return  imageModelArrayList;
    }

    public void getItems() {
       ArrayList<FoodItem> list=db.getAllFoodItems();
       for(int i =0 ;i<list.size();i++){
           arrayList.add(list.get(i));
           horizontalAdapter.notifyDataSetChanged();
           verticalAdapter.notifyDataSetChanged();
       }

        Log.e("LOL", String.valueOf(list.size()));
       horizontalAdapter.notifyDataSetChanged();
       verticalAdapter.notifyDataSetChanged();

    }
}