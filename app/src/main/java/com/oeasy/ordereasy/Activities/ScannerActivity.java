package com.oeasy.ordereasy.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.oeasy.ordereasy.Modals.WaiterModel;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.RequestHandler;
import com.oeasy.ordereasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


/**
 * Created by Stan on 4/5/2018.
 */

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView sView;
    ZXingScannerView zscanner;
    DatabaseHelper db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initialize();
        setToolbar();
        zscanner = findViewById(R.id.scanner_zxing);
        sView=new ZXingScannerView(this);
        zscanner.addView(sView);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkPermission()){

            }
            else{
                requestCameraPermission();
            }
        }
        if(Build.VERSION.SDK_INT<=23)
        sView.startCamera();
        sView.setResultHandler(this);

    }

    private void initialize() {
        db=new DatabaseHelper(this);
    }

    private void requestCameraPermission() {
            if (ContextCompat.checkSelfPermission(this, CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void setToolbar() {
        toolbar=getToolbar();
        getSupportActionBar().setTitle("QR Scanner");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrowback);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(this, CAMERA)== PackageManager.PERMISSION_GRANTED);
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }


    @Override
    public void handleResult(Result result) {
        String sResult=result.getText();

        if(checkQRAcceptance(sResult)&&db.countWaiter()==0){
            String table_no=sResult.toLowerCase().replace("table ","").replace(" ","");
            Log.e("QR",table_no);
            sendWaiterRequest(table_no);
            startActivity(new Intent(this,CartActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
        else {
            Toast.makeText(this,"QR not accepted",Toast.LENGTH_LONG).show();
            sView.resumeCameraPreview(this);

        }
    }

    private void sendWaiterRequest(final String tableNo) {
        StringRequest request=new StringRequest(Request.Method.POST, Constants.URL_PROCESS_REQUEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                        Log.e("LOLA",response);
                        JSONObject waiters = new JSONObject(response);


                            WaiterModel waiter=new WaiterModel();
                            waiter.setName(waiters.getString("name"));
                            waiter.setTable_no(tableNo);
                    waiter.setContact_no(waiters.getString("contact"));
                            waiter.setWaiter_id(waiters.getInt("id"));
                            if(db.countWaiter()==0)
                            db.addWaiter(waiter);

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
                params.put("waiter_table", tableNo);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }


    private boolean checkQRAcceptance(String sResult) {
        return sResult.compareToIgnoreCase("Table 1")==0||sResult.compareToIgnoreCase("Table 2")==0||sResult.compareToIgnoreCase("Table 3")==0||sResult.compareToIgnoreCase("Table 4")==0||sResult.compareToIgnoreCase("Table 5")==0||sResult.compareToIgnoreCase("Table 6")==0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkPermission()){
                if(sView==null){
                    sView=new ZXingScannerView(this);
                    setContentView(sView);
                }
                sView.setResultHandler(this);
                sView.startCamera();
            }
            else{
                requestCameraPermission();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(checkPermission())
        sView.stopCamera();
    }
}
