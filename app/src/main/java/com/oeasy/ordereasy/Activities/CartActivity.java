package com.oeasy.ordereasy.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.oeasy.ordereasy.Adapters.CartTabsAdapter;
import com.oeasy.ordereasy.Adapters.HomeRecyclerAdapter;
import com.oeasy.ordereasy.Fragments.BillFragment;
import com.oeasy.ordereasy.Fragments.PreviewFragment;
import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stan on 4/6/2018.
 */

public class CartActivity extends BaseActivity {


    private CartTabsAdapter adapter;

    private Dialog mDialog;
    public ViewPager mPager;
    public TabLayout mTab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialize();
        setToolbar();
        setTabs();
        setOTP();
    }

    private void setOTP() {
        LinearLayout otpView=findViewById(R.id.otp_view);
        TextView otp=findViewById(R.id.otp);
        SharedPreferences sp= getSharedPreferences("table",MODE_PRIVATE);
        String op=sp.getString("otp","");
        String status=sp.getString("status","");
        Log.e("LOL",status);
        if(status.compareTo("-1")==0){
            if(op.compareTo("")!=0) {
                otpView.setVisibility(View.VISIBLE);
                otp.setText(op);
            }
        }else if(status.compareTo("1")==0){
            otpView.setVisibility(View.GONE);
            displayDialog();

        }else{
            otpView.setVisibility(View.GONE);
        }
    }



    private void displayDialog() {
        mDialog=new Dialog(this);
        mDialog.setContentView(R.layout.dialog_otp);
        final EditText dOTP=mDialog.findViewById(R.id.dialog_otp);
        Button cncl=mDialog.findViewById(R.id.dialog_otp_cancel);
        Button ok=mDialog.findViewById(R.id.dialog_otp_ok);
        mDialog.show();
        cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOTP(dOTP.getText().toString());

            }
        });
    }

    private void verifyOTP(String otp) {
        final JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email", GoogleSignIn.getLastSignedInAccount(this).getEmail().toString());
            jsonObject.put("otp",otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!="0"){
                    Toast.makeText(getApplicationContext(),"You are connected to table no : "+response,Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"OTP Entered is wrong",Toast.LENGTH_SHORT).show();

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
                params.put("verify_otp", String.valueOf(jsonObject));
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    private void initialize() {
        mTab=findViewById(R.id.cart_tabs);
        mPager=findViewById(R.id.cart_vp);
        adapter=new CartTabsAdapter(this,getSupportFragmentManager());
    }
    private void setTabs() {
        addTabs();
        mPager.setAdapter(adapter);
        mTab.setupWithViewPager(mPager);
    }

    private void addTabs() {
        adapter.addFragment(new PreviewFragment(), "Preview");
        adapter.addFragment(new BillFragment(), "Bill");
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("Cart");
        toolbar.setNavigationIcon(R.drawable.ic_action_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart_menu:
                startActivity(new Intent(this,MenuActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.cart_table_info:
                startActivity(new Intent(this,TableInformationActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}