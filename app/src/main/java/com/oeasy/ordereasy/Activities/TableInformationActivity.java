package com.oeasy.ordereasy.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.oeasy.ordereasy.Adapters.UsersAdapter;
import com.oeasy.ordereasy.Adapters.WaitersAdapter;
import com.oeasy.ordereasy.Modals.UserInformation;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableInformationActivity extends BaseActivity {
    RecyclerView wRView,uRView;
    ArrayList<WaiterModel> wList;
    ArrayList<UserInformation> uList;
    String tn;
    TextView tno;
    WaitersAdapter wAdapter;
    UsersAdapter uAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_info);
        initialize();
        getTableNo();
        setAdapters();
        sendRequest();

    }

    private void sendRequest() {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RES",response);
                try {
                    JSONObject jOb=new JSONObject(response);
                    JSONObject wOb=jOb.getJSONObject("waiters_info");

                    WaiterModel waiter=new WaiterModel();
                    waiter.setName(wOb.getString("name"));
                    waiter.setContact_no(wOb.getString("contact_no"));
                    if(waiter.getContact_no()!=""||waiter.getName()!="")
                        wList.add(waiter);

                    JSONArray jA=jOb.getJSONArray("users_info");
                    for(int i=0;i<jA.length();i++){
                        JSONObject jOb1=jA.getJSONObject(i);
                        UserInformation user=new UserInformation();
                        user.setEmail(jOb1.getString("name"));
                        if(user.getEmail()!="")
                            uList.add(user);
                    }
                    uAdapter.notifyDataSetChanged();
                    wAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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

                params.put("table_all",tn);
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void getTableNo() {
        SharedPreferences sp = getSharedPreferences("table",MODE_PRIVATE);
        tn=sp.getString("table_no","");
        Log.e("TN",tn);
    }

    private void initialize() {
        wRView=this.findViewById(R.id.activity_table_info_rvw);
        uRView=this.findViewById(R.id.activity_table_info_rvu);
        wList=new ArrayList<>();
        uList=new ArrayList<>();
        wRView.setLayoutManager(new LinearLayoutManager(this));
        uRView.setLayoutManager(new LinearLayoutManager(this));
        tno=findViewById(R.id.activity_table_info_tn);
    }
    private void setAdapters() {
        tno.setText(tn);
         wAdapter=new WaitersAdapter(this,wList);
        wRView.setAdapter(wAdapter);

         uAdapter=new UsersAdapter(this,uList);
        uRView.setAdapter(uAdapter);
    }
}
