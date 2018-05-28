package com.oeasy.ordereasy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Activities.MenuActivity;
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

public class RecommendedFragment extends BaseFragment implements NoInternetInterface,MenuBtmSearchInterface{
    private ArrayList<FoodItem> rList=new ArrayList<>();
    private MenuItemAdapter adapter;
    private RecyclerView rView;
    private ProgressBar pBar;
    LinearLayout ref,erll;
    private LinearLayout btmToolbar;
    private TextView noRec;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommended,container,false);
        initialize(view);
        setRecView(rView);
        setBtmToolbar();
        loadData();
        return view;
    }
    private void setRecView(RecyclerView rView) {
        rView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=setAdapter(getContext(),rList,rView);
    }

    private void initialize(View view) {
        rView=view.findViewById(R.id.rec_rview);
        pBar=view.findViewById(R.id.menu_recommended_progress);
        ref= view.findViewById(R.id.tap_to_retry);
        erll=view.findViewById(R.id.no_connection_view);
        MenuActivity activity= (MenuActivity) getActivity();
        btmToolbar=activity.findViewById(R.id.btm_toolbar);
        noRec=view.findViewById(R.id.rec_norecommended);
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
                    JSONArray fType = jsonObject.getJSONArray("food_items");
                    rList.clear();
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
                        fItem.setFid(item.getInt("id"));
                        fItem.setType(item.getInt("item_type"));
                        if(fItem.getType()==Constants.RECOMMENDED){
                        rList.add(fItem);
                        adapter.notifyDataSetChanged();
                        }
                    }
                    if(rList.size()==0){
                        noRec.setVisibility(View.VISIBLE);
                    }else{
                        noRec.setVisibility(View.GONE);
                    }

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
                params.put("all", GoogleSignIn.getLastSignedInAccount(getContext()).getEmail());
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

            @Override
            public void filterItems(String title) {
                
            }
        }.setSearchToolbar();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setToolbarIcon(MenuItem item,SearchView searchView) {


             searchView.setQueryHint("Search in Recommended");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ArrayList<FoodItem> tempList = new ArrayList<>();
                    tempList.clear();
                    for (FoodItem temp : rList) {
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

    @Override
    public void filterItems(String title) {
        ArrayList<FoodItem> fList=new ArrayList<>();
        if(title.compareTo("Veg")==0){
            for(int i=0;i<rList.size();i++){
                if(rList.get(i).getCategory()==0){
                    fList.add(rList.get(i));
                }
            }
        }else if(title.compareTo("Non Veg")==0){
            for(int i=0;i<rList.size();i++){
                if(rList.get(i).getCategory()==1){
                    fList.add(rList.get(i));
                }
            }
        }else{
            for(int i=0;i<rList.size();i++){
                fList.add(rList.get(i));
            }
        }
        setAdapter(getContext(),fList,rView);
    }
}
