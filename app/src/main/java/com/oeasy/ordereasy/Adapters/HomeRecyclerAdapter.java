package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by Stan on 4/5/2018.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FoodItem> items;
    private int type;
    public HomeRecyclerAdapter(Context context, int type, ArrayList<FoodItem> items) {
        this.items=items;
        this.context=context;
        this.type=type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.home_item_view,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FoodItem current = items.get(position);
        Log.e("hm",current.getImg().toString());
        if (current.getImg() != null)
            Utilities.setPicassoImage(context, current.getImg(), holder.fImg, Constants.SQUA_PLACEHOLDER);
        holder.fName.setText(current.getName());
    }

    @Override
    public int getItemCount() {
       return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fImg;
        TextView fName;
        CardView fView;

        public ViewHolder(View itemView) {
            super(itemView);
            fImg=itemView.findViewById(R.id.home_food_img);
            fName=itemView.findViewById(R.id.home_food_name);
            fView=itemView.findViewById(R.id.home_food_view);
        }
    }
}