package com.oeasy.ordereasy.Others;

import android.app.Activity;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.oeasy.ordereasy.Fragments.BreadFragment;
import com.oeasy.ordereasy.Fragments.DessertFragment;
import com.oeasy.ordereasy.Fragments.DrinksFragment;
import com.oeasy.ordereasy.Fragments.HomeFragment;
import com.oeasy.ordereasy.Fragments.MainCourseFragment;
import com.oeasy.ordereasy.Fragments.PlaceOrderFragment;
import com.oeasy.ordereasy.Fragments.PreviewFragment;
import com.oeasy.ordereasy.Fragments.RecommendedFragment;
import com.oeasy.ordereasy.Fragments.StartersFragment;
import com.oeasy.ordereasy.Interfaces.NoInternetInterface;

/**
 * Created by Stan on 2/11/2018.
 */

public class RequestHandler extends OrderEasyApplication {
    private static RequestHandler mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private NoInternetInterface callback;
    public static HomeFragment f1;
    public static StartersFragment f2;
    public static MainCourseFragment f3;
    public static DessertFragment f4;
    public static DrinksFragment f5;
    public static RecommendedFragment f6;
    public static BreadFragment f7;
    public static PreviewFragment f8;
    public static PlaceOrderFragment f9;

    private RequestHandler(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }
    public static synchronized RequestHandler getInstance(Context context,HomeFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f1= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,StartersFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f2= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,MainCourseFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f3= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,DessertFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f4= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,DrinksFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f5= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,RecommendedFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f6= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,BreadFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f7= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,PreviewFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f8= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Context context,PlaceOrderFragment f) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        f9= f;
        return mInstance;
    }
    public static synchronized RequestHandler getInstance(Activity activity) {
        if (mInstance == null) {
            mInstance = new RequestHandler(activity);
        }

        return mInstance;
    }


    public static synchronized RequestHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestHandler(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        if (Utilities.isNetworkAvailable(mCtx)) {
            req.setRetryPolicy(new DefaultRetryPolicy(
                    60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            getRequestQueue().add(req);
        } else {
            if(f1!=null) {
                callback = f1;
                callback.showRefreshLayout();
            }
            else if(f2!=null){
                callback = f2;
                callback.showRefreshLayout();
            }
            else if(f3!=null){
                callback = f3;
                callback.showRefreshLayout();
            }
            else if(f4!=null){
                callback = f4;
                callback.showRefreshLayout();
            }
            else if(f5!=null){
                callback = f5;
                callback.showRefreshLayout();
            }
            else if(f6!=null){
                callback = f6;
                callback.showRefreshLayout();
            }
            else if(f7!=null){
                callback = f7;
                callback.showRefreshLayout();
            }
            else if(f8!=null){
                callback = (NoInternetInterface) f8;
                callback.showRefreshLayout();
            }
            else if(f9!=null){
                callback = (NoInternetInterface)f9;
                callback.showRefreshLayout();
            }
            else {
                callback= (NoInternetInterface) mCtx;
                callback.showRefreshLayout();
            }
        }
    }
}