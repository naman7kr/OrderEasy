package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {
    Context context;
    ArrayList<FoodItem> list;
    public RatingAdapter(Context context, ArrayList<FoodItem> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public RatingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rating_item,parent,false);
        return new RatingHolder(view);
    }

    @Override
    public void onBindViewHolder(RatingHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.rBar.setOnRatingBarChangeListener(null);
        holder.rBar.setRating(list.get(position).getRating());
        holder.rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                list.get(position).setRating(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class RatingHolder extends RecyclerView.ViewHolder{
        TextView name;
        RatingBar rBar;
        public RatingHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.r_food_name);
            rBar=itemView.findViewById(R.id.r_rbar);
        }
    }
}
