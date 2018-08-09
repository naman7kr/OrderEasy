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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Adapters.CartPlaceOrderAdapter;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stan on 4/14/2018.
 */

public class BillFragment extends Fragment {
    private RecyclerView rView;
    private ArrayList<FoodItem> list;
    private DatabaseHelper db;
    CartPlaceOrderAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_bill,container,false);
        initialize(view);

        setCartDetails();
        getData();
        setData();

        return view;
    }

    private void getData() {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("get_bill","");
                return params;
            }
        };

        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);
    }

    private void initialize(View view) {
        rView=view.findViewById(R.id.bill_rv);
        db= new DatabaseHelper(getContext());
        list=new ArrayList<>();


    }

    private void setCartDetails() {
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartPlaceOrderAdapter(getContext(), setData());
        rView.setAdapter(adapter);
    }
    private ArrayList<FoodItem> setData() {
        ArrayList<FoodItem> bList=db.getBillItems();
        for(int i=0;i<bList.size();i++){
            bList.get(i).setTag(1);
            list.add(bList.get(i));
        }
        ArrayList<FoodItem> cList=db.getAllFoodItems();
        for(int i=0;i<cList.size();i++){
            cList.get(i).setTag(0);
            list.add(cList.get(i));
        }
        return list;
    }
}
