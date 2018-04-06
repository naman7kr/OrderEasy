package com.oeasy.ordereasy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oeasy.ordereasy.R;


/**
 * Created by Stan on 4/4/2018.
 */

public class AboutUsFragment extends Fragment {

    LayoutInflater inflater;
    ViewGroup container;

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
    }




}
