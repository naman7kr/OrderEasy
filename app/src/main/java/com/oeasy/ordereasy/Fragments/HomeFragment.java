package com.oeasy.ordereasy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Activities.MenuActivity;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Adapters.HomeSliderAdapter;
import com.oeasy.ordereasy.Interfaces.NoInternetInterface;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.CustomScroller;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Stan on 4/4/2018.
 */

public class HomeFragment extends Fragment implements NoInternetInterface, ViewPager.OnPageChangeListener {

    private static final int BANNER_TRANSITION_DELAY = 1200;
    private static final int BANNER_DELAY_TIME = 5 * 1000;

    private Handler handler;
    private boolean firstScroll = true;
    private Runnable runnable;
    private RecyclerView rView;
    private ArrayList<HomeRecyclerAdapter> adapterList=new ArrayList<>();
    private ArrayList<ArrayList<FoodItem>> typeLists=new ArrayList<>();
    private HomeRecyclerAdapter adapter;
    private TextView seeAll;
    private LinearLayout erll;
    private ScrollView homell;
    private ProgressBar pBar;
    LinearLayout ref;
    ViewPager sliderVP;
    CircleIndicator indicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragemnt, container, false);
        initialize(view);
        setRecomended(view);
        setStarters(view);
        setMainCourse(view);
        setBreads(view);
        setDesserts(view);
        setDrinks(view);
        loadData();
        return view;
    }
    private void initialize(View view) {
        homell=view.findViewById(R.id.home_view);
        erll=view.findViewById(R.id.no_connection_view);
        pBar=view.findViewById(R.id.home_progress);
        ref= view.findViewById(R.id.tap_to_retry);
        sliderVP=view.findViewById(R.id.home_poster);
        indicator=view.findViewById(R.id.home_indicator);

        erll.setVisibility(View.GONE);
        homell.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);

        for(int i=0;i<6;i++){
            typeLists.add(new ArrayList<FoodItem>());
        }
    }
    public void loadData() {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                erll.setVisibility(View.GONE);
                homell.setVisibility(View.VISIBLE);
                pBar.setVisibility(View.GONE);
                try {
                    Log.e("LOLLL",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray fType = jsonObject.getJSONArray("food_items");

                    for (int i = 0; i < fType.length(); i++) {
                        JSONObject item = fType.getJSONObject(i);

                        FoodItem fItem = new FoodItem();
                        fItem.setName(item.getString("name"));
                        if (item.getString("image").compareTo("") == 0) {
                            fItem.setImg("R.drawable.placeholder_square");

                        } else
                            fItem.setImg(item.getString("image"));

                        fItem.setPrice((float) item.getDouble("price"));
                        fItem.setCategory(item.getInt("category"));
                        fItem.setQtyType(item.getInt("quantity_type"));
                        fItem.setDesc(item.getString("description"));
                        fItem.setFid(item.getInt("id"));
                        fItem.setType(item.getInt("item_type"));
                        typeLists.get(fItem.getType()).add(fItem);

                        HomeRecyclerAdapter ad = adapterList.get(fItem.getType());
                        ad.notifyDataSetChanged();
                    }
                    setSlider();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLayout();
                showRefreshPage();
                onRefresh();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();

                params.put("home", GoogleSignIn.getLastSignedInAccount(getContext()).getEmail());
                return params;
            }
        };

        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);
    }

    private void setSlider() {
        String img[]={"ic_slider1.jpg","ic_slider2.jpg","ic_slider3.jpg"};
        sliderVP.setAdapter(new HomeSliderAdapter(getContext(), img));
        indicator.setViewPager(sliderVP);
        sliderVP.setOnPageChangeListener(HomeFragment.this);
        try{
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(sliderVP, new CustomScroller(sliderVP.getContext(),BANNER_TRANSITION_DELAY ));
        } catch (Exception e){}

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                int currItem = sliderVP.getCurrentItem();
                if (currItem == 2){
                    sliderVP.setCurrentItem(0);
                } else {
                    sliderVP.setCurrentItem(++currItem);
                }
            }
        };
        handler.postDelayed(runnable, BANNER_DELAY_TIME);
    }

    public void setRecomended(View view) {

        seeAll=view.findViewById(R.id.home_rec_seeall);
        onSeeAllClick(seeAll,Constants.RECOMMENDED);

        rView = view.findViewById(R.id.home_recommended);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext() , getFoodItem(Constants.RECOMMENDED));
        adapterList.add(adapter);
        rView.setAdapter(adapter);
    }
    private void setStarters(View view) {
        seeAll=view.findViewById(R.id.home_starter_seeall);
        onSeeAllClick(seeAll,Constants.STARTERS);

        rView = view.findViewById(R.id.home_starters);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),  getFoodItem(Constants.STARTERS));
        adapterList.add(adapter);
        rView.setAdapter(adapter);
    }

    private void setMainCourse(View view) {
        seeAll=view.findViewById(R.id.home_maincourse_seeall);
        onSeeAllClick(seeAll,Constants.MAIN_COURSE);

        rView = view.findViewById(R.id.home_maincourse);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),getFoodItem(Constants.BREADS));
        adapterList.add(adapter);

        rView.setAdapter(adapter);
    }

    private void setBreads(View view) {
        seeAll=view.findViewById(R.id.home_bread_seeall);
        onSeeAllClick(seeAll,Constants.BREADS);

        rView = view.findViewById(R.id.home_bread);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),getFoodItem(Constants.MAIN_COURSE));
        adapterList.add(adapter);

        rView.setAdapter(adapter);
    }
    private void setDesserts(View view) {
        seeAll=view.findViewById(R.id.home_desserts_seeall);
        onSeeAllClick(seeAll,Constants.DESSERT);

        rView = view.findViewById(R.id.home_desserts);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(), getFoodItem(Constants.DESSERT));
        adapterList.add(adapter);

        rView.setAdapter(adapter);
    }
    public void setDrinks(View view) {
        seeAll=view.findViewById(R.id.home_drinks_seeall);
        onSeeAllClick(seeAll,Constants.DRINKS);

        rView = view.findViewById(R.id.home_drinks);
        rView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new HomeRecyclerAdapter(getContext(),  getFoodItem(Constants.DRINKS));
        adapterList.add(adapter);

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
        return typeLists.get(type);
    }

    @Override
    public void showRefreshLayout() {
        hideLayout();
        showRefreshPage();
        onRefresh();
    }

    private void onRefresh() {

        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recreateActivityCompat(getActivity());

            }
        });
    }
    public  final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
    private void showRefreshPage() {

        erll.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.GONE);
    }

    private void hideLayout() {
       homell.setVisibility(View.GONE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (firstScroll){
            firstScroll = false;
        } else {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE){
            handler.postDelayed(runnable, BANNER_DELAY_TIME);
        }
    }


}
