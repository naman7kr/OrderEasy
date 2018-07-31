package com.oeasy.ordereasy.Others;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.oeasy.ordereasy.Adapters.RatingAdapter;
import com.oeasy.ordereasy.R;

public class RatingClass {
    private static RecyclerView rView;
    private static void createRatingDialogue(Context context) {
        Dialog rDialog=new Dialog(context);
        rDialog.setContentView(R.layout.rating_dialog);
        rView=rDialog.findViewById(R.id.rating_rview);

    }
}
