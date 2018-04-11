package com.oeasy.ordereasy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oeasy.ordereasy.Adapters.HorizontalRecyclerViewAdapter;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mHorizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        horizontalAdapter = new HorizontalRecyclerViewAdapter(fillWithData());
        horizontalLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);

        mVerticalRecyclerView=findViewById(R.id.verticalRecyclerView);
        verticalAdapter = new VerticalRecyclerViewAdapter(fillWithDatav());
        verticalLayoutManager = new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
       mVerticalRecyclerView.setAdapter(verticalAdapter);



    }

    private ArrayList<FoodItem> fillWithDatav() {
        ArrayList<FoodItem> arrayList=new ArrayList<>();

        FoodItem foodItem=new FoodItem();
        foodItem.setName("khana");
        arrayList.add(foodItem);

        FoodItem foodItem1=new FoodItem();
        foodItem1.setName("khana");
        arrayList.add(foodItem1);

        FoodItem foodItem2=new FoodItem();
        foodItem2.setName("khana");
        arrayList.add(foodItem2);

        FoodItem foodItem3=new FoodItem();
        foodItem3.setName("khana");
        arrayList.add(foodItem3);


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
}
