package com.oeasy.ordereasy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.oeasy.ordereasy.Activities.CartActivity;
import com.oeasy.ordereasy.Adapters.CartPlaceOrderAdapter;
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

public class PlaceOrderFragment extends Fragment {
    private RecyclerView rView;
    private ArrayList<FoodItem> list;
    private DatabaseHelper db;
    CartPlaceOrderAdapter adapter;
    private Button poBtn;
    private static int TAG = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_place_order,container,false);
        initialize(view);

        setCartDetails();

        setData();

        onPlaceOrderClick();
        updateItemOnPageChange();
        return view;
    }
    private void updateItemOnPageChange() {
        CartActivity activity= (CartActivity) getActivity();
        activity.mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if(position==1){
                    Log.e("LOL", String.valueOf(position));
                    list.clear();
                    ArrayList<FoodItem> bList=db.getBillItems();

                    for(int i=0;i<bList.size();i++){
                        list.add(bList.get(i));
                        TAG=1;
                        adapter.notifyDataSetChanged();
                    }
                    ArrayList<FoodItem> cList=db.getAllFoodItems();
                    for(int i=0;i<cList.size();i++){
                        list.add(cList.get(i));
                        TAG=0;
                        adapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();
                    Log.e("LOL",list.toString());
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initialize(View view) {
        rView=view.findViewById(R.id.place_order_rv);
        db= new DatabaseHelper(getContext());
        list=new ArrayList<>();
        poBtn=view.findViewById(R.id.place_order_btn);
    }

    private void setCartDetails() {
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartPlaceOrderAdapter(getContext(), getList(),TAG);
        rView.setAdapter(adapter);
    }

    public ArrayList<FoodItem> getList() {
        return list;
    }

    private void setData() {
        ArrayList<FoodItem> bList=db.getBillItems();
        for(int i=0;i<bList.size();i++){
            list.add(bList.get(i));
            TAG=1;
            adapter.notifyDataSetChanged();
        }
        ArrayList<FoodItem> cList=db.getAllFoodItems();
        for(int i=0;i<cList.size();i++){
            list.add(cList.get(i));
            TAG=0;
            adapter.notifyDataSetChanged();
        }
        adapter.notifyDataSetChanged();
    }
    private void onPlaceOrderClick() {
        poBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.createBill(db.getAllFoodItems());
                sendDataToDatabase(db.getAllFoodItems());
                db.deleteAllFoodItems();
                recreateActivityCompat(getActivity());

            }
        });
    }
    private void sendDataToDatabase(ArrayList<FoodItem> fList) {

            final JSONArray jsonArray=new JSONArray();
        if(db.countWaiter()!=0){
        String tableNo=db.getAllWaiters().get(0).getTable_no();
        for(int i=0;i<fList.size();i++){
            JSONObject jObject=fList.get(i).getJSONObject();
            try {
                jObject.put("qty",fList.get(i).getQty());
                jObject.put("table_no",tableNo);
                jsonArray.put(jObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("JSON",jsonArray.toString());
            Toast.makeText(getContext(),"Your Order has been placed",Toast.LENGTH_LONG).show();
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
                params.put("store_order",jsonArray.toString() );
                return params;
            }
        };

        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);
        }else{
            Toast.makeText(getContext(),"No waiter added First scan QR",Toast.LENGTH_SHORT).show();
        }
    }

    public  final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        }
        else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
}
