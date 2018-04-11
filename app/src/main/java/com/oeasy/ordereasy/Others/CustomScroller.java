package com.oeasy.ordereasy.Others;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Stan on 4/11/2018.
 */

public class CustomScroller extends Scroller {

    private int mDuration;

    public CustomScroller(Context context, int mDuration) {
        super(context);
        this.mDuration = mDuration;
    }

    public CustomScroller(Context context, Interpolator interpolator, int mDuration) {
        super(context, interpolator);
        this.mDuration = mDuration;
    }

    public CustomScroller(Context context, Interpolator interpolator, boolean flywheel, int mDuration) {
        super(context, interpolator, flywheel);
        this.mDuration = mDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
