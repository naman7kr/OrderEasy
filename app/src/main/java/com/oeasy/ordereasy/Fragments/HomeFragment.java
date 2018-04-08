package com.oeasy.ordereasy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.oeasy.ordereasy.Activities.MenuActivity;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stan on 4/4/2018.
 */

public class HomeFragment extends Fragment {
    private static final int RECOMMENDED_FLAG=0;
    private static final int DESSERT_FLAG = 3;
    private static final int STARTER_FLAG=1;
    private static final int MAINCOURSE_FLAG=2;
    private static final int DRINKS_FLAG=4;

    private RecyclerView rView;
    private HomeRecyclerAdapter adapter;
    private ArrayList<FoodItem> fItem=new ArrayList<>();
    private View recomended;
    private View drinks;
    private TextView seeAll;

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
        setDrinks(view);
        getData();
        return view;
    }
    public void getData() {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_TEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("JSON","OK");
                try {

                    JSONObject jsonObject=new JSONObject(response);

                    for(int i=0;i<jsonObject.length();i++){
//                        Log.e("JSON",jsonObject.get
//                        Log.e("JSON1",jsonArray.getJSONObject(i).getString("id"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("four", "s");
                return params;
            }
        };
        //RequestQueue requestQueue= Volley.newRequestQueue(this);
        //requestQueue.add(request);
        RequestHandler.getInstance(getContext()).addToRequestQueue(request);
    }
    private void initialize(View view) {

    }
    private void setSlider(View view) {

    }
    public void setRecomended(View view) {

        seeAll=view.findViewById(R.id.home_rec_seeall);
        onSeeAllClick(seeAll,RECOMMENDED_FLAG);

        rView = view.findViewById(R.id.home_recommended);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),RECOMMENDED_FLAG , getFoodItem(Constants.RECOMMENDED));
        rView.setAdapter(adapter);
    }
    private void setStarters(View view) {
        seeAll=view.findViewById(R.id.home_starter_seeall);
        onSeeAllClick(seeAll,STARTER_FLAG);

        rView = view.findViewById(R.id.home_starters);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), STARTER_FLAG, getFoodItem(Constants.STARTERS));
        rView.setAdapter(adapter);
    }

    private void setMainCourse(View view) {
        seeAll=view.findViewById(R.id.home_maincourse_seeall);
        onSeeAllClick(seeAll,MAINCOURSE_FLAG);

        rView = view.findViewById(R.id.home_maincourse);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), MAINCOURSE_FLAG, getFoodItem(Constants.MAIN_COURSE));
        rView.setAdapter(adapter);
    }
    private void setDesserts(View view) {
        seeAll=view.findViewById(R.id.home_desserts_seeall);
        onSeeAllClick(seeAll,DESSERT_FLAG);

        rView = view.findViewById(R.id.home_desserts);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), DESSERT_FLAG, getFoodItem(Constants.DESSERT));
        rView.setAdapter(adapter);
    }
    public void setDrinks(View view) {
        seeAll=view.findViewById(R.id.home_drinks_seeall);
        onSeeAllClick(seeAll,DRINKS_FLAG);

        rView = view.findViewById(R.id.home_drinks);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), DRINKS_FLAG, getFoodItem(Constants.DRINKS));
        rView.setAdapter(adapter);
    }

    private void onSeeAllClick(TextView seeAll, final int type) {
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MenuActivity.class);
                intent.putExtra("START_TAB",type);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

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
