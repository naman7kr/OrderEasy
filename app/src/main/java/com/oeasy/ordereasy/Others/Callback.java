package com.oeasy.ordereasy.Others;

/**
 * Created by Stan on 4/6/2018.
 */

public interface Callback {
    void onSuccess();

    void onError();

    public static class EmptyCallback implements com.squareup.picasso.Callback {

        @Override public void onSuccess() {
        }

        @Override public void onError() {
        }
    }
}
