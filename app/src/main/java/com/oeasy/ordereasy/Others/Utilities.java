package com.oeasy.ordereasy.Others;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.oeasy.ordereasy.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Stan on 4/5/2018.
 */

public class Utilities {

    public static void changeStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    public static void setPicassoImage(final Context context, final String imgSrc, final ImageView iv, final int FLAG){
        if (FLAG == Constants.SQUA_PLACEHOLDER){
            Log.e("HM","HM");
            Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder_sqaure).fit().networkPolicy(NetworkPolicy.OFFLINE).into(iv, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder_sqaure).fit().into(iv);
                }
            });
        } else {
            Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder_rect).fit().networkPolicy(NetworkPolicy.OFFLINE).into(iv, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(context).load(imgSrc).placeholder(R.drawable.placeholder_rect).fit().into(iv);
                }
            });
        }
    }
    public static void setPageTransitionAnimation(ViewPager mPager) {
        mPager.setPageTransformer(true,new FlipHorizontalTransformer());
    }
}