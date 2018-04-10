package com.oeasy.ordereasy.Others;

import android.app.Application;

/**
 * Created by Stan on 4/9/2018.
 */

public class OrderEasyApplication extends Application {

    public static OrderEasyApplication mInstance;
    public static synchronized OrderEasyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
