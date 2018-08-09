package com.oeasy.ordereasy.Activities;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Modals.UserInformation;
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
 * Created by Stan on 4/6/2018.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    private int check=-1;
    private final int SHOW_RATING=1;
    Dialog mDialog;
    DatabaseHelper db;
    public String res="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar=findViewById(R.id.include_toolbar);
        fab=findViewById(R.id.include_fab);
       checkData();

        if(toolbar!=null){
            setSupportActionBar(toolbar);
        }

    }
    private void checkData() {

        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOL",response);

                if(response.compareTo("")!=0){
                    db.deleteAllFoodItems();
                    db.deleteAllBill();
                    db.deleteAllWaiters();
                    SharedPreferences sp=getSharedPreferences("table",MODE_PRIVATE);
                    SharedPreferences.Editor ed= sp.edit();
                    ed.remove("table_no");
                    ed.commit();
                    res=response;
                    showRating();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("check_item", GoogleSignIn.getLastSignedInAccount(getApplicationContext()).getEmail());
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
    private void showRating() {
        mDialog=null;
        mDialog=new Dialog(this);
        mDialog.setContentView(R.layout.rating_dialog);
        mDialog.show();
        mDialog.findViewById(R.id.rat_cancel).setOnClickListener(this);
        mDialog.findViewById(R.id.rat_later).setOnClickListener(this);
        mDialog.findViewById(R.id.rate_yes).setOnClickListener(this);
    }

    public Toolbar getToolbar(){
        return toolbar;
    }
    public FloatingActionButton getFab(){
        return fab;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rat_cancel){

        }
        if(view.getId()==R.id.rat_later){

        }
        if(view.getId()==R.id.rate_yes){
            mDialog.dismiss();
            Intent intent=new Intent(getApplicationContext(),RatingActivity.class);
            intent.putExtra("rat_data",res);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( mDialog!=null && mDialog.isShowing() ){
            mDialog.cancel();
        }
    }
}