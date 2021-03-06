package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.provider.SyncStateContract;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

/**
 * Created by Stan on 4/5/2018.
 */

public class HomeSliderAdapter extends PagerAdapter {

    private Context context;
    private String[] imageUrls;

    public HomeSliderAdapter(Context context, String[] imageUrls){
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutinflater.inflate(R.layout.item_slider, container, false);
        ImageView iv=view.findViewById(R.id.iv_poster);
        Utilities.setPicassoImage(context, Constants.IMG_SLIDER_ROOT+ imageUrls[position], iv, 0);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
