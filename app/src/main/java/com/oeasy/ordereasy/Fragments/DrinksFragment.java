package com.oeasy.ordereasy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.oeasy.ordereasy.Activities.MenuActivity;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Adapters.MenuItemAdapter;
import com.oeasy.ordereasy.Interfaces.MenuBtmSearchInterface;
import com.oeasy.ordereasy.Interfaces.NoInternetInterface;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.HidingScrollListener;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stan on 4/6/2018.
 */

public class DrinksFragment extends BaseFragment implements NoInternetInterface,MenuBtmSearchInterface{
    private ArrayList<FoodItem> dList=new ArrayList<>();
    private MenuItemAdapter adapter;
    private RecyclerView rView;
    private ProgressBar pBar;
    LinearLayout ref,erll;
    private Toolbar btmToolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_drinks,container,false);
        initialize(view);
        setRecView(rView);

        loadData();

        return view;

    }

    private void setRecView(RecyclerView rView) {
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=setAdapter(getContext(),dList,rView);
    }

    private void initialize(View view) {
        rView=view.findViewById(R.id.drinks_rview);
        pBar=view.findViewById(R.id.menu_drinks_progress);
        ref= view.findViewById(R.id.tap_to_retry);
        erll=view.findViewById(R.id.no_connection_view);
        MenuActivity activity= (MenuActivity) getActivity();
        btmToolbar=activity.findViewById(R.id.btm_toolbar);

        erll.setVisibility(View.GONE);
        rView.setVisibility(View.GONE);
        pBar.setVisibility(View.VISIBLE);
    }

    public void loadData() {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                erll.setVisibility(View.GONE);
                rView.setVisibility(View.VISIBLE);
                pBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray fType = jsonObject.getJSONArray( getType(Constants.DRINKS));
                    dList.clear();
                    for (int i = 0; i < fType.length(); i++) {
                        JSONObject item = fType.getJSONObject(i);

                        FoodItem fItem=new FoodItem();
                        fItem.setName(item.getString("name"));
                        if(item.getString("image")==null){
                            fItem.setImg("R.drawable.placeholder_square");
                        }else
                            fItem.setImg(item.getString("image"));
                        fItem.setPrice((float) item.getDouble("price"));
                        fItem.setCategory(item.getInt("category"));
                        fItem.setQtyType(item.getInt("quantity_type"));
                        fItem.setDesc(item.getString("description"));
                        dList.add(fItem);
                        adapter.notifyDataSetChanged();


                    }
                    setBtmToolbar();
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
                params.put("all", "s");
                return params;
            }
        };

        RequestHandler.getInstance(getContext(),this).addToRequestQueue(request);
    }
    private void setBtmToolbar() {

        new MenuBtmSearchInterface() {
            @Override
            public void setSearchToolbar() {
                rView.setOnScrollListener(new HidingScrollListener() {
                    @Override
                    public void onHide() {

                        hideViews(btmToolbar);

                    }

                    @Override
                    public void onShow() {
                        showViews(btmToolbar);
                    }
                });

            }

            @Override
            public void setToolbarIcon(MenuItem item,SearchView searchView) {


            }
        }.setSearchToolbar();

    }
    public String getType(int tPos) {
        if(tPos==1){
            return "starters";
        }
        if(tPos==2){
            return "main_course";
        }
        if(tPos==3){
            return "dessert";
        }
        if(tPos==4){
            return "drinks";
        }
        return null;
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
        rView.setVisibility(View.GONE);
    }
    @Override
    public void showRefreshLayout() {
        hideLayout();
        showRefreshPage();
        onRefresh();
    }

    @Override
    public void setSearchToolbar() {

    }

    @Override
    public void setToolbarIcon(MenuItem item,SearchView searchView) {

        searchView.setQueryHint("Search in Drinks");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<FoodItem> tempList = new ArrayList<>();
                    tempList.clear();
                    for (FoodItem temp : dList) {
                        if (temp.getName().toLowerCase().replace(" ","").contains(newText.toLowerCase().replace(" ",""))){
                            tempList.add(temp);
                        }
                    }
                    MenuItemAdapter adapter = new MenuItemAdapter(getContext(), tempList);
                    rView.setAdapter(adapter);
                    return true;
                }
            });

    }
}
