package com.oeasy.ordereasy.Fragments;

import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.oeasy.ordereasy.Activities.CartActivity;
import com.oeasy.ordereasy.Activities.MainActivity;
import com.oeasy.ordereasy.Adapters.HorizontalRecyclerViewAdapter;
import com.oeasy.ordereasy.Adapters.VerticalRecyclerViewAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.WaiterModel;
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
    private Button subBtn;
    private static int TAG = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);


        initialize(view);

        setCartLayout();
        setWaitersLayout();
        getCartItems();
        getWaiters();
        setRatingToDatabase();
      //  updateItemOnPageChange();
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
                if(position==0){
                    Log.e("LOL", String.valueOf(position));
                    verticalAdapter.notifyDataSetChanged();
                    horizontalAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initialize(View view) {
        mVerticalRecyclerView=view.findViewById(R.id.verticalRecyclerView);
        mHorizontalRecyclerView = view.findViewById(R.id.horizontalRecyclerView);
        waitersList=new ArrayList<>();
        itemsList=new ArrayList<>();
        db = new DatabaseHelper(getContext());
        subBtn=view.findViewById(R.id.cart_submit_rating_btn);
    }
    private void setRatingToDatabase() {
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FoodItem> fItem=db.getAllFoodItems();
                //send json array to database
                db.deleteAllFoodItems();
                GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(getContext());
                JSONArray jArray=null;
                    try {
                jArray=new JSONArray();
                for(int i=0;i<fItem.size();i++){
                    JSONObject jObject=fItem.get(i).getJSONObject();
                    jObject.put("rating",fItem.get(i).getRating());
                    jObject.put("email",account.getEmail());
                    jArray.put(jObject);
                    }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                }
                Log.e("LOL",jArray.toString());
                sendData(jArray);
                Intent intent=new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }
    private void sendData(final JSONArray jsonArray) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOL",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("review_entry",jsonArray.toString() );
                return params;
            }
        };
        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);

    }

    private void setWaitersLayout() {
        horizontalAdapter = new HorizontalRecyclerViewAdapter(getContext(),getWaitersList());
        horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mHorizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        mHorizontalRecyclerView.setAdapter(horizontalAdapter);
        horizontalAdapter.notifyDataSetChanged();
    }

    private void setCartLayout() {
        verticalAdapter = new VerticalRecyclerViewAdapter(getContext(),getItemsList(),TAG);
        verticalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mVerticalRecyclerView.setLayoutManager(verticalLayoutManager);
        mVerticalRecyclerView.setAdapter(verticalAdapter);
    }

    public ArrayList<WaiterModel> getWaitersList() {
        return waitersList;
    }

    public ArrayList<FoodItem> getItemsList() {
        return itemsList;
    }
    public void getCartItems() {
            ArrayList<FoodItem> cList=db.getAllFoodItems();
            for(int i=0;i<cList.size();i++){
                itemsList.add(cList.get(i));
                TAG=0;
                verticalAdapter.notifyDataSetChanged();
                Log.e("preview cart",cList.get(i).getName());
            }
            ArrayList<FoodItem> bList=db.getBillItems();
            for(int i=0;i<bList.size();i++){
                itemsList.add(bList.get(i));
                TAG=1;
                verticalAdapter.notifyDataSetChanged();
                Log.e("preview bill",bList.get(i).getName());
            }
    }

    public void getWaiters() {
        ArrayList<WaiterModel> list=db.getAllWaiters();
        for(int i =0 ;i<list.size();i++){
            waitersList.add(list.get(i));
            horizontalAdapter.notifyDataSetChanged();
        }
        horizontalAdapter.notifyDataSetChanged();
    }
}
