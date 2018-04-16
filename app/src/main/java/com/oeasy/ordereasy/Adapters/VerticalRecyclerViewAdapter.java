package com.oeasy.ordereasy.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.oeasy.ordereasy.Modals.FoodItem;
import com.oeasy.ordereasy.Others.Constants;
import com.oeasy.ordereasy.Others.DatabaseHelper;
import com.oeasy.ordereasy.Others.Utilities;
import com.oeasy.ordereasy.R;

import java.util.ArrayList;

/**
 * Created by utkarshh12 on 4/11/2018.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter<VerticalRecyclerViewAdapter.MessageViewHolder> {

    private ArrayList<FoodItem> foodItemArrayList;
    private Context context;
    private DatabaseHelper db;

    public VerticalRecyclerViewAdapter(Context context,ArrayList<FoodItem> foodItems) {
        this.foodItemArrayList=foodItems;
        this.context=context;
        db=new DatabaseHelper(context);
    }

    @Override
    public int getItemViewType(int position) {
        return  super.getItemViewType(position);
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ItemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new VerticalRecyclerViewAdapter.MessageViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, final int position) {
        final FoodItem fdItem=foodItemArrayList.get(position);
        VerticalRecyclerViewAdapter.MessageViewHolder messageViewHolder =holder;
        messageViewHolder.fName.setText(fdItem.getName());
        messageViewHolder.fDescription.setText(fdItem.getDesc());
        messageViewHolder.rBar.setRating(fdItem.getRating());
        messageViewHolder.remBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFoodItem(fdItem.getFid());
                foodItemArrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        if (fdItem.getImg() != null)
            Utilities.setPicassoImage(context, Constants.IMG_ROOT+fdItem.getImg(), holder.fImage, Constants.SQUA_PLACEHOLDER);
        messageViewHolder.fPrice.setText(String.valueOf(fdItem.getPrice()));
        messageViewHolder.rBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                holder.rBar.setIsIndicator(false);
                db.setRating(fdItem.getFid(),rating);

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemArrayList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView fName;
        TextView fDescription;
        ImageView fImage;
        TextView fPrice;
        Spinner qtySp;
        RatingBar rBar;
        TextView remBtn;
        public MessageViewHolder(View itemView) {
            super(itemView);
            initialize(itemView);
        }

        private void initialize(View itemView) {
            fName=itemView.findViewById(R.id.cart_item_name);
            fDescription=itemView.findViewById(R.id.cart_item_description);
            fImage=itemView.findViewById(R.id.cart_item_img);
            fPrice=itemView.findViewById(R.id.cart_item_price);
            qtySp=itemView.findViewById(R.id.cart_item_qty_spinner);
            rBar=itemView.findViewById(R.id.cart_rating_bar);
            remBtn=itemView.findViewById(R.id.cart_item_remove_btn);
            remBtn.setTextColor(Color.RED);
        }
    }
}