package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.oeasy.ordereasy.Adapters.CartTabsAdapter;
import com.oeasy.ordereasy.Adapters.HorizontalRecyclerViewAdapter;
import com.oeasy.ordereasy.Adapters.MenuTabsAdapter;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Fragments.PlaceOrderFragment;
import com.oeasy.ordereasy.Fragments.PreviewFragment;
import com.oeasy.ordereasy.Fragments.RecommendedFragment;
import com.oeasy.ordereasy.Fragments.StartersFragment;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/6/2018.
 */

public class CartActivity extends BaseActivity {


    private CartTabsAdapter adapter;


    private ViewPager mPager;
    private TabLayout mTab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialize();
        setToolbar();
        setTabs();
    }
    private void initialize() {
        mTab=findViewById(R.id.cart_tabs);
        mPager=findViewById(R.id.cart_vp);
        adapter=new CartTabsAdapter(this,getSupportFragmentManager());


    }
    private void setTabs() {
        addTabs();
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }

    private void addTabs() {
        adapter.addFragment(new PreviewFragment(), "Preview");
        adapter.addFragment(new PlaceOrderFragment(), "Place Order");
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("Cart");
        toolbar.setNavigationIcon(R.drawable.ic_action_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
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