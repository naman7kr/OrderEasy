package com.oeasy.ordereasy.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Adapters.RatingAdapter;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rView;
    private ArrayList<FoodItem> rList;
    HashSet<FoodItem> unq;
    private Button fB;
    private Button cB;
    String data;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        initialize();
        setAdapter();

        fB.setOnClickListener(this);
        cB.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initialize() {
        rView=findViewById(R.id.rating_rview);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rList=new ArrayList<>();
        fB=findViewById(R.id.rat_finish);
        cB=findViewById(R.id.rat_cancel);
        data=getIntent().getStringExtra("rat_data");
        int r=0,l=0,k=0;
        while(r<data.length()){
            if(r+1<data.length()&&data.charAt(r)==' '&&data.charAt(r+1)==','){
                FoodItem item=new FoodItem();
                item.setName(data.substring(l,r));
                rList.add(item);
                l=r+2;
                r=r+2;
                k++;
            }
            r++;
        }
       rList=removeDuplicates(rList);
    }
    private void setAdapter() {
        rView.setAdapter(new RatingAdapter(this,rList));
    }
    private ArrayList<FoodItem> removeDuplicates(ArrayList<FoodItem> list) {
        ArrayList<FoodItem> result = new ArrayList<>();
        HashSet<String> set = new HashSet<>();
        for (FoodItem item : list) {
            if (!set.contains(item.getName())) {
                result.add(item);
                set.add(item.getName());
            }
        }
        return result;
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.rat_finish){
            JSONObject jOB=prepareData();
            sendData(jOB);
            Log.e("JOB",jOB.toString());
            finish();
        }
        if(view.getId()==R.id.rat_cancel){

        }
    }

    private void sendData(final JSONObject jOb) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("store_rating", jOb.toString());
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private JSONObject prepareData() {
        JSONObject jOB=new JSONObject();
        try {
            jOB.put("email", GoogleSignIn.getLastSignedInAccount(this).getEmail());
            JSONArray jA=new JSONArray();
            jOB.put("food",jA);
            for(int i=0;i<rList.size();i++){
                JSONObject jOB1=new JSONObject();
                jOB1.put("food_name",rList.get(i).getName());
                jOB1.put("food_rat",rList.get(i).getRating());
                jA.put(i,jOB1);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jOB;
    }
}
