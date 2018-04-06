package com.oeasy.ordereasy.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * Created by Stan on 4/5/2018.
 */

public class





















ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView sView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        sView=new ZXingScannerView(this);
        setContentView(sView);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkPermission()){
                Toast.makeText(this,"Permission is granted",Toast.LENGTH_LONG).show();
            }
            else{
                requestCameraPermission();
            }
        }

    }
    private void requestCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
    }
    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void handleResult(Result result) {
        String sResult=result.getText();
        Log.e("QR",sResult);
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
        sView.stopCamera();
    }
}
