package com.oeasy.ordereasy.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.oeasy.ordereasy.Activities.CartActivity;
import com.oeasy.ordereasy.Activities.MainActivity;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;

import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.PlaceOrderDialogFragment;

import com.oeasy.ordereasy.Others.SimpleItemTouchHelper;
import com.oeasy.ordereasy.R;


import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Stan on 4/14/2018.
 */

public class PreviewFragment extends Fragment implements PlaceOrderDialogFragment.Communicator{
    private RecyclerView mHorizontalRecyclerView;
    private LinearLayoutManager horizontalLayoutManager;
    private RecyclerView mVerticalRecyclerView;
    private LinearLayoutManager verticalLayoutManager;
    private VerticalRecyclerViewAdapter verticalAdapter;

    private ArrayList<FoodItem> itemsList;
    private DatabaseHelper db;

    private Button poBtn;
    String tn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);


        initialize(view);
        checkScan();
        setCartLayout();
        onPlaceOrderClick();


        return view;
    }

    private void checkScan() {
        SharedPreferences sp=getContext().getSharedPreferences("table",MODE_PRIVATE);
        String tn=sp.getString("table_no","");
        if(tn.compareTo("")!=1){
            poBtn.setEnabled(false);
        }else{
            poBtn.setEnabled(true);
        }
    }

    private void initialize(View view) {
        mVerticalRecyclerView=view.findViewById(R.id.verticalRecyclerView);
        poBtn=view.findViewById(R.id.place_order_btn);
        itemsList=new ArrayList<>();
        db = new DatabaseHelper(getContext());
    }
    private void onPlaceOrderClick() {
        poBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     sendDataToDatabase(db.getAllFoodItems());
                createPlaceOrderDialog();
//                db.deleteAllFoodItems();
//                startActivity(new Intent(getContext(), MainActivity.class));
//                getActivity().finish();
            }
        });
    }

    private void createPlaceOrderDialog() {
        PlaceOrderDialogFragment dialog=new PlaceOrderDialogFragment(getContext(),this,itemsList);
        dialog.show(getFragmentManager(),"MyFragment");


    }

    private void setCartLayout() {
        verticalAdapter = new VerticalRecyclerViewAdapter(getContext(),getCartItems());
        verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelper(verticalAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mVerticalRecyclerView);
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


    @Override
    public void communicatorMessage(String message) {
        if(message.compareTo("confirm")==0){
            itemsList.clear();
            verticalAdapter.notifyDataSetChanged();
        }
    }
}

