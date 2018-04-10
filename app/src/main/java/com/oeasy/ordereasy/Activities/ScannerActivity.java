package com.oeasy.ordereasy.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.oeasy.ordereasy.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by Stan on 4/5/2018.
 */

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView sView;
    ZXingScannerView zscanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

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
        Log.e("QR","HM");

    }
    @Override
    public void handleResult(Result result) {
        String sResult=result.getText();

        if(sResult.compareToIgnoreCase("stan.com")==0){
            String table_no=sResult.replace(".com","");
            Log.e("QR",table_no);
            startActivity(new Intent(this,CartActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        }
        else {
            Toast.makeText(this,"QR not accepted",Toast.LENGTH_LONG).show();
            sView.resumeCameraPreview(this);

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

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        Toast.makeText(this, "No Permission to use the Camera services", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                }

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        finish();
                    }
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
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
