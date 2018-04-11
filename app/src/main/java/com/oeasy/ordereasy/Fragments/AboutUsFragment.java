package com.oeasy.ordereasy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oeasy.ordereasy.Activities.MainActivity;
import com.oeasy.ordereasy.R;


/**
 * Created by Stan on 4/4/2018.
 */

public class AboutUsFragment extends Fragment {

    LayoutInflater inflater;
    ViewGroup container;
    ImageView about_us_image;
    TextView about_us_text;
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.qr_fragment,container,false);
        initialize(view);
        this.inflater=inflater;
        this.container=container;


        return view;
    }


    private void initialize(View v) {
        about_us_image=v.findViewById(R.id.about_us_image);
        about_us_text=v.findViewById(R.id.about_us_text);
        title= v.findViewById(R.id.title);



    }




}
